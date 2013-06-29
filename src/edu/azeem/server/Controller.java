//:org.azeem.server/Controller.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class encapsulates the responsibility of handling NLP components and their interaction 
 */
package edu.azeem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import edu.azeem.answerGeneration.FilterCandidate;
import edu.azeem.answerGeneration.KnowledgeMining;
import edu.azeem.answerGeneration.QATokens;
import edu.azeem.answerGeneration.QuestionTypes;
import edu.azeem.answerGeneration.VectorSpace;
import edu.azeem.client.Analysis;
import edu.azeem.posTagging.BrownCorpusReader;
import edu.azeem.posTagging.Hmm;
import edu.azeem.retrieval.NetConnector;
import edu.azeem.retrieval.Retrieve;
import edu.azeem.tokenization.Tokenizer;

public class Controller extends Server{

	private ObjectInputStream _inFromClient;				//De-serialises primitive data and objects previously written using an ObjectOutputStream (Java API 1.6)
	private ObjectOutputStream  _outToClient;				//Writes primitive data types and graphs of Java objects to an OutputStream (Java API 1.6)
	private volatile String _humanQuery;					//Holds the human input
	private volatile ArrayList<String> _keywords;			//Holds keywords that is used to query web search engine
	private Understanding _tokernizer;
	private Understanding _hmm;
	private Generation _retrieve;
	private Generation _knowledgeMining;
	private Generation _vectorSpace;
	private BrownCorpusReader _brownCorpusReader;
	private Thread _timerThread;
	private double _time;
	private boolean _done = false;
	
	/**
	 * Initialises the server socket
	 * @param host Host ID
	 */
	public Controller(int host) {
		super(host);
	}
	
	/**
	 * This method defines the transport media to acquire human requests
	 */
	@Override
	public void handleRequest(){

		try {
			_inFromClient = new ObjectInputStream(_socket.getInputStream());
			_outToClient = new ObjectOutputStream(_socket.getOutputStream());
		    _humanQuery = (String) _inFromClient.readObject();
		    _outToClient.writeObject(manageCommunication());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object manageCommunication(){
		QATokens qaToken;
		setDone(false);
		elapsedTime();
		if(_humanQuery.equals("5518120732323464049L")){
			readCorpus();
			return "done";
		}
		else{
			clean();
			Analysis.getUniqueInstance().clear();
			Analysis.getUniqueInstance().setQestion(_humanQuery);
			System.out.println(FilterCandidate.remoevStopWords(_humanQuery));
			
			_tokernizer = new Tokenizer(_humanQuery);
			_tokernizer.handle();
			_tokernizer.print();
			qaToken = determineQuestionType((ArrayList<String>) _tokernizer.output());
			Analysis.getUniqueInstance().setType(qaToken);
			_hmm = new Hmm((ArrayList<String>) _tokernizer.output());
			_hmm.handle();
			_retrieve = new Retrieve((ArrayList<String>) _tokernizer.output(),(String[][]) _hmm.output());
			_retrieve.handle();
			_keywords = (ArrayList<String>) _retrieve.output();
			if(qaToken != QATokens.OTHER){
				_knowledgeMining = new KnowledgeMining(_keywords, qaToken);
				_knowledgeMining.handle();
				setDone(true);
				return _knowledgeMining.get();
			}
			else{
				_vectorSpace = new VectorSpace(_keywords);
				_vectorSpace.handle();
				setDone(true);
				return _vectorSpace.get();
			}
		}
	}
	
	@Override
	public void readCorpus(){
		_brownCorpusReader = new BrownCorpusReader();
		_brownCorpusReader.read(true);
		_brownCorpusReader.saveProbabilities();
		_brownCorpusReader.serializeProbabilities();
	}
	
	protected void clean(){
		NetConnector nc = new NetConnector();
		nc.cleanUpContents();
		nc.cleanUpData();
		nc.cleanUpSearch();
		nc.cleanUpSeg();
	}
	
	protected QATokens determineQuestionType(ArrayList<String> tokenList){
		return QuestionTypes.determine(tokenList);
	}
	
	/**
	 * This is a dummy class
	 * @param x
	 * @return upper cased x
	 */
	public String test(String x){
		return x.toUpperCase();
	}
	
	private void elapsedTime(){
		_timerThread = new Thread(new Runnable(){
			@Override
			public void run(){
				_time = 0;
				while(!isDone()){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					_time += 1;
				}
			}
		});				
		_timerThread.start();
	}

	public void setDone(boolean _done) {
		this._done = _done;
		if(_done)
			Analysis.getUniqueInstance().setTime(Double.toString(_time));
	}

	public boolean isDone() {
		return _done;
	}
}
///:~