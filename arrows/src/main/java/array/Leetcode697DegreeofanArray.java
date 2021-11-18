//  Copyright 2021 The KeepTry Open Source Project
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode697DegreeofanArray {
  /*
    Leetcode  697. Degree of an Array

    Given a non-empty array of non-negative integers nums,
    the degree of this array is defined as the maximum frequency of any one of its elements.

    Your task is to find the smallest possible length of a (contiguous) subarray of nums,
    that has the same degree as nums.


    Input: nums = [1,2,2,3,1]
    Output: 2
    Explanation:
    The input array has a degree of 2 because both elements 1 and 2 appear twice.
    Of the subarrays that have the same degree:
    [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
    The shortest length is 2. So return 2.


    Input: nums = [1,2,2,3,1,4,2]
    Output: 6
    Explanation:
    The degree is 3 because the element 2 is repeated 3 times.
    So [2,2,3,1,4,2] is the shortest subarray, therefore returning 6.


    Constraints:

    nums.length will be between 1 and 50,000.
    nums[i] will be an integer between 0 and 49,999.
  */

  /*
  O(N) time and space
  */
  public static int findShortestSubArray(int[] A) {
    Map<Integer, List<Integer>> map = new HashMap<>();
    int degree = 0;
    for (int i = 0; i < A.length; i++) {
      int v = A[i];
      map.putIfAbsent(v, new ArrayList<>());
      map.get(v).add(i);
      degree = Math.max(degree, map.get(v).size());
    }
    // The length of the shortest sub-array that shares that degree
    if (degree == 1) return 1;
    int len = Integer.MAX_VALUE;
    for (int key : map.keySet()) {
      if (map.get(key).size() == degree) {
        List<Integer> list = map.get(key);
        int curLen = list.get(list.size() - 1) - list.get(0) + 1;
        len = Math.min(len, curLen);
      }
    }
    return len;
  }

  // one loop
  public int findShortestSubArray_(int[] A) {
    Map<Integer, Integer> count = new HashMap<>(), left = new HashMap<>();
    int res = 0, degree = 0;
    for (int i = 0; i < A.length; i++) {
      left.putIfAbsent(A[i], i);
      count.put(A[i], count.getOrDefault(A[i], 0) + 1);
      if (count.get(A[i]) > degree) {
        degree = count.get(A[i]);
        res = i - left.get(A[i]) + 1;
      } else if (count.get(A[i]) == degree) {
        res = Math.min(res, i - left.get(A[i]) + 1);
      }
    }
    return res;
  }
}
