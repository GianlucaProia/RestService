package com.test;

import com.services.gps.SerialCommGps;

public class SerialTest {
	
	
	public static void main(String[] args) {
		
		SerialCommGps gps = new SerialCommGps();
		gps.initialize();
		
		System.out.println(SerialCommGps.dataObject.getData());
//		System.out.println("MEMORIA MASSIAMD ISPONIBILE: "+(Runtime.getRuntime().totalMemory())/1000000 + "MegaByte");
//		System.out.println("MEMORIA UTILIZZATA: "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + "MegaByte");
	}

}
