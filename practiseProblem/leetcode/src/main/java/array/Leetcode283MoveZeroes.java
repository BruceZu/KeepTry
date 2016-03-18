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

package array;

// Given an array nums, write a function to move all 0's to the end of it
// while maintaining the relative order of the non-zero elements.
// For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
// Note:
// You must do this in-place without making a copy of the array.
// Minimize the total number of operations.

// https://leetcode.com/problems/move-zeroes/

// not find the fast one yet
public class Leetcode283MoveZeroes {
    public void moveZeroes(int[] nums) {
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

    public void moveZeroes2(int[] nums) {
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++;
            }
        }
    }

    public void moveZeroes3(int[] nums) {
        int p = 0;
        for (int i : nums) {
            if (i != 0) {
                nums[p++] = i;
            }
        }
        while (p < nums.length) nums[p++] = 0;
    }
}