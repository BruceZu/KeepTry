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

package doublepoint;

public class Leetcode1781SumofBeautyofAllSubstrings {
  /*
      1781. Sum of Beauty of All Substrings
      The beauty of a string is the difference in frequencies
      between the most frequent and least frequent characters.

      "abaacc" Beauty is 3 - 1 = 2.

      Given a string s, return the sum of beauty of all of its substrings.

      Input: s = "aabcb"
      Output: 5
      Explanation: The substrings with non-zero beauty are
       ["aab","aabc","aabcb","abcb","bcb"], each with beauty equal to 1.

      Input: s = "aabcbaa"
      Output: 17

      Constraints:

          1 <= s.length <= 500
          s consists of only lowercase English letters.
  */

  /* Idea:
  1. use `int frequency[26]` and max to trace max frequency
  2. based on it use extra  `int num[frequency]` and `int min` to trace the min
  num[frequency] is the number of char with the same frequency
  num[0] = 26;
  crucial part:  min initial value is 1, not 0

  O(N^2) time O(1) space, actually it is restricted by the String length limitation
  */
  public static int beautySum(String s) {
    int N = s.length();
    int sum = 0;
    for (int i = 0; i < N; i++) {
      int[] f = new int[26]; //  trace the max
      int max = 1;
      // 1 <= s.length <= 500;
      int[] num = new int[500];
      num[0] = 26; // initial value when window has not any char
      int min = 1; //  crucial part

      for (int j = i; j < N; j++) {
        int c = s.charAt(j) - 'a';
        // before add current char frequency
        num[f[c]]--;
        if (num[f[c]] == 0 && min == f[c]) min = f[c] + 1; //  crucial part
        // add
        f[c]++;
        if (f[c] > max) max = f[c];
        // after add
        num[f[c]]++;
        if (f[c] == 1 && min != 1) min = 1;
        //
        sum += max - min;
      }
    }
    return sum;
  }

  /*
  When the string is very long
  the num[] is not good to allocate memory
  Improve the perfomance but not change the O(N^2) time:
     only do it when increase the count that equals the min count, or less the min count
     E.g. "ababc"      index  max min
           a:1          0     1   1
           a:1 b:1      1     1   1
           a:2 b:1      2     2   1
           a:2 b:2      3     2   2
           a:2 b:2 c:1  4     2   1

    Note x:0 are skipped,  this is crucial part
   */
  public static int beautySum2(String s) {
    int r = 0;
    for (int i = 0; i < s.length(); i++) {
      int[] f = new int[26];
      int min = 0, max = 0;
      for (int j = i; j < s.length(); j++) {
        int v = s.charAt(j) - 'a';
        f[v]++;
        max = Math.max(max, f[v]);
        if (min >= f[v] - 1) { // if (min == f[v] - 1||f[v]==1) {
          min = f[v];
          for (int k = 0; k < 26; ++k) if (f[k] != 0) min = Math.min(min, f[k]);
        }
        r += max - min;
      }
    }
    return r;
  }
}
