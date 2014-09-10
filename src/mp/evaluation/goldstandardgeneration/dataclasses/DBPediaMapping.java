package mp.evaluation.goldstandardgeneration.dataclasses;

import java.util.List;

/**
 * Wrapper class for DBPedia Mapping between DBPedia Ontology and a given Infobox class
 * @author Evgeny Mitichkin
 *
 */
public class DBPediaMapping {
	private String templateName;//name of the template, e.g. Country, City and etc.
	private String ontologyClass;//name of the ontology class used to map template to
	private List<Mapping> mappings;//Set of property of cinstant mappings
	private boolean isRedirect = false;//Used in case the page is redirected, i.e. the text block has something like "#REDIRECT [[Mapping:Infobox adult biography]]" 
	private String redirectPageTitle = "";
	
	public DBPediaMapping() {
		
	}
	
	public DBPediaMapping(String templateName, String ontologyClass, List<Mapping> mappings) {
		this.setTemplateName(templateName);
		this.setOntologyClass(ontologyClass);
		this.setMappings(mappings);
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}

	public String getOntologyClass() {
		return ontologyClass;
	}

	public void setOntologyClass(String ontologyClass) {
		this.ontologyClass = ontologyClass;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getRedirectPageTitle() {
		return redirectPageTitle;
	}

	public void setRedirectPageTitle(String redirectPageTitle) {
		this.redirectPageTitle = redirectPageTitle;
	}
}
