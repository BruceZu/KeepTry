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

import java.util.Arrays;

public class Leetcode324WiggleSortII3 {
    public void wiggleSort2(int[] nums) {
        Arrays.sort(nums); // O(NlgN)
        int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
        int[] copy = Arrays.copyOf(nums, n); // O(N) extra space
        int index = 0;
        for (int i = 0; i <= mid; i++) {
            nums[index] = copy[mid - i];
            if (index + 1 < n) {
                nums[index + 1] = copy[n - i - 1];
            }
            index += 2;
        }
    }

    public void wiggleSort(int[] nums) {
        Arrays.sort(nums); // O(NlgN)
        int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
        int[] copy = Arrays.copyOf(nums, n); // O(N) extra space
        int index = 0;
        for (int i = 0; i <= mid; i++) {
           // todo simply it
        }
    }
}
