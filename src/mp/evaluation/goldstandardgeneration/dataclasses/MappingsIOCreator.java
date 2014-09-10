package mp.evaluation.goldstandardgeneration.dataclasses;

import mp.io.FileIONotifier;

/**
 * Reads a mapping 
 * @author Evgeny Mitichkin
 *
 */
public class MappingsIOCreator implements FileIONotifier{
	
	private static MappingsIOCreator instance = null;
	private StringBuilder rawMappingsData = new StringBuilder();
	
	public static MappingsIOCreator getInstance() {
		if (instance == null) {
            instance = new MappingsIOCreator();
        }
        return instance;
	}
	
	private MappingsIOCreator() { 
		rawMappingsData = new StringBuilder();
	}

	@Override
	public void onChunkRead(String[] str) {
		for (String strr : str) {
			rawMappingsData.append(strr);
		}
	}

	public void clear() {
		this.rawMappingsData = null;
	}
	
	public String getrawMappings() {
		return rawMappingsData.toString();
	}
}
