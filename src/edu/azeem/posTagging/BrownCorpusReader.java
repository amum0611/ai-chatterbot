//:org.azeem.posTagging/BrownCorpusReader.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class contains the mechanism that is needed to retrieve tag information form  
 * Brown Corpus
 * @Acknowledgement Mr. Sujit Pal (http://sujitpal.blogspot.com/) for the his information on how to retrieve 
 * from Brown Corpus
 */
package edu.azeem.posTagging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import edu.azeem.client.CorpusNormalizer;
import edu.azeem.server.FilePaths;

public class BrownCorpusReader{
	
	private FilePaths _filePaths;

	private File _brownCorpusFile;				//An abstract representation of file and directory pathnames
	private File[] _dataFiles;					//stores array of abstract pathnames denoting the files
	private BufferedReader _reader;				//Provides efficient reading of texts from a character input stream
	private PennTags _pennTags;					//An instance of PennTag enum
	private PennTags _previousePenTag;			//An instance of PennTag enum which corresponds the previous Penn Tag
	private Map<String, double[]> _wordTagMap;	//This map holds unique words in the Brown corpus with _posProbability
	private AbstractList<String> _piCount;		//List of penn tags as the starting tag
	private AbstractList<String> _aCount;		//List of two adjacent Penn Tags
	private AbstractList<String> _words;		//List of unique Brown Tag words
	private boolean _flag;
	
	private CorpusNormalizer _normalizer;
	private Serializer _serializer;
	private double[] _posProbability;			//Number of occurrence of a Penn Tag for a particular Brown Corpus Word
 	private double[][] _pi;						//Initial Probability of Penn Tags as the starting word in a sentence
 	private double[][] _a;						//Transition Probability: The probabilities of the machine moving from one state to another
 	private double[][] _b;						//Emmision or Observation probability: of observing observing a particular observable state given that the hidden model is in a particular hidden state
 	
	/**
	 * This constructor takes the location of Brown Corpus and Tag relation list
	 * @param brownCorpusLocation
	 * @param tagRelation
	 */
	public BrownCorpusReader(){
		_serializer = Serializer.getUniqueInstance();
		_normalizer = CorpusNormalizer.construct();
		_filePaths = FilePaths.getUniqueInstance();
		
		_brownCorpusFile = new File(_filePaths.getBrownCorpusLocation());
		if(_brownCorpusFile.isDirectory()){				//Checks whether _brownCorpusFile is a directory
			_dataFiles = _brownCorpusFile.listFiles();
		}
		else
			_dataFiles = new File[]{_brownCorpusFile};
		
		_wordTagMap = new HashMap<String, double[]>();
		_piCount = new ArrayList<String>();
		_aCount = new ArrayList<String>();
		_words = new ArrayList<String>();
		
	}
	
	/**
	 * This methods encapsulates the mechanism to read from Brown Corpus, accumulating the count of tag occurrence 
	 * for each unique words, normalising the 
	 */
	public void read(boolean flags){
		String dataLine;							//Line of data in a file
		StringTokenizer aStringTokenizer;			//Tokenize string into token with space as a delim
		String token = null; 						//a token from aStringTokenizer
		String[] tokenPair; 						//break the token into two which contains a word and its POS tag
		int wordCount = 0;							//Number of words in a data line
		int fileCount = 0;							//Keeps the count of files this method processing
		int numPennTags = PennTags.values().length;	//Number of tags available in the PennTag enum
		_flag = flags;
		
		for(File aDataFile : _dataFiles){			//Loops through all files in _dataFiles
			try {
				_reader = new BufferedReader(new InputStreamReader(new FileInputStream(aDataFile)));
				while((dataLine = _reader.readLine()) != null){
					wordCount = 0;
					if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
						continue;
					_previousePenTag = null;
					aStringTokenizer = new StringTokenizer(dataLine); 				//Tokenize the string by space dilim
					
					while(aStringTokenizer.hasMoreTokens()){
						token = aStringTokenizer.nextToken();
						tokenPair = token.split("/");								//Split the String by / delim because a token represent like word/POS
						stat("Currently Reading: " + token);
						String[] tempTokenPair = tokenPair[1].split("-");			//Split the String by - delim because a token represent like word/POS
						if(tempTokenPair.length == 2)								//Check whether tempTokenPair contains 2 tokens
							tokenPair[1] = tempTokenPair[0];
						if(tokenPair.length != 2)									//Check whether the token is further tokenized into 2 tokens
							continue;
						
						_pennTags = PennTags.getPennTag(tokenPair[1].toUpperCase());	//Get the corresponding Penn Tag token for Brown Tag
						
						if(_pennTags == PennTags.OTHER)								//If penn tag is not matched with BrownTag
							continue;
						
						if(!_wordTagMap.containsKey(tokenPair[0])){					//Checks whether the currently reading word from Brown Corpus is already in HashMap
							_posProbability = new double[PennTags.values().length];	
							for(int i = 0; i < _posProbability.length; i++)
								_posProbability[i] = new Double(0.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
							_wordTagMap.put(tokenPair[0], _posProbability);			//Put the Brown Corpus word and its occurrence into the HashMap
						}
						_posProbability = _wordTagMap.get(tokenPair[0]);			//Get the probability of the current word being read
						_posProbability[_pennTags.ordinal()] += 1.0D;				//Get the position in the currently reading Penn tag from its enum declaration
						_wordTagMap.put(tokenPair[0], _posProbability);				//Put the Brown Corpus word and its occurrence into the HashMap
						
						if(wordCount == 0)
							_piCount.add(_pennTags.getValue());
						else
							_aCount.add(_previousePenTag.getValue() + ":" + _pennTags.getValue());
						
						_previousePenTag = _pennTags;
						
						wordCount++;
					}
				}
				
			}catch (IOException e){
				System.err.println("Reading from: " + aDataFile.getName() + " is failed.");
			}catch (NullPointerException e){
				stat("ERROR: Either Tag Relation or Brown Corpus location or both are invalid");
			}
			
			fileCount ++;					//Increment the number of files processed so far
		
			try {
				_reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e){
				
			}
		}		
		stat("Reading is Completed...");
		stat("Computing Pi...");
		/*
		 * Compute _pi
		 * Calculate the number of occurrence of each Penn Tag in the Brown Corpus as the starting tag in a sentence
		 */
		_pi = new double[numPennTags][1];
		for(int i = 0; i < numPennTags; i++){
			int numberOfOccurence = 1;
			for(int j = 0; j < _piCount.size(); j++){
				if(_piCount.get(j).equals((PennTags.values()[i]).getValue())){
					numberOfOccurence++;
				}
			}
			_pi[i][0] = numberOfOccurence;
		}
		stat("Normalizing Pi...");
		
		_pi = scalarMultiplication(_pi, 1 / norm(_pi));
	
		stat("Computing Transition Probability...");
		
		/*
		 * Compute _a
		 * This computes the occurrence of a pair of tags (i.e. VV:VBZ) in the brown corpus
		 */
		_a = new double[numPennTags][numPennTags];
		for(int i = 0; i < numPennTags; i++){
			int numberOfOccurence = 0;
			for(int j = 0; j < numPennTags; j++){
				for(int k = 0; k < _aCount.size(); k++){
					if(_aCount.get(k).equals(PennTags.values()[i].getValue() + ":" + PennTags.values()[j].getValue())){
						numberOfOccurence++;
					}
				}
				_a[i][j] = numberOfOccurence + 1;			//Add-one smoothing
			}
		}
		stat("Computing Emission/Observed Probability...");
		
		/*
		 * Compute _b
		 * Map each words occurrence in the Brown corpus according to the Penn Tags
		 */
		int numOfWords = _wordTagMap.size();						//Total number of unique words in the Brown Corpus
		_words.addAll(_wordTagMap.keySet());						//Add each those words from the HashMap to ArrayList
		_b = new double[numPennTags][numOfWords];					
		for(int i = 0; i < numPennTags; i++){
			for(int j = 0; j < numOfWords; j++){
				String word = _words.get(j);
				_b[i][j] = _wordTagMap.get(word)[i] + 1;	//Add-one smoothing
			}
		}
		stat("Normalizing Transition and Emission Probabilities...");
		
		/*
		 * Vector _a and _b normalisation.
		 * A vector of arbitrary length can be divided by its length to create a unit vector
		 * each row's sum is 1 thus row = column + column + ... = 1
		 */
		for(int i = 0; i < numPennTags; i++){
			double rowSumA = 0;
			for(int j = 0; j < numPennTags; j++){
				rowSumA += _a[i][j];
			}
			for(int j = 0; j < numPennTags; j++){
				_a[i][j] = _a[i][j]/rowSumA;
			}
			
			double rowSumB = 0;
			for(int j = 0; j < numOfWords; j++){
				rowSumB += _b[i][j];
			}
			for(int j = 0; j < numOfWords; j++){
				_b[i][j] = _b[i][j]/rowSumB;
			}
		}
		stat("Operation Completed...");
	}
	
	/**
	 * Save all calculated probabilities in seperate files
	 */
	public void saveProbabilities(){
		saveStatPi();
		saveStatA();
		saveStatB();
		saveBrownCorpusWords();
	}
	
	/**
	 * Serialise all probability vectors as persistent objects for later use
	 */
	public void serializeProbabilities(){
		stat("Serializing Transition Probabilities...");
		_serializer.serializedA(_filePaths.getSaveA(), _a);
		stat("Serializing Pi Probabilities...");
		_serializer.serializedPi(_filePaths.getSavePi(), _pi);
		stat("Serializing Emission/Observation Probabilities...");
		_serializer.serializedB(_filePaths.getSaveB(), _b);
		stat("Serializing Unique Words...");
		_serializer.serializedWords(_filePaths.getBrownWordsSerialized(), (ArrayList<String>) _words);
	}
	
	/**
	 * Saves the transition probability in a text file in a table format
	 */
	private void saveStatA(){
		stat("Saving Tansition Probability into text file...");
		int numPennTags = PennTags.values().length;	//Number of tags available in the PennTag enum

		try{
			FileWriter writer = new FileWriter(_filePaths.getSaveStatA());
			
			for(int i = 0; i < numPennTags; i++){
				for(int j = 0; j < numPennTags; j++){
					writer.write(Double.toString(_a[i][j]));
					writer.write("\t");
				}
				writer.write("\n");
				writer.flush();
			}			
			writer.close();
		}catch (IOException e){
			System.out.println("Saving failed: " + _filePaths.getSaveStatA());
		}
	}
	
	/**
	 * Saves the start probability in a file each in a new line
	 */
	private void saveStatPi(){
		stat("Saving Pi Probability into text file...");
		int numPennTags = PennTags.values().length;	//Number of tags available in the PennTag enum

		try{
			FileWriter writer = new FileWriter(_filePaths.getSaveStatPi());
			
			for(int i = 0; i < numPennTags; i++){
				writer.write(Double.toString(_pi[i][0]));
				writer.write("\t");
				writer.write("\n");
				writer.flush();
			}		
			writer.close();
		}catch (IOException e){
			System.out.println("Saving failed: " + _filePaths.getSaveStatPi());
		}
	}
	
	/**
	 * Saves the emission probability in a text file in a table format
	 */
	private void saveStatB(){
		stat("Saving Emission/Observation Probability into text file...");
		int numPennTags = PennTags.values().length;	//Number of tags available in the PennTag enum
		int numOfWords = _wordTagMap.size();						//Total number of unique words in the Brown Corpus
		
		try{
			FileWriter writer = new FileWriter(_filePaths.getSaveStatB());			
			for(int i = 0; i < numPennTags; i++){
				for(int j = 0; j < numOfWords; j++){
					writer.write(Double.toString(_b[i][j]));
					writer.write("\t");
				}
				writer.write("\n");
				writer.flush();
			}		
			writer.close();
		}catch (IOException e){
			System.out.println("Saving failed: " + _filePaths.getSaveStatB());
		}
	}
	
	/**
	 * This method saves each unique word found in Brown Corpus into a file as a new line
	 */
	private void saveBrownCorpusWords(){
		stat("Saving uniques words into text file...");
		
		try {
			FileWriter writer = new FileWriter(_filePaths.getBrownWords());
			for(String word : _words){
				writer.write(word + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e){
			System.out.println("Saving failed: " + _filePaths.getBrownWords());
		}
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
	
	public static void main(String[] args){
		BrownCorpusReader b = new BrownCorpusReader();
		b.read(false);
		b.serializeProbabilities();
		b.saveProbabilities();
	}
	
	private void stat(String message){
		if(_flag){
			_normalizer.setProgressInfo(message);
		}
		else{
			System.out.println(message);
		}
	}
}
///:~