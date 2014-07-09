package mp.io;

import java.util.HashMap;

import mp.dataclasses.WikiPage;

public interface JsonIONotifier {
	/**
	 * Happens when the contributer processes the chunk and requires to read a new portion
	 * @param <K>
	 * @param <V>
	 */
	public <K, V> boolean onChunkProcessed(HashMap<K, V> pageSet);
}
