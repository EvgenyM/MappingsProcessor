package mp.exec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import mp.dataclasses.ILLTypes;
import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxSetStatistics;
import mp.dataclasses.WikiDataStatistics;
import mp.dataclasses.WikiPage;
import mp.dataclasses.WikiPage4Graph;
import mp.exceptions.FileTooLargeException;
import mp.exceptions.PageConversionException;
import mp.io.FileIO;
import mp.io.JsonIOManager;
import mp.io.dataclasses.Page4GraphMapEntry;
import mp.io.dataclasses.PageMapEntry;
import mp.preprocessing.Infobox4GraphFactory;
import mp.preprocessing.WikiDataExtractor;

import org.xml.sax.SAXException;

/**
 * This class provides a set of high-level methods for WikiData extraction, cleansing, 
 * transformation and load (ETL).
 * @author Evgeny Mitichkin
 *
 */
public class WikiDataProcessor{
	
	public WikiDataProcessor() {
		
	}
	
	/**
	 * Generates statistics for a given set of {@link WikiPage4Graph} objects
	 * @param inputDataPaths Graph-ready data
	 * @param statisticsDumpPath Path to save statistics to 
	 */
	public void getStatsForGraph(String[] inputDataPaths, String[] statisticsDumpPath) {
		for (int i=0;i<inputDataPaths.length;i++) {
			HashMap<String, WikiPage4Graph> wikiData4Graph = retrieveDumpedPages(inputDataPaths[i], Page4GraphMapEntry.class);
			getStatistics4Graph(wikiData4Graph, statisticsDumpPath[i]);
			wikiData4Graph = null;
		}
	}
	
