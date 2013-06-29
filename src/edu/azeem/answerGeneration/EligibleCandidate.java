//:edu.azeem.answerGeneration/EligibleCandidate.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Filters top candidates
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.HashMap;

import edu.azeem.client.Analysis;

public class EligibleCandidate {

	private ArrayList<String> _keySet;
	private ArrayList<Integer> _valueSet;
	private ArrayList<String> _answers;
	private QATokens _qaToken;
	
	private final int CANDIDATE_COUNT = 10;
	private final String SPACE = " ";
	
	/**
	 * Public constructor
	 * @param qaToken
	 */
	public EligibleCandidate(QATokens qaToken){
		_qaToken = qaToken;
	}
	
	/**
	 * This method is responsible to get the top answers that are eligible candidates
	 * @param candidates
	 * @return
	 */
	public ArrayList<String> eligibles(HashMap<String, Integer> candidates){
		int count = 0;
		_answers = new ArrayList<String>();
		_keySet = new ArrayList<String>(candidates.keySet());
		_valueSet = new ArrayList<Integer>(candidates.values());
		Integer[][] sortedArray = new Integer[candidates.size()][2];
		
		for(int i = 0; i < sortedArray.length; i++){
			sortedArray[i][0] = _valueSet.get(i);
			sortedArray[i][1] = i;
		}
		sortedArray = sort(sortedArray);
		System.out.println("Top 10 Answers: ");
		for(int i = 0; i < sortedArray.length; i++){
			String key = _keySet.get(sortedArray[i][1]);
			Integer value = _valueSet.get(sortedArray[i][1]);			
			
			if(_qaToken == QATokens.TIME){
				if(key.split("/").length == 1){
					_answers.add(key.replaceAll("[ ]", ""));
					print(key, value);
					count++;
				}
			}
			else if(_qaToken != QATokens.TIME){
				if(key.split("/").length > 1){
					_answers.add(key);
					print(key, value);
					count++;
				}
			}
			
			//XXX System.out.println(sortedArray[i][0] + "\t" + _keySet.get(sortedArray[i][1]));
			if(count == CANDIDATE_COUNT)
				break;
		}
		return _answers;
	}
	
	/**
	 * this is used to sort Hash Map
	 * @param sortedArray
	 * @return
	 */
	private Integer[][] sort(Integer[][] sortedArray){
		for(int i = 1; i < sortedArray.length; i++){
			for(int j = 0; j < sortedArray.length - i; j++){
				if(sortedArray[j][0] < sortedArray[j+1][0]){
					Integer temp = sortedArray[j][0];
					Integer count = sortedArray[j][1];
					sortedArray[j][0] = sortedArray[j+1][0];
					sortedArray[j][1] = sortedArray[j+1][1];
					sortedArray[j+1][0] = temp;
					sortedArray[j+1][1] = count;
				}
			}
		}
		return sortedArray;
	}

	/**
	 * Prints the array
	 * @param sortedArray
	 */
	public void print(Integer[][] sortedArray){
		for(int i = 0; i < sortedArray.length; i++){
			System.out.println(sortedArray[i][0] + "\t" + sortedArray[i][1]);
		}
	}
	
	/**
	 * Print the answers along with its score on the console
	 * @param key
	 * @param value
	 */
	public void print(String key, Integer value){
		StringBuffer answer = new StringBuffer();
		String[] tokens = key.split("/");
		
		for(int i = 0; i < tokens.length; i++){
			answer.append(toTitleCase(tokens[i]));
			answer.append(SPACE);
		}
		Analysis.getUniqueInstance().setTable(value.toString(), answer.toString());
		System.out.println(answer.toString() + "(" + value + ")");
	}
	
	/**
	 * converts a sentence to the title case
	 * @param token
	 * @return
	 */
	public static String toTitleCase(String token){
		StringBuffer sb = new StringBuffer();
		if(token.length() > 1){
			sb.append(token.substring(0,1).toUpperCase());
			sb.append(token.substring(1).toLowerCase());
			return sb.toString();
		}
		return token;
	}
}
///:~