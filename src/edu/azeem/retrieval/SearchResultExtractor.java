//:org.azeem.retrieval/NetConnector.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class provides mechanism to grab search result URLs
 */
package edu.azeem.retrieval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.MutableAttributeSet;

import edu.azeem.server.FilePaths;

public class SearchResultExtractor {

	private static ArrayList<String> _resultUrlList;
	private static ParserDelegator _parserDeligator;
	private static ParserCallback _parserCallBack;
	private static Reader _reader;
	private static File _file;
	
	private static final String FILE_EXT = ".html";
	
	public SearchResultExtractor(){}
	
	public static ArrayList<String> grabUrls(String fileName) throws IOException{
		_resultUrlList = new ArrayList<String>();
		_parserDeligator = new ParserDelegator();
		_parserCallBack = new ParserCallback(){
			public void handleStartTag(Tag tag, MutableAttributeSet mutableAttribute, int pos) {
				if (tag == Tag.A){					
					Object address = mutableAttribute.getAttribute(Attribute.HREF);
					if(address != null){
						_resultUrlList.add((String)address);
					}
				}
			}
		};
		readFile(fileName);
		_parserDeligator.parse(_reader, _parserCallBack, true);
		_reader.close();
		return _resultUrlList;
	}
	
	private static void readFile(String fileName){
		_file = new File(FilePaths.getUniqueInstance().getWebContentLocationSearch() + "/" + fileName + FILE_EXT);
		try {
			_reader = new FileReader(_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
///:~