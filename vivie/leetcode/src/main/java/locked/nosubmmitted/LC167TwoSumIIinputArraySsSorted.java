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

package locked.nosubmmitted;

import java.util.HashMap;
import java.util.Map;

/**
 * 167. Two Sum II - Input array is sorted
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * <pre>
 *     Difficulty: Medium
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 *
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
 *
 * You may assume that each input would have exactly one solution.
 *
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2
 *
 * Hide Company Tags: Amazon
 * Hide Tags: Array, Two Pointers, Binary Search
 * Hide Similar Problems: (E) Two Sum
 *
 * </pre>
 */
public class LC167TwoSumIIinputArraySsSorted {
    /**
     * the fast one beat 99.35%
     * This solution combines two pointer with binary search
     */
    public class Solution {
        //high：寻找比target小，但是其右边比target大的坐标
        //low：寻找比target大，但是左边比target小的坐标
        public int[] twoSum(int[] numbers, int target) {
            int low = 0;
            int high = numbers.length - 1;
            while (low < high) {
                if ((numbers[low] + numbers[high]) > target) {
                    int start = low + 1;
                    int end = high;
                    while (start < end) {
                        int mid = (start + end) / 2;
                        if ((numbers[low] + numbers[mid]) > target) {
                            end = mid - 1;
                        } else if ((numbers[low] + numbers[mid]) < target) {
                            start = mid + 1;
                        } else {
                            end = mid;
                            break;
                        }
                    }
                    if ((numbers[low] + numbers[end]) > target) end--;
                    high = end;
                } else if ((numbers[low] + numbers[high]) < target) {
                    int start = low;
                    int end = high - 1;
                    while (start < end) {
                        int mid = (start + end) / 2;
                        if ((numbers[high] + numbers[mid]) > target) {
                            end = mid - 1;
                        } else if ((numbers[high] + numbers[mid]) < target) {
                            start = mid + 1;
                        } else {
                            end = mid;
                            break;
                        }
                    }
                    if ((numbers[end] + numbers[high]) < target) end++;
                    low = end;
                } else {
                    break;
                }
            }
            return new int[]{low + 1, high + 1};
        }
    }

    /**
     * beat 57,35
     */
    public int[] twoSum1(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] ret = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                ret[0] = map.get(target - numbers[i]) + 1;
                ret[1] = i + 1;
                break;
            }
            map.put(numbers[i], i);
        }
        return ret;
    }

    // two-pointer method
    public int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        int[] ret = new int[2];
        while (l < r) {
            int sum = numbers[l] + numbers[r];
            if (sum > target)
                r--;
            else if (sum < target)
                l++;
            else {
                ret[0] = l + 1;
                ret[1] = r + 1;
                break;
            }
        }
        return ret;
    }
}

