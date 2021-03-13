package edu.eci.arep.dockerapp;

import com.mongodb.BasicDBObject;
import java.util.Date;


public class DataSpark {
	
	private String param;
    private Date fecha;    
    public DataSpark() {}
	public DataSpark(String param, Date fecha) {
	this.param = param;
	this.fecha = fecha;
	}

    
	public DataSpark(BasicDBObject dBObject) {
		this.param = dBObject.getString("param");
		this.fecha = dBObject.getDate("fecha");
	}

	public BasicDBObject toDBObjectData() {	
		BasicDBObject dBObjectData = new BasicDBObject();
		dBObjectData.append("param", this.getparam());
		dBObjectData.append("fecha", this.getFecha());	
		return dBObjectData;
	}

	public String getparam() {
		return param;
	}
	
	public void setparam(String param) {
		this.param = param;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setEdad(Date fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		return "mensaje: " + this.getparam() + " fecha: " + this.getFecha();
	}
    

}
