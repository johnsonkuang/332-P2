package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        int i = 0;

        while(i != array.length){
            if(heap.size() < k){
                heap.add(array[i]);
            } else {
                if(comparator.compare(array[i], heap.peek()) > 0){
                    heap.next();
                    heap.add(array[i]);
                }
            }

            i++;
        }

        i = 0;
        while(i != array.length){
            if(i < k){
                array[i] = heap.next();
            } else {
                array[i] = null;
            }
            i++;
        }
    }
}
