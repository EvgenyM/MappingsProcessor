package mp.io.utils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import mp.dataclasses.Multimap;

public class PropertyUtils {

	//private static final Logger log = LoggerFactory.getLogger(PropertyUtils.class);

	/**
	 * Reads property file and puts its content into a {@link Multimap}
	 */
	public static void loadProperties(String resourcePath, String propertyType,
			Multimap<String, String> populatableMap) {
		Properties properties = new Properties();
		try (InputStream propStream = PropertyUtils.class
				.getResourceAsStream(resourcePath)) {
			properties.load(propStream);
			loadTermsPerGroup(properties, propertyType, populatableMap);
		} catch (Exception e) {
			//log.error("Could not load institution property file: " + resourcePath, e);
		}
	}

	/**
	 * Populates a {@link Multimap} with properties provided
	 */
	private static void loadTermsPerGroup(Properties properties, String propertyType,
			Multimap<String, String> populatableMap) {
		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String property = enumeration.nextElement();
			String[] parts = property.split("\\.");
			if (propertyType.equals(parts[0])) {
				populatableMap.putAll(StringUtils.upperCase(parts[1]), Arrays.asList(StringUtils.split(properties.getProperty(property), ",")));
			}
		}
	}

}
