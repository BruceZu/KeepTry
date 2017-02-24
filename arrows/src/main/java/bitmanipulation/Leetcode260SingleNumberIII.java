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
 * Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear
 * exactly twice. Find the two elements that appear only once.
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
 * ================================================================================================================
 * Ideas:
 *      1 xor
 *      2 math
 *   if value is positive or all is negative
 *      1  value as index
 *   if update array is allowed:
 *      1  mark sign
 *      2  swap
 *
 * 1>  xor
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
 *      why it can be used to divide the 2 member into 2 groups?
 *          on this bit column others xor result will be 0, so 1 is caused by these 2 numbers.
 *      3 get X and Y by xor 'divideBy' with each group
 *
 *     Note:
 *          step 3 start from index of 0
 *          & operation need in ()
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
     * <a href="http://www.geeksforgeeks.org/find-the-two-repeating-elements-in-a-given-array/">Geeksforgeeks</a>
     * Find the two repeating elements in a given array
     * You are given an array of n+2 elements.
     * All elements of the array are in range <strong>1 to n</strong>. (means all are positive number)
     * And all elements occur once except two numbers which occur twice.
     * Find the two repeating numbers.
     *
     * For example, array = {4, 2, 4, 5, 2, 3, 1} and n = 5
     *
     * The above array has n + 2 = 7 elements with all elements occurring once except 2 and 4 which occur twice.
     * So the output should be 4, 2
     *
     * A:
     *    1  xor from <strong>1 to n</strong>;  and each element of array.  now xor = X^Y
     *       1~n: 1 2 3 4 5;
     *       arr: 1 2 3 4 5; 4 2
     *              |   |    | |
     *    2  divide above 1~n and arr into 2 groups;
     *
     * B:
     *    X*Y
     *    X+Y
     * C:
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
    static void printRepeating(int arr[]) {
        int n = arr.length - 2;

        int xor = arr[0];
        for (int i = 1; i < arr.length; i++)
            xor ^= arr[i];
        for (int i = 1; i <= n; i++)
            xor ^= i;

        int dividBy = (xor & ~(xor - 1));

        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
            int a = arr[i] & dividBy;
            if (a != 0)
                x = x ^ arr[i];
            else
                y = y ^ arr[i];
        }
        for (int i = 1; i <= n; i++) {
            int a = i & dividBy;
            if (a != 0)
                x = x ^ i;
            else
                y = y ^ i;
        }

        System.out.println(x + " " + y);
    }

    /* Driver program to test the above function */
    public static void main(String[] args) {
        int arr[] = {4, 2, 4, 5, 2, 3, 1};
        printRepeating(arr);
    }
}
