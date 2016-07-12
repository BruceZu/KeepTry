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

package locked.nosubmmitted;

import java.util.HashMap;
import java.util.Map;

/**
 * 325. Maximum Size Subarray Sum Equals k
 * Difficulty: Easy <pre>
 * Given an array nums and a target value k, find the maximum length of
 * a subarray that sums to k. If there isn't one, return 0 instead.
 * <p/>
 * Example 1:
 * Given nums = [1, -1, 5, -2, 3], k = 3,
 * return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)
 * <p/>
 * Example 2:
 * Given nums = [-2, -1, 2, 1], k = 1,
 * return 2. (because the subarray [-1, 2] sums to 1 and is the longest)
 * <p/>
 * Follow Up:
 * Can you do it in O(n) time?
 * <p/>
 * Hide Company Tags Palantir Facebook
 * Hide Tags Hash Table
 * Hide Similar Problems (M) Minimum Size Subarray Sum (E) Range Sum Query - Immutable
 */
public class LC325MaximumSizeSubarraySumEqualsk {
    // beat 100%

    /**
     * Even though HashMap operations are amortized O(1) in practice they
     * are not that fast which usually means that things like if
     * (map.containsKey(...)) {map.get(...)...} should be avoided.
     * I was coding with that in mind, and got this solution.
     * It was 26 ms first, then I added a termination condition that
     * stopped searching if it is guaranteed that there won't be any better results.
     * That improved to 24 ms. Then I figured out that I can allocate a HashMap
     * with a specified capacity—that sped up to 22 ms, and the strange thing is,
     * if I specify capacity equal to nums.length instead of nums.length + 1,
     * I get 19 ms for some reason. Probably some random subtle effect explained
     * by the specific test cases (such as hash function giving better
     * distribution for certain capacity values).
     *
     * @param nums
     * @param k
     * @return
     */
    public static int maxSubArrayLen(int[] nums, int k) {
        int[] sum = new int[nums.length + 1];
        Map<Integer, Integer> longest = new HashMap<>(nums.length + 1);
        longest.put(0, 0); // The longest sum that equals to 0 so far is the zero-length prefix sum.
        for (int i = 0; i < nums.length; ++i) {
            sum[i + 1] = sum[i] + nums[i];
            longest.put(sum[i + 1], i + 1);
        }
        int len = 0;
        for (int i = 0; i < nums.length; ++i) {
            // What is the largest j such as that sum[j] - sum[i] = k?
            // It's the same as the largest j such as that sum[j] = k + sum[i].
            Integer l = longest.get(k + sum[i]);
            if (l != null && l - i > len) {
                if (l >= nums.length - 1) {
                    return l - i; // It doesn't get any longer than this!
                } else {
                    len = l - i;
                }
            }
        }
        return len;
    }

    // beat 98%
    public int maxSubArrayLen2(int[] nums, int k) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) {
            if (nums[0] == k) return 1;
            return 0;
        }
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < sum.length; i++) {
            hashtable.put(sum[i], i);
        }
        int max = 0;
        for (int i = 0; i < sum.length; i++) {
            Integer c = hashtable.get(sum[i] + k);
            if (c != null && c.intValue() > i && c.intValue() - i > max)
                max = c.intValue() - i;
        }
        return max;
    }

    // beat 98%

    /**
     * Even though HashMap operations are amortized O(1) in practice they are not that fast which usually
     * means that things like if (map.containsKey(...)) {map.get(...)...} should be avoided.
     * I was coding with that in mind, and got this solution. It was 26 ms first, then I added
     * a termination condition that stopped searching if it is guaranteed that there won't be
     * any better results. That improved to 24 ms. Then I figured out that I can allocate a
     * HashMap with a specified capacity—that sped up to 22 ms, and the strange thing is,
     * if I specify capacity equal to nums.length instead of nums.length + 1,
     * I get 19 ms for some reason. Probably some random subtle effect explained by
     * the specific test cases (such as hash function giving better distribution for certain capacity values).
     */
    public int maxSubArrayLen3(int[] nums, int k) {
        int[] sum = new int[nums.length + 1];
        Map<Integer, Integer> longest = new HashMap<>(nums.length + 1);
        longest.put(0, 0); // The longest sum that equals to 0 so far is the zero-length prefix sum.
        for (int i = 0; i < nums.length; ++i) {
            sum[i + 1] = sum[i] + nums[i];
            longest.put(sum[i + 1], i + 1);
        }
        int len = 0;
        for (int i = 0; i < nums.length; ++i) {
            // What is the largest j such as that sum[j] - sum[i] = k?
            // It's the same as the largest j such as that sum[j] = k + sum[i].
            Integer l = longest.get(k + sum[i]);
            if (l != null && l - i > len) {
                if (l >= nums.length - 1) {
                    return l - i; // It doesn't get any longer than this!
                } else {
                    len = l - i;
                }
            }
        }
        return len;
    }
    // other ideas
    /**
     * The subarray sum reminds me the range sum problem.
     * Preprocess the input array such that you get the range sum in constant time.
     * sum[i] means the sum from 0 to i inclusively the sum from i to j is sum[j] - sum[i - 1]
     * except that from 0 to j is sum[j]. j-i is equal to the length of subarray of original
     * array. we want to find the max(j - i) for any sum[j] we need to find if there
     * is a previous sum[i] such that sum[j] - sum[i] = k
     Instead of scanning from 0 to j -1 to find such i, we use hashmap to do the job in constant
     time. However, there might be duplicate value of of sum[i]
     we should avoid overriding its index as we want the max j - i, so we want to keep i
     as left as possible.
     */

}
