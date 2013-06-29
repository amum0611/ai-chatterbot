//:edu.azeem.wordNet/Synonyms.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class has methods that will return a set of synonyms for a particular word
 */
package edu.azeem.wordNet;

import java.util.ArrayList;
import java.util.List;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;

public class Synonyms {
	
	private static ConnectWordNet _wordNet;
	private static IIndexWord _indexWord;
	private static IWordID _wordID;
	private static IWord _word;
	private static ISynset _synset;
	
	public Synonyms(){
		_wordNet = ConnectWordNet.getUniqueInstance();
		_wordNet.getDictionary().open();
	}
	
	/**
	 * This method checks whether a synset exist. if so it is returned
	 * @param input
	 * @param tag
	 */
	public List<String> getSynonyms(String input, String tag){
		List<String> list = new ArrayList<String>();
		List<String> synset = new ArrayList<String>();
		synset = get(input, tag);
		if(_indexWord == null){
			list = _wordNet.removeStem(input);
			for(int i = 0; i < list.size(); i++){
				synset = get(list.get(i), tag);
			}
		}
		return synset;
	}
	
	/**
	 * This method gets a word string and tag string and returns the synset
	 * @param input
	 * @param tag
	 */
	private List<String> get(String input, String tag){	
		List<String> synset = new ArrayList<String>();
		//XXX System.out.println(input + "\t" + tag);
		_indexWord = _wordNet.getDictionary().getIndexWord(input, _wordNet.getPOS(tag));
		if(_indexWord != null){
			//Get the first sense of the input string
			_wordID = _indexWord.getWordIDs().get(0);
			_word = _wordNet.getDictionary().getWord(_wordID);
			_synset = _word.getSynset();
			for(IWord word : _synset.getWords()){
				//System.out.println(word.getLemma());
				synset.add(word.getLemma());
			}
		}
		return synset;
	}
	
	/**
	 * Dummy method to test this class
	 * @param args
	 */
	public static void main(String args[]){
		String input = "did";
		Synonyms syn = new Synonyms();
		syn.getSynonyms(input, "VERB");
	}
}
///:~