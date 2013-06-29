//:org.azeem.posTagging/Hmm.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information from Online Repositories 
 * @description This class implements forward algorithm as well as Viterbi algorithm to tag human queries
 */
package edu.azeem.posTagging;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

import edu.azeem.client.Analysis;
import edu.azeem.server.FilePaths;
import edu.azeem.server.Understanding;
import edu.azeem.tokenization.Tokenizer;

public class Hmm extends Understanding{
 
	private RuleBasedTagger _ruleBasedTagger;
	private FilePaths _filePaths;
	private Serializer _serializer;
	private AbstractList<String> _words;						//List of unique Brown Tag words
	private AbstractList<String> _tokenizedHumanQuery;			//The tokenized human query
	private ArrayList<Double> _alphaProbability;
	private ArrayList<Double> _deltaProbability;
	private ArrayList<Integer> _mostLikelyTags;
	private ArrayList<String> _taggedTokens;
	private String[][] _wordsAndtags;
 	
	private HashMap<Integer, ArrayList<Double>> _forward;			//keeps the partial probabilities during the forward algorithm
	private HashMap<Integer, ArrayList<Double>> _viterbi;
	
	private double[][] _pi;						//Initial Probability of Penn Tags as the starting word in a sentence
 	private double[][] _a;						//Transition Probability: The probabilities of the machine moving from one state to another
 	private double[][] _b;						//Emmision or Observation probability: of observing observing a particular observable state given that the hidden model is in a particular hidden state
	private double _forwardProbability;			//probability of an observed sequence given a HMM
 	private double _alpha;						//Partial probability
 	private double _delta;
 	
 	private int _numberOfTokens;				//Number of tokens in _tokenizedHumanQuery
 	//private boolean _flag = false;				//Set this to True if a token doesn't have a corresponding tag in the data
 	private final int START_STATE = 0;
 	
	private int _numPennTags = PennTags.values().length;	//Number of tags available in the PennTag enum

	public Hmm(ArrayList<String> humanQuery){
		_serializer = Serializer.getUniqueInstance();
		_filePaths = FilePaths.getUniqueInstance();
		
		_tokenizedHumanQuery = humanQuery;
		deserializeProbabilities();
		_numberOfTokens = _tokenizedHumanQuery.size();
		_forward = new HashMap<Integer, ArrayList<Double>>();
		_viterbi = new HashMap<Integer, ArrayList<Double>>();
		_mostLikelyTags = new ArrayList<Integer>();
		_taggedTokens = new ArrayList<String>();
		_ruleBasedTagger = new RuleBasedTagger();
	}	
	
	@Override
	public void handle() {
		//evaluate(START_STATE);
		decode(START_STATE);
		tagSentence();
		print();
	}
	
	protected void evaluate(int t){

		int position = 0; 			//Position of a token in the _words vector
		ArrayList<Double> tempAlphaProbability = new ArrayList<Double>();
		String token = null;
		if(t < _tokenizedHumanQuery.size()){
			token = _tokenizedHumanQuery.get(t);
		}
		position = _words.lastIndexOf(token);		
		_alphaProbability = new ArrayList<Double>();
		//System.out.println("POSITION: " + token + "\t"+ position);
		if(t == 0){
			for(int i = 0; i < _numPennTags; i++){
				_alpha = _pi[i][0] * _b[i][position];
				_alphaProbability.add(_alpha);
				//System.out.println(_alpha);
			}
			_forward.put(t, _alphaProbability);
			evaluate(t+1);
		}
		else if (t < _numberOfTokens){
			tempAlphaProbability = _forward.get(t-1);
			for(int i = 0; i < _numPennTags; i++){
				for(int j = 0; j < _numPennTags; j++){
					_alpha += ((double)tempAlphaProbability.get(j) * (double)_a[i][j]);
					//System.out.println(i + "\t" + j + "\t" + _alpha);
				}
				System.out.println("position: " + position);
				_alpha *= _b[i][position];
				_alphaProbability.add(_alpha);
			}
			_forward.put(t, _alphaProbability);
			if(t == _numberOfTokens - 1){
				for(int i = 0; i < _alphaProbability.size(); i++){
					_forwardProbability += _alphaProbability.get(i);
				}
			}
			evaluate(t+1);
		}
	}
	
