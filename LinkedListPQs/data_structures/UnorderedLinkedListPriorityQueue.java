/**
 *  Unordered PriorityQueue with Linked Lists
 *  Implement a FIFO Priority Queue using linked lists and insert generic items in no specific order
 *  and compare priorities using the Comparable Interface
 *  March 9th, 2020
 *  @author  Jason Songvilay 
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

@SuppressWarnings("unchecked")
public class UnorderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    public static final int DEFAULT_MAX_CAPACITY = 1000;
    private Node<E> head;
    private int currentSize;
    private long modificationCounter;

    public UnorderedLinkedListPriorityQueue() {
        head = null;
        currentSize = 0;
        modificationCounter = 0;
    }

    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E object) {
        // create new node
        Node<E> newNode = new Node<E>(object);
        // set the next field of new node to first element of existing list
        newNode.next = head;
        // head pointer gets address of new node
        head = newNode;
        // list has been modified, increase times modified
        modificationCounter++;
        currentSize++;
        return true;
    }

    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
    public E remove() {
        Node<E> current = head;
        Node<E> previous = null;
        Node<E> reference = null;
        Node<E> highestPriority = head;
        if (isEmpty()) {
            return null;
        }
        // iterate through list until end
        while (current != null) {
            // if the node of higher priority is found,
            // set highest priority to current node
            if (highestPriority.data.compareTo(current.data) >= 0) {
                highestPriority = current;
                reference = previous;
            }
            // iterate through list
            previous = current;
            current = current.next;
        }
        // in a list with just head node, head is the highest priority
        if (highestPriority == head) {
            head = head.next;
        } else {
            // if highest priority is not the head, then iterate through list
            reference.next = highestPriority.next;
        }
        currentSize--;
        modificationCounter++;
        return highestPriority.data;
    }

    // Deletes all instances of the parameter obj from the PQ if found, and
    // returns true. Returns false if no match to the parameter obj is found.
    public boolean delete(E obj) {
        if (isEmpty()) {
            return false;
        }
        // if the head is to be deleted, set next node to be the head
        while (head != null && ((Comparable<E>) obj).compareTo(head.data) == 0) {
            head = head.next;
            currentSize--;
        }
        // if desired element is found, set the current to the node
        // after the one to be deleted and iterate through, rest is garbage collected
        Node<E> current = head;
        while (current.next != null) {
            if (((Comparable<E>) obj).compareTo(current.next.data) == 0) {
                current.next = current.next.next;
                currentSize--;
            } else {
                current = current.next;
            }
        }
        modificationCounter++;
        return true;
    }

    // Returns the object of highest priority that has been in the
    // PQ the longest, but does NOT remove it.
    // Returns null if the PQ is empty.
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        // if current node is a higher or equal priority to the head node,
        // set the highest priority to the current node and return
        Node<E> current = head;
        E hiPriority = head.data;
        while (current != null) {
            if ((current.data).compareTo(hiPriority) <= 0) {
                hiPriority = current.data;
            }
            current = current.next;
        }
        return hiPriority;
    }

    // Returns true if the priority queue contains the specified element
    // false otherwise.
    public boolean contains(E obj) {
        if (isEmpty()) {
            return false;
        }
        // if desired element matches node in the list, return true or iterate
        Node<E> newNode = head;
        while (newNode != null) {
            if (((Comparable<E>) obj).compareTo(newNode.data) == 0) {
                return true;
            }
            newNode = newNode.next;
        }
        return false;
    }

    // Returns the number of objects currently in the PQ.
    public int size() {
        return currentSize;
    }

    // Returns the PQ to an empty state.
    public void clear() {
        currentSize = 0;
        head = null;
        modificationCounter = 0;
    }

    // Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() {
        return head == null;
    }

    // Returns true if the PQ is full, otherwise false. List based
    // implementations should always return false.
    public boolean isFull() {
        return false;
    }

    // Returns an iterator of the objects in the PQ, in no particular
    // order.
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {
        Node<E> nodePtr;
        private long modCounter = modificationCounter;

        public IteratorHelper() {
            nodePtr = head;
        }

        public boolean hasNext() {
            if (modCounter != modificationCounter)
                throw new ConcurrentModificationException();
            return nodePtr != null;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E tmp = nodePtr.data;
            nodePtr = nodePtr.next;
            return tmp;
        }
    }

    private class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
            next = null;
        }
    }
}