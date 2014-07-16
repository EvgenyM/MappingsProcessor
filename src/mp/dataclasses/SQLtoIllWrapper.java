package mp.dataclasses;

import java.util.HashMap;

/**
 * Wrapper class for handling SQL to ILL instance
 * @author Evgeny Mitichkin
 *
 */
public class SQLtoIllWrapper {
	
	private long id;
	private HashMap<String, String> langTitleCorrespondence = new HashMap<String, String>(3);//Language is the key, title is the value
	
	public SQLtoIllWrapper() {
		
	}
	
	public SQLtoIllWrapper(long id, HashMap<String, String> langTitleCorrespondence) {
		this.id = id;
		this.setLangTitleCorrespondence(langTitleCorrespondence);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public HashMap<String, String> getLangTitleCorrespondence() {
		return langTitleCorrespondence;
	}

	public void setLangTitleCorrespondence(HashMap<String, String> langTitleCorrespondence) {
		this.langTitleCorrespondence = langTitleCorrespondence;
	}
}
