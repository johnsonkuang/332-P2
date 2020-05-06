package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers =
                    new ChainingHashTable<A, HashTrieNode>(() -> new AVLTree<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new HashTrieNodeIterator();
        }

        private class HashTrieNodeIterator implements Iterator<Entry<A,
                HashTrieMap<A, K, V>.HashTrieNode>>{

            Iterator<Item<A, HashTrieNode>> entryItr =
                    HashTrieMap.HashTrieNode.this.pointers.iterator();

            @Override
            public boolean hasNext() {
                return entryItr.hasNext();
            }

            @Override
            public Entry<A, HashTrieNode> next() {
                Item<A, HashTrieNode> nextItem = entryItr.next();
                return new AbstractMap.SimpleEntry<>(nextItem.key, nextItem.value);
            }
        }
    }

    private enum Method {
        find, findPrefix, insert;
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode nodeRef = (HashTrieNode)trieMapHelper(key, Method.insert);
        V foundVal = nodeRef.value; // null if no value
        if(foundVal == null) {      // increment size if there was no existing value
            this.size++;
        }

        nodeRef.value = value;
        return foundVal;
    }

    @Override
    public V find(K key) {
        return (V)trieMapHelper(key, Method.find);
    }

    @Override
    public boolean findPrefix(K key) {
        return (boolean)trieMapHelper(key, Method.findPrefix);
    }

    // based on the flag, the correct values are returned in order to execute the behavior of the method
    // that is indicated by the flag
    private Object trieMapHelper(K key, Method methodFlag){
        if(key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> keyIterator = key.iterator();

        HashTrieNode nodeRef = (HashTrieNode) this.root;
        while(keyIterator.hasNext()) {
            A nextChar = keyIterator.next();
            if(nodeRef.pointers.find(nextChar) == null) {
                switch (methodFlag){
                    case find:
                        return null;
                    case findPrefix:
                        return false;
                    case insert:
                        nodeRef.pointers.insert(nextChar, new HashTrieNode());
                        break;
                }
            }
            nodeRef = nodeRef.pointers.find(nextChar);
        }
        switch (methodFlag){
            case find:
                return nodeRef.value;
            case findPrefix:
                return true;
            case insert:
                return nodeRef;
            default:
                return null;
        }
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
