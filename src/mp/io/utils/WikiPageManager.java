package mp.io.utils;

import java.util.HashMap;
import java.util.Map;

import mp.dataclasses.ILLTypes;
import mp.dataclasses.WikiLink;
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
		page4g.setPageId(page.getPageId());
		
		return page4g;
	}
	
	/**
	 * Converts set of {@link WikiPage} objects into set of {@link WikiPage4Graph} objects.
	 * @param wikiPages
	 * @param selectorType 
	 * @return
	 */
	public HashMap<String, WikiPage4Graph> toWikiPage4Graph(HashMap<String, WikiPage> wikiPages, ILLTypes selectorType) {
		HashMap<String, WikiPage4Graph> transformedData = new HashMap<String, WikiPage4Graph>((int)(wikiPages.size()/0.75+1));
		for (Map.Entry<String, WikiPage> page : wikiPages.entrySet()) {
			if (isPageValid(selectorType, page.getValue())) {
				WikiPage4Graph page4g = getWikiPage4Graph(page.getValue());
				transformedData.put(page.getKey(), page4g);
				page4g = null;
			}
		}		
		return transformedData;
	}

	/**
	 * Checks whether the page contains the ILL set specified by {@link ILLTypes}
	 * @param selectorType
	 * @param wikiPage
	 * @return
	 */
	private boolean isPageValid(ILLTypes selectorType, WikiPage wikiPage) {
		boolean res = false;
		if (selectorType.equals(ILLTypes.NoFilter)) {
			res = true;
		} else {
			if (selectorType.equals(ILLTypes.MainOrNone)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (!lnk.isFeatured() && !lnk.isGood()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.Main)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (lnk.isInitialized()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.Additional)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (lnk.isGood() || lnk.isFeatured()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.All)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (lnk.isFeatured() || lnk.isGood() || lnk.isInitialized()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.Featured)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (lnk.isFeatured()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.Good)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (lnk.isGood()) {
						res = true;
						break;
					}
				}
			}
			if (selectorType.equals(ILLTypes.None)) {
				for (WikiLink lnk : wikiPage.getILLs().values()) {
					if (!lnk.isInitialized() && !lnk.isFeatured() && !lnk.isGood()) {
						res = true;
						break;
					}
				}
			}
		}
		
		return res;
	}
}
