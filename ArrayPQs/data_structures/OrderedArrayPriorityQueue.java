      /**
       *  PriorityQueue
       *  Implement a FIFO Priority Queue and insert generic items in sorted order
       *  and compare priorities using the Comparable Interface
       *  February 12th, 2020
       *  @author  Jason Songvilay 
       */

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private int currentSize, maxSize;
    private E[] storage;

 public OrderedArrayPriorityQueue() {
    this(DEFAULT_MAX_CAPACITY);
    }

 public OrderedArrayPriorityQueue(int max) {
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
    //find the correct index using recursive method
    int insert = findInsertionPoint(object,0,currentSize-1);
    //shift the elements to the left to make space for new insert
    for(int i = currentSize-1; i >= insert; i--) {
        storage[i+1] = storage[i];
     }
    storage[insert] = object;
    currentSize++;
    return true;
    }

 // Removes the object of highest priority that has been in the
 // PQ the longest, and returns it. Returns null if the PQ is empty.
 public E remove() {
     if(isEmpty())
        return null;    
    //return and remove the last element (AKA the highest priority) in the queue 
    return storage[--currentSize];
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
    //return the last element (AKA the highest priority) in the queue
    return storage[currentSize-1];
    }

 // Returns true if the priority queue contains the specified element
 // false otherwise.
 public boolean contains(E obj) {
    int lo = 0;
    int hi = currentSize - 1;
   //binary search whether an obj in the queue matches the parameter
      while (lo <= hi) {
         int mid = (lo + hi) / 2;
         if (((Comparable<E>)storage[mid]).compareTo(obj) < 0) {
            hi = mid - 1;
         } else if (((Comparable<E>)storage[mid]).compareTo(obj) > 0) {
            lo = mid + 1;
         } else {
            return true;
         }
       }
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

 /* Recursive search for correct location to insert new data into array using
 binary search tree, if priority compared is greater or equal to 
 then return the midpoint modified +1 (shift right) or -1 (shift left)*/
 private int findInsertionPoint(E object, int lo, int hi) {
    if(hi < lo)
        return lo;
    int mid = (lo+hi) / 2;
    if(((Comparable<E>)object).compareTo(storage[mid]) >= 0) {
        return findInsertionPoint(object,lo,mid-1); 
    }
    return findInsertionPoint(object, mid+1, hi);
  }
}