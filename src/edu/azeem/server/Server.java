//:org.azeem.server/Server.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds codes that provides the ServerSocket programming capability to the child classes
 */
package edu.azeem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	
	protected ServerSocket _serverSocket;
	protected volatile Socket _socket;
	
	/**
	 * Initialise the ServerSocket with the specified Host ID
	 * @param host Host ID
	 */
	public Server(int host){
		try {
			_serverSocket = new ServerSocket(host);
		} catch (IOException e) {
			System.err.println("Could not instantiate serverSocket");
		}
	}
	
	
	/**
	 * This class listen through ServerSocket in order to accept communication with the client
	 */
	public void acceptRequest() {
		//_filePaths = FilePaths.getUniqueInstance();
		while(true){
			try {
				synchronized(this){
					_socket = _serverSocket.accept(); 			//Listen to the client's socket
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					handleRequest();
				}
			}).start();
		}
	}
	
	/**
	 * Let the child classes implement how the request from the server should be handled. 
	 * This provides a default implementation
	 */
	public void handleRequest(){						
		throw new AbstractMethodError("No subclasses of Server implements handleRequest method");
	}

	public void readCorpus(){
		throw new AbstractMethodError("No subclasses of Server implements handleRequest method");
	}
}
///:~