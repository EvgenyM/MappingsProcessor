package mp.dataclasses;

import java.util.Collection;
import java.util.Map;

public interface Multimap<K, V> {

    int size();

    boolean isEmpty();

    Collection<V> get(K key);

    boolean containsKey(K key);

    Collection<V> remove(K key);

    boolean put(K key, V value);

    boolean putAll(K key, Collection<V> values);

    boolean putAll(Map<K, Collection<V>> map);

    V getFirst(K key);

    V getFirstOrElse(K key, V defaultValue);

    Map<K, Collection<V>> asMap();

    void clear();

    boolean replaceAll(K key, Collection<V> values);
}