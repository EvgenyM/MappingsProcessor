package mp.exec;

import mp.global.GlobalVariables;
import mp.preprocessing.WikiDataProcessor;

public class Executor {	
	
	public static void main(String[] args) {
		WikiDataProcessor proc = new WikiDataProcessor();
		/*proc.makeETL(GlobalVariables.pageSetsToAnalyze,
					GlobalVariables.dumpsToJson,
					GlobalVariables.StatsJson,
					GlobalVariables.Logs);
		System.gc();
		proc.compress4Graph(GlobalVariables.dumpsToJson, GlobalVariables.dumpsToJson4Graph, ILLTypes.NoFilter);
		System.gc();
		proc.getStatsForGraph(GlobalVariables.dumpsToJson4Graph, GlobalVariables.StatsJson4g);
		System.gc();
		proc.getStatsForRawDumpedData(GlobalVariables.dumpsToJson, GlobalVariables.StatsJsonRaw);
		System.gc();
		proc.getAdditionalILLs(GlobalVariables.wikiLangLinks, GlobalVariables.wikiLangLinksTransformed);
		System.gc();
		proc.filterAdditionalILLs(GlobalVariables.wikiLangLinksTransformed, GlobalVariables.wikiLangLinksFiltered);*/
		proc.mergeAndDump4GraphDatasets(GlobalVariables.dumpsToJson4Graph, GlobalVariables.wikiLangLinksFiltered, GlobalVariables.dumpsToJson4GraphMergedWithILLs);
	}
}
