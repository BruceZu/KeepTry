package sort;

import java.util.Arrays;

/**
 * <pre>
 * Note:
 * You may assume all input has valid answer.
 * ====================================
 *
 * step 1.
 * Adjust the array to make the smaller number on the <strong>left </strong> side of median.
 * numbers in both sides of median need not be sorted.
 *
 * step 2.
 * wiggle sort to make  nums[0] < nums[1] > nums[2] < nums[3]....
 *
 * Follow up:
 *   1 Can you do it in O(n) time and/or in-place with O(1) extra space?
 *   2 Todo: how about  nums[0] > nums[1] < nums[2] > nums[3]....
 *
 * reference www.careercup.com and stackoverflow.com
 * and
 * @see
 * <a href="https://leetcode.com/problems/wiggle-sort-ii/">
 *     leetcode</a>
 * <br> <a href="https://en.wikipedia.org/wiki/Introselect">
 *     introspective selection</a>
 * <br> <a href="https://en.wikipedia.org/wiki/Selection_algorithm">
 *     selection algorithm</a>
 * <br><a href="https://en.wikipedia.org/wiki/Median_of_medians">
 *     median of medians</a>
 * <br><a href="https://en.wikipedia.org/wiki/Floyd%E2%80%93Rivest_algorithm">
 *     Floyd–Rivest algorithm</a>
 * <br><a href="https://en.wikipedia.org/wiki/Dutch_national_flag_problem#Pseudocode">
 *     Dutch national flag problem</a>
 */
public class Leetcode324WiggleSortII {
    public static void wiggleSort(int[] nums) {
        // 1
        Arrays.sort(nums); // o(NlgN)
        // 2
        wiggleSortPartitionedArray(nums);
    }

    /**
     * <pre>
     * o(n) time, o(1) extra space
     *
     * partitioned array:
     *       array is partitioned by median and the smaller ones without sorted at its left side
     *
     *  nextB:
     *       mark the index where  swap next bigger one to here
     *  i:
     *       used to calculate current index which go along index sequence of
     *       1, 3, 5 ... (current is smaller ones will be allocated to bigger ones)
     *      then  0, 2, 4 ... (current is bigger ones will be allocated to smaller ones)
     *
     *  nextS:
     *       mark the index where swap next smaller one to here
     *
     *  With this method array is wiggled sorted as
     *      in even index (start with 0) will be the smaller ones
     *      in odd index  (start with 1) will be the bigger ones
     */
    public static void wiggleSortPartitionedArray(int[] arr /* partitioned by Median */) {
        int midV = arr[arr.length - 1 >> 1]; // median or left median

        int nextB = 0;
        int i = 0;
        int nextS = arr.length - 1;

        while (i <= nextS) {
            if (arr[indexOf(i, arr)] > midV) {
                swap(arr, indexOf(i, arr), indexOf(nextB, arr));
                i++;
                nextB++;
            } else if (arr[indexOf(i, arr)] < midV) {
                swap(arr, indexOf(i, arr), indexOf(nextS, arr));
                nextS--;
            } else {
                i++;
            }
        }
    }

    // Get the true iterator order of index in loop of wiggleSortPartitionedArray()
    public static int indexOf(int index, int[] arr) {
        return (index * 2 + 1) % (arr.length | 1);
    }

    /*-------------------------------common method---------------------------------------------------*/
    public static void swap(int[] arr, int i, int j) {
        if (arr[i] != arr[j]) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
