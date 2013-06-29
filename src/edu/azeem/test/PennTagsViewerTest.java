//:edu.azeem.test.client/PennTagsViewerTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import org.junit.Before;
import org.junit.Test;

import edu.azeem.client.PennTagsViewer;

public class PennTagsViewerTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	private PennTagsViewer _penn;
	
	@Before
	public void beforeTest(){
		_penn = PennTagsViewer.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testConstructStringStringInt() {
		PennTagsViewer.construct(TITLE, SERVER_IP, HOST);
	}

	@Test
	public void testConstruct() {
		PennTagsViewer.construct();
	}

	@Test
	public void testShow() {
		_penn.show();
	}
}
///:~
