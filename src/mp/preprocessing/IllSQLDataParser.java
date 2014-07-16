package mp.preprocessing;

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
		int idx = line.indexOf(SQL_VALUES);
		if (idx>0) {
			String strToSplit = line.substring(idx, line.length()-1);
			String[] els = strToSplit.split("\\),\\(");
			for (int i=0;i<els.length;i++) {
				if (i!=els.length-1){
					if (i==0) {
						els[i] = els[i].substring(SQL_VALUES.length()).trim();
						els[i] = els[i].substring(1);
					}
					String[] oneEl = els[i].split(",");
					for (int j=0;j<oneEl.length;j++) {
						oneEl[j] = oneEl[j].replaceAll("'", "");
					}				
					try {
						SQLtoIllWrapper wrapper = new SQLtoIllWrapper();
						wrapper.setId(Long.parseLong(oneEl[0]));
						/*if (oneEl[1].equals(""))
							oneEl[1] = "NAN";*/
						wrapper.setLang(oneEl[1]);
						/*if (oneEl[2].equals(""))
							oneEl[2] = "NAN";*/
						wrapper.setTitle(oneEl[2]);
						result.put(wrapper.getId(), wrapper);
					} catch (Exception parsingException) {
						parsingException.printStackTrace();
					}							
				} else {
					String el = els[i].substring(0, els[i].length()-1);
					String[] oneEl = el.split(",");
					for (int j=0;j<oneEl.length;j++) {
						oneEl[j] = oneEl[j].replaceAll("'", "");
					}
					try {
						SQLtoIllWrapper wrapper = new SQLtoIllWrapper();
						wrapper.setId(Long.parseLong(oneEl[0]));
						/*if (oneEl[1].equals(""))
							oneEl[1] = "NAN";*/
						wrapper.setLang(oneEl[1]);
						/*if (oneEl[2].equals(""))
							oneEl[2] = "NAN";*/
						wrapper.setTitle(oneEl[2]);
						result.put(wrapper.getId(), wrapper);
					} catch (Exception parsingException) {
						parsingException.printStackTrace();
					}
				}
			}
		} 	
		return result;		
	}

	public HashMap<Long, SQLtoIllWrapper> getLangLinkSet() {
		return langLinkSet;
	}
}
