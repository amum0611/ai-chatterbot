//:org.azeem.tokenization/Category.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description Category enum categorises the different types Punctuations in terms of the commonalities. In technical
 * terms Punctuation FSA is cascaded with category FSA and composing a FST.
 * @author Azeem
 *
 */
package edu.azeem.tokenization;

import java.util.EnumMap;

public enum Category{
	
	BACK_REFERENCES(
		Punctuations.PERIOD, Punctuations.QUESTION, Punctuations.OPEN_PARANTHESE, 
		Punctuations.CLOSE_PARANTHESE, Punctuations.OPEN_BRACKET, 
		Punctuations.CLOSE_BRACKET, Punctuations.OPEN_CURLY_BRACKETS, 
		Punctuations.CLOSE_CURLY_BRACKETS
	),
	
	NON_BACK_REFERENCES(
		Punctuations.EXCLAMATION, Punctuations.COLON, Punctuations.SEMI_COLON, 
		Punctuations.HYPHEN, Punctuations.DASH,  Punctuations.APOSTROPHE, 
		Punctuations.SLASH, Punctuations.COMMA
	),
	
	SINGLE_BACK_REFERENCES(
		Punctuations.QUATATION	
	),
	
	SPECIAL_CASE_BACKWARD_SLASH(
		Punctuations.BACKWARD_SLASH
	),

	OPEN(
		Punctuations.OPEN_PARANTHESE, Punctuations.OPEN_BRACKET, 
		 Punctuations.OPEN_CURLY_BRACKETS
	),
	
	CLOSE(
		Punctuations.CLOSE_PARANTHESE, Punctuations.CLOSE_BRACKET,
		Punctuations.CLOSE_CURLY_BRACKETS
	);
	
	private Punctuations[] values;							//Holds the Punctuation values for a category
	
	public Punctuations[] getValue() {
		return values;
	}
	
	Category(Punctuations... types){
		values = types;
	}
	
	/**
	 * This initialises an EnumMap that is extremely faster than any other data structures and it is used for 
	 * enum-based lookups (Bruce Ekel, Thinking in Java, 4th).
	 * EnumMap maps the punctuation and category together which provides faster lookups
	 */
	public static EnumMap<Punctuations, Category> categories = new EnumMap<Punctuations, Category>(Punctuations.class);
	static {
		for(Category c : Category.class.getEnumConstants()){
			for(Punctuations type : c.values){
				categories.put(type, c);
			}
		}
	}
	
	public static Category categorize(Punctuations input) {
		return categories.get(input);
	}
	
}