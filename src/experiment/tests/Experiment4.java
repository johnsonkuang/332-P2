package experiment.tests;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.ChainingHashTable;
import experiment.copies.ExpCircularArrayFIFOQueue;

import java.util.ArrayList;

// Runtime experiment for testing the effectiveness of two different types of hash functions
//          Hash Function 1: Return the hash code of the first element
//          Hash Function 2: The sum of obj.hashcode() * (i + 1) where i is the index of the element in the queue

//The experiment tests various data sizes running three trials for each size
public class Experiment4 {
    public static final int[] NUMBER_OF_QUEUES = {2000, 4000, 8000, 16000, 32000, 64000};
    public static final int NUM_WARMUP = 3;
    public static final int NUM_TESTS = 8;


    public static void main(String[] args) {
        StringBuilder output = new StringBuilder();
        for(int trial = 0; trial < NUM_TESTS; trial++){
            if(trial >= NUM_WARMUP){
                output.append("-----------------------------------------\n");
                output.append("Trial " + (trial - NUM_WARMUP + 1) + " Results: \n");
            }
            for(int i = 0; i < NUMBER_OF_QUEUES.length; i++){
                //new array list to store all circular queues
                ArrayList<ExpCircularArrayFIFOQueue> lst = new ArrayList<>();

                //instantiate all the cq's
                for(int j = 1; j <= NUMBER_OF_QUEUES[i]; j++){
                    ExpCircularArrayFIFOQueue<Integer> cq = new ExpCircularArrayFIFOQueue<>(5);
                    for(int elt = j; elt < j + 5; elt++){
                        cq.add(elt);
                    }
                    lst.add(cq);
                }

                ChainingHashTable<ExpCircularArrayFIFOQueue<Integer>, Integer> hashTable =
                        new ChainingHashTable<>(() -> new BinarySearchTree());

                //start timer
                long addStartTime = System.nanoTime();
                //map CQ to first element in the CQ for accurate find later
                for(int idx = 0; idx < lst.size(); idx++){
                    hashTable.insert(lst.get(idx), idx + 1);
                }
                //end timer
                long addEndTime = System.nanoTime();

                if(NUM_WARMUP <= trial){
                    output.append("\t" + NUMBER_OF_QUEUES[i] + " CQs: \n");
                    output.append("\t\tAdd Time: " + (addEndTime - addStartTime) / 1_000_000.0 + " ms\n");
                }

                long findStartTime = System.nanoTime();
                for(int idx = 0; idx < lst.size(); idx++){
                    assert hashTable.find(lst.get(idx)) == (idx + 1);
                }
                long findEndTime = System.nanoTime();
                //aggregate result output
                if(NUM_WARMUP <= trial){
                    output.append("\t\tFind Time: " + (findEndTime - findStartTime) / 1_000_000.0 + " ms\n");
                }
            }
        }

        System.out.println(output);
    }


}
