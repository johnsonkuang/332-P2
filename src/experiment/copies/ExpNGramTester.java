package experiment.copies;

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

public class ExpNGramTester {
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

    public static final String[] SAMPLE_NGRAMS = {"What a curious", "said Alice to", "a little of", "said Alice, rather",
            "said the Cat", "Twinkle, twinkle, twinkle", "said the Hatter", "tone of great", "Queen of Hearts", "I'll have you"};

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

                    WordSuggestor suggestions = null;
                    //start time
                    long addStartTime = System.nanoTime();
                    switch (dict) {
                        case hashTrieMap:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    p2.clients.NGramTester.trieConstructor(NGram.class),
                                    p2.clients.NGramTester.trieConstructor(AlphabeticString.class));
                            break;
                        case hashTable:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    p2.clients.NGramTester.hashtableConstructor(() -> new AVLTree()),
                                    p2.clients.NGramTester.hashtableConstructor(() -> new AVLTree()));
                            break;
                        case bst:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    p2.clients.NGramTester.binarySearchTreeConstructor(),
                                    p2.clients.NGramTester.binarySearchTreeConstructor());
                            break;
                        case avl:
                            suggestions = new WordSuggestor("alice.txt", 3, -1,
                                    p2.clients.NGramTester.avlTreeConstructor(),
                                    p2.clients.NGramTester.avlTreeConstructor());
                    }
                    //end time
                    long addEndTime = System.nanoTime();
                    //in milliseconds
                    double addTime = (addEndTime - addStartTime);

                    if(trial > NUM_WARMUP){
                        output.append("\tTrial " + (trial - NUM_WARMUP) + ":\n");
                        output.append("\t\tAdd Time: " + (addTime / 1_000_000.0) + "\n");
                        totalAddTime += addTime;
                    }

                    //find

                    //measure the time it takes for each dictionary type to find the results for a pre-selected sample
                    //of messages from Alice
                    long findStartTime = System.nanoTime();
                    for(String msg : SAMPLE_NGRAMS){
                        suggestions.getAllSuggestions(msg);
                    }
                    long findEndTime = System.nanoTime();
                    double findTime = (findEndTime - findStartTime);

                    if(trial > NUM_WARMUP){
                        output.append("\t\tFind Time: " + (findTime / 1_000_000.0) + "\n");
                    }
                }
            }
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
