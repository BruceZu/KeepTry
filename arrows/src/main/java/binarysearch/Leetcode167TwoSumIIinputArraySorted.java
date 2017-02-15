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

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/">LeetCode</a>
 */
public class Leetcode167TwoSumIIinputArraySorted {
    // Input array is sorted
    // assume each input would have exactly one solution and you may not use the same element twice.
    // using 2 pointer: O(N)
    public int[] twoSum3(int[] numbers, int target) {
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int sum2 = numbers[i] + numbers[j];
            if (sum2 == target) {
                return new int[]{i + 1, j + 1};
            } else if (sum2 < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }

    /**
     * assume each input would have exactly one solution and you may not use the same element twice.
     * <p>
     * using map:  O(N)
     * to keep number and its index, target - current number, checking if remaining exists in map
     * numbers can be unsorted.
     */
    public int[] twoSum2(int[] numbers, int target) {
        Map<Integer, Integer> indexOfRemaining = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int remaining = target - numbers[i];
            //  if there is duplicated members
            //  need add check remaining ==  numbers[i]
            if (indexOfRemaining.containsKey(remaining)) {
                return new int[]{indexOfRemaining.get(remaining) + 1, i + 1};
            }
            indexOfRemaining.put(numbers[i], i);
        }
        return null;
    }

    // Input array is sorted
    // assume each input would have exactly one solution and you may not use the same element twice.
    // using binary search O(logn)
    //
    // Cases:
    // target  13; [0, 1, 2, 9, 12, 20]
    // target  12;  [-12,0,3,4,12]
    // target  100; [1 3 5 7 9 ..... 47 49 50 52 54 .... 94 96 98]
    // target  5; [0,0,0,0 0,0,0,0,2,3,9,9,9,9,9,9,9,9,9,9,9,9,9,9]
    //
    public int[] twoSum(int[] numbers, int target) {
        // input checking
        int candidateL = 0;
        int candidateLv = numbers[0];

        int candidateR = numbers.length; // just for initial
        int candidateRv = target - candidateLv;

        int searchFrom;
        int searchTo;
        while (true) {
            // find the index of the right candidate with remaining value
            searchFrom = candidateL + 1; // not use the same element twice
            searchTo = candidateR - 1;
            candidateR = binarySeachIndex(numbers, searchFrom, searchTo, candidateRv);
            if (numbers[candidateL] + numbers[candidateR] == target) {
                return new int[]{candidateL + 1, candidateR + 1};
            } else {
                candidateLv = target - numbers[candidateR];
            }

            // find the index of the left candidate with remaining value
            searchFrom = candidateL + 1;
            searchTo = candidateR - 1; // not use the same element twice
            candidateL = binarySeachIndex(numbers, searchFrom, searchTo, candidateLv);
            if (numbers[candidateL] + numbers[candidateR] == target) {
                return new int[]{candidateL + 1, candidateR + 1};
            } else {
                candidateRv = target - numbers[candidateL];
            }
        }
    }

    public int binarySeachIndex(int[] nums, int l, int r, long RemainingCandidate) {
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] > RemainingCandidate) {
                r = mid - 1;
            } else if (nums[mid] < RemainingCandidate) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return l; // one element left, it still may be result
    }
}
