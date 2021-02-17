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

package dp;

import java.util.Arrays;

public class Leetcode1024VideoStitching {

  //  Greedy O(NlogN)
  public static int videoStitching1(int[][] clips, int T) {
    /*
     * ' 1 <= clips.length <= 100
     *   0 <= clips[i][0] <= clips[i][1] <= 100
     *   0 <= T <= 100'
     *   So T can be 0
     *  'int[][] clips' means clips[i][0] and clips[i][1] is integer
     *  'int T' means T is integer too
     */
    int N = clips.length; // N>=1
    Arrays.sort(clips, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    if (T == 0) return 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    if (0 < clips[0][0]) return -1; // with above line, this line can be saved.  clips start from 0.
    // no way to know the reached right most boundary
    // previous interval right bounder and extended right most position by far.
    // em initiated -1 means it is virtual interval to make sure the first interval [0, x] is
    // calculated when T is 0.
    // clips[0][0] is 0 now. Calculate the left and right bounder of the continued intervals from
    // the fist one of the  sorted intervals.
    int result = 0, pr = 0, em = -1, i = 0;
    while (true) {
      // overlap. e.g. 'We can cut these clips into segments freely: for example,
      // a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].'
      while (i < N && clips[i][0] <= pr) {
        em = Math.max(em, clips[i][1]);
        i++;
      }
      if (i == N) return em >= T ? ++result : -1;
      if (pr == em) return -1; // preview em is checked and it is not reach T
      if (em >= T) return ++result;
      // next
      pr = em;
      result++;
    }
  }

  // Greedy O(NlogN) without comments
  public static int videoStitching2(int[][] clips, int T) {
    int N = clips.length;
    Arrays.sort(clips, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    if (T == 0) return 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    if (0 < clips[0][0]) return -1; // with above line, this line can be saved
    int result = 0, pr = 0, em = -1, i = 0;
    while (true) {
      while (i < N && clips[i][0] <= pr) {
        em = Math.max(em, clips[i][1]);
        i++;
      }
      if (i == N) return em >= T ? ++result : -1;
      if (pr == em) return -1;
      if (em >= T) return ++result;
      pr = em;
      result++;
    }
  }

  // Greedy O(N) time and space
  public static int videoStitching3(int[][] clips, int T) {
    // 'int[][] clips' means clips[i][0] and clips[i][1] is integer
    // 'int T' means T is integer too
    // Either of 1> Use `Integer dp[]` to check null or 2> use  new int[T+1] is not
    // good as  `new int[101]`.
    int[] vcs = new int[101]; // valid clips
    // O(N). valid clips maybe not continuous, so no way to know whether the default value like
    // vcs[0]=0, vcs[x]=0 is existing clips or not. Des it matter? No
    // When T=0, return 0. So we do not care whatever the clips is.
    // When T is not 0. As T is integer too. Any valid clips start from and end at x, a point, does
    // not affect the result.
    for (int[] i : clips) vcs[i[0]] = Math.max(vcs[i[0]], i[1]);
    if (T == 0) return 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    // result=0 means a status before calculation. value 0 does not have any meaning. result should
    // be -1 or any number >=1 at last.
    int i = 0, result = 0, pr = 0, em = -1;
    while (true) {
      while (i < T + 1 && i <= pr) {
        em = Math.max(em, vcs[i]);
        i++;
      }
      // note for input is [[0, 0]], 0. now the em==pr==0.
      if (i == T + 1) return em >= T ? ++result : -1;
      if (em == pr) return -1;
      if (em >= T) return ++result;

      pr = em;
      result++;
    }
  }

  // Greedy O(N) time and space. No comments version
  public static int videoStitching4(int[][] clips, int T) {
    int[] vcs = new int[101];
    for (int[] i : clips) vcs[i[0]] = Math.max(vcs[i[0]], i[1]);
    if (T == 0) return 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    int i = 0, result = 0, pr = 0, em = -1;
    while (true) {
      while (i < T + 1 && i <= pr) {
        em = Math.max(em, vcs[i]);
        i++;
      }
      if (i == T + 1) return em >= T ? ++result : -1;
      if (em == pr) return -1;
      if (em >= T) return ++result;

      pr = em;
      result++;
    }
  }
  // DP O(100*N)
  public static int videoStitching5(int[][] clips, int T) {
    /*
     * ' 1 <= clips.length <= 100
     *   0 <= clips[i][0] <= clips[i][1] <= 100
     *   0 <= T <= 100'
     *   So T can be 0
     *  'int[][] clips' means clips[i][0] and clips[i][1] is integer
     *  'int T' means T is integer too
     */
    if (T == 0) return 0;
    // Either of 1> Use `Integer dp[]` to check null or 2> use  new int[T+1] is not
    // good as  `new int[101]`.
    int[] vcs = new int[101];
    for (int[] i : clips) vcs[i[0]] = Math.max(vcs[i[0]], i[1]);

    // dp[i] value is the minimum number of clips to cover T=i.
    // dp[T] should <= T.

    int dp[] = new int[101]; // some valid clip can be have a right bounder > T
    Arrays.fill(dp, T + 1);
    dp[0] = 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    for (int i = 0; i <= T; i++) {
      for (int j = i + 1; j <= vcs[i]; j++) {
        dp[j] = Math.min(dp[j], dp[i] + 1);
      }
    }
    return dp[T] == T + 1 ? -1 : dp[T];
  }

  // DP O(100*N) without comments version
  public static int videoStitching(int[][] clips, int T) {
    if (T == 0) return 0;
    int[] vcs = new int[101];
    for (int[] i : clips) vcs[i[0]] = Math.max(vcs[i[0]], i[1]);
    int dp[] = new int[101];
    Arrays.fill(dp, T + 1);
    dp[0] = 0;
    // From 1 to T
    for (int i = 1; i <= T; i++) {
      for (int j = 0; j < i; j++) {
        if (i <= vcs[j]) dp[i] = Math.min(dp[i], dp[j] + 1);
      }
    }
    return dp[T] == T + 1 ? -1 : dp[T];
  }

  public static void main(String[] args) {
    System.out.println(videoStitching(new int[][] {new int[] {2, 4}}, 0) == 0);
    System.out.println(videoStitching(new int[][] {new int[] {0, 0}}, 0) == 0);
    System.out.println(
        videoStitching(
                new int[][] {
                  new int[] {0, 4},
                  new int[] {2, 8}
                },
                5)
            == 2);

    System.out.println(
        videoStitching(
                new int[][] {
                  new int[] {0, 2},
                  new int[] {4, 6},
                  new int[] {8, 10},
                  new int[] {1, 9},
                  new int[] {1, 5},
                  new int[] {5, 9}
                },
                10)
            == 3);
  }
}
