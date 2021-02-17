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

public class Leetcode1025DivisorGame {
  public boolean divisorGame(int N) {
    boolean[] dp = new boolean[N + 1]; // default false
    //  1 <= N <= 1000
    for (int n = 2; n <= N; n++) {
      // 'assuming both players play optimally.'
      // This means: even success and odd failed.
      // try to let other get and odd number.
      // For a odd number in my hand, its factors are all odd
      //  odd-odd=even
      // For an even number in my hand, its factors can be even and odd
      //  use even factors only to let other have even-odd=odd
      //
      // ' Choosing any x with 0 < x < N and N % x == 0.
      for (int i = 1; i < n; i++) {
        if (n % i == 0) {
          //   Replacing the number N on the chalkboard with N - x.'
          if (dp[n - i] == false) { // Bob
            dp[n] = true; // Alice(me)
            break;
          }
        }
      }
    }
    return dp[N];
  }

  public boolean divisorGame2(int N) {
    return N % 2 == 0;
  }
}
