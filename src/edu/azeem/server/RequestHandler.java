//:org.azeem.server/RequestHandler.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class provides the interface to implement the chain of responsibility pattern
 */
package edu.azeem.server;

public interface RequestHandler {
	
	public abstract void handle();			//Handles the human request
	public abstract void print();			//Prints the results of the component
	public abstract Object output();		//sends the results of the component to the Server
	public abstract String get();			//Get the output of the RequestHandler in String format
}
///:~
