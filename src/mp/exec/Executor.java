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
	
	//public static final String path = "E:/WikiDumps/unzipped/de/dewiki-20140615-pages-articles/dewiki_articles.xml";//"D:/1M Mannheim/Master Thesis/WikiData/enwiki/enwiki_20140502_pages_articles1.xml";//"D:/1M Mannheim/Master Thesis/WikiData/frwiki/frwiki-20140521-pages-articles1.xml";//
	//public static final String dumpPath = "E:/WikiMappingsOutput/stats.csv";

	//public static final String dumpJson = "E:/WikiMappingsOutput/PagesAsJson.txt";
	//public static final String StatsJson = "E:/WikiMappingsOutput/Stats.csv";
	
	public static final String[] pageSetsToAnalyze = new String[] {
		"E:/WikiDumps/unzipped/fr/frwiki-20140626-pages-articles/frwiki_articles.xml",
		"E:/WikiDumps/unzipped/nl/nlwiki-20140630-pages-articles/nlwiki_articles.xml",
		"E:/WikiDumps/unzipped/es/eswiki-20140613-pages-articles/eswiki_articles.xml",
		"E:/WikiDumps/unzipped/ru/dewiki-20140615-pages-articles/ruwiki_articles.xml",
		"E:/WikiDumps/unzipped/it/itwiki-20140612-pages-articles/itwiki_articles.xml"
		};
	
	public static final String[] dumpsToJson = new String[] {
		"E:/WikiMappingsOutput/FR_PagesAsJson.txt",
		"E:/WikiMappingsOutput/NL_PagesAsJson.txt",
		"E:/WikiMappingsOutput/ES_PagesAsJson.txt",
		"E:/WikiMappingsOutput/RU_PagesAsJson.txt",
		"E:/WikiMappingsOutput/IT_PagesAsJson.txt"
		};
	
	public static final String[] StatsJson = new String[] {
		"E:/WikiMappingsOutput/FR_Stats.csv",
		"E:/WikiMappingsOutput/NL_Stats.csv",
		"E:/WikiMappingsOutput/ES_Stats.csv",
		"E:/WikiMappingsOutput/RU_Stats.csv",
		"E:/WikiMappingsOutput/IT_Stats.csv"
		};
	
	public static final String[] Logs = new String[] {
		"E:/WikiMappingsOutput/FR_log.txt",
		"E:/WikiMappingsOutput/NL_log.txt",
		"E:/WikiMappingsOutput/ES_log.txt",
		"E:/WikiMappingsOutput/RU_log.txt",
		"E:/WikiMappingsOutput/IT_log.txt"
		};
	
	public static void main(String[] args) {
		
		for (int i=0;i<pageSetsToAnalyze.length;i++){
			extractAndDumpPages(pageSetsToAnalyze[i], dumpsToJson[i], Logs[i]);			
			HashMap<String, WikiPage> wikiData = retrieveDumpedPages(dumpsToJson[i]);
			getStatistics(wikiData, StatsJson[i]);
		}
	}
	
	private static void extractAndDumpPages(String pagesLocation, String dumpLocation, String logpath) {
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();		
		System.out.println("Started reading file: "+pagesLocation);
		WikiDataExtractor extractor = new WikiDataExtractor(pagesLocation, logpath);
		extractor.setIgnoreUnnamedAttributes(true);//Strict mode since unnamed attributes cause ambiguity
		extractor.setNamesToLowerCase(true);
		
		try {
			extractor.readAndDumpWikidata(dumpLocation);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileTooLargeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		extractor = null;
	}
	
	private static HashMap<String, WikiPage> extractPages(String path, String logpath) {
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();		
		System.out.println("Started reading file: "+path);
		WikiDataExtractor extractor = new WikiDataExtractor(path, logpath);
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
	
	private static void dumpPages(HashMap<String, WikiPage> wikiData, String dumpJson) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		jsonIOManager.writeToJson(wikiData, dumpJson);
	}
	
	private static HashMap<String, WikiPage> retrieveDumpedPages(String dumpJson) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();
		try {
			wikiData = jsonIOManager.readFromJson(dumpJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wikiData;
	}
	
	private static void getStatistics(HashMap<String, WikiPage> wikiData, String dumpStats) {
		InfoboxStatistics analyzer = new InfoboxStatistics(wikiData);
		HashMap<String, List<Infobox>> stats = analyzer.getStatistics();
		System.out.println("Number of classes: "+analyzer.getNumberOfClasses());
		FileIO.writeToFile(dumpStats, "", false);
		for (Map.Entry<String, List<Infobox>> list : stats.entrySet()) {
			FileIO.writeToFile(dumpStats, list.getKey()+", "+list.getValue().size()+"\n", true);
		}
		System.out.println("Done");
	}
}
