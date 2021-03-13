package edu.eci.arep.dockerapp;

import static spark.Spark.*;
import org.json.JSONObject;
import spark.Request;
import spark.Response;


public class SparkWebServer {
    
	public static void main(String args[]) {
        port(getPort());
        get("/Datos", (req, res) -> inputDataPage(req, res) );
        get("/Resultados", (req, res) -> resultsPage(req, res));

    }

    
    private static String inputDataPage(Request req, Response res) {
        String page
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body style=\"background-color:#F8F9C1;\">" 
    			+"<font align=\"center\" color=\"Olive\" face=\"Comic Sans MS,arial\">"
                + "<h2>Registro de cadenas del laboratorio de Docker y AWS</h2>"
                + "<form action=\"/Resultados\">"
                + "  Ingrese la cadena <br>"
                + "  <input type=\"text\" name=\"cadena\" >"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return page;
    }
    
    
    private static JSONObject resultsPage(Request req, Response res) {        
        String param = req.queryParams("cadena");        
        ConnectionSpark connection = new ConnectionSpark();        
        connection.addData(param);        
        res.header("Content-Type","application/json");        
        return connection.getAllData();         
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 34999;
    }
}