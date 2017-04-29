//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package sort;

import java.util.Arrays;

import static common_lib.Common.swap;

/**
 * <pre>
 * Note:
 * You may assume all input has valid answer.
 * ====================================
 *
 * step 1.
 * find the median (or left median) value
 *
 * step 2.
 * wiggle sort with the median value to make nums[0] < nums[1] > nums[2] < nums[3]....
 * loop along the order:  odd index, even index to do  separation in 3 ways
 * biggers, medieans, smallers, thus biggers will be at odd index and smallers will be at even index
 * and medians will be separated too by wiggle index.
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
        wiggleSortPartitionedArrayIn3ways(nums, nums[nums.length - 1 >> 1]);// median or left median
    }

    /**
     * <pre>
     * o(n) time, o(1) extra space
     *
     * partitioned array:
     *       array where the smallers and biggers are separated by median without sorted on both sides
     * midV:  median or left median value
     *  With this method array is wiggled sorted as 3 way ->
     *  in order of biggers( -> odd index), mideans, smallers ( -> even index)
     */
    public static void wiggleSortPartitionedArrayIn3ways(int[] A /* partitioned by Median */, int midV) {
        int b = 0; // next Biger Sequence. Ascending
        int i = 0; // sequence of current . Ascending
        int s = A.length - 1;// next Smaller Sequence. Descending

        while (i <= s) {
            if (A[index(i, A)] > midV) {
                swap(A, index(i, A), index(b, A));// wiggle order
                i++;
                b++;
            } else if (A[index(i, A)] < midV) {
                swap(A, index(i, A), index(s, A));
                s--;
            } else {
                i++;
            }
        }
    }

    // Get the true iterator order of index, the wiggle order index, in loop of wiggleSortPartitionedArray()
    // wiggle order: Jump over or skip one index each time
    public static int index(int i, int[] arr) {
        // 1, 3 ,5 ,....0,2,4,...
        // odd          even
        // bigger       smaller
        return (i * 2 + 1) % (arr.length | 1);
    }

    public static void main(String[] args) {
        // 2, 3, 6, 7; 9, 9, 9 ; 11, 12, 13, 15
        int[] num = new int[]{9, 12, 15, 6, 9, 2, 7, 3, 11, 13, 9};
        wiggleSortPartitionedArrayIn3ways(num, 9);
        System.out.println(Arrays.toString(num)); //[9, 13, 9, 15, 7, 11, 3, 12, 6, 9, 2]
        wiggleSortPartitionedArrayIn3ways(new int[]{1, 2, 3, 3, 4}, 3);
    }
}
