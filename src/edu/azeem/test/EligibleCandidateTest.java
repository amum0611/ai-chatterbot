//:edu.azeem.test/EligibleCandidateTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.answerGeneration.EligibleCandidate;
import edu.azeem.answerGeneration.QATokens;

public class EligibleCandidateTest {

	private static EligibleCandidate _ele;
	private static final String KEY = "key/key";
	private static final Integer VALUE = 0;
	
	@BeforeClass
	public static void BeforeClass(){
		_ele = new EligibleCandidate(QATokens.OTHER);
	}
	
	@Test
	public void testEligibleCandidate() {
		new EligibleCandidate(QATokens.OTHER);
	}

	@Test
	public void testEligibles() {
		_ele.eligibles(new HashMap<String, Integer>());
	}

	@Test
	public void testPrintIntegerArrayArray() {
		_ele.print(new Integer[][]{});
	}

	@Test
	public void testPrintStringInteger() {
		_ele.print(KEY, VALUE);
	}

	@Test
	public void testToTitleCase() {
		EligibleCandidate.toTitleCase(KEY);
	}
}
///:~
