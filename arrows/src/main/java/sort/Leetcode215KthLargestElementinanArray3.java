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

package sort;

public class Leetcode215KthLargestElementinanArray3 {
    // assume k always is valid  1~ nums.length
    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, k - 1);
    }

    private static int quickSelect(int[] nums, int kIndex) {
        int l = 0, r = nums.length - 1;
        while (true) { // care
            if (l == r) { // care: quick select will end up with 1:  where l == r == k
                return nums[l];
            }
            int locatedPivotIndex
                    = partitionInDesendingOrder(nums, l, r, getPivotIndexUsingMedianOf3(nums, l, r));
            if (locatedPivotIndex == kIndex)
                return nums[locatedPivotIndex]; // care: quick select will end up with 2: pivot index = k index
            else if (locatedPivotIndex > kIndex)
                r = locatedPivotIndex - 1;
            else
                l = locatedPivotIndex + 1;
        }
    }

    private static int getPivotIndexUsingMedianOf3(int[] nums, int l, int r) {
        int mid = (r + l) / 2;
        int lv = nums[l], mv = nums[mid], rv = nums[r];
        return lv <= mv
                ? mv <= rv ? mid : lv <= rv ? r : l
                : lv <= rv ? l : mv <= rv ? r : mid;
    }

    // result will be :
    //   >p, p, <=p
    private static int partitionInDesendingOrder(int[] nums, int l, int r, int pivotIndex) {
        int p = nums[pivotIndex];
        // allocate the pivot on the right side
        swap(nums, pivotIndex, r);

        int nextBigIndex = l;
        for (int i = l; i < r; ++i) {
            if (nums[i] > p) {
                swap(nums, nextBigIndex++, i);
            }
        }
        swap(nums, r, nextBigIndex);
        return nextBigIndex;
    }

    private static void swap(int[] nums, int index1, int index2) {
        if (nums[index1] != nums[index2]) {
            nums[index1] ^= nums[index2];
            nums[index2] ^= nums[index1];
            nums[index1] ^= nums[index2];
        }
    }

    // -----------------------------------------------------------------------------------------
    public static void main(String[] args) {
        findKthLargest(new int[]{3, 1, 2, 4}, 2);
    }
}
