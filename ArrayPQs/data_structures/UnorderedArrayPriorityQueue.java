      /**
       *  PriorityQueue
       *  Implement a FIFO Priority Queue and insert generic items in no specific order
       *  and compare priorities using the Comparable Interface
       *  February 12th, 2020
       *  @author  Jason Songvilay cssc1277
       */

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private int currentSize, maxSize;
    private E[] storage;

public UnorderedArrayPriorityQueue() {
    this(DEFAULT_MAX_CAPACITY);
    }

public UnorderedArrayPriorityQueue(int max) {
    currentSize = 0;
    maxSize = max;
    storage = (E[]) new Comparable [maxSize];
    }

 // Inserts a new object into the priority queue. Returns true if
 // the insertion is successful. If the PQ is full, the insertion
 // is aborted, and the method returns false.
 public boolean insert(E object) {
     if(isFull()) 
        return false;
    //insert element first and increase size of the queue
    storage[currentSize++] = object;
    return true;
    }

 // Removes the object of highest priority that has been in the
 // PQ the longest, and returns it. Returns null if the PQ is empty.
 public E remove() {
     if(isEmpty())
        return null;    
    E remove = storage[0];
    int highestIndex = 0;
    //if the current element in queue is of a higher priority than 
    //our first element, then get the index of that element for return
    for(int i = 0; i < currentSize; i++) {
        if(((Comparable<E>)storage[i]).compareTo(remove) < 0) {
            highestIndex = i;
            remove = storage[i];
       }
    }
    //shift the elements to the right and decrease the size to 
    //remove highest priority element at the end
    for(int i = highestIndex; i < currentSize-1; i++) {
        storage[i] = storage[i+1];
     }
    currentSize--;
    return remove;
    }

 // Deletes all instances of the parameter obj from the PQ if found, and
 // returns true. Returns false if no match to the parameter obj is found.
public boolean delete(E obj) {
    if(!contains(obj))
        return false;
    int i = 0;
    int count = 0;
    //loop through the elements and if the matching element is found, increase count
    //to add to new index i but don't swap elements yet
    for(int k = 0; k < currentSize; k++) {
        if(((Comparable<E>)storage[k]).compareTo(obj) == 0) {
            count++;
        } else {
            //if elements are not the same, swap them by adding index i 
            //with the number of counts (aka matches), increment i 
            storage[i] = storage[i+count];
            i++;
        }
     }
    //reduce the current size subtracted by the number of matches 
    currentSize = currentSize - count;
    return true;
    }
    
 // Returns the object of highest priority that has been in the
 // PQ the longest, but does NOT remove it.
 // Returns null if the PQ is empty.
 public E peek() {
     if(isEmpty()) 
        return null;
    E highestPriority = storage[0];
    //if the current element in queue is of a higher priority than 
    //our first element, then get the data of that element for return
    for(int i = 1; i < currentSize; i++) {
        if(((Comparable<E>)storage[i]).compareTo(highestPriority) < 0) {
            highestPriority = storage[i];
        }
     }
    return highestPriority;
    }

 // Returns true if the priority queue contains the specified element
 // false otherwise.
 public boolean contains(E obj) {
    for(int i = 0; i < currentSize; i++)
        if(((Comparable<E>)storage[i]).compareTo(obj) == 0)
            return true;
    return false;
    }

 // Returns the number of objects currently in the PQ.
 public int size() { return currentSize; }

 // Returns the PQ to an empty state.
 public void clear() { currentSize = 0; }

 // Returns true if the PQ is empty, otherwise false
 public boolean isEmpty() { return currentSize == 0; }

 // Returns true if the PQ is full, otherwise false. List based
 // implementations should always return false.
 public boolean isFull() { return currentSize == maxSize; }

 // Returns an iterator of the objects in the PQ, in no particular
 // order.
 public Iterator<E> iterator() {
    return new Iterator<E>() {
    private int counter = 0;

    public boolean hasNext() { return counter < currentSize; }

    public E next() {
        if(!hasNext())
            throw new NoSuchElementException();
        return storage[counter++];
        }
    };
  }
}