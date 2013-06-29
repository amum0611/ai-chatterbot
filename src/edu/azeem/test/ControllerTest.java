//:edu.azeem.test/ControllerTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.azeem.server.Controller;

public class ControllerTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	private static Controller _server;
	
	@BeforeClass
	public static void beforeClass(){
		_server = new Controller(HOST);
	}
	
	@Test
	public void testHandleRequest() {
		_server.handleRequest();
	}

	@Ignore
	public void testReadCorpus() {
		_server.readCorpus();
	}

	@Test
	public void testController() {
		new Controller(HOST);
	}

	@Test
	public void testTest() {
		_server.test(TITLE);
	}

	@Test
	public void testAcceptRequest() {
		_server.acceptRequest();
	}
}
///:~
