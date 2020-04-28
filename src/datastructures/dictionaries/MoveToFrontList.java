package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    MVFNode front;

    public MoveToFrontList() {
        front = null;
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }

        // if key was not found
        if(find(key) == null) {
            // create new node and set it to the front
            front = new MVFNode(key, value, front);
            size++;
            return null;
        }
        // existing node has been moved to front via find()
        // store old value
        V oldValue = front.value;
        front.value = value;
        return oldValue;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }

        if(front == null){
            return null;
        }

        if(front.key.equals(key)) {
            // check if front.key equals key
            return front.value;
        }
        MVFNode curr = front;
        while(curr.next != null) {
            if(curr.next.key.equals(key)) {
                // found key in curr.next
                MVFNode temp = curr.next;
                // link curr to curr.next.next
                curr.next = curr.next.next;
                // link temp to front
                temp.next = front;
                front = temp;
                return front.value;
            }
            curr = curr.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }

    private class MoveToFrontListIterator implements Iterator<Item<K, V>> {
        MVFNode curr = MoveToFrontList.this.front;

        @Override
        public boolean hasNext() {
            return this.curr != null;
        }

        @Override
        public Item<K, V> next() {
            Item<K, V> returnNode = new Item<>(curr.key, curr.value);
            curr = curr.next;
            return returnNode;
        }
    }

    private class MVFNode {
        public K key;
        public V value;
        public MVFNode next;

        public MVFNode(K key, V value) {
            this(key, value, null);
        }

        public MVFNode(K key, V value, MVFNode next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
