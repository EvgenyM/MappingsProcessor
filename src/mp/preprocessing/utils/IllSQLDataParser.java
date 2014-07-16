package mp.preprocessing.utils;

import java.util.HashMap;

import mp.dataclasses.SQLtoIllWrapper;
import mp.io.FileIONotifier;

/**
 * Parses the raw WikiData file (from the WikiData projects) and searches for ILLs in it.
 * @author Evgeny Mitichkin
 *
 */
public class IllSQLDataParser implements FileIONotifier {
	
	private static IllSQLDataParser instance = null;
	private static final String SQL_VALUES = "VALUES";
	private static HashMap<Long, SQLtoIllWrapper> langLinkSet;
	
	public static IllSQLDataParser getInstance() {
		if (instance == null) {
            instance = new IllSQLDataParser();
        }
        return instance;
	}
	
	private IllSQLDataParser() { 
		langLinkSet = new HashMap<Long, SQLtoIllWrapper>();
	}

	@Override
	public void onChunkRead(String[] str) {
		try {
			for (int i=0;i<str.length;i++) {
				parseLine(str[i]);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	/**
	 * Parses the "INSERT INTO" string and puts new values into the {@code langLinkSet}
	 * @param line
	 * @return
	 */
	private void parseLine(String line) {
		int idx = line.indexOf(SQL_VALUES);
		if (idx>0) {
			String strToSplit = line.substring(idx, line.length()-1);
			String[] els = strToSplit.split("\\),\\(");
			for (int i=0;i<els.length;i++) {
				if (i!=els.length-1) {
					if (i==0) {
						els[i] = els[i].substring(SQL_VALUES.length()).trim();
						els[i] = els[i].substring(1);
					}
					addToLangLinkSet(els[i]);						
				} else {
					String el = els[i].substring(0, els[i].length()-1);
					addToLangLinkSet(els[i]);					
				}
			}
		} 			
	}
	
	/**
	 * Prepares a wrapped representation of ILL extracted from SQL	
	 * @param element
	 * @return
	 */
	private SQLtoIllWrapper getWrappedObject(String[] element) {
		SQLtoIllWrapper wrapper = new SQLtoIllWrapper();
		wrapper.setId(Long.parseLong(element[0]));
		wrapper.getLangTitleCorrespondence().put(element[1], element[2]);
		return wrapper;
	}
	
	/**
	 * Extracts an ILL from an SQL string and checks whether it already exists in the resulting {@link HashMap}
	 * If it exists, the methods updates the fields of {@link SQLtoIllWrapper} objects,
	 * otherwise creates a new object and adds it to the resulting {@link HashMap}
	 */
	private void addToLangLinkSet(String element) {
		String[] oneEl = element.split(",");
		for (int j=0;j<oneEl.length;j++) {
			oneEl[j] = oneEl[j].replaceAll("'", "");
		}				
		try {
			long pageKey = Long.parseLong(oneEl[0]);
			SQLtoIllWrapper wrapper = langLinkSet.get(pageKey);
			if (wrapper!= null) {
				wrapper.getLangTitleCorrespondence().put(oneEl[1], oneEl[2]);
			} else {
				wrapper = getWrappedObject(oneEl);							
			}
			langLinkSet.put(wrapper.getId(), wrapper);
		} catch (Exception parsingException) {
			parsingException.printStackTrace();
		}	
	}

	public HashMap<Long, SQLtoIllWrapper> getLangLinkSet() {
		return langLinkSet;
	}
}
