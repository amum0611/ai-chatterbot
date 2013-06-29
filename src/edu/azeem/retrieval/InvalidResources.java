//:org.azeem.retrieval/InvalidResources.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This enum contains the host of resources that are considered as invalid
 */
package edu.azeem.retrieval;

public enum InvalidResources {

	WIKIPEDIA("www.wikipedia.org"),
	WIKIPEDIA1("en.wikipedia.org"),
	YOUTUBE("www.youtube.com"),
	AMAZON("www.amazon.com"),
	YAHOO_ANSWERS("answers.yahoo.com"),
	WHITE_HOUSE("www.whitehouse.gov"),
	INVENTORS("inventors.about.com"),
	BLURTIT("www.blurtit.com"),
	ASK("answers.ask.com"),
	;
	
	private String _value;
	
	private InvalidResources(String value){
		setValue(value);
	}
	
	public void setValue(String value) {
		this._value = value;
	}
	
	public String getValue() {
		return _value;
	}
	
	/**
	 * This method will return true is host is an invalid resource
	 * @param host
	 * @return boolean
	 */
	public static boolean isInvalidResource(String host){
		for(int i = 0; i < InvalidResources.values().length; i++){
			if(host.equals(InvalidResources.values()[i].getValue().toString())){
				return true;
			}
		}
		return false;
	}
}
///:~