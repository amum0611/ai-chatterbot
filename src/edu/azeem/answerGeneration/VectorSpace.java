//:edu.azeem.answerGeneration/VectorSpace.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This implements Vector Space Model, which ranks sentences by cosin in terms of the 
 * similarity with the human query..
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
import java.util.StringTokenizer;

import edu.azeem.client.Analysis;
import edu.azeem.server.FilePaths;
import edu.azeem.server.Generation;

public class VectorSpace extends Generation{

	private File _dataLocation;			//An abstract representation of file and directory pathnames
	private File[] _dataFiles;			//stores array of abstract pathnames denoting the files
	private FilePaths _filePaths;
	private BufferedReader _reader;		//Provides efficient reading of texts from a character input stream
	private DocumentSegmentation _ds;
	
	private ArrayList<String> _humanQuery;
	private ArrayList<String> _sentences;
	private HashMap<String, double[]> _wordOccurence;
	
	private double[][] _pi;
	private double _maxTf;		//Most frequent term in a document
	private double[][] _tf;		//Term Frequency within a single document
	private int _N;				//Total number of documents in the collection
	private int _n;				//number of documents in which term i occurs
	private int _numberOfSenteces = 0;
	private String _answer;
	private double _weight;
	
	/**
	 * The weight of term i in the vector for document j is the product of its
	 * overall frequency in j with the log its inverse document frequency in the
	 * collection. (Jurafsky and Martin, 2000, p.680)
	 */
	//private double _weight;		// _tf x _idf
	
	public VectorSpace(ArrayList<String> humanQuery){
		//double[] occuranceFrequency;
		_filePaths = FilePaths.getUniqueInstance();
		_ds = new DocumentSegmentation();
		_ds.retrievePassage();
		_dataLocation = new File(_filePaths.getWebContentLocationSeg());
		if(_dataLocation.isDirectory()){				//Checks whether _brownCorpusFile is a directory
			_dataFiles = _dataLocation.listFiles();
		}
		else
			_dataFiles = new File[]{_dataLocation}; 
		
		_wordOccurence = new HashMap<String, double[]>();
		_humanQuery = humanQuery;
		_N = _dataFiles.length;
		_sentences = new ArrayList<String>();
	}
	
	/**
	 * This method reads the web content
	 */
	private void read(){
		String dataLine;							//Line of data in a file
		
		for(File aDataFile : _dataFiles){			//Loops through all files in _dataFiles
			try {
				_reader = new BufferedReader(new InputStreamReader(new FileInputStream(aDataFile)));
				while((dataLine = _reader.readLine()) != null){
					if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
						continue;
					determineOccurance(dataLine);
				}
				_reader.close();
			} catch (FileNotFoundException e) {
				System.err.println("File is missing: " + aDataFile.getName());
			} catch (IOException e) {
				System.err.println("Reading from: " + aDataFile.getName() + " is failed.");
			}
		}
	}
	
