package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private QueueNode<E> head;

    private QueueNode<E> tail;

    private int size;
    
    public ListFIFOQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        QueueNode<E> newNode = new QueueNode<>(work);
        if(head == null){
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("The queue is empty.");
        }
        return head.data;
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException("The queue is empty.");
        }
        QueueNode<E> headRef = head;
        head = head.next;
        size--;
        return headRef.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private class QueueNode<E> {
        public E data;
        public QueueNode<E> next;

        public QueueNode(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
