//:edu.azeem.test/SearchResultExtractorTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.azeem.retrieval.SearchResultExtractor;

public class SearchResultExtractorTest {

	private static final String NAME = "1www.google.lk";
	
	@Test
	public void testSearchResultExtractor() {
		new SearchResultExtractor();
	}

	@Test
	public void testGrabUrls() throws IOException {
		assertNotNull(SearchResultExtractor.grabUrls(NAME));
	}

}
///:~