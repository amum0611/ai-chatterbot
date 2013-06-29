//:edu.azeem.client/Chatterbot.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class list outs results of each intermediate component
 */
package edu.azeem.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import edu.azeem.answerGeneration.FilterCandidate;
import edu.azeem.answerGeneration.QATokens;

public class Analysis extends Client{

	private static volatile Analysis _uniqueInstance;	//Keep a reference to this class
	
	private static JFrame _frame;
	private static JPanel _panel, _mainPanel;
	private static JTextArea _question, _tokenization, _posTag, _syntactic, _semantic, _query, _answer, _discourse;
	private JTable _answerTable;
	private DefaultTableModel _answerModel;
	private static JLabel _timeElapsed;
	private static JButton _add;
	private static QATokens _type;
	
	private static final String TIME = "Time Elapsed: ";
	private static final String TITLE = "Analysis";
	private static final int ROW = 3;
	private static final int COLOUMN = 30;
	
	private Analysis(String title, String serverIP, int host){
		super(serverIP);
		buildGui(title);
		eventController();
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @return _uniqueInstance
	 */
	public static Analysis construct(String title, String serverIP, int host){
		synchronized(Analysis.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new Analysis(title, serverIP, host);
			}
			return _uniqueInstance;
		}
	}
	
	/**
	 * This method call is risk because this will not throw any error if _uniqueInstance is not null
	 * @return
	 */
	public static Analysis getUniqueInstance(){
		return _uniqueInstance;
	}
	
	public void show(){
		_frame.pack();
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		_frame.setResizable(false);
	}
	
	@Override
	protected void buildGui(String title) {
		_frame = new JFrame(TITLE);
		_frame.setLayout(new BorderLayout());
		_frame.setResizable(false);
		
		_question = new JTextArea("", ROW, COLOUMN);
		_question.setEditable(false);
		
		_tokenization = new JTextArea("", ROW, COLOUMN);
		_tokenization.setEditable(false);
		
		_posTag = new JTextArea("", ROW, COLOUMN);
		_posTag.setEditable(false);
		
		_syntactic = new JTextArea("", ROW, COLOUMN);
		_syntactic.setEditable(false);
		
		_semantic = new JTextArea("", ROW, COLOUMN);
		_semantic.setEditable(false);
		
		_query = new JTextArea("", ROW, COLOUMN);
		_query.setEditable(false);
		
		_answer = new JTextArea("", ROW, COLOUMN);
		_answer.setEditable(false);		
		
		_discourse = new JTextArea("", ROW, COLOUMN);
		_discourse.setEditable(false);
		
		_add = new JButton("Is this answer correct?");
		
		JScrollPane questionSP = new JScrollPane(_question);
		questionSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_question.setLineWrap(true);
		_question.setWrapStyleWord(true);	
		
		JScrollPane tokenizationSP = new JScrollPane(_tokenization);
		tokenizationSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_tokenization.setLineWrap(true);
		_tokenization.setWrapStyleWord(true);
		
		JScrollPane posSP = new JScrollPane(_posTag);
		posSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_posTag.setLineWrap(true);
		_posTag.setWrapStyleWord(true);
		
		JScrollPane syntacticSP = new JScrollPane(_syntactic);
		syntacticSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_syntactic.setLineWrap(true);
		_syntactic.setWrapStyleWord(true);
		
		JScrollPane semanticSP = new JScrollPane(_semantic);
		semanticSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_semantic.setLineWrap(true);
		_semantic.setWrapStyleWord(true);
		
		JScrollPane querySP = new JScrollPane(_query);
		querySP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_query.setLineWrap(true);
		_query.setWrapStyleWord(true);
		
		JScrollPane answerSP = new JScrollPane(_answer);
		answerSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_answer.setLineWrap(true);
		_answer.setWrapStyleWord(true);
		
		JScrollPane discourseSP = new JScrollPane(_discourse);
		discourseSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_discourse.setLineWrap(true);
		_discourse.setWrapStyleWord(true);
		
		_panel = new JPanel(new GridLayout(7, 2));
		
		_panel.add(new JLabel("Human Question: "));
		_panel.add(new JLabel("Tokenization: "));
		_panel.add(questionSP);
		_panel.add(tokenizationSP);
		
		_panel.add(new JLabel("HMM & Rule Based Tagger: "));		
		_panel.add(new JLabel("Google Search Query: "));
		_panel.add(posSP);
		_panel.add(querySP);
		
		_panel.add(new JLabel("Answer: "));
		_panel.add(new JLabel("Discourse"));
		_panel.add(answerSP);
		_panel.add(discourseSP);
		
		_panel.add(_add);
		
		_timeElapsed = new JLabel(TIME + "0 ms", SwingConstants.CENTER);
		_panel.add(_timeElapsed);
		
		String[] answerTableColumn = {"Score/Similarity Index", "Answer"};
		String[][] answerTableRow = {};
		
		_answerModel = new DefaultTableModel(answerTableRow, answerTableColumn);
		_answerTable = new JTable(_answerModel){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
		        return false; //User cannot edit cells
			}
		};
		_answerTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
		_answerTable.setFillsViewportHeight(true);
		JScrollPane answerTableSP = new JScrollPane(_answerTable);
		
		_mainPanel = new JPanel(new GridLayout(2,1));
		_mainPanel.add(_panel);
		_mainPanel.add(answerTableSP);
		_frame.add(_mainPanel, BorderLayout.CENTER);
	}
	
	public void setQestion(String arg0){
		_question.setText(arg0);
	}
	
	public void setTokenization(String arg0){
		_tokenization.setText(arg0);
	}
	
	public void setPos(String arg0){
		_posTag.setText(arg0);
	}
	
	public void setQuery(String arg0){
		_query.setText(arg0);
	}
	
	public void setAnswer(String arg0){
		_answer.setText(arg0);
	}
	
	public void setDiscourse(String arg0){
		_discourse.setText(arg0);
	}
	
	public void setTable(String[][] answers){
		for(int i = 0; i < answers.length; i++){
			_answerModel.insertRow(0, answers[i]);
		}
	}
	
	public void setTable(String col1, String col2){
		_answerModel.insertRow(_answerModel.getRowCount(), new String[]{col1, col2});
	}
	
	public void setTime(String time){
		_timeElapsed.setText(TIME + time);
	}
	
	public void clear(){
		_question.setText("");
		_tokenization.setText("");
		_posTag.setText("");
		_query.setText("");
		_answer.setText("");
		_discourse.setText("");
		for(int i = 0; i < _answerModel.getRowCount(); i++){
			_answerModel.removeRow(0);
		}
		_timeElapsed.setText(TIME);
	}
	
	/**
	 * Encapsulates events as inner classes and anonymous inner classes
	 */
	private static void eventController(){

		_frame.addWindowListener(new WindowAdapter(){ 				//windowAdapter is a dummy class
			@Override
			public void windowClosing(WindowEvent e){
				_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
			}
		});
		
		_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				performAction();
			}
		});
	}
	
	/**
	 * This method encapsulates the procedures that has to be done before 
	 * passing the Human query to the server
	 */
	private static void performAction(){	
		if(confirm() && _question.getText().length() != 0 && _answer.getText().length() != 0){
			MySqlConnector connector = new MySqlConnector();
			//connector.add(FilterCandidate.remoevStopWords(_question.getText()), _answer.getText(), _type);
		}
	}
	
	/**
	 * This method gets triggered when HUmna tries instantiate JDBC object
	 */
	private static boolean confirm(){
		int option;
		option = JOptionPane.showConfirmDialog(_frame, "Are you sure " +
				"you want to add this answer to the database", "Confirm", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			return true;
		}
		return false;		
	}

	public void setType(QATokens _type) {
		Analysis._type = _type;
	}

	public QATokens getType() {
		return _type;
	}
	
}
///:~