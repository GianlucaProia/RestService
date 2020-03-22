package com.services.webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;

public class WebCamService {

	public static BufferedImage getImageFromWebcam() {
		
		Webcam webcam = Webcam.getDefault();
		//webcam.setViewSize(WebcamResolution.VGA.getSize());
		// Set dimensioni...
		webcam.setViewSize(new Dimension(640,480));
		webcam.open();
		BufferedImage image = webcam.getImage();
		
		webcam.close();
		Webcam.getDiscoveryService().stop();
		
		System.out.println("\nMEMORIA MASSIMA DISPONIBILE: "+(Runtime.getRuntime().totalMemory())/1000000 + " MegaByte");
    	System.out.println("MEMORIA UTILIZZATA: "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + " MegaByte\n");
		
    	return image;
	}
	
	
}
