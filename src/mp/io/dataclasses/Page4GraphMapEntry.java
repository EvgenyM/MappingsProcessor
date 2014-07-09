package mp.io.dataclasses;

import java.util.Map;

import mp.dataclasses.WikiPage4Graph;

/**
 * Wrapper class for {@link Entry} for JSON parser
 * @author Evgeny Mitichkin
 *
 * @param <K>
 * @param <V>
 */
public class Page4GraphMapEntry<K, V> implements Map.Entry<K, V> {

	private final String key;
    private WikiPage4Graph value;

    public Page4GraphMapEntry(String key, WikiPage4Graph value) {
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
	public V setValue(V arg0) {
		WikiPage4Graph old = this.value;
        this.value = (WikiPage4Graph) value;
        return (V) old;
	}

}
