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

package greedy;

import java.util.Arrays;
import java.util.Comparator;

public class Leetcode1326MinimumNumberofTapstoOpentoWateraGarden {

  /*
    1326. Minimum Number of Taps to Open to Water a Garden
    a one-dimensional garden on the x-axis.

    The garden starts at the point 0 and ends at the point n.
    (i.e The length of the garden is n).

    n + 1 taps located at points [0, 1, ..., n] in the garden.

    Given an integer n and an integer array ranges of length n + 1 where
    ranges[i] (0-indexed) means the i-th tap can water the area [i - ranges[i], i + ranges[i]]

    Return the minimum number of taps that should be open to water the whole garden,
    If the garden cannot be watered return -1.


    Input: n = 5, ranges = [3,4,1,1,0,0]
    Output: 1

    Input: n = 3, ranges = [0,0,0,0]
    Output: -1


    Input: n = 7, ranges = [1,2,1,0,2,1,0,1]
    Output: 3


    Input: n = 8, ranges = [4,0,0,0,0,0,0,0,4]
    Output: 2


    Input: n = 8, ranges = [4,0,0,0,4,0,0,0,4]
    Output: 1


      1 <= n <= 10^4
      ranges.length == n + 1
      0 <= ranges[i] <= 100

  */

  /*
  Greedy
    sort(O(NlogN)) ranges by start in ascending order and end in descending order
    -------------(selected)
    ------
    ---
       ----------------( do not select this)
       ------
       ---
              ----------------(selected)
              ------

     O(NlogN) time, O(1） space

     r: rightmost point can be reached with it to hook next batch and from next batch select next right most
     nr: next rightmost
     need cover all garden;
     need check r, nr and current tap left point
     once the last batch intervals is over.
     current tap belongs to next batch from which to select longest one to hook with current
     rightmost point
     to make sure cover all garden with lest taps
  */
  public static int minTapsOriginalGreedy2(int n, int[] ranges) {
    if (n <= 0 || ranges == null || ranges.length == 0) return 0;

    int N = ranges.length;
    int[][] A = new int[ranges.length][2];
    for (int i = 0; i < N; i++) A[i] = new int[] {Math.max(0, i - ranges[i]), i + ranges[i]};
    Arrays.sort(A, Comparator.comparingInt(a -> a[0]));

    int answer = 0;
    int r = 0, nr = 0;
    for (int i = 0; i < A.length; i++) {
      int[] c = A[i];
      if (c[0] <= r) {
        if (c[1] > nr) nr = c[1];
      } else {
        if (nr != r) answer++;
        if (nr >= n) return answer;
        r = nr;
        // last batch is end, start a new batch
        if (r < c[0]) return -1;
        else i--;
      }
    }

    if (nr != r) answer++;
    if (nr < n) return -1;
    return answer;
  }
  /* 2 alternative
   O(NlogN) -> O(N) time:
   current special scenario enable using an array, length n+1,
   to represent each tap interval.
     -  index: start point tap interval
     -  value: the right most one of intervals whose start point are same at index of array.

  */
  public static int minTapsOriginalGreedy(int n, int[] ranges) {
    if (n <= 0 || ranges == null || ranges.length == 0) return 0;

    int[] A = new int[n + 1];
    for (int i = 0; i <= n; i++) A[Math.max(0, i - ranges[i])] = i + ranges[i];


    int answer = 0;
    int r = 0, nr = 0;
    for (int i = 0; i <=n; i++) {
      if (i <= r)  {
        if (A[i] > nr) nr = A[i];
      } else {
        if (nr != r) answer++;
        if (nr >= n) return answer;
        r = nr;

        if (r < i) return -1;
        else i--;
      }
    }

    if (nr != r) answer++;
    if (nr < n) return -1;
    return answer;
  }

  /*DP ==========================================================================
   DP
   dp[i] is the minimum number of taps to water [0, i].
   Initialize dp[i] with max = n + 2
   Note: dp[0] = [0] need no tap to water nothing.

   Find the leftmost point of garden to water with tap i.
   Find the rightmost point of garden to water with tap i.
   We can water [left, right] with one tap and water [0, left - 1] with dp[left - 1] taps.

  O(NR) time, R is max ranges[i] <= 100; O(N) space
    */
  public int minTaps3(int n, int[] A) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, n + 2);
    dp[0] = 0; // !!
    for (int i = 0; i <= n; i++) {
      int l = Math.max(0, i - A[i]), r = Math.min(n, i + A[i]); // valid scope of current tap
      for (int j = l + 1; j <= r; j++) dp[j] = Math.min(dp[j], dp[l] + 1);
    }
    return dp[n] < n + 2 ? dp[n] : -1;
  }
}
