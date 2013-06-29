//:edu.azeem.test/NGramsTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.answerGeneration.NGrams;
import edu.azeem.answerGeneration.QATokens;

public class NGramsTest {

	private static final String SENTENCE = "Who is the father of modern computer science?";
	
	private static NGrams _nGrams;
	
	@BeforeClass
	public static void beforeClass(){
		_nGrams = new NGrams(QATokens.PERSON);
	}
	
	@Test
	public void testNGrams() {
		new NGrams(QATokens.OTHER);
	}

	@Test
	public void testGetNGrams() {
		assertNotNull(_nGrams.getNGrams(SENTENCE));
	}

}
