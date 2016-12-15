package mp.dataclasses;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LinkedHashSetMultimap<K, V> extends AbstractMultimap<K, V> {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<V> createCollection(int initialSize) {
		return new LinkedHashSet<>(initialSize);
	}

	@Override
	protected Map<K, Collection<V>> createMap() {
		return new LinkedHashMap<>();
	}
}
