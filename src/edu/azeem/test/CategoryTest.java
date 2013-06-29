//:edu.azeem.test/CategoryTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.azeem.tokenization.Category;
import edu.azeem.tokenization.Punctuations;

public class CategoryTest {
	
	@Test
	public void testCategorize() {
		for(int i = 0; i < Punctuations.values().length; i++){
			assertNotNull(Category.categorize(Punctuations.values()[i]));
		}	
	}
}
///:~