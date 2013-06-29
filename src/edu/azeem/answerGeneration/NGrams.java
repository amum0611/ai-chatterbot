//:edu.azeem.answerGeneration/NGrams.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.azeem.tokenization.Punctuations;
import edu.azeem.tokenization.Tokenizer;

public class NGrams {
	
	private ArrayList<String> _listofNgrams;
	private String _candidateSentence;
	private QATokens _qaToken;
	
	private final String STRING_SEPERATOR = "/";
	
	public NGrams(QATokens qaToken){
		_qaToken = qaToken;
	}
	
	/**
	 * Generate n-grams
	 * @param sentence
	 * @return
	 */
	public ArrayList<String> getNGrams(String sentence){
		
		ArrayList<String> tokenList = new ArrayList<String>();
		_listofNgrams = new ArrayList<String>();
		_candidateSentence = sentence.toLowerCase();
		tokenList = tokenize(_candidateSentence);
		
		if(tokenList.size() >= 1)
			firstGram(tokenList);
		if(tokenList.size() >= 2)
			secondGram(tokenList);
		
		_listofNgrams = removeDuplicates(_listofNgrams);
		return _listofNgrams;
		
	}
	
	/**
	 * Generate 1-gram
	 * @param tokenList
	 */
	protected void firstGram(ArrayList<String> tokenList){
		for(int i = 0; i < tokenList.size(); i++){
			_listofNgrams.add(tokenList.get(i));
		}
	}
	
	/**
	 * Generate 2-gram
	 * @param tokenList
	 */
	protected void secondGram(ArrayList<String> tokenList){
		String temp;
		for(int i = 0; i < tokenList.size() - 1; i++){
			temp = tokenList.get(i) + STRING_SEPERATOR + tokenList.get(i+1);
			_listofNgrams.add(temp);
		}
	}
	
	/**
	 * Generate 3-gram
	 * @param tokenList
	 */
	protected void thrirdGram(ArrayList<String> tokenList){
		String temp;
		for(int i = 0; i < tokenList.size() - 2; i++){
			temp = tokenList.get(i) + STRING_SEPERATOR + tokenList.get(i+1) +  
					STRING_SEPERATOR + tokenList.get(i+2);
			_listofNgrams.add(temp);
		}
	}
	
	/**
	 * Generate 4-gram
	 * @param tokenList
	 */
	protected void fourthGram(ArrayList<String> tokenList){
		String temp;
		for(int i = 0; i < tokenList.size() - 3; i++){
			temp = tokenList.get(i) + STRING_SEPERATOR + tokenList.get(i+1) +  
					STRING_SEPERATOR + tokenList.get(i+2) + 
					STRING_SEPERATOR + tokenList.get(i+3);
			_listofNgrams.add(temp);
		}
	}
	
	/**
	 * tokenize sentences
	 * @param sentence
	 * @return
	 */
	private ArrayList<String> tokenize(String sentence){
		StringTokenizer st = new StringTokenizer(sentence);
		String token;
		ArrayList<String> tokenList = new ArrayList<String>();		
		while(st.hasMoreTokens()){
			token = st.nextToken();
			tokenList.add(token);
		}
		return tokenList;
	}
	
	/**
	 * remove duplicate n-grams
	 * @param list
	 * @return
	 */
	private ArrayList<String> removeDuplicates(ArrayList<String> list){
		ArrayList<String> newList = new ArrayList<String>();
		String temp;
		for(int i = 0; i < list.size(); i++){
			temp = list.get(i);
			temp = removeUnnecessaryPunctuations(temp);
			if(_qaToken == QATokens.TIME){
				if(!newList.contains(temp) && temp.length() == 4){
					newList.add(temp);
				}
			}
			else if(_qaToken != QATokens.TIME){
				if(!newList.contains(temp) && temp.length() != 0){
					newList.add(temp);
				}
			}
		}
		return newList;
	}
	
	/**
	 * remove unnecessary punctuations 
	 * @param token
	 * @return
	 */
	private String removeUnnecessaryPunctuations(String token){
		
		StringTokenizer st = new StringTokenizer(token, " ", false);
		String newToken = "";
		while(st.hasMoreTokens())
			newToken += st.nextToken();
		
		if(_qaToken == QATokens.TIME){
			newToken = newToken.replaceAll("[^0-9/]", "");
			//newToken = newToken.replaceAll(" ", "");
			token = newToken;
		}
		else{
			token = token.replaceAll("[^A-Za-z/]", "");
		}
		
		return token;
	}
	
	@SuppressWarnings("unused")
	private String containsPunctuations(String token){
		for(final Punctuations punctuation : Punctuations.class.getEnumConstants()){
			String[] temp = token.split(punctuation.getValue());
			if(temp.length != 0){
				Tokenizer tokenizer = new Tokenizer(token);				
				return temp[0];
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static boolean isStopWord(String nGram){
		for(final StopWords stopWords : StopWords.class.getEnumConstants()){
			if(nGram.equalsIgnoreCase(stopWords.getValue())){
				return true;
			}
		}
		return false;
	}
}
///:~