package mp.dataclasses;

/**
 * Wrapper class for handling SQL to ILL instance
 * @author Evgeny Mitichkin
 *
 */
public class SQLtoIllWrapper {
	
	private long id;
	private String lang;
	private String title;
	
	public SQLtoIllWrapper() {
		
	}
	
	public SQLtoIllWrapper(long id, String lang, String title) {
		this.id = id;
		this.lang = lang;
		this.title = title;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
