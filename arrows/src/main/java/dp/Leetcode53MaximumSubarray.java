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

package dp;

/**
 * <pre>
 *     53. Maximum Subarray
 * Difficulty: Medium
 * Find the contiguous subarray within an array
 * (containing at least one number) which has the largest sum.
 *
 * For example, given the array
 *          [−2,1,−3,4,−1,2,1,−5,4],
 *
 * the contiguous subarray [4,−1,2,1]
 * has the largest sum = 6.
 *
 * More practice:
 * If you have figured out the O(n) solution,
 * try coding another solution using the divide and conquer approach, which is more subtle.
 *
 *  Tags
 *          Array
 *          Dynamic Programming
 *          Divide and Conquer
 *
 *   Similar Problems
 *          (E) Best Time to Buy and Sell Stock
 *          (M) Maximum Product Subarray
 *
 *
 *   ===========================================================================
 *
 *   O(n) in time  see {@link KadaneAlgorithmMaxSubArray Kadane Algorithm  }
 *
 * @see <a href="https://leetcode.com/problems/maximum-subarray/">leetcode</a>
 */
public class Leetcode53MaximumSubarray {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int max = nums[0], maxEndingHere = nums[0];
        for (int i = 1; i < n; i++) {
            maxEndingHere = ((maxEndingHere < 0) ? 0 : maxEndingHere) + nums[i];
            max = maxEndingHere > max ? maxEndingHere : max;
        }
        return max;
    }
}
