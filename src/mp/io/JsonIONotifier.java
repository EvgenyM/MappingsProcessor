package mp.io;

import java.util.HashMap;

import mp.dataclasses.WikiPage;

public interface JsonIONotifier {
	/**
	 * happens when the contributer processes the chunk and requires to read a new portion
	 */
	public boolean onChunkProcessed(HashMap<String, WikiPage> pageSet);
}
