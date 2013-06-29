//:edu.azeem.test/AnalysisTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.answerGeneration.QATokens;
import edu.azeem.client.Analysis;

public class AnalysisTest{

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	private static final String TEST_SENTENCE = "THIS IS FOR TESTING PURPOSE";
	
	private Analysis _analysis;
	
	@Before
	public void triggerBeforeEachTest(){
		_analysis = Analysis.getUniqueInstance();
	}
	
	@Test
	public void testConstruct() {
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}

	@Test
	public void testGetUniqueInstance() {
		assertNotNull(_analysis);
	}

	@Test
	public void testShow() {
		_analysis.show();
	}

	@Test
	public void testSetQestion() {
		_analysis.setQestion(TEST_SENTENCE);
	}

	@Test
	public void testSetTokenization() {
		_analysis.setTokenization(TEST_SENTENCE);
	}

	@Test
	public void testSetPos() {
		_analysis.setPos(TEST_SENTENCE);
	}

	@Test
	public void testSetQuery() {
		_analysis.setQuery(TEST_SENTENCE);
	}

	@Test
	public void testSetAnswer() {
		_analysis.setAnswer(TEST_SENTENCE);
	}

	@Test
	public void testSetTableStringArrayArray() {
		_analysis.setTable(new String[][]{});
	}

	@Test
	public void testSetTableStringString() {
		_analysis.setTable(TEST_SENTENCE, TEST_SENTENCE);
	}

	@Test
	public void testClear() {
		_analysis.clear();
	}

	@Test
	public void testSetType() {
		_analysis.setType(QATokens.OTHER);
	}

	@Test
	public void testGetType() {
		_analysis.getType();
	}
}
///:~