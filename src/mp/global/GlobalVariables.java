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
		"J:/WikiDumps/unzipped/en/enwiki-20140614-langlinks/enwiki-20140614-langlinks.sql",
		"J:/WikiDumps/unzipped/de/dewiki-20140615-langlinks/dewiki-20140615-langlinks.sql",
		"J:/WikiDumps/unzipped/fr/frwiki-20140626-langlinks/frwiki-20140626-langlinks.sql",
		"J:/WikiDumps/unzipped/nl/nlwiki-20140630-langlinks/nlwiki-20140630-langlinks.sql"
		};
	
	/**
	 * Location of WikiILLs to be stored
	 */
	public static final String[] wikiLangLinksTransformed =  new String[] {
		"J:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_en.json",
		"J:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_de.json",
		"J:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_fr.json",
		"J:/WikiMappingsOutput/Additional ILLs/Complete/ILLs_from_nl.json",
		};
	
	/**
	 * Location of WikiILLs to be stored
	 */
	public static final String[] wikiLangLinksFiltered =  new String[] {
		"J:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_en.json",
		"J:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_de.json",
		"J:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_fr.json",
		"J:/WikiMappingsOutput/Additional ILLs/Filtered/ILLs_from_nl.json",
		};
	
	/**
	 * Location of extraction process log
	 */
	public static String[] wikiDataExtractionLog =  new String[] {"J:/wikiDataExtractionLog.txt"};
	
	/**
	 * Locations of WikiData files to be processed
	 */
	public static final String[] pageSetsToAnalyze = new String[] {
		"J:/WikiDumps/unzipped/en/enwiki-20140614-pages-articles/enwiki_articles.xml",
		"J:/WikiDumps/unzipped/de/dewiki-20140615-pages-articles/dewiki_articles.xml",
		"J:/WikiDumps/unzipped/fr/frwiki-20140626-pages-articles/frwiki_articles.xml",
		"J:/WikiDumps/unzipped/nl/nlwiki-20140630-pages-articles/nlwiki_articles.xml",
		"J:/WikiDumps/unzipped/es/eswiki-20140613-pages-articles/eswiki_articles.xml",
		"J:/WikiDumps/unzipped/ru/ruwiki-20140617-pages-articles/ruwiki_articles.xml",
		"J:/WikiDumps/unzipped/it/itwiki-20140612-pages-articles/itwiki_articles.xml"
		};
	
	/**
	 * Locations of processed dumps of WikiData
	 */
	public static final String[] dumpsToJson = new String[] {
		"J:/WikiMappingsOutput/Raw extracted data/Wiki EN/withIDs/EN_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki DE/withIDs/DE_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki FR/withIDs/FR_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki NL/withIDs/NL_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki ES/withIDs/ES_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki RU/withIDs/RU_PagesAsJson.txt",
		"J:/WikiMappingsOutput/Raw extracted data/Wiki IT/withIDs/IT_PagesAsJson.txt"
		};
	
	/**
	 * Locations Graph-ready data
	 */
	public static final String[] dumpsToJson4Graph = new String[] {
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/EN_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/DE_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/FR_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/NL_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/ES_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/RU_PagesAsJson4Graph.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/IT_PagesAsJson4Graph.json4g"
		};
	
	/**
	 * Locations Graph-ready data
	 */
	public static final String[] dumpsToJson4GraphMergedWithILLs = new String[] {
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/EN_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/DE_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/FR_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/NL_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/ES_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/RU_PagesAsJson4GraphMergedWithILLs.json4g",
		"J:/WikiMappingsOutput/Graph-ready data/withIDs/IT_PagesAsJson4GraphMergedWithILLs.json4g"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson = new String[] {
		"J:/WikiMappingsOutput/Statistics/EN_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/DE_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/FR_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/NL_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/ES_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/RU_Stats.csv",
		"J:/WikiMappingsOutput/Statistics/IT_Stats.csv"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJsonRaw = new String[] {
		"J:/WikiMappingsOutput/Statistics/Raw/EN/",
		"J:/WikiMappingsOutput/Statistics/Raw/DE/",
		"J:/WikiMappingsOutput/Statistics/Raw/FR/",
		"J:/WikiMappingsOutput/Statistics/Raw/NL/",
		"J:/WikiMappingsOutput/Statistics/Raw/ES/",
		"J:/WikiMappingsOutput/Statistics/Raw/RU/",
		"J:/WikiMappingsOutput/Statistics/Raw/IT/"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson4g = new String[] {
		"J:/WikiMappingsOutput/Statistics/EN/",
		"J:/WikiMappingsOutput/Statistics/DE/",
		"J:/WikiMappingsOutput/Statistics/FR/",
		"J:/WikiMappingsOutput/Statistics/NL/",
		"J:/WikiMappingsOutput/Statistics/ES/",
		"J:/WikiMappingsOutput/Statistics/RU/",
		"J:/WikiMappingsOutput/Statistics/IT/"
		};
	
	/**
	 * Locations for logging the system events for each dataset
	 */
	public static final String[] Logs = new String[] {
		"J:/WikiMappingsOutput/Logs/EN_log.txt",
		"J:/WikiMappingsOutput/Logs/DE_log.txt",
		"J:/WikiMappingsOutput/Logs/FR_log.txt",
		"J:/WikiMappingsOutput/Logs/NL_log.txt",
		"J:/WikiMappingsOutput/Logs/ES_log.txt",
		"J:/WikiMappingsOutput/Logs/RU_log.txt",
		"J:/WikiMappingsOutput/Logs/IT_log.txt"
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
