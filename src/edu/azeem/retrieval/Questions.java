//:edu.azeem.retrieval/Questions.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This enum, categorises the PennTags that is to be eliminated when generating the query
 */
package edu.azeem.retrieval;

import edu.azeem.posTagging.PennTags;

public enum Questions {

	QUESTIONSTYPES(PennTags.PERSONAL_PRONOUN.getValue(),
			PennTags.WH_ADVERB.getValue(),
			PennTags.WH_DETERMINER.getValue(),
			PennTags.WH_PRONOUN.getValue(),
			//PennTags.VERB_3RD_PERSON_SINGULAR_PRESENT.getValue(),
			//PennTags.VERB_BASE.getValue(),
			//PennTags.VERB_GERUND.getValue(),
			//PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue(),
			//PennTags.VERB_PAST.getValue(),
			//PennTags.VERB_PAST_PARTICIPLE.getValue()
			"are",
			"is");
	
	private String[] _value;

	private Questions(String... value){
		setValue(value);
	}

	public void setValue(String[] _value) {
		this._value = _value;
	}

	public String[] getValue() {
		return _value;
	}
	
	public static boolean contains(String value){
		for(Questions q : Questions.class.getEnumConstants()){
			for(String s : q.getValue()){
				if(value.equalsIgnoreCase(s))
					return true;
			}
		}
		return false;
	}
}
