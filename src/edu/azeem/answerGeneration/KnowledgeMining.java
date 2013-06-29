//:edu.azeem.answerGeneration/KnowledgeMining.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.answerGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import edu.azeem.client.Analysis;
import edu.azeem.server.FilePaths;
import edu.azeem.server.Generation;

public class KnowledgeMining extends Generation{
	
	private File _dataLocation;			//An abstract representation of file and directory pathnames
	private File[] _dataFiles;			//stores array of abstract pathnames denoting the files
	private FilePaths _filePaths;
	private BufferedReader _reader;		//Provides efficient reading of texts from a character input stream
	private ArrayList<String> _keywords;
	private ArrayList<String> _listOfNGrams;
	private HashMap<String, Integer> _candidates;
	private ArrayList<String> _answers;
	private NGrams _nGrams;
	private DocumentSegmentation _ds;
	private QATokens _qaToken;
	
	private final String SPACE = " ";
	
	/**
	 * Public constructor
	 * @param keywords
	 * @param qaTokens
	 */
	public KnowledgeMining(ArrayList<String> keywords, QATokens qaTokens){
		
		_filePaths = FilePaths.getUniqueInstance();
		_ds = new DocumentSegmentation();
		_ds.retrievePassage();
		_dataLocation = new File(_filePaths.getWebContentLocationSeg());
		if(_dataLocation.isDirectory()){				//Checks whether _brownCorpusFile is a directory
			_dataFiles = _dataLocation.listFiles();
		}
		else
			_dataFiles = new File[]{_dataLocation}; 
		
		_keywords = keywords;
		_listOfNGrams = new ArrayList<String>();
		_candidates = new HashMap<String, Integer>();
		_qaToken = qaTokens;
	}
	
	/**
	 * This read method is responsible to read web contents to compute n-grams
	 */
	private void read(){
		String dataLine;					//Line of data in a file
		
		for(File aDataFile : _dataFiles){	//Loops through all files in _dataFiles
			try {
				_reader = new BufferedReader(new InputStreamReader(new FileInputStream(aDataFile)));
				while((dataLine = _reader.readLine()) != null){
					if(dataLine.isEmpty())	//If dataLine is empty then start the loop with the next line
						continue;
					_nGrams = new NGrams(_qaToken);
					_listOfNGrams.addAll(_nGrams.getNGrams(dataLine));
				}
				_reader.close();
			} catch (FileNotFoundException e) {
				System.err.println("File is missing: " + aDataFile.getName());
			} catch (IOException e) {
				System.err.println("Reading from: " + aDataFile.getName() + " is failed.");
			}
		}
	}
	
	private void vote(){
		_candidates = Voting.vote(_listOfNGrams);
	}
	
	private void filter(){
		_candidates = FilterCandidate.filter(_candidates, _keywords);
	}
	
	@SuppressWarnings("unused")
	private void combine(){
		_candidates = CombineCandidate.combine(_candidates);
	}
	
	private void eligible(){
		EligibleCandidate ec = new EligibleCandidate(_qaToken);
		_answers = ec.eligibles(_candidates);
	}
	
	@Override
	public void handle() {
		read();
		vote();
		filter();
		//XXX combine();
		eligible();
	}
	
	@Override
	public void print() {
		for(int i = 0; i < _listOfNGrams.size(); i++){
			System.out.println(_listOfNGrams.get(i));
		}
		System.out.println(_listOfNGrams.size());
	}
	
	protected void print(HashMap<String, Integer> voteBallot) {
		ArrayList<String> keySet = new ArrayList<String>(voteBallot.keySet());
		ArrayList<Integer> valueSet = new ArrayList<Integer>(voteBallot.values());
		for(int i = 0; i < keySet.size(); i ++){
			System.out.println(keySet.get(i) + "\t" + valueSet.get(i));
		}
	}
	
	@Override
	public Object output() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String get() {
		StringBuffer answer = new StringBuffer();
		String[] tokens = _answers.get(0).split("/");
		
		for(int i = 0; i < tokens.length; i++){
			answer.append(EligibleCandidate.toTitleCase(tokens[i]));
			answer.append(SPACE);
		}
		
		Analysis.getUniqueInstance().setAnswer(answer.toString());
		return answer.toString();
	}
}
///:~