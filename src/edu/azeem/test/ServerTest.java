package edu.azeem.test;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.server.Server;

public class ServerTest {

	private static final int HOST = 6789;						//Host ID 
	
	private static Server _server;
	
	@BeforeClass
	public static void beforeClass(){
		_server = new Server(HOST);
	}
	
	@Test
	public void testServer() {
		new Server(HOST);
	}

	@Test
	public void testAcceptRequest() {
		_server.acceptRequest();
	}

}
