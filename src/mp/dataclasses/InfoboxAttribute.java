package mp.dataclasses;

/**
 * Wraps an {@link Infobox} attributes and its type in context of the given dataset or task
 * @author Evgeny Mitichkin
 *
 */
public class InfoboxAttribute {

	private String name;
	private Object value;
	private AttributeClass type;
	
	/**
	 * Wraps {@link Infobox} attribute, assigning it a special {@link AttributeClass} type
	 * @param name Attribute name
	 * @param value Attribute value
	 * @param type {@link AttributeClass} type (In the context of data)
	 */
	public InfoboxAttribute(String name, Object value, AttributeClass type) {
		this.setName(name);
		this.setValue(value);
		this.setType(type);
	}
	
	/**
	 * Wraps {@link Infobox} attribute, assigning it a special {@link AttributeClass} type as {@code AttributeClass.Undefined}. 
	 * @param name Attribute name
	 * @param value Attribute value
	 */
	public InfoboxAttribute(String name, Object value) {
		this.setName(name);
		this.setValue(value);
		this.setType(AttributeClass.Undefined);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public AttributeClass getType() {
		return type;
	}

	public void setType(AttributeClass type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
