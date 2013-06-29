//:org.azeem.retrieval/NetConnector.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description This class will retrieve web content from the Internet when a url is given
 */
package edu.azeem.retrieval;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import edu.azeem.server.FilePaths;

public class NetConnector {
	
	private URL _url;
	private URL _responseUrl;
	private URLConnection _urlConnection;
	private HttpURLConnection _httpUrlConnection;
	private Map<String, List<String>> _headerMap;
	private int _responseCode;
	private int _contentLength;
	private int _bufferLength;
	private String _contentType;
	private String[] _contentTypeParts;
	private String _contentEncoding;
	private String _charset;
	//XXX private String _mimeType;							//Multi-purpose Internet Mail Extensions (MIME)
	private String _title;
	private File _file;
	private Writer _writer;
	private PrintWriter _printWriter;
	
	private InputStream _errorStream;
	private InputStream _contentStream;
	private Object _contentObject;
	private boolean available = false;
	
	private final int CONNECTION_TIME_OUT = 200000;		//Timeout limit
	private final int READ_TIME_OUT = 200000;			//Read timeout limit
	private final int K_BYTE = 1024;
	private final String FILE_EXT = ".html";
	private final String TEMP_URL = "http://www.google.lk/";
	//private final String KEY = "Googlebot/2.1 (+http://www.googlebot.com/bot.html)";	//Request is known with this name
	//private final String VALUE = "bot";					//The value associated with the request

	private final String KEY = "User-agent"; 			//Request is known with this name
	private final String VALUE = "";					//The value associated with the request
	//'User-agent', 'Mozilla/5.0'
	//Content Type: User-agent
	
