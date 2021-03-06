package org.wesss.general_utils.collection;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents a collection where each key has exactly one value and vice versa.
 * When duplicate keys or values are added, any previous pairings are overwritten.
 * @param <K> the key type
 * @param <V> the value type
 */
public class OneToOne<K, V> {

    private HashMap<K, V> keyToValue;
    private HashMap<V, K> valueToKey;

    public OneToOne() {
        keyToValue = new HashMap<>();
        valueToKey = new HashMap<>();
    }

    public boolean isEmpty() {
        return keyToValue.isEmpty();
    }

    public int size() {
        return keyToValue.size();
    }

    public boolean containsKey(K key) {
        return keyToValue.containsKey(key);
    }

    public boolean containsValue(V value) {
        return valueToKey.containsKey(value);
    }

    public Set<K> keySet() {
        return keyToValue.keySet();
    }

    public Set<V> valueSet() {
        return valueToKey.keySet();
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public void put(K key, V value) {
        removeKey(key);
        removeValue(value);

        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public boolean remove(K key, V value) {
        V storedValue = keyToValue.get(key);
        if (storedValue == null || !storedValue.equals(value)) {
            return false;
        }

        keyToValue.remove(key);
        valueToKey.remove(value);
        return true;
    }

    public boolean removeKey(K key) {
        if (!keyToValue.containsKey(key)) {
            return false;
        }

        remove(key, keyToValue.get(key));
        return true;
    }

    public boolean removeValue(V value) {
        if (!valueToKey.containsKey(value)) {
            return false;
        }

        remove(valueToKey.get(value), value);
        return true;
    }

    public void clear() {
        keyToValue.clear();
        valueToKey.clear();
    }
}
