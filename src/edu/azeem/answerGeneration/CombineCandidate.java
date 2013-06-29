//:edu.azeem.answerGeneration/CombineCandidate.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Shorter candidates' scores are combined with longer candidates' 
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CombineCandidate {

	public CombineCandidate(){}
	
	public static HashMap<String, Integer> combine(HashMap<String, Integer> candidates){
		String key, longKey;
		Integer score, longScore;
		Matcher matcher;
		
		int count = 0, errorCount = 0;
		ArrayList<String> keySet = new ArrayList<String>(candidates.keySet());
		for(int i = 0; i < keySet.size(); i++){
			key = keySet.get(i);
			score = candidates.get(key);
			for(int j = 0; j < keySet.size(); j++){
				count++;
				if(i == j)
					continue;
				longKey = keySet.get(j);
				longScore = candidates.get(longKey);
				try{
					//See whether given longKey has a pattern of key
					matcher = Pattern.compile(key, Pattern.CASE_INSENSITIVE).matcher(longKey);
					if(matcher.find()){
						longScore += score;
						candidates.put(longKey, longScore);
					}
				}catch(PatternSyntaxException e){
					errorCount++;
					continue;
				}
			}
		}
		//XXX System.out.println("count: " + count + "\terrorCount: " + errorCount);
		return candidates;
	}
}
///:~