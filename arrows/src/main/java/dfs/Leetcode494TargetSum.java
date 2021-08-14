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

package dfs;

import java.util.HashMap;
import java.util.Map;

public class Leetcode494TargetSum {

  /*
     Ask:
      an integer array nums and an integer target.
      adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers
      to build the expression.
      Return the number of different expressions that you can build, which evaluates to target.
     Cases:
       nums = [1,1,1,1,1], target = 3
       Output: 5
       Explanation:
       -1 + 1 + 1 + 1 + 1 = 3
       +1 - 1 + 1 + 1 + 1 = 3
       +1 + 1 - 1 + 1 + 1 = 3
       +1 + 1 + 1 - 1 + 1 = 3
       +1 + 1 + 1 + 1 - 1 = 3

       Input: nums = [1], target = 1
       Output: 1

        1 <= nums.length <= 20
        0 <= nums[i] <= 1000
        0 <= sum(nums[i]) <= 1000
        -1000 <= target <= 1000
     Idea:
        nums is not null, length>=1
        target can be negative
        nums[i] >=0

        recursion
        O(2^N）time.
        O(N) call stack

        + cache, note:  `0 <= sum(nums[i]) <= 1000`
        cache is based on back forward recursion
         O(N*2000）time and space.
          The cache size l∗N has been filled just once.
          l is the range of sum: 2000
          N is nums array size
  */

  public int findTargetSumWays(int[] nums, int target) {
    Map<String, Integer> cache = new HashMap();
    return dfs(nums, 0, target, cache);
  }

  private int dfs(int[] A, int i, int s, Map<String, Integer> c) {
    if (i == A.length) return s == 0 ? 1 : 0;
    String key = i + "-" + s; // value is got from left recursion
    if (!c.containsKey(key)) c.put(key, dfs(A, i + 1, s + A[i], c) + dfs(A, i + 1, s - A[i], c));
    return c.get(key);
  }

  /*
  DP
  E.g.:
  [0,0,0,0,0,0,0,0,1]
  1
  Expected 256
  O(L*N) time and space
          The cache size l∗N has been filled just once.
          l is the range of sum: 2000
          N is nums array size
  It can be improved to 1 D DP to save space to be O(N) space
  */

  public int findTargetSumWays2(int[] A, int S) {
    int[][] dp = new int[A.length][2001];
    dp[0][A[0] + 1000] = 1;
    dp[0][-A[0] + 1000] = dp[0][-A[0] + 1000] + 1;

    for (int i = 1; i < A.length; i++) {
      for (int sum = -1000; sum <= 1000; sum++) {
        int s = sum + 1000;
        if (dp[i - 1][s] > 0) {
          dp[i][s + A[i]] = dp[i][s + A[i]] + dp[i - 1][s];
          dp[i][s - A[i]] = dp[i][s - A[i]] + dp[i - 1][s];
        }
      }
    }
    return S > 1000 ? 0 : dp[A.length - 1][S + 1000];
  }
}
