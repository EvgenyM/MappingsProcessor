package mp.preprocessing;

import java.util.HashMap;
import java.util.Map.Entry;

import mp.io.JsonIONotifier;

/**
 * Parses the raw WikiData file (from the WikiData projects) and searches for ILLs in it.
 * @author EG
 *
 */
public class IllSQLDataParser implements JsonIONotifier {
	
	private static IllSQLDataParser instance = null;
	
	public static IllSQLDataParser getInstance() {
		if (instance == null) {
            instance = new IllSQLDataParser();
        }
        return instance;
	}
	
	private IllSQLDataParser() { }
	
	
	
	

	@Override
	public <K, V> boolean onChunkProcessed(HashMap<K, V> pageSet) {
		boolean success = true;
		try {
			for (Entry<K, V> pg : pageSet.entrySet()) {				
				
			}
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}		
		return success;
	}

}
