//:edu.azeem.answerGeneration/LappinLeass.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This extracts the discourse context using Lappin & Leass algorithm
 */
package edu.azeem.answerGeneration;

import java.util.ArrayList;
import java.util.Stack;

public class LappinLeass {

	private String _sentence;
	private final int SALIENCE = 3;
	
	public LappinLeass(String sentence){
		_sentence = sentence;
	}
	
	public String selectReferent(ArrayList<String> sentenceList){
		
		Stack<String> stack = new Stack<String>();
		Stack<String> tempStack = new Stack<String>();
		boolean found = false;
		for(int i = 0; i < sentenceList.size(); i++){
			stack.add(sentenceList.get(i));
			if(sentenceList.get(i).equalsIgnoreCase(_sentence)){
				found = true;
				if(i + 1 < sentenceList.size()){
					stack.add(sentenceList.get(i + 1));
				}
			}
			if(found){
				for(int j = 0; j < SALIENCE; j++){
					if(!stack.empty()){
						tempStack.add(stack.pop());
					}
				}
				break;
			}
		}
		return convertToString(tempStack);
	}
	
	private String convertToString(Stack<String> stack){
		StringBuffer sb = new StringBuffer();
		while(!stack.empty()){
			sb.append(stack.pop());
			sb.append("\n");
		}
		return sb.toString();
	}
}
///:~