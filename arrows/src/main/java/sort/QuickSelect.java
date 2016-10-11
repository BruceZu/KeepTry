package sort;


import static common_lib.Common.swap;
import static sort.InsertSort.insertSort;

public class QuickSelect {
    /**
     * <pre>  for 5 or less elements just get final index of median or <strong>left</strong> median
     * right - left < 5
     * implement this using insertion sort
     */
    private static int pivot5(int[] arr, int left, int right) {
        insertSort(arr, left, right);
        return left + ((right - left) >> 1);
    }

    /*------------------------------- like the nth_element() int c++. implementation base on intro sort  -------------*/
    // select a pivot based on median of medians strategy
    // return the index of median or left median which is as the pivot
    private static int pivotByMedianOfMedians(int[] arr, int left, int right) {
        if (right - left < 5) {
            return pivot5(arr, left, right);
        }

        // otherwise move the medians of five-element subgroups to the first n/5 positions
        int swapTo = left;
        for (int i = left; i <= right; i = i + 5) {
            // get the median of the i'th five-element subgroup
            int indexOfCurMedian = pivot5(arr, i, i + 4 > right ? right : i + 4);
            swap(arr, indexOfCurMedian, swapTo++);
        }
        // compute the median of the n/5 medians-of-five
        return introSelectKth(arr, left, swapTo - 1, left + ((swapTo - 1 - left) >> 1));
    }


    // find and return the pivot final index scope by move all elements in 3-ways.
    // those less or equal to its value are moved to its left.
    private static int[] partition(int[] arr, int left, int right, int pivotIndex) {
        int pv = arr[pivotIndex];
        // 3-ways partition
        int swapNextSmallTo = left;
        int swapNextBigTo = right;
        int i = left;
        while (i <= swapNextBigTo) {
            if (arr[i] < pv) {
                swap(arr, i++, swapNextSmallTo++);
            } else if (arr[i] == pv) {
                i++;
            } else {
                swap(arr, i, swapNextBigTo--);
            }
        }
        return new int[]{swapNextSmallTo, swapNextBigTo};
    }

    // Returns the index of the n-th smallest element of list within left..right inclusive
    // (left <= n <= right)
    // And make sure it is in its final sorted position with
    // all those <= it is in its left and in an unsorted order
    // all those >= it is in its right and in an unsorted order.
    //
    // worst case performance O(n), best case performance O(n).
    // Todo: o(1) extra space?? o(lgN)??
    public static int introSelectKth(int[] arr, int left, int right, int k) {
        while (left < right) {
            int pivotalIndex = pivotByMedianOfMedians(arr, left, right);
            int[] pivotalIndexScope = partition(arr, left, right, pivotalIndex);
            // now the pivot is in its final sorted position.
            if (pivotalIndexScope[0] <= k && k <= pivotalIndexScope[1]) {
                return k;
            }
            if (k < pivotalIndexScope[0]) {
                right = pivotalIndexScope[0] - 1;
            } else {
                left = pivotalIndexScope[1] + 1;
            }
        }
        // left == right
        return arr[left];
    }
}
