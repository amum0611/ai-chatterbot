//:org.azeem.tokenization/Punctuations.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class contains common punctuations in English Language which is insulated in an enum
 */
package edu.azeem.tokenization;

import java.util.EnumMap;

public enum Punctuations{
	PERIOD("."), QUESTION("?"), EXCLAMATION("!"), COLON(":"), SEMI_COLON(";"), 
	HYPHEN("-"), DASH("–"), OPEN_PARANTHESE("("), CLOSE_PARANTHESE(")"), 
	OPEN_BRACKET("["), CLOSE_BRACKET("]"), OPEN_CURLY_BRACKETS("{"), CLOSE_CURLY_BRACKETS("}"), 
	QUATATION("\""), SLASH("/"), BACKWARD_SLASH("\\"), COMMA(","),
	APOSTROPHE("'");
	
	private String value;
	
	private Punctuations(String value){
		this.setValue(value);
	}

	Punctuations(){
		
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	/**
	 * This initialises an EnumMap that is extremely faster than any other data structures and it is used for 
	 * enum-based lookups (Bruce Ekel, Thinking in Java, 4th).
	 * EnumMap maps the punctuation and command together which provides faster lookups
	 */
	public static EnumMap<Punctuations, Command> punctuationMap = new EnumMap<Punctuations, Command>(Punctuations.class);
	static {
		for(final Punctuations p : Punctuations.class.getEnumConstants()){
			//System.out.println(p.getValue());
			punctuationMap.put(p, new Command(){
				@Override
				public void execute(){
					System.out.println("Punctuation is being computed: " + p.getValue());
				}
				@Override
				public String value(){
					return p.getValue();
				}
			});
		}
	}
}
