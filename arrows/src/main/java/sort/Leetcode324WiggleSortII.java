package sort;

import java.util.Arrays;

/**
 * <pre>
 * Note:
 * You may assume all input has valid answer.
 * ====================================
 * concept: medium number. (when n is even, it is left medium in the smaller part)
 *
 * step 1.
 * Adjust the array to make the smaller number on the <strong>left </strong> side of median.
 * numbers in both sides of median not must be sorted.
 *
 * step 2
 * wiggle sort to make  nums[0] < nums[1] > nums[2] < nums[3]....
 *
 * Follow up:
 * how about  nums[0] > nums[1] < nums[2] > nums[3]....
 *
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
    /**
     * <pre>
     * ==even length array:
     * ==odd length array:
     *
     * distributed them to make
     * smaller in odd index start with median or left median and
     * bigger in even index to form wiggled sorted array
     *
     * o(n) time, o(1) extra space
     */

    public static void wiggleSortDividedArray(int[] divided /* By Median */) {
        int moveSmallTo = 0, i = 0, moveBigTo = divided.length - 1;
        int mid = divided[divided.length - 1 >> 1];
        while (i <= moveBigTo) {
            if (divided[(i * 2 + 1) % (divided.length | 1)] > mid) {
                swap(divided, (i * 2 + 1) % (divided.length | 1), (moveSmallTo * 2 + 1) % (divided.length | 1));
                i++;
                moveSmallTo++;
            } else if (divided[(i * 2 + 1) % (divided.length | 1)] < mid) {
                swap(divided, (i * 2 + 1) % (divided.length | 1), (moveBigTo * 2 + 1) % (divided.length | 1));
                moveBigTo--;
            } else {
                i++;
            }
        }
    }

    public static void wiggleSort(int[] nums) {
        /* median or left median in ascending orderArray */
        int medianIndex = nums.length - 1 >> 1;
        // 1
        introSelectKth(nums, 0, nums.length - 1, medianIndex);
        // 2
        wiggleSortDividedArray(nums);
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
    // o(1) extra space?? o(lgN)??
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
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for 5 or less elements just get final index of median or left median
    // right - left < 5
    // implement this using insertion sort
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
        /* https://www.careercup.com/question?id=9987819
        * https://discuss.leetcode.com/topic/32929/o-n-o-1-after-median-virtual-indexing
        * http://stackoverflow.com/questions/251781/how-to-find-the-kth-largest-element-in-an-unsorted-array-of-length-n-in-on
        */
        int[] nums;
        nums = new int[]{8, 7, 10, 9, 6, 5, 3, 2, 4};
        nums = new int[]{8, 7, 10, 6, 9, 5, 3, 2, 4, 1};
        nums = new int[]{1, 5, 1, 1, 6, 4};
        nums = new int[]{3, 6, 6, 7, 2, 9, 10, 8, 8, 10,
                3, 4, 7, 8, 9, 5, 6, 8, 8, 4, 7, 3, 7, 7, 5, 10, 4, 2, 8, 9, 5,
                1, 8, 4, 8, 10, 6, 5, 5, 9, 6, 5, 2, 1, 4, 3, 9, 1, 3, 7, 6, 4, 4, 9, 1, 5, 3, 5, 1,
                10, 1, 10, 10, 6, 5, 9, 10, 8, 1, 1, 10, 4, 1, 4, 4, 2, 7, 6, 2, 2, 1, 9,
                7, 9, 9, 5, 5, 10, 2, 9, 3, 3, 9, 6, 2, 4, 6, 10, 1, 2, 6, 1, 2, 2, 7, 7, 1,
                4, 7, 3, 4, 7, 1, 7, 7, 10, 9, 8, 3, 2, 5, 3, 6, 9, 9, 7, 4, 4, 4, 6, 7, 3, 9,
                6, 2, 1, 9, 3, 3, 2, 1, 4, 8, 3, 5, 3, 4, 5, 2, 6, 6, 3, 9, 8, 8, 7, 4, 7, 5,
                3, 6, 5, 1, 5, 10, 1, 1, 9, 10, 10, 9, 1, 2, 9, 5, 10, 4, 2, 5, 2, 4, 10, 6,
                1, 4, 5, 1, 1, 2, 4, 2, 1, 6, 1, 10, 8, 9, 6, 8, 7, 6, 8, 7, 4, 6, 10, 2, 8,
                5, 4, 4, 1, 2, 8, 9, 8, 4, 10, 8, 1, 3, 5, 1, 6, 7, 9, 8, 6, 4, 2, 3, 8, 4,
                4, 8, 10, 7, 10, 8, 10, 8, 10, 7, 3, 6, 9, 9, 9, 10, 8, 3, 8, 1, 3, 5, 5, 8,
                5, 5, 7, 6, 3, 1, 9, 9, 6, 3, 7, 1, 7, 4, 7, 2, 5, 10, 7, 8, 9, 8, 3, 5, 1,
                2, 9, 8, 10, 5, 1, 3, 3, 2, 3, 2, 7, 3, 6, 7, 8, 5, 10, 6, 4, 8, 1, 4, 8, 8,
                7, 10, 1, 5, 6, 10, 2, 7, 2, 5, 5, 9, 7, 1, 4, 6, 6, 6, 8, 7, 9, 7, 3, 1, 5, 7,
                7, 5, 9, 7, 2, 3, 5, 5, 1, 2, 2, 2, 7, 2, 9, 10, 10, 3, 5, 5, 2, 3, 1, 2, 1, 10,
                5, 4, 10, 7, 6, 7, 8, 10, 5, 1, 4, 9, 4, 7, 6, 7, 10, 5, 7, 6, 9, 5, 2, 10, 1,
                9, 6, 6, 4, 10, 4, 8, 10, 6, 5, 6, 8, 10, 8, 4, 2, 8, 9, 6, 8, 8, 10, 4, 5, 3, 8,
                1, 4, 3, 9, 2, 9, 2, 9, 9, 5, 2, 6, 10, 1, 4, 10, 6, 9, 10, 5, 10, 5, 8, 9, 10, 1,
                7, 7, 8, 8, 1, 6, 1, 8, 4, 4, 2, 1, 1, 6, 5, 9, 3, 3, 8, 5, 1, 4, 3, 8, 9, 3, 10,
                4, 6, 7, 6, 3, 2, 5, 3, 7, 6, 8, 2, 6, 10, 9, 9, 5, 3, 1, 4, 7, 7, 9, 8, 10, 7, 5,
                7, 4, 1, 8, 7, 7, 6, 7, 7, 8, 5, 4, 2, 4, 3, 2, 6, 3, 6, 6, 1, 4, 6, 8, 7, 9, 6, 2,
                1, 7, 9, 5, 1, 3, 5, 3, 8, 9, 2, 6, 5, 8, 1, 10, 4, 10, 3, 2, 4, 9, 8, 7, 1, 2, 6,
                10, 1, 3, 3, 7, 9, 8, 2, 5, 1, 7, 1, 1, 6, 5, 9, 3, 5, 1, 6, 9, 8, 6, 10, 1, 2, 4,
                10, 4, 3, 6, 9, 6, 4, 5, 8, 10, 7, 4, 3, 3, 7, 2, 1, 4, 2, 5, 6, 6, 9, 1, 2, 10, 9,
                6, 5, 10, 4, 4, 9, 1, 9, 3, 2, 3, 6, 6, 4, 2, 7, 7, 4, 8, 7, 3, 2, 2, 7, 2, 2, 8, 10,
                10, 8, 5, 8, 6, 7, 3, 8, 4, 1, 4, 8, 5, 5, 2, 4, 3, 3, 5, 7, 2, 3, 5, 9, 8, 2, 7, 1,
                2, 7, 7, 6, 2, 6, 1, 5, 7, 2, 8, 6, 7, 4, 9, 9, 2, 6, 3, 8, 8, 1, 5, 4, 8, 2, 5, 10,
                7, 3, 9, 5, 8, 10, 3, 10, 5, 6, 10, 9, 4, 1, 8, 7, 3, 8, 6, 8, 9, 8, 8, 6, 9, 8, 2,
                10, 1, 6, 2, 1, 2, 6, 5, 10, 9, 2, 5, 5, 10, 10, 10, 7, 10, 5, 1, 1, 6, 10, 7, 7, 7,
                4, 8, 6, 5, 10, 9, 8, 10, 9, 5, 8, 5, 5, 2, 6, 7, 1, 8, 8, 7, 5, 10, 9, 3, 8, 5, 6,
                7, 7, 4, 10, 7, 7, 4, 10, 2, 6, 6, 2, 5, 7, 2, 4, 2, 6, 10, 4, 2, 2, 3, 2, 2, 2, 8,
                8, 3, 1, 7, 6, 3, 6, 6, 3, 1, 10, 1, 8, 2, 9, 2, 7, 10, 5, 4, 10, 6, 10, 2, 2, 3, 6,
                5, 3, 10, 8, 9, 6, 7, 3, 7, 1, 10, 5, 1, 4, 5, 5, 1, 4, 4, 3, 6, 10, 10, 10, 10, 4,
                3, 9, 2, 9, 1, 8, 9, 4, 4, 1, 8, 5, 4, 2, 6, 7, 2, 8, 10, 10, 6, 7, 7, 9, 3, 4, 10,
                1, 2, 1, 8, 9, 1, 1, 3, 5, 10, 8, 2, 9, 2, 6, 7, 4, 5, 3, 6, 9, 3, 9, 5, 1, 9, 9,
                2, 7, 4, 2, 6, 5, 5, 3, 6, 4, 3, 5, 5, 8, 9, 6, 9, 10, 6, 4, 8, 6, 6, 1, 10, 9, 4,
                7, 5, 8, 4, 1, 7, 10, 10, 1, 2, 9, 9, 2, 7, 9, 1, 1, 3, 3, 1, 1, 9, 9, 10, 5, 1, 7,
                1, 6, 2, 9, 10, 1, 6, 6, 9, 7, 10, 7, 3, 8, 4, 4, 4, 7, 7, 2, 9, 7, 1, 4, 6, 5, 3, 6,
                1, 7, 10, 6, 8, 9, 3, 9, 5, 2, 6, 6, 6, 10, 5, 9, 5, 7, 2, 1, 10, 6, 8, 7, 1, 2, 9, 2,
                6, 2, 6, 8, 8, 2, 9, 4, 8, 3, 8, 7, 3, 6, 1, 1, 9, 2, 1, 4, 8, 9, 4, 7, 3, 8, 5, 8, 3,
                10, 9, 1, 4, 10, 2, 3, 9, 8, 10, 10, 4, 10, 4, 2, 3, 1, 2, 4, 2, 9, 5, 5, 8, 6, 10, 8,
                5, 2, 6, 2, 5, 7, 7, 4, 1, 4, 2, 7, 10, 8, 3, 1, 7, 9, 6, 6, 2, 5, 8, 9, 10, 3, 4, 2,
                8, 4, 8, 3, 1, 6, 6, 5, 10, 5, 5, 2, 9, 1, 2, 6, 4, 9, 2, 10, 8, 5, 9, 5, 6, 2, 5, 3,
                2, 6, 3, 6, 2, 8, 3, 6, 5, 7, 9, 1, 10, 3, 7, 1, 1, 10, 7, 4, 8, 1, 6, 8, 7, 5, 6, 3,
                5, 1, 1, 1, 6, 2, 7, 5, 8, 2, 2, 9, 8, 10, 10, 6, 7, 1, 1, 4, 8, 5, 5, 10, 6, 6, 8, 5,
                8, 3, 2, 10, 7, 10, 3, 5, 2, 5, 7, 10, 9, 6, 9, 6, 9, 10, 5, 1, 9, 5, 1, 6, 8, 9, 7, 10,
                8, 10, 4, 10, 9, 4, 2, 9, 3, 3, 10, 7, 3, 3, 3, 8, 6, 10, 3, 8, 2, 1, 9, 2, 7, 2, 7, 4,
                5, 1, 2, 8, 6, 3, 1, 8, 7, 5, 2, 10, 6, 7, 10, 8, 6, 6, 9, 7, 1, 2, 9, 2, 8, 6, 5, 2, 6,
                10, 7, 3, 10, 8, 7, 8, 5, 8, 1, 5, 9, 8, 7, 10, 2, 8, 6, 10, 4, 2, 2, 7, 8, 7, 1, 3, 9, 10,
                4, 6, 10, 9, 7, 5, 10, 10, 1, 4, 10, 1, 6, 3, 8, 6, 1, 6, 9, 9, 9, 6, 9, 3, 10, 7, 1, 4, 8,
                4, 9, 3, 1, 4, 6, 10, 10, 7, 1, 3, 5, 6, 7, 1, 5, 3, 8, 6, 1, 1, 6, 2, 1, 5, 1, 7, 6, 10, 5,
                5, 6, 10, 6, 6, 9, 8, 8, 1, 4, 5, 6, 3, 4, 7, 8, 6, 8, 1, 9, 4, 5, 5, 3, 3, 5, 3, 1, 2, 10,
                6, 7, 2, 9, 8, 8, 6, 9, 7, 8, 2, 2, 3, 5, 4, 9, 9, 8, 10, 8, 4, 5, 7, 2, 5, 4, 1, 9, 7, 2,
                2, 9, 4, 3, 3, 3, 8, 5, 1, 8, 7, 1, 8, 5, 7, 7, 4, 5, 6, 5, 9, 5, 5, 4, 8, 8, 1, 10, 2, 4,
                4, 2, 10, 7, 1, 8, 4, 10, 7, 4, 2, 3, 4, 10, 9, 3, 1, 9, 3, 1, 2, 7, 9, 3, 4, 2, 3, 1, 2, 8,
                2, 6, 5, 4, 10, 7, 3, 2, 10, 8, 6, 2, 2, 3, 6, 5, 3, 1, 4, 7, 4, 8, 3, 1, 9, 2, 6, 2, 5, 8,
                3, 4, 5, 8, 1, 4, 6, 6, 5, 9, 3, 1, 6, 1, 10, 9, 9, 2, 10, 6, 10, 6, 1, 6, 2, 4, 6, 6, 4, 6,
                3, 5, 10, 2, 9, 6, 5, 8, 7, 9, 5, 8, 9, 3, 4, 2, 2, 5, 2, 4, 1, 10, 10, 2, 4, 2, 1, 10, 6, 7,
                7, 3, 10, 10, 2, 5, 1, 6, 8, 6, 10, 9, 4, 7, 5, 4, 4, 10, 8, 6, 5, 3, 6, 3, 5, 7, 8, 2, 10, 3,
                1, 1, 2, 10, 6, 4, 4, 8, 6, 6, 2, 8, 2, 5, 4, 5, 3, 8, 3, 4, 6, 8, 7, 5, 7, 5, 5, 4, 8, 1, 7, 2,
                7, 3, 2, 1, 10, 2, 10, 9, 10, 10, 10, 8, 5, 10, 3, 10, 8, 7, 4, 1, 5, 6, 9, 7, 5, 6, 3, 6, 3, 5,
                2, 10, 4, 1, 2, 7, 9, 4, 10, 4, 7, 8, 1, 2, 6, 7, 10, 10, 8, 5, 8, 4, 1, 4, 8, 6, 6, 2, 5, 9, 8,
                1, 6, 3, 5, 6, 6, 9, 9, 2, 2, 10, 1, 10, 9, 4, 8, 9, 3, 6, 1, 1, 10, 2, 4, 7, 5, 6, 3, 7, 6, 6,
                9, 8, 3, 5, 1, 6, 9, 5, 3, 1, 10, 5, 10, 8, 3, 2, 7, 1, 4, 4, 1, 9, 4, 7, 9, 4, 2, 5, 10, 5, 8,
                10, 6, 9, 1, 3, 10, 3, 8, 3, 1, 7, 9, 5, 7, 2, 1, 5, 4, 6, 7, 7, 4, 4, 3, 1, 9, 6, 6, 1, 9, 6, 3,
                5, 2, 4, 4, 5, 3, 8, 9, 2, 2, 2, 3, 7, 8, 1, 8, 1, 10, 8, 6, 10, 9, 2, 10, 2, 9, 2, 5, 5, 6, 9, 4,
                7, 8, 5, 10, 7, 3, 7, 7, 6, 1, 1, 6, 3, 1, 7, 10, 5, 4, 2, 9, 7, 9, 10, 2, 4, 10, 7, 5, 8, 7, 7, 3,
                3, 1, 3, 8, 10, 9, 1, 8, 4, 7, 4, 6, 9, 1, 9, 5, 2, 10, 10, 3, 8, 4, 8, 4, 9, 6, 4, 3, 10, 7, 8, 10,
                2, 8, 1, 1, 7, 9, 5, 8, 3, 5, 10, 8, 1, 4, 1, 8, 6, 6, 5, 4, 3, 2, 8, 5, 10, 5, 8, 3, 3, 9, 7, 2, 5,
                5, 3, 7, 6, 1, 1, 10, 9, 3, 7, 10, 3, 5, 8, 1, 9, 1, 1, 3, 1, 9, 6, 8, 6, 2, 10, 6, 8, 7, 10, 8, 8,
                3, 7, 2, 1, 7, 3, 5, 3, 10, 10, 10, 1, 2, 2, 9, 2, 8, 7, 7, 1, 5, 2, 1, 5, 6, 5, 7, 6, 10, 1, 4, 9,
                6, 8, 8, 6, 2, 7, 5, 1, 6, 9, 6, 8, 9, 8, 2, 4, 10, 8, 5, 3, 1, 8, 1, 9, 10, 8, 4, 5, 1, 2, 2, 9, 1,
                7, 2, 6, 10, 1, 6, 2, 2, 9, 2, 2, 2, 3, 4, 7, 6, 7, 7, 7};

        nums = new int[]{1, 5, 1, 1, 6, 4};
        nums = new int[]{4, 5, 5, 6};
        nums = new int[]{1, 1, 2, 1, 2, 2, 1};
        nums = new int[]{2, 4, 5, 1, 2, 4, 1, 1, 3, 3, 1, 2, 4, 3};

        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
