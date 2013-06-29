//:edu.azeem.client/Simulator.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class instantiates the Server and Client, and holds the main thread
 */
package edu.azeem.client;

import edu.azeem.server.Controller;
import edu.azeem.server.Server;

public class Simulator {
	
	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	public static void main(String[] args){
		
		final Server SERVER = new Controller(HOST);				
		new Thread(new Runnable(){
			@Override
			public void run() {
				SERVER.acceptRequest();
			}
		}).start();
		
		Chatterbot.construct(TITLE, SERVER_IP, HOST); 		//Parameter: Title, Server IP, and Host ID
		
	}	
}
///:~