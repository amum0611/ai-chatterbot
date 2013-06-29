package edu.azeem.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import edu.azeem.answerGeneration.FilterCandidate;

public class FilterCandidateTest {

	@Test
	public void testFilterCandidate() {
		new FilterCandidate();
	}

	@Test
	public void testFilter() {
		FilterCandidate.filter(new HashMap<String, Integer>(), new ArrayList<String>());
	}

	@Test
	public void testIsStopWord() {
		assertNotNull(FilterCandidate.isStopWord("TEST"));
	}

	@Test
	public void testRemoevStopWords() {
		FilterCandidate.remoevStopWords("TEST");
	}

}
