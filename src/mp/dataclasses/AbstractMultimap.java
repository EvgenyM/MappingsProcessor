package mp.dataclasses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class AbstractMultimap<K, V> implements Multimap<K, V>, Serializable {
	private final Map<K, Collection<V>> multimap = createMap();

	protected abstract Collection<V> createCollection(int initialSize);

	protected abstract Map<K, Collection<V>> createMap();

	@Override
	public Collection<V> get(K key) {
		return key == null ? null : multimap.get(key);
	}

	@Override
	public boolean containsKey(K key) {
		return multimap.containsKey(key);
	}

	@Override
	public Collection<V> remove(K key) {
		return multimap.remove(key);
	}

	@Override
	public boolean put(K key, V value) {
		Collection<V> values = multimap.get(key);

		if (values == null) {
			values = createCollection(1);
			multimap.put(key, values);
		}

		return values.add(value);
	}

	@Override
	public boolean putAll(K key, Collection<V> values) {
		if (values == null || values.isEmpty()) {
			return false;
		}

		Collection<V> existingValues = multimap.get(key);

		if (existingValues != null) {
			return existingValues.addAll(values);
		} else {
			existingValues = createCollection(values.size());
			existingValues.addAll(values);
			multimap.put(key, existingValues);
			return true;
		}
	}

	@Override
	public boolean replaceAll(K key, Collection<V> values) {
		if (values == null || values.isEmpty()) {
			return false;
		}

		Collection<V> newValues = createCollection(values.size());
		newValues.addAll(values);
		multimap.put(key, newValues);
		return true;
	}

	@Override
	public boolean putAll(Map<K, Collection<V>> map) {
		boolean modified = false;

		for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
			modified |= putAll(entry.getKey(), entry.getValue());
		}

		return modified;
	}

	@Override
	public V getFirst(K key) {
		return getFirstOrElse(key, null);
	}

	@Override
	public V getFirstOrElse(K key, V defaultValue) {
		Collection<V> set = multimap.get(key);
		if (set == null || set.isEmpty()) {
			return defaultValue;
		} else {
			return set.iterator().next();
		}
	}

	@Override
	public Map<K, Collection<V>> asMap() {
		return multimap;
	}

	@Override
	public int size() {
		return multimap.size();
	}

	@Override
	public boolean isEmpty() {
		return multimap.isEmpty();
	}

	@Override
	public void clear() {
		multimap.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AbstractMultimap<?, ?> multimap1 = (AbstractMultimap<?, ?>) o;

		return new EqualsBuilder()
				.append(multimap, multimap1.multimap)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(multimap)
				.toHashCode();
	}
}