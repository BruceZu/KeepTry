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

public class Leetcode1049LastStoneWeightII {
  /*
    1 <= stones.length <= 30
    1 <= stones[i] <= 100
    A Idea:
      let f(group[0,i]) as 'the smallest possible weight of the left stone' in group made by
      stones[0,i]
       f(group([0,i])) =min{ |f(group([0,i]-j)-stones[j]|). j is in [0,i], j has i+1 options
      If going ahead with this way, it is need to represent the sub group.
      Note '1 <= stones.length <= 30'. So any sub group can be represented with
      a binary value from 1 to (2^31)-1
      Its runtime is O(N*2^31)

    B Idea:
     think over more times on the scenarios
     - '
        If x == y, both stones are destroyed, and
        If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
        '
     - 'Return the smallest possible weight of the left stone'

     When select one stone. nothing will happen.
     When select 2 stones. diff weights is left.
     When there are total 3 stones. a<b<c. The answer comes from  a + b vs c
     Where there is another stone left. put it on the other side than having left.
     always keep another stone on the side without left.
     When the last stone is located. we find 2 lines and want the last diff left
     are as small as possible. Then the question is can be converted to divided all
     stones to 2 groups and make the diff of these 2 groups bo be as small as possible
     it is same as try best to select stones to make their weights number near
     but not get over the half of total weights.
     Thus the question is converted to a 0-1 backpack

  Meaning of dp[]
      int[] dp = new int[M + 1];
      Alternative is to use boolean which is for calculating if stones, from start till now,
      can just make i wights for dp[i]. This way will require calculating the diff at last by
      checking dp[i] from the end to start in another loop.

      dp[i] is the max weights stones selected from of [0,current] can make
      for a container which at most can have i weights.
      Initial value: as it is not required to just make the target i weights.
                     So let any dp[i] to be 0

     O(N*M) M is the half of total weights
   */
  public int lastStoneWeightII(int[] stones) {
    int N = stones.length;
    int sumw = 0;
    for (int i = 0; i < N; i++) {
      int w = stones[i];
      sumw += w;
    }
    int M = sumw / 2; // Note it is not 30*1500/2
    int[] dp = new int[M + 1]; // default 0
    for (int i = 0; i < N; i++) {
      int w = stones[i];
      for (int j = M; j >= 0; j--) { // o-1 backpack: back forwarding
        if (j - w >= 0) dp[j] = Math.max(dp[j], dp[j - w] + w);
      }
    }
    return sumw - 2 * dp[M];
  }
}
