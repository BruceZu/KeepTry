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

/** @see <a href="">leetcode</> */

/** @see <a href="https://leetcode.com/problems/merge-sorted-array/">leetcode</> */
public class Leetcode88MergeSortedArray {
  public static void merge1(int[] nums1, int m, int[] nums2, int n) {
    int findBiggerFrom = 0;
    for (int s = 0; s < n; s++) {
      int insertTo = -1;
      for (int f = findBiggerFrom; f < m; f++) {
        if (nums1[f] > nums2[s]) {
          insertTo = f;
          break;
        }
      }
      if (insertTo == -1) {
        insertTo = m;
      }

      for (int i = m - 1; i >= insertTo; i--) {
        nums1[i + 1] = nums1[i];
      }
      m++;
      nums1[insertTo] = nums2[s];
      findBiggerFrom = insertTo + 1;
    }
  }

  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int cur1 = m - 1, cur2 = n - 1, end = m + n - 1;
    while (cur1 >= 0 && cur2 >= 0) {
      nums1[end--] = nums1[cur1] > nums2[cur2] ? nums1[cur1--] : nums2[cur2--];
    }
    if (cur1 <= 0) {
      while (cur2 >= 0) {
        nums1[end--] = nums2[cur2--];
      }
    }
  }
}
