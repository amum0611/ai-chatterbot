//:org.azeem.tokenization/Command.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class provides the interface for an command object
 */
package edu.azeem.tokenization;

public interface Command {
	public void execute();
	public String value();
}
///:~