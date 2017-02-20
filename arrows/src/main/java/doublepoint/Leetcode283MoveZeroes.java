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

package doublepoint;

/**
 * Difficulty: Easy
 * <pre>
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
 *
 * @see <a href="https://leetcode.com/problems/move-zeroes/">leetcode</a>
 */
public class Leetcode283MoveZeroes {
    // better idea 1
    public void moveZeroes(int[] nums) {
        int forNextNum = 0;

        for (int i : nums) {
            if (i != 0) {
                nums[forNextNum++] = i;
            }
        }
        // the left elements will all be zero.
        while (forNextNum < nums.length) {
            nums[forNextNum++] = 0;
        }
    }

    //better idea 2
    public void moveZeroes2(int[] nums) {
        int num = 0, forNextNum = 0;
        while (num < nums.length) {
            if (nums[num] != 0) {
                int temp = nums[num];
                nums[num] = nums[forNextNum];
                nums[forNextNum] = temp;

                forNextNum++;
            }
            num++;
        }
    }

    // Basic Idea 1:
    public void moveZeroes3(int[] nums) {
        int zeroForNextNum = -1; // index
        int num = -1; // index
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroForNextNum = i;
                break;
            }
        }
        if (zeroForNextNum == -1) {
            return;
        }

        int findNextNumFrom = zeroForNextNum + 1;
        while (true) {
            for (int i = findNextNumFrom; i < nums.length; i++) {
                if (nums[i] != 0) {
                    num = i;
                    break;
                }
            }
            if (num == -1) {
                return;
            }

            nums[zeroForNextNum] = nums[num];
            nums[num] = 0;

            zeroForNextNum++;
            findNextNumFrom = num + 1;
            num = -1;
        }
    }

    // Basic Idea 1, improved version
    public void moveZeroes4(int[] nums) {
        int zeroForNextNum = 0;
        while (zeroForNextNum < nums.length && nums[zeroForNextNum] != 0) {
            zeroForNextNum++;
        }
        if (zeroForNextNum == nums.length) {
            return;
        }

        int num = zeroForNextNum;
        while (true) {
            while (num + 1 < nums.length && nums[num + 1] == 0) {
                num++;
            }
            if (num == nums.length - 1) {
                return;
            }

            nums[zeroForNextNum] = nums[num + 1];
            nums[num + 1] = 0;

            zeroForNextNum++;
            num++;
        }
    }

    // Basic Idea 1, other version
    public void moveZeroes5(int[] nums) {
        int zeroIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];

            if (cur == 0 && zeroIndex == -1) {
                zeroIndex = i;
                continue;
            }

            if (cur != 0 && zeroIndex != -1) {
                //swap
                nums[zeroIndex] = cur;
                nums[i] = 0;
                zeroIndex++;
            }
        }
    }
}
