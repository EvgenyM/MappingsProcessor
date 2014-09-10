package mp.evaluation.goldstandardgeneration;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.OAEPParameterSpec;
import javax.xml.bind.JAXBElement.GlobalScope;

import mp.evaluation.goldstandardgeneration.dataclasses.ConstantMapping;
import mp.evaluation.goldstandardgeneration.dataclasses.DBPediaMapping;
import mp.evaluation.goldstandardgeneration.dataclasses.Mapping;
import mp.evaluation.goldstandardgeneration.dataclasses.MappingsIOCreator;
import mp.evaluation.goldstandardgeneration.dataclasses.PropertyMapping;
import mp.global.GlobalVariables;
import mp.io.FileIO;
import mp.io.utils.Parser;

/**
 * Parses the DBPedia Mappings Wiki file and delivers a set of object-wrapped objects
 * @author Evgeny Mitichkin
 *
 */
public class MappingsParser {
	
	private static final String pageBeginTag = "<page";
	private static final String pageEndTag = "</page>";
	private static final String titleBeginTag = "<title";
	private static final String titleEndTag = "</title>";
	private static final String textBeginTag = "<text";
	private static final String textEndTag = "</text>";
	private static final String revisionBeginTag = "<revision";
	private static final String revisionEndTag = "</revision>";

	private String mappingsPath;
	private HashMap<String, DBPediaMapping> mappingSet = null;

	public MappingsParser() {
		this.mappingSet = new HashMap<String, DBPediaMapping>();
	}
	
	public MappingsParser(String path) {
		this.mappingsPath = path;
		this.mappingSet = new HashMap<String, DBPediaMapping>();
	}
	
	public HashMap<String, DBPediaMapping> getMappings() {
		if (mappingSet == null) {
			process();
		}
		return mappingSet;
	}
	
	/**
	 * Processes the mappings file
		For each node create a DBPediaMapping object by parsing the following values:
		- Extract <title>, then split it by ":" and take the [1]...[n] part
		- Extract <revision>, then extract <text> from it. This text is the mapping.
	 */
	public void process() {
		//Store the result as a HashMap with the template name as a key.
		//Read all mappings file.
		String rawMappingsData = readRawMappings();
		//Extract all <page> nodes
		List<String> pages = Parser.getContents(rawMappingsData, pageBeginTag, pageEndTag);
		
		for (String pg : pages) {
			DBPediaMapping mapping4Page = new DBPediaMapping();
			String titleContents = Parser.getContents(pg, titleBeginTag, titleEndTag).get(0);
			String[] titleParts = titleContents.split(":");
			String title = "";
			for (int j=1;j<titleParts.length;j++) {
				title+=titleParts[j];
			}
			List<String> revList = Parser.getContents(pg, revisionBeginTag, revisionEndTag);
			if (revList.size()>0) {
				String text = Parser.getContents(pg, textBeginTag, textEndTag).get(0);
				
				if (!title.equals("") && !text.equals("")) {
					mapping4Page.setTemplateName(title);
					try {
						String mappingTextContent = Parser.getContentsNoOffset(text, "{{", "}}", true).get(0);
						String[] ress = mappingTextContent.split("\\|");
						String ontologyClass = ress[1].split("=")[1].trim();
						mapping4Page.setOntologyClass(ontologyClass);
						if (ress.length>2) { // not only mapping, but also properties
							List<String> mappings = Parser.getContentsNoOffset(mappingTextContent, "{{", "}}", true);
							List<Mapping> mappingsContent = new ArrayList<Mapping>();
							Mapping mappingContent = null;
							for (String mapping : mappings) {
								try {
									mapping = mapping.replaceAll("\\{", "");
									mapping = mapping.replaceAll("\\}", "");
									String[] mapps = mapping.split("\\|");
									for (String mappsComp : mapps) {
										mappsComp = mappsComp.trim();
									}
									//TODO Conditional mapping and table mapping are not handeled
									if (mapps[0].equals("PropertyMapping")) {
										mappingContent = extractPropertyMapping(mapps);
									} 
									if (mapps[0].equals("ConstantMapping")) {
										mappingContent = extractConstantMapping(mapps);
									}
									mappingsContent.add(mappingContent);
								} catch (Exception ex) {
									System.out.println("ERROR parsing: "+mapping);
									ex.printStackTrace();
								}
							}
							mapping4Page.setMappings(mappingsContent);
						}
					} catch (Exception e) {
						mapping4Page.setMappings(null);
						mapping4Page.setOntologyClass(null);						
						String redirectTitle = getRedirectTitle(text);
						if (redirectTitle!=null) {
							mapping4Page.setRedirect(true);
							mapping4Page.setRedirectPageTitle(redirectTitle);
						} else {
							System.out.println("Exception processing:\n"+text);
							e.printStackTrace();
						}
					}
				}
			}	
			if (!mapping4Page.isRedirect()) {
				mappingSet.put(mapping4Page.getTemplateName(), mapping4Page);
			} else {
				mappingSet.put(mapping4Page.getRedirectPageTitle(), mapping4Page);
			}
		}
	}
	
	private String getRedirectTitle(String text) {
		String title = null;
		try {
			title = Parser.getContentsNoOffset(text, "[[", "]]", true).get(0).split(":")[1];
		} catch (Exception e) {
			System.out.println("Cannot process redirect for: " + text);
		}
		return null;
	}

	private PropertyMapping extractPropertyMapping(String[] parts) {
		PropertyMapping mppng = new PropertyMapping();
		String templateProp = parts[1].split("=")[1];
		String ontologyProp = parts[2].split("=")[1];
		mppng.setTemplateProperty(templateProp);
		mppng.setOntologyProperty(ontologyProp);
		return mppng;
	}
	
	private ConstantMapping extractConstantMapping(String[] parts) {
		ConstantMapping mppng = new ConstantMapping();
		String value = parts[2].split("=")[1];
		String ontologyProp = parts[1].split("=")[1];
		mppng.setOntologyProperty(ontologyProp);
		mppng.setValue(value);
		return mppng;
	}
	
	private String readRawMappings() {
		FileIO ioManager = new FileIO();
		MappingsIOCreator mappingsReader = MappingsIOCreator.getInstance();
		try {
			ioManager.readFileByChunks(mappingsPath, mappingsReader);
		} catch (IOException e) {
			if (GlobalVariables.IS_DEBUG) {
				System.out.println("Failed to read mappings from: " +mappingsPath);
				e.printStackTrace();
			}
		}
		return mappingsReader.getrawMappings();
	}
	
	public String getMappingsPath() {
		return mappingsPath;
	}

	public void setMappingsPath(String mappingsPath) {
		this.mappingsPath = mappingsPath;
	}	
}
