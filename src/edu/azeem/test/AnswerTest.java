//:edu.azeem.test/AnswerTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.answerGeneration.Answer;

public class AnswerTest {

	private static final String ANSWER = "answer";
	private static final Double WEIGHT = 2.0;
	private static final int S_ID = 0;
	private static final int D_ID = 0;
	
	private static Answer _answer;
	
	@BeforeClass
	public static void beforeClass(){
		_answer = new Answer(ANSWER, WEIGHT, S_ID, D_ID);
	}
	
	@Test
	public void testAnswer() {
		new Answer(ANSWER, WEIGHT, S_ID, D_ID);
	}

	@Test
	public void testSetAnswer() {
		_answer.setAnswer(ANSWER);
	}

	@Test
	public void testGetAnswer() {
		assertNotNull(_answer.getAnswer());
	}

	@Test
	public void testSetWeight() {
		_answer.setWeight(WEIGHT);
	}

	@Test
	public void testGetWeight() {
		assertNotNull(_answer.getWeight());
	}

	@Test
	public void testSetSentenceID() {
		_answer.setSentenceID(S_ID);
	}

	@Test
	public void testGetSentenceID() {
		assertNotNull(_answer.getSentenceID());
	}

	@Test
	public void testSetDocumentID() {
		_answer.setSentenceID(D_ID);
	}

	@Test
	public void testGetDocumentID() {
		assertNotNull(_answer.getDocumentID());
	}

	@Test
	public void testCompare() {
		assertNotNull(_answer.compare(new Answer(ANSWER, WEIGHT, S_ID, D_ID), new Answer(ANSWER, WEIGHT*2, S_ID, D_ID)));
	}
}
///:~
