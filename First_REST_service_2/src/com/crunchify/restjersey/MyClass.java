package com.crunchify.restjersey;

public class MyClass implements Runnable {

	// Funziona!!! IL valore della variabile a cui si vuole accedere deve essere static!!!
	static int a = 0;
	// Funziona!!! la variabile deve essere volatile!!! 
	static volatile int stop = 0;
	
	public void run() {
		 while (stop == 0) {
		    	   this.a = a+1;
		    	   System.out.println("MyClass is running!!!\nIl valore di a è: "+a);
//		    	   System.out.println("\nMEMORIA MASSIMA DISPONIBILE: "+(Runtime.getRuntime().totalMemory())/1000000 + " MegaByte");
//		       	   System.out.println("MEMORIA UTILIZZATA: "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + " MegaByte\n");
		    	   try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       try {
				java.lang.Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	   }
	
}