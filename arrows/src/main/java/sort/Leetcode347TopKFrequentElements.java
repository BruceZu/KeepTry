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

package sort;

import java.util.*;

public class Leetcode347TopKFrequentElements {
  /*
   347. Top K Frequent Elements
        Given an integer array nums and an integer k,
        return the k most frequent elements.
        You may return the answer in any order.


    Input: nums = [1,1,1,2,2,3], k = 2
    Output: [1,2]

    Input: nums = [1], k = 1
    Output: [1]


    Constraints:

    1 <= nums.length <= 105
    k is in the range [1, the number of unique elements in the array].
    It is guaranteed that the answer is unique.

    Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
  */

  /*
  Clarify the constraints
  Follow up:  target runtime O(N)
  quick select
  */
  public static int[] topKFrequent_(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i : nums) map.put(i, map.getOrDefault(i, 0) + 1);
    Map.Entry<Integer, Integer>[] a = new Map.Entry[map.size()];
    map.entrySet().toArray(a);
    quickSortDescending(a, k);
    int[] r = new int[k];
    // assume result is unique
    for (int i = 0; i < k; i++) r[i] = a[i].getKey();
    return r;
  }

  // Assume kth is in valid range [1, N]
  private static void quickSortDescending(Map.Entry<Integer, Integer>[] a, int kth) {
    int l = 0, r = a.length - 1;
    while (true) {
      int pi = partition2wayWithMedianPivotal(a, l, r);
      int w = pi + 1;
      if (w == kth) return;
      else if (w < kth) l = pi + 1;
      else r = pi - 1;
    }
  }

  /* return the right most index of pivotal after partition into 3ways in descending:
      [l, r] => [>p , p(s), < p]
  */
  private static int partition3wayWithMedianPivotal(Map.Entry<Integer, Integer>[] a, int l, int r) {
    int pv = a[l + r >>> 1].getValue();
    swap(a, l, l + r >>> 1);
    int b = l + 1, s = r; // next index for inserting v bigger than p, v smaller than p
    int i = l + 1; // l and i start from start +1
    while (i <= s) {
      int v = a[i].getValue();
      if (v > pv) swap(a, b++, i++);
      else if (v == pv) i++;
      else swap(a, i, s--);
    }
    swap(a, l, s); // replace pivotal with the last bigger one
    return s;
  }
  /* return the right most index of pivotal after partition into 2ways in descending:
       [l ,r ] => [>= p,  < p]
  */
  private static int partition2wayWithMedianPivotal(Map.Entry<Integer, Integer>[] a, int l, int r) {
    int pv = a[l + r >>> 1].getValue();
    swap(a, l, l + r >>> 1);
    int s = r; // next index for v smaller than p
    int i = l + 1; // l and i start from start +1
    while (i <= s) {
      int v = a[i].getValue();
      if (v >= pv) i++;
      else swap(a, i, s--);
    }
    swap(a, l, s);
    return s;
  }

  private static void swap(Map.Entry<Integer, Integer>[] a, int l, int r) {
    Map.Entry<Integer, Integer> tmp = a[l];
    a[l] = a[r];
    a[r] = tmp;
  }
  /*
  Use 2 maps
   first keep integer and its frequency
   second keep frequency and integers

  E.g  [1,1,1,2,2,3]
   the second   Map<Frequency, Set<Integer>>  Frequency<=N
   1 3
   2 2
   3 1
   4
   5
   6

   O(n) time and space
  */
  public static int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> mif = new HashMap<>();
    Map<Integer, Set<Integer>> fi = new HashMap<>();
    for (int i : nums) {
      mif.put(i, mif.getOrDefault(i, 0) + 1);
      if (mif.get(i) > 1) fi.get(mif.get(i) - 1).remove(i);
      fi.putIfAbsent(mif.get(i), new HashSet<>());
      fi.get(mif.get(i)).add(i);
    }
    Set<Integer> set = new HashSet<>();
    for (int i = nums.length; i >= 1; i--) {
      if (fi.containsKey(i)) set.addAll(fi.get(i));
      if (set.size() == k) break; // `It is guaranteed that the answer is unique.`
    }
    int[] r = new int[k];
    int i = 0;
    for (int e : set) r[i++] = e;
    return r;
  }
}
