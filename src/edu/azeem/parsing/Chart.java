//:org.azeem.parsing/EarleyParser.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

import java.util.ArrayList;

public class Chart {
	
	private ArrayList<Edge> _hypothesesEdge;

	public Chart(){
		_hypothesesEdge = new ArrayList<Edge>();
	}
	
	public Chart(Chart aChart){
		_hypothesesEdge = new ArrayList<Edge>();
		Edge edge;
		String[] symbols = new String[]{};
		for(int i = 0; i < aChart.getHypothesesEdge().size(); i++){
			symbols = aChart.getHypothesesEdge().get(i).getRightHandSide();
			edge = new Edge(aChart.getEdge(i).getRoot(), symbols, aChart.getEdge(i).getPositionOne(), aChart.getEdge(i).getPositionOne());
			edge.setPositionOfDot(aChart.getEdge(i).getPositionOfDot());
			_hypothesesEdge.add(edge);
		}
	}
	
	public void addEdges(Edge edge){
		_hypothesesEdge.add(edge);
	}
	
	public Edge getEdge(int index){
		return _hypothesesEdge.get(index);
	}
	
	public ArrayList<Edge> getHypothesesEdge() {
		return _hypothesesEdge;
	}
	
}
///:~