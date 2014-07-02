package mp.dataclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Presents a view of {@link Infobox} infoboxes grouped by their class
 * @author Evgeny Mitichkin
 *
 */
public class InfoboxStatistics {

	private HashMap<String, List<Infobox>> statistics = new HashMap<String, List<Infobox>>();
	private HashMap<String, WikiPage> pageSet; 

	public InfoboxStatistics(HashMap<String, WikiPage> map) {
		this.pageSet = map;
		generateStatistics();
	}

	public HashMap<String, List<Infobox>> getStatistics() {		
		return statistics;
	}
	
	private void generateStatistics() {
		for (WikiPage pg : pageSet.values()) {
			Infobox box = pg.getInfobox();
			if (box!=null) {
				String infoboxClass = box.getInfoboxClass();
				List<Infobox> boxStats = statistics.get(infoboxClass);
				if (boxStats != null) { 
					boxStats.add(box);
				} else {
					boxStats = new ArrayList<Infobox>();
					boxStats.add(box);
					statistics.put(infoboxClass, boxStats);
				}
			}			
		}
	}
	
	public int getNumberOfClasses() {
		return statistics.size();
	}
}
