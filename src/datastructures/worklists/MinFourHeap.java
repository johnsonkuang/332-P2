package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;
import cse332.exceptions.NotYetImplementedException;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;

    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    public MinFourHeap() {
        data = (E[])new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if(size > data.length - 1) {
            E[] data2 = (E[])new Comparable[data.length * 2];
            // copying all elements into new stack
            for(int i = 0; i < data.length; i++){
                data2[i] = data[i];
            }
            data = data2;
        }

        data[size] = work;
        int parentIndex = (size - 1) / 4;
        int childIndex = size;
        // while the parent data is greater than the work, percolate work up
        while(parentIndex >= 0 && work.compareTo(data[parentIndex]) < 0){
            data[childIndex] = data[parentIndex];
            data[parentIndex] = work;
            childIndex = parentIndex;
            parentIndex = (childIndex - 1) / 4;
        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("Heap is empty.");
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException("Heap is empty.");
        }
        E rootRef = data[0];
        data[0] = data[size - 1];
        size--;
        int parentIndex = 0;
        int minChildIndex = findMinChildIndex(parentIndex);
        // while the parent data is greater than the child data, percolate down by
        // swapping parent data with the smallest child data
        while(minChildIndex < size && data[parentIndex].compareTo(data[minChildIndex]) > 0){
            E temp = data[parentIndex];
            data[parentIndex] = data[minChildIndex];
            data[minChildIndex] = temp;
            parentIndex = minChildIndex;
            minChildIndex = findMinChildIndex(parentIndex);
        }
        return rootRef;
    }

    private int findMinChildIndex(int index){
        int minIndex = (4*index) + 1;
        int i = (4*index) + 2;
        while(i < size && i <= (4*index) + 4){
            if(data[minIndex].compareTo(data[i]) > 0){
                minIndex = i;
            }
            i++;
        }
        return minIndex;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }
}
