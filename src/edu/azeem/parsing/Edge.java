//:org.azeem.parsing/Edge.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

public class Edge {

	private String _root;					//Nonterminal constituent of CFG in the left hand side of the arrow
	private String[] _rightHandSide;		//Nonterminal and terminal symbols in the CFG on right hand side
	
	private int _positionOne = 0;			//Indicates that the constituent predicted by this state should begin at the start of the input
	private int _positionTwo = 0;			//This reflects the fact that the dot lies at the beginning 
	private int _positionOfDot = 0;			//Position of the dot in _rightHandSide
	
	private boolean complete = false;
	
	public Edge(String root, String[] rightHandSideSymbols, int positionOne, int positionTwo){
		this.setRoot(root);
		this.setRightHandSide(rightHandSideSymbols);
		this.setPositionOne(positionOne);
		this.setPositionTwo(positionTwo);
		this.setComplete(false);
	}

	public void incPositionOne(){
		_positionOne++;
	}
	
	public void incPositionTwo(){
		_positionTwo++;
	}
	
	public void incPositionOfDot(){
		if(!complete){
			_positionOfDot++;
		}
		if(_positionOfDot == _rightHandSide.length){
			setComplete(true);
		}
	}
	
	@Override
	public String toString(){
		String s;
		String righthandSide = "";
		boolean dotPlaced = false;
		
		for(int i = 0; i < _rightHandSide.length; i++){
			if(_positionOfDot == i && !dotPlaced){
				righthandSide += ".";
				dotPlaced = true;
			}
			righthandSide += _rightHandSide[i];
			righthandSide += " ";
			if(!dotPlaced){
				righthandSide += ".";
				dotPlaced = true;
			}
		}
		s = _root + " -> " + righthandSide + " [" + _positionOne + ", " + _positionTwo + "]"; 
		return s;		
	}
	
	/*@Override
	public boolean equals(Object object){
		Edge edge = (Edge) object;
		if(_root.equals(edge.getRoot()) && _rightHandSide.equals(edge.getRightHandSide()) 
				&& _positionOfDot == edge.getPositionOfDot() 
				&& _positionOne == edge.getPositionOne()
				&& _positionTwo == edge.getPositionTwo()){
			return true;
		}
		return false;
	}*/
	
	public void setRoot(String root) {
		this._root = root;
	}

	public String getRoot() {
		return _root;
	}

	public void setRightHandSide(String[] symbols) {
		this._rightHandSide = symbols;
	}

	public String[] getRightHandSide() {
		return _rightHandSide;
	}

	public void setPositionOne(int positionOne) {
		this._positionOne = positionOne;
	}

	public int getPositionOne() {
		return _positionOne;
	}

	public void setPositionTwo(int positionTwo) {
		this._positionTwo = positionTwo;
	}

	public int getPositionTwo() {
		return _positionTwo;
	}
	
	public static void main(String[] args){
		
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setPositionOfDot(int _positionOfDot) {
		this._positionOfDot = _positionOfDot;
	}

	public int getPositionOfDot() {
		return _positionOfDot;
	}
}
///:~