package mp.dataclasses;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for a page object.
 * @author Evgeny Mitichkin
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiPage extends WikiPageBase{

	@JsonProperty("pageTitle")
	private String pageTitle;
	
	@JsonProperty("pageId")
	private long pageId;
	
	@JsonProperty("pageContent")
	private String pageContent;
	
	//private Infobox infobox;
	//private HashMap<String, WikiLink> ILLs;
	
	public WikiPage(Infobox infobox, HashMap<String, WikiLink> ILLs) {
		//this.infobox = infobox;
		//this.ILLs = ILLs;
	}
	
	public WikiPage(Infobox infobox) {
		//this.infobox = infobox;
		//this.ILLs = new HashMap<String, WikiLink>();
	}
	
	public WikiPage() {
		
	}
	
	public Infobox getInfobox() {
		return null;//infobox;
	}
	
	public void setInfobox(Infobox infobox) {
		//this.infobox = infobox;
	}
	
	public HashMap<String, WikiLink> getILLs() {
		return null;//ILLs;
	}
	
	public void setILLs(HashMap<String, WikiLink> iLLs) {
		//ILLs = iLLs;
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

	public long getPageId() {
		return pageId;
	}

	public void setPageId(long pageId) {
		this.pageId = pageId;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
}
