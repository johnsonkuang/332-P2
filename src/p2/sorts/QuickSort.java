package p2.sorts;

import java.util.Comparator;

import cse332.exceptions.NotYetImplementedException;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, comparator, 0, array.length - 1);
    }

    private static <E> void quickSort(E[] array, Comparator<E> cmp, int lo, int upper) {
        if(upper > lo) {
            // get pivot
            int pivotIndex = medianOfThree(array, cmp, lo, upper);
            E pivotVal = array[pivotIndex];

            // swap pivot to last place
            E temp = array[array.length - 1];
            array[array.length - 1] = pivotVal;
            array[pivotIndex] = temp;

            // decrement hi
            int hi = upper - 1;

            while (lo < hi) {
                // find swap values
                while (cmp.compare(array[lo], pivotVal) < 0 && lo < hi) {
                    lo++;
                }
                while (cmp.compare(array[hi], pivotVal) > 0 && lo < hi) {
                    hi--;
                }
                if (lo < hi) {
                    // conduct swap
                    E tempHi = array[hi];
                    array[hi] = array[lo];
                    array[lo] = tempHi;
                }
            }

            // swap pivot back to new lo
            array[array.length - 1] = array[lo];
            array[lo] = pivotVal;

            quickSort(array, cmp, 0, pivotIndex - 1);
            quickSort(array, cmp, pivotIndex + 1, upper);
        }
    }

    private static <E> int medianOfThree(E[] array, Comparator<E> cmp, int lo, int hi) {
        E med1 = array[lo];
        E med2 = array[hi];
        int mid = (hi+lo)/2;
        E med3 = array[mid];
        int cmp31 = Integer.signum(cmp.compare(med3, med1));
        int cmp32 = Integer.signum(cmp.compare(med3, med2));
        if(Math.abs(cmp31-cmp32) == 2) {    // if 3 is between 1 and 2, return 3
            return mid;
        } else {
            if(cmp31 == 1) {                // if 3 is greater than both
                if(cmp.compare(med2, med1) >= 0) {  // if 2 is also greater, return 1
                    return hi;
                } else {
                    return lo;                      // 2 is less, return 1
                }
            } else {                        // 3 is less than both
                if(cmp.compare(med2, med1) >= 0) {  // if 2 is greater, return 1
                    return lo;
                } else {
                    return hi;                      // 2 is less, return 2
                }
            }
        }
    }
}
