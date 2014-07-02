package mp.dataclasses;

/**
 * Wrapper for Interlanguage Link
 * @author Evgeny Mitichkin
 *
 */
public class WikiLink {

	private String langCode;
	private String sameAsPageTitle;
	private String subCategory;
	private String featuredLangCode;
	private String goodLangCode;
	private boolean isFeatured = false;
	private boolean isGood = false;
	private boolean isInitialized = false;
	private boolean hasSpecialMark = false;
	
	public WikiLink() {	}
	
	public WikiLink(String langCode, String sameAsOageTitle) {
		this.langCode = langCode;
		this.sameAsPageTitle = sameAsOageTitle;
		this.isInitialized = true;
	}
	
	public String getLangCode() {
		return langCode;
	}
	
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	
	public String getSameAsPageTitle() {
		return sameAsPageTitle;
	}
	
	public void setSameAsPageTitle(String sameAsPageTitle) {
		this.sameAsPageTitle = sameAsPageTitle;
	}

	public boolean isFeatured() {
		return isFeatured;
	}

	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getFeaturedLangCode() {
		return featuredLangCode;
	}

	public void setFeaturedLangCode(String featuredLangCode) {
		this.featuredLangCode = featuredLangCode;
	}

	public String getGoodLangCode() {
		return goodLangCode;
	}

	public void setGoodLangCode(String goodLangCode) {
		this.goodLangCode = goodLangCode;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public boolean isHasSpecialMark() {
		return hasSpecialMark;
	}

	public void setHasSpecialMark(boolean hasSpecialMark) {
		this.hasSpecialMark = hasSpecialMark;
	}
	
}
