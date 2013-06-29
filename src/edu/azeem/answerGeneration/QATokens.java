//:edu.azeem.answerGeneration/QuestionTypes.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This enum has encapsulated QA-Tokens proposed by Prager et al (2000)
 */
package edu.azeem.answerGeneration;

public enum QATokens {

	WHAT("WHAT"),		//What is
	PLACE("PLACE$"),	//Where
	PERSON("PERSON$"),	//Who
	TIME("TIME$"),		//When
	OTHER("OTHER$"),
	;
	
	private String _value;
	
	private QATokens(String value){
		this.setValue(value);
	}

	public void setValue(String _value) {
		this._value = _value;
	}

	public String getValue() {
		return _value;
	}
}
