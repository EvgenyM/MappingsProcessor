package mp.preprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mp.dataclasses.Infobox;
import mp.dataclasses.RawItemStatistics;
import mp.dataclasses.WikiPage;
import mp.io.JsonIONotifier;

/**
 * Handles chunk-by-chunk retrieval of statistics, its aggregation and delivery
 * @author Evgeny Mitichkin
 *
 */
public class StatisticsFactory implements JsonIONotifier{
	
	private static StatisticsFactory instance = null;
	private static List<RawItemStatistics> mStatSet;//statistical set for one page
	private static long totalNumberOfItems = 0;//total number of pages
	private static long numberOfCompleteIBXs = 0;//number of pages with complete infoboxes
	private static long totalNumberOfILLs = 0;//number of interlanguage links

	private StatisticsFactory() {
		mStatSet = new ArrayList<RawItemStatistics>();
		totalNumberOfILLs = 0;
		numberOfCompleteIBXs = 0;
		totalNumberOfItems = 0;
	}
	
	public static StatisticsFactory getInstance() {
		if (instance == null) {
            instance = new StatisticsFactory();
        }
        return instance;
	}
	
	@Override
	public <K, V> boolean onChunkProcessed(HashMap<K, V> pageSet) {
		boolean success = true;
		try {
			for (Entry<K, V> pg : pageSet.entrySet()) {				
				WikiPage page = (WikiPage)pg.getValue();
				Infobox box = page.getInfobox();
				if (box!=null) {					
					if (box.getAttributes()!=null) {
						if (box.getAttributes().size()>0) {
							RawItemStatistics stats = new RawItemStatistics();
							numberOfCompleteIBXs++;
							if (page.getILLs()!=null) {
								int size = page.getILLs().size();
								totalNumberOfILLs +=size;
								stats.setNumberOfILLs(size);
							}							
							stats.setInfoboxClass(box.getInfoboxClass());
							stats.setNumberOfAttributes(box.getAttributes().size());
							mStatSet.add(stats);
						}
					}
				}
			}
			totalNumberOfItems+= pageSet.size();			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	public List<RawItemStatistics> getmStatSet() {
		return mStatSet;
	}

	public long getTotalNumberOfItems() {
		return totalNumberOfItems;
	}

	public long getNumberOfCompleteIBXs() {
		return numberOfCompleteIBXs;
	}

	public long getTotalNumberOfILLs() {
		return totalNumberOfILLs;
	}

	public void setInstanceNull() {
		StatisticsFactory.instance = null;
	}
}
