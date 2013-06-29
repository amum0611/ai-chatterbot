//:org.azeem.parsing/EarleyParser.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

import java.util.ArrayList;

import edu.azeem.posTagging.Hmm;
import edu.azeem.server.Understanding;
import edu.azeem.tokenization.Tokenizer;

public class EarleyParser extends Understanding{

	private ArrayList<Chart> _chartMap;								//This Hashmap contains N+1 number of Charts
	private String[][] _wordsAndTags;								//This represent tagged human query
	private ArrayList<Edge> _initialEdges;							//This has a reference of initial edges
	private int _humanQuerySize;									//The length of human query N
	private Chart _chart;											//Chart that contains hypothesis edges
	private Chart _nextChart;										//Chart, that is next to _chart, which contains hypothesis edges
	private Edge _currentEdge;										//current edge being computed
	private String _nextSymbol = null;								//Next terminal or non-terminal symbol in rightHandSide
	private String _expectingTag;									//Expecting tag by the parser
	private String _nextTag;										//Next tag of human query
	private String[] _rightHandSide;								//
	private int _wordPosition = 0;
	
	public EarleyParser(String[][] wordsAndTags){
		this._wordsAndTags = wordsAndTags;
		_initialEdges = EdgeFactory.getInitialEdges();
		_humanQuerySize = _wordsAndTags.length;
		_chartMap = new ArrayList<Chart>();
		_chart = new Chart();
		_nextChart = new Chart();
	}
	
	protected void parse(){
		
		int dotPosition = 0;		

		_chart.addEdges(_initialEdges.get(0));			//Dummy State

		//System.out.println(_humanQuerySize);
		for(int i = 0; i < _humanQuerySize + 1; i++){
			if(i < _humanQuerySize)
				_nextTag = _wordsAndTags[i][1];
			else 
				_nextTag = "0";
			//_nextChart = new Chart();
			//System.out.println(_chart.getHypothesesEdge().size());
			for(int j = 0; j < _chart.getHypothesesEdge().size(); j++){
				_currentEdge = _chart.getHypothesesEdge().get(j);
				_rightHandSide = _currentEdge.getRightHandSide();
				dotPosition = _currentEdge.getPositionOfDot();
				
				//System.out.println(i + " - "+ j + " - " + _currentEdge.isComplete());
				if(!_currentEdge.isComplete() && isNextSymbolNonTerminal(_rightHandSide[dotPosition])){
					predictor(_rightHandSide[dotPosition], i, i);
				}
				else if(!_currentEdge.isComplete() && isNextSymbolTerminal(_rightHandSide[dotPosition])){
					scanner(_rightHandSide[dotPosition], i);
				}
				else{
					//System.out.println(_currentEdge.getRoot() + "\ti: " + i + "\tDotPosition:" + _currentEdge.getPositionOfDot() + "\tLength:" + _currentEdge.getRightHandSide().length);
					completer(_currentEdge.getRoot(), i);
				}
			}
			_chartMap.add(_chart);
			_chart = _nextChart;
			_nextChart = new Chart();
			//System.out.println(i);
		}
		print();
		//System.out.println("\n" + _chartMap.size());
	}
	
	private boolean isNextSymbolNonTerminal(String nextSymbol){
		
		if(nextSymbol == null){
			return false;
		}
		for(int i = 0; i < NonTerminalSymbols.values().length; i++){
			if(nextSymbol == NonTerminalSymbols.values()[i].getValue()){
				return true;
			}
		}
		return false;
	}
	
	private boolean isNextSymbolTerminal(String nextSymbol){
		String[] terminals;
		if(nextSymbol == null){
			return false;
		}
		for(int i = 0; i < TerminalSymbols.values().length; i++){
			terminals = TerminalSymbols.values()[i].getValue();
			if(nextSymbol.equals(terminals[0])){
				return true;
			}			
		}
		return false;
	}
	
