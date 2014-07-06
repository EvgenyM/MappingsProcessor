package mp.exec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxStatistics;
import mp.dataclasses.WikiPage;
import mp.exceptions.FileTooLargeException;
import mp.io.FileIO;
import mp.io.JsonIOManager;
import mp.io.WikiDataExtractor;

import org.xml.sax.SAXException;

public class Executor {
	
	public static final String path = "E:/WikiDumps/unzipped/en/enwiki-20140614-pages-articles/enwiki_articles.xml";//"D:/1M Mannheim/Master Thesis/WikiData/enwiki/enwiki_20140502_pages_articles1.xml";//"D:/1M Mannheim/Master Thesis/WikiData/frwiki/frwiki-20140521-pages-articles1.xml";//
	public static final String dumpPath = "E:/WikiMappingsOutput/stats.csv";

	public static final String dumpJson = "E:/WikiMappingsOutput/PagesAsJson.txt";
	public static final String StatsJson = "E:/WikiMappingsOutput/Stats.csv";
	
	
	
	public static void main(String[] args) {
		//HashMap<String, WikiPage> wikiDataRaw = extractPages();
		//dumpPages(wikiDataRaw);
		extractAndDumpPages();
		
		HashMap<String, WikiPage> wikiData = retrieveDumpedPages();
		getStatistics(wikiData);
	}
	
	private static void extractAndDumpPages() {
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();		
		System.out.println("Started reading file: "+path);
		WikiDataExtractor extractor = new WikiDataExtractor(path);
		extractor.setIgnoreUnnamedAttributes(true);//Strict mode since unnamed attributes cause ambiguity
		extractor.setNamesToLowerCase(true);
		
		try {
			extractor.readAndDumpWikidata(dumpJson);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileTooLargeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
	
	private static HashMap<String, WikiPage> extractPages() {
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();		
		System.out.println("Started reading file: "+path);
		WikiDataExtractor extractor = new WikiDataExtractor(path);
		extractor.setIgnoreUnnamedAttributes(true);//Strict mode since unnamed attributes cause ambiguity
		extractor.setNamesToLowerCase(true);
		
		try {
			wikiData = extractor.readWikidata();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileTooLargeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		System.out.println("Reading complete. Infoboxes found: " + wikiData.size());
		
		return wikiData;
	}
	
	private static void dumpPages(HashMap<String, WikiPage> wikiData) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		jsonIOManager.writeToJson(wikiData, dumpJson);
	}
	
	private static HashMap<String, WikiPage> retrieveDumpedPages() {
		JsonIOManager jsonIOManager = new JsonIOManager();
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();
		try {
			wikiData = jsonIOManager.readFromJson(dumpJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wikiData;
	}
	
	private static void getStatistics(HashMap<String, WikiPage> wikiData) {
		InfoboxStatistics analyzer = new InfoboxStatistics(wikiData);
		HashMap<String, List<Infobox>> stats = analyzer.getStatistics();
		System.out.println("Number of classes: "+analyzer.getNumberOfClasses());
		FileIO.writeToFile(StatsJson, "", false);
		for (Map.Entry<String, List<Infobox>> list : stats.entrySet()) {
			FileIO.writeToFile(StatsJson, list.getKey()+", "+list.getValue().size()+"\n", true);
		}
		System.out.println("Done");
	}
}
