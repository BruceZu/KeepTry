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

public class Leetcode1029TwoCityScheduling {

  // Return the minimum cost to fly every person to a city such that exactly n people arrive in each
  // city.
  public int twoCitySchedCost(int[][] costs) {
    /*
    2 * n == costs.length
    2 <= costs.length <= 100
    costs.length is even.
    1 <= aCosti, bCosti <= 1000
    */
    int ac = 0;
    int N = costs.length;
    int diff[] = new int[N];
    for (int i = 0; i < N; i++) {
      int[] c = costs[i];
      ac += c[0];
      diff[i] = c[1] - c[0]; // B city cost -A city cost
    }
    // Greedy
    Arrays.sort(diff);
    for (int i = 0; i < N / 2; i++) {
      ac += diff[i];
    }
    return ac;
  }
}
