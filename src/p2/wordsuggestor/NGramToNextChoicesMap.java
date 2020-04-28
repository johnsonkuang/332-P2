package p2.wordsuggestor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map; // outer map
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner; // inner map

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        // check if ngram is in outer map
        if(map.find(ngram) == null) {
            map.insert(ngram, newInner.get());
        }
        // get reference to inner map for this ngram
        Dictionary<AlphabeticString, Integer> innerMapRef = map.find(ngram);

        // convert word to alphabetic word
        AlphabeticString alphabeticWord = new AlphabeticString(word);

        // check that word is in the map
        if(innerMapRef.find(alphabeticWord) == null) {
            // if not, create new mapping at 0
            innerMapRef.insert(alphabeticWord, 0);
        }
        // increment occurrences
        innerMapRef.insert(alphabeticWord, innerMapRef.find(alphabeticWord) + 1);
    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        // get reference to map for this ngram
        Dictionary<AlphabeticString, Integer> outerRef = map.find(ngram);

        if(outerRef == null) {
            return (Item<String, Integer>[]) new Item[0];
        }

        // create empty array
        Item<String, Integer>[] countsArr = (Item<String, Integer>[]) new Item[outerRef.size()];

        // get iterator for inner map
        Iterator<Item<AlphabeticString, Integer>> innerIterator = outerRef.iterator();
        int i = 0;
        while(innerIterator.hasNext()) {
            // store item from iterator
            Item<AlphabeticString, Integer> itemRef = innerIterator.next();
            // create string key of item's alphabeticString key
            String key = itemRef.key.toString();
            // create new Item with string key
            countsArr[i] = new Item<>(key, itemRef.value);
            i++;
        }
        return countsArr;
    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            InsertionSort.sort(afterNGrams, comp);
        }
        else {
            // You must fix this line toward the end of the project
            throw new NotYetImplementedException();
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
