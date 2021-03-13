package edu.eci.arep.dockerapp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class RoundBobinMain {
    
    private static int bal = 1;
    public static void main(String args[]) {
        port(getPort());
        get("/Datos", (req, res) -> inputDataPage(req, res) );
        get("/Resultados", (req, res) -> resultsPage(req, res));

    }

    private static String inputDataPage(Request req, Response res) {
        String page
                = "<!DOCTYPE html>"
                + "<html>"
                +"<body style=\"background-color:#F8F9C1;\">" 
    			+"<font align=\"center\" color=\"Olive\" face=\"Comic Sans MS,arial\">"
                + "<h2>Registro de cadenas laboratorio de Docker y AWS</h2>"
                + "<form action=\"/Resultados\">"
                + "  Ingrese la cadena <br>"
                + "  <input type=\"text\" name=\"param\" >"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return page;
    }
    
    private static String resultsPage(Request req, Response res) throws MalformedURLException, IOException {
        
        String param = req.queryParams("param").replace(" ","+");        
        BufferedReader entrada = null;        
        bal = getNumLogService();        
        URL roundService = null;
        if (bal==1){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34000/Resultados?param="+param);
        } else if (bal==2){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34001/Resultados?param="+param);
        } else if (bal==3){
        	roundService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:34002/Resultados?param="+param);
        }            
        URLConnection urlcon = roundService.openConnection();        
        entrada = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));		
        BufferedReader stdIn = new BufferedReader(
        new InputStreamReader(System.in));
        String line = entrada.readLine();       
        System.out.println(line);
        entrada.close();
        stdIn.close();
        res.header("Content-Type", "application/json");  
        return line.replace("\\\"", "").replace(",","");
    }
    
    private static int getNumLogService(){
        if (bal == 3){
            bal = 0;
        }
        bal++;        
        return bal;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 12000;
    }

}