 /**
       *  Program #4 BinarySearchTree
       *  Create a Dictionary structure using a binary tree implementation
       *  CS310-01
       *  April 22nd, 2020
       *  @author  Jason Songvilay 
       */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class BinarySearchTree<K, V> implements DictionaryADT<K, V> {
    private int currentSize;
    private long modCounter;
    private Node<K, V> root;
    boolean usedSuccessorLast;

    public BinarySearchTree() {
        root = null;
        currentSize = 0;
        modCounter = 0;
        }

    // Adds the given key/value pair to the dictionary. Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    // From Riggins course reader
    public boolean put(K key, V value) {
        if (findKey(value, root) != null) 
            return false;
        if (root == null)
            root = new Node<K, V>(key, value);
        else 
            insert(key, value, root, null, false);
        currentSize++;
        modCounter++;
        return true;
        }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and deleted,
    // otherwise false.
    public boolean delete(K key) {
        if (isEmpty() || root == null)
            return false;
        else
            delete(key, root, null, false);
        currentSize--;
        modCounter++;
        return true;
        }

    // Returns the value associated with the parameter key. Returns
    // null if the key is not found or the dictionary is empty.
    public V get(K key) {
        if (isEmpty()) 
            return null;
        return findValue(key, root);
        }

    // Returns the key associated with the parameter value. Returns
    // null if the value is not found in the dictionary. If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value) {
        if (isEmpty()) 
            return null;
        return findKey(value, root);
        }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size() { return currentSize; }

    // Returns true if the dictionary is full
    public boolean isFull() { return false; }

    // Returns true if the dictionary is empty
    public boolean isEmpty() { return currentSize == 0; }

    // Makes the dictionary empty
    public void clear() {
        root = null;
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
    private void insert(K k, V v, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
        if (n == null)
            if (wasLeft)
                parent.leftChild = new Node<K, V>(k, v);
            else 
                parent.rightChild = new Node<K, V>(k, v);
        else if (((Comparable<K>) k).compareTo((K) n.key) < 0) 
            insert(k, v, n.leftChild, n, true);
        else 
            insert(k, v, n.rightChild, n, false);
        }

    private void delete(K k, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
        if (n == null) 
            return;
        else if (((Comparable<K>) k).compareTo(n.key) < 0) 
            delete(k, n.leftChild, n, true); // traverse left
        else if (((Comparable<K>) k).compareTo(n.key) > 0) 
            delete(k, n.rightChild, n, false); // traverse right
        else // node is found, cases:
            if (n.leftChild == null && n.rightChild == null) // node to be deleted has zero children
                if (parent == null)  
                    // 1 node tree is empty once node is deleted, root becomes null
                    root = null;
                else if (wasLeft) 
                    // deleted node was parent's left child
                    parent.leftChild = null;
                else 
                    // deleted node was parent's right child
                    parent.rightChild = null;
            else if (n.leftChild != null && n.rightChild == null) // node to be deleted has 1 left child
                if (parent == null) 
                    // 2 node tree with parent and left child, parent is deleted, so left child
                    // becomes new root
                    root = n.leftChild;
                else if (wasLeft) 
                    // node deleted is on left, left node had a left child, left child becomes
                    // parent's new left child
                    parent.leftChild = n.leftChild;
                else 
                    // node deleted is on right, right node had a left child, left child
                    // becomes parent's new right child
                    parent.rightChild = n.leftChild;
            else if (n.rightChild != null && n.leftChild == null) // node to be deleted has 1 right child
                if (parent == null) 
                    // 2 node tree with parent and right child, parent is deleted, so right child
                    // becomes new root
                    root = n.rightChild;
                else if (!wasLeft)
                    // node deleted is on right, right node had right child, right child
                    // becomes parent's new right child
                    parent.rightChild = n.rightChild;
                else
                    // node deleted is on left, left child had right child, right child
                    // becomes parent's new left child
                    parent.leftChild = n.rightChild;
            else
                // node to be deleted has two children, get inorder successor or predecessor
                // of node to be deleted
                if(usedSuccessorLast) {   
                    usedSuccessorLast = false;  
                    deleteUsingPredecessor(n);
                } else {
                    usedSuccessorLast = true;
                    deleteUsingSuccessor(n);
                }
                
        }

    //Helper method to determine and delete node with 2 children using successor
    private void deleteUsingSuccessor(Node<K, V> n) {
        Node<K, V> node = n.rightChild;
        while (node.leftChild != null && node.rightChild != null) {
            node = node.leftChild;
            }
        if (node.rightChild != null && node.leftChild == null)
            n.key = node.key;
            n.value = node.value;
        }
        
    //Helper method to determine and delete node with 2 children using predecessor
    private void deleteUsingPredecessor(Node<K, V> n) {
        Node<K, V> node = n.leftChild;
        while (node.rightChild != null && node.leftChild != null) {
            node = node.rightChild;
            }
        if (node.leftChild != null && node.rightChild == null)
            n.key = node.key;
            n.value = node.value;
        }

    // getKey() helper method
    private K findKey(V v, Node<K, V> n) {
        if (n == null) 
            return null;
        if (((Comparable<V>) v).compareTo((V) n.value) < 0) 
            return findKey(v, n.leftChild);
        else if (((Comparable<V>) v).compareTo((V) n.value) > 0) 
            return findKey(v, n.rightChild);
        else 
            return n.key;
        }

    // From Riggins course reader, get() helper method
    private V findValue(K k, Node<K, V> n) {
        if (n == null) 
            return null;
        if (((Comparable<K>) k).compareTo(n.key) < 0) 
            return findValue(k, n.leftChild);
        else if (((Comparable<K>) k).compareTo(n.key) > 0) 
            return findValue(k, n.rightChild);
        else 
            return (V) n.value;
        }

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected Node<K, V>[] nodes;
        protected int index, iterIndex;
        protected long modCheck;

        public IteratorHelper() {
            nodes = new Node[currentSize];
            index = 0;
            iterIndex = 0;
            modCheck = modCounter;
            inorderFillArray(root);
            }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return index < currentSize;
            }

        public abstract E next();

        public void delete() {
            throw new UnsupportedOperationException();
            }

        // From Riggins lecture 15
        private void inorderFillArray(Node<K, V> n) {
            if (n == null)
                return;
            inorderFillArray(n.leftChild);
            nodes[iterIndex++] = n;
            inorderFillArray(n.rightChild);
            }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
            }

        public K next() {
            return (K) nodes[index++].key;
            }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
            }

        public V next() {
            return (V) nodes[index++].value;
            }
    }

    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;

        public Node(K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
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
}