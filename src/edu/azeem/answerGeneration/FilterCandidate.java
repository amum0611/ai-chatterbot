//:edu.azeem.answerGeneration/FilterCandidate.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class filters the candidate by stop words as well as 
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FilterCandidate {

	private final static String SPACE = " ";
	
	public FilterCandidate(){}
	
	/**
	 * the public method that is invoked to filter candidates from an HashMap
	 * @param voteBallot
	 * @param keywords
	 * @return
	 */
	public static HashMap<String, Integer> filter(HashMap<String, Integer> voteBallot, ArrayList<String> keywords){
		HashMap<String, Integer> filteredCandidates;
		filteredCandidates = filterStopWords(voteBallot);
		filteredCandidates = filterKeywords(filteredCandidates, keywords);
		return filteredCandidates;
	}
	
	/**
	 * this method is responsible to filter candidates by stopwords
	 * @param voteBallot
	 * @return
	 */
	private static HashMap<String, Integer> filterStopWords(HashMap<String, Integer> voteBallot){
		String key;
		ArrayList<String> keySet = new ArrayList<String>(voteBallot.keySet());
		for(int i = 0; i < keySet.size(); i++){
			key = keySet.get(i);
			if(isStopWord(key)){
				voteBallot.remove(key);
			}			
		}
		return voteBallot;
	}
	
	/**
	 * this method is responsible to filter candidates by keywords
	 * @param voteBallot
	 * @param keywords
	 * @return
	 */
	private static HashMap<String, Integer> filterKeywords(HashMap<String, Integer> voteBallot, ArrayList<String> keywords){
		String key;
		Matcher matcher;
		ArrayList<String> keySet = new ArrayList<String>(voteBallot.keySet());
		for(int i = 0; i < keySet.size(); i++){
			key = keySet.get(i);
			for(int j = 0; j < keywords.size(); j++){
				try{
					matcher = Pattern.compile(keywords.get(j), Pattern.CASE_INSENSITIVE).matcher(key);
					if(matcher.find()){
						voteBallot.remove(key);
						break;
					}
				}catch(PatternSyntaxException e){
					continue;
				}
			}
		}
		//XXX System.out.println(voteBallot.size());
		return voteBallot;
	}
	
	/**
	 * This methods return true if a particular token is a a stopword
	 * @param nGram
	 * @return
	 */
	public static boolean isStopWord(String nGram){
		String[] token = nGram.split("/");
		for(final StopWords stopWords : StopWords.class.getEnumConstants()){
			for(int j = 0; j < token.length; j++){
				if(token[j].equalsIgnoreCase(stopWords.getValue())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method removes stopword
	 * @param nGram
	 * @return
	 */
	public static String remoevStopWords(String nGram){
		StringTokenizer st = new StringTokenizer(nGram);
		StringBuffer sb = new StringBuffer();
		String token;
		
		while(st.hasMoreTokens()){
			token = st.nextToken();
			if(!isStopWord(token)){
				sb.append(token);
				sb.append(SPACE);
			}
		}
		
		return sb.toString();
	}
}
///:~