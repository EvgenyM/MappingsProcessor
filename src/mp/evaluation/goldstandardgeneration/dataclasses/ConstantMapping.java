package mp.evaluation.goldstandardgeneration.dataclasses;

public class ConstantMapping extends Mapping{

	private String ontologyProperty;
	private String value;
	
	public ConstantMapping() {
		
	}
	
	public ConstantMapping(String ontologyProperty, String value) {
		this.setOntologyProperty(ontologyProperty);
		this.setValue(value);
		this.setMappingType(MappingType.CONSTANT);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOntologyProperty() {
		return ontologyProperty;
	}

	public void setOntologyProperty(String ontologyProperty) {
		this.ontologyProperty = ontologyProperty;
	}
	
	
}
