//:edu.azeem.wordNet/WordNetTags.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class contains the 4 tags in WOrdNEt which is insulated in an enum
 */
package edu.azeem.wordNet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.azeem.server.FilePaths;

public enum WordNetTags {
	
	NOUN("NOUN"),
	VERB("VERB"),
	ADJECTIVE("ADJECTIVE"),
	ADVERB("ADVERB"),
	OTHER("OTHER");

	private String _value;
	private static BufferedReader _reader;
	//Location of Tag Relation List
	private static final String _tagRelation = FilePaths.getUniqueInstance().getTagRelation();	

	public static WordNetTags getWordNetTag(String input){
		String dataLine = null;
		String[] tokens = null;
		
		try{
			_reader = new BufferedReader(new InputStreamReader(new FileInputStream(_tagRelation)));
			while((dataLine = _reader.readLine()) != null){
				if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
					continue;
				else if(dataLine.startsWith("#"))
					continue;
				tokens = dataLine.split("\t");
				if(input.equals(tokens[2])){
					for(final WordNetTags tag : WordNetTags.class.getEnumConstants()){
						if(tokens[1].equals(tag.getValue())){
							_reader.close();
							return tag;
						}
					}
				}
			}
		}catch (FileNotFoundException e) {
			System.err.println("File is missing: " + _tagRelation);
		} catch (IOException e) {
			System.err.println("Reading from: " + _tagRelation + " is failed.");
		}
		return WordNetTags.OTHER;
	}
	
	private WordNetTags(String value){
		this.setValue(value);
	}

	public void setValue(String _value) {
		this._value = _value;
	}

	public String getValue() {
		return _value;
	}
	
	public static void main(String args[]){
		System.out.println(WordNetTags.getWordNetTag("VBZ").getValue());
	}
}
///:~