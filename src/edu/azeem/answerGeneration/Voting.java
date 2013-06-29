//:edu.azeem.answerGeneration/Voting.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class will give a vote to each n-gram generated from NGrams class depends on
 * it's Occurrence. To eliminate zero probabilities each n-gram is given a initial score
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.HashMap;

public class Voting {
	
	private final static int INITIAL_SCORE = 0;
	
	public Voting(){}
	
	/**
	 * vote each n-grams
	 * @param listOfNGrams
	 * @return
	 */
	public static HashMap<String, Integer> vote(ArrayList<String> listOfNGrams){
		String key;
		int score;
		HashMap<String, Integer> voteBallot = new HashMap<String, Integer>();
		for(int i = 0; i < listOfNGrams.size(); i++){
			key = listOfNGrams.get(i);
			if(!voteBallot.containsKey(key)){
				voteBallot.put(key, INITIAL_SCORE);
			}
			score = voteBallot.get(key);
			score += 1;
			voteBallot.put(key, score);
		}
		return voteBallot;
	}
	
}
///:~