	/**
	 * Prepares a compressed version of the WikiData ready for building the graph
	 * @param transformedDataPath
	 * @param transformedDataDumpPath
	 * @param selectionType Determines which WikiPage entities should be taken (only with main ILLs, including featured/good ILLs, only featured ILLs and etc.)
	 */
	public void compress4Graph(String[] transformedDataPath, String[] transformedDataDumpPath, ILLTypes selectionType) {
		for (int i=0; i<transformedDataPath.length; i++) {
			//Read the data chunk by chunk
			//Process the data read --> transform it to WikiPage4Graph format
			JsonIOManager fileReader = new JsonIOManager();
			Infobox4GraphFactory converter = Infobox4GraphFactory.getInstance();
			converter.setSelectorType(selectionType);
			try {
				fileReader.readFromJson(transformedDataPath[i], converter, Page4GraphMapEntry.class);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (PageConversionException e) {
				System.out.println("Conversion for " + transformedDataPath[i] + " failed");
				e.printStackTrace();
			}
			
			//Dump the resulting data
			dumpPages(converter.getmPageSet(), transformedDataDumpPath[i]);
			//Release resources
			converter = null;
		}
	}
	
	/**
	 * Performs initial transformation of WikiData to {@link WikiPage} objects.
	 * Reads the file chunk by chunk, extracts Interlingual links, Infoboxes with their attributes,
	 * and generates statistics for retrieved dataset.
	 */
	public void makeETL(String[] rawDataPaths, String[] transformedDataDumpPath, String[] statisticsDumpPath, String[] logPaths) {
		for (int i=0;i<rawDataPaths.length;i++){
			extractAndDumpPages(rawDataPaths[i], transformedDataDumpPath[i], logPaths[i]);			
			HashMap<String, WikiPage> wikiData = retrieveDumpedPages(transformedDataDumpPath[i], PageMapEntry.class);
			getStatistics(wikiData, statisticsDumpPath[i]);
		}
	}
	
	/**
	 * Extracts the {@link WikiPage}s from raw WikiData and dumps them to a given location by parts
	 * @param pagesLocation
	 * @param dumpLocation
	 * @param logpath
	 */
	private void extractAndDumpPages(String pagesLocation, String dumpLocation, String logpath) {
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
	
	/**
	 * Extracts the {@link WikiPage}s from raw WikiData
	 * @param path
	 * @param logpath
	 * @return
	 */
	@SuppressWarnings("unused")
	private  HashMap<String, WikiPage> extractPages(String path, String logpath) {
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
	
	/**
	 * Dumps set of {@link WikiPage}s to a given path
	 * @param <K>
	 * @param <V>
	 * @param wikiData
	 * @param dumpJson
	 */
	private <K, V> void dumpPages(HashMap<K, V> wikiData, String dumpJson) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		jsonIOManager.writeToJson(wikiData, dumpJson);
	}
	
	/**
	 * Reads a set of {@link WikiPage}s
	 * @param <K>
	 * @param <V>
	 * @param <T>
	 * @param dumpJson
	 * @return
	 */
	private <K, V, T> HashMap<K, V> retrieveDumpedPages(String dumpJson, Class<T> classOfPage) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		HashMap<K, V> wikiData = new HashMap<K, V>();
		try {
			wikiData = jsonIOManager.readFromJson(dumpJson, classOfPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wikiData;
	}
	
	/**
	 * Generates statistics for a given set of {@link WikiPage}s
	 * @param wikiData
	 * @param dumpStats
	 */
	private void getStatistics(HashMap<String, WikiPage> wikiData, String dumpStats) {
		InfoboxSetStatistics analyzer = new InfoboxSetStatistics(wikiData);
		HashMap<String, List<Infobox>> stats = analyzer.getStatistics();
		System.out.println("Number of classes: "+analyzer.getNumberOfClasses());
		FileIO.writeToFile(dumpStats, "", false);
		for (Map.Entry<String, List<Infobox>> list : stats.entrySet()) {
			FileIO.writeToFile(dumpStats, list.getKey()+", "+list.getValue().size()+"\n", true);
		}
		System.out.println("Done");
	}

	/**
	 * Generates statistics for a given set of {@link WikiPage4Graph}s
	 * @param wikiData
	 * @param dumpStats
	 */
	private <V> void getStatistics4Graph(HashMap<String, V> wikiData, String dumpStats) {
		WikiDataStatistics<V> stats = new WikiDataStatistics<>(wikiData);
		HashMap<String, Integer> ibxStats = stats.getInfoboxStatistics();
		String path1 = dumpStats + "StatsIbox4g.csv";
		StringBuilder bldr = new StringBuilder();
		long IBXsTotal = 0;
		for (Map.Entry<String, Integer> ibx : ibxStats.entrySet()) {
			IBXsTotal += ibx.getValue();
			bldr.append(ibx.getKey()+", "+ibx.getValue()+"\n");
		}
		FileIO.writeToFile(path1, "Number of IBXs (total): "+IBXsTotal+"\n", false);
		FileIO.writeToFile(path1, "Pages per infobox: "+(float)IBXsTotal/(float)ibxStats.size()+"\n", true);
		FileIO.writeToFile(path1, "Name,Value\n", true);
		FileIO.writeToFile(path1, bldr.toString(), true);
		ibxStats = null;
		path1 = dumpStats + "StatsILLs4g.csv";
		bldr = new StringBuilder();
		HashMap<String, Integer> ILLStats = stats.getILLStatistics();
		long ILLsTotal = 0;
		for (Map.Entry<String, Integer> ibx : ILLStats.entrySet()) {
			ILLsTotal += ibx.getValue();
			bldr.append(ibx.getKey()+", "+ibx.getValue()+"\n");
		}
		FileIO.writeToFile(path1, "Number of ILLs (total): "+ILLsTotal+"\n", false);
		FileIO.writeToFile(path1, "Number of ILLs (per page): "+(float)ILLsTotal/(float)ILLStats.size()+"\n", true);
		FileIO.writeToFile(path1, "\n", true);
		FileIO.writeToFile(path1, bldr.toString(), true);
		System.out.println("Done");
		stats = null;
	}	
}