	private static int taskCount = 0;
	private final int id = taskCount++; 
	/**
	 * Constructor initiates this object to grab the page
	 * @param url
	 */
	public NetConnector(URL url){
		String x = url.toString();
		try {
			this._url = new URL(x);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public NetConnector(){
		try {
			this._url = new URL(TEMP_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method has encapsulated approach to get the web content in step by step manner
	 */
	public String getContent(boolean isGoogleSearch){
		openConnection();
		sendRequest();
		getResponse();

		closeConnection();
		/*if(_contentObject == null)
			System.out.println(isGoogleSearch + " - ContentObject is null");
		else
			System.out.println(isGoogleSearch + " - Content Object is not null");
		*/
		return serializeContent(isGoogleSearch);
		
	}
	
	/**
	 * Initiate the process before sending the request
	 */
	protected void openConnection(){
		try {
			_urlConnection = _url.openConnection();					//this opens the connection using the _url object
			_httpUrlConnection = (HttpURLConnection)_urlConnection; //UrlConnection is a generic interface for several protocols. Java provides a special class to handle HTTP protocol connections. 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The request will be sent to the specified URL
	 */
	protected void sendRequest(){
		_httpUrlConnection.setConnectTimeout(CONNECTION_TIME_OUT);	//If the web server fails to responds to the connection within the given time, fail
		_httpUrlConnection.setReadTimeout(READ_TIME_OUT);			//If web server fails to respond to the request with the web file, fail
		_httpUrlConnection.setInstanceFollowRedirects(true);		//HTTP redirects is enabled
		_httpUrlConnection.setRequestProperty(KEY, VALUE);			//Sets general request property
		
		available = true;
		try {
			_httpUrlConnection.connect();
		} catch (IOException e) {
			System.out.println("Page does not available");
			available = false;
		}
	}
	
	/**
	 * Gets the response from the web
	 */
	protected void getResponse(){		

		try{
			if(available){
				setHeaderMap(_httpUrlConnection.getHeaderFields());
				//XXX printHeaderMap();
				setResponseCode(_httpUrlConnection.getResponseCode());	//Status codes of HTTP response message
				setResponseUrl(_httpUrlConnection.getURL());
				_contentType = _httpUrlConnection.getContentType();
				setContentEncoding(_httpUrlConnection.getContentEncoding());
				_contentLength = _httpUrlConnection.getContentLength();
			}
		}catch (IOException e){
			
		}
		
		if(_contentType != null && available){
			_contentTypeParts = _contentType.split(";"); 				//Sample content type looks like this >> "type/subtype; charset=set"
			//XXX _mimeType = _contentTypeParts[0].trim();
			//XXX System.out.println(_mimeType);
			for(int i=1;i<_contentTypeParts.length && _charset == null;i++){
				String text = _contentTypeParts[i].trim();
				int index = text.toLowerCase().indexOf("charset=");
				if(index != -1)
					_charset = text.substring(index+8);
				//XXX System.out.println(_charset);
			}
		}
		else
			System.err.println("Content Type is null");
		
		if(available){
			retrieveContent();
		}
		else
			System.err.println("Page is not available");
	}
	
	/**
	 * This method retrieves the web content which client expects 
	 */
	protected void retrieveContent(){
		_errorStream = _httpUrlConnection.getErrorStream();
		try{
			if(_errorStream == null){		//No error is found
				/*
				 * Content can be in any format(text, image, etc).. Since URLConnection is a generic type and
				 * HttpURLConnection inherits it's super class's(URLConnection) getContent() method,
				 * is simply returns an Object
				 */
				//Class type[] = {String.class, Reader.class, URLConnection.class};
				_contentObject = _httpUrlConnection.getContent();
				_contentStream = (InputStream) _contentObject;			//The _contentObject is an InputSteram object
				_contentObject = readInputStream();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}	
	
	/**
	 * Reads the content
	 * @return Object class
	 * @throws IOException
	 */
	private Object readInputStream() throws IOException {
		
		/*
		 * inputStream.available() returns an estimate of the number of bytes that can be read (or skipped over)
		 * from this input stream without blocking by the next invocation of a method for this input stream
		 */
		_bufferLength = Math.max(K_BYTE, Math.max(_contentLength, _contentStream.available()));
		byte[] buffer = new byte[_bufferLength];
	    byte[] bytes = null;
	    byte[] newBytes = null;
	    for (int i = _contentStream.read(buffer); i != -1; i = _contentStream.read(buffer)){
	    	if (bytes == null) {
	            bytes = buffer;
	            buffer = new byte[_bufferLength];
	            continue;
	        }
	        newBytes = new byte[bytes.length + i];
	        System.arraycopy(bytes, 0, newBytes, 0, bytes.length );
	        System.arraycopy(buffer, 0, newBytes, bytes.length, i );
	        bytes = newBytes;
	    }
	    if (_charset == null){					//character set (encoding) name for text content
	        return bytes;
	    }
	    try {
	        return new String(bytes, _charset); //Convert the bytes in to characters where  
	    }
	    catch (UnsupportedEncodingException e){ 
	    	//e.printStackTrace();
	    }
	    return bytes;
	}
	
	/**
	 * This will save the content to hard drive
	 * @return
	 */
	protected String serializeContent(boolean isGoogleSearch) {
		if(!available){
			return _url.getHost();
		}
		//XXX String title = _url.toString().split("\\.")[1].trim();
		if(isGoogleSearch)
			_file = new File(FilePaths.getUniqueInstance().getWebContentLocationSearch() + "/" + id + _url.getHost() + FILE_EXT);
		else
			_file = new File(FilePaths.getUniqueInstance().getWebContentLocationTemp() + "/" + id + _url.getHost() + FILE_EXT);
			
		try {
			_writer = new FileWriter(_file);
			_printWriter = new PrintWriter(_writer);
			_printWriter.print(_contentObject);
			_writer.close();
			_printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id + _url.getHost();

	}
	
	public boolean cleanUpContents(){
		File contentLocation = new File(FilePaths.getUniqueInstance().getWebContentLocationTemp());
		File[] contentFiles = null;
		
		if(contentLocation.isDirectory()){
			contentFiles = contentLocation.listFiles();
			for(int i = 0; i < contentFiles.length; i++){
				contentFiles[i].delete();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean cleanUpData(){
		File dataLocation = new File(FilePaths.getUniqueInstance().getWebContentLocationData());
		File[] dataFiles = null;
		
		if(dataLocation.isDirectory()){
			dataFiles = dataLocation.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
				dataFiles[i].delete();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean cleanUpSearch(){
		File dataLocation = new File(FilePaths.getUniqueInstance().getWebContentLocationSearch());
		File[] dataFiles = null;
		
		if(dataLocation.isDirectory()){
			dataFiles = dataLocation.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
				dataFiles[i].delete();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean cleanUpSeg(){
		File dataLocation = new File(FilePaths.getUniqueInstance().getWebContentLocationSeg());
		File[] dataFiles = null;
		
		if(dataLocation.isDirectory()){
			dataFiles = dataLocation.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
				dataFiles[i].delete();
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Close the connection
	 */
	protected void closeConnection(){
		_httpUrlConnection.disconnect();
	}

	private void setResponseCode(int _responseCode) {
		this._responseCode = _responseCode;
	}

	public int getResponseCode() {
		return _responseCode;
	}

	private void setContentEncoding(String _contentEncoding) {
		this._contentEncoding = _contentEncoding;
	}

	public String getContentEncoding() {
		return _contentEncoding;
	}

	public String getTitle() {
		return _title;
	}

	private void setHeaderMap(Map<String, List<String>> _headerMap) {
		this._headerMap = _headerMap;
	}

	public Map<String, List<String>> getHeaderMap() {
		return _headerMap;
	}

	private void setResponseUrl(URL _responseUrl) {
		this._responseUrl = _responseUrl;
	}

	public URL getResponseUrl() {
		return _responseUrl;
	}
	
	protected void printHeaderMap(){
		for(int i = 0; i <_headerMap.size(); i++){
			System.out.println(_headerMap.values().toArray()[i].toString());
		}
	}
}
///:~