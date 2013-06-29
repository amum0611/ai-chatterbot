//:edu.azeem.wordNet/ConnectWordNet.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class has methods that instantiates IDictionary also makes sure that only
 * one instance is created.
 */
package edu.azeem.wordNet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.azeem.server.FilePaths;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class ConnectWordNet {

	private static volatile ConnectWordNet _uniqueInstance; 			//Keep a reference to this class
	
	private FilePaths _filePaths;
	private URL _url;
	private IDictionary _dictionary;
	
	public ConnectWordNet(){
		_filePaths = FilePaths.getUniqueInstance();
		try {
			_url = new URL("file", null, _filePaths.getWordNetLocation());
		} catch (MalformedURLException e) {
			System.out.println("Invalid URL");
		}
		_dictionary = new Dictionary(_url);
	}
	
	public static ConnectWordNet getUniqueInstance(){
		synchronized(FilePaths.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new ConnectWordNet();
			}	
		}
		return _uniqueInstance;
	}
	
	public IDictionary getDictionary(){
		return _dictionary;
	}
	
	/**
	 * This method will call IStemmer interface and finds the root stem of a word
	 * @param input
	 */
	public List<String> removeStem(String input){
		
		List<String> list = new ArrayList<String>();
		WordnetStemmer ss = new WordnetStemmer(_dictionary);
		list = ss.findStems(input);
		return list;
	}
	
	/**
	 * This method determines the type of the POS tag with the WordNet tag
	 * @param tag
	 * @return
	 */
	public POS getPOS(String tag){
		if(tag.equalsIgnoreCase(WordNetTags.NOUN.getValue())){
			return POS.NOUN;
		}
		else if(tag.equalsIgnoreCase(WordNetTags.ADJECTIVE.getValue())){
			return POS.ADJECTIVE;
		}
		else if(tag.equalsIgnoreCase(WordNetTags.ADVERB.getValue())){
			return POS.ADVERB;
		}
		else if(tag.equalsIgnoreCase(WordNetTags.VERB.getValue())){
			return POS.VERB;
		}
		else{
			return null;
		}
	}
	
	public static void main(String args[]){
		ConnectWordNet.getUniqueInstance();
	}
}
///:~