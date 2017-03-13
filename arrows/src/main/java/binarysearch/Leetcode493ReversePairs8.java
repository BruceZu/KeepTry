//  Copyright 2017 The keepTry Open Source Project
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

package binarysearch;

public class Leetcode493ReversePairs8 {
    // merge sort
    // O(nlogn)
    public int reversePairs(int[] nums) {
        return getPairSizeBymergeSort(nums, 0, nums.length - 1);
    }

    private int getPairSizeBymergeSort(int[] nums, int l, int r) {
        if (l >= r) {
            return 0;
        }

        int m = (r + l) >> 1;
        int result = getPairSizeBymergeSort(nums, l, m) + getPairSizeBymergeSort(nums, m + 1, r);

        int li = l, riForSort = m + 1, riForPairs = m + 1;

        int[] tmp = new int[r - l + 1]; // for sort
        int tmpSize = 0;

        while (li <= m) {
            // get pairs
            while (riForPairs <= r && 2L * nums[riForPairs] < nums[li]) { // Integer->Long
                riForPairs++;
            }
            result += riForPairs - (m + 1);

            // sort
            while (riForSort <= r && nums[riForSort] <= nums[li]) {
                tmp[tmpSize++] = nums[riForSort++];
            }
            tmp[tmpSize++] = nums[li];

            // next
            li++;
        }

        // sort left
        while (riForSort <= r) {
            tmp[tmpSize++] = nums[riForSort++];
        }

        System.arraycopy(tmp, 0, nums, l, tmp.length);
        return result;
    }
}
