package p2.clients;

import java.io.IOException;
import java.util.function.Supplier;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import p2.wordsuggestor.WordSuggestor;

import javax.sound.midi.SysexMessage;

public class NGramTester {
    public static <A extends Comparable<A>, K extends BString<A>, V> Supplier<Dictionary<K, V>> trieConstructor(Class<K> clz) {
        return () -> new HashTrieMap<A, K, V>(clz);
    }

    public static <K, V> Supplier<Dictionary<K, V>> hashtableConstructor(
            Supplier<Dictionary<K, V>> constructor) {
        return () -> new ChainingHashTable<K, V>(constructor);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <K, V> Supplier<Dictionary<K, V>> binarySearchTreeConstructor() {
        return () -> new BinarySearchTree();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <K, V> Supplier<Dictionary<K, V>> avlTreeConstructor() {
        return () -> new AVLTree();
    }

    public enum dicts {
        hashTrieMap, hashTable, bst, avl
    }

    public static final int NUM_TESTS = 8;
    public static final int NUM_WARMUP = 3;

    public static void main(String[] args) {
        try {
            StringBuilder output = new StringBuilder();
            for(dicts dict: dicts.values()){
                output.append("---------------------------------------\n");
                output.append("Results for " + dict.name() + "\n");

                double totalAddTime = 0;
                double totalFindTime = 0;

                for(int trial = 1; trial <= NUM_TESTS; trial++){
                    //start add time

                    WordSuggestor suggestions;
                    //start time
                    long addStartTime = System.nanoTime();
                    switch (dict) {
                        case hashTrieMap:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    NGramTester.trieConstructor(NGram.class),
                                    NGramTester.trieConstructor(AlphabeticString.class));
                            break;
                        case hashTable:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    NGramTester.hashtableConstructor(() -> new AVLTree()),
                                    NGramTester.hashtableConstructor(() -> new AVLTree()));
                            break;
                        case bst:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    NGramTester.binarySearchTreeConstructor(),
                                    NGramTester.binarySearchTreeConstructor());
                            break;
                        case avl:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    NGramTester.avlTreeConstructor(),
                                    NGramTester.avlTreeConstructor());
                    }
                    //end time
                    long addEndTime = System.nanoTime();
                    //in milliseconds
                    double addTime = (addEndTime - addStartTime) / 1_000_000.0;

                    if(trial > NUM_WARMUP){
                        output.append("\tTrial " + (trial - NUM_WARMUP) + ":\n");
                        output.append("\t\tAdd Time: " + addTime + " ms\n");
                        totalAddTime += addTime;
                    }
                    //find component

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
