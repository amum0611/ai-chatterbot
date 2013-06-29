//:org.azeem.retrieval/TextExtractor.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class removes all the HTML tags and extract only text
 */
package edu.azeem.retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import edu.azeem.server.FilePaths;

public class TextExtractor {

	private static ArrayList<String> _textList;
	private static ParserDelegator _parserDeligator;
	private static ParserCallback _parserCallBack;
	private static Reader _reader;
	private static BufferedReader _bufferedReader;
	private static File _file;
	private static Matcher _matcher;
	
	private static final String NEW_LINE = "\n";
	private final static String FILE_EXT = ".html";
	
	private TextExtractor(){}
	
	public static ArrayList<String> extract(String fileName, boolean flag) throws IOException{
		_textList = new ArrayList<String>();
		_parserDeligator = new ParserDelegator();
		_parserCallBack = new ParserCallback(){
			public void handleText(char[] data, int pos) {
				_textList.add(new String(data));
			}
		};
		readFile(fileName, flag);
		_parserDeligator.parse(_reader, _parserCallBack, flag);
		_reader.close();
		return _textList;
	}
	
	public static void extractRegex(String fileName){
		String data = readFileRegex(fileName);
		data = removeHtmlGroups(data);
		System.out.println(data);
	}
	
	private static void readFile(String fileName, boolean flag){
		if(flag)
			_file = new File(FilePaths.getUniqueInstance().getWebContentLocationTemp() + "/" + fileName + FILE_EXT);
		else
			_file = new File(FilePaths.getUniqueInstance().getWebContentLocationSearch() + "/" + fileName + FILE_EXT);
		
		try {
			_reader = new FileReader(_file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName + "\nLocation: " + _file);
		}
	}
	
	private static String readFileRegex(String fileName){
		String dataLine;
		String data = "";
		
		_file = new File(FilePaths.getUniqueInstance().getWebContentLocationTemp() + "/" + fileName + FILE_EXT);
		try {
			_bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(_file)));
			while((dataLine = _bufferedReader.readLine()) != null){
				if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
					continue;
				dataLine += NEW_LINE;
				data += dataLine;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private static String removeHtmlGroups(String data){
		//data = data.replaceAll("\"", "");
		for(int i = 0; i < HtmlTags.values().length; i++){
			_matcher = Pattern.compile(HtmlTags.values()[i].getValue(), Pattern.CASE_INSENSITIVE).matcher(data);
			if(_matcher.find()){
				System.out.println("I'm here: " + HtmlTags.values()[i].getValue());
				data = data.replaceAll(HtmlTags.values()[i].getValue(), "");
			}
		}
		data = data.replaceAll(NEW_LINE, "\n");
		return data;
	}
	
	public static void main(String[] args){
		String fileName = "java-forums";
		TextExtractor.extractRegex(fileName);
		//System.out.println(new UID().toString());
	}
}
///:~