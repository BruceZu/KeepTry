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

package bitmanipulation;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 169. Majority Element
 *
 * Difficulty: Easy
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is <strong>non-empty </strong>and the majority element <strong>always exist</strong>
 * in the array.
 *
 *
 * Company Tags Adobe Zenefits
 * Tags Array Divide and Conquer Bit Manipulation
 * Similar Problems (M) Majority Element II
 * ===========================================================================================
 *
 * Idea:
 *   1 HashMap;  value: times
 *       each time map.containsKey()? after put, check
 *             if (vtimes.get(v) > nums.length / 2) {
 *                      return v;
 *             }
 *   2 vote. {@link array.Leetcode169MajorityElement2#majorityElement(int[])}
 *
 *   5 bit manipulation: it is a 2D map.
 *   Watch all elements on this 2D map,
 *   As the majority exists, so sure there is some column where there is the bit of majority
 *   number identified by its sum is more than n/2.
 *
 * @see
 * <br><a href="http://www.cs.utexas.edu/~moore/best-ideas/mjrty/">A Linear Time Majority Vote Algorithm</a>
 * <br><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html">Operator Precedence</a>
 */
public class Leetcode169MajorityElement {
    public static int majorityElement1(int[] nums) {
        int candidator = 0;
        for (int i = 31; i >= 0; i--) {
            int times = 0;
            for (int j = 0; j < nums.length; j++) {
                times = times + (nums[j] >>> i & 1);
            }
            if (times > nums.length / 2) {
                candidator += (1 << i);
            }
        }
        return candidator;
    }

    // hashmap, too slow
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> times = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            Integer t = times.get(nums[i]);
            if (t == null) {
                times.put(nums[i], 1);
            } else {
                times.put(nums[i], t + 1);
            }

            if (times.get(nums[i]) > nums.length / 2) {
                return nums[i];
            }
        }
        return 100; // never reach here
    }
}
