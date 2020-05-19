/**
 *  BinaryHeapPriorityQueue
 *  Implement a FIFO Priority Queue to insert generic Wrapper items in a min heap
 *  and compare their priorities using the Comparable Interface
 *  March 27th, 2020
 *  @author  Jason Songvilay cssc1277
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

@SuppressWarnings("unchecked")
public class BinaryHeapPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    public static final int DEFAULT_MAX_CAPACITY = 1000;
    private int currentSize, maxSize;
    private long modificationCounter, entryNumber;
    private Wrapper<E>[] heap;

    public BinaryHeapPriorityQueue() {
        this(DEFAULT_MAX_CAPACITY);
        }

    public BinaryHeapPriorityQueue(int max) {
        currentSize = 0;
        entryNumber = 0;
        maxSize = max;
        modificationCounter = 0;
        heap = new Wrapper[maxSize];
        }

    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E object) {
        if (isFull()) 
            return false;
        // insert Wrapper object as a child
        heap[currentSize] = new Wrapper<E>(object);
        trickleUp(currentSize); // determine correct placement at current position
        currentSize++;
        modificationCounter++;
        return true;
        }

    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
    public E remove() {
        if (isEmpty()) 
            return null;
        E removed = heap[0].data; // parent node to be removed and returned
        trickleDown(0); // remove parent and reorder the heap by swapping parent and children
        currentSize--;
        modificationCounter++;
        return removed;
        }

    // Deletes all instances of the parameter obj from the PQ if found, and
    // returns true. Returns false if no match to the parameter obj is found.
    public boolean delete(E obj) {
        if (isEmpty()) 
            return false;
        // iterate through the heap, if matching element is found
        // then swap parent and children
        for (int i = 0; i < currentSize; i++) {
            if (heap[i].data.compareTo(obj) == 0) {
                trickleDown(i);
                currentSize--;
                }
            if (heap[0].data.compareTo(obj) == 0) {
                remove(); // if the parent node is also matching, then remove it
                }
            }
        modificationCounter++;
        return true;
        }

    // Returns the object of highest priority that has been in the
    // PQ the longest, but does NOT remove it.
    // Returns null if the PQ is empty.
    public E peek() {
        if (isEmpty()) 
            return null;
        return heap[0].data;
        }

    // Returns true if the priority queue contains the specified element
    // false otherwise.
    public boolean contains(E obj) {
        for (int i = 0; i < currentSize; i++) {
            if ((heap[i].data).compareTo(obj) == 0)
                return true;
            }
        return false;
        }

    // Returns the number of objects currently in the PQ.
    public int size() { return currentSize; }

    // Returns the PQ to an empty state.
    public void clear() {
        currentSize = 0;
        modificationCounter = 0;
        }

    // Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() { return currentSize == 0; }

    // Returns true if the PQ is full, otherwise false. List based
    // implementations should always return false.
    public boolean isFull() { return currentSize == maxSize; }

    // Returns an iterator of the objects in the PQ, in no particular
    // order.
    public Iterator<E> iterator() {
        return new IteratorHelper();
        }

    // Method from Riggins course reader, modified to accept index parameter
    // if any child node is smaller than its parent, it's swapped
    private void trickleUp(int index) {
        int newIndex = index;
        int parentIndex = (newIndex - 1) >> 1;
        Wrapper<E> newValue = heap[newIndex];
        while (parentIndex >= 0 && newValue.compareTo(heap[parentIndex]) < 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = (parentIndex - 1) >> 1;
            }
        // new node is inserted to new position
        heap[newIndex] = newValue;
        }

    // Method from Riggins course reader, modified to accept index parameter
    // if any child node is larger than its parent, it's swapped and removed
    private void trickleDown(int index) {
        int current = index;
        int child = getNextChild(current);
        while (child != -1 && heap[current].compareTo(heap[child]) < 0
                && heap[child].compareTo(heap[currentSize - 1]) < 0) {
            heap[current] = heap[child];
            current = child;
            child = getNextChild(current);
            }
        // last node gets moved to current position
        heap[current] = heap[currentSize - 1];
        }

    // Auxiliary method to find the next child to be
    // inserted or removed from Riggins course reader
    private int getNextChild(int current) {
        int left = (current << 1) + 1;
        int right = left + 1;
        if (right < currentSize) { // if there are 2 children
            if (heap[left].compareTo(heap[right]) < 0) {
                return left; // left child is smaller
                }
            return right; // right child is smaller
        }
        if (left < currentSize) { // only one child
            return left;
            }
        return -1;
        }

    // Fail-fast iterator from Riggins course reader
    class IteratorHelper implements Iterator<E> {
        int iterIndex;
        long stateCheck;

        public IteratorHelper() {
            iterIndex = 0;
            stateCheck = modificationCounter;
            }

        public boolean hasNext() {
            if (stateCheck != modificationCounter)
                throw new ConcurrentModificationException();
            return iterIndex < currentSize;
            }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return heap[iterIndex++].data;
            }
    }

    // Wrapper class from Riggins course reader
    protected class Wrapper<E> implements Comparable<Wrapper<E>> {
        E data;
        long number;

        public Wrapper(E d) {
            number = entryNumber++; // counter for priority of object to be inserted
            data = d;
            }

        public int compareTo(Wrapper<E> o) {
            if (((Comparable<E>) data).compareTo(o.data) == 0) {
                return (int) (number - o.number); // if data is equal, then use sequence number
                }
            return ((Comparable<E>) data).compareTo(o.data);
            }
    }
}