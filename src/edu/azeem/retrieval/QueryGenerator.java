//:edu.azeem.retrieval/NetConnector.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class provides methods to generator queries for Google search
 */
package edu.azeem.retrieval;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.azeem.wordNet.Synonyms;
import edu.azeem.wordNet.WordNetTags;

public class QueryGenerator {
	
	private static URL _newUrl;
	private static StringBuffer _stringBuffer;
	private static ArrayList<String> _listOfKeywords;
	
	private final static String QUERY_START_GOOGLE = "http://www.google.lk/search?q=";
	private final static String QUERY_START_ASK = "http://www.ask.com/web?q=";
	private final static String QUERY_START_YAHOO = "http://search.yahoo.com/search?p=";
	private final static String NUM_OF_RESULTS = "&num=30";
	private final static String CONCAT = "+";
	//XXX private final static String JAVA = "java";
	private final static String OR = "OR";
	private final static String Q_MARK = "?";
	
	public QueryGenerator(){}
	
	/**
	 * This method gets an array of strings and forms a google URL with query string 
	 * @param keywords
	 * @return URL
	 */
	public static URL getQuery(String[][] wordsAndTags){
		String[] keywords;
		_listOfKeywords = new ArrayList<String>();
		_stringBuffer = new StringBuffer();
		
		keywords = formQuery(wordsAndTags);		
		for(int i = 0; i < keywords.length; i++){
			if(keywords[i].equals(Q_MARK))
				continue;
			_stringBuffer.append(keywords[i]);
			_stringBuffer.append(CONCAT);
		}
		
		_stringBuffer.insert(0, getQueryStartGoogle());
		_stringBuffer.append(NUM_OF_RESULTS);
		
		try {
			_newUrl = new URL(_stringBuffer.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return _newUrl;
	}
	
	private static String[] formQuery(String[][] wordsAndTags){
		
		ArrayList<String> keywordList = new ArrayList<String>();
		List<String> synset = new ArrayList<String>();
		String[] keywords = null;
		String tag;
		Synonyms synonyms = new Synonyms();
		
		for(int i = 0; i < wordsAndTags.length; i++){
			tag = wordsAndTags[i][1];
			
			//This portion skips stopwords from the query
			//if(FilterCandidate.isStopWord(wordsAndTags[i][0]))
				//continue;
				
			if(WordNetTags.getWordNetTag(tag).equals(WordNetTags.NOUN) || 
					WordNetTags.getWordNetTag(tag).equals(WordNetTags.VERB)){
				synset = synonyms.getSynonyms(wordsAndTags[i][0], WordNetTags.NOUN.getValue());
				if(synset != null){
					if(!Questions.contains(wordsAndTags[i][1])){
						keywordList.add(formTuple(wordsAndTags[i][0], synset));
					}
					else
						_listOfKeywords.add(wordsAndTags[i][0]);
				}
				else{
					if(!Questions.contains(wordsAndTags[i][1])){
						keywordList.add(wordsAndTags[i][0]);
					}
					_listOfKeywords.add(wordsAndTags[i][0]);
				}
			}
			else{
				if(!Questions.contains(wordsAndTags[i][1])){
					keywordList.add(wordsAndTags[i][0]);
				}
				_listOfKeywords.add(wordsAndTags[i][0]);
			}
		}
		
		keywords = new String[keywordList.size()];
		for(int i = 0; i < keywordList.size(); i++){
			keywords[i] = keywordList.get(i);
		}
		return keywords;
	}
	
	private static String formTuple(String word, List<String> synset){
		StringBuffer tuple = new StringBuffer();
		String regex = "_";
		Matcher matcher;
		
		tuple.append("(");
		tuple.append(word);
		_listOfKeywords.add(word);
		/*XXX if(synset.size() >= 2){
			tuple.append(CONCAT);
			tuple.append(OR);
			tuple.append(CONCAT);
			tuple.append(synset.get(1));
			_listOfKeywords.add(synset.get(1));
		}*/
		
		for(int i = 1; i < synset.size(); i++){
			matcher = Pattern.compile(regex).matcher(synset.get(i));
			if(!matcher.find()){
				tuple.append(CONCAT);
				tuple.append(OR);
				tuple.append(CONCAT);
				tuple.append(synset.get(i));
				_listOfKeywords.add(synset.get(i));
				break;
			}
		}
		
		tuple.append(")");
		return tuple.toString();
	}

	public static String getQueryStartGoogle() {
		return QUERY_START_GOOGLE;
	}

	public static String getQueryStartAsk() {
		return QUERY_START_ASK;
	}

	public static String getQueryStartYahoo() {
		return QUERY_START_YAHOO;
	}
	
	public static ArrayList<String> getKeywords(){
		return _listOfKeywords;
	}
}
///:~