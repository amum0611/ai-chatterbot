//:org.azeem.parsing/EdgeFactory.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.parsing;

import java.util.ArrayList;

public class EdgeFactory {
	
	private static volatile EdgeFactory _uniqueInstance; 			//Keep a reference to this class
	private static ArrayList<Edge> _edges;
	
	private EdgeFactory(){
		createEdges();
	}
	
	public static ArrayList<Edge> getInitialEdges(){
		synchronized(EdgeFactory.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new EdgeFactory();
			}
		}
		return _edges;
	}
	
	private void createEdges(){
		_edges = new ArrayList<Edge>();
		/**
		 * ROOT -> .S [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.ROOT.getValue(), 
				new String[]{NonTerminalSymbols.SENTENCE.getValue()}, 0, 0));
		/**
		 * S -> .NP VP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.SENTENCE.getValue(), 
				new String[]{NonTerminalSymbols.NOUN_PHRASE.getValue(), NonTerminalSymbols.VERB_PHRASE.getValue()}, 0, 0));
		/**
		 * S -> .VP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.SENTENCE.getValue(), 
				new String[]{NonTerminalSymbols.VERB_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> .Verb NP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> .Verb NP PP [0,0] **
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue(), NonTerminalSymbols.PREPOSITIONAL_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> .Verb PP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0], NonTerminalSymbols.PREPOSITIONAL_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> .Verb [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0]}, 0, 0));
		/**
		 * VP -> .Verb S [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0], NonTerminalSymbols.SENTENCE.getValue()}, 0, 0));
		/**
		 * PP -> .Preposition NP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.PREPOSITIONAL_PHRASE.getValue(),
				new String[]{TerminalSymbols.PREPOSITION.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()}, 0, 0));
		/**
		 * NP -> .Pronoun [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{TerminalSymbols.PRONOUN.getValue()[0]}, 0, 0));
		/**
		 * NP -> .Det Nominal [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{TerminalSymbols.DETERMINER.getValue()[0], NonTerminalSymbols.NOMINAL.getValue()}, 0, 0));
		/**
		 * NP -> .Card Nominal [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{TerminalSymbols.CARDINAL.getValue()[0], NonTerminalSymbols.NOMINAL.getValue()}, 0, 0));
		/**
		 * NP -> .AP Nominal [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{NonTerminalSymbols.ADJECTIVE_PHRASE.getValue(), NonTerminalSymbols.NOMINAL.getValue()}, 0, 0));
		/**
		 * NP -> .Nominal [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{NonTerminalSymbols.NOMINAL.getValue()}, 0, 0));
		/**
		 * Nominal -> .Noun [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOMINAL.getValue(),
				new String[]{TerminalSymbols.NOUN.getValue()[0]}, 0, 0));
		/**
		 * Nominal -> .Noun Noun [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOMINAL.getValue(),
				new String[]{TerminalSymbols.NOUN.getValue()[0], TerminalSymbols.NOUN.getValue()[0]}, 0, 0));
		/**
		 * Nominal -> .Nominal PP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOMINAL.getValue(),
				new String[]{NonTerminalSymbols.NOMINAL.getValue(), NonTerminalSymbols.PREPOSITIONAL_PHRASE.getValue()}, 0, 0));
		/**
		 * Nominal -> .Nominal RelClause [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOMINAL.getValue(),
				new String[]{NonTerminalSymbols.NOMINAL.getValue(), NonTerminalSymbols.REL_CLAUSE.getValue()}, 0, 0));
		/**
		 * NP -> .NP And NP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.NOUN_PHRASE.getValue(),
				new String[]{NonTerminalSymbols.NOUN_PHRASE.getValue(), TerminalSymbols.AND.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> .VP And VP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{NonTerminalSymbols.VERB_PHRASE.getValue(), TerminalSymbols.AND.getValue()[0], NonTerminalSymbols.VERB_PHRASE.getValue()}, 0, 0));
		/**
		 * S -> .S And S [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.SENTENCE.getValue(),
				new String[]{NonTerminalSymbols.SENTENCE.getValue(), TerminalSymbols.AND.getValue()[0], NonTerminalSymbols.SENTENCE.getValue()}, 0, 0));
		/**
		 * AP -> .Adv Adj [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.ADJECTIVE_PHRASE.getValue(),
				new String[]{TerminalSymbols.ADVERB.getValue()[0], TerminalSymbols.ADJECTIVE.getValue()[0]}, 0, 0));
		/**
		 * PP -> .TO NP [0,0]
		 */
		_edges.add(new Edge(NonTerminalSymbols.PREPOSITIONAL_PHRASE.getValue(),
				new String[]{TerminalSymbols.TO.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()}, 0, 0));
		/**
		 * VP -> Verb Particle NP [0,0] **
		 */
		_edges.add(new Edge(NonTerminalSymbols.VERB_PHRASE.getValue(),
				new String[]{TerminalSymbols.VERB.getValue()[0], TerminalSymbols.PARTICLE.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()}, 0, 0));
		
	}
	
	public static void main(String[] args){
		ArrayList<Edge> edges = EdgeFactory.getInitialEdges();
		for(int i = 0; i < edges.size(); i++){
			System.out.println(edges.get(i).toString());
		}
		
		//String[] x = new String[]{NonTerminalSymbols.NOUN_PHRASE.getValue(), TerminalSymbols.AND.getValue()[0], NonTerminalSymbols.NOUN_PHRASE.getValue()};
		//System.out.println("\n" + x.length);
		
		System.out.println("Number of CFG rules: " + edges.size());
	}
}
///:~