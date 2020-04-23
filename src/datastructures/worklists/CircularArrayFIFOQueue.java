package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private int head;

    private int tail;

    private int size;

    private E[] queue;

    public CircularArrayFIFOQueue(int capacity) {
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
        throw new NotYetImplementedException();
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
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
