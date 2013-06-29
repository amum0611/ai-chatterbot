//:edu.azeem.test/BrownCorpusReaderTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.posTagging.BrownCorpusReader;
import edu.azeem.posTagging.Serializer;

public class BrownCorpusReaderTest {

	private static BrownCorpusReader _brownReader;
	
	@BeforeClass
	public static void beforeTest(){
		_brownReader = new BrownCorpusReader();
		Serializer.getUniqueInstance();
	}

	@Test
	public void testBrownCorpusReader() {
		new BrownCorpusReaderTest();
	}

	@Test
	public void testRead() {
		_brownReader.read(false);
	}

	@Test
	public void testSerializeProbabilities() {
		_brownReader.serializeProbabilities();
		_brownReader.saveProbabilities();
	}

}
///:~
