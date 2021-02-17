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

import java.util.HashMap;

public class Leetcode1027LongestArithmeticSubsequence {
  public int longestArithSeqLength(int[] A) {
    // the length of the longest arithmetic subsequence in A.
    // a sequence B is arithmetic if B[i+1] - B[i] are all the same value
    // (for 0 <= i < B.length -  1).

    //  2 <= A.length <= 1000
    //  0 <= A[i] <= 500
    int result = 0;
    int N = A.length;
    int dp[][] = new int[N][1000 + 1]; // diff value range is [-500, 500], default 0.
    // dp[i][j] is the length, of longest arithmetic sequence which end at i and the diff value is
    // j, minus 1. understand it from dp[1][x]

    for (int i = 1; i < N; i++) {
      for (int j = 0; j < i; j++) {
        int diff = A[i] - A[j] + 500;
        dp[i][diff] = dp[j][diff] + 1;
        result = Math.max(result, dp[i][diff]);
      }
    }
    return result + 1;
  }
}
