//  Copyright 2018 The KeepTry Open Source Project
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

package dp.LongestIncreasingCommonSequence;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/longest-increasing-subsequence/description/">leetcode</a>
 */
public class Leetcode300LongestIncreasingSubsequence {
  /**
   * @param A sorted array
   * @param key
   * @param from and to are scope [from, to] in which to find the index of key's ceil value
   * @param to
   */
  public static int ceilIndex(int[] A, int key, int from, int to) {
    if (from < 0 || to > A.length - 1) return -1;
    if (A == null || A.length == 0) return -1;
    if (key >= A[to]) return -1;
    if (key < A[from]) return from;

    int m;
    int l = from, r = to;

    while (r - l > 1) {
      m = l + (r - l) / 2;

      if (A[m] <= key) l = m;
      else r = m;
    }
    return r;
  }

  // Runtime: O(nlogn) to figure the length
  static int lengthOfLIS(int A[]) {
    if (A == null || A.length == 0) return 0;
    int[] tails = new int[A.length];
    int len;

    tails[0] = A[0];
    len = 1;
    for (int i = 1; i < A.length; i++) { // O(n)
      if (A[i - 1] == A[i]) continue; // continued duplicated number
      if (A[i] < tails[0]) // new smallest value. replace.
      tails[0] = A[i];
      else if (A[i] > tails[len - 1]) // new one.
      tails[len++] = A[i];
      else { //find and replace.
        int ceilIndex = ceilIndex(tails, A[i], 0, len - 1);
        if (tails[ceilIndex - 1] == A[i])
          continue; // make sure no duplicated numbers in increasing sequence
        tails[ceilIndex] = A[i]; // O (logn)
      }
    }
    return len;
  }

  // Runtime O(n^2)
  public static int DP(int[] A) {
    if (A == null || A.length == 0) return 0;
    int[] DP =
        new int[A.length]; //length of longest increasing sequence ending at element with index i
    DP[0] = 1;
    int max = 1; // it is 1 not MIN_VALUE
    // O(n^2)
    for (int i = 1; i < A.length; i++) {
      int v = 1; // initial value of DP[i]
      for (int j = i - 1; j >= 0; j--) {
        if (A[j] < A[i] /*increasing*/) v = Math.max(v, DP[j] + 1);
      }
      DP[i] = v;
      max = Math.max(max, v);
    }
    return max;
  }
  // Other idea of DP is: sort the given array, O(nlogn)
  // and trim duplicated members, O(n), get a new array with length m
  // then using DP solution for LCS, O(n*m)

  // Runtime O(n^2)
  public static void DPWithPrintOneOfResult(int[] A) {
    if (A == null || A.length == 0) return;
    int[] DP =
        new int[A.length]; //length of longest increasing sequence ending at element with index i
    DP[0] = 1;
    int maxLen = 1; // it is 1 not MIN_VALUE
    int maxEndIndex = 0; // using 0 as initial value

    int[] preIndex = new int[A.length];

    // O(n^2)
    for (int i = 1; i < A.length; i++) {
      int v = 1; // initial value of DP[i]
      for (int j = i - 1; j >= 0; j--) {
        if (A[j] < A[i] && DP[j] + 1 > v) {
          v = DP[j] + 1;
          preIndex[i] = j;
        }
      }
      DP[i] = v;
      if (maxLen < v) {
        maxLen = v;
        maxEndIndex = i;
      }
    }
    // print out one
    int[] LIS = new int[maxLen];
    LIS[maxLen - 1] = A[maxEndIndex];
    maxLen--;
    int pre = preIndex[maxEndIndex];
    while (maxLen-- > 0) {
      LIS[maxLen] = A[pre];
      pre = preIndex[pre];
    }
    System.out.println(Arrays.toString(LIS));
  }

  public static void main(String[] args) {
    System.out.println(lengthOfLIS(null) == 0);
    System.out.println(lengthOfLIS(new int[] {}) == 0);
    System.out.println(lengthOfLIS(new int[] {1}) == 1);
    System.out.println(lengthOfLIS(new int[] {1, 2}) == 2);
    System.out.println(lengthOfLIS(new int[] {2, 1}) == 1);
    System.out.println(lengthOfLIS(new int[] {2, 2}) == 1);
    System.out.println(lengthOfLIS(new int[] {2, 2, 2, 2, 3}) == 2);
    System.out.println(lengthOfLIS(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6}) == 6);
    System.out.println(lengthOfLIS(new int[] {10, 9, 2, 5, 3, 7, 101, 18}) == 4);
    System.out.println(lengthOfLIS(new int[] {4, 10, 4, 3, 8, 9}) == 3);
    System.out.println(lengthOfLIS(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12}) == 6);
    // DP
    System.out.println("DP====");
    System.out.println(DP(null) == 0);
    System.out.println(DP(new int[] {}) == 0);
    System.out.println(DP(new int[] {1}) == 1);
    System.out.println(DP(new int[] {1, 2}) == 2);
    System.out.println(DP(new int[] {2, 1}) == 1);
    System.out.println(DP(new int[] {2, 2}) == 1);
    System.out.println(DP(new int[] {2, 2, 2, 2, 3}) == 2);
    System.out.println(DP(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6}) == 6);
    System.out.println(DP(new int[] {10, 9, 2, 5, 3, 7, 101, 18}) == 4);
    System.out.println(DP(new int[] {4, 10, 4, 3, 8, 9}) == 3);
    System.out.println(DP(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12}) == 6);

    // DPWithPrintOneOfResult
    System.out.println("DPWithPrintOneOfResult====");
    DPWithPrintOneOfResult(null);
    DPWithPrintOneOfResult(new int[] {});
    DPWithPrintOneOfResult(new int[] {1});
    DPWithPrintOneOfResult(new int[] {1, 2});
    DPWithPrintOneOfResult(new int[] {2, 1});
    DPWithPrintOneOfResult(new int[] {2, 2});
    DPWithPrintOneOfResult(new int[] {2, 2, 2, 2, 3});
    DPWithPrintOneOfResult(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6});
    DPWithPrintOneOfResult(new int[] {10, 9, 2, 5, 3, 7, 101, 18});
    DPWithPrintOneOfResult(new int[] {4, 10, 4, 3, 8, 9});
    DPWithPrintOneOfResult(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12});
  }
}
