//:edu.azeem.test/VotingTest.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Implements Unit Testing Strategy
 */
package edu.azeem.test;

import java.util.ArrayList;

import org.junit.Test;

import edu.azeem.answerGeneration.Voting;

public class VotingTest {

	@Test
	public void testVoting() {
		new Voting();
	}

	@Test
	public void testVote() {
		Voting.vote(new ArrayList<String>());
	}

}
