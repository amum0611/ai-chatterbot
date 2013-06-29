//:edu.azeem.test/RetrieveTest.java
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
import edu.azeem.retrieval.Retrieve;
import edu.azeem.tokenization.Tokenizer;

public class RetrieveTest {
	
	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	private static final String TEST_SENTENCE = "Who is the father of modern computer science?";

	private static Tokenizer _tokenizer;
	private static Hmm _hmm;
	private static Retrieve _retrieve;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void beforeClass(){
		Analysis.construct(TITLE, SERVER_IP, HOST);
		_tokenizer = new Tokenizer(TEST_SENTENCE);
		_tokenizer.handle();
		_hmm = new Hmm((ArrayList<String>) _tokenizer.output());
		_hmm.handle();
		_retrieve = new Retrieve((ArrayList<String>) _tokenizer.output(), (String[][]) _hmm.output());
	}
	
	@Test
	public void testHandle() {
		_retrieve.handle();
	}

	@Test
	public void testPrint() {
		_retrieve.print();
	}

	@Test
	public void testOutput() {
		assertNotNull(_retrieve.output());
	}

	@Test
	public void testGet() {
		assertNull(_retrieve.get());
	}
}
///:~