	protected void decode(int t){
		int position = 0; 			//Position of a token in the _words vector
		ArrayList<Double> tempDeltaProbability = new ArrayList<Double>();
		String token = null;
		int mostLikelyTag = 0;
		double innerMax = 0.0d;
		double outerMax = 0.0d;
		
		if(t < _tokenizedHumanQuery.size()){
			token = _tokenizedHumanQuery.get(t);
		}
		position = _words.lastIndexOf(token);		
		_deltaProbability = new ArrayList<Double>();
		if(t == 0){
			
			for(int i = 0; i < _numPennTags; i++){
				try{
					_delta = _pi[i][0] * _b[i][position];
					_deltaProbability.add(_delta);
					//System.out.println(_delta);
				}catch(ArrayIndexOutOfBoundsException ex){
					
				}
				if(position < 0){
					mostLikelyTag = PennTags.values().length - 1;
					_deltaProbability.add(_delta);
					continue;
				}
				if(_delta >= innerMax){
					innerMax = _delta;
					mostLikelyTag = i;
				}
			}
			_mostLikelyTags.add(mostLikelyTag);
			_viterbi.put(t, _deltaProbability);
			decode(t+1);
		}
		else if (t < _numberOfTokens){
			tempDeltaProbability = _viterbi.get(t-1);
			for(int i = 0; i < _numPennTags; i++){
				for(int j = 0; j < _numPennTags; j++){
					_delta = (((double)tempDeltaProbability.get(j) * (double)_a[i][j]));
					if(_delta >= innerMax){
						innerMax = _delta;
					}						
				}
				if(position < 0){
					mostLikelyTag = PennTags.values().length - 1;
					_deltaProbability.add(_delta);
					continue;
				}
				_delta = innerMax * _b[i][position];
				if(_delta >= outerMax){
					outerMax = _delta;
					mostLikelyTag = i;
				}
				_deltaProbability.add(_delta);
				//System.out.println(mostLikelyTag);
			}
			//System.out.println(t + "\t" + _delta);
			_mostLikelyTags.add(mostLikelyTag);
			_viterbi.put(t, _deltaProbability);
			decode(t+1);
		}
	}
	
	/**
	 * This method tags the sentence 
	 */
	private void tagSentence(){ //Sentence 
		String token;
		String tag;
		_wordsAndtags = new String[_mostLikelyTags.size()][2];
		
		for(int i = 0; i < _mostLikelyTags.size(); i++){
			token = _tokenizedHumanQuery.get(i);
			//mostLikelyTag = _mostLikelyTags.get(i);			
			tag = PennTags.values()[_mostLikelyTags.get(i)].getValue().toString();
			_taggedTokens.add(token + "/" + tag);
			_wordsAndtags[i][0] = token;
			_wordsAndtags[i][1] = tag;
		}
		//_ruleBasedTagger = new RuleBasedTagger();
		_wordsAndtags = _ruleBasedTagger.transformTags(_wordsAndtags);
		
		
		//_ruleBasedTagger = new RuleBasedTagger();
		_taggedTokens = _ruleBasedTagger.tagUnkownTokens(_wordsAndtags);
		convertArrayToArrayList();
	}
	
	private void convertArrayToArrayList(){
		String taggedToken;
		String[] temp;
		_wordsAndtags = new String[_taggedTokens.size()][2];
		
		for(int i = 0; i < _taggedTokens.size(); i++){
			taggedToken = _taggedTokens.get(i);
			temp = taggedToken.split("/");
			_wordsAndtags[i][0] = temp[0];
			_wordsAndtags[i][1] = temp[1];
		}
	}
	
	/**
	 * This method retrieve existing probability data from disk
	 */
	private void deserializeProbabilities(){
		_serializer.deserializedA(_filePaths.getSaveA());
		_serializer.deserializedB(_filePaths.getSaveB());
		_serializer.deserializedPi(_filePaths.getSavePi());
		_serializer.deserializedWords(_filePaths.getBrownWordsSerialized());		
		_a = _serializer.getA();
		_b = _serializer.getB();
		_pi = _serializer.getPi();
		_words = _serializer.getWords();
	}

	/**
	 * Print tagged tokens in the console for verification
	 */
	@Override
	public void print() {
		String x = "";
		System.out.println("Tagged Human Query: ");
		for(int i = 0; i < _tokenizedHumanQuery.size(); i++){
			x += _taggedTokens.get(i) + " ";
			System.out.print(_taggedTokens.get(i) + " ");
		}
		System.out.println("\n");
		Analysis.getUniqueInstance().setPos(x);
	}

	/**
	 * @return the output of this component as an Object
	 */
	public Object output() {
		return _wordsAndtags;
	}

	@Override
	public String get() {
		String temp = "";
		for(int i = 0; i < _tokenizedHumanQuery.size(); i++){
			temp += _taggedTokens.get(i) + " ";
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		
		ArrayList<String> a = new ArrayList<String>();
		String x = "the city hurts a micross lot";
		Tokenizer t = new Tokenizer(x);
		t.handle();
		a = (ArrayList<String>) t.output();
		Hmm hmm = new Hmm(a);
		hmm.handle();
	}

}
///:~