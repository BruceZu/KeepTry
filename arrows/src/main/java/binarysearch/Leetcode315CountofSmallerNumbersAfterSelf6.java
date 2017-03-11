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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode315CountofSmallerNumbersAfterSelf6 {
    // Binary index tree this is the best solution see
    // http://www.programmercoach.com/2017/03/programming-interview-pearls-count.html
    // O(nlogn)
    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new LinkedList();
        }
        // Index is non-negative value even Integer.MAX_VALUE-Integer.MIN_VALUE=-1.
        // make sure O(nlogn)
        int[] sorted = nums.clone();
        Arrays.sort(sorted); // O(nlogn)
        for (int i = 0; i < nums.length; i++) {// O(nlogn)
            nums[i] = Arrays.binarySearch(sorted, nums[i]);
        }
        // max is nums.length -1, flat array length is nums.length
        // BIT array length is (flat array length + 1)
        int[] BITArray = new int[nums.length + 1];

        Integer[] result = new Integer[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) { // O(nlogn)
            int indexInFlatArray = nums[i]; // no negative, long index = r - l
            updateBITAfterAddOneToFlatArrayAtIndex(indexInFlatArray, BITArray);
            result[i] = faltArraySumBefore(indexInFlatArray - 1, BITArray); // [0,indexInFlatArray)
        }
        return Arrays.asList(result);
    }

    private int faltArraySumBefore(int index_0_based, int[] BITArray) {
        int idx = index_0_based + 1; // idx is 1 based
        int sum = 0; // [0,idx]
        while (idx > 0) {
            sum += BITArray[idx];
            idx -= idx & (-idx);
        }
        return sum;
    }

    private void updateBITAfterAddOneToFlatArrayAtIndex(int index_0_based, int[] BITArray) {
        int idx = index_0_based + 1; // idx is 1 based
        while (idx < BITArray.length) {
            BITArray[idx]++;
            idx += idx & (-idx);
        }
    }
}
