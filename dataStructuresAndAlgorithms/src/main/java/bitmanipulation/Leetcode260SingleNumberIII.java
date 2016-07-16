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
 * Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.
 *
 * For example:
 *
 * Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
 *
 * Note:
 *      The order of the result is not important. So in the above example, [5, 3] is also correct.
 *      Your algorithm should run in linear runtime complexity.
 *      Could you implement it using only constant space complexity?
 *
 * Tags: Bit Manipulation
 * Similar Problems
 * (M) Single Number
 * (M) Single Number II
 */
public class Leetcode260SingleNumberIII {
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        for (int i = 0; i < nums.length; i++) {
            xor ^= nums[i];
        }
        int divideBy = xor & (-xor);
        int[] r = new int[2];
        r[0] = xor;
        r[1] = xor;
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            if ((divideBy & v) == divideBy) {
                r[0] ^= v;
            } else {
                r[1] ^= v;
            }
        }
        return r;
    }

    /**
     * <pre>
     * Find the two non-repeating elements in an array of repeating elements (twice)
     *
     *  Q:  2, 4, 2, 7, 9, 4, -5, -5
     *  (not in order, negative and positive integer)
     *
     *
     *  A: X = 7 and Y = 9
     *
     *   Space complexity O(1). constant.
     *      Some use require not use any extra space, include variable.
     *
     *    Runtime O(N). Liner
     *
     *    Idea:
     *      1 > XOR all element to get: xor =   X^Y;  (1110)
     *      2 > divide all element to 2 groupt by rightest set bit; (0010)
     *             xor & (-xor)
     *             or
     *             xor & (~(xor -1))
     *
     *      3 get X and Y by XOR with each group
     *
     *     Note:
     *          step 3 start from index of 0
     *          & operation need in ()
     */
    public static void printNoRepeated2Num(int[] nums) {
        int xor = nums[0];
        for (int i = 1; i < nums.length; i++) {
            xor ^= nums[i];
        }
        int divideBy = xor & (-xor);

        int X = xor;
        int Y = xor;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & divideBy) == divideBy) {
                X ^= nums[i];
            } else {
                Y ^= nums[i];
            }
        }
        System.out.println("X: " + X + " Y: " + Y);
    }

    public static void main(String[] args) {
        printNoRepeated2Num(new int[]{2, 4, 2, 7, 9, 4, -5, -5});
    }

    /**
     * <pre>
     * Find the two repeating elements in a given array
     * You are given an array of n+2 elements.
     * All elements of the array are in range 1 to n.
     * And all elements occur once except two numbers which occur twice.
     * Find the two repeating numbers.
     *
     * For example, array = {4, 2, 4, 5, 2, 3, 1} and n = 5
     *
     * The above array has n + 2 = 7 elements with all elements occurring once except 2 and 4 which occur twice.
     * So the output should be 4, 2
     *
     *
     *  Idea A:
     *    1  xor from 1 to n;  and each element of array.  now xor = X^Y
     *       1 2 3 4 5; 1 2 3 4 5; 4 2
     *    2  divide above into 2 groups;  ...
     *
     *  Idea B:
     *    X*Y
     *    X+Y
     *  Idea C:
     *    Cons: if all element > 0  or all element < 0
     *    Pros:
     *        without using any variables
     *        the runtime is liner.
     *        not limited to 2 duplicated number.
     *    Steps:
     *     1  Use array elements as index
     *     2  check for sign
     *     3  at last restore the sign.
     */
    public static void printRepeated2Num(int[] nums) {

    }
}
