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

/**
 * <pre>
 * Difficulty: Medium
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 *
 * For example,
 * Given nums = [0, 1, 3] return 2.
 *
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
 *
 *   Tags: Array, Math, Bit Manipulation
 *
 *  Similar Problems:
 *  (H) First Missing Positive
 *  (M) Single Number
 *  (H) Find the Duplicate Number
 *
 * </pre>
 */
public class Leetcode268MissingNumber {
    public int missingNumber(int[] nums) {
        int sum = 0;
        int all = 0;
        int i = 0;
        for (; i < nums.length; i++) {
            all += i;
            sum += nums[i];
        }
        all += i;
        return all - sum;
    }

    public int missingNumber2(int[] nums) {
        int xor = nums[0];
        for (int i = 1; i < nums.length; i++) {
            xor ^= nums[i];
            xor ^= i;
        }
        xor ^= 0;
        xor ^= nums.length;
        return xor;
    }
}
