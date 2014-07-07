package mp.io.utils;

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
	 * Creates a reduced representation of WikiPage 
	 * @return
	 */
	public WikiPage4Graph geWikiPageWithoutAttributes(WikiPage page) {
		WikiPage4Graph page4g = new WikiPage4Graph();
		page4g.setPageTitle(page.getPageTitle());
		page4g.setInfoboxClass(page.getInfobox().getInfoboxClass());
		page4g.setNumberOfAttributes(page.getInfobox().getAttributes().size());
		page4g.setILLs(page.getILLs());
		
		return page4g;
	}
	
}
