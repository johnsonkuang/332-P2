package experiment.tests;

import datastructures.dictionaries.ChainingHashTable;
import experiment.copies.ExpBinarySearchTree;
import experiment.copies.ExpChainingHashTable;
import experiment.copies.ExpMoveToFrontList;
import experiment.copies.ExpNGramTester;

public class ChainingHashTableChains {

    public static final int[] testSizes = new int[]{2000, 4000, 8000, 16000, 32000};
    public static final int NUM_TESTS = 8;
    public static final int NUM_WARMUP = 5;

    public static void main(String[] args) {
        // get three Chaining HashTables with different chains
        ExpChainingHashTable<Integer, Integer> chainBST =
                new ExpChainingHashTable<>(ExpNGramTester.binarySearchTreeConstructor());
        ExpChainingHashTable<Integer, Integer> chainAVL =
                new ExpChainingHashTable<>(ExpNGramTester.avlTreeConstructor());
        ExpChainingHashTable<Integer, Integer> chainMTF =
                new ExpChainingHashTable<>(ExpMoveToFrontList::new);

        // SORTED || INSERT TESTS \\
        System.out.println("__________________SORTED__INSERT__________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test sorted insert on BST chain
        for(int size : testSizes) {
            System.out.print("|| SORTED INSERT || CHAIN BST || " + size + " || Runtime = ");
            sortedInsert(chainBST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test sorted insert on AVL chain
        for(int size : testSizes) {
            System.out.print("|| SORTED INSERT || CHAIN AVL || " + size + " || Runtime = ");
            sortedInsert(chainAVL, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test sorted insert on MTF chain
        for(int size : testSizes) {
            System.out.print("|| SORTED INSERT || CHAIN MTF || " + size + " || Runtime = ");
            sortedInsert(chainMTF, size);
        }

        // SORTED || FIND TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("___________________SORTED__FIND___________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on BST chain
        for(int size : testSizes) {
            System.out.print("|| SORTED FIND || CHAIN BST || " + size + " || Runtime = ");
            find(chainBST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on AVL chain
        for(int size : testSizes) {
            System.out.print("|| SORTED FIND || CHAIN AVL || " + size + " || Runtime = ");
            find(chainAVL, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on MTF chain
        for(int size : testSizes) {
            System.out.print("|| SORTED FIND || CHAIN MTF || " + size + " || Runtime = ");
            find(chainMTF, size);
        }

        // UNSORTED || INSERT TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("_________________UNSORTED__INSERT_________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on BST chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED INSERT || CHAIN BST || " + size + " || Runtime = ");
            unsortedInsert(chainBST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on AVL chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED INSERT || CHAIN AVL || " + size + " || Runtime = ");
            unsortedInsert(chainAVL, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on MTF chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED INSERT || CHAIN MTF || " + size + " || Runtime = ");
            unsortedInsert(chainMTF, size);
        }

        // UNSORTED || FIND TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("__________________UNSORTED__FIND__________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on BST chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED FIND || CHAIN BST || " + size + " || Runtime = ");
            find(chainBST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on AVL chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED FIND || CHAIN AVL || " + size + " || Runtime = ");
            find(chainAVL, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on MTF chain
        for(int size : testSizes) {
            System.out.print("|| UNSORTED FIND || CHAIN MTF || " + size + " || Runtime = ");
            find(chainMTF, size);
        }

    }

    private static void sortedInsert(ExpChainingHashTable<Integer, Integer> tree, int testSize) {

        // store total time for multiple trials
        double totalTime = 0;

        // run NUM_TESTS trials
        for (int n = 0; n < NUM_TESTS; n++) {


            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                long startTime = System.currentTimeMillis();

                tree.insert(i, i);

                long endTime = System.currentTimeMillis();

                // only add for non-warmup
                if (i > NUM_WARMUP) {
                    totalTime += (endTime - startTime);
                }
            }
        }
        double avgTime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(avgTime);
    }

    private static void find(ExpChainingHashTable<Integer, Integer> tree, int testSize) {

        // store total time for multiple trials
        double totalTime = 0;

        // run NUM_TESTS trials
        for(int n = 0; n < NUM_TESTS; n++) {


            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                long startTime = System.currentTimeMillis();

                tree.find(i);

                long endTime = System.currentTimeMillis();

                // only add for non-warmup
                if(i > NUM_WARMUP) {
                    totalTime+=(endTime - startTime);
                }
            }
        }
        double avgTime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(avgTime);
    }
}
