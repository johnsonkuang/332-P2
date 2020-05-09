package experiment.copies;

import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class ExpCircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private int head;

    private int tail;

    private int size;

    private E[] queue;

    public ExpCircularArrayFIFOQueue(int capacity) {
        super(capacity);
        head = 0;
        tail = 0;
        size = 0;
        queue = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if(isFull()){
            throw new IllegalStateException("Queue is full.");
        }
        if(tail == capacity()){
            tail = 0;
        }
        size++;
        queue[tail++] = work;
    }

    @Override
    public E peek() {
        return peek(0);
    }
    
    @Override
    public E peek(int i) {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        if(i < 0 || i >= size()){
            throw new IndexOutOfBoundsException();
        }
        int index = (head + i) % capacity();
        return queue[index];
    }
    
    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E headRef = queue[head];
        head = (head == capacity() - 1) ? 0 : head + 1;
        size--;
        return headRef;
    }
    
    @Override
    public void update(int i, E value) {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        if(i < 0 || i >= size()){
            throw new IndexOutOfBoundsException();
        }
        int index = (head + i) % capacity();
        queue[index] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        // subtract hashCodes to get the correct sign, divide by abs value to get a reasonable
        // number

        // use iterator to get elements of other object
        // use iterator to get elements of other object
        Iterator<E> otherIterator = other.iterator();
        for(int i = 0; i < this.size; i++) {
            // get the other object's next element
            E otherElt = otherIterator.next();
            // if any corresponding elements are different, return false
            if(this.queue[(this.head + i) % capacity()].compareTo(otherElt) != 0) {
                return this.queue[(this.head + i) % capacity()].compareTo(otherElt);
            }

            if(!otherIterator.hasNext() && i != this.size - 1){
                return 1;
            }
        }
        if(otherIterator.hasNext()){
            return -1;
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here
            // first, verify sizes are equal
            if(this.size != other.size()) {
                return false;
            }

            // use iterator to get elements of other object
            Iterator<E> otherIterator = other.iterator();
            for(int i = 0; i < this.size; i++) {
                // get the other object's next element
                E otherElt = otherIterator.next();
                // if any corresponding elements are different, return false
                if(this.queue[(this.head + i) % capacity()].compareTo(otherElt) != 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /*
    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int sum = 0;
        // add all elt
        int idx = 0;
        while(idx != size){
            // multiply by index to factor in position
            sum += this.queue[(head + idx) % this.capacity()].hashCode() * (idx + 1);
            idx = idx + 1;
        }
        return sum;
   }
   */

   //second hash function

   @Override
   public int hashCode() {
        return 1;
   }

}
