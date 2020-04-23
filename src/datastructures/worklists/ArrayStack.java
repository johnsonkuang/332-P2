package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] stack;

    private int top;

    public ArrayStack() {
        stack = (E[])new Object[DEFAULT_CAPACITY];
        top = 0;
    }

    @Override
    public void add(E work) {
        if(top > stack.length - 1) {
            E[] stack2 = (E[])new Object[stack.length * 2];
            // copying all elements into new stack
            for(int i = 0; i < stack.length; i++){
                stack2[i] = stack[i];
            }
            stack = stack2;
        }
        stack[top++] = work;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("The stack is empty.");
        }
        return stack[top - 1];
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException("The stack is empty.");
        }
        // does not need to be set to null because it will be overwritten with next add
        return stack[--top];
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public void clear() {
        stack = (E[])new Object[DEFAULT_CAPACITY];
        top = 0;
    }
}
