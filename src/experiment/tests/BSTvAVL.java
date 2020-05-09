package experiment.tests;

import experiment.copies.ExpAVLTree;
import experiment.copies.ExpBinarySearchTree;

public class BSTvAVL {

    public static final int[] testSizes = new int[]{100, 1000, 2000, 4000, 8000};
    public static final int NUM_TESTS = 8;
    public static final int NUM_WARMUP = 5;

    public static void main(String[] args) {
        // first, make one of each!
        ExpBinarySearchTree<Integer, Integer> BST = new ExpBinarySearchTree<>();
        ExpAVLTree<Integer, Integer> AVL = new ExpAVLTree<>();

        // INSERT TESTS \\
        System.out.println("______________________INSERT______________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test insert on BST
        for(int size : testSizes) {
            System.out.print("|| INSERT || BST || " + size + " || Runtime = ");
            insert(BST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test insert on AVL
        for(int size : testSizes) {
            System.out.print("|| INSERT || AVL || " + size + " || Runtime = ");
            insert(AVL, size);
        }

        // FIND TESTS \\

        System.out.println();
        System.out.println();
        System.out.println("_______________________FIND_______________________");
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on BST
        for(int size : testSizes) {
            System.out.print("|| INSERT || BST || " + size + " || Runtime = ");
            find(BST, size);
        }

        System.out.println();
        System.out.println("__________________________________________________");
        System.out.println();

        // Test find on AVL
        for(int size : testSizes) {
            System.out.print("|| INSERT || AVL || " + size + " || Runtime = ");
            find(AVL, size);
        }
    }

    private static void insert(ExpBinarySearchTree<Integer, Integer> tree, int testSize) {

        // store total time for multiple trials
        double totalTime = 0;

        // run NUM_TESTS trials
        for(int n = 0; n < NUM_TESTS; n++) {


            // insert values up to the current test Size
            for (int i = 0; i < testSize; i++) {

                long startTime = System.currentTimeMillis();

                tree.insert(i, i);

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

    private static void find(ExpBinarySearchTree<Integer, Integer> tree, int testSize) {

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
