package edu.azeem.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.azeem.answerGeneration.KnowledgeMining;
import edu.azeem.answerGeneration.QATokens;
import edu.azeem.client.Analysis;

public class KnowledgeMiningTest {

	private static final int HOST = 6789;						//Host ID 
	private static final String SERVER_IP = "localhost"; 		//Server IP address
	private static final String TITLE = "Natural Language Dialogue System (Chatterbot)";
	
	private static KnowledgeMining _km;
	
	@BeforeClass
	public static void beforeClass(){
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("father");
		keywords.add("modern");
		keywords.add("computer");
		keywords.add("science");
		
		_km = new KnowledgeMining(keywords, QATokens.PERSON);
		Analysis.construct(TITLE, SERVER_IP, HOST);
	}
	
	@Test
	public void testHandle() {
		_km.handle();
	}

	@Test
	public void testPrint() {
		_km.print();
	}

	@Test
	public void testOutput() {
		assertNull(_km.output());
	}

	@Test
	public void testGet() {
		assertNull(_km.output());
	}

	@Test
	public void testKnowledgeMining() {
		new KnowledgeMining(new ArrayList<String>(), QATokens.PERSON);
	}

}
