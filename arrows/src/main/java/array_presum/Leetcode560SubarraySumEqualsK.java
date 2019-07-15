//  Copyright 2019 The KeepTry Open Source Project
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

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers and an integer k, you need to find the total number of continuous
 * subarrays whose sum equals to k.
 *
 * <pre>
 *   Example 1:
 *   Input:nums = [1,1,1], k = 2
 *   Output:  2
 *
 *   Note:
 *   The length of the array is in range [1, 20,000].
 *   The range of numbers in the array is [-1000, 1000]
 *   and the range of the integer k is [-1e7, 1e7].
 */
public class Leetcode560SubarraySumEqualsK {
    // O(n)
    public int subarraySum(int[] nums, int k) {
        // map Sum To its Counts
        Map<Integer, Integer> sn = new HashMap();
        int s = 0, r = 0;
        sn.put(s, 1); // empty array
        for (int i : nums) {
            s += i;
            r += sn.getOrDefault(s - k, 0);
            sn.put(s, sn.getOrDefault(s, 0) + 1);
        }
        return r;
    }

    public int subarraySum2(int[] nums, int k) {
        // map Sum To its Counts
        Map<Integer, Integer> sn = new HashMap();
        int s = 0, r = 0;
        for (int i : nums) {
            sn.put(s, sn.getOrDefault(s, 0) + 1);
            s += i;
            r += sn.getOrDefault(s - k, 0);
        }
        return r;
    }
}
