//:edu.azeem.test/ChatterbotTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import junit.framework.TestCase;
import org.junit.Test;
import edu.azeem.client.Chatterbot;

public class ChatterbotTest extends TestCase{

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";

	@Test
	public void testConstruct() {		
		Chatterbot.construct(TITLE, SERVER_IP, HOST);
	}
}
///:~