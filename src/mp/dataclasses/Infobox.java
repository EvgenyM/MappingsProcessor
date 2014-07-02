package mp.dataclasses;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class wraps Wikipedia Infobox into an object format
 * @author Evgeny Mitichkin
 *
 */
public class Infobox {

	private String ID;
	private String infoboxClass;
	private HashMap<String, InfoboxAttribute> attributes;
	private boolean isInitialized = false;
	
	/**
	 * This class wraps Wikipedia Infobox into an object format
	 * @param infoboxClass Infobox class name
	 */
	public Infobox(String name) {
		this.infoboxClass = name;
		this.ID = UUID.randomUUID().toString();
		this.attributes = new HashMap<String, InfoboxAttribute>();
		this.isInitialized = true;
	}
	
	
	/**
	 * This class wraps Wikipedia Infobox into an object format
	 * @param infoboxClass Infobox class name
	 * @param attributes Attribute (parameters) set
	 */
	public Infobox(String name, HashMap<String, InfoboxAttribute> attributes) {
		this.infoboxClass = name;
		this.ID = UUID.randomUUID().toString();
		this.attributes = attributes;
		this.isInitialized = true;
	}
	
	public String getInfoboxClass() {
		return infoboxClass;
	}
	public void setInfoboxClass(String name) {
		this.infoboxClass = name;
	}
	
	public HashMap<String, InfoboxAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, InfoboxAttribute> attributes) {
		this.attributes = attributes;
	}

	public boolean isInitialized() {
		return isInitialized;
	}
	
	public int size() {
		return attributes.size();
	}


	public String getID() {
		return ID;
	}
}
