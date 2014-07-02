package mp.exec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxStatistics;
import mp.dataclasses.WikiPage;
import mp.exceptions.FileTooLargeException;
import mp.io.FileIO;
import mp.io.WikiDataExtractor;

public class Executor {
	
	public static final String path = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/enwiki_20140502_pages_articles1.xml";//"D:/1M Mannheim/Master Thesis/WikiData/frwiki/frwiki-20140521-pages-articles1.xml";//
	public static final String dumpPath = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/stats.csv";

	public static final String dumpJson = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/PagesAsJson.txt";
	public static final String StatsJson = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/Stats.csv";
	
	public static HashMap<String, WikiPage> wikiData = new HashMap<String, WikiPage>();
	
	public static void main(String[] args) {
		System.out.println("Started reading file: "+path);
		WikiDataExtractor extractor = new WikiDataExtractor(path);
		extractor.setIgnoreUnnamedAttributes(true);//Strict mode since unnamed attributes cause ambiguity
		extractor.setNamesToLowerCase(true);
		
		try {
			wikiData = extractor.readWikidata();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Reading complete. Infoboxes found: " +wikiData.size());
		Gson gson = new Gson();
		String jsonStr = gson.toJson(wikiData);
		FileIO.writeToFile(dumpJson, jsonStr, false);
		 
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