	private String getWord(String nextSymbol, int wordPosition){
		String[] terminals;
		if(wordPosition >= _humanQuerySize){
			return null;
		}
		for(int i = 0; i < TerminalSymbols.values().length; i++){
			terminals = TerminalSymbols.values()[i].getValue();
			if(nextSymbol.equals(terminals[0])){
				for(int j = 1; j < terminals.length; j++){
					if(_wordsAndTags[wordPosition][1].equals(terminals[j])){
						return _wordsAndTags[wordPosition][0];
					}
				}
			}			
		}
		return null;
	}
	
	private boolean isEdgeAlreadyExist(Edge edge){
		for(int i = 0; i < _chart.getHypothesesEdge().size(); i++){
			if(edge.equals(_chart.getHypothesesEdge().get(i))){
				return true;
			}
		}
		return false;
	}
	
	protected void scanner(String nextSymbol, int wordPosition){
		String word;
		Edge tempEdge;
		
		word = getWord(nextSymbol, wordPosition);
		//System.out.println(nextSymbol + " - " + word);
		if(word != null){
			tempEdge = new Edge(nextSymbol, new String[]{word}, wordPosition, wordPosition + 1);
			tempEdge.incPositionOfDot();
			_nextChart.addEdges(tempEdge);
		}
	}
	
	protected void predictor(String nextSymbol, int positionOne, int positionTwo){
		Edge edge;
		for(int i = 0; i < _initialEdges.size(); i++){			
			if(nextSymbol == _initialEdges.get(i).getRoot() && !isEdgeAlreadyExist(_initialEdges.get(i))){
				edge = _initialEdges.get(i);
				edge.setPositionOne(positionOne);
				edge.setPositionTwo(positionTwo);
				_chart.addEdges(edge);
			}
		}
	}
	
	protected void completer(String root, int processingChart){
		//int numberOfCharts = _chartMap.size();
		ArrayList<Chart> tempChartMap = _chartMap;
		Chart tempChart;
		Edge tempEdge;
		String[] righthHandSide;
		int dotPosition;
		//System.out.println(numberOfCharts);
		for(int i = processingChart - 1; i >= 0; i--){
			//tempChart = tempChartMap.get(i);
			tempChart = new Chart(tempChartMap.get(i));
			for(int j = 0; j < tempChart.getHypothesesEdge().size(); j++){
				tempEdge = tempChart.getEdge(j);
				righthHandSide = tempEdge.getRightHandSide();
				dotPosition = tempEdge.getPositionOfDot();
				/*if(dotPosition == righthHandSide.length){
					continue;
				}*/
				//System.out.println("BINGO " + righthHandSide.length + " - " + dotPosition);
				if(!tempEdge.isComplete() && root.equals(righthHandSide[dotPosition])){
					//tempEdge.setPositionOne(10);
					tempEdge.incPositionOfDot();
					tempEdge.incPositionTwo();
					_chart.addEdges(tempEdge);
				}
			}
		}
	}
	
	@Override
	public void handle() {
		parse();
	}
	
	@Override
	public void print() {
		Chart tempChart;
		Edge tempEdge;
		
		for(int i = 0; i < _chartMap.size(); i++){
			tempChart = _chartMap.get(i);
			System.out.println("\nChart[" + i + "]");
			System.out.println("--------");
			for(int j = 0; j < tempChart.getHypothesesEdge().size(); j++){
				tempEdge = tempChart.getEdge(j);
				System.out.println(tempEdge.toString());
			}
		}
	}
	
	@Override
	public Object output() {		
		return null;
	}
	
	@Override
	public String get() {		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		String humanQuery = "Papa ate the caviar with a spoon";
		//String humanQuery = "book that flight";
		Tokenizer t = new Tokenizer(humanQuery);
		t.handle();
		Hmm hmm = new Hmm((ArrayList<String>) t.output());
		hmm.handle();
		EarleyParser earleyParser = new EarleyParser((String[][]) hmm.output());
		earleyParser.handle();
	}
}
///:~