package mp.evaluation.goldstandardgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mp.evaluation.goldstandardgeneration.dataclasses.DBPediaMapping;
import mp.evaluation.goldstandardgeneration.dataclasses.GSElement;
import mp.evaluation.goldstandardgeneration.dataclasses.MappingsIOCreator;

/**
 * Creates baskets of correspondences
 * @author Evgeny Mitichkin
 *
 */
public class MappingsWikiProcessor {
	
	public MappingsWikiProcessor() { }
	
	public void buildGoldStandard() {
		/*HashMap<String, DBPediaMapping> mappingsEN = getMappings4LanguageEdition("D:/Workspace/DBPedia_mappings/Mapping_en.xml");
		HashMap<String, DBPediaMapping> mappingsDE = getMappings4LanguageEdition("D:/Workspace/DBPedia_mappings/Mapping_de.xml");
		HashMap<String, DBPediaMapping> mappingsFR = getMappings4LanguageEdition("D:/Workspace/DBPedia_mappings/Mapping_fr.xml");
		HashMap<String, DBPediaMapping> mappingsNL = getMappings4LanguageEdition("D:/Workspace/DBPedia_mappings/Mapping_nl.xml");
		
		for (Map.Entry<String, DBPediaMapping> mappingItem : mappingsEN.entrySet()) {
			
		}*/
		
		//Gold standard will be represented by a HashMap of objects pf a following type:
		//List of LANG1 names --> Ontology class <-- List of Lang2 names
		//Ontology class is used as a HashMap key
		HashMap<String, GSElement> goldStandardSet = getGoldStandard("D:/Workspace/DBPedia_mappings/Mapping_en.xml", "D:/Workspace/DBPedia_mappings/Mapping_de.xml");
		
		
	}
	
	/**
	 * Creates a gold standard mapping for a given pair of languages based on DBPedia Ontology
	 * @param pathLang1
	 * @param pathLang2
	 * @return
	 */
	public HashMap<String, GSElement> getGoldStandard(String pathLang1, String pathLang2) {
		
		HashMap<String, GSElement> goldStandardSet = new HashMap<String, GSElement>();
		
		HashMap<String, DBPediaMapping> mappingsLang1 = getMappings4LanguageEdition(pathLang1);//key is the template name
		HashMap<String, DBPediaMapping> mappingsLang2 = getMappings4LanguageEdition(pathLang2);
		
		for (Map.Entry<String, DBPediaMapping> mappingItem : mappingsLang1.entrySet()) {
			GSElement elt = goldStandardSet.get(mappingItem.getValue().getOntologyClass());
			if (elt!=null) {
				elt.getLeftLangSet().add(mappingItem.getValue());
			} else {
				elt = new GSElement();
				List<DBPediaMapping> leftMappings = new ArrayList<DBPediaMapping>();
				leftMappings.add(mappingItem.getValue());
				elt.setLeftLangSet(leftMappings);
				elt.setOntologyClass(mappingItem.getValue().getOntologyClass());
				goldStandardSet.put(mappingItem.getValue().getOntologyClass(), elt);
			}
		}
		
		for (Map.Entry<String, DBPediaMapping> mappingItem : mappingsLang2.entrySet()) {
			GSElement elt = goldStandardSet.get(mappingItem.getValue().getOntologyClass());
			if (elt!=null) {
				if (elt.getRightLangSet() == null){
					List<DBPediaMapping> rightMappings = new ArrayList<DBPediaMapping>();
					rightMappings.add(mappingItem.getValue());
					elt.setRightLangSet(rightMappings);
				} else {
					elt.getRightLangSet().add(mappingItem.getValue());
				}
			} else {
				elt = new GSElement();
				List<DBPediaMapping> rightMappings = new ArrayList<DBPediaMapping>();
				rightMappings.add(mappingItem.getValue());
				elt.setRightLangSet(rightMappings);
				elt.setOntologyClass(mappingItem.getValue().getOntologyClass());
				goldStandardSet.put(mappingItem.getValue().getOntologyClass(), elt);
			}
		}
		
		List<String> oneDirectionMappings = new ArrayList<String>();
		
		for (Map.Entry<String, GSElement> mappingElement: goldStandardSet.entrySet()) {
			if (mappingElement.getValue().getLeftLangSet() == null || mappingElement.getValue().getRightLangSet() == null)
				oneDirectionMappings.add(mappingElement.getValue().getOntologyClass());
		}
		
		HashMap<String, GSElement> goldStandardSetResult = new HashMap<String, GSElement>();
		
		for (Map.Entry<String, GSElement> mappingElement: goldStandardSet.entrySet()) {
			boolean ress = true;
			for (String excClass: oneDirectionMappings) {
				if (mappingElement.getValue().getOntologyClass()!=null) {
					if (mappingElement.getValue().getOntologyClass().equals(excClass)) {
						ress = false;
						break;
					}	
				}
			}
			if (ress) {
				goldStandardSetResult.put(mappingElement.getValue().getOntologyClass(), mappingElement.getValue());
			}
		}
		
		return goldStandardSetResult;
	}
	
	private HashMap<String, DBPediaMapping> getMappings4LanguageEdition(String editionPath){
		MappingsParser prsr = new MappingsParser(editionPath);
		prsr.process();
		HashMap<String, DBPediaMapping> mappings = prsr.getMappings();
		return mappings;
	}
}
