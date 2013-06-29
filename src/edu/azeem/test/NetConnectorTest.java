//:edu.azeem.test/NetConnectorTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.retrieval.NetConnector;
import edu.azeem.retrieval.QueryGenerator;

public class NetConnectorTest {

	private static final String[][] KEYS = {{"father", "NN"}, {"modern", "NN"}, {"computer", "NN"}, {"science", "NN"}};
	private static NetConnector _netConnector;
	
	@BeforeClass
	public static void beforeClass(){
		_netConnector = new NetConnector(QueryGenerator.getQuery(KEYS));
	}
	
	@Test
	public void testNetConnectorURL() throws MalformedURLException {
		new NetConnector(QueryGenerator.getQuery(KEYS));
	}

	@Test
	public void testNetConnector() {
		new NetConnector();
	}

	@Test
	public void testGetContentTrue() {
		_netConnector.getContent(true);
	}

	@Test
	public void testGetContentFalse() {
		_netConnector.getContent(false);
	}

	@Test
	public void testCleanUpContents() {
		_netConnector.cleanUpContents();
	}

	@Test
	public void testCleanUpData() {
		_netConnector.cleanUpData();
	}

	@Test
	public void testCleanUpSearch() {
		_netConnector.cleanUpSearch();
	}

	@Test
	public void testCleanUpSeg() {
		_netConnector.cleanUpSeg();
	}

	@Test
	public void testGetResponseCode() {
		assertNotNull(_netConnector.getResponseCode());
	}

	@Test
	public void testGetContentEncoding() {
		assertNotNull(_netConnector.getContentEncoding());
	}

	@Test
	public void testGetTitle() {
		assertNotNull(_netConnector.getTitle());
	}

	@Test
	public void testGetHeaderMap() {
		assertNotNull(_netConnector.getHeaderMap());
	}

	@Test
	public void testGetResponseUrl() {
		assertNotNull(_netConnector.getResponseUrl());
	}
}
///:~
