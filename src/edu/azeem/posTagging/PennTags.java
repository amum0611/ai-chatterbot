//:edu.azeem.posTagging/PennTags.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class contains the 38 tags in Penn Treebank Tagsets which is insulated in an enum
 */
package edu.azeem.posTagging;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;

import edu.azeem.server.FilePaths;

public enum PennTags{
	
	COORDINATING_CONJUNCTION("CC"),					//1
	CARDINAL_NUMBER("CD"),							//2
	DETERMINER( "DT"),								//3
	EXISTENTIAL("EX"),								//4
	FOREIGN_WORD("FW"),								//5
	PREPOSITION("IN"),								//6
	ADJECTIVE("JJ"),								//7
	ADJECTIVE_COMPARATIVE("JJR"),					//8
	ADJECTIVE_SUPERLATIVE("JJS"),					//9
	LIST_ITEM_MARKER("LS"),							//10*
	MODAL("MD"),									//11
	NOUN_SINGULAR("NN"),							//12
	NOUN_PLURAL("NNS"),								//13
	PROPER_NOUN_SINGULAR("NNP"),					//14
	PROPER_NOUN_PLURAL("NNPS"),						//15
	PREDETERMINER("PDT"),							//16
	POSSESSIVE_ENDING("POS"),						//17*
	PERSONAL_PRONOUN("PP"),							//18
	POSSESSIVE_PRONOUN("PP$"),						//19
	ADVERB("RB"),									//20
	ADVERB_COMPARATIVE("RBR"),						//21
	ADVERB_SUPERLATIVE("RBS"),						//22
	PARTICLE("RP"),									//23
	SYMBOL("SYM"),									//24*
	TO("TO"),										//25
	INTERJECTION("UH"),								//26
	VERB_BASE("VB"),								//27
	VERB_PAST("VBD"),								//28
	VERB_GERUND("VBG"),								//29
	VERB_PAST_PARTICIPLE("VBN"),					//30
	VERB_NON_3RD_PERSON_SINGULAR_PRESENT("VBP"),	//31
	VERB_3RD_PERSON_SINGULAR_PRESENT("VBZ"),		//32
	WH_DETERMINER("WDT"),							//33
	WH_PRONOUN("WP"),								//34
	POSSESSIVE_WH_PRONOUN("WP$"),					//35
	WH_ADVERB("WRB"),								//36
	POUND_SIGN("#"),								//37*
	DOLLAR_DIGN("$"),								//38*
	PERIOD("."), 									//39
	QUESTION("?"), 									//40*
	EXCLAMATION("!"), 								//41*
	COLON(":"), 									//42
	SEMI_COLON(";"),								//43 
	HYPHEN("-"), 									//44
	DASH("–"), 										//45*
	OPEN_PARANTHESE("("), 							//46
	CLOSE_PARANTHESE(")"),							//47
	COMMA(","),										//48
	STAR("*"),										//49
	OTHER("OTHER");									//50
	
	private String _value;
	private static BufferedReader _reader;
	//private static final String _tagRelation = 
		//"C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/list/TagRelation.txt";	//Location of Tag Relation List

	//Location of Tag Relation List
	private static final String _tagRelation = FilePaths.getUniqueInstance().getTagRelation();	

	private PennTags(String value){
		this.setValue(value);
	}
	
	PennTags(){}
	
	public void setValue(String value) {
		this._value = value;
	}

	public String getValue() {
		return _value;
	}
	
	/**
	 * This initialises an EnumMap that is extremely faster than any other data structures and it is used for 
	 * enum-based lookups (Bruce Ekel, Thinking in Java, 4th).
	 * EnumMap maps the PennTag and command together which provides faster lookups
	 */
	public static EnumMap<PennTags, String> pennTagMap = new EnumMap<PennTags, String>(PennTags.class);
	static {
		String dataLine;
		String[] tokens;

		try {
			_reader = new BufferedReader(new InputStreamReader(new FileInputStream(_tagRelation)));
		} catch (FileNotFoundException e) {
			System.err.println("File is missing: " + _tagRelation);
		}
		try {
			while((dataLine = _reader.readLine()) != null){
				if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
					continue;
				else if(dataLine.startsWith("#"))
					continue;
				//_aStringTokenizer = new StringTokenizer(dataLine, "\t");
				tokens = dataLine.split("\t");
				
				for(final PennTags p : PennTags.class.getEnumConstants()){
					if(p.getValue().equals(tokens[2])){
						pennTagMap.put(p, tokens[0]);
					}
				}
			}
			_reader.close();
		} catch (IOException e){
			System.err.println("Reading from: " + _tagRelation + " is failed.");
		}
	}
	
	/**
	 * This method gets the string value of Brown Tag and returns the corresponding Penn Tag
	 * @param input
	 * @return Penn Tag
	 */
	public static PennTags getPennTag(String input) {
		String dataLine = null;
		String[] tokens = null;

		try {
			_reader = new BufferedReader(new InputStreamReader(new FileInputStream(_tagRelation)));
		} catch (FileNotFoundException e) {
			System.err.println("File is missing: " + _tagRelation);
			e.printStackTrace();
		}
		try {
			while((dataLine = _reader.readLine()) != null){
				if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
					continue;
				else if(dataLine.startsWith("#"))
					continue;

				tokens = dataLine.split("\t");
				if(input.equals(tokens[0])){
					for(final PennTags pennTag : PennTags.class.getEnumConstants()){
						if(tokens[2].equals(pennTag.getValue())){
							_reader.close();
							return pennTag;
						}
					}
				}
			}
		} catch (IOException e){
			System.err.println("Reading from: " + _tagRelation + " is failed.");
		}
		try{
			_reader.close();
		}catch(IOException e){
	
		}
		//System.out.println("");
		return PennTags.OTHER;
	}
	
	/**
	 * This main thread can be used to demonstrates how this component works
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		System.out.println(PennTags.values().length);
		System.out.println(PennTags.getPennTag("BED").getValue());
	}
}


///:~