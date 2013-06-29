//:edu.azeem.test/VectorSpaceTest.java
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

import edu.azeem.answerGeneration.VectorSpace;
import edu.azeem.client.Analysis;

public class VectorSpaceTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	private static VectorSpace _vector;
	
	@BeforeClass
	public static void beforeClass(){
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("father");
		keywords.add("modern");
		keywords.add("computer");
		keywords.add("science");
		_vector = new VectorSpace(keywords);
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testHandle() {
		_vector.handle();
	}

	@Test
	public void testPrint() {
		_vector.print();
	}

	@Test
	public void testOutput() {
		assertNotNull(_vector.output());
	}

	@Test
	public void testGet() {
		assertNotNull(_vector.get());
	}

	@Test
	public void testVectorSpace() {
		new VectorSpace(new ArrayList<String>());
	}
}
///:~
