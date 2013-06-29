//:edu.azeem.test/EarleyParserTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.parsing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.client.Analysis;
import edu.azeem.posTagging.Hmm;
import edu.azeem.tokenization.Tokenizer;

public class EarleyParserTest {
	
	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	private static final String TEST_SENTENCE = "Who is the father of modern computer science?";
	
	private EarleyParser _earley;
	
	@SuppressWarnings("unchecked")
	@Before
	public void beforeTest(){
		_earley = new EarleyParser((String[][]) new Hmm((ArrayList<String>) new Tokenizer(TEST_SENTENCE).output()).output());
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testHandle() {
		_earley.handle();
	}

	@Test
	public void testPrint() {
		_earley.print();
	}

	@Test
	public void testOutput() {
		assertNotNull(_earley.output());
	}

	@Test
	public void testGet() {
		assertNotNull(_earley.get());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEarleyParser() {
		new EarleyParser((String[][]) new Hmm((ArrayList<String>) new Tokenizer(TEST_SENTENCE).output()).output());
	}
}
///:~
