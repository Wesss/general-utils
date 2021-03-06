package org.wesss.general_utils.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a collection where each key has several associated values.
 * When duplicate values are added, previous pairings are overwritten.
 * @param <K> the key type
 * @param <V> the value type
 */
public class OneToMany<K, V> {

    /*
     * For every (K, V) pair present:
     *      V must be the only unique V
     *
     * No empty sets are contained within the maps
     */
    private HashMap<K, HashSet<V>> keyToValues;
    private HashMap<V, K> valueToKey;

    public OneToMany() {
        keyToValues = new HashMap<>();
        valueToKey = new HashMap<>();
    }

    public boolean isEmpty() {
        return keyToValues.isEmpty();
    }

    /**
     * @return the number of key-value pairings present
     */
    public int size() {
        return valueToKey.size();
    }

    public boolean containsKey(K key) {
        return keyToValues.containsKey(key);
    }

    public boolean containsValue(V value) {
        return valueToKey.containsKey(value);
    }

    public Set<K> keySet() {
        return keyToValues.keySet();
    }

    public Set<V> valueSet() {
        return valueToKey.keySet();
    }

    public Set<V> getValues(K key) {
        Set<V> resultSet = keyToValues.get(key);
        if (resultSet == null) {
            return new HashSet<>();
        } else {
            return new HashSet<>(resultSet);
        }
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public void put(K key, V value) {
        removeValue(value);

        if (!keyToValues.containsKey(key))
            keyToValues.put(key, new HashSet<>());
        keyToValues.get(key).add(value);

        valueToKey.put(value, key);
    }

    public boolean remove(K key, V value) {
        Set<V> storedValues = keyToValues.get(key);
        if (storedValues == null || !storedValues.contains(value)) {
            return false;
        }

        valueToKey.remove(value);
        keyToValues.get(key).remove(value);
        if (keyToValues.get(key).isEmpty()) {
            keyToValues.remove(key);
        }

        return true;
    }

    public boolean removeKey(K key) {
        if (!keyToValues.containsKey(key)) {
            return false;
        }

        Set<V> values = getValues(key);
        for (V value : values) {
            remove(key, value);
        }
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
        keyToValues.clear();
        valueToKey.clear();
    }
}
