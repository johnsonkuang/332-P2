package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
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
            if(!nodeRef.pointers.containsKey(nextChar)) {
                switch (methodFlag){
                    case find:
                        return null;
                    case findPrefix:
                        return false;
                    case insert:
                        nodeRef.pointers.put(nextChar, new HashTrieNode());
                        break;
                }
            }
            nodeRef = nodeRef.pointers.get(nextChar);
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
        if(key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> keyIterator = key.iterator();

        HashTrieNode nodeRef = (HashTrieNode) this.root;

        // The last node with multiple children and/or a value (highest possible node we can
        // sever from)
        HashTrieNode topNode = null;
        // Keep reference of the next character key from topNode
        A topChar = null;

        while(keyIterator.hasNext()) {
            A nextChar = keyIterator.next();
            if(nodeRef.value != null || nodeRef.pointers.size() > 1) {
                topNode = nodeRef;
                topChar = nextChar;
            }
            // if there is no connection via the nextChar, stop
            if(!nodeRef.pointers.containsKey(nextChar)) {
                return;
            }
            // otherwise, continue
            nodeRef = nodeRef.pointers.get(nextChar);
        }
        // if current node value is null, don't do anything (child nodes have a non-null value)
        if(nodeRef.value != null) {
            if(nodeRef.pointers.size() > 0) {
                nodeRef.value = null;
            } else {
                if (topNode == null) {
                    this.root = new HashTrieNode();
                } else {
                    topNode.pointers.remove(topChar);
                }
            }
            this.size--;
        }
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
}
