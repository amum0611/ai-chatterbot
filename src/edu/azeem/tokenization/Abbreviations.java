//:org.azeem.tokenization/Abbreviations.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class contains common Abbreviations in English Language which is insulated in an enum
 */
package edu.azeem.tokenization;

import java.util.EnumMap;

public enum Abbreviations{
	MRS("mrs."), MR("mr."), MS("ms."), Prof("prof."), GEN("gen."), 
	SR("sr."), JR("jr."), PHD("ph.d"), BA("b.a."), 
	MA("m.a."), BSC("b.sc"), USA("u.s.a"), UK("u.k."), 
	AM("a.m."), PM("p.m."), EG("e.g."), IE("i.e.");
	
	private String value;
	
	private Abbreviations(String value){
		this.setValue(value);
	}

	Abbreviations(){
		
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	/**
	 * This initializes an EnumMap that is extremely faster than any other data structures and it is used for 
	 * enum-based lookups (Bruce Ekel, Thinking in Java, 4th).
	 * EnumMap maps the Abbreviations and category together which provides faster lookups
	 */
	public static EnumMap<Abbreviations, Command> abbreviationsMap = new EnumMap<Abbreviations, Command>(Abbreviations.class);
	static {
		for(final Abbreviations p : Abbreviations.class.getEnumConstants()){
			//System.out.println(p.getValue());
			abbreviationsMap.put(p, new Command(){
				@Override
				public void execute() {
					System.out.println("Abbreviations is being computed: " + p.getValue());
				}

				@Override
				public String value() {
					return p.getValue();
				}
			});
		}
	}
	public static String abbreviates(Abbreviations input) {
		return abbreviationsMap.get(input).value();
	}
}
