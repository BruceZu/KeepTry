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

/**
 *
 *
 * <pre>
 * Difficulty: Hard
 * Given an unsorted integer array, find the first missing positive integer.
 *
 * For example,
 * Given [1,2,0] return 3,
 * and [3,4,-1,1] return 2.
 *
 * Your algorithm should run in O(n) time and uses constant space.
 *
 * Subscribe to see which companies asked this question
 *
 * Hide Tags Array
 * Hide Similar Problems (M) Missing Number (H) Find the Duplicate Number
 *
 * Idea 1 :
 *    the missing integer is "positive"
 *    O(n) time
 *    constant space -> can use length = Integer.MAX_VALUE. ( some JVM  not supported)
 *
 *    using a constant array 'cpn' to keep consequent positive numbers from 1 to max
 *
 *
 *
 *    Note:
 *     "the fist missing" does NOT mean continue positive numbers
 *
 *     1 when the input nums is null and {}.
 *       Also note in this case the answer should be 1 not 0
 *       o is not positive number.
 *
 *     2 cpn length:
 *       max + 1:  array index from 0;
 *       max + 1 +1 : for the case  Given [1,2,0] return 3.  the max +1 is the answer.
 *
 *    Cons:  it is not constant space;  depends on max
 *
 *
 * </pre>
 */
public class Leetcode41FirstMissingPositive {
  /**
   * <pre>
   *
   *  Idea:
   *       1 filter
   *          as find the positive number, the positive number starts from 1, 2 ,3 ,....
   *          So firstly from the array filter out negative, 0 , bigger that n by replacing them with n+1, or
   *          other number out of 1 ~ n.
   *
   *       2 mark.
   *          now the room of array maybe just
   *          index 0, 1, 2, ... n-1
   *             {1, 2, 3,  ... n}, then the answer is n+1
   *          else mapping the left number to index of number -1 by marking the sign of num[number -1] as negative.
   *          Note:
   *               maybe the current number is already be marked as negative. so need check its abs value.
   *               maybe the num[abs(number)-1] is already be marked as negative. so need check it to avoid repeat marking
   *          after marking the sign. also reach a aim of sorting them in order.
   *
   *       3 check
   *           So now check from left to right to find the first positive
   *
   *
   *  index:     0,  1  ... ,  n-2,   n-1   |  length = n
   *
   *           { 1,  2, ... ,   n-1 ,   n  }
   *
   *  1 filter those elements out of the room:
   *       1   e <=0
   *       2   e > nums.length
   *  2 mark
   *     index:     0   1,  2...  n-2,  n-1    n
   *                   /   /     /     /     /
   *               { 1,  2, ... ,   n-1 ,   n  }
   *
   *  3 check.
   *   Note:
   *      1 Also care those repeated e;
   *      2 when nums is null or empty
   *   Test case:
   *   {1, 9, 11, 12}
   *   {2, 1}
   *   {2, 2}
   *   null
   *   {}
   *   {1}
   *   {-8}
   *   {9}
   *   e.g.
   *    index  0   1  2  3  4  5   6 7
   *         {-2 , 0, 1, 7, 6, 12}
   *
   *    index 0    1  2  3  4   5   6 7
   *   ->    {7,   7, 1, 7, 6,  7}
   *
   *    index  0   1  2  3   4   5   6 7
   *   ->    {-7,  7, 1, 7,  7, -7}
   *   ->          2
   *
   * e.g.
   *    index 0 1 2
   *         {1}
   *
   *    index 0 1 2
   *         {1}
   *
   *    index  0 1 2
   *         {-1}
   *     ->2
   */
  public static int firstMissingPositive(int[] nums) {
    if (nums == null) {
      return 1;
    }
    int n = nums.length;
    int MARK = n + 1; // n+1 maybe bigger that Integer.MAX_VALUE

    for (int i = 0; i < n; i++) {
      int v = nums[i];
      if (v <= 0 || v > n) {
        nums[i] = MARK;
      }
    }

    for (int i = 0; i < n; i++) {
      int absv = Math.abs(nums[i]);
      if (absv != MARK /* can */ && nums[absv - 1] > 0 /* no repeat update sign */) {
        nums[absv - 1] = -1 * nums[absv - 1];
      }
    }

    for (int i = 0; i < n; i++) {
      int v = nums[i];
      if (v > 0) {
        return i + 1;
      }
    }
    return n + 1;
  }

  public int firstMissingPositive2(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 1;
    }

    int max = nums[0];
    for (int i = 0; i < nums.length; i++) {
      int v = nums[i];
      if (max < v) {
        max = v;
      }
    }

    int[] cpn = new int[max + 1 + 1]; //continue Positive number from 1 to max.
    for (int i = 0; i < nums.length; i++) {
      int v = nums[i];
      if (v > 0) {
        cpn[v] = 1;
      }
    }
    for (int i = 1; i < cpn.length; i++) {
      if (cpn[i] == 0) {
        return i;
      }
    }
    return -1;
  }
}
