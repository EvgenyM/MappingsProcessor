package mp.exec;

import mp.dataclasses.ILLTypes;
import mp.global.GlobalVariables;


public class Executor {	
	
	public static void main(String[] args) {
		WikiDataProcessor proc = new WikiDataProcessor();
		proc.makeETL(GlobalVariables.pageSetsToAnalyze,
					GlobalVariables.dumpsToJson,
					GlobalVariables.StatsJson,
					GlobalVariables.Logs);
		System.gc();
		proc.compress4Graph(GlobalVariables.dumpsToJson, GlobalVariables.dumpsToJson4Graph, ILLTypes.Main);
		System.gc();
		proc.getStatsForGraph(GlobalVariables.dumpsToJson4Graph, GlobalVariables.StatsJson4g);
		System.gc();
		proc.getStatsForRawDumpedData(GlobalVariables.dumpsToJson, GlobalVariables.StatsJsonRaw);
		System.gc();
		//proc.getAdditionalILLs(GlobalVariables.wikiData);
	}
}
