//:org.azeem.tokenization/Tokenizer.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds the computation mechanism of finite state machine implementations 
 * which gets Natural language and tokenize them while considering important punctuations. Two Finite State 
 * Automatas (Punctuations and Abbreviation] are cascaded with the possible states that a token can be 
 * and the output is achieved.
 */
package edu.azeem.tokenization;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.azeem.client.Analysis;
import edu.azeem.server.Understanding;

public class Tokenizer extends Understanding{
	
	private String _humanQuery;								//Human query
	private StringTokenizer _stringTokenizer;				//Contains tokenized human query prior to evaluation using FSM
	private StringTokenizer _tempStringTokenizer;			//Temporary _stringTokenizer
	private static State _state = State.INITIAL;			//Default state of the FSM
	private static ArrayList<String> _tokenList;			//Contains tokenized human query after evaluating through FSM
	private static Pattern _pattern;						//This stores an instance of Pattern class, that is a Singleton
	private static Matcher _matcher;						//THis stores an instance of Matcher class, that is a Singleton
	private static String punctuationValue;					//The current punctuationValue that's being referred
	private static int count = 0;							//The count of the current position in the Punctuation FSA
	private static boolean patternIsFound = false;			//True if specified pattern is found in a token
	private final static int COUNT = Punctuations.values().length - 1;	//Stores the length of Punctuation FSA

	/**
	 * Machine moves to this state as soon as the transition between States are over
	 * @author Azeem
	 */
	private enum StateDuration{
		TRANSIENT
	}
	
	/**
	 * This enum State has the encapsulated logic to tookenize each word according to rules
	 * @author Azeem
	 */
	private enum State{
		
		/**
		 * INITIAL State holds the logic of determining which Punctuation belongs to which State
		 */
		INITIAL {
			public void next(String token) {
				_state.setTransient(false);
				patternIsFound = false;
				String regex = null;
				
				if(count > COUNT){
					_state = State.TERMINATE;
				}
				punctuationValue = Punctuations.values()[count].getValue();
				
				switch(Category.categorize(Punctuations.values()[count])){
					case BACK_REFERENCES:
						regex = "\\" + punctuationValue;
						_pattern = Pattern.compile(regex);
						break;
						
					case NON_BACK_REFERENCES:
						regex = punctuationValue;
						_pattern = Pattern.compile(regex);
						break;
					
					case SINGLE_BACK_REFERENCES:
						regex = "\"";
						_pattern = Pattern.compile(regex);
						break;
						
					case SPECIAL_CASE_BACKWARD_SLASH:
						regex = "\\\\";
						_pattern = Pattern.compile(regex);
						break;
						
					case OPEN:
						regex = "\\" + punctuationValue;
						_pattern = Pattern.compile(regex);
						break;
						
					case CLOSE:
						regex = "\\" + punctuationValue;
						_pattern = Pattern.compile(regex);
						break;
						
					default:
						break;
				}
				_matcher = _pattern.matcher(token);
				
				if(_matcher.find()){
					//if token has a Period
					if(_matcher.group().equals(Punctuations.PERIOD.getValue())){
						_state = State.CURRENCY_STATE;
					}
					//if a token has either of (, {, [, ], }, ) brackets or "
					else if(_matcher.group().equals(Punctuations.OPEN_BRACKET.getValue())
							|| _matcher.group().equals(Punctuations.OPEN_PARANTHESE.getValue())
							|| _matcher.group().equals(Punctuations.OPEN_CURLY_BRACKETS.getValue())
							|| _matcher.group().equals(Punctuations.CLOSE_BRACKET.getValue())
							|| _matcher.group().equals(Punctuations.CLOSE_PARANTHESE.getValue())
							|| _matcher.group().equals(Punctuations.CLOSE_CURLY_BRACKETS.getValue())
							|| _matcher.group().equals(Punctuations.QUATATION.getValue())){
						_state = State.BRACKET_STATE;			//Set the State to TERMINATE
					}
					//if a token has a APOSTROPHE
					else if(_matcher.group().equals(Punctuations.APOSTROPHE.getValue())){
						_state = State.APOSTROPHE_STATE;
					}
					//token has a punctuation that has no ambiguity usage
					else{
						String[] temp = token.split(regex);
						if(temp.length != 0){
							_tokenList.add(temp[0]);
							_tokenList.add(_matcher.group());
						}
						if(temp.length == 2){
	                		_tokenList.add(temp[1]);
	                	}
		                count = 0;
		                _state = State.TERMINATE;
					}
				}
				//Token doesn't have any punctuations
				else{
					count++;
					if(count > COUNT)
						_tokenList.add(token);					//Add token to an ArrayList
					patternIsFound = false;						//No punctuation is found
				}
			}
		},
		
