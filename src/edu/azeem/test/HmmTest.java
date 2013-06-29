//:edu.azeem.test/HmmTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.client.Analysis;
import edu.azeem.posTagging.Hmm;
import edu.azeem.tokenization.Tokenizer;

public class HmmTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	private static final String TEST_SENTENCE = "Who is the father of modern computer science?";

	private static Hmm _hmm;
	private static Tokenizer _tokenizer;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void beforeTest(){
		Analysis.construct(TITLE, SERVER_IP, HOST);
		_tokenizer = new Tokenizer(TEST_SENTENCE);
		_tokenizer.handle();
		_hmm = new Hmm((ArrayList<String>) _tokenizer.output());
	}
	
	@Test
	public void testHandle() {
		_hmm.handle();
	}

	@Test
	public void testPrint() {
		_hmm.print();
	}

	@Test
	public void testOutput() {
		assertNotNull(_hmm.output());
	}

	@Test
	public void testGet() {
		assertNotNull(_hmm.get());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHmm() {
		Tokenizer t = new Tokenizer(TEST_SENTENCE);
		t.handle();
		new Hmm((ArrayList<String>) t.output());
	}
}
///:~
