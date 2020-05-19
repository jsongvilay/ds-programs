/**
 *  Ordered PriorityQueue with Linked Lists
 *  Implement a FIFO Priority Queue using linked lists and insert generic items in priority order
 *  and compare priorities using the Comparable Interface
 *  March 9th, 2020
 *  @author  Jason Songvilay cssc1277
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

@SuppressWarnings("unchecked")
public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    public static final int DEFAULT_MAX_CAPACITY = 1000;
    private Node<E> head;
    private int currentSize;
    private long modificationCounter;

    public OrderedLinkedListPriorityQueue() {
        head = null;
        currentSize = 0;
        modificationCounter = 0;
    }

    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E object) {
        Node<E> newNode = new Node<E>(object);
        Node<E> prev = null, current = head;
        // traverse until finding correct insertion point
        while (current != null && ((Comparable<E>) object).compareTo(current.data) >= 0) {
            prev = current;
            current = current.next;
        }
        // goes in first node or list is empty
        if (prev == null) {
            newNode.next = head;
            head = newNode;
        } else {
            // goes in the middle or end
            prev.next = newNode;
            newNode.next = current;
        }
        modificationCounter++;
        currentSize++;
        return true;
    }

    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        E remove = head.data;
        if (head == null) {
            // list is empty
            return null;
        } else {
            // set head to the next node and return first element (highest priority) to
            // remove
            head = head.next;
        }
        currentSize--;
        modificationCounter++;
        return remove;
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
        // after the one to be deleted is removed and iterate through, rest is garbage
        // collected
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
        // list is sorted in priority order, so return top of head
        return head.data;
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
