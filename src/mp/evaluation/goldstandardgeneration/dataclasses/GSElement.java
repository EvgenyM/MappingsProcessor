package mp.evaluation.goldstandardgeneration.dataclasses;

import java.util.List;

public class GSElement {

	private List<DBPediaMapping> leftLangSet;
	private String ontologyClass;
	private List<DBPediaMapping> rightLangSet;
	
	public GSElement(List<DBPediaMapping> leftElements, String ontClass, List<DBPediaMapping> rightElements) {
		this.setLeftLangSet(leftElements);
		this.setOntologyClass(ontClass);
		this.setRightLangSet(rightElements);
	}
	
	public GSElement() {
		
	}

	public List<DBPediaMapping> getLeftLangSet() {
		return leftLangSet;
	}

	public void setLeftLangSet(List<DBPediaMapping> leftLangSet) {
		this.leftLangSet = leftLangSet;
	}

	public String getOntologyClass() {
		return ontologyClass;
	}

	public void setOntologyClass(String ontologyClass) {
		this.ontologyClass = ontologyClass;
	}

	public List<DBPediaMapping> getRightLangSet() {
		return rightLangSet;
	}

	public void setRightLangSet(List<DBPediaMapping> rightLangSet) {
		this.rightLangSet = rightLangSet;
	}
}
