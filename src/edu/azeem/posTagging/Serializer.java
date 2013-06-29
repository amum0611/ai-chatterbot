//:org.azeem.posTagging/Serializer.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information from Online Repositories 
 * @description This class encapsulates methods to serialise and de-serialise vectors and matrices that 
 * contains probabilities for HMM
 */
package edu.azeem.posTagging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractList;
import java.util.ArrayList;

public class Serializer {

	private static volatile Serializer _uniqueInstance; 	//Keep a reference to this class
	private AbstractList<String> _words;					//List of unique Brown Tag words
	private double[][] _pi;									//Initial Probability of Penn Tags as the starting word in a sentence
 	private double[][] _a;									//Transition Probability: The probabilities of the machine moving from one state to another
 	private double[][] _b;									//Emmision or Observation probability: of observing observing a particular observable state given that the hidden model is in a particular hidden state
 
	private Serializer(){}
	
	public static Serializer getUniqueInstance(){
		synchronized (Serializer.class) {
			if(_uniqueInstance == null)
				_uniqueInstance = new Serializer();
		}
		return _uniqueInstance;
	}
	/**
	 * De-serialises the _a vector as a persistent objects 
	 */
	public synchronized void deserializedA(String saveA){
		if(_a == null){
			try {
				FileInputStream fileInputStream = new FileInputStream(saveA);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				_a = (double[][])objectInputStream.readObject();
				objectInputStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * De-serialises the _pi vector as a persistent objects 
	 */
	public synchronized void deserializedPi(String savePi){
		if(_pi == null){
			try {
				FileInputStream fileInputStream = new FileInputStream(savePi);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				_pi = (double[][])objectInputStream.readObject();
				objectInputStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * De-serialises the _b vector as a persistent objects 
	 */
	public synchronized void deserializedB(String saveB){
		if(_b == null){
			try {
				FileInputStream fileInputStream = new FileInputStream(saveB);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				_b = (double[][])objectInputStream.readObject();
				objectInputStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * De-serialises the _brownWord ArrayList as a persistent objects 
	 */
	@SuppressWarnings("unchecked")
	public synchronized void deserializedWords(String brownWordsSerialized){
		if(_words == null){
			try {
				FileInputStream fileInputStream = new FileInputStream(brownWordsSerialized);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				_words = (ArrayList<String>)objectInputStream.readObject();
				objectInputStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Serialises the _a vector as a persistent objects
	 * @param saveA
	 * @param a
	 */
	public void serializedA(String saveA, double[][] a){
		_a = a;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(saveA);
			//XXX GZIPOutputStream gzipOut = new GZIPOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(a);
			objectOutputStream.flush();
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serialises the _pi vector as a persistent objects 
	 * @param savePi
	 * @param pi
	 */
	public void serializedPi(String savePi, double[][] pi){
		_pi = pi;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(savePi);
			//XXX GZIPOutputStream gzipOut = new GZIPOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(pi);
			objectOutputStream.flush();
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serialises the _b vector as a persistent objects
	 * @param saveB
	 * @param b
	 */
	public void serializedB(String saveB, double[][] b){
		_b = b;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(saveB);
			//XXX GZIPOutputStream gzipOut = new GZIPOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(b);
			objectOutputStream.flush();
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serialises the _brownWord ArrayList as a persistent objects 
	 * @param brownWordsSerialized
	 * @param words
	 */
	public void serializedWords(String brownWordsSerialized, ArrayList<String> words){
		_words = words;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(brownWordsSerialized);
			//XXX GZIPOutputStream gzipOut = new GZIPOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(words);
			objectOutputStream.flush();
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double[][] getPi() {
		return _pi;
	}

	public double[][] getA() {
		return _a;
	}

	public double[][] getB() {
		return _b;
	}

	public AbstractList<String> getWords() {
		return _words;
	}
}
///:~