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
	 * Locations of WikiData files to be processed
	 */
	public static final String[] pageSetsToAnalyze = new String[] {
		"E:/WikiDumps/unzipped/en/enwiki-20140614-pages-articles/enwiki_articles.xml",
		"E:/WikiDumps/unzipped/de/dewiki-20140615-pages-articles/dewiki_articles.xml",
		"E:/WikiDumps/unzipped/fr/frwiki-20140626-pages-articles/frwiki_articles.xml",
		"E:/WikiDumps/unzipped/nl/nlwiki-20140630-pages-articles/nlwiki_articles.xml",
		"E:/WikiDumps/unzipped/es/eswiki-20140613-pages-articles/eswiki_articles.xml",
		"E:/WikiDumps/unzipped/ru/ruwiki-20140617-pages-articles/ruwiki_articles.xml",
		"E:/WikiDumps/unzipped/it/itwiki-20140612-pages-articles/itwiki_articles.xml"
		};
	
	/**
	 * Locations of processed dumps of WikiData
	 */
	public static final String[] dumpsToJson = new String[] {
		"E:/WikiMappingsOutput/Raw extracted data/Wiki EN/EN_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki DE/DE_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki FR/FR_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki NL/NL_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki ES/ES_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki RU/RU_PagesAsJson.txt",
		"E:/WikiMappingsOutput/Raw extracted data/Wiki IT/IT_PagesAsJson.txt"
		};
	
	/**
	 * Locations Graph-ready data
	 */
	public static final String[] dumpsToJson4Graph = new String[] {
		"E:/WikiMappingsOutput/Graph-ready data/EN_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/DE_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/FR_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/NL_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/ES_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/RU_PagesAsJson4Graph.json4g",
		"E:/WikiMappingsOutput/Graph-ready data/IT_PagesAsJson4Graph.json4g"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson = new String[] {
		"E:/WikiMappingsOutput/Statistics/EN_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/DE_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/FR_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/NL_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/ES_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/RU_Stats.csv",
		"E:/WikiMappingsOutput/Statistics/IT_Stats.csv"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson4g = new String[] {
		"E:/WikiMappingsOutput/Statistics/EN/",
		"E:/WikiMappingsOutput/Statistics/DE/",
		"E:/WikiMappingsOutput/Statistics/FR/",
		"E:/WikiMappingsOutput/Statistics/NL/",
		"E:/WikiMappingsOutput/Statistics/ES/",
		"E:/WikiMappingsOutput/Statistics/RU/",
		"E:/WikiMappingsOutput/Statistics/IT/"
		};
	
	/**
	 * Locations for logging the system events for each dataset
	 */
	public static final String[] Logs = new String[] {
		"E:/WikiMappingsOutput/Logs/EN_log.txt",
		"E:/WikiMappingsOutput/Logs/DE_log.txt",
		"E:/WikiMappingsOutput/Logs/FR_log.txt",
		"E:/WikiMappingsOutput/Logs/NL_log.txt",
		"E:/WikiMappingsOutput/Logs/ES_log.txt",
		"E:/WikiMappingsOutput/Logs/RU_log.txt",
		"E:/WikiMappingsOutput/Logs/IT_log.txt"
		};
	
}
