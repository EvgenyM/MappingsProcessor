package mp.exec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mp.dataclasses.LinkedHashSetMultimap;
import mp.dataclasses.Multimap;
import mp.io.utils.PropertyUtils;
import mp.preprocessing.WikiDataProcessor;

public class Executor {	
	
	private static final String SOURCE_PROPERTY_PATH = "/sources.properties";

	private static final String SOURCE_PROPERTY = "source";
	
	private static Multimap<String, String> paths = new LinkedHashSetMultimap<String, String>();
	
	static {
		loadResources();
	}
	
	private static void loadResources() {
		PropertyUtils.loadProperties(SOURCE_PROPERTY_PATH, SOURCE_PROPERTY, paths);
	}
	
	public static void main(String[] args) {
		
		WikiDataProcessor proc = new WikiDataProcessor();
		
		for (Map.Entry<String, Collection<String>> lang : paths.asMap().entrySet()) {
			List<String> langPaths = new ArrayList<String>(lang.getValue());
			//System.out.println(lang.getKey());
			proc.extractAndDumpPages(langPaths.get(0),langPaths.get(1),langPaths.get(2));			
			/*ObjectConverter conv = new ObjectConverter();
			HashMap<String, WikiPage> wikiData = conv.getAsHashMap(langPaths.get(1), PageMapEntry.class);
			proc.getStatistics(wikiData, langPaths.get(2));*/
		}
		
		
		
		
		
		
		
		//System.gc();
		//proc.compress4Graph(GlobalVariables.dumpsToJson, GlobalVariables.dumpsToJson4Graph, ILLTypes.NoFilter);
		//System.gc();
		//proc.getStatsForGraph(GlobalVariables.dumpsToJson4Graph, GlobalVariables.StatsJson4g);
		//System.gc();
		//proc.getStatsForRawDumpedData(GlobalVariables.dumpsToJson, GlobalVariables.StatsJsonRaw);
		//System.gc();
		//proc.getAdditionalILLs(GlobalVariables.wikiLangLinks, GlobalVariables.wikiLangLinksTransformed);
		//System.gc();
		//proc.filterAdditionalILLs(GlobalVariables.wikiLangLinksTransformed, GlobalVariables.wikiLangLinksFiltered);
		//proc.mergeAndDump4GraphDatasets(GlobalVariables.dumpsToJson4Graph, GlobalVariables.wikiLangLinksFiltered, GlobalVariables.dumpsToJson4GraphMergedWithILLs);
		//System.gc();
		
		
		
		///MappingsWikiProcessor mwp = new MappingsWikiProcessor();
		//mwp.buildGoldStandard();
	}
}
