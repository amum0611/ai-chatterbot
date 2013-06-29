//:org.azeem.parsing/TerminalSymbols.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

import edu.azeem.posTagging.PennTags;

public enum TerminalSymbols {
	
	VERB(
		"Verb",
		PennTags.VERB_BASE.getValue(), 
		PennTags.VERB_PAST.getValue(),
		PennTags.VERB_3RD_PERSON_SINGULAR_PRESENT.getValue(),
		PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue(),
		PennTags.VERB_PAST_PARTICIPLE.getValue(),
		PennTags.EXISTENTIAL.getValue(),
		PennTags.MODAL.getValue(),
		PennTags.INTERJECTION.getValue(),
		PennTags.VERB_GERUND.getValue()
	),
	NOUN(
		"Noun",
		PennTags.NOUN_SINGULAR.getValue(), 
		PennTags.NOUN_PLURAL.getValue(),
		PennTags.PROPER_NOUN_SINGULAR.getValue(), 
		PennTags.PROPER_NOUN_PLURAL.getValue()
	),
	PREPOSITION(
		"Prep",
		PennTags.PREPOSITION.getValue()
	),
	PRONOUN(
		"Pronoun",
		PennTags.PERSONAL_PRONOUN.getValue(), 
		PennTags.POSSESSIVE_PRONOUN.getValue(),
		PennTags.WH_PRONOUN.getValue(),
		PennTags.POSSESSIVE_WH_PRONOUN.getValue()
	),
	PARTICLE(
		"Particle",
		PennTags.PARTICLE.getValue()	
	),
	POSSESIVE_ENDING(
		"Pos",
		PennTags.POSSESSIVE_ENDING.getValue()
	),
	DETERMINER(
		"Det",
		PennTags.DETERMINER.getValue(), 
		PennTags.PREDETERMINER.getValue(),
		PennTags.WH_DETERMINER.getValue()
	),
	CARDINAL(
		"Card",
		PennTags.CARDINAL_NUMBER.getValue(),
		PennTags.LIST_ITEM_MARKER.getValue()
	),
	ADJECTIVE(
		"Adj",
		PennTags.ADJECTIVE.getValue(),
		PennTags.ADJECTIVE_COMPARATIVE.getValue(),
		PennTags.ADJECTIVE_SUPERLATIVE.getValue()
	),
	ADVERB(
		"Adv",
		PennTags.ADVERB.getValue(),
		PennTags.ADVERB_COMPARATIVE.getValue(),
		PennTags.ADVERB_SUPERLATIVE.getValue(),
		PennTags.WH_ADVERB.getValue()
	),
	AND(
		"Conj",
		PennTags.COORDINATING_CONJUNCTION.getValue()
	),
	SYMBOL(
		"Sym",
		PennTags.POUND_SIGN.getValue(),
		PennTags.DOLLAR_DIGN.getValue(),
		PennTags.COLON.getValue(),
		PennTags.SEMI_COLON.getValue(),
		PennTags.HYPHEN.getValue(),
		PennTags.DASH.getValue(),
		PennTags.OPEN_PARANTHESE.getValue(),
		PennTags.CLOSE_PARANTHESE.getValue(),
		PennTags.COMMA.getValue(),
		PennTags.STAR.getValue(),
		PennTags.SYMBOL.getValue(),
		PennTags.PERIOD.getValue(),
		PennTags.QUESTION.getValue(),
		PennTags.EXCLAMATION.getValue()
	),
	TO(
		"TO",
		PennTags.TO.getValue()
	);
	
	private TerminalSymbols(String... values){
		setValue(values);
	}
	
	private String[] _values;
	
	public void setValue(String[] values) {
		this._values = values;
	}

	
	public String[] getValue() {		
		return _values;
	}	
}
///:~