		/**
		 * This state has encapsulated the logic to deal with brackets if found in a token
		 */
		BRACKET_STATE{
			public void next(String token){
				boolean hasTwoBrackets = false;
				Punctuations[] regex = null;
				int i = 0;
				int currentBracketType = 0; 				//1 if this token has an open bracket. 2 if vice versa 
				switch(Category.categorize(Punctuations.values()[count])){
					case OPEN: 
						regex = Category.CLOSE.getValue();	//Load CLOSE types into regex
						currentBracketType = 1;				//Token has an open bracket
						break;
						
					case CLOSE:
						regex = Category.OPEN.getValue();	//Load CLOSE types into regex
						currentBracketType = 2;				//Token has a close bracket
						break;
					
					default:
						break;
				}
				
				if(regex != null){
					for(i = 0; i < regex.length; i++){
						/*Checks whether the token has another same type token. If the token already has a 
						 * [ then this portion check whether the same token has a close bracket in this case
						 * it is ] 
						 */
						_pattern = Pattern.compile("\\" + regex[i].getValue());	
						_matcher = _pattern.matcher(token);
						if(_matcher.find()){
							hasTwoBrackets = true;			//Token has two Punctuations 
							break;
						}
					}
				}
				
				String[] temp = token.split("\\" + punctuationValue);	//Breaks the token down into smaller tokens with the punctuationValue

				if(hasTwoBrackets){
                	_tokenList.add(punctuationValue);		//Add punctuationValue into the ArrayList
                	String[] temp1 = temp[1].split("\\" + regex[i].getValue());
                	if(temp1.length != 0){
                		_tokenList.add(temp1[0]);				//Add the word stem in the ArrayList
                		_tokenList.add(regex[i].getValue());	//Add the next Punctuations
                	}
                	count = 0;
	                _state = State.TERMINATE;				//Set the State to TERMINATE
				}
				else{
					//Token has only an OPEN punctuation
					if(currentBracketType == 1){
	                	_tokenList.add(punctuationValue);	//Add punctuationValue into the ArrayList
	                	if(temp.length == 2){
	                		_tokenList.add(temp[1]);			//Add the word stem in the ArrayList
	                	}
	                }
					//Token has only a CLOSE punctuation
					else if(currentBracketType == 2){
						try{
							_tokenList.add(temp[0]);			//Add the word stem in the ArrayList
							_tokenList.add(punctuationValue);	//Add punctuationValue into the ArrayList
						}catch(ArrayIndexOutOfBoundsException ex){}
					}
	                count = 0;
	                _state = State.TERMINATE;				//Set the State to TERMINATE
				}
			}
		},
		
		/**
		 * This state has encapsulated the logic to check whether the token is a Currency
		 */
		CURRENCY_STATE{
			public void next(String token){
				String regex = "^\\$?(\\d|-)?(\\d|,)*\\.?\\d*$"; 	/*This regular expression checks 
				whether a token is a number, with(out) dollar sign, with(out) cents, and with(out) commas*/
				_pattern = Pattern.compile(regex);
				_matcher = _pattern.matcher(token);
				if(_matcher.find()){
					_tokenList.add(token);
					count = 0;
					_state = State.TERMINATE;				//Set the State to TERMINATE
				}
				else{
	                _state = State.ABBREVIATION_PERIODS;	/*Token is not a Currency value so Set 
	                										the State to ABBREVIATION_PERIODS*/
				}
			}
		}, 
		
		/**
		 * This state determines which Abbreviation type does the token is
		 */
		ABBREVIATION_PERIODS{
			public void next(String token){
				int flag = 0;								//keeps the count of Abbreviations
				for(int i = 0; i < Abbreviations.values().length; i++){
					//Check a particular Abbreviation matches the token
					if(token.toLowerCase().equals(Abbreviations.abbreviates(Abbreviations.values()[i]))){
						_tokenList.add(token);
						count = 0;
						_state = State.TERMINATE;			//Set the State to TERMINATE
						break;
					}
					flag++;
					
					if(flag == Abbreviations.values().length){
						_state = State.END_OF_LINE_STATE;	/*If the token is not a Abbreviation it is 
															passed to the END_OF_LINE_STATE state*/
						break;
					}
				}
			}
		},
		
