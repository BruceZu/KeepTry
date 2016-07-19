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

/**
 * <pre>
 * 169. Majority Element
 *
 * Difficulty: Easy
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is <strong>non-empty </strong>and the majority element <strong>always exist</strong> in the array.
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
 *   2  votes:
 *             {3,2,2}, times.
 *                 3     1
 *                 2     0
 *                 2     1
 *    {1,3,3,2,2,2,5 }, times.
 *                 1     1
 *                 3     0
 *                 2     1
 *                 2     2
 *                 2     3
 *                 2     2
 *                 2     3
 *    init:
 *          major= first element, and times =1
 *    loop:
 *          if current element == major, times++;
 *          else  times--.
 *            check: if times == 0,
 *                   major = current
 *                   <strong>times =1;</strong>
 *   3 Randomly pick an element and check if it is the majority element.
 *
 *   4 Divide ->two halves? how to divide and how to check, e.g. for 1,1,2;2,2
 *
 *   5 bit manipulation: it is a 2D map.
 *   Watch all elements on this 2D map, As the majority exists, so sure there is some column where there is the bit of majority
 *   number and its sum is more than n/2.
 *
 * @see  Leetcode169MajorityElement2#majorityElement(int[])
 * @see
 * <br><a href="http://www.cs.utexas.edu/~moore/best-ideas/mjrty/">A Linear Time Majority Vote Algorithm</a>
 * <br><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html">Operator Precedence</a>
 */
public class Leetcode169MajorityElement {
    public static int majorityElement1(int[] nums) {
        int major = 0;
        for (int bits = 0; bits <= 31; bits++) {
            int counts = 0;
            for (int column : nums) {
                if ((column >>> bits & 1) == 1) {
                    counts++;
                }
            }
            if (counts > nums.length / 2) {
                major |= 1 << bits;
            }
        }
        return major;
    }
}
