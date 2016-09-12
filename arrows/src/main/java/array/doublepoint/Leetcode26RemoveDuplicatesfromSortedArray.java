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
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 *
 * Array
 * Two Pointers
 *
 * @see <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array/">leetcode</a>
 */
public class Leetcode26RemoveDuplicatesfromSortedArray {
    public int removeDuplicates(int[] nums) {
        int size = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[size++] = nums[i];
            }
        }
        return size;
    }
}
