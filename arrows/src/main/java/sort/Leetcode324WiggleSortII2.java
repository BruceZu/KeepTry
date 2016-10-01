package sort;

import java.util.Arrays;

import static sort.Leetcode324WiggleSortII.swap;
import static sort.Leetcode324WiggleSortII.wiggleSortPartitionedArray;

/**
 * <pre>
 * Note:
 *  wiggleSortPartitionedArray() and introSelectKth() should use same median especially for even length array,
 *  else e.g.: wiggleSortPartitionedArray() use right median and introSelectKth() use left median:
 *  Input:     [1,5,1,1,6,4]
 *  Output:    [1,6,4,5,1,1]
 *  Expected:  [1,6,1,5,1,4]
 */
public class Leetcode324WiggleSortII2 {
    /*-------------------------------follow up: o(n) runtime and o(1) extra space -------------------------------*/
    public static void wiggleSort(int[] nums) {
        /**
         * <pre>
         *  For even length array. use left median with smaller ones at its left, this is to keep consistent with
         *  wiggleSortPartitionedArray(). both of them can use right median together.
         */
        int medianIndex = nums.length - 1 >> 1;
        // 1
        introSelectKth(nums, 0, nums.length - 1, medianIndex);
        // 2
        wiggleSortPartitionedArray(nums);
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

        swap(arr, pivotIndex, right); // Move pivot to end

        int allocateTo = left;
        for (int i = left; i <= right - 1; i++) {
            if (arr[i] < pv) { // less than pivot value
                swap(arr, i, allocateTo++);
            }
        }

        int leftPivotIndex = -1;
        for (int i = left; i <= right - 1; i++) {
            if (arr[i] == pv) { // equal to pivot value
                if (leftPivotIndex == -1) {
                    leftPivotIndex = allocateTo;
                }
                swap(arr, i, allocateTo++);
            }
        }
        swap(arr, right, allocateTo); // Move pivot to its final place
        return new int[]{leftPivotIndex == -1 ? allocateTo : leftPivotIndex, allocateTo};
    }

    // Returns the index of the n-th smallest element of list within left..right inclusive
    // (left <= n <= right)
    // And make sure it is in its final sorted position with
    // all those <= it is in its left and in an unsorted order
    // all those >= it is in its right and in an unsorted order.
    //
    // worst case performance O(n), best case performance O(n).
    // Todo: o(1) extra space?? o(lgN)??
    private static int introSelectKth(int[] arr, int left, int right, int k) {
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

    /*-------------------------------common method---------------------------------------------------*/

    /**
     * <pre>  for 5 or less elements just get final index of median or <strong>left</strong> median
     * right - left < 5
     * implement this using insertion sort
     */
    private static int pivot5(int[] arr, int left, int right) {
        insertSort(arr, left, right);
        return left + ((right - left) >> 1);
    }

    public static void insertSort(int[] arr, int left, int right) {
        // Input check
        if (arr == null || arr.length <= 1 || right == left) {
            return;
        }
        int iv;
        int curIndex;
        for (int i = left + 1; i <= right; i++) {
            iv = arr[i];
            curIndex = i;
            while (left < curIndex && iv < arr[curIndex - 1]) {
                swap(arr, curIndex, curIndex - 1);
                curIndex--;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums;
        nums = new int[]{8, 7, 10, 9, 6, 5, 3, 2, 4};
        nums = new int[]{8, 7, 10, 6, 9, 5, 3, 2, 4, 1};
        nums = new int[]{1, 5, 1, 1, 6, 4};
        nums = new int[]{1, 5, 1, 1, 6, 4};
        nums = new int[]{4, 5, 5, 6};
        nums = new int[]{2, 4, 5, 1, 2, 4, 1, 1, 3, 3, 1, 2, 4, 3};
        nums = new int[]{0, 1, 2, 3, 4, 5};
        nums = new int[]{1, 1, 2, 1, 2, 2, 1};
        nums = new int[]{4, 5, 5, 6};
        nums = new int[]{4, 5, 5, 5, 5, 6, 6, 6};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
