//:edu.azeem.test/CombineCandidateTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import edu.azeem.answerGeneration.CombineCandidate;

public class CombineCandidateTest {

	@Test
	public void testCombineCandidate() {
		new CombineCandidate();
	}

	@Test
	public void testCombine() {
		assertNotNull(CombineCandidate.combine(new HashMap<String,Integer>()));
	}
}
///:~
