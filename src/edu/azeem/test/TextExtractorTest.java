package edu.azeem.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import edu.azeem.retrieval.TextExtractor;
import edu.azeem.server.FilePaths;

public class TextExtractorTest {

	private final static String FILE_EXT = ".html";
	
	@Test
	public void testExtract() throws IOException {
		File files = new File(FilePaths.getUniqueInstance().getWebContentLocationTemp());
		File[] dataFiles = null;
		
		if(files.isDirectory())
			dataFiles = files.listFiles();
		
		for(File aDataFile : dataFiles){
			TextExtractor.extract(aDataFile.getName().split(FILE_EXT)[0], true);
		}
	}

}
