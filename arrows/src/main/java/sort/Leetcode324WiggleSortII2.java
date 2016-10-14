package sort;

import static sort.Leetcode324WiggleSortII.wiggleSortPartitionedArray;
import static sort.QuickSelect.introSelectKth;

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
    // follow up: o(n) runtime and o(1) extra space
    public static void wiggleSort(int[] nums) {
        /**
         * <pre>
         *  For even length array. use left median with smaller ones at its left.
         *  This is to keep consistent with wiggleSortPartitionedArray().
         *  both of them can use right median together.
         */
        int leftMedianIndex = nums.length - 1 >> 1;
        // 1
        introSelectKth(nums, 0, nums.length - 1, leftMedianIndex);
        // 2
        wiggleSortPartitionedArray(nums);
    }

    /** test cases:
     8, 7, 10, 9, 6, 5, 3, 2, 4
     8, 7, 10, 6, 9, 5, 3, 2, 4, 1
     1, 5, 1, 1, 6, 4
     4, 5, 5, 6
     2, 4, 5, 1, 2, 4, 1, 1, 3, 3, 1, 2, 4, 3
     0, 1, 2, 3, 4, 5
     1, 1, 2, 1, 2, 2, 1
     4, 5, 5, 6
     4, 5, 5, 5, 5, 6, 6, 6
     */
}
