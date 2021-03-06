package com.services.gps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;


public class SerialCommGps implements SerialPortEventListener {

	SerialPort serialPort;
    /** The port we're normally going to use. */
	
	// Oggetto a cui si passano i dati acquisiti dal GPS... 
	public static GPSDataObject dataObject = new GPSDataObject();
	
	private static final String PORT_NAMES[] = 
		{
//		"/dev/tty.usbserial-A9007UX1", // Mac OS X
//        "/dev/ttyUSB0", // Linux
        "COM3" // Windows
		};

	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;

	
// INIZIALIZZAZIONE =======================================================================================
	public void initialize() {
    
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                break;
            }
        }
    }
    if (portId == null) {
        System.out.println("Could not find COM port.");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
        serialPort.setSerialPortParams(
        		DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        output = serialPort.getOutputStream();
        // Prova a scrivere sul dispositivo...
        
        
        
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


// CHIUSURA ======================================================================================================
	
	public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    	}
	}


// SERIAL EVENT ==================================================================================================

public synchronized void serialEvent(SerialPortEvent oEvent) {
	 if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
	    try {
	        String inputLine=null;
	        if (input.ready()) {
	            inputLine = input.readLine();
	            // Import con calcoli...
	            
	          // Esempio di filtro sulle coordinate...
	          if(inputLine.contains("$GPGGA")){  
	        	  System.out.println(inputLine);
	              dataObject.setData(inputLine);
	              System.out.println("\nMEMORIA MASSIMA DISPONIBILE: "+(Runtime.getRuntime().totalMemory())/1000000 + " MegaByte");
	      		  System.out.println("MEMORIA UTILIZZATA: "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + " MegaByte\n");
	          }
	        }
	    } catch (Exception e) {
	        System.err.println(e.toString());
	    }
	 }
	// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
//public synchronized void serialEvent(SerialPortEvent oEvent) {
//	 if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//	    try {
//	        String inputLine=null;
//	        if (input.ready()) {
//	            inputLine = input.readLine();
//	            // Import con calcoli...
//	            
//	          // Esempio di filtro sulle coordinate...
//	          if(inputLine.contains("$GPGGA")){  
//	           
//	         while(true) {
//	        	  // Esempio di acquisizione di 10 dati ogni 5 secondi...
//	        	 // Cambiare ovviamente il numero di acquisizioni in i<1...
//	        	  for(int i=0; i<1;i++) {
//	        	  System.out.println(inputLine);
//	              dataObject.setData(inputLine);
//	        	  };
//	           
//	        	  break;
//	         }
//	        
////	          System.out.println(inputLine);
//	       }
//	          
//	      }
//
//	    } catch (Exception e) {
//	        System.err.println(e.toString());
//	    }
//	 }
//	// Ignore all the other eventTypes, but you should consider the other ones.
//	}


//public static void main(String[] args) throws Exception {
//    
//	System.setProperty("serial.lib", "C:\\Users\\Utente\\Desktop\\mfz-rxtx-2.2-20081207-win-x64");
//	
//	SerialTest main = new SerialTest();
//    main.initialize();
//
//    Thread t = new Thread() {
//        public void run() {
//            //the following line will keep this app alive for 1000    seconds,
//            //waiting for events to occur and responding to them    (printing incoming messages to console).
//            try {
//            	Thread.sleep(1000000);
//            	}
//            catch (InterruptedException ie){}
//        }
//    };
//    
//    t.start();
//    System.out.println("Started: lettura dati dal gps\n");
//	
//}
}
