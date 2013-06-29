//:org.azeem.retrieval/NetConnector.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class will remove unnecessary and invalid search URLs
 */
package edu.azeem.retrieval;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnnecessaryUrlRemover {
	
	private static ArrayList<String> _validLinks;
	private static Matcher _matcher;
	
	private static final String GOOGLE = "google";
	public UnnecessaryUrlRemover(){}
	
	public static ArrayList<String> remove(ArrayList<String> urlList){
		_validLinks = new ArrayList<String>();
		removeInvalidHostLinks(removeGoogleHostLinks(urlList));
		print(_validLinks);
		return _validLinks;
	}
	
	private static ArrayList<String> removeGoogleHostLinks(ArrayList<String> urlList){
		String temp;
		URL url;
		ArrayList<String> tempUrl = new ArrayList<String>();
		
		for(int i = 0; i < urlList.size(); i++){
			temp = urlList.get(i);
			try{
				url = new URL(temp);
				url.getHost();
			}catch(MalformedURLException e){
				continue;
			}
			_matcher = Pattern.compile(GOOGLE, Pattern.CASE_INSENSITIVE).matcher(temp);
			if(!_matcher.find()){
				tempUrl.add(temp);
			}
		}
		return tempUrl;
	}
	
	private static void removeInvalidHostLinks(ArrayList<String> urlList){
		String temp, host;
		URL tempUrl;
		for(int i = 0; i < urlList.size(); i++){
			temp = urlList.get(i);
			try {
				tempUrl = new URL(temp);
			} catch (MalformedURLException e) {
				continue;
			}
			host = tempUrl.getHost();
			if(!InvalidResources.isInvalidResource(host)){
				_validLinks.add(temp);
			}
		}
	}
	
	private static void print(ArrayList<String> urls){
		System.out.println("Retrieved URLs: ");
		for(int i = 0; i < urls.size(); i++){
			System.out.println(urls.get(i));
		}
		System.out.println("\n");
	}
}
///:~