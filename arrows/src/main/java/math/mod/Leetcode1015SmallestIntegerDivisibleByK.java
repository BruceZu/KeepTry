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

package math.mod;

import java.util.HashSet;
import java.util.Set;

public class Leetcode1015SmallestIntegerDivisibleByK {
  public int smallestRepunitDivByK(int K) {
    // 1 <= K <= 10^5
    int e = K % 10;
    if (e == 2 || e == 4 || e == 5 || e == 6 || e == 8) return -1;
    // k's end number is [1,3,7,9]
    // The reminder part in calculating next number in sequence
    // '1 <= K <= 10^5'
    int l = 1, r = 1;
    // Set<Integer> period = new HashSet();
    // "n may not fit in a 64-bit signed integer" so involved in modula operation
    while (l <= K) {
      if (r % K == 0) return l;
      // period.add(r);
      // next
      r = (r * 10 + 1) % K;
      // if (period.contains(r)) return -1;
      l++;
    }
    // System.out.println(K+" has not satisfied result");
    return -1;
  }
}
