//:edu.azeem/CorpusNormalizerTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.client.CorpusNormalizer;

public class CorpusNormalizerTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";

	private CorpusNormalizer _courpus;
	
	@Before
	public void beforeTest(){
		_courpus = CorpusNormalizer.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testConstructStringStringInt() {
		CorpusNormalizer.construct(TITLE, SERVER_IP, HOST);
	}

	@Test
	public void testConstruct() {
		CorpusNormalizer.construct();
	}

	@Test
	public void testShow() {
		_courpus.show();
	}

	@Test
	public void testSetDone() {
		_courpus.setDone(true);
	}

	@Test
	public void testIsDone() {
		boolean flag = _courpus.isDone();
		assertTrue(flag);
	}

	@Test
	public void testSetProgressInfo() {
		_courpus.setProgressInfo(SERVER_IP);
	}
}
///:~