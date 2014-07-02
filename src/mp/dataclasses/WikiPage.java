package mp.dataclasses;

import java.util.HashMap;

/**
 * Wrapper class for a page object.
 * @author Evgeny Mitichkin
 *
 */
public class WikiPage {

	private String pageTitle;
	private Infobox infobox;
	private HashMap<String, WikiLink> ILLs;
	
	public WikiPage(Infobox infobox, HashMap<String, WikiLink> ILLs) {
		this.infobox = infobox;
		this.ILLs = ILLs;
	}
	
	public WikiPage(Infobox infobox) {
		this.infobox = infobox;
		this.ILLs = new HashMap<String, WikiLink>();
	}
	
	Infobox getInfobox() {
		return infobox;
	}
	
	void setInfobox(Infobox infobox) {
		this.infobox = infobox;
	}
	
	HashMap<String, WikiLink> getILLs() {
		return ILLs;
	}
	
	void setILLs(HashMap<String, WikiLink> iLLs) {
		ILLs = iLLs;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
}
