package mp.io;

import java.io.IOException;
import java.util.HashMap;

import mp.dataclasses.WikiPage;


/**
 * Enables serealization and deserealization of data objects, as well as IO operations.
 * Example: Read json->convert to {@link WikiPage}
 * @author Evgeny Mitichkin
 *
 */
public class ObjectConverter {

	public ObjectConverter() { }
	
	/**
	 * Reads a dataset and converts it to a given objects if possible
	 * @param dataAsJsonPath Path to the dataset in serialized (JSON) format
	 * @param clazz Class of the object to be converted to
	 * @return HashMap
	 */
	public <K, V, T> HashMap<K, V> getAsHashMap(String dataAsJsonPath, Class<T> clazz) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		HashMap<K, V> wikiData = new HashMap<K, V>();
		try {
			wikiData = jsonIOManager.readFromJson(dataAsJsonPath, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wikiData;
	}
	
	/**
	 * Serializes the object ({@link HashMap}) and writes it to a file
	 * @param dataset Dataset as a {@link HashMap}
	 * @param dataAsJsonPath path to write the serialized dataset
	 */
	public <K, V> void dumpHashMap(HashMap<K, V> dataset, String dataAsJsonPath) {
		JsonIOManager jsonIOManager = new JsonIOManager();
		jsonIOManager.writeToJson(dataset, dataAsJsonPath);
	}
}
