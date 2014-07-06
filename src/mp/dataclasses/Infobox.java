package mp.dataclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps Wikipedia Infobox into an object format
 * @author Evgeny Mitichkin
 *
 */
public class Infobox {

	private String infoboxClass;
	private List<InfoboxAttribute> attributes;
	private boolean isInitialized = false;
	
	/**
	 * This class wraps Wikipedia Infobox into an object format
	 * @param infoboxClass Infobox class name
	 */
	public Infobox(String name) {
		this.infoboxClass = name;
		this.attributes = new ArrayList<InfoboxAttribute>();
		this.isInitialized = true;
	}
	
	/**
	 * This class wraps Wikipedia Infobox into an object format
	 * @param infoboxClass Infobox class name
	 * @param attributes Attribute (parameters) set
	 */
	public Infobox(String name, List<InfoboxAttribute> attributes) {
		this.infoboxClass = name;
		this.attributes = attributes;
		this.isInitialized = true;
	}
	
	public String getInfoboxClass() {
		return infoboxClass;
	}
	public void setInfoboxClass(String name) {
		this.infoboxClass = name;
	}
	
	public List<InfoboxAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<InfoboxAttribute> attributes) {
		this.attributes = attributes;
	}

	public boolean isInitialized() {
		return isInitialized;
	}
	
	public int size() {
		return attributes.size();
	}
}
