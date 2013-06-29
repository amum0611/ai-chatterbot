//:org.azeem.client/CorpusNormalizer.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds GUI components to give power to Human to read Brown Corpus
 */
package edu.azeem.client;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CorpusNormalizer extends Client{
	
	private static volatile CorpusNormalizer _uniqueInstance; 			//Keep a reference to this class
	private JFrame _frame;
	private JProgressBar _progressBar;
	private JTextArea _textArea;
	private JButton _read;
	private JScrollPane _scrollPane;
	private int _host;
	private boolean done = false;
	private Thread _readThread;
	private Thread _timerThread;
	private volatile int _time;
	
	private final int ROW = 10;
	private final int COLUMN = 25;
	private final String IDENTIFIER = "5518120732323464049L";
	private final String TITLE = "Brown Corpus Reader";
	private final String NEWLINE = "\n";
	
	private CorpusNormalizer(String title, String serverIP, int host){
		super(serverIP);
		_host = host;
		buildGui(title);
		eventController();
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @param title Title of this
	 * @param host Server IP or Host ID
	 * @return _uniqueInstance
	 */
	public static CorpusNormalizer construct(String title, String serverIP, int host){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new CorpusNormalizer(title, serverIP, host);
			}
			return _uniqueInstance;
		}
	}
	
	/**
	 * This method will be called globally and guarantees that only one instance of this class ever instantiated
	 * @return _uniqueInstance
	 */
	public static CorpusNormalizer construct(){
		synchronized(Chatterbot.class){
			if(_uniqueInstance == null){
				return null;
			}
			return _uniqueInstance;
		}
	}
	
	@Override
	protected void buildGui(String title){
		_frame = new JFrame();
		_frame.setTitle(TITLE);
		
		_frame.setLayout(new BorderLayout());		
		_read = new JButton("Read Brown Corpus");	
		_progressBar = new JProgressBar(0, 100);
		_progressBar.setValue(0);
		_progressBar.setStringPainted(true);
		
		_textArea = new JTextArea(ROW, COLUMN);
		_textArea.setEditable(false);
		_textArea.setMargin(new Insets(10, 10, 10, 10));
		
		_scrollPane = new JScrollPane(_textArea);
		_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		_frame.add(_progressBar, BorderLayout.NORTH);
		_frame.add(_scrollPane, BorderLayout.CENTER);
		_frame.add(_read, BorderLayout.SOUTH);
	}
	
	public void show(){
		_frame.pack();
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		_frame.setResizable(false);
	}
	
	/**
	 * Encapsulates events as inner classes and anonymous inner classes
	 */
	private void eventController(){

		_frame.addWindowListener(new WindowAdapter(){ 				//windowAdapter is a dummy class
			@Override
			public void windowClosing(WindowEvent e){
				_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setDone(true);
			}
		});
		
		_read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_readThread = new Thread(new Runnable(){
					@Override
					public void run() {
						_frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						_read.setEnabled(false);
						_progressBar.setIndeterminate(true);
						read();
					}
				});
				
				_timerThread = new Thread(new Runnable(){
					@Override
					public void run(){
						_time = 0;
						while(!isDone()){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							_time += 1;
						}
					}
				});				
				_timerThread.start();
				_readThread.start();
			}
		});
	}
	
	private void read(){
		_textArea.setText(null);
		handleRequest(IDENTIFIER, _host);
		int progress = 0;
		_progressBar.setIndeterminate(false);
		while (progress < 100) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignore) {
				System.err.println("Interrucped... ");
			}
			progress += 1;
			_progressBar.setValue(Math.min(progress, 100));
		}
		setDone(true);
		setProgressInfo("Time Elapsed: " + _time / 60 + "m " + _time % 60 + "s");
	}
	
	public void setDone(boolean done) {
		this.done = done;
		Toolkit.getDefaultToolkit().beep();
		_read.setEnabled(true);
		_frame.setCursor(null);
	}

	public boolean isDone() {
		return done;
	}

	public void setProgressInfo(String info){
		_textArea.append(info + NEWLINE);
        _textArea.setCaretPosition(_textArea.getDocument().getLength());
	}
	
}
///:~