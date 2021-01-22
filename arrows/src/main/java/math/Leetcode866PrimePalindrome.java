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

package math;

public class Leetcode866PrimePalindrome {

  public int primePalindrome(int N) {
    // Even digits palindrome number is divisible by 11, so they are not prime, exception is 11.
    if (8 <= N && N <= 11) return 11;
    // Odd digit palindrome number. checking by root.
    //  'The answer is guaranteed to exist and be less than 2 * 10^8.'
    //   so use endless loop and break once find the answer
    for (int r = getInitialRoot(N); ; r++) { // let r start from 1 is okay
      String l = String.valueOf(r);
      StringBuilder sb = new StringBuilder(l);
      for (int index = l.length() - 2; 0 <= index; index--) {
        sb.append(l.charAt(index));
      }

      // odd digits palindrome number
      int n = Integer.valueOf(sb.toString());
      if (N <= n && isPrime(n)) {
        return n;
      }
    }
  }

  // '1 <= N <= 10^8'
  // 'The answer is guaranteed to exist and be less than 2 * 10^8.'
  //  so use int array is enough
  // root of minimum palindrome number of odd digits i
  static int[] mRoot = new int[10];

  static {
    mRoot[1] = 1;
    mRoot[3] = 10; // 101
    mRoot[5] = 100; // 10001
    mRoot[7] = 1000; // 1000001
    mRoot[9] = 10000; // 100000001
  }

  private static int getInitialRoot(int N) {
    int l = String.valueOf(N).length();
    if ((l & 1) == 0) {
      l++;
    }
    // even N is one of the minimum palindrome number, it could not be prime
    return mRoot[l];
  }
  // assume i is positive integer
  public static boolean isPrime(int N) {
    if (N < 2) return false;
    if (N == 2) return true;
    // N > 2
    if (N % 2 == 0) return false;
    int to = (int) Math.pow(N, 0.5);
    for (int i = 3; i <= to; i += 2) {
      if (N % i == 0) {
        return false;
      }
    }
    return true;
  }
}
