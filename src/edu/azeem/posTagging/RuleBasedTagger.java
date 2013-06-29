//:org.azeem.posTagging/RuleBasedTagger.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information from Online Repositories 
 * @description This class has a finite state machine that handles tagging unkown words as weel as 
 * transforming tags to other tags..
 */
package edu.azeem.posTagging;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleBasedTagger {
	
	private ArrayList<String> _transformedTaggedOutput;
	private String[][] _wordsAndtags;
	private static String _currentTag;
	private static String _nextTag = "0";
	private static String _previousTag = "0";
	private static String _transformedTag;
	private static String _previousToken = null;
	private static State _state = State.INITIAL;			//Default state of the FSM
	private static Matcher _matcher;
	private static boolean _done = false;
	private static boolean _mode = false;					//If false: Transforming tags else tagging unknown words
	
	public RuleBasedTagger(){
		_mode = false;
	}
	
	/**
	 * Machine moves to this state as soon as the transition between States are over
	 * @author Azeem
	 */
	private enum StateDuration{
		TRANSIENT
	}
	
	/**
	 * This enum State has the 
	 * @author Azeem
	 */
	private enum State{
		
		/**
		 * Instantiates the state machine
		 */
		INITIAL{
			@Override
			public void next(String token){
				//System.out.println("INITIAL");
				_state.setTransient(false);
				_state = State.RULE1;
			}
		},
		
		/**
		 * Rule 1
		 * Does it has a suffix -s
		 */
		RULE1{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE1");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "s";				
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.NOUN_PLURAL.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE2;
				
			}
		},
		
		/**
		 * Rule 2
		 * Does it has a suffix -ed 
		 */
		RULE2{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE2");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ed";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.VERB_PAST_PARTICIPLE.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE3;
			}
		},
		
		/**
		 * Rule 3
		 * Does it has a suffix -ing
		 */
		RULE3{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE3");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ing";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.VERB_GERUND.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE4;
			}
		},
		
		/**
		 * Rule 4
		 * Does it has a suffix -ly 
		 */
		RULE4{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE4");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ly";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.ADVERB.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE5;
				
			}
		},
		
		/**
		 * Rule 5
		 * Does it has a suffix -al 
		 */
		RULE5{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE5");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "al";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.ADJECTIVE.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE6;
			}
		},
		
		/**
		 * Rule 6
		 * Does the previous token is equals to "would" 
		 */
		RULE6{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE6");
				if(_previousToken.equals("would")){
					_transformedTag = PennTags.VERB_BASE.getValue();
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE7;
			}
		},
		
		/**
		 * Rule 7
		 * Does the token has a numeric number
		 */
		RULE7{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE7");
				String tempToken = token;
				String[] temp;
				int count = 0;
				boolean tagFound = false;
				
				while(true){
					temp = tempToken.split(Integer.toString(count));
					_matcher = Pattern.compile(Integer.toString(count)).matcher(token);
					if(_matcher.find() && temp.length != 1){
						_transformedTag = PennTags.CARDINAL_NUMBER.getValue();
						tagFound = true;
						break;
					}
					if(count >= 9){
						break;
					}
					count++;
				}
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE8;
			}
		},
		
		/**
		 * Rule 8
		 * Does the previous token is equals to "be" 
		 */
		RULE8{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE8");
				if(_previousToken.equals("be")){
					_transformedTag = PennTags.ADJECTIVE.getValue();
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE9;
			}
		},
		
		/**
		 * Rule 9
		 * Does it has a suffix -us 
		 */
		RULE9{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE9");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "us";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){					
						_transformedTag = PennTags.ADJECTIVE.getValue();
						_state = State.TERMINATE;
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE10;
			}
		},
		
		/**
		 * Rule 10
		 * Does the previous token is equals to "it"  
		 */
		RULE10{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE10");
				if(_previousToken.equals("it")){
					_transformedTag = PennTags.VERB_3RD_PERSON_SINGULAR_PRESENT.getValue();
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE11;
			}
		},
		
		/**
		 * Rule 11
		 * Does it has a suffix -ble 
		 */
		RULE11{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE11");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ble";
				
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.ADJECTIVE.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE12;
			}
		},
		
		/**
		 * Rule 12
		 * Does it has a suffix -ic 
		 */
		RULE12{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE12");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ic";
				
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.ADJECTIVE.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE13;
			}
		},
		
		/**
		 * Rule 13
		 * Does it has a suffix -ss 
		 */
		RULE13{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE13");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ss";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.NOUN_SINGULAR.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE14;
			}
		},
		
		/**
		 * Rule 14
		 * Does it has a suffix -ive 
		 */
		RULE14{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE14");
				String tempToken = token;
				String[] temp;
				boolean tagFound = false;
				String regex = "ive";
				temp = tempToken.split(regex);
				_matcher = Pattern.compile(regex).matcher(token);
				if(temp.length == 1){
					while(_matcher.find()){
						_transformedTag = PennTags.ADJECTIVE.getValue();
						tagFound = true;
					}
				}
				
				if(tagFound){
					_state = State.TERMINATE;
				}
				else
					_state = State.RULE15;
			}
		},
		
		/**
		 * Rule 15
		 * If previous tag is "TO"
		 */
		RULE15{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE15");
				if(_previousTag.equals(PennTags.TO.getValue())){
					if(_currentTag.equals(PennTags.NOUN_SINGULAR.getValue())
							|| _currentTag.equals(PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.VERB_BASE.getValue();
						_state = State.TERMINATE;
						//System.out.println("TOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTOTO");
					}
					else
						_state = State.RULE16;
				}
				else
					_state = State.RULE16;
			}
		},
		
		/**
		 * Rule 15
		 * If previous tag is "PP"
		 */
		RULE16{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE16");
				if(_previousTag.equals(PennTags.PERSONAL_PRONOUN.getValue())){
					if(_currentTag.equals(PennTags.POSSESSIVE_ENDING.getValue())){
						_transformedTag = PennTags.VERB_3RD_PERSON_SINGULAR_PRESENT.getValue();
						_state = State.TERMINATE;
					}
					else if(_currentTag.equals(PennTags.VERB_PAST_PARTICIPLE.getValue())){
						_transformedTag = PennTags.VERB_PAST.getValue();
						_state = State.TERMINATE;
					}
					else if(_currentTag.equals(PennTags.VERB_BASE.getValue())){
						_transformedTag = PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue();
						_state = State.TERMINATE;
					}
					else if(_currentTag.equals(PennTags.NOUN_SINGULAR.getValue())){
						_transformedTag = PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE17;
				}
				else
					_state = State.RULE17;
			}
		},
		
		/**
		 * Rule 17
		 * If previous tag is "NNP"
		 */
		RULE17{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE17");
				if(_previousTag.equals(PennTags.PROPER_NOUN_SINGULAR.getValue())){
					if(_currentTag.equals(PennTags.VERB_PAST_PARTICIPLE.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.VERB_PAST.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE18;
				}
				else
					_state = State.RULE18;
			}
		},
		
		/**
		 * Rule 18
		 * If previous tag is "VBD"
		 */
		RULE18{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE18");
				if(_previousTag.equals(PennTags.VERB_PAST.getValue())){
					if(_currentTag.equals(PennTags.VERB_PAST.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.VERB_PAST_PARTICIPLE.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE19;
				}
				else
					_state = State.RULE19;
			}
		},
		
		/**
		 * Rule 19
		 * If previous tag is "NNS"
		 */
		RULE19{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE19");
				if(_previousTag.equals(PennTags.NOUN_PLURAL.getValue())){
					if(_currentTag.equals(PennTags.VERB_BASE.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.VERB_NON_3RD_PERSON_SINGULAR_PRESENT.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE20;
				}
				else
					_state = State.RULE20;
			}
		},

		/**
		 * Rule 20
		 * if next tag is "VBZ"
		 */
		RULE20{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE20");
				if(_nextTag.equals(PennTags.VERB_3RD_PERSON_SINGULAR_PRESENT.getValue())){
					if(_currentTag.equals(PennTags.PREPOSITION.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.WH_DETERMINER.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE21;
				}
				else
					_state = State.RULE21;
			}
		},
		
		/**
		 * Rule 21
		 * if next tag is "NN"
		 */
		RULE21{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE21");
				if(_nextTag.equals(PennTags.NOUN_SINGULAR.getValue())){
					if(_currentTag.equals(PennTags.PREPOSITION.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.DETERMINER.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE22;
				}
				else
					_state = State.RULE22;
			}
		},
		
		/**
		 * Rule 22
		 * if next tag is "NNP"
		 */
		RULE22{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE22");
				if(_nextTag.equals(PennTags.PROPER_NOUN_SINGULAR.getValue())){
					if(_currentTag.equals(PennTags.ADJECTIVE.getValue())){
						_transformedTag = PennTags.PROPER_NOUN_SINGULAR.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE23;
				}
				else
					_state = State.RULE23;
			}
		},
		
		/**
		 * Rule 23
		 * if next tag is "VBD"
		 */
		RULE23{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE23");
				if(_nextTag.equals(PennTags.VERB_PAST.getValue())){
					if(_currentTag.equals(PennTags.PREPOSITION.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.WH_DETERMINER.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE24;
				}
				else
					_state = State.RULE24;
			}
		},
		
		/**
		 * Rule 24
		 * if next tag is "JJ"
		 */
		RULE24{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE24");
				if(_nextTag.equals(PennTags.ADJECTIVE.getValue())){
					if(_currentTag.equals(PennTags.ADJECTIVE_COMPARATIVE.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.ADVERB_COMPARATIVE.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.RULE25;
				}
				else
					_state = State.RULE25;
			}
		},

		/**
		 * Rule 25
		 * If previous tag is "NNS"
		 */
		RULE25{
			@Override
			public void next(String token){
				//System.out.println(token + "\tRULE19");
				if(_previousTag.equals(PennTags.DETERMINER.getValue())){
					if(_currentTag.equals(PennTags.VERB_BASE.getValue())
							|| _currentTag.equals(PennTags.OTHER.getValue())){
						_transformedTag = PennTags.NOUN_SINGULAR.getValue();
						_state = State.TERMINATE;
					}
					else
						_state = State.TAG_NOT_FOUND;
				}
				else
					_state = State.TAG_NOT_FOUND;
			}
		},

		/**
		 * Tag not found state
		 */
		TAG_NOT_FOUND{
			@Override
			public void next(String token){
				//System.out.println("TAG_NOT_FOUND");
				if(!_mode)
					_transformedTag = PennTags.OTHER.getValue();
				_state = State.TERMINATE;
			}
		},
		
		/**
		 * This state corresponds that the State is now Terminated that the token is determined
		 * as containing a punctuation or abbreviation and is now in Transient state
		 */
		TERMINATE(StateDuration.TRANSIENT){
			public void next(String token){
				//System.out.println("TERMINATE");
				_done = true;
				//XXX System.out.println("Transformation is Done!");
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
	 * This will control the State machine by passing one token, which is tagged as OTHER in HMM,
	 * at a time to the FST
	 * @param wordsAndtags
	 * @return ArrayList<String> 
	 */
	public ArrayList<String> tagUnkownTokens(String[][] wordsAndtags){
		
		_transformedTaggedOutput = new ArrayList<String>();
		this._wordsAndtags = wordsAndtags;
		_previousTag = "0";
		_nextTag = "0";
		_mode = false;
		for(int i = 0; i < wordsAndtags.length; i++){
			
			_state = State.INITIAL;
			_currentTag = _wordsAndtags[i][1];

			if(i+1 < wordsAndtags.length)
				_nextTag = _wordsAndtags[i+1][1];
			else
				_nextTag = "0";

			if(_wordsAndtags[i][1].equals("OTHER")){
				
				while(!_done){
					//System.out.println("I'm here\t" + _wordsAndtags[i][0]);
					_state.next(_wordsAndtags[i][0]);
				}
				if(_transformedTag == PennTags.OTHER.getValue())
					_transformedTag = PennTags.NOUN_SINGULAR.getValue();
				
				_wordsAndtags[i][1] = _transformedTag;
			}
			_previousTag = _currentTag;
			_previousToken = _wordsAndtags[i][0];
			_transformedTaggedOutput.add(_wordsAndtags[i][0] + "/" + _wordsAndtags[i][1]);
			_done = false;
		}		
		return _transformedTaggedOutput;
	}

	public String[][] transformTags(String[][] wordsAndtags){
		_transformedTaggedOutput = new ArrayList<String>();
		this._wordsAndtags = wordsAndtags;
		_previousTag = "0";
		_nextTag = "0";
		_mode = true;
		
		for(int i = 0; i < wordsAndtags.length; i++){
			_state = State.RULE15;
			_currentTag = _wordsAndtags[i][1];
			_transformedTag = _wordsAndtags[i][1];
			if(i+1 < wordsAndtags.length)
				_nextTag = _wordsAndtags[i+1][1];
			else
				_nextTag = "0";
			
			while(!_done){
				_state.next(_wordsAndtags[i][0]);
			}
			_wordsAndtags[i][1] = _transformedTag;
			//
			_previousTag = _currentTag;
			_previousToken = _wordsAndtags[i][0];
			_transformedTaggedOutput.add(_wordsAndtags[i][0] + "/" + _wordsAndtags[i][1]);
			_done = false;
		}
		return _wordsAndtags;
	}
}
///:~