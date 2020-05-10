package experiment.tests;

import datastructures.dictionaries.ChainingHashTable;
import experiment.copies.*;
import p2.clients.NGramTester;

public class ChainingHashTableChains {

    public static final int[] testSizes = new int[]{2000, 4000, 8000, 16000, 32000};
    public static final int NUM_TESTS = 8;
    public static final int NUM_WARMUP = 3;

    public enum chainType {
        bst,
        avl,
        mvf
    }

    public static void main(String[] args) {

        // <String, Integer> for unsorted input
        ExpChainingHashTable<String, Integer>[] arrChainBSTStr =
                new ExpChainingHashTable[testSizes.length];
        ExpChainingHashTable<String, Integer>[] arrChainAVLStr =
                new ExpChainingHashTable[testSizes.length];
        ExpChainingHashTable<String, Integer>[] arrChainMVFStr =
                new ExpChainingHashTable[testSizes.length];

        // UNSORTED || INSERT TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("_________________UNSORTED__INSERT_________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on BST chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| UNSORTED INSERT || CHAIN BST || " + testSizes[i] + " || " +
                    "Runtime:");
            unsortedInsert(i, testSizes[i], chainType.bst, arrChainBSTStr);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on AVL chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| UNSORTED INSERT || CHAIN AVL || " + testSizes[i] + " || " +
                    "Runtime:");
            unsortedInsert(i, testSizes[i], chainType.avl, arrChainAVLStr);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test unsorted insert on MTF chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| UNSORTED INSERT || CHAIN MTF || " + testSizes[i] + " || " +
                    "Runtime:");
            unsortedInsert(i, testSizes[i], chainType.mvf, arrChainMVFStr);
        }

        // UNSORTED || FIND TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("__________________UNSORTED__FIND__________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on BST chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| UNSORTED FIND || CHAIN BST || " + testSizes[i] + " || Runtime:");
            unsortedFind(arrChainBSTStr[i], testSizes[i]);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on AVL chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.print("|| UNSORTED FIND || CHAIN AVL || " + testSizes[i] + " || Runtime:");
            unsortedFind(arrChainAVLStr[i], testSizes[i]);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on MTF chain
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| UNSORTED FIND || CHAIN MTF || " + testSizes[i] + " || Runtime:");
            unsortedFind(arrChainMVFStr[i], testSizes[i]);
        }

    }

    private static void unsortedInsert(int idx, int testSize,
                                       chainType chain,
                                       ExpChainingHashTable<String, Integer>[] trees) {

        // store total time for multiple trials
        // double totalTime = 0;

        // run NUM_TESTS trials
        for (int n = 0; n < NUM_TESTS; n++) {
            ExpChainingHashTable<String, Integer> tree = null;
            switch(chain){
                case bst:
                    tree = new ExpChainingHashTable<>(() -> new ExpBinarySearchTree());
                    break;
                case avl:
                    tree = new ExpChainingHashTable<>(() -> new ExpAVLTree());
                    break;
                case mvf:
                    tree = new ExpChainingHashTable<>(() -> new ExpMoveToFrontList());
                    break;
            }
            // reset trial time
            double trialTime = 0;

            // insert values in a pseudo random order!

            for (int i = 0; i < testSize; i++) {
                int k = (i % testSize) * 37 % testSize;
                String str = String.format("%05d", k);

                long startTime = System.nanoTime();

                tree.insert(str, i);

                long endTime = System.nanoTime();

                // only add for non-warmup
                if (n >= NUM_WARMUP) {

                    // totalTime += (endTime - startTime) / 1000.0;
                    trialTime += (endTime - startTime) / 1000000.0;
                }
            }
            if (n >= NUM_WARMUP) {
                System.out.println("     Trial " + (n - NUM_WARMUP + 1) + ": " + trialTime);
            }

            trees[idx] = tree;
        }
    }

    private static void unsortedFind(ExpChainingHashTable<String, Integer> tree, int testSize) {

        // store total time for multiple trials

        // run NUM_TESTS trials
        for(int n = 0; n < NUM_TESTS; n++) {

            // reset trial time
            double trialTime = 0;

            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                int k = (i % testSize) * 37 % testSize;
                String str = String.format("%06d", k);

                long startTime = System.nanoTime();

                tree.find(str);

                long endTime = System.nanoTime();

                // only add for non-warmup
                if (n >= NUM_WARMUP) {

                    // totalTime += (endTime - startTime) / 1000.0;
                    trialTime += (endTime - startTime) / 1000000.0;
                }
            }
            if (n >= NUM_WARMUP) {
                System.out.println("     Trial " + (n - NUM_WARMUP + 1) + ": " + trialTime);
            }
        }
    }
}
