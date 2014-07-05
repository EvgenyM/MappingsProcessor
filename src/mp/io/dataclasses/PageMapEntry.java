package mp.io.dataclasses;
import java.util.Map;

import mp.dataclasses.WikiPage;

/**
 * Wrapper class for {@link Entry} for JSON parser 
 * @author Evgeny Mitichkin
 *
 * @param <K>
 * @param <V>
 */
public final class PageMapEntry <K, V> implements Map.Entry<K, V>{	
	
	    private final String key;
	    private WikiPage value;

	    public PageMapEntry(String key, WikiPage value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public K getKey() {
	        return (K) key;
	    }

	    @Override
	    public V getValue() {
	        return (V) value;
	    }

	    @Override
	    public V setValue(V value) {
	    	WikiPage old = this.value;
	        this.value = (WikiPage) value;
	        return (V) old;
	    }	
}
