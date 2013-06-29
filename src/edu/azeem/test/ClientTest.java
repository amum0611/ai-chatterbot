//:edu.azeem.test/ClientTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.client.Analysis;
import edu.azeem.client.Chatterbot;
import edu.azeem.client.Client;
import edu.azeem.server.Controller;
import edu.azeem.server.Server;

public class ClientTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";

	@Before
	public void beforeTest(){
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testClient() {
		new Client(SERVER_IP) {			
			@Override
			protected void buildGui(String title) {
				Chatterbot.construct(TITLE, SERVER_IP, HOST); 
			}
		};
	}

	@Test
	public void testHandleRequest() {
		String testQuestion = "Who is the father modern computer science?";
		final Server SERVER = new Controller(HOST);				
		new Thread(new Runnable(){
			@Override
			public void run() {
				SERVER.acceptRequest();
			}
		}).start();
		Chatterbot.handleRequest(testQuestion, HOST);
	}

}
///:~