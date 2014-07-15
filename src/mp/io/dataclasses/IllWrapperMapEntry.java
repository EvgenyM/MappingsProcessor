package mp.io.dataclasses;

import java.util.Map;

import mp.dataclasses.SQLtoIllWrapper;

/**
 * Wrapper class for {@link Entry} for JSON parser
 * @author Evgeny Mitichkin
 *
 * @param <K>
 * @param <V>
 */
public class IllWrapperMapEntry<K, V> implements Map.Entry<K, V> {

	private final Long key;
    private SQLtoIllWrapper value;
    
    public IllWrapperMapEntry(Long key, SQLtoIllWrapper value) {
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
		SQLtoIllWrapper old = this.value;
        this.value = (SQLtoIllWrapper) value;
        return (V) old;
	}

}
