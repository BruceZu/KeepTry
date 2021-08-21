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

package string.palindrome;

public class Leetcode866PrimePalindrome {
  /*
  866. Prime Palindrome

  Given an integer n, return the smallest prime palindrome >= n.

  An integer is prime if it has exactly two divisors: 1 and itself.
  Note that 1 is not a prime number.
  For example, 2, 3, 5, 7, 11, and 13 are all primes.

  An integer is a palindrome if it reads the same from left to right as it does from right to left.
  For example, 101 and 12321 are palindromes.

  The test cases are generated so that the answer always exists and is in the range [2, 2 * 108].

  Input: n = 6
  Output: 7

  Input: n = 8
  Output: 11

  Input: n = 13
  Output: 101

  Constraints:  1 <= n <= 10^8
   */
  /*
   2 condition:
   - Palindrome number
   - prime number
   Check:
   single: 2,3,5,7
   double: 11.
   triple: ???
   Observe:
    even length palindrome number is not prime number, except 11
    E.g.: 11, 22, 33; 1221,2112, because `1221=1111+11*10` it is times of 11
    So only care about the odd length number
       'root' is the left wing + the center. use concept `root` to keep the odd length.
        10 of 101
        100 of 10001
       from one odd palindrome to next odd palindrome
        100  -> 10001
        101  -> 10101
        102  -> 10201  ...
        109  -> 10901
        110  -> 11011
        111  -> 11111
        112  -> 11211  ...
        119  -> 11911
        120  -> 12021  ...
        199  -> 19991
        200  -> 20001 ...
        999  -> 99999 ...
       1000  -> 1000001 ...

   'The answer is guaranteed to exist and be less than 2 * 10^8.'
    so use endless loop and break once find the answer
  */
  public int primePalindrome(int N) {
    if (8 <= N && N <= 11) return 11;
    int root = getInitialRoot(N); // 100
    while (true) {
      String r = String.valueOf(root);
      StringBuilder sb = new StringBuilder(r);
      for (int i = r.length() - 2; i >= 0; i--) sb.append(r.charAt(i)); // 10001

      // odd digits palindrome number
      int n = Integer.valueOf(sb.toString());
      if (N <= n && isPrime(n)) return n;
      root++;
    }
  }

  // root of minimum palindrome number of odd digits i
  static int[] root = new int[10];

  static {
    root[1] = 1;
    root[3] = 10; // 101
    root[5] = 100; // 10001
    root[7] = 1000; // 1000001
    root[9] = 10000;
    // 100000001     'The answer is guaranteed to exist and be less than 200000000.'
  }

  private static int getInitialRoot(int N) {
    int l = String.valueOf(N).length();
    // even number length palindrome number: is not prime number
    if ((l & 1) == 0) l++;
    return root[l];
  }
  // assume i is positive integer
  public static boolean isPrime(int N) {
    if (N < 2) return false;
    if (N == 2) return true;
    // N > 2
    if (N % 2 == 0) return false;
    int to = (int) Math.pow(N, 0.5);
    for (int i = 3; i <= to; i += 2) if (N % i == 0) return false;
    return true;
  }
}
