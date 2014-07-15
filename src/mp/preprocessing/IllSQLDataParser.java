package mp.preprocessing;

import java.util.HashMap;

import mp.dataclasses.SQLtoIllWrapper;
import mp.io.FileIONotifier;

/**
 * Parses the raw WikiData file (from the WikiData projects) and searches for ILLs in it.
 * @author EG
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
				getLangLinkSet().putAll(parseLine(str[i]));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	/**
	 * Parses the "INSERT INTO" string
	 * @param line
	 * @return
	 */
	private HashMap<Long, SQLtoIllWrapper> parseLine(String line) {
		HashMap<Long, SQLtoIllWrapper> result = new HashMap<Long, SQLtoIllWrapper>();
		String strToSplit = line.substring(line.indexOf(SQL_VALUES), line.length()-1);
		String[] els = strToSplit.split("),(");
		for (int i=0;i<els.length;i++) {
			if (i!=els.length-1){
				String[] oneEl = els[i].split(",");
				for (int j=0;j<oneEl.length;j++) {
					oneEl[j] = oneEl[j].replaceAll("'", "");
				}				
				try {
					SQLtoIllWrapper wrapper = new SQLtoIllWrapper();
					wrapper.setId(Long.parseLong(oneEl[0]));
					wrapper.setLang(oneEl[1]);
					wrapper.setLang(oneEl[2]);
					getLangLinkSet().put(wrapper.getId(), wrapper);
				} catch (Exception parsingException) {
					parsingException.printStackTrace();
				}							
			} else {
				String el = els[i].substring(0, els[i].length()-2);
				String[] oneEl = el.split(",");
				for (int j=0;j<oneEl.length;j++) {
					oneEl[j] = oneEl[j].replaceAll("'", "");
				}
				try {
					SQLtoIllWrapper wrapper = new SQLtoIllWrapper();
					wrapper.setId(Long.parseLong(oneEl[0]));
					wrapper.setLang(oneEl[1]);
					wrapper.setLang(oneEl[2]);
					getLangLinkSet().put(wrapper.getId(), wrapper);
				} catch (Exception parsingException) {
					parsingException.printStackTrace();
				}
			}
		}
		return result;		
	}

	public HashMap<Long, SQLtoIllWrapper> getLangLinkSet() {
		return langLinkSet;
	}
}
