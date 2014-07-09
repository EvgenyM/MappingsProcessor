package mp.dataclasses;

import java.util.HashMap;
import java.util.Map;

public class WikiDataStatistics<V> {
	private int numberOfOnfoboxGroups;
	private HashMap<String, V> pageSet;//Initial page set
	private HashMap<String, Integer> infoboxStatistics = new HashMap<String, Integer>();//Number of pages per infobox class
	private HashMap<String, Integer> ILLStatistics = new HashMap<String, Integer>();//Number of ILLs per infobox class

	public WikiDataStatistics(HashMap<String, V> pageSet) {
		this.setPageSet(pageSet);
		calculateStatistics();
	}
	
	private void calculateStatistics() {
		numberOfOnfoboxGroups = 0;
		infoboxStatistics.clear();
		ILLStatistics.clear();
		
		for (Map.Entry<String, V> pageObj : pageSet.entrySet()) {
			if (pageObj.getValue() instanceof WikiPage) {
				WikiPage pg = (WikiPage)pageObj.getValue();
				Infobox box = pg.getInfobox();
				if (box!=null) {
					String infoboxClass = box.getInfoboxClass();
					Integer boxStats = infoboxStatistics.get(infoboxClass);
					if (boxStats != null) {
						infoboxStatistics.put(infoboxClass, boxStats+1);
					} else {
						infoboxStatistics.put(infoboxClass, 1);
					}
				}
				box = null;
				ILLStatistics.put(pg.getPageTitle(), pg.getILLs().size());
				pg = null;
			}
			if (pageObj.getValue() instanceof WikiPage4Graph) {
				WikiPage4Graph pg = (WikiPage4Graph)pageObj.getValue();
				Integer boxStats = infoboxStatistics.get(pg.getInfoboxClass());
				if (boxStats != null) {
					infoboxStatistics.put(pg.getInfoboxClass(), boxStats+1);
				} else {
					infoboxStatistics.put(pg.getInfoboxClass(), 1);
				}
				ILLStatistics.put(pg.getPageTitle(), pg.getILLs().size());		
				pg = null;
			}
		}
	}
	
	public int getNumberOfOnfoboxGroups() {
		return numberOfOnfoboxGroups;
	}

	/**
	 * @return the pageSet
	 */
	public HashMap<String, V> getPageSet() {
		return pageSet;
	}

	/**
	 * @param pageSet the pageSet to set
	 */
	public void setPageSet(HashMap<String, V> pageSet) {
		this.pageSet = pageSet;
		calculateStatistics();
	}

	/**
	 * @return the infoboxStatistics
	 */
	public HashMap<String, Integer> getInfoboxStatistics() {
		return infoboxStatistics;
	}

	public HashMap<String, Integer> getILLStatistics() {
		return ILLStatistics;
	}
}
