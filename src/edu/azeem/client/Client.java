//:org.azeem.client/Client.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds codes that provides the socket programming capability to the child classes
 */
package edu.azeem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public abstract class Client{
	
	private static String _serverIP = "localhost";				//Server IP address. Default is localhost
	private static Socket _socketHandleRequest;
	
	private static ObjectInputStream _inFromServer;				//Deserializes primitive data and objects previously written using an ObjectOutputStream (Java API 1.6)
	private static ObjectOutputStream  _outToServer;			//Writes primitive data types and graphs of Java objects to an OutputStream (Java API 1.6)

	public Client(String serverIP){
		Client._serverIP = serverIP;
	}
	
	public static String handleRequest(String text, int host){

		String reply = null;
		
		try {
			
			_socketHandleRequest = new Socket(_serverIP, host);
			_outToServer = new ObjectOutputStream(_socketHandleRequest.getOutputStream());
			_inFromServer = new ObjectInputStream(_socketHandleRequest.getInputStream());	        
			_outToServer.writeObject(text);
			reply = (String) _inFromServer.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try{
			_inFromServer.close();
			_outToServer.close();
			_socketHandleRequest.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return reply;					//Return the Chatterbot's reply to the front end
	}

	protected abstract void buildGui(String title);
}
///:~