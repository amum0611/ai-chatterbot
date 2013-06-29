//:edu.azeem.client/Chatterbot.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds the code to construct the front end which gives a gateway to interact with the internal components of Chatterbot
 */
package edu.azeem.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Chatterbot extends Client{

	private static volatile Chatterbot _uniqueInstance; 			//Keep a reference to this class
	private static volatile JTextArea _chatterbotTextArea; 			//Displays the dialogue between human & bot
	private static JTextArea _humanTextArea;						//Human enters their query here
	private static JPanel _humanPanel, _chatterbotPanel;
	private static JFrame _frame;
	private static JButton _send;
	private static JScrollPane _textFieldScrollPane, _humanTextFieldScrollPane;
	private static JLabel _caption;
	private static int _host; 										//Host ID
	
	private static CorpusNormalizer _corpusNormalizer;
	private static FileLocator _fileLocator;
	private static PennTagsViewer _pennTagViewer;
	private static Analysis _analysis;
	
	private static JMenuBar _menuBar;
	private static JMenu _menu;
	private static JMenuItem _menuItemBrown;
	private static JMenuItem _menuItemLocations;
	private static JMenuItem _menuItemQuit;
	private static JMenuItem _menuItemPennTags;
	private static JMenuItem _menuItemAnalysis;
	
	private static final Dimension FRAME_DIMESION = new Dimension(500, 400); 	//Size of the frame
	private static final int ROW = 18; 								//Number of rows in chatterbotTextArea
	private static final int HUMAN_ROW = 2; 						//Number of rows in humanTextArea
	private static final int COLOUMN = 40; 							//Number of columns in chatterbotTextArea
	private static final int HUMAN_COLOUMN = COLOUMN - 7; 			//Number of columns in chatterbotTextArea
	private static final String NEWLINE = "\n";
	private static final String NULL_INPUT = "Your entered a dumb question";
	private static final String CHATTERBOT = "MRIGA: ";
	
	/**
	 * Constructor
	 * @param title Title of the Chatterbot
	 * @param host Host ID
	 * @param serverIP Server IP address
	 */
	private Chatterbot(String title, String serverIP, int host){
		super(serverIP);
		Chatterbot._host = host;
		buildGui(title);
		buildMenu();
		eventController();
		_corpusNormalizer = CorpusNormalizer.construct(title, serverIP, host);
		_fileLocator = FileLocator.construct();
		_pennTagViewer = PennTagsViewer.construct(title, serverIP, host);
		_analysis = Analysis.construct(title, serverIP, host);
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @param title Title of the Chatterbot
	 * @param host Server IP or Host ID
	 */
	public static void construct(String title, String serverIP, int host){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new Chatterbot(title, serverIP, host);
			}	
		}
	}
	
	/**
	 * Generates the Chatterbot front end
	 * @param title Title of the Chatterbot
	 */
	@Override
	protected void buildGui(String title){
		_frame = new JFrame(title);
		_frame.setLayout(new BorderLayout());
		_frame.setSize(FRAME_DIMESION);
		_frame.setBackground(Color.green); 
		_frame.setResizable(false);
		
		_caption = new JLabel("      Conversation: ");
		_caption.setFont(new Font("Cambria", Font.BOLD, 16));
		
		_send = new JButton("Send");
		
		_chatterbotTextArea = new JTextArea("", ROW, COLOUMN);
		_chatterbotTextArea.setEditable(false);
		
		_textFieldScrollPane = new JScrollPane(_chatterbotTextArea);
		_textFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_chatterbotTextArea.setLineWrap(true);
		_chatterbotTextArea.setWrapStyleWord(true);		
		
		_humanTextArea = new JTextArea("", HUMAN_ROW, HUMAN_COLOUMN);
		_humanTextArea.setFocusable(true);
		_humanTextFieldScrollPane = new JScrollPane(_humanTextArea);
		_humanTextFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_humanTextArea.setLineWrap(true);
		_humanTextArea.setWrapStyleWord(true);
		
		_humanPanel = new JPanel(new FlowLayout());
		_chatterbotPanel = new JPanel();		
		
		_humanPanel.add(_humanTextFieldScrollPane);
		_humanPanel.add(_send);
		_chatterbotPanel.add(_textFieldScrollPane);
		
		_frame.add(BorderLayout.NORTH, _caption);
		_frame.add(BorderLayout.CENTER, _chatterbotPanel);
		_frame.add(BorderLayout.SOUTH, _humanPanel);

		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		//frame.pack();
		_humanTextArea.requestFocus();
	}
	
	/**
	 * This method builds the menu bar at the top of JFrame
	 */
	private void buildMenu(){
		_menuBar = new JMenuBar();
		
		_menu = new JMenu("Administrative Actions");
		_menu.setMnemonic(KeyEvent.VK_A);
		_menuBar.add(_menu);
		
		_menuItemBrown = new JMenuItem("Normalize Brown Corpus", KeyEvent.VK_N);
		_menuItemBrown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		_menu.add(_menuItemBrown);

		_menuItemLocations = new JMenuItem("Set File Locations", KeyEvent.VK_S);
		_menuItemLocations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		_menu.add(_menuItemLocations);
		_menu.addSeparator();
		
		_menuItemPennTags = new JMenuItem("View Penn Tagset", KeyEvent.VK_V);
		_menuItemPennTags.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		_menu.add(_menuItemPennTags);
		_menu.addSeparator();
		
		_menuItemAnalysis = new JMenuItem("Analysis", KeyEvent.VK_A);
		_menuItemAnalysis.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		_menu.add(_menuItemAnalysis);
		_menu.addSeparator();
		
		_menuItemQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
		_menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		_menu.add(_menuItemQuit);
		
		_frame.setJMenuBar(_menuBar);
	}
	
	/**
	 * Encapsulates events as inner classes and anonymous inner classes
	 */
	private static void eventController(){

		_frame.addWindowListener(new WindowAdapter(){ 				//windowAdapter is a dummy class
			@Override
			public void windowClosing(WindowEvent e){
				quit();
			}
		});
		
		_send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				performAction();
			}
		});
		
		_menuItemBrown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_corpusNormalizer.show();
			}
		});
		
		_menuItemLocations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_fileLocator.show();
			}
		});
		
		_menuItemAnalysis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_analysis.show();
			}
		});
		
		_menuItemQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				quit();
			}
		});
		
		_menuItemPennTags.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_pennTagViewer.show();
			}
		});
	}
	
	/**
	 * This method encapsulates the procedures that has to be done before 
	 * passing the Human query to the server
	 */
	private static void performAction(){	
		new Thread(new Runnable(){
			@Override
			public void run() {
				String text = (String)_humanTextArea.getText();
				_analysis.clear();
				if(text.length() >= 1){
					_humanTextArea.setText(null);
					manupilateChatterbotTextArea(text, 0); 						//Set the human input in the chatterbotTextArea
					manupilateChatterbotTextArea(handleRequest(text, _host), 1);	//Set the Chatterbot's reply in the chatterbotTextArea			
				}
				else
					manupilateChatterbotTextArea(NULL_INPUT, 1);
			}
		}).start();
		_humanTextArea.requestFocus();
	}
	
	/**
	 * This method encapsulate the logic to accumulate the texts in TextArea
	 * @param title Title of the Chatterbot
	 * @param choice If choice is 1 then it is Chatterbot accessing this method or else it's Human
	 */
	private static void manupilateChatterbotTextArea(String text, int choice){
		String _tag;
		synchronized(Chatterbot.class){			
			if(choice == 1){ 								//Chatterbot is accessing chatterbotTextArea
				_tag = CHATTERBOT;
			}		
			else{ 											//Human is accessing chatterbotTextArea
				_tag = "HUMAN: ";
			}
			_chatterbotTextArea.append(_tag + NEWLINE + text + NEWLINE + NEWLINE);
			
			//Makes sure the new appended text to _chatterbotTextArea is visible
			_chatterbotTextArea.setCaretPosition(_chatterbotTextArea.getDocument().getLength()); 
		}
	}
	
	/**
	 * This method gets triggered when HUmna tries to close the Chatterbot interface
	 */
	private static void quit(){
		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int option;
		option = JOptionPane.showConfirmDialog(_frame, "Are you sure to quit", "Exit", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
}
///:~