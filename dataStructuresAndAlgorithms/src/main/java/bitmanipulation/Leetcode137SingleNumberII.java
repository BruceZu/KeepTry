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
 * Difficulty: Medium
 * Given an array of integers, every element appears three times except for one. Find that single one.
 *
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 *
 * Tags: Bit Manipulation
 * Similar Problems
 * (M) Single Number
 * (M) Single Number III
 * ==============================================================================================
 * watch directly in the 2D map with 31~0 bit columns
 * sum number of '1' on each bit column and mod 3, this will filter out those elements which appears 2 times
 * sum maybe 0, 1, 2
 */
public class Leetcode137SingleNumberII {
    public int singleNumber(int[] nums) {
        int answer = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                sum += ((nums[j] >> i) & 1) == 1 ? 1 : 0;
            }
            sum %= 3;
            answer |= sum << i;
        }
        return answer;
    }

    /**
     * <pre>
     * @see
     * <a href="https://leetcode.com/discuss/40246/another-bitwise-operation-method-numbers-detailed-explanation">best idea</a>
     * <a href="https://leetcode.com/discuss/857/constant-space-solution">better idea</a>
     * <a href="https://leetcode.com/discuss/54970/an-general-way-to-handle-all-this-sort-of-questions">idea 2</a>
     * <a href="https://leetcode.com/discuss/47154/java-bitmanipulate-solution-with-o-n-and-constant-space">idea 3</a>
     * <a href="https://leetcode.com/discuss/44345/java-bit-manipulation-solution">idea 4</a>
     * <a href="https://leetcode.com/discuss/43377/the-simplest-solution-ever-with-clear-explanation"> idea 5</a>
     * <a href="https://leetcode.com/discuss/31595/detailed-explanation-generalization-bitwise-operation-numbers">idea 6</a>
     * <a href="https://leetcode.com/discuss/6632/challenge-me-thx">idea 7</a>
     */
    public int singleNumber2(int[] nums) {
        return -1; //  TODO
    }
}
