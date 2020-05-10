package experiment.tests;

import experiment.copies.ExpAVLTree;
import experiment.copies.ExpBinarySearchTree;

public class BSTvAVL {

    public static final int[] testSizes = new int[]{100, 1000, 2000, 4000, 8000};
    public static final int NUM_TESTS = 8;
    public static final int NUM_WARMUP = 3;

    public static void main(String[] args) {
        // first, make arrays of each
        ExpBinarySearchTree<Integer, Integer>[] arrBST = new ExpBinarySearchTree[testSizes.length];
        ExpAVLTree<Integer, Integer>[] arrAVL = new ExpAVLTree[testSizes.length];

        // INSERT TESTS \\
        System.out.println("______________________INSERT______________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test insert/find for each size on BST
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| INSERT || BST || " + testSizes[i] + " || Runtime:");
            arrBST[i] = new ExpBinarySearchTree<>();
            insert(arrBST[i], testSizes[i]);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test insert on AVL
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| INSERT || AVL || " + testSizes[i] + " || Runtime:");
            arrAVL[i] = new ExpAVLTree<>();
            insert(arrAVL[i], testSizes[i]);
        }

        // FIND TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("_______________________FIND_______________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on BST
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| INSERT || BST || " + testSizes[i] + " || Runtime:");
            find(arrBST[i], testSizes[i]);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on AVL
        for(int i = 0; i < testSizes.length; i++) {
            System.out.println("|| INSERT || AVL || " + testSizes[i] + " || Runtime:");
            find(arrAVL[i], testSizes[i]);
        }
    }

    private static void insert(ExpBinarySearchTree<Integer, Integer> tree, int testSize) {

        // store total time for multiple trials
        // double totalTime = 0;
        double trialTime = 0;

        // run NUM_TESTS trials
        for(int n = 0; n < NUM_TESTS; n++) {

            // reset trial time
            trialTime = 0;

            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                long startTime = System.nanoTime();

                tree.insert(i, i);

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
        // double avgTime = totalTime / (NUM_TESTS - NUM_WARMUP);
        // System.out.println(avgTime);
    }

    private static void find(ExpBinarySearchTree<Integer, Integer> tree, int testSize) {

        // store total time for multiple trials
        // double totalTime = 0;
        double trialTime = 0;

        // run NUM_TESTS trials
        for(int n = 0; n < NUM_TESTS; n++) {

            // reset trialTime
            trialTime = 0;

            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                long startTime = System.nanoTime();

                tree.find(i);

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
        // double avgTime = totalTime / (NUM_TESTS - NUM_WARMUP);
        // System.out.println(avgTime);
    }
}
