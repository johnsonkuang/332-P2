package datastructures.dictionaries;

import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.*;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!). 
 * 5. HashTable should be able to resize its capacity to prime numbers for more 
 *    than 200,000 elements. After more than 200,000 elements, it should 
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 *    dictionary/list and return that dictionary/list's iterator. 

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;

    private static final double GROWTH_RATIO_MAX = 0.8;

    private static final int initSize = 61;
    private static final int[] NEXT_PRIMES = {127, 251, 503, 1009, 2011, 4027, 8053, 16111, 32261,
            65003, 130099, 200003};
    private int nextPrimeIndex;

    private Dictionary<K, V>[] hashTable;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        nextPrimeIndex = 0;
        hashTable = (Dictionary<K, V>[]) new Dictionary[initSize];
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // check load factor first
        if(needsRehash()) {
            rehash();
        }
        V prevVal = hashInsert(key, value, hashTable);
        if(prevVal == null) {
            size++;
        }
        return prevVal;
    }

    // calculates and returns the hash index for the key based on given length
    private int getHashIndex(K key, int length) {
        // generate key hash
        int keyHash = key.hashCode();
        // mod by capacity
        return keyHash % length;
    }

    // helper method — checks and inserts item, returns previous value
    private V hashInsert (K key, V value, Dictionary<K, V>[] table) {
        int hashIndex = getHashIndex(key, table.length);

        // check if chain exists at index
        if(table[hashIndex] == null) {
            table[hashIndex] = newChain.get();
        }
        // get old value
        // try insert, if already exists, won't add
        return table[hashIndex].insert(key, value);
    }

    // checks if load factor is too high
    private boolean needsRehash() {
        return ((double) this.size) / hashTable.length > GROWTH_RATIO_MAX;
    }

    // uses hashInsert to add all elements to new hashTable
    private void rehash() {
        // setup new hashTable with ~double capacity

        // init to double size as default
        int nextTableSize = hashTable.length * 2;
        // check nextPrimeIndex, if still in table range, use prime size
        if(nextPrimeIndex < NEXT_PRIMES.length) {
            nextTableSize = NEXT_PRIMES[nextPrimeIndex];
            nextPrimeIndex++;
        }
        Dictionary<K, V>[] newHashTable =
                (Dictionary<K, V>[]) new Dictionary[nextTableSize];

        for(Dictionary<K, V> chains : hashTable) {
            if(chains != null) {
                for(Item<K, V> item : chains) {
                    hashInsert(item.key, item.value, newHashTable);
                }
            }
        }
        hashTable = newHashTable;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        int hashIndex = getHashIndex(key, hashTable.length);
        if(hashTable[hashIndex] != null) {
            return hashTable[hashIndex].find(key);
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        // check if table index >= length
        // else, run through current iterator
        // running out, get next available index's iterator
        return new ChainingHashTableIterator();
    }

    private class ChainingHashTableIterator implements Iterator<Item<K, V>> {
        Item<K, V> curr;
        private int hashTableIndex = 0;
        private int itemsSeen = 0;
        private Iterator<Item<K, V>> dictItr;

        public ChainingHashTableIterator() {
            dictItr = getNextIterator();
        }

        private Iterator<Item<K, V>> getNextIterator() {
            // loop through hashTable until dict (with iterator) is found
            while(hashTableIndex < ChainingHashTable.this.hashTable.length &&
                  ChainingHashTable.this.hashTable[hashTableIndex] == null){
                hashTableIndex++;
            }
            if(hashTableIndex == ChainingHashTable.this.hashTable.length){
                throw new IndexOutOfBoundsException();
            }
            //invariant: hashTableIndex points to the index of the first dictionary in the hashtable
            return ChainingHashTable.this.hashTable[hashTableIndex].iterator();
        }

        @Override
        public boolean hasNext() {
            return itemsSeen < ChainingHashTable.this.size();
        }

        @Override
        public Item<K, V> next() {
            // if current dict iterator empty, use index to search for next iterator
            if(!dictItr.hasNext()) {
                dictItr = getNextIterator();
            }
            itemsSeen++;
            return dictItr.next();
        }
    }
}
