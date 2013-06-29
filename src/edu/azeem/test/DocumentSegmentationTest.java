//:edu.azeem.test/AnalysisTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.answerGeneration.DocumentSegmentation;

public class DocumentSegmentationTest {

	private static DocumentSegmentation _docSeg;
	
	@BeforeClass
	public static void beforeClass(){
		_docSeg = new DocumentSegmentation();
	}
	
	@Test
	public void testDocumentSegmentation() {
		new DocumentSegmentation();
	}

	@Test
	public void testRetrievePassage() {
		_docSeg.retrievePassage();
	}

}
