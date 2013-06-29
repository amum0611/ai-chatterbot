//:org.azeem.retrieval/Retrieve.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class will retrieve web content from the Internet Corpus
 */
package edu.azeem.retrieval;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.azeem.client.Analysis;
import edu.azeem.server.FilePaths;
import edu.azeem.server.Generation;

public class Retrieve extends Generation{

	private NetConnector _netConnector;
	private URL _url;
	private ArrayList<String> _keywords;
	private ArrayList<String> _resultUrlList;

	private String[] _arrKeywords;
	private String[][] _wordsAndtags;
	
	private final int NUMBER_OF_LINKS = 10;			//this will be the count for the number of web results that will be saved
	private final String DATA_EXT = ".data";
	
	public Retrieve(ArrayList<String> keywords, String[][] wordsAndtags){
		this._keywords = keywords;
		this._wordsAndtags = wordsAndtags;
		_arrKeywords = new String[_keywords.size()];
		for(int i = 0; i < _keywords.size(); i++){
			_arrKeywords[i] = _keywords.get(i);
		}
	}
	
	@Override
	public void handle() {
		searchGoogle();
		getContent();
	}
	
	@Override
	public void print() {
		System.out.println("Generated Google Query: ");
		System.out.println(_url.toString());
		System.out.println();
		Analysis.getUniqueInstance().setQuery(_url.toString());
	}

	@Override
	public Object output() {
		return _keywords;
	}

	@Override
	public String get() {
		return null;
	}

	private void searchGoogle(){
		String fileName;
		_url = QueryGenerator.getQuery(_wordsAndtags);
		
		_keywords = QueryGenerator.getKeywords();
		print();
		//XXX System.out.println(_url + "\t" + _url.getHost());
		_netConnector = new NetConnector(_url);
		
		NetConnector temp = new NetConnector();
		temp.cleanUpContents();
		temp.cleanUpData();
		temp.cleanUpSearch();
		temp.cleanUpSeg();
		
		fileName = _netConnector.getContent(true);	//This is a Google Search
		
		try {
			_resultUrlList = UnnecessaryUrlRemover.remove(SearchResultExtractor.grabUrls(fileName));
			//XXX textList = TextExtractor.extract(fileName, false); //XXX
			//XXX saveData(textList, fileName); //XXX
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < _resultUrlList.size(); i++){
			//System.out.println(_resultUrlList.get(i));
		}
	}
	
	private void getContent(){
		ArrayList<String> fileNameList = new ArrayList<String>();
		ArrayList<String> textList = new ArrayList<String>();
		ArrayList<ArrayList<String>> webContents = new ArrayList<ArrayList<String>>();
		String fileName;
		int count = 0;
		for(int i = 0; i < _resultUrlList.size(); i++){
			//tempUrlString = _resultUrlList.get(i);
			try {
				_netConnector = new NetConnector(new URL(_resultUrlList.get(i)));
				//XXX System.out.println(new URL(_resultUrlList.get(i)));
				fileName = _netConnector.getContent(false);	//This is NOT a Google Search
				if(fileName == null)
					continue;
				fileNameList.add(fileName);
				textList = TextExtractor.extract(fileName, true);
				if(textList.size() != 0 || textList.size() != 1){
					webContents.add(textList);
					saveData(textList, fileName);
					count++;
				}
			} catch (MalformedURLException e) {
				//continue;
			} catch (IOException e) {
				//continue;
			}
			if(count == NUMBER_OF_LINKS){
				break;
			}
		}
	}
	
	private void saveData(ArrayList<String> content, String host){
		try{
			FileWriter writer = new FileWriter(FilePaths.getUniqueInstance().getWebContentLocationData() + "/" + host + DATA_EXT);
			
			for(int i = 0; i < content.size(); i++){
				writer.write(content.get(i));
				writer.write("\n");
				writer.flush();
			}		
			writer.close();
		}catch (IOException e){
			
		}
	}
	
	public static void main(String[] args){
		//String[] keywords = new String[]{"how","to","initialize","int"};
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("how");
		arrayList.add("to");
		arrayList.add("initialize");
		arrayList.add("int");
		
		//Retrieve r = new Retrieve(arrayList);
		//r.handle();
		//r.print();
	}
}
///:~