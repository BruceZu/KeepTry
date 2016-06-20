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

package array.rotate;

/**
 * <pre>
 * Difficulty: Easy
 * Rotate an array of n elements to the right by k steps.
 *
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 *
 * Hint:
 * Could you do it in-place with O(1) extra space?
 *
 * Related problem: Reverse Words in a String II
 *
 *
 * Hide Tags Array
 *
 *  @see <a href = "https://leetcode.com/problems/rotate-array/">leetcoce link</a>
 */
public class Leetcode189RotateArray {
    /**
     * Force way <pre>
     *  Note:
     *      k = k % array length
     *      do not change the array reference
     */
    public void rotate3(int[] nums, int k) {
        int[] re = new int[nums.length];
        k = k % nums.length;
        for (int i = k; i < nums.length; i++) {
            re[i] = nums[i - k];
        }
        for (int i = 0; i < k; i++) {
            re[i] = nums[nums.length - k + i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = re[i];
        }
    }

    public void reverse(int[] n, int l, int r) {
        while (l < r) {
            n[l] ^= n[r];
            n[r] ^= n[l];
            n[l] ^= n[r];
            l++;
            r--;
        }
    }

    public void rotate2(int[] nums, int k) {
        if (k == 0 || nums == null || nums.length == 0) {
            return;
        }
        k = k % nums.length;
        int size = nums.length;
        reverse(nums, 0, size - k - 1);
        reverse(nums, size - k, size - 1);
        reverse(nums, 0, size - 1);
    }
}
