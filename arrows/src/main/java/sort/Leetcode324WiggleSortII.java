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
 * Adjust the array to make the smaller number on the <strong>right </strong> side of median.
 * numbers in both sides of median not must be sorted.
 * resolution:
 * require in O(N) time and O(1) extra space
 *
 * step 2
 * based on a given array whose medium number is <strong>assumed to be just at its place and
 * numbers in both sides of medium need not is sorted.</strong>
 *
 * wiggle sort to make  nums[0] < nums[1] > nums[2] < nums[3]....
 * resolution:
 * require in O(N) time and O(1) extra space
 *
 * explain:
 * ==even length array:
 * before wiggle sort index: 0  1   2  3  4  5  6  7  8  9
 * number                    8, 7, 10, 6, 9, 5, 3, 2, 4, 1
 * |
 * wiggle sorted:            5  8   3  7  2 10  4  6  1  9
 * index after sorted        0  1   2  3  4  5  6  7  8  9
 *
 * watch:
 * after distributed, smaller in odd index and bigger in even index of wiggled sorted array
 * index  ---> index after wiggle sorted.
 *        odd
 * 0      1
 * 1      3
 * 2      5
 * 3      7
 * 4      9
 *        even
 * 5      0 (left median)
 * 6      2
 * 7      4
 * 8      6
 * 9      8
 *
 * index i --> index after wiggle sorted: (2*i +1 ) % (n+1) . n is array length
 *
 * The key conclusion: there is adjusting circle without lost any number
 *
 * 0->1->3->7->4->9->8->6->2->5->0
 *
 * ==odd length array:
 * before wiggle sort index: 0  1   2  3  4  5  6  7  8
 * number                    8, 7, 10, 9, 6, 5, 3, 2, 4
 * |
 * wiggle sorted:               8      7    10     9  6
 *                           5      3     2     4
 * index after sorted        0  1   2  3  4  5  6  7  8
 *
 * watch:
 * after distributed, smaller in odd index and bigger in even index of wiggled sorted array
 * index  ---> index after wiggle sorted.
 *        odd
 * 0      1
 * 1      3
 * 2      5
 * 3      7
 *
 * 4      8 (<strong>assume the median is left to the last place</strong>)
 *
 *        even
 * 5      0
 * 6      2
 * 7      4
 * 8      6
 *
 * index i --> index after wiggle sorted:
 * (2*i +  (i< n/2 ? 1:0) ) % (n+1). n is array length
 *
 * The key conclusion: based on the index relation there is adjusting circle without lost any number
 *
 * 0->1->3->7->4->8->6->2->5->0
 *
 * So at last:
 * no matter the array length is even or odd the sorted index =
 * (2 * index + ((n & 1) == 0 ? 1 : (index < n / 2 ? 1 : 0))) % (n + 1);
 *
 * Follow up:
 * how about  nums[0] > nums[1] < nums[2] ></> nums[3]....
 *
 *
 * @see
 * <a href="https://leetcode.com/problems/wiggle-sort-ii/">
 *     leetcode</a>
 *
 * <br> <a href="https://en.wikipedia.org/wiki/Introselect">
 *     introspective selection</a>
 * <br> <a href="https://en.wikipedia.org/wiki/Selection_algorithm">
 *     selection algorithm</a>
 * <br><a href="https://en.wikipedia.org/wiki/Median_of_medians">
 *     median of medians</a>
 *
 * <br><a href="https://en.wikipedia.org/wiki/Dutch_national_flag_problem#Pseudocode">
 *     Dutch national flag problem</a>
 */
public class Leetcode324WiggleSortII {
    public static void wiggleSort(int[] nums) {
        /* median or left median in ascending orderArray */
        int medianIndex = nums.length - 1 >> 1;
        introSelectKth(nums, 0, nums.length - 1, medianIndex);
        reverse(nums);
        wiggleSortDividedArray(nums);
    }

    // o(n) time, o(1) extra space
    private static void wiggleSortDividedArray(int[] divided /* By Median */) {
        int n = divided.length;
        int index = 0, v = divided[index];
        int sortedIndex, tmp;
        do {
            sortedIndex = (2 * index + ((n & 1) == 0 ? 1 : (index < n / 2 ? 1 : 0))) % (n + 1);

            tmp = divided[sortedIndex];
            divided[sortedIndex] = v;

            index = sortedIndex;
            v = tmp;
        } while (sortedIndex != 0);
    }


    // as a result of the arr,
    // the kth element is in its final position with
    // all those preceding it in an unsorted order and in its left
    // all those following it in an unsorted order and in its right
    // average o(n) runtime and o(1) extra space
    // Worst case performance  O(n), Best case performance O(n), o(1) extra space
    private static void introSelectKth(int[] arr, int left, int right, int k) {
        // TODO: 9/28/16  
    }

    // required by wiggleSortDividedArray
    private static void reverse(int[] arr) {
       //// TODO: 9/28/16  
    }

    public static void main(String[] args) {
        /* https://www.careercup.com/question?id=9987819
        * https://discuss.leetcode.com/topic/32929/o-n-o-1-after-median-virtual-indexing
        * http://stackoverflow.com/questions/251781/how-to-find-the-kth-largest-element-in-an-unsorted-array-of-length-n-in-on
        */
        int[] odd = new int[]{8, 7, 10, 9, 6, 5, 3, 2, 4};
        int[] even = new int[]{8, 7, 10, 6, 9, 5, 3, 2, 4, 1};
        wiggleSort(odd);
        wiggleSort(even);
        System.out.println(Arrays.toString(odd));
        System.out.println(Arrays.toString(even));
    }
}
