//  Copyright 2020 The KeepTry Open Source Project
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

/**
 * <pre>
 * You have an initial power P, an initial score of 0 points, and a bag of tokens.
 *
 * Each token can be used at most once, has a value token[i], and has potentially two ways to use it.
 *
 * If we have at least token[i] power, we may play the token face up, losing token[i] power, and gaining 1 point.
 * If we have at least 1 point, we may play the token face down, gaining token[i] power, and losing 1 point.
 * Return the largest number of points we can have after playing any number of tokens.
 */
public class Leetcode948BagofTokens {

  /** why it is greedy. Buy at the cheapest and sell at the most expensive. */
  public int bagOfTokensScore(int[] tokens, int P) {
    if (tokens == null || tokens.length == 0) {
      return 0;
    }
    int res = 0, s = 0, l = 0, r = tokens.length - 1;
    Arrays.sort(tokens); // need not reassign
    while (l <= r) {
      while (l <= r && P >= tokens[l]) { // check l<=r firstly
        P -= tokens[l++];
        s++;
        res = Math.max(res, s); // ever max
      }
      if (s == 0 || l > r) break;
      P += tokens[r--];
      s--;
    }
    return res;
  }
}
