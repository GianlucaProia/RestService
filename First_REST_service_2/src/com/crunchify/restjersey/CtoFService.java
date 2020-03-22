package com.crunchify.restjersey;
 
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

/**
 * @author Crunchify.com
 * * Description: This program converts unit from Centigrade to Fahrenheit.
 * Last updated: 12/28/2018
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crunchify.restjersey.MyClass;
import com.services.gps.GPSDataObject;
import com.services.gps.SerialCommGps;
import com.services.webcam.WebCamService;
 
@Path("/ctofservice")
public class CtoFService {
	
	// Funziona!!! IL valore della variabile a cui si vuole accedere deve essere static!!!
	MyClass myClass = new MyClass();
	Thread t1 = new Thread(myClass);
	SerialCommGps gpsSerial = new SerialCommGps();
	
	// Cosi va bene ma parte solo quando si chiama il servizio da web...
	public CtoFService() {
		// run();
		
	}
	
	@GET
	@Produces("application/xml")
	public String convertCtoF() {
 
		Double fahrenheit;
		Double celsius = 36.8;
		fahrenheit = ((celsius * 9) / 5) + 32;
 
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
	
	@Path("{c}")
	@GET
	@Produces("application/xml")
	public String convertCtoFfromInput(@PathParam("c") Double c) {
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = ((celsius * 9) / 5) + 32;
 
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
	
	
	// Si può fare un metodo che mette a start il thread
	@Path("/start")
	@GET
	@Produces("application/xml")
	public Response startThread(@PathParam("start") String start) {
	    // La lettura della porta conviene avviarla in un metodo separato...
		gpsSerial.initialize();
		myClass.stop = 0;
		runThread();
		String output = "<startTask> Task avviato </startTask>";
		return Response.status(200).type(MediaType.TEXT_XML).entity(output).build();
	}
	
	
	@Path("/stop")
	@GET
	@Produces("application/xml")
	public String stopThread(@PathParam("stop") String stop) {
		gpsSerial.close();
		myClass.stop = 1;
		return "<ctofservice> Task fermato </ctofservice> ";
	}
	
	
	@Path("/getA")
	@GET
	@Produces("application/xml")
	public Response getAvalue(@PathParam("start") String start) {
		// Riprendere il valore di a in modo statico...
		// Usare Response per l'output...
		String output = "<ctofservice> Il valore di a è:" + MyClass.a + "</ctofservice>";
		return Response.status(200).type(MediaType.TEXT_XML).entity(output).build();
	}
	
	
	@Path("/getImageWebCam")
	@GET
	@Produces("application/xml")
	public String getWebCamImage() {
		
		BufferedImage image = WebCamService.getImageFromWebcam();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", bos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] imageBytes = bos.toByteArray();
		String stringImage = new String(Base64.getEncoder().encode(imageBytes));
		return "<image>"+stringImage+"</image>";
	}
	
	
	@Path("/getCoordinateGPS")
	@GET
	@Produces("application/xml")
	public String getCoordinateGPS() {
		// Deve essere accessibile in modo statico...
		String line = SerialCommGps.dataObject.getData();
		return "<coordinate>"+line+"</coordinate>";
	}
	
	

	// Metodo run() per attivare il thread...
	public void runThread() {
		// TODO Auto-generated method stub
		this.t1.start();
	}


}