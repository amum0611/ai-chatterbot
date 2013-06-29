//:edu.azeem.test/QueryGeneratorTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.azeem.retrieval.QueryGenerator;

public class QueryGeneratorTest {

	@Test
	public void testQueryGenerator() {
		new QueryGenerator();
	}

	@Test
	public void testGetQuery() {
		assertNotNull(QueryGenerator.getQuery(new String[][]{}));
	}

	@Test
	public void testGetQueryStartGoogle() {
		assertNotNull(QueryGenerator.getQueryStartGoogle());
	}

	@Test
	public void testGetKeywords() {
		assertNotNull(QueryGenerator.getKeywords());
	}
}
///:~
