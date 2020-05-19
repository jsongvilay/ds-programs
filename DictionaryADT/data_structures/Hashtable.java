 /**
       *  Program #4 Hashtable
       *  Create a Dictionary structure using a hashtable with chaining implementation
       *  CS310-01
       *  April 22nd, 2020
       *  @author  Jason Songvilay cssc1277
       */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class Hashtable<K, V> implements DictionaryADT<K, V> {
    private int currentSize, tableSize, maxSize;
    private long modCounter;
    private ListADT<DictionaryNode<K, V>>[] list;

    public Hashtable(int n) {
        currentSize = 0;
        maxSize = n;
        tableSize = (int) (maxSize * 1.3f);
        list = new LinkedListDS[tableSize];
        for (int i = 0; i < tableSize; i++)
            list[i] = new LinkedListDS<DictionaryNode<K, V>>();
    }

    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    // Modified from Rob Edwards add() method video
    public boolean put(K key, V value) {
        if (list[getIndex(key)].contains(new DictionaryNode<K, V>(key, null)))
            return false;
        DictionaryNode<K, V> element = new DictionaryNode(key, value);
        int hashVal = getIndex(key);
        list[hashVal].addLast(element);
        currentSize++;
        modCounter++;
        return true;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    // Modified from Rob Edwards remove() method video
    public boolean delete(K key) {
        if (isEmpty() || !list[getIndex(key)].contains(new DictionaryNode<K, V>(key, null)))
            return false;
        DictionaryNode<K, V> element = new DictionaryNode(key, null);
        int hashVal = getIndex(key);
        list[hashVal].remove(element);
        currentSize--;
        modCounter++;
        return true;
    }

    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    // From Riggins lecture 14
    public V get(K key) {
        if (isEmpty() || !list[getIndex(key)].contains(new DictionaryNode<K, V>(key, null)))
            return null;
        DictionaryNode<K, V> tmp = list[getIndex(key)].search(new DictionaryNode<K, V>(key, null));
        if (tmp == null)
            return null;
        return tmp.value;
    }

    // Returns the key associated with the parameter value. Returns
    // null if the value is not found in the dictionary. If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value) {
        if (isEmpty())
            return null;
        for (int i = 0; i < tableSize; i++)
            for (DictionaryNode<K, V> he : list[i])
                if (((Comparable<V>) value).compareTo(he.value) == 0)
                    return he.key;
        return null;
    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size() {
        return currentSize;
    }

    // Returns true if the dictionary is full
    public boolean isFull() {
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty() {
        return currentSize == 0;
    }

    // Makes the dictionary empty
    public void clear() {
        for (int i = 0; i < tableSize; i++)
            list[i].makeEmpty();
        currentSize = 0;
        modCounter = 0;
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order
    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    }

    // Returns an Iterator of the values in the dictionary. The
    // order of the values must match the order of the keys.
    public Iterator<V> values() {
        return new ValueIteratorHelper();
    }

    // From Riggins course reader
    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K, V>[] nodes;
        protected int idx;
        protected long modCheck;

        public IteratorHelper() {
            nodes = new DictionaryNode[currentSize];
            idx = 0;
            int j = 0;
            modCheck = modCounter;
            for (int i = 0; i < tableSize; i++)
                for (DictionaryNode n : list[i])
                    nodes[j++] = n;
            nodes = (DictionaryNode<K, V>[]) shellSort(nodes);
        }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currentSize;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }

        public K next() {
            return (K) nodes[idx++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }

        public V next() {
            return (V) nodes[idx++].value;
        }
    }

    private class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
        K key;
        V value;

        public DictionaryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(DictionaryNode<K, V> node) {
            return ((Comparable<K>) key).compareTo(((K) node.key));
        }
    }

    // From Riggins lecture 14 video
    private int getIndex(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % tableSize;
    }

    // From Riggins course reader, modified for use with DictionaryNode<K,V>
    private DictionaryNode<K, V>[] shellSort(DictionaryNode[] array) {
        DictionaryNode<K, V>[] n = array;
        int in, out, h = 1;
        DictionaryNode<K, V> temp = null;
        int size = n.length;
        while (h <= size / 3)
            h = h * 3 + 1;
        while (h > 0) {
            for (out = h; out < size; out++) {
                temp = n[out];
                in = out;
                while (in > h - 1 && n[in - h].compareTo(temp) >= 0) {
                    n[in] = n[in - h];
                    in -= h;
                }
                n[in] = temp;
            }
            h = (h - 1) / 3;
        }
        return n;
    }
}