		/**
		 * This state assumes that the token's period indicates as a full stop 
		 */
		END_OF_LINE_STATE{
			public void next(String token){				
				String[] temp = token.split("\\.");
				if(temp.length != 0){
					_tokenList.add(temp[0]);
            		_tokenList.add(".");
				}
                count = 0;
                _state = State.TERMINATE;					//Set the State to TERMINATE
			}
		},
		
		/**
		 * This state corresponds the mechanism of handling APOSTROPHE's in a proper manner
		 */
		APOSTROPHE_STATE{
			public void next(String token){
				String[] temp = token.split("'");
				if(temp.length >= 1)
					_tokenList.add(temp[0]);
				/*if(temp.length == 2){
					_tokenList.add("'" + temp[1]);
				}*/
				count = 0;
                _state = State.TERMINATE;					//Set the State to TERMINATE
			}
		},
		
		/**
		 * This state corresponds that the State is now Terminated that the token is determined
		 * as containing a punctuation or abbreviation and is now in Transient state
		 */
		TERMINATE(StateDuration.TRANSIENT){
			public void next(String token){
				patternIsFound = true;
				//XXX System.out.println("Tokenization Done!");
			}
		};
		
		@SuppressWarnings("unused")
		private boolean isTransient = false;				//Set if the Finite State Transducer falls in to Transient state
		
		State(){}
		State(StateDuration trans){ 
			setTransient(true); 
		}
		
		public void next(String token){
			throw new RuntimeException("Only call next(String token) for " + "non-transient states");
		}
		
		public void setTransient(boolean isTransient) {
			this.isTransient = isTransient;			
		}		
	}
	
	/**
	 * This private method will control the State machine by passing one token at a time to the FST
	 */
	private void trigger(){
		String tempToken;
		while(_tempStringTokenizer.hasMoreTokens()){
			_state = State.INITIAL;							//Set the initial State of the machine to INITIAL
			tempToken = _tempStringTokenizer.nextToken();
			//XXX System.out.println("NEXT TOKEN: " + tempToken);
			while(count <= COUNT){
				_state.next(tempToken);
				if(patternIsFound)
					break;
			}
			count = 0;
		}
	}
	
	/**
	 * Public Constructor
	 * @param humanQuery
	 */
	public Tokenizer(String humanQuery){
		this._humanQuery = humanQuery;						//Input to the tokenizer
		_tokenList = new ArrayList<String>();				//ArrayList which will contain tokenized human query
	}


	/**
	 * This method will be called by the controller of the Chatterbot to get the trigger the tokenization process
	 * @return _tokenList
	 */
	@Override
	public void handle(){
		/*
		 * _humanQuery will be tokenized into token using StringTokenizer class that divides 
		 * the Strings in to tokens by considering the <space> delim
		 */
		_stringTokenizer = new StringTokenizer(_humanQuery);
		_tempStringTokenizer = _stringTokenizer;
		trigger();											//trigger the tokenization process
	}
	
	/**
	 * This object returns the output of the Tokenization process
	 * @return ArrayList
	 */
	@Override
	public Object output(){
		return _tokenList;
	}
	
	/**
	 * Print tokens in the console for verification
	 */
	@Override
	public void print(){
		String x = "";
		System.out.println("Tokenized Human Query: ");
		for(int i = 0; i < _tokenList.size(); i++){
			x += " | " + _tokenList.get(i);
			System.out.print(" | " + _tokenList.get(i));
		}
		System.out.println("\n");
		Analysis.getUniqueInstance().setTokenization(x);
	}
	
	/**
	 * @return the output of this component as a String
	 */
	@Override
	public String get() {
		String temp = "";
		for(int i = 0; i < _tokenList.size(); i++){
			temp += " | " + _tokenList.get(i);
		}
		return temp;
	}
	
	/**
	 * This main thread can be used to demonstrates how this component works
	 * @param args
	 */
	public static void main(String args[]){
		String s = "Mr. Smith $100.00 a [laptop fgdf] worth, after: bidding, on Johns offer.";
		Tokenizer t = new Tokenizer(s);
		t.handle();
		t.print();
	}
}
///:~