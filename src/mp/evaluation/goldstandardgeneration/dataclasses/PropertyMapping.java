package mp.evaluation.goldstandardgeneration.dataclasses;

public class PropertyMapping extends Mapping {
	
	private String templateProperty;
	private String ontologyProperty;
	
	public PropertyMapping() { }
	
	public PropertyMapping(String templateProperty, String ontologyProperty) {
		this.setTemplateProperty(templateProperty);
		this.setOntologyProperty(ontologyProperty);
		this.setMappingType(MappingType.PROPERTY);
	}

	public String getOntologyProperty() {
		return ontologyProperty;
	}

	public void setOntologyProperty(String ontologyProperty) {
		this.ontologyProperty = ontologyProperty;
	}

	public String getTemplateProperty() {
		return templateProperty;
	}

	public void setTemplateProperty(String templateProperty) {
		this.templateProperty = templateProperty;
	}
}
