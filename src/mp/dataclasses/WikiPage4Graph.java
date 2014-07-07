package mp.dataclasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Compact representation of {@link WikiPage}. Includes only its title and basic statistics necessary for comparisons.
 * @author Evgeny Mitichkin
 *
 */
public class WikiPage4Graph {
	private String pageTitle;
	private HashMap<String, WikiLink> ILLs;//Should keep only the ILLs among the language space
	private String infoboxClass;
	private int numberOfAttributes;
	
	public WikiPage4Graph() {
		
	}
	
	public WikiPage4Graph(String title, HashMap<String, WikiLink> ILLs, String infoboxClass, int numberOfAttributes) {
		this.pageTitle = title;
		this.ILLs = ILLs;
		this.infoboxClass = infoboxClass;
		this.numberOfAttributes = numberOfAttributes;
	}
	
	/**
	 * Returns a set of interlingual links targeting another page directly, e.g. {@code "en:Berlin" or "de:Berlin" }
	 * @return
	 */
	public HashMap<String, WikiLink> getMainILLs() {		
		int arrSize = getNumberOfMainILLs();		
		if (arrSize>0) {
			HashMap<String, WikiLink> resLinks = new HashMap<String, WikiLink>((int) (arrSize*0.75+1));
			for (Map.Entry<String, WikiLink> ill : ILLs.entrySet()) {
				if (ill.getValue().isInitialized())
					resLinks.put(ill.getKey(), ill.getValue());
			}
			return resLinks;
		} else {
			return null;
		}	
	}
	
	/**
	 * Returns a set of interlingual links targeting another page directly, e.g. {@code "en:Berlin" or "de:Berlin" } for a given set of languages
	 * @param langCodes Array containing language codes
	 * @return
	 */
	public HashMap<String, WikiLink> getMainILLsByLanguage(String[] langCodes) {
		int arrSize = getNumberOfMainILLs();		
		if (arrSize>0) {
			HashMap<String, WikiLink> resLinks = new HashMap<String, WikiLink>((int) (arrSize*0.75+1));
			for (Map.Entry<String, WikiLink> ill : ILLs.entrySet()) {
				if (ill.getValue().isInitialized()) {
					for (int i=0;i<langCodes.length;i++) {
						if (ill.getValue().getLangCode().equals(langCodes[i])) {
							resLinks.put(ill.getKey(), ill.getValue());
							break;
						}
					}
				}
			}
			return resLinks;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a set of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "featured"} or {@code "good"}
	 * @return
	 */
	public HashMap<String, WikiLink> getAdditionalILLs() {
		int arrSize = getNumberOfAdditionalILLs();
		if (arrSize>0) {
			HashMap<String, WikiLink> resLinks = new HashMap<String, WikiLink>((int) (arrSize*0.75+1));
			for (Map.Entry<String, WikiLink> ill : ILLs.entrySet()) {
				if (ill.getValue().isGood() || ill.getValue().isFeatured()) {
					resLinks.put(ill.getKey(), ill.getValue());
				}
			}
			return resLinks;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a set of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "featured"} or {@code "good"} for a given set of languages
	 * @param langCodes Array containing language codes
	 * @return
	 */
	public HashMap<String, WikiLink> getAdditionalILLsByLanguage(String[] langCodes) {
		int arrSize = getNumberOfAdditionalILLs();
		if (arrSize>0) {
			HashMap<String, WikiLink> resLinks = new HashMap<String, WikiLink>((int) (arrSize*0.75+1));
			for (Map.Entry<String, WikiLink> ill : ILLs.entrySet()) {
				if (ill.getValue().isGood() || ill.getValue().isFeatured()) {
					for (int i=0;i<langCodes.length;i++) {
						if (ill.getValue().getGoodLangCode().equals(langCodes[i]) || ill.getValue().getFeaturedLangCode().equals(langCodes[i])) {
							resLinks.put(ill.getKey(), ill.getValue());
							break;
						}
					}
					resLinks.put(ill.getKey(), ill.getValue());
				}
			}
			return resLinks;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Returns the number of interlingual links targeting another page directly, e.g. {@code "en:Berlin" or "de:Berlin" }
	 * @return
	 */
	public int getNumberOfMainILLs() {
		int num = 0;
		for (WikiLink lnk:ILLs.values()) {
			if(lnk.isInitialized())
				num++;
		}
		
		return num;
	}
	

	/**
	 * Returns the number of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "featured"}
	 * @return
	 */
	public int getNumberOfFeaturedIlls() {
		int num = 0;
		for (WikiLink lnk:ILLs.values()) {
			if (lnk.isFeatured())
				num++;
		}
		
		return num;
	}
	
	/**
	 * Returns the number of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "good"}
	 * @return
	 */
	public int getNumberOfGoodILLs() {
		int num = 0;
		for (WikiLink lnk:ILLs.values()) {
			if (lnk.isGood())
				num++;
		}	
		
		return num;
	}
	
	/**
	 * Returns the number of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "featured"} or {@code "good"}
	 * @return
	 */
	public int getNumberOfAdditionalILLs() {
		int num = 0;
		for (WikiLink lnk:ILLs.values()) {
			if (lnk.isFeatured() || lnk.isGood())
				num++;
		}
		
		return num;
	}
	
	/**
	 * Returns the number of interlingual links that are targeting {@code "languages"} in which the article is considered {@code "featured"} and {@code "good"}
	 * @return
	 */
	public int getNumberOfGoodAndFeatured() {
		int num = 0;
		for (WikiLink lnk:ILLs.values()) {
			if (lnk.isFeatured() && lnk.isGood())
				num++;
		}
		
		return num;
	}
	
	public int getNumberOfILLs() {
		return ILLs.size();
	}
	
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public HashMap<String, WikiLink> getILLs() {
		return ILLs;
	}
	public void setILLs(HashMap<String, WikiLink> iLLs) {
		ILLs = iLLs;
	}
	
	public String getInfoboxClass() {
		return infoboxClass;
	}
	public void setInfoboxClass(String infoboxClass) {
		this.infoboxClass = infoboxClass;
	}
	
	public int getNumberOfAttributes() {
		return numberOfAttributes;
	}
	public void setNumberOfAttributes(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
	}
}
