package mp.exec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mp.dataclasses.LinkedHashSetMultimap;
import mp.dataclasses.Multimap;
import mp.io.utils.PropertyUtils;
import mp.preprocessing.WikiDataProcessor;

public class Executor {	
	
	private static final String SOURCE_PROPERTY_PATH = "/sources.properties";

	private static final String SOURCE_PROPERTY = "source";
	
	private static Multimap<String, String> paths = new LinkedHashSetMultimap<String, String>();
	
	static {
		loadResources();
	}
	
	private static void loadResources() {
		PropertyUtils.loadProperties(SOURCE_PROPERTY_PATH, SOURCE_PROPERTY, paths);
	}
	
	public static void main(String[] args) {
		WikiDataProcessor proc = new WikiDataProcessor();		
		for (Map.Entry<String, Collection<String>> lang : paths.asMap().entrySet()) {
			List<String> langPaths = new ArrayList<String>(lang.getValue());
			proc.extractAndDumpPages(langPaths.get(0),langPaths.get(1),langPaths.get(2), StringUtils.lowerCase(lang.getKey()));
		}
	}
}
