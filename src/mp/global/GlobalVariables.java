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
	 * Locations processed dumps of WikiData
	 */
	public static final String[] dumpsToJson = new String[] {
		"E:/WikiMappingsOutput/EN_PagesAsJson.txt",
		"E:/WikiMappingsOutput/DE_PagesAsJson.txt",
		"E:/WikiMappingsOutput/FR_PagesAsJson.txt",
		"E:/WikiMappingsOutput/NL_PagesAsJson.txt",
		"E:/WikiMappingsOutput/ES_PagesAsJson.txt",
		"E:/WikiMappingsOutput/RU_PagesAsJson.txt",
		"E:/WikiMappingsOutput/IT_PagesAsJson.txt"
		};
	
	/**
	 * Locations of statistics for the WikiData
	 */
	public static final String[] StatsJson = new String[] {
		"E:/WikiMappingsOutput/EN_Stats.csv",
		"E:/WikiMappingsOutput/DE_Stats.csv",
		"E:/WikiMappingsOutput/FR_Stats.csv",
		"E:/WikiMappingsOutput/NL_Stats.csv",
		"E:/WikiMappingsOutput/ES_Stats.csv",
		"E:/WikiMappingsOutput/RU_Stats.csv",
		"E:/WikiMappingsOutput/IT_Stats.csv"
		};
	
	/**
	 * Locations for logging the system events for each dataset
	 */
	public static final String[] Logs = new String[] {
		"E:/WikiMappingsOutput/EN_log.txt",
		"E:/WikiMappingsOutput/DE_log.txt",
		"E:/WikiMappingsOutput/FR_log.txt",
		"E:/WikiMappingsOutput/NL_log.txt",
		"E:/WikiMappingsOutput/ES_log.txt",
		"E:/WikiMappingsOutput/RU_log.txt",
		"E:/WikiMappingsOutput/IT_log.txt"
		};
	
}
