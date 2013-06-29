//:edu.azeem.answerGeneration/DocumentSegmentation.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class will segment the document extract the passages...  
 */
package edu.azeem.answerGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.azeem.server.FilePaths;
import edu.azeem.tokenization.Punctuations;

public class DocumentSegmentation {

	private File _dataLocation;			//An abstract representation of file and directory pathnames
	private File[] _dataFiles;			//stores array of abstract pathnames denoting the files
	private FilePaths _filePaths;
	private BufferedReader _reader;		//Provides efficient reading of texts from a character input stream
	private ArrayList<String> _sentences;
	
	private final int COUNT_TOKENS = 15;	//A sentence is longer that this count
	private final String REGEX = Punctuations.PERIOD.getValue();
	private final String COPYRIGHT = "copyright";
	private final String BROWSER = "browser";
	private final String HTML_DETAILS = "#";
	
	/**
	 * Public constructor 
	 */
	public DocumentSegmentation(){
		_filePaths = FilePaths.getUniqueInstance();
		_dataLocation = new File(_filePaths.getWebContentLocationData());
		if(_dataLocation.isDirectory()){				//Checks whether _brownCorpusFile is a directory
			_dataFiles = _dataLocation.listFiles();
		}
		else
			_dataFiles = new File[]{_dataLocation};
	}
	
	/**
	 * This method is responsible for retrieve passages
	 */
	public void retrievePassage (){
		String dataLine;					//Line of data in a file
		String lastToken = "0";
		StringTokenizer st1;
		Matcher matcher;
		
		for(File aDataFile : _dataFiles){	//Loops through all files in _dataFiles
			try {
				_sentences = new ArrayList<String>();
				_reader = new BufferedReader(new InputStreamReader(new FileInputStream(aDataFile)));
				while((dataLine = _reader.readLine()) != null){
					st1 = new StringTokenizer(dataLine);
					if(dataLine.isEmpty())	//If dataLine is empty then start the loop with the next line
						continue;
					if(st1.countTokens() <= COUNT_TOKENS)
						continue;
					matcher = Pattern.compile(COPYRIGHT, Pattern.CASE_INSENSITIVE).matcher(dataLine);
					if(matcher.find())
						continue;
					matcher = Pattern.compile(BROWSER, Pattern.CASE_INSENSITIVE).matcher(dataLine);
					if(matcher.find())
						continue;
					matcher = Pattern.compile(HTML_DETAILS, Pattern.CASE_INSENSITIVE).matcher(dataLine);
					if(matcher.find())
						continue;
					
					while(st1.hasMoreTokens()){
						lastToken = st1.nextToken();
						//XXX System.out.println(lastToken);
					}
					
					matcher = Pattern.compile(REGEX).matcher(lastToken);
					if(matcher.find()){
						_sentences.add(dataLine);
						//XXX System.out.println(dataLine);
					}
				}
				saveSegmentedData(aDataFile.getName(), _sentences);
				_reader.close();
			} catch (FileNotFoundException e) {
				System.err.println("File is missing: " + aDataFile.getName());
			} catch (IOException e) {
				System.err.println("Reading from: " + aDataFile.getName() + " is failed.");
			}
		}
		
	}
	
	/**
	 * This protected method will save the segmented data as different data.
	 * @param fileName
	 * @param sentences
	 */
	protected void saveSegmentedData(String fileName, ArrayList<String> sentences){
		File file = new File(_filePaths.getWebContentLocationSeg() + "/" + fileName);
		try {
			FileWriter writer = new FileWriter(file);
			for(int i = 0; i < sentences.size(); i++){
				writer.write(sentences.get(i));
				writer.write("\n");
				writer.flush();
			}		
			writer.close();
		} catch (IOException e) {
			
		}		
	}
	
	public static void main(String args[]){
		DocumentSegmentation ds = new DocumentSegmentation();
		ds.retrievePassage();
	}
	
}
///:~