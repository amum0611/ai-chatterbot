//:org.azeem.client/Client.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class shows the PennTag list
 */
package edu.azeem.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.azeem.server.FilePaths;

public class PennTagsViewer extends Client{
	
	private static volatile PennTagsViewer _uniqueInstance; 			//Keep a reference to this class
	
	private FilePaths _filePaths;
	private static JFrame _frame;
	private JLabel _pennTagImage;
	
	private final String TITLE = "45 Penn Tagsets";
	
	private PennTagsViewer(String title, String serverIP, int host){
		super(serverIP);
		_filePaths = FilePaths.getUniqueInstance();
		buildGui(title);
		eventController();
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @param title Title of this
	 * @param host Server IP or Host ID
	 * @return _uniqueInstance
	 */
	public static PennTagsViewer construct(String title, String serverIP, int host){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new PennTagsViewer(title, serverIP, host);
			}
			return _uniqueInstance;
		}
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @return _uniqueInstance
	 */
	public static PennTagsViewer construct(){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				return null;
			}
			return _uniqueInstance;
		}
	}

	@Override
	protected void buildGui(String title) {
		_frame = new JFrame(TITLE);
		_frame.setLayout(new BorderLayout());
		_frame.setResizable(false);
		
		_pennTagImage = new JLabel(new ImageIcon(_filePaths.getPennTagImage()));
		_frame.add(_pennTagImage, BorderLayout.CENTER);
	}
	
	public void show(){
		_pennTagImage = new JLabel(new ImageIcon(_filePaths.getPennTagImage()));
		_frame.add(_pennTagImage);
		_frame.pack();
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		_frame.setResizable(false);
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
	}
}
///:~
