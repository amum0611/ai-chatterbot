//:edu.azeem.test/AbbreviationsTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.azeem.tokenization.Abbreviations;

public class AbbreviationsTest {

	@Test
	public void testAbbreviates() {
		for(int i = 0; i < Abbreviations.values().length; i++){
			assertNotNull(Abbreviations.abbreviates(Abbreviations.values()[i]));
		}		
	}
}
///:~