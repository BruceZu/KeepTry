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

package sort;

public class Leetcode75SortColors {
    private void swap(int[] arr, int i, int j) {
        if (arr[i] != arr[j]) {
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }

    /*---------------------------------------------------------------------------------*/
    public void sortColors(int[] nums) {
        int left = 0, r = nums.length - 1;
        int i = 0;
        while (i <= r) { // care  <=
            if (nums[i] == 0) {
                swap(nums, i++, left++);
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, i, r--); // care --
            }
        }
    }
}
