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

package doublepoint;

import java.util.Stack;

/**
 * <pre>
 *
 * 42. Trapping Rain Water
 * Difficulty: Hard
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 *
 * For example,
 * Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 *
 *
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water (blue section) are being trapped.
 *
 * Tags
 *      Array
 *      Stack
 *      Two Pointers
 * Similar Problems
 *      (M) Container With Most Water
 *      (M) Product of Array Except Self
 *      ========================================================================
 *      Cases:
 *      4, 2, 0, 3, 2, 4, 3, 4 -> 10
 *      0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 -> 6
 *      2, 1, 0, 2 -> 3
 *      5, 5, 1, 7, 1, 1, 5, 2, 7, 6 -> 9
 *      4, 2, 3 -> 1
 *      Idea: compare value of l and r to decide go from next or pre.
 *            if next or pre can contain water, can calculate it
 *            else update l or r.
 *      Note:
 *            once l and r is updated, need compare them again to decide to use next or pre.
 *            always make sure the next and pre is valid
 */
public class Leetcode42TrappingRainWater {
  public int trap(int[] height) {
    if (height == null || height.length <= 2) return 0;
    // at least we have 3 elements
    int l = 1, r = height.length - 2;
    int lh = height[0], rh = height[height.length - 1];
    int result = 0;
    while (l <= r) { // '==' also need need calculate
      if (lh <= rh) { // calculate from l side
        int lv = height[l];
        if (lh > lv) result += lh - lv; // there is water
        else lh = lv; // no water
        l++; // next on in loop
      } else { // calculate from r side
        int rv = height[r];
        if (rh > rv) result += rh - rv; // there is water
        else rh = rv; // no water
        r--; // next on in loop
      }
    }
    return result;
  }

  // using stack
  public static int trap2(int[] height) {
    if (height == null || height.length <= 2) return 0;

    int result = 0;
    Stack<Integer> s = new Stack(); // Height Index, descending from bottom Up,
    for (int i = 0; i < height.length; i++) {
      int iv = height[i];
      while (!s.isEmpty() && iv > height[s.peek()]) {
        int top = s.pop();
        if (!s.isEmpty()) { // 三缺一不存水.
          int width = i - s.peek() - 1;
          int wh = Math.min(iv, height[s.peek()]) - height[top];
          result += width * wh;
        }
      }
      s.push(i);
    }

    return result;
  }
}
