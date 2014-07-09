package mp.exec;

import mp.global.GlobalVariables;


public class Executor {	
	
	public static void main(String[] args) {
		WikiDataProcessor proc = new WikiDataProcessor();
		/*proc.makeETL(GlobalVariables.pageSetsToAnalyze,
					GlobalVariables.dumpsToJson,
					GlobalVariables.StatsJson,
					GlobalVariables.Logs);*/
		//proc.compress4Graph(GlobalVariables.dumpsToJson, GlobalVariables.dumpsToJson4Graph, ILLTypes.Main);
		proc.getStatsForGraph(GlobalVariables.dumpsToJson4Graph, GlobalVariables.StatsJson4g);
	}
}
