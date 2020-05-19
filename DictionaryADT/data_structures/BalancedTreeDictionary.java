 /**
       *  Program #4 BalancedTreeDictionary
       *  Create a Dictionary structure using a red black tree implementation
       *  using Java's TreeMap API
       *  CS310-01
       *  April 22nd, 2020
       *  @author  Jason Songvilay 
       */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class BalancedTreeDictionary<K, V> implements DictionaryADT<K, V> {
    private TreeMap<K, V> redBlackTree;

    public BalancedTreeDictionary() {
        redBlackTree = new TreeMap();
    }

    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean put(K key, V value) {
        if (isFull() || redBlackTree.containsKey(key)) {
            return false;
        }
        redBlackTree.put(key, value);
        return true;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key) {
        if (isEmpty() || redBlackTree.remove(key) == null) {
            return false;
        }
        return true;
    }

    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    public V get(K key) {
        if (isEmpty() || !redBlackTree.containsKey(key)) {
            return null;
        }
        return (V) redBlackTree.get(key);
    }

    // Returns the key associated with the parameter value. Returns
    // null if the value is not found in the dictionary. If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value) {
        Iterator<K> key = keys();
        Iterator<V> val = values();
        while (key.hasNext() && val.hasNext()) {
            if (((Comparable<V>) value).compareTo(val.next()) == 0) {
                return key.next();
            }
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size() {
        return redBlackTree.size();
    }

    // Returns true if the dictionary is full
    public boolean isFull() {
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty() {
        return redBlackTree.isEmpty();
    }

    // Makes the dictionary empty
    public void clear() {
        redBlackTree.clear();
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order
    public Iterator<K> keys() {
        return redBlackTree.keySet().iterator();
    }

    // Returns an Iterator of the values in the dictionary. The
    // order of the values must match the order of the keys.
    public Iterator<V> values() {
        return redBlackTree.values().iterator();
    }
}