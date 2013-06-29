//:edu.azeem.client/Chatterbot.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds the client side objects in order to build a facade which retrieve 
 * answers from user defined queries
 */
package edu.azeem.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RestrictedChatterbot{
	
	private static volatile RestrictedChatterbot _uniqueInstance; 	//Keep a reference to this class
	private static JTextArea _chatterbotTextArea;
	private static JTextArea _humanTextArea, _onlineRepositories;	//Human enters their query here
	private static JPanel _panel;
	private static JFrame _frame;
	private static JButton _send;
	private static JScrollPane _textFieldScrollPane, _humanTextFieldScrollPane;
	private final String TITLE = "Restricted - ";
	
	private RestrictedChatterbot(String title) {
		buildGui(TITLE + title);
		//eventController();
	}

	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @param title Title of the Chatterbot
	 * @param host Server IP or Host ID
	 */
	public static void construct(String title){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new RestrictedChatterbot(title);
			}	
		}
	}
	
	protected void buildGui(String title) {
		
	}

}
///:~