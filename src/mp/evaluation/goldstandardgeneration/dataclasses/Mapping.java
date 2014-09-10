package mp.evaluation.goldstandardgeneration.dataclasses;

public abstract class Mapping {
	protected MappingType mappingType;
	
	public Mapping() {
		
	}

	public Mapping(MappingType type) {
		this.mappingType = type;
	}
	
	public MappingType getMappingType() {
		return mappingType;
	}

	public void setMappingType(MappingType mappingType) {
		this.mappingType = mappingType;
	}
	
}
