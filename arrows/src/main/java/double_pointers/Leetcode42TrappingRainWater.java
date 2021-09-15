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

package double_pointers;

public class Leetcode42TrappingRainWater {
  /*
      42. Trapping Rain Water
      Given n non-negative integers representing an
      elevation map where the width of each bar is 1,
      compute how much water it can trap after raining.

    Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
    Output: 6
    Explanation:
    The above elevation map (black section) is represented
    by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case,
    6 units of rain water (blue section) are being trapped.


    Input: height = [4,2,0,3,2,5]
    Output: 9

    Constraints:

        n == height.length
        1 <= n <= 2 * 104
        0 <= height[i] <= 105
  */

  /*
    l and r are no calculated index, when l==r also need check
    hl: left hight in [0, i-1], hr: right height in [r+1, length-1]
        initial value are both 0
    from min{hl, hr} side,
    each step only handle one element:  update lh or rh in need or update result
    O(N) time O(1) space
  */
  public int trap(int[] A) {
    int l = 0, r = A.length - 1, a = 0, hl = 0, hr = 0;
    while (l <= r) {
      if (hl < hr) {
        hl = Math.max(hl, A[l]);
        a += hl - A[l];
        l++;
      } else {
        hr = Math.max(hr, A[r]);
        a += hr - A[r];
        r--;
      }
    }
    return a;
  }
}
