//  Copyright 2017 The keepTry Open Source Project
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

package array_presum;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

/**
 * <pre>
 *
 * <a href="https://leetcode.com/problems/count-of-range-sum/#/description'>LeetCode</a>
 */
public class Leetcode327CountofRangeSum {
    /**
     * prepare pre-sum array firstly.
     * <pre>
     * A>  applying the sequential recurrence relation
     * the subproblem C: find the ones out of
     * visited in pre-sum array that are within the given range.
     * It involves searching on "dynamic searching space"; Using BST, BIT...
     *
     * applying the partition recurrence relation,
     * subproblem C:
     * for each element in the left half, find the ones in the right half that match the given range,
     * which can be embedded into the merging process using the two-pointer technique.
     */
    static public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) return 0;
        // Arrays.parallelPrefix(nums, (l, r) -> l + r);
        long[] preSum = new long[nums.length]; // long
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) { // so need check
            preSum[i] = preSum[i - 1] + nums[i];
        }
        return count_MergeSort(preSum, 0, nums.length - 1, lower, upper);
    }

    static private int count_MergeSort(long[] preSums, int l, int r, int lower, int upper) {
        if (l == r) {
            int re = lower <= preSums[l] && preSums[l] <= upper ? 1 : 0;
            return re;
        }
        int mid = (l + r) / 2;
        int count = count_MergeSort(preSums, l, mid, lower, upper)
                + count_MergeSort(preSums, mid + 1, r, lower, upper);

        long[] tmp = new long[r - l + 1];
        int tmpSize = 0;

        int ri = mid + 1;
        int li = l;

        int rLclude = mid + 1;
        int rRplus1 = mid + 1;
        while (li <= mid || ri <= r) {
            if (ri == r + 1 || li <= mid && preSums[li] <= preSums[ri]) {
                // can not use binary search, duplicated targets
                while (rLclude <= r && preSums[rLclude] - preSums[li] < lower) rLclude++;
                while (rRplus1 <= r && preSums[rRplus1] - preSums[li] <= upper) rRplus1++;
                count += rRplus1 - rLclude;

                tmp[tmpSize++] = preSums[li++];
            } else {
                tmp[tmpSize++] = preSums[ri++];
            }
        }

        System.arraycopy(tmp, 0, preSums, l, r - l + 1);
        return count;
    }

    public static void main(String[] args) {
        System.out.println(
                countRangeSum(new int[]{0, -3, 2, 0}, 1, 2)); //2
        System.out.println(
                countRangeSum(new int[]{-2, 0, 0, 2, 2, -2}, -3, 1)); //10
        System.out.println(
                countRangeSum(new int[]{2147483647, -2147483648, -1}, -1, 0)); // 2
        System.out.println(
                countRangeSum(new int[]{-2, 5, -1}, -2, 2)); // 3
        System.out.println(
                countRangeSum(new int[]{2147483647, -2147483648, -1, 0}, -1, 0)); // 4
        System.out.println(
                countRangeSum(new int[]{-3, 1, 2, -2, 2, -1}, -3, -1));// 7
        System.out.println(
                countRangeSum(new int[]{}, -2, 2)); // 0

    }
}
