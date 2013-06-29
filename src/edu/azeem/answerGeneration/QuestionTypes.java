//:edu.azeem.answerGeneration/QuestionTypes.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class determines the question type
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;

public class QuestionTypes {

	public QuestionTypes(){}
	
	/**
	 * This method determines the question type
	 * @param tokenList
	 * @return
	 */
	public static QATokens determine(ArrayList<String> tokenList){		
		String token = "";
		String nextToken = "";
		
		if(tokenList.size() >= 1){
			token = tokenList.get(0);
		}
		if(tokenList.size() >= 2){
			nextToken = tokenList.get(1);
		}
		
		if(token.equalsIgnoreCase("what") && nextToken.equalsIgnoreCase("is"))
			return QATokens.WHAT;
		else if(token.equalsIgnoreCase("where"))
			return QATokens.PLACE;
		else if(token.equalsIgnoreCase("who"))
			return QATokens.PERSON;
		else if(token.equalsIgnoreCase("when"))
			return QATokens.TIME;
		else
			return QATokens.OTHER;
	}
}
///:~