//:org.azeem.client/FileLocator.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class prompts a JFrame that provides functionality to set paths of various files
 */
package edu.azeem.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;

import edu.azeem.server.FilePaths;

public class FileLocator {

	private static volatile FileLocator _uniqueInstance; 			//Keep a reference to this class
	private JFrame _frame;
	private JButton _set;
	private JButton _default;
	private JButton _clear;
	private JTextField _brownCorpusLocation, _tagRelation, _saveStatA, _saveStatB, _pennTagImage;
	private JTextField _saveStatPi, _brownWords, _saveA, _saveB, _savePi, _brownWordsSerialized;
	private JPanel _panelFields, _panelButtons;
	private FilePaths _filePaths;
	
	private final String TITLE = "File Locations";
	private final int COLUMN = 20;
	
	private FileLocator(){
		buildGui();
		eventController();
		_filePaths = FilePaths.getUniqueInstance();
	}
	
	public static FileLocator construct(){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new FileLocator();
			}
			return _uniqueInstance;
		}
	}
	
	protected void buildGui(){
		_frame = new JFrame(TITLE);
		_frame.setLayout(new BorderLayout());
		
		_set = new JButton("Set Locations");
		_default = new JButton("Load Default");
		_clear = new JButton("Clear");
		
		_panelFields = new JPanel(new GridLayout(11, 2));
		_panelButtons = new JPanel(new FlowLayout());
		
		_brownCorpusLocation = new JTextField(COLUMN);
		_tagRelation = new JTextField(COLUMN); 
		_saveStatA = new JTextField(COLUMN); 
		_saveStatB = new JTextField(COLUMN);
		_saveStatPi = new JTextField(COLUMN); 
		_brownWords = new JTextField(COLUMN); 
		_saveA = new JTextField(COLUMN); 
		_saveB = new JTextField(COLUMN); 
		_savePi = new JTextField(COLUMN); 
		_brownWordsSerialized = new JTextField(COLUMN);
		_pennTagImage = new JTextField(COLUMN);
		
		_panelFields.add(new JLabel("Brown Corpus Location: "));
		_panelFields.add(_brownCorpusLocation);
		_panelFields.add(new JLabel("Tag Relation: "));
		_panelFields.add(_tagRelation);
		_panelFields.add(new JLabel("Probability A (.txt): "));
		_panelFields.add(_saveStatA);
		_panelFields.add(new JLabel("Probability B (.txt): "));
		_panelFields.add(_saveStatB);
		_panelFields.add(new JLabel("Probability Pi (.txt): "));
		_panelFields.add(_saveStatPi);
		_panelFields.add(new JLabel("Unique Brown Words (.txt)"));
		_panelFields.add(_brownWords);
		_panelFields.add(new JLabel("Probability A (.data): "));
		_panelFields.add(_saveA);
		_panelFields.add(new JLabel("Probability B (.data): "));
		_panelFields.add(_saveB);
		_panelFields.add(new JLabel("Probability Pi (.data): "));
		_panelFields.add(_savePi);
		_panelFields.add(new JLabel("Unique Brown Words (.data): "));
		_panelFields.add(_brownWordsSerialized);
		_panelFields.add(new JLabel("Penn Tagset Image"));
		_panelFields.add(_pennTagImage);
		
		_panelButtons.add(_clear);
		_panelButtons.add(_default);
		_panelButtons.add(_set);
		
		_frame.add(_panelFields, BorderLayout.CENTER);
		_frame.add(_panelButtons, BorderLayout.SOUTH);
	}
	

	/**
	 * Encapsulates events as inner classes and anonymous inner classes
	 */
	private void eventController(){

		_frame.addWindowListener(new WindowAdapter(){ 				//windowAdapter is a dummy class
			@Override
			public void windowClosing(WindowEvent e){
				_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		_default.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadDefault();
			}
		});
		
		_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		
		_set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLocations();
			}
		});
	}
	
	protected void loadDefault(){
		_brownCorpusLocation.setText(_filePaths.getBrownCorpusLocation());
		_tagRelation.setText(_filePaths.getTagRelation());
		_saveStatA.setText(_filePaths.getSaveStatA());
		_saveStatB.setText(_filePaths.getSaveStatB());
		_saveStatPi.setText(_filePaths.getSaveStatPi());
		_brownWords.setText(_filePaths.getBrownWords());
		_saveA.setText(_filePaths.getSaveA());
		_saveB.setText(_filePaths.getSaveB());
		_savePi.setText(_filePaths.getSavePi());
		_brownWordsSerialized.setText(_filePaths.getBrownWordsSerialized());
		_pennTagImage.setText(_filePaths.getPennTagImage());
	}
	
	@SuppressWarnings("deprecation")
	protected void setLocations(){
		boolean flag = false;
		if(_brownCorpusLocation.getText().length() != 0){
			_filePaths.setBrownCorpusLocation(_brownCorpusLocation.getText());
			flag = true;
		}
		if(_tagRelation.getText().length() != 0){
			_filePaths.setTagRelation(_tagRelation.getText());
			flag = true;
		}
		if(_saveStatA.getText().length() != 0){
			_filePaths.setSaveStatA(_saveStatA.getText());
			flag = true;
		}
		if(_saveStatB.getText().length() != 0){
			_filePaths.setSaveStatB(_saveStatB.getText());
			flag = true;
		}
		if(_saveStatPi.getText().length() != 0){
			_filePaths.setSaveStatPi(_saveStatPi.getText());
			flag = true;
		}
		if(_brownWords.getText().length() != 0){
			_filePaths.setBrownWords(_brownWords.getText());
			flag = true;
		}
		if(_saveA.getText().length() != 0){
			_filePaths.setSaveA(_saveA.getText());
			flag = true;
		}
		if(_saveB.getText().length() != 0){
			_filePaths.setSaveB(_saveB.getText());
			flag = true;
		}
		if(_savePi.getText().length() != 0){
			_filePaths.setSavePi(_savePi.getText());
			flag = true;
		}
		if(_brownWordsSerialized.getText().length() != 0){
			_filePaths.setBrownWordsSerialized(_brownWordsSerialized.getText());
			flag = true;
		}
		if(_pennTagImage.getText().length() != 0){
			_filePaths.setPennTagImage(_pennTagImage.getText());
			flag = true;
		}
		
		if(flag){
			JOptionPane.showMessageDialog(_frame, "Successfully Saved!");
			clear();
			_frame.hide();
		}
	}
	
	protected void clear(){
		_brownCorpusLocation.setText(null);
		_tagRelation.setText(null);
		_saveStatA.setText(null);
		_saveStatB.setText(null);
		_saveStatPi.setText(null);
		_brownWords.setText(null);
		_saveA.setText(null);
		_saveB.setText(null);
		_savePi.setText(null);
		_brownWordsSerialized.setText(null);
		_pennTagImage.setText(null);
	}
	
	public void show(){
		_frame.pack();
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		_frame.setResizable(false);
	}
}
//:~