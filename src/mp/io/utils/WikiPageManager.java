package mp.io.utils;

import java.util.HashMap;
import java.util.Map;

import mp.dataclasses.WikiPage;
import mp.dataclasses.WikiPage4Graph;

/**
 * Implements basic supporting functions for WikiPages, e.g. print out their contents, or 
 * create a reduced WikiPage with statistics..
 * @author eomit_000
 *
 */
public class WikiPageManager {

	public WikiPageManager() {
		
	}
	
	/**
	 * Prints the contents of the {@link WikiPage} object
	 * @param page
	 */
	public void printWikiPage(WikiPage page) {
		System.out.println(page.toString());
		System.out.println();
	}
	
	/**
	 * Creates a reduced representation of WikiPage for graph builder. This page does not contain attributes, but only statistics regarding them.
	 * @return
	 */
	public WikiPage4Graph getWikiPage4Graph(WikiPage page) {
		WikiPage4Graph page4g = new WikiPage4Graph();
		page4g.setPageTitle(page.getPageTitle());
		page4g.setInfoboxClass(page.getInfobox().getInfoboxClass());
		page4g.setNumberOfAttributes(page.getInfobox().getAttributes().size());
		page4g.setILLs(page.getILLs());
		
		return page4g;
	}
	
	/**
	 * Converts set of {@link WikiPage} objects into set of {@link WikiPage4Graph} objects.
	 * @param wikiPages
	 * @return
	 */
	public HashMap<String, WikiPage4Graph> toWikiPage4Graph(HashMap<String, WikiPage> wikiPages) {
		HashMap<String, WikiPage4Graph> transformedData = new HashMap<String, WikiPage4Graph>((int)(wikiPages.size()/0.75+1));
		for (Map.Entry<String, WikiPage> page : wikiPages.entrySet()) {
			WikiPage4Graph page4g = getWikiPage4Graph(page.getValue());
			transformedData.put(page.getKey(), page4g);
			page4g = null;
		}		
		return transformedData;
	}
}
