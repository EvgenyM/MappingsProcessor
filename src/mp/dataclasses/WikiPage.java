package mp.dataclasses;

import java.util.HashMap;
import java.util.List;

/**
 * Wrapper class for a page object.
 * @author Evgeny Mitichkin
 *
 */
public class WikiPage extends WikiPageBase{

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
	
	public Infobox getInfobox() {
		return infobox;
	}
	
	public void setInfobox(Infobox infobox) {
		this.infobox = infobox;
	}
	
	public HashMap<String, WikiLink> getILLs() {
		return ILLs;
	}
	
	public void setILLs(HashMap<String, WikiLink> iLLs) {
		ILLs = iLLs;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("*****BEGIN*****");
		res.append(this.getPageTitle());
		res.append("ILLS:" + this.getILLs().size());
		HashMap<String, WikiLink> ills = this.getILLs();
		for (WikiLink lnk : ills.values()) {
			res.append(lnk.getLangCode()+":"+lnk.getSameAsPageTitle()+" Cat:"+lnk.getSubCategory());
			res.append("GOOD:"+lnk.isGood()+":"+lnk.getGoodLangCode());
			res.append("FEATURED:" + lnk.isFeatured()+":"+lnk.getFeaturedLangCode());
		}
		Infobox box = this.getInfobox();
		res.append("Infobox class:" + box.getInfoboxClass());
		List<InfoboxAttribute> attrs = box.getAttributes();
		res.append("Attributes:" + attrs.size());
		for (InfoboxAttribute attr : attrs) {
			res.append("Type: "+attr.getType() + " Attribute: "+ attr.getName()+" Value: "+attr.getValue());
		}
		res.append("*****END*****");
		return res.toString();
	}
}
