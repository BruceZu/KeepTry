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

package two_pointer;

import java.util.*;

/**
 * <pre>
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
 * The solution set must not contain duplicate triplets.
 * For example, given array S = {-1 0 1 2 -1 -4},
 *
 * A solution set is:
 * (-1, 0, 1)
 * (-1, -1, 2)
 * <a href="https://leetcode.com/problems/3sum/">LeetCode</a>
 */

public class Leetcode15_3Sum {
    /**
     * duplicated number:   unique triplets, but nums[j] can be same as nums[i]
     * print out in order of ascending
     * triplets are unique
     * --
     * 3sum -> 2sum:
     * sort + 2 pointer, O(n*n)
     * sort + set or map, O(n*n).
     *     can not work here, as there maybe duplicated element
     *     need care the duplicated, keep times, and care the order, the index.
     * sort + binary search, O(nlogn) ~O(n*n)
     */

    public static void main(String[] args) {
        threeSum(new int[]{-1, 0, 1, 2, -1, -4}); // 2 results
        threeSum(new int[]{3, 0, -2, -1, 1, 2}); // 3 results
    }

    // o(n*n)
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                int j = i + 1, k = nums.length - 1, sum = 0 - nums[i];
                while (j < k) {
                    if (nums[j] + nums[k] == sum) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        while (j < k && nums[j] == nums[j + 1]) {
                            j++;
                        }
                        while (j < k && nums[k] == nums[k - 1]) {
                            k--;
                        }
                        j++;
                        k--;
                    } else if (nums[j] + nums[k] < sum) {
                        j++;
                    } else {
                        k--;
                    }
                }
            }
        }
        return result;
    }

    // o(n*n)
    public static List<List<Integer>> threeSum4(int[] nums) {
        //input checking
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i != 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (j < k && nums[j] == nums[j + 1]) { // or move k
                        j++;
                    }
                    j++;
                } else if (nums[i] + nums[j] + nums[k] > 0) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return result;
    }

    // o(n*n)
    public static List<List<Integer>> threeSum5(int[] nums) {
        //input checking
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (i != 0 && i < nums.length && nums[i - 1] == nums[i]) {
                i++;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                // nums[j] can be same as nums[i]
                if (nums[i] + nums[j] + nums[k] == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));

                    j++;
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                    k--;
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else if (nums[i] + nums[j] + nums[k] > 0) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum6(int[] nums) {
        //input checking
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (i != 0 && i < nums.length && nums[i - 1] == nums[i]) {
                i++;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                // nums[j] can be same as nums[i], do not compare j and i.
                if (nums[i] + nums[j] + nums[k] == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    // -1, -3, -3, 4, 4
                    j++;
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                    k--;
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else if (nums[i] + nums[j] + nums[k] > 0) {
                    k--;
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else {
                    j++;
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                }
            }
        }
        return result;
    }

    // Like public version, but without range checks.
    private static int binarySearch(int[] a, int low, int high,
                                    int key) {
        while (low < high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }
        return low;
    }

    public static List<List<Integer>> threeSum3(int[] nums) {
        //input checking
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length - 3; i++) {
            if (i != 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            // binary search 2 sum start
            int cl = i + 1; // left side candidate index
            int candidateLv = nums[cl];

            int cr = nums.length; // just for initial
            int candidateRv = 0 - nums[i] - candidateLv;

            int l; // index search from
            int r;
            while (cl < cr) {
                // find the index of the right candidate with remaining value
                l = cl + 1; // not use the same element twice
                r = cr - 1;
                if (l > r) {
                    break;
                }
                cr = binarySearch(nums, l, r, candidateRv);
                if (nums[i] + nums[cl] + nums[cr] == 0) {
                    result.add(Arrays.asList(nums[i], nums[cl], nums[cr]));
                    cr--;
                    while (cl < cr && nums[cr] == nums[cr + 1]) {
                        cr--;
                    }
                }
                candidateLv = 0 - nums[i] - nums[cr];

                // find the index of the left candidate with remaining value
                l = cl + 1;
                r = cr - 1; // not use the same element twice
                if (l > r) {
                    break;
                }
                cl = binarySearch(nums, l, r, candidateLv);
                if (nums[i] + nums[cl] + nums[cr] == 0) {
                    result.add(Arrays.asList(nums[i], nums[cl], nums[cr]));
                    cl++;
                    while (cl < cr && nums[cl] == nums[cl - 1]) {
                        cl++;
                    }
                }
                candidateRv = 0 - nums[i] - nums[cl];
                // binary serach 2 sum end
            }
        }
        return result;
    }
}
