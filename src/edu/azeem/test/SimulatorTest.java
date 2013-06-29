//:edu.azeem.test/SimulatorTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.Test;

import edu.azeem.client.Chatterbot;
import edu.azeem.server.Controller;
import edu.azeem.server.Server;

public class SimulatorTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	@Test
	public void testMain() {
		final Server SERVER = new Controller(HOST);				
		new Thread(new Runnable(){
			@Override
			public void run() {
				SERVER.acceptRequest();
			}
		}).start();
		
		Chatterbot.construct(TITLE, SERVER_IP, HOST);
	}

}
///:~