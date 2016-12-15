package mp.io.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.WikiPage;

/**
 * Implements a set of methods for generating wrapper objects out of parsed data
 * @author Evgeny Mitichkin
 *
 */
public class Converter {

	private static final String pageBeginTag = "<doc";
	private static final String pageEndTag = "</doc>";
	private static final String titleBeginTag = "<title";
	private static final String titleEndTag = "</title>";
	private static final String idBeginTag = "<id";
	private static final String idEndTag = "</id>";
	private static final String textStartTag =">";
	public static final String dumpPath = "D:/1M Mannheim/Master Thesis/WikiData/enwiki/dump.txt";
	
	/**
	 * Extracts pages from XML document
	 * @param wikiDataAsString
	 * @return
	 */
	public static HashMap<String, WikiPage> extractPages(String wikiDataAsString, boolean ignoreUnnamedAttributes, boolean isNamesToLowerCase) {
		HashMap<String, WikiPage> pagesRetrieved = new HashMap<String, WikiPage>();
		
		List<String> pageAsString = Parser.getContents(wikiDataAsString, pageBeginTag, pageEndTag);
		for (String str: pageAsString) {
			WikiPage page = getWikiPage(str, ignoreUnnamedAttributes, isNamesToLowerCase);
			if (isPageConsistent(page)) 
				pagesRetrieved.put(Long.toString(page.getPageId()), page);
		}
		return pagesRetrieved;
	}
	
	/**
	 * Checks whether page is consistent and contains at least one ILL, Infobox and at least one attribute.
	 * @param page
	 * @return
	 */
	private static boolean isPageConsistent(WikiPage page) {
		boolean isConsistent = false;
		
		if (page.getPageContent().length() > 15) {
			isConsistent = true;
		}
		
		//if (page.getILLs()!=null) {
			//if (page.getILLs().size()>0) {
				/*if (page.getInfobox()!=null) {
					if (page.getInfobox().getAttributes()!= null) {
						if (page.getInfobox().getAttributes().size()>0) {
							for (InfoboxAttribute att:page.getInfobox().getAttributes()) {
								if (!att.equals(null)) {
									if (!att.getName().equals("") && !att.getValue().equals("")) {
										isConsistent = true;
									}
								}
							}
						}
					}						
				}*/
			//}
		//}
		
		return isConsistent;
	}
	
	/**
	 * Returns an object representation of a Wiki page as {@link WikiPage}
	 * @param pageData Raw Wiki page data
	 * @param ignoreUnnamedAttributes Whether unnamed attributes should be ignored
	 * @param isNamesToLowerCase Whether attribute names should be translated to lower case
	 * @return
	 */
	private static WikiPage getWikiPage(String pageData, boolean ignoreUnnamedAttributes, boolean isNamesToLowerCase) {

		long pageId = (long)(new Random().nextDouble()*10000000000d);				
		try {
			pageId = Long.parseLong(Parser.getContents(pageData, "id=", "\"").get(0));
		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		String content = pageData.substring(pageData.indexOf(">") + 1);
		WikiPage page = new WikiPage();
		page.setPageId(pageId);
		page.setPageContent(content);
		pageData = null;
		return page;
	}
}
