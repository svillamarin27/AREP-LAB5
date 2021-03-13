package edu.eci.arep.dockerapp;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.util.ArrayList;

import java.util.Date;
import org.json.JSONObject;

public class ConnectionSpark {
	
	public ConnectionSpark() {}
	
	public void addData(String param) {
		
        ArrayList<DataSpark> dataSpa = new ArrayList<DataSpark>();
        dataSpa.add(new DataSpark(param, new Date()));
        try {
            MongoClient mc = new MongoClient(new MongoClientURI("mongodb://ec2-54-236-9-109.compute-1.amazonaws.com:27017"));
            DB db = mc.getDB("Datas");
            DBCollection collection = db.getCollection("Data");            
            for (DataSpark dat : dataSpa) {
                collection.insert(dat.toDBObjectData());
            }
            mc.close();
        } catch (Exception ex) {
            System.out.println("Exception Mongo: " + ex.getMessage());
        }
    }
	
    public JSONObject getAllData() {
        
        JSONObject myob = new JSONObject();
        try {
            MongoClient mc = new MongoClient(new MongoClientURI("mongodb://ec2-54-236-9-109.compute-1.amazonaws.com:34000"));          
            DB db = mc.getDB("Datas");
            DBCollection dbcol = db.getCollection("Data");
            DBCursor dbcur = dbcol.find();            
            ArrayList<String> lista = new ArrayList<String>();            
            try {
                while (dbcur.hasNext()) {            
                    lista.add(dbcur.next().toString());
                }
            } finally {
            	dbcur.close();
            }            
            myob = lastElements(lista);            
            mc.close();            
            return myob;
        } catch (Exception ex) {
            System.out.println("Exception :" + ex.getMessage());
        }
        return myob;
    }    
    
    public JSONObject lastElements(ArrayList jsonlista){
    	
        int cont = 0;
        JSONObject jsonobjeto = new JSONObject();
        for (int i=jsonlista.size()-1 ; i>=0 && cont<=9 ; i--){
            jsonobjeto.append("Resultado", jsonlista.get(i));
            cont+=1;
        }
        return jsonobjeto;
    }
	

}
