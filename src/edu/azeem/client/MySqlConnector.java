//:edu.azeem.client/MySqlConnector.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class holds the code to construct the front end which gives a gateway to interact with the internal components of Chatterbot
 */
package edu.azeem.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.azeem.answerGeneration.QATokens;

public class MySqlConnector {

	private Connection _connection;
	private Statement _statement;
	private ResultSet _resultSet;
	private String _url;
	private String _driver = "com.mysql.jdbc.Driver";
	private String _location = "C:\\Users\\Azeem\\workspace\\Chatterbot\\src\\edu\\resource\\knowledge";
	private String _database = "\\knowledge";
	private String _username = "";
	private String _password = "";
	
	public MySqlConnector(){}
	/*
	private void connect(){
		try {
			_url = "jdbc:mysql://" + _location + _database;
			Class.forName(_driver).newInstance();
			_connection = DriverManager.getConnection(_url);
			_statement = _connection.createStatement();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String keywords, String answer, QATokens type){
		String sql = "INSERT tblQuestions VALUES (" + keywords + ", " + answer + ", " + type.getValue() + ")";
		try {
			connect();
			_statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String get(String question, QATokens qaToken){
		String sql = "SELECT * FROM tblQuestions";
		String keywords, answer = null, type;
		try {
			connect();
			_resultSet = _statement.executeQuery(sql);
			while(_resultSet.next()){
				keywords = _resultSet.getString("QUE_NAME");
				answer = _resultSet.getString("QUE_ANSWER");
				type = _resultSet.getString("QUE_TYPE");
				if(contains(keywords, question) && type.equalsIgnoreCase(qaToken.getValue())){
					break;
				}
			}
			_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}

	private boolean contains(String keywords, String question){
		int count = 0;
		boolean flag = false;
		StringTokenizer stKeywords = new StringTokenizer(keywords);
		StringTokenizer stQuestions = new StringTokenizer(question);
		ArrayList<String> keywordsList = new ArrayList<String>();
		ArrayList<String> questionsList = new ArrayList<String>();
		
		while(stKeywords.hasMoreTokens()){
			keywordsList.add(stKeywords.nextToken());
		}
		while(stQuestions.hasMoreTokens()){
			questionsList.add(stQuestions.nextToken());
		}

		for(int i = 0; i < keywordsList.size(); i++){
			flag = false;
			for(int j = 0; j < questionsList.size(); j++){
				if(keywordsList.get(i) == questionsList.get(j)){
					flag = true;
				}
			}
			if(flag){
				count++;
			}
		}
		if(count == keywordsList.size())
			return true;
		else
			return false;
	}
	
	public static void main(String[] args){
		MySqlConnector msc = new MySqlConnector();
		msc.add("me is ur mom", "false", QATokens.PERSON);
	}
	*/
}
///:~