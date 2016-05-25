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

package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
  Leetcode 1. 2 sum
  Given an array of integers, find two numbers such that they add up to a specific target number.
  The function twoSum should return indices of the two numbers such that they add up to the target,
  where index1 must be less than index2. Please note that your returned answers (both index1 and index2)
  are not zero-based.
  You may assume that each input would have exactly one solution.

  Array:[3,2,4]  target:6

  expected:
  [2,3]
  */

/*
two sum
one array:
— k is one element of array;
—— ask i and j,  a[k] = a[i] + a[j],  i, j < k
—— ask all pairs of i and j , a[k] = a[i] + a[j],
— K is given
—— ask i and j,  K = a[i] + a[j]
—— ask all pair of i and j , K = a[i]+ a[j]

ask :
-yes/no
-who are they
-ask their index

two array, a and b:
— K is given
—— ask i and j,  K = a[i] + b[j]
—— ask all pair of i and j , K = a[i]+ b[j]
 */
public class Leetcode1twoSum {
    // runtime beats 99.66% of java submissions
    // it depends on the max number.
    // O(N)
    public static int[] twoSum(int[] nums, int target) {
        Integer[] vIndex = new Integer[0Xffff];
        int delta = 0Xfff;

        for (int i = 0; i < nums.length; i++) {
            int index = target - nums[i] + delta;
            if (vIndex[index] != null) {
                return new int[]{vIndex[index], i};
            } else {
                vIndex[nums[i] + delta] = i;
            }
        }
        return null;
    }

    public static int[] twoSum1(final int[] nums, int target) {
        int[] r = new int[2];
        r[0] = -1;
        r[1] = -1;
        int max = nums[0];
        int min = nums[0];
        int minGap = target - nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
            if (nums[i] < min) {
                min = nums[i];
            }
            if (target - nums[i] < minGap) {
                minGap = target - nums[i];
            }
        }

        min = Math.min(min, target);
        max = Math.max(max, target);

        min = Math.min(minGap, min);
        int delta = min < 0 ? -min : 0;  // min number and min distance

        int maxIndex = Math.max(max, nums.length);// max number and array length
        maxIndex += delta;

        int[] vIndex = new int[maxIndex + 1];
        for (int i = 0; i < nums.length; i++) {
            int gapIndex = target - nums[i] + delta;
            if (vIndex[gapIndex] != 0) {
                r[0] = vIndex[gapIndex];
                r[1] = i + 1;
                return r;
            } else {
                vIndex[nums[i] + delta] = i + 1;
                // 1> For the nums[0],  vIndex[ nums[0]] will save 1.
                // If it save 0, nums[0] will be ignore later, that would be wrong
                // 2> for [0, 8 , 8 , 8] and 16. The first 8 is saved, 2 sum problem is ok, but
                // if it if for 3 sum problem, and target is 24, the last second 8 would cover all advance '8'.
            }
        }
        return r;
    }

    // O(nlogn)
    // runtime beats 90.26% of java submissions. on Feb 5, 2016
    public static int[] twoSum2(final int[] nums, int target) {
        int[] r = new int[2];
        r[0] = -1;
        r[1] = -1;

        int[] clone = nums.clone();
        Arrays.sort(clone); // O(nlogn) in the worse case

        int l = 0;
        int right = clone.length - 1;
        while (true) {  // O(logn) in the worse case
            if (l == right) {
                break;
            }
            int sum = clone[l] + clone[right];
            if (sum == target) {
                break;
            }
            if (sum < target) {
                l++;
            } else {
                right--;
            }
        }

        if (l == right) {
            return r;
        }

        int ida = -1, idb = -1;
        for (int i = 0; i < nums.length; i++) {  // O(n) in the worse case
            if (nums[i] == clone[l] && ida == -1) {
                ida = i;
                if (idb != -1) {
                    break;
                }
                continue;
            }
            if (nums[i] == clone[right] && idb == -1) {
                idb = i;
                if (ida != -1) {
                    break;
                }
                continue;
            }
        }
        ida++;
        idb++;
        r[0] = ida < idb ? ida : idb;
        r[1] = ida == r[0] ? idb : ida;
        return r;
    }

    // runtime is O(nlogn)
    // Leetcode 1. Two Sum
    // beats 90.26% of java submissions on Feb 5, 2016
    public static int[] twoSum3(final int[] nums, int target) {
        int[] r = new int[2];
        r[0] = -1;
        r[1] = -1;

        int[] clone = nums.clone();
        Arrays.sort(clone); // O(nlogn)

        for (int i = 0; i < clone.length; ++i) { //O(n)
            int j = Arrays.binarySearch(clone, i + 1, clone.length, target - clone[i]); // O(logn)
            if (j >= 0) {
                int ida = -1, idb = -1;
                for (int n = 0; n < nums.length; n++) {//O(n)
                    if (nums[n] == clone[i] && ida == -1) {
                        ida = n;
                        if (idb != -1) {
                            break;
                        }
                        continue;
                    }
                    if (nums[n] == clone[j] && idb == -1) {
                        idb = n;
                        if (ida != -1) {
                            break;
                        }
                        continue;
                    }
                }
                ida++;
                idb++;
                r[0] = ida > idb ? idb : ida;
                r[1] = ida == r[0] ? idb : ida;
                return r;
            }
        }
        return r;
    }

    // runtime is O(N)
    // Leetcode 1. Two Sum
    // beats 33.47% of java submissions on Feb 5, 2016
    public static int[] twoSum4(final int[] nums, int target) {
        int[] r = new int[2];
        r[0] = -1;
        r[1] = -1;
        Map<Integer, List<Integer>> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            Set set = map.keySet();
            if (set.contains(target - v)) {
                r[0] = map.get(target - v).get(0) + 1;
                r[1] = i + 1;
                return r;
            }
            List ids = map.get(v);
            if (ids == null) {
                ids = new LinkedList<>();
                ids.add(i); // keep all indexes
                map.put(v, ids);

            } else {
                ids.add(i);
                map.put(v, ids);
            }
        }
        return r;
    }
}
