package mp.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.LanguageCodes;
import mp.dataclasses.WikiLink;
import mp.dataclasses.WikiPage;
import mp.exceptions.FileTooLargeException;
import mp.global.GlobalVariables;
import mp.io.dataclasses.InfoboxExtractionObject;
import mp.io.utils.Converter;
import mp.io.utils.Parser;

import org.xml.sax.SAXException;

//TODO Create utils: Parser class, Converter class

/**
 * 
 * @author Evgeny Mitichkin
 *
 */
public class WikiDataExtractor {
	
	public static final String dumpPath = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/chunks.txt";
	
	@SuppressWarnings("unused")
	private static final String pageBeginTag = "<page";
	private static final String pageEndTag = "</page>";
	
	private String path;
	private boolean ignoreUnnamedAttributes;
	private boolean namesToLowerCase;
	
	public static final int chunkSize = 1024*1024*50;//100 MBytes
	
	public WikiDataExtractor(String path) {
		this.path = path;
		this.ignoreUnnamedAttributes = false;
		this.setNamesToLowerCase(false);
	}
	
	/**
	 * Reads the wikidata and returns a {@link HashSet} of {@link Infobox} wrapped objects with their attributes and properties
	 * @param path Path to the Wikidata
	 * @return {@code HashMap<String, Infobox>}
	 * @throws IOException
	 * @throws FileTooLargeException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public HashMap<String, WikiPage> readWikidata() throws IOException, FileTooLargeException, ParserConfigurationException, SAXException  {
		HashMap<String, WikiPage> result = new HashMap<String, WikiPage>();
		
		FileInputStream fis = new FileInputStream(path);
		FileChannel fc = fis.getChannel();
		ByteBuffer bb = ByteBuffer.allocate(chunkSize);
		
		int readingIterationsPassed = 0;//number of iterations
		String remainderString = "";//Part of string that possibly contains valuable data. Should be merged with the string in next iteration
		
		long bytesTotal = 0;
		long len = 0;
		
		while((len = fc.read(bb)) != -1) {
			bytesTotal +=len;
			bb.flip();
			String wikiDataAsString = new String(bb.array(), "UTF-8");
			bb.clear();	
			
			//Adding the last chunk that was not properly processed
			if (readingIterationsPassed>0) {
				wikiDataAsString = remainderString + wikiDataAsString;
				remainderString = "";
			}
			
			//Calculate the index of the closing tag of last complete page to be extracted in a chunk
			int lastCompletePageEndingIndex = wikiDataAsString.lastIndexOf(pageEndTag) + pageEndTag.length();
			remainderString = wikiDataAsString.substring(lastCompletePageEndingIndex, wikiDataAsString.length());
			wikiDataAsString = wikiDataAsString.substring(0, lastCompletePageEndingIndex);
			if (readingIterationsPassed>0) {
				wikiDataAsString = Parser.completeChunk(wikiDataAsString, true);
			} else {
				wikiDataAsString = Parser.completeChunk(wikiDataAsString, false);
			}
			
			HashMap<String, WikiPage> pagesPerIteration = Converter.extractPages(wikiDataAsString, ignoreUnnamedAttributes, isNamesToLowerCase());
			
			result.putAll(pagesPerIteration);
			
			if (GlobalVariables.IS_DEBUG) {
				System.out.println("Iteration "+readingIterationsPassed+" finished. "+ wikiDataAsString.length() +" bytes processed." );
			}
			readingIterationsPassed ++;
		}
		
		fc.close();
		fis.close();
				
		if (GlobalVariables.IS_DEBUG) {
			System.out.println("Total " +bytesTotal+ " bytes processed. ("+(double)bytesTotal/(1024*1024*1024)+" GBytes)" );
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private String read() throws IOException, FileTooLargeException {
		byte[] wikiData = Files.readAllBytes(Paths.get(getPath()));//Do this in chunks!
		if (wikiData.length > Integer.MAX_VALUE)
			throw new FileTooLargeException("File size is "+ wikiData.length + " bytes when " + Integer.MAX_VALUE+ " is allowed");
		String wikiDataAsString = new String(wikiData, "UTF-8");
		wikiData = null;
		return wikiDataAsString;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isIgnoreUnnamedAttributes() {
		return ignoreUnnamedAttributes;
	}

	public void setIgnoreUnnamedAttributes(boolean ignoreUnnamedInfoboxes) {
		this.ignoreUnnamedAttributes = ignoreUnnamedInfoboxes;
	}

	public boolean isNamesToLowerCase() {
		return namesToLowerCase;
	}

	public void setNamesToLowerCase(boolean namesToLowerCase) {
		this.namesToLowerCase = namesToLowerCase;
	}
}