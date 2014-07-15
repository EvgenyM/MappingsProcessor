package mp.io.utils;

import java.util.HashMap;
import java.util.List;

import mp.dataclasses.Infobox;
import mp.dataclasses.InfoboxAttribute;
import mp.dataclasses.WikiLink;
import mp.dataclasses.WikiPage;
import mp.global.GlobalVariables;
import mp.io.FileIO;
import mp.io.dataclasses.InfoboxExtractionObject;

/**
 * Implements a set of methods for generating wrapper objects out of parsed data
 * @author Evgeny Mitichkin
 *
 */
public class Converter {

	private static final String pageBeginTag = "<page";
	private static final String pageEndTag = "</page>";
	private static final String titleBeginTag = "<title";
	private static final String titleEndTag = "</title>";
	private static final String idBeginTag = "<id>";
	private static final String idEndTag = "</id>";
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
				pagesRetrieved.put(page.getPageTitle(), page);
		}
		pageAsString = null;
		return pagesRetrieved;
	}
	
	/**
	 * Checks whether page is consistent and contains at least one ILL, Infobox and at least one attribute.
	 * @param page
	 * @return
	 */
	private static boolean isPageConsistent(WikiPage page) {
		boolean isConsistent = false;
		
		//if (page.getILLs()!=null) {
			//if (page.getILLs().size()>0) {
				if (page.getInfobox()!=null) {
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
				}
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
		
		//PAGE ID
		long pageId = Long.parseLong(Parser.getContents(pageData, idBeginTag, idEndTag).get(0));
		
		//PAGE TITLE
		String pageTitle = Parser.getContents(pageData, titleBeginTag, titleEndTag).get(0);
		
		//INFOBOX
		InfoboxExtractionObject extractedChunk = Parser.extractInfboxesFromUnstructuredText(pageData, ignoreUnnamedAttributes, isNamesToLowerCase);
		Infobox box = null;
		//take the 1st (the only in this case) infobox
		for (Infobox mbox : extractedChunk.getInfoBoxes()) {
			box = mbox;
			break;
		}
		
		//ILLs
		HashMap<String, WikiLink> ILLSet = Parser.getILLs(pageData, true);
		
		/*if (GlobalVariables.IS_DEBUG) {
			FileIO.writeToFile(dumpPath, "Page: *" + pageTitle+"*"+"\n", true);
			System.out.println("Page: *" + pageTitle+"*");
				
			for (WikiLink lnk : ILLSet.values()) {
				String strOut = lnk.getLangCode()+":"+lnk.getSameAsPageTitle()+ "#" + lnk.getSubCategory() + "|FEATURED: "+lnk.getFeaturedLangCode()+"|GOOD: "+lnk.getGoodLangCode();
				FileIO.writeToFile(dumpPath, strOut+"\n", true);
				System.out.println(strOut);
			}
			System.out.println();
			FileIO.writeToFile(dumpPath, "\n", true);
		}*/
		
		WikiPage page = new WikiPage(box, ILLSet);	
		page.setPageTitle(pageTitle);
		page.setPageId(pageId);

		extractedChunk = null;
		pageData = null;
		ILLSet = null;
		pageTitle = null;
		box = null;
		
		return page;
	}
}
