//:org.azeem.vectorSpace/vectorSpace.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This encapsulates the mechanism to retrieve information from documents by representing
 * them as vectors of features.
 */
package edu.azeem.vectorSpace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import edu.azeem.server.FilePaths;
import edu.azeem.server.Generation;

public class VectorSpaceModel extends Generation{

	private File _dataLocation;			//An abstract representation of file and directory pathnames
	private File _mostReleventFile;		//The most relevant file
	private File[] _dataFiles;			//stores array of abstract pathnames denoting the files
	private FilePaths _filePaths;
	private BufferedReader _reader;		//Provides efficient reading of texts from a character input stream
	
	private ArrayList<String> _humanQuery;
	private HashMap<String, double[]> _wordOccurence;
	
	private double[][] _pi;
	private double _maxTf;		//Most frequent term in a document
	private double[][] _tf;		//Term Frequency within a single document
	private int _N;				//Total number of documents in the collection
	private int _n;				//number of documents in which term i occurs
	
	/**
	 * The weight of term i in the vector for document j is the product of its
	 * overall frequency in j with the log its inverse document frequency in the
	 * collection. (Jurafsky and Martin, 2000, p.680)
	 */
	//private double _weight;		// _tf x _idf
	
	public VectorSpaceModel(ArrayList<String> humanQuery){
		//double[] occuranceFrequency;
		_filePaths = FilePaths.getUniqueInstance();
		_dataLocation = new File(_filePaths.getWebContentLocationData());
		if(_dataLocation.isDirectory()){				//Checks whether _brownCorpusFile is a directory
			_dataFiles = _dataLocation.listFiles();
		}
		else
			_dataFiles = new File[]{_dataLocation}; 
		
		//XXX System.out.println("_dataFiles: " + _dataFiles.length);
		_wordOccurence = new HashMap<String, double[]>();
		_humanQuery = humanQuery;
		_N = _dataFiles.length;
	}

	private void read(){
		String dataLine;							//Line of data in a file
		String token;
		ArrayList<String> termsInLine;
		StringTokenizer aStringTokenizer;
		double[] occuranceFrequency;
		int count = 0;
		
		for(File aDataFile : _dataFiles){			//Loops through all files in _dataFiles
			try {
				_reader = new BufferedReader(new InputStreamReader(new FileInputStream(aDataFile)));
				while((dataLine = _reader.readLine()) != null){
					if(dataLine.isEmpty())				//If dataLine is empty then start the loop with the next line
						continue;
					aStringTokenizer = new StringTokenizer(dataLine);
					
					occuranceFrequency = new double[_dataFiles.length];
					for(int j = 0; j < _dataFiles.length; j++){
						occuranceFrequency[j] = new Double(0.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
					}
					
					termsInLine = new ArrayList<String>();
					
					while(aStringTokenizer.hasMoreTokens()){
						token = aStringTokenizer.nextToken();
						termsInLine.add(token);
					}
					
					for(int i = 0; i < termsInLine.size(); i++){
						if (_humanQuery.contains(termsInLine.get(i))){
							if(!_wordOccurence.containsKey(termsInLine.get(i))){
								_wordOccurence.put(termsInLine.get(i), occuranceFrequency);
							}
							else {
								occuranceFrequency = _wordOccurence.get(termsInLine.get(i));
								occuranceFrequency[count] += 1.0D;
							}
						}
					}
				}
				_reader.close();
				count++;
				
			} catch (FileNotFoundException e) {
				System.err.println("File is missing: " + aDataFile.getName());
			} catch (IOException e) {
				System.err.println("Reading from: " + aDataFile.getName() + " is failed.");
			}
		}
	}
	
	/**
	 * This method converts the raw frequency terms into weights
	 */
	private void convertToWeights(){
		double[] temp;
		double[] maxTf;
		
		maxTf = new double[_dataFiles.length];
		_tf = new double[_humanQuery.size()][_dataFiles.length];		
		_pi = new double[_humanQuery.size()][_dataFiles.length];
		
		for(int j = 0; j < _dataFiles.length; j++){
			maxTf[j] = new Double(0.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
		}
		
		/*
		 * This portion finds the most frequent term in each document
		 */
		for(int i = 0; i < _wordOccurence.size(); i++){
			temp = _wordOccurence.get(_humanQuery.get(i));
			for(int j = 0; j < temp.length; j++){
				if(temp[j] > maxTf[j]){
					maxTf[j] = temp[j];
				}
			}
		}
		
		/*
		 * This portion of code updates term-by-document matrix
		 * with weights
		 */
		for(int i = 0; i < _wordOccurence.size(); i++){
			temp = _wordOccurence.get(_humanQuery.get(i));
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
	
	private void mostReleventWebPage(){
		int selectedWebContentCount = 0;
		double[] totalWeight = new double[_dataFiles.length];
		double max = 0;
		
		for(int i = 0; i < _dataFiles.length; i++){
			totalWeight[i] = new Double(0.0D);				//Set the count of occurrence to zero of the particular word from brown corpus in terms of Penn Tags
		}
		for(int i = 0; i < _pi.length; i++){
			for(int j = 0; j <_pi[i].length; j++){
				totalWeight[j] += _pi[i][j];
			}
		}
		
		for(int i = 0; i < totalWeight.length; i++){
			if(max <= totalWeight[i]){
				max = totalWeight[i];
				selectedWebContentCount = i;
			}
		}
		
		_mostReleventFile = _dataFiles[selectedWebContentCount].getAbsoluteFile();
		//XXX SortingAlgorithm quickSort = new QuickSort(totalWeight);
		//XXX quickSort.sort();
		//XXX quickSort.print(quickSort.isSorted(), totalWeight);
		System.out.println(_mostReleventFile.getName());
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
	protected double calculateIdf(int n){
		return Math.log1p(_N/n);
	}
	
	/**
	 * This method calculates the weight of a term in the vector for document j
	 * @param idf
	 * @param tf
	 * @return double
	 */
	protected double termWeight(double idf, double tf){
		return ((0.5 + (0.5*tf/_maxTf))*idf);
	}
	
	/**
	 * Assigns a strictly positive length or size to all vectors in a vector space
	 * @param array
	 * @return maximum positive column length
	 */
	protected double norm(double[][] array){

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

	@Override
	public void handle() {
		read();
		convertToWeights();
		normalizeVector();
		mostReleventWebPage();
		print();
	}

	@Override
	public void print() {
		/*XXX
		double[] temp;
		for(int i = 0; i < _humanQuery.size(); i++){
			temp = _wordOccurence.get(_humanQuery.get(i));
			for(int j = 0; j < temp.length; j++){
				System.out.print("\t" + temp[j]);
			}
			System.out.println();
		}
		
		for(int i = 0; i < _tf.length; i++){
			for(int j = 0; j < _tf[i].length; j++){
				System.out.print("\t" + _tf[i][j]);
			}
			System.out.println();
		}
		
		for(int i = 0; i < _pi.length; i++){
			for(int j = 0; j < _pi[i].length; j++){
				System.out.print("\t" + _pi[i][j]);
			}
			System.out.println();
		}*/
		
		System.out.println("Vector Space Model is completed... ");
	}

	@Override
	public Object output() {
		return _mostReleventFile;
	}

	@Override
	public String get() {
		throw new AbstractMethodError();
	}
	
	public static void main(String[] args){
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("how");
		arrayList.add("to");
		arrayList.add("initialize");
		arrayList.add("int");
		
		VectorSpaceModel vsm = new VectorSpaceModel(arrayList);
		vsm.handle();
	}
}
///:~