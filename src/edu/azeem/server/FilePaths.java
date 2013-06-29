//:org.azeem.server/FilePaths.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class is a singleton that provides global access to file locations
 */
package edu.azeem.server;

public class FilePaths {
	
	private volatile String _brownCorpusLocation = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/brown";			//Location of Brown Corpus
	private volatile String _tagRelation = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/list/TagRelation.txt";	//Location of Tag Relation List
	private volatile String _saveStatA = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/statA.txt";
	private volatile String _saveStatB = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/statB.txt";
	private volatile String _saveStatPi = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/statPi.txt";
	private volatile String _brownWords = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/words.txt";
	
	private volatile String _saveA = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/A.data";
	private volatile String _saveB = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/B.data";
	private volatile String _savePi = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/Pi.data";
	private volatile String _brownWordsSerialized = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/uniqueWords.data";
	private volatile String _pennTagImage = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/repository/PennTags.png";
	
	private volatile String _webContentLocation = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/webContents";
	private volatile String _webContentLocationTemp = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/webContents/temp";
	private volatile String _webContentLocationData = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/webContents/data";
	private volatile String _webContentLocationSearch = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/webContents/searchPage";
	private volatile String _webContentLocationSeg = "C:/Users/Azeem/workspace/Chatterbot/src/edu/resource/webContents/segmentedData";
	
	private volatile String _wordNetLocation = "C:/Program Files (x86)/WordNet/2.1/dict";
	private static volatile FilePaths _uniqueInstance; 			//Keep a reference to this class
	
	private FilePaths(){
		
	}
	
	public static FilePaths getUniqueInstance(){
		synchronized(FilePaths.class){
			if(_uniqueInstance == null){
				_uniqueInstance = new FilePaths();
			}	
		}
		return _uniqueInstance;
	}

	public void setBrownCorpusLocation(String brownCorpusLocation) {
		this._brownCorpusLocation = brownCorpusLocation;
	}

	public String getBrownCorpusLocation() {
		return _brownCorpusLocation;
	}

	public void setTagRelation(String tagRelation) {
		this._tagRelation = tagRelation;
	}

	public String getTagRelation() {
		return _tagRelation;
	}

	public void setSaveStatA(String saveStatA) {
		this._saveStatA = saveStatA;
	}

	public String getSaveStatA() {
		return _saveStatA;
	}

	public void setSaveStatB(String saveStatB) {
		this._saveStatB = saveStatB;
	}

	public String getSaveStatB() {
		return _saveStatB;
	}

	public void setSaveStatPi(String saveStatPi) {
		this._saveStatPi = saveStatPi;
	}

	public String getSaveStatPi() {
		return _saveStatPi;
	}

	public void setBrownWords(String brownWords) {
		this._brownWords = brownWords;
	}

	public String getBrownWords() {
		return _brownWords;
	}

	public void setSaveA(String saveA) {
		this._saveA = saveA;
	}

	public String getSaveA() {
		return _saveA;
	}

	public void setSaveB(String saveB) {
		this._saveB = saveB;
	}

	public String getSaveB() {
		return _saveB;
	}

	public void setSavePi(String savePi) {
		this._savePi = savePi;
	}

	public String getSavePi() {
		return _savePi;
	}

	public void setBrownWordsSerialized(String brownWordsSerialized) {
		this._brownWordsSerialized = brownWordsSerialized;
	}

	public String getBrownWordsSerialized() {
		return _brownWordsSerialized;
	}

	public void setPennTagImage(String pennTagImage) {
		this._pennTagImage = pennTagImage;
	}

	public String getPennTagImage() {
		return _pennTagImage;
	}

	public void setWebContentLocation(String webContentLocation) {
		this._webContentLocation = webContentLocation;
	}

	public String getWebContentLocation() {
		return _webContentLocation;
	}

	public void setWebContentLocationTemp(String _webContentLocationTemp) {
		this._webContentLocationTemp = _webContentLocationTemp;
	}

	public String getWebContentLocationTemp() {
		return _webContentLocationTemp;
	}
	
	public void setWebContentLocationData(String _webContentLocationData) {
		this._webContentLocationData = _webContentLocationData;
	}

	public String getWebContentLocationData() {
		return _webContentLocationData;
	}

	public void setWordNetLocation(String wordNetLocation) {
		this._wordNetLocation = wordNetLocation;
	}

	public String getWordNetLocation() {
		return _wordNetLocation;
	}

	public void setWebContentLocationSearch(String webContentLocationSearch) {
		this._webContentLocationSearch = webContentLocationSearch;
	}

	public String getWebContentLocationSearch() {
		return _webContentLocationSearch;
	}

	public void setWebContentLocationSeg(String webContentLocationSeg) {
		this._webContentLocationSeg = webContentLocationSeg;
	}

	public String getWebContentLocationSeg() {
		return _webContentLocationSeg;
	}
}
///:~