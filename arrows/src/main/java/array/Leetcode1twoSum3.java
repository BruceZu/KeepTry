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

/** O(nlogn) sort 2 pointer or binary search map to original index, this is not always right. */
public class Leetcode1twoSum3 {

  public static int[] twoSum(final int[] nums, int target) {
    int[] clone = nums.clone();
    Arrays.sort(clone); // O(nlogn) in the worse case

    int l = 0;
    int right = clone.length - 1;
    while (l != right) { // O(logn) in the worse case
      int sum = clone[l] + clone[right];
      if (clone[l] + clone[right] == target) {
        break;
      }
      if (sum < target) {
        l++;
      } else {
        right--;
      }
    }

    if (l == right) {
      return new int[] {-1, -1};
    }

    int ida = -1, idb = -1;
    for (int i = 0; i < nums.length; i++) { // O(n) in the worse case
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
    return new int[] {
      ida > idb ? idb : ida, ida > idb ? ida : idb,
    };
  }

  public static int[] twoSum3(final int[] nums, int target) {
    int[] clone = nums.clone();
    Arrays.sort(clone); // O(nlogn)

    for (int i = 0; i < clone.length; ++i) { //O(n)
      int j = Arrays.binarySearch(clone, i + 1, clone.length, target - clone[i]); // O(logn)
      if (j >= 0) {
        int ida = -1, idb = -1;
        for (int k = 0; k < nums.length; k++) { //O(n)
          if (nums[k] == clone[i] && ida == -1) {
            ida = k;
            if (idb != -1) {
              break;
            }
            continue;
          }
          if (nums[k] == clone[j] && idb == -1) {
            idb = k;
            if (ida != -1) {
              break;
            }
            continue;
          }
        }

        return new int[] {
          ida > idb ? idb : ida, ida > idb ? ida : idb,
        };
      }
    }
    return new int[] {-1, -1};
  }
}
