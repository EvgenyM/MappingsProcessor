package mp.preprocessing.utils;

import java.util.HashMap;

import mp.dataclasses.ILLTypes;
import mp.dataclasses.WikiPage;
import mp.dataclasses.WikiPage4Graph;
import mp.io.JsonIONotifier;
import mp.io.utils.WikiPageManager;

/**
 * Handles reading operations and {@link WikiPage} to {@link WikiPage4Graph} conversion. Implements Singleton pattern.
 * @author Evgeny Mitichkin
 *
 */
public class Infobox4GraphFactory implements JsonIONotifier{
	
	private static Infobox4GraphFactory instance = null;
	private static HashMap<String, WikiPage4Graph> mPageSet;
	private static ILLTypes selectorType = ILLTypes.All;
	
	private Infobox4GraphFactory() {
		mPageSet =  new HashMap<String, WikiPage4Graph>();
	}	
	
	public static Infobox4GraphFactory getInstance() {
		if (instance == null) {
            instance = new Infobox4GraphFactory();
        }
        return instance;
	}

	@Override
	public <K, V> boolean onChunkProcessed(HashMap<K, V> pageSet) {
		boolean success = true;
		try {
			WikiPageManager mgr = new WikiPageManager();
			getmPageSet().putAll(mgr.toWikiPage4Graph((HashMap<String, WikiPage>) pageSet, selectorType));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		return success;
	}

	public HashMap<String, WikiPage4Graph> getmPageSet() {
		return mPageSet;
	}

	public ILLTypes getSelectorType() {
		return selectorType;
	}

	public void setSelectorType(ILLTypes selectorType) {
		Infobox4GraphFactory.selectorType = selectorType;
	}
}