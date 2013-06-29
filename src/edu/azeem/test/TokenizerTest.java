//:edu.azeem.test/TokenizerTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.client.Analysis;
import edu.azeem.tokenization.Tokenizer;

public class TokenizerTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	private static final String TEST_SENTENCE = "Who is the father of modern computer science?";
	
	private Tokenizer _tokenizer;
	
	@Before
	public void beforeTest(){
		_tokenizer = new Tokenizer(TEST_SENTENCE);
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testHandle() {
		_tokenizer.handle();
	}

	@Test
	public void testPrint() {
		_tokenizer.print();
	}

	@Test
	public void testOutput() {
		assertNotNull(_tokenizer.output());
	}

	@Test
	public void testGet() {
		assertNotNull(_tokenizer.get());
	}

	@Test
	public void testTokenizer() {
		new Tokenizer(TEST_SENTENCE);
	}
}
///:~
