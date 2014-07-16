package mp.global;

/**
 * Stores the variables used in around the whole project, including settings, parsing tags,
 * algorithm parameters
 * @author Evgeny Mitichkin
 *
 */
public class GlobalVariables {
	public static final boolean IS_DEBUG = true;
	
	/**
	 * Location of WikiILLs file
	 */
	public static final String[] wikiLangLinks = new String[] {
		"G:/WikiDumps/unzipped/en/enwiki-20140614-langlinks/enwiki-20140614-langlinks.sql",
		"G:/WikiDumps/unzipped/de/dewiki-20140615-langlinks/dewiki-20140615-langlinks.sql",
		"G:/WikiDumps/unzipped/fr/frwiki-20140626-langlinks/frwiki-20140626-langlinks.sql",
		"G:/WikiDumps/unzipped/nl/nlwiki-20140630-langlinks/nlwiki-20140630-langlinks.sql"
		};
	
	/**
	 * Location of WikiILLs to be stored
	 */
	public static final String[] wikiLangLinksTransformed =  new String[] {
		"G:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_en.json",
		"G:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_de.json",
		"G:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_fr.json",
		"G:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_nl.json",
		};
	
	/**
	 * Location of WikiILLs to be stored
	 */
	public static final String[] wikiLangLinksFiltered =  new String[] {
		"G:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_en.json",
		"G:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_de.json",
		"G:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_fr.json",
		"G:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_nl.json",
		};
	
	/**
	 * Location of extraction process log
	 */
	public static String[] wikiDataExtractionLog =  new String[] {"G:/wikiDataExtractionLog.txt"};
	
	/**
	 * Locations of WikiData files to be processed
	 */
	public static final String[] pageSetsToAnalyze = new String[] {
		"G:/WikiDumps/unzipped/en/enwiki-20140614-pages-articles/enwiki_articles.xml",
		"G:/WikiDumps/unzipped/de/dewiki-20140615-pages-articles/dewiki_articles.xml",
		"G:/WikiDumps/unzipped/fr/frwiki-20140626-pages-articles/frwiki_articles.xml",
		"G:/WikiDumps/unzipped/nl/nlwiki-20140630-pages-articles/nlwiki_articles.xml",
		"G:/WikiDumps/unzipped/es/eswiki-20140613-pages-articles/eswiki_articles.xml",
		"G:/WikiDumps/unzipped/ru/ruwiki-20140617-pages-articles/ruwiki_articles.xml",
		"G:/WikiDumps/unzipped/it/itwiki-20140612-pages-articles/itwiki_articles.xml"
		};
	
	/**
	 * Locations of processed dumps of WikiData
	 */
	public static final String[] dumpsToJson = new String[] {
		"G:/WikiMappingsOutput/Raw extracted data/Wiki EN/withIDs/EN_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki DE/withIDs/DE_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki FR/withIDs/FR_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki NL/withIDs/NL_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki ES/withIDs/ES_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki RU/withIDs/RU_PagesAsJson.txt",
		"G:/WikiMappingsOutput/Raw extracted data/Wiki IT/withIDs/IT_PagesAsJson.txt"
		};
	
	/**
	 * Locations Graph-ready data
	 */
	public static final String[] dumpsToJson4Graph = new String[] {
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/EN_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/DE_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/FR_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/NL_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/ES_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/RU_PagesAsJson4Graph.json4g",
		"G:/WikiMappingsOutput/Graph-ready data/withIDs/IT_PagesAsJson4Graph.json4g"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson = new String[] {
		"G:/WikiMappingsOutput/Statistics/EN_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/DE_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/FR_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/NL_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/ES_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/RU_Stats.csv",
		"G:/WikiMappingsOutput/Statistics/IT_Stats.csv"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJsonRaw = new String[] {
		"G:/WikiMappingsOutput/Statistics/Raw/EN/",
		"G:/WikiMappingsOutput/Statistics/Raw/DE/",
		"G:/WikiMappingsOutput/Statistics/Raw/FR/",
		"G:/WikiMappingsOutput/Statistics/Raw/NL/",
		"G:/WikiMappingsOutput/Statistics/Raw/ES/",
		"G:/WikiMappingsOutput/Statistics/Raw/RU/",
		"G:/WikiMappingsOutput/Statistics/Raw/IT/"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson4g = new String[] {
		"G:/WikiMappingsOutput/Statistics/EN/",
		"G:/WikiMappingsOutput/Statistics/DE/",
		"G:/WikiMappingsOutput/Statistics/FR/",
		"G:/WikiMappingsOutput/Statistics/NL/",
		"G:/WikiMappingsOutput/Statistics/ES/",
		"G:/WikiMappingsOutput/Statistics/RU/",
		"G:/WikiMappingsOutput/Statistics/IT/"
		};
	
	/**
	 * Locations for logging the system events for each dataset
	 */
	public static final String[] Logs = new String[] {
		"G:/WikiMappingsOutput/Logs/EN_log.txt",
		"G:/WikiMappingsOutput/Logs/DE_log.txt",
		"G:/WikiMappingsOutput/Logs/FR_log.txt",
		"G:/WikiMappingsOutput/Logs/NL_log.txt",
		"G:/WikiMappingsOutput/Logs/ES_log.txt",
		"G:/WikiMappingsOutput/Logs/RU_log.txt",
		"G:/WikiMappingsOutput/Logs/IT_log.txt"
		};	
	
	/**
	 * Language editions used in the analysis. This value is used in filtering.
	 */
	public static final String[] allowedLanguages = new String[] {
		"en",
		"de",
		"fr",
		"nl" };	
}
