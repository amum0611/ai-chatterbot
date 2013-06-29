//:org.azeem.parsing/NonTerminalSymbols.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

public enum NonTerminalSymbols{

	ROOT("ROOT"),
	SENTENCE("S"), 
	NOUN_PHRASE("NP"),
	VERB_PHRASE("VP"),
	WH_NOUN_PHRASE("WHNP"),
	PREPOSITIONAL_PHRASE("PP"),
	ADJECTIVE_PHRASE("AP"),
	NOMINAL("Nominal"),
	GREUND_VP("GreundVP"),
	REL_CLAUSE("RelClause");
	//THIRD_SG_NP("3sgNP"),
	//NON_THIRD_SG_NP("Non3sgNP"),
	//SG_NOMINAL("SgNominal"),
	//PL_NOMINAL("PlNominal");
	
	private String _value;
	
	private NonTerminalSymbols(String value){
		setValue(value);
	}
	
	
	public void setValue(String value) {
		this._value = value;
	}
	
	public String getValue() {
		return _value;
	}	
}
///:~