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

public class Leetcode493ReversePairs10 {

    public int reversePairs(int[] nums) {
        this.tmp = new int[nums.length];
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int l, int r) {
        if (l >= r) return 0;

        int mid = l + (r - l) / 2;

        int result = mergeSort(nums, l, mid) + mergeSort(nums, mid + 1, r);

        for (int li = l, ri = mid + 1; li <= mid; li++) {
            while (ri <= r && nums[li] > 2L * nums[ri]) ri++;
            result += ri - (mid + 1);
        }
        // Arrays.sort(nums, l, r + 1);
        myMerge(nums, l, mid, r);
        return result;
    }

    private int[] tmp;

    private void myMerge(int[] nums, int l, int mid, int r) {
        for (int i = l; i <= r; i++) {
            tmp[i] = nums[i];
        }
        int li = l;
        int ri = mid + 1;
        int size = l;//pointer for sorted array
        while (li <= mid || ri <= r) {
            if (ri > r || (li <= mid && tmp[li] <= tmp[ri])) {
                nums[size++] = tmp[li++];
            } else {
                nums[size++] = tmp[ri++];
            }
        }
    }
}
