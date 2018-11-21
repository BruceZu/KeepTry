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

/** @see <a href="https://leetcode.com/problems/remove-element/">leetcode</a> */
public class Leetcode27RemoveElement {

  /** Try two pointers. */
  public static int removeElement2(int[] nums, int val) {
    int forNext = 0;
    int i = 0;
    while (i < nums.length) {
      int current = nums[i];
      if (current != val) {
        nums[forNext++] = nums[i];
      }
      i++;
    }
    return forNext;
  }

  /**
   * Did you use the property of "the order of elements can be changed"? What happens when the
   * elements to remove are rare?
   */
  public static int removeElement(int[] nums, int val) {
    int valIndex = -1;
    int otherIndex = -1;
    int findValFrom = 0;
    int findOtherFrom = nums.length - 1;

    while (true) {
      for (int i = findValFrom; i <= findOtherFrom; i++) {
        if (nums[i] == val) {
          valIndex = i;
          break;
        }
      }
      if (valIndex == -1) {
        return findOtherFrom + 1;
      }

      for (int i = findOtherFrom; i > valIndex; i--) {
        if (nums[i] != val) {
          otherIndex = i;
          break;
        }
      }
      if (otherIndex == -1) {
        return valIndex;
      }

      nums[valIndex] ^= nums[otherIndex];
      nums[otherIndex] ^= nums[valIndex];
      nums[valIndex] ^= nums[otherIndex];

      findValFrom = valIndex + 1;
      findOtherFrom = otherIndex - 1;
      valIndex = -1;
      otherIndex = -1;
    }
  }
}
