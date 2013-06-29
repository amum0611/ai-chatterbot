//:edu.azeem.answerGeneration/Answer.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class encapsulate methods and attributes to rank answers for Vector Space Model
 */
package edu.azeem.answerGeneration;

import java.util.Comparator;

public class Answer implements Comparator<Answer>{

	private String _answer;		//The Chatterbot's answer
	private double _weight;		//Weight of the answer
	private int _sentenceID;	//Sentence ID
	private int _documentID;	//Documetn ID
	
	public Answer(String answer, double weight, int sentenceID, int documentID){
		setAnswer(answer);
		setWeight(weight);
		setSentenceID(sentenceID);
		setDocumentID(documentID);
	}
	
	public void setAnswer(String answer) {
		this._answer = answer;
	}

	public String getAnswer() {
		return _answer;
	}

	public void setWeight(double weight) {
		this._weight = weight;
	}

	public double getWeight() {
		return _weight;
	}

	public void setSentenceID(int sentenceID) {
		this._sentenceID = sentenceID;
	}

	public int getSentenceID() {
		return _sentenceID;
	}

	public void setDocumentID(int documentID) {
		this._documentID = documentID;
	}

	public int getDocumentID() {
		return _documentID;
	}

	@Override
	/*
	 * This method will compare 2 answer object, that is used to prioritise
	 */
	public int compare(Answer o1, Answer o2) {
		double tempWeight1 = o1.getWeight();
		double tempWeight2 = o2.getWeight();
		
		if(tempWeight1 > tempWeight2)
			return -1;
		else if(tempWeight1 < tempWeight2)
			return 1;
		else
			return 0;
	}
}
///:~