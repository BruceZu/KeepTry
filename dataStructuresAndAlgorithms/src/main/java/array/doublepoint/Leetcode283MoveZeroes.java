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

package array.doublepoint;

/**
 * Difficulty: Easy <pre>
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative
 * order of the non-zero elements.
 * For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
 *
 * Note:
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 * 
 * Tags: Array Two Pointers
 * Similar Problems (E) Remove Element
 */
public class Leetcode283MoveZeroes {
    /**
     * <pre>
     * Idea:
     *   from start. 2 pointers to start and end index of zeros field
     *   swap
     *   update the end index
     *   till reach the end of array
     * or
     *   from end, 2 pointers to start and end index of numbers field
     *   swap
     *   update the start index
     *   till reach the first element of array
     *
     * Leetcode site shows this method has better performance that the next one.
     * It depends on the test data. Their idea are same.
     *
     * bad case:
     * [ 0, 0, 0 , num, num, num, num]
     *    m                n
     *
     *  operations: m x n
     */
    public void moveZeroes(int[] nums) {
        int izero = 0;
        while (izero < nums.length && nums[izero] != 0) {
            izero++;
        }
        if (izero == nums.length) {
            return;
        }

        int zeroi = izero;
        while (true) {
            while (zeroi + 1 < nums.length && nums[zeroi + 1] == 0) {
                zeroi++;
            }
            if (zeroi == nums.length - 1) {
                return;
            }

            nums[izero] = nums[zeroi + 1];
            nums[zeroi + 1] = 0;
            izero++;
            zeroi++;
        }
    }

    public void moveZeroes2(int[] nums) {
        int ni = nums.length - 1;
        while (ni >= 0 && nums[ni] == 0) {
            ni--;
        }
        if (ni == -1) {
            return;
        }
        int in = ni;
        while (true) {
            while (in - 1 >= 0 && nums[in - 1] != 0) {
                in--;
            }
            if (in == 0) {
                return;
            }
            for (int m = in - 1; m < ni; m++) {
                nums[m] = nums[m + 1];
            }
            nums[ni] = 0;
            ni--;
            in--;
        }
    }

    /**
     * Maintain the right index for next number,
     * hop each number, till no number are left,
     * then update the elements from the 'right index for next number' to the end.
     *
     * @param nums
     */
    public void moveZeroes3(int[] nums) {
        int rIndexForNextNumber = 0;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] != 0) {
                // hop
                nums[rIndexForNextNumber] = nums[i];
                rIndexForNextNumber++;
            }
            i++;
        }
        while (rIndexForNextNumber < nums.length) {
            nums[rIndexForNextNumber] = 0;
            rIndexForNextNumber++;
        }
    }
}
