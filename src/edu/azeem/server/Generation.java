//:org.azeem.server/Generation.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class implements the interface to provide surrogate to the Natural Language 
 * Understanding Components
 */
package edu.azeem.server;

public abstract class Generation implements RequestHandler{

	@Override
	public abstract void handle();

	@Override
	public abstract void print();

	@Override
	public abstract Object output();
	
	@Override
	public abstract String get();
}
///:~