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

import java.util.Arrays;

/**
 * <pre>
 * Array
 * Could you do it in-place with O(1) extra space?
 *  @see <a href = "https://leetcode.com/problems/rotate-array/">leetcoce link</a>
 */
public class Leetcode189RotateArray {
    public void reverse(int[] n, int l, int r) {
        while (l < r) {
            if (n[l] != n[r]) {
                n[l] ^= n[r];
                n[r] ^= n[l];
                n[l] ^= n[r];
            }
            l++;
            r--;
        }
    }

    public void rotate(int[] nums, int k) {
        if (k == 0 || nums == null || nums.length == 0) {
            return;
        }
        k = k % nums.length;
        int size = nums.length;
        reverse(nums, 0, size - k - 1);
        reverse(nums, size - k, size - 1);
        reverse(nums, 0, size - 1);
    }

    // use extra space
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] temp = Arrays.copyOfRange(nums, 0, n - k);
        System.arraycopy(nums, n - k, nums, 0, k);
        System.arraycopy(temp, 0, nums, k, n - k);
    }
}