	/**
	 * This method determines the occurrence of tokens in a given sentence
	 * @param dataLine
	 */
	private void determineOccurance(String dataLine){
		String regex = "\\.";
		String token;
		double[] occuranceFrequency;
		StringTokenizer st;
		ArrayList<String> termsInSentence;
		String[] sentences;
		
		sentences = dataLine.split(regex);
		occuranceFrequency = new double[_humanQuery.size()];
		termsInSentence = new ArrayList<String>();
		
		for(int j = 0; j < _humanQuery.size(); j++){
			occuranceFrequency[j] = new Double(0.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
		}
		
		for(int k = 0; k < sentences.length; k++){
			if(sentences[k].length() <= 10)
				continue;
			_sentences.add(sentences[k]);
			st = new StringTokenizer(sentences[k]);
			while(st.hasMoreTokens()){
				token = st.nextToken();
				termsInSentence.add(token);
			}	
		
			for(int i = 0; i < termsInSentence.size(); i++){
				for(int j = 0; j < _humanQuery.size(); j++){					
					occuranceFrequency[j] += 1.0D;
					if(termsInSentence.get(i).equalsIgnoreCase(_humanQuery.get(j))){
						if(!_wordOccurence.containsKey(sentences[k])){
							_wordOccurence.put(sentences[k], occuranceFrequency);
						}
						else{
							occuranceFrequency = _wordOccurence.get(sentences[k]);
							occuranceFrequency[j] += 1.0D;
						}
					}
					_wordOccurence.put(sentences[k], occuranceFrequency);
				}
			}
		}
	}
	
	/**
	 * This method converts the raw frequency terms into weights
	 */
	private void convertToWeights(){
		double[] temp;
		double[] maxTf;
		
		maxTf = new double[_sentences.size()];
		
		_numberOfSenteces = _sentences.size();
		
		_tf = new double[_numberOfSenteces][_humanQuery.size()];		
		_pi = new double[_numberOfSenteces][_humanQuery.size()];
		
		for(int j = 0; j < _sentences.size(); j++){
			maxTf[j] = new Double(1.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
		}
		
		/*
		 * This portion finds the most frequent term in each document
		 */
		for(int i = 0; i < _wordOccurence.size(); i++){
			temp = _wordOccurence.get(_sentences.get(i));
			for(int j = 0; j < temp.length; j++){
				if(temp[j] > maxTf[j]){
					maxTf[j] = temp[j];
				}
			}
		}
		
		/*
		 * This portion of code updates term-by-sentence matrix
		 * with weights
		 */
		for(int i = 0; i < _numberOfSenteces; i++){
			temp = _wordOccurence.get(_sentences.get(i));
			_n = 0;
			for(int j = 0; j < temp.length; j++){
				if(temp[j] != 0){
					_n++;
				}
			}
			for(int j = 0; j < temp.length; j++){
				_tf[i][j] = temp[j] + 1;			//Add one smoothing
				_maxTf = maxTf[j];
				_pi[i][j] = termWeight(calculateIdf(_n), _tf[i][j]);
			}
		}
	}
	
	/**
	 * Normalises the vector
	 */
	private void normalizeVector(){
		_pi = scalarMultiplication(_pi, 1 / norm(_pi));
	}
	
	/**
	 * Calculates the idf value
	 * @return
	 */
	private double calculateIdf(int n){
		return Math.log1p(_N/n);
	}
	
	/**
	 * This method calculates the weight of a term in the vector for document j
	 * @param idf
	 * @param tf
	 * @return double
	 */
	private double termWeight(double idf, double tf){
		return ((0.5 + (0.5*tf/_maxTf))*idf);
	}
	
	/**
	 * Assigns a strictly positive length or size to all vectors in a vector space
	 * @param array
	 * @return maximum positive column length
	 */
	private double norm(double[][] array){

		double euclideanLength = 0;				//Euclidean length
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				euclideanLength += Math.pow(_pi[i][j], 2);
			}
		}
		//return euclideanLength;
		return Math.sqrt(euclideanLength);
	}

	/**
	 * This method takes a 2-dimensional array and a scalar value and performs scalar multiplication
	 * @param array
	 * @param scalar
	 * @return The scalar multiplication of the array
	 */
	private double[][] scalarMultiplication(double[][] array, double scalar){
		double[][] tempArray = array;
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				tempArray[i][j] = array[i][j] * scalar;
			}				
		}
		return tempArray;
	}
	
	/**
	 * This method will select the top 10 answers along with their probability
	 */
	private void selectCandidateAnswers(){
		double[] totalWeight = new double[_pi[0].length];
		double max = 0.0D;
		int sentenceID = 0;
		
		for(int i = 0; i < totalWeight.length; i++){
			totalWeight[i] = new Double(0.0D);
		}
		
		for(int i = 0; i < _pi.length; i ++){
			for(int j = 0; j < _pi[i].length; j++){
				totalWeight[j] += _pi[i][j];
			}
		}
		
		for(int i = 0; i < totalWeight.length; i++){
			if(max <= totalWeight[i]){
				max = totalWeight[i];
				sentenceID = i;
			}
		}
		_answer = _sentences.get(sentenceID);
		_weight = totalWeight[sentenceID];
	}
	
	@Override
	public void handle() {
		read();
		convertToWeights();
		normalizeVector();
		selectCandidateAnswers();
		print();
		collectReferents();
	}

	@Override
	public void print() {
		System.out.println("Answers: ");
		System.out.println(_weight + "\t" + _answer);
		System.out.println();
		Analysis.getUniqueInstance().setTable(Double.toString(_weight), _answer);
	}

	@Override
	public Object output() {
		return _answer;
	}

	@Override
	public String get() {
		Analysis.getUniqueInstance().setAnswer(_answer);
		return _answer;
	}
	
	private void collectReferents(){
		LappinLeass ll = new LappinLeass(_answer);
		Analysis.getUniqueInstance().setDiscourse(ll.selectReferent(_sentences));
	}
}
///:~