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

import java.util.ArrayList;
import java.util.List;

public class Leetcode1177CanMakePalindromeFromSubstring {
  /*
     a string s and array queries where
     queries[i] = [lefti, righti, ki].
     We may rearrange the substring s[lefti...righti] for each query and
     then choose up to ki of them to replace with any lowercase English letter.

     If the substring is possible to be a palindrome string after the operations above,
     the result of the query is true. Otherwise, the result is false.

     Return a boolean array answer where answer[i] is the result of the ith query queries[i].

     Note that each letter is counted individually for replacement,
     so if, for example s[lefti...righti] = "aaa", and ki = 2, we can only replace two of the letters.
     Also, note that no query modifies the initial string s.



  Example :

  Input: s = "abcda",
  queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
  Output:     [true,false,false,true,true]
  Explanation:
  queries[0]: substring = "d", is palidrome.
  queries[1]: substring = "bc", is not palidrome.
  queries[2]: substring = "abcd", is not palidrome after replacing only 1 character.
  queries[3]: substring = "abcd", could be changed to "abba" which is palidrome. Also this can be changed to "baab" first rearrange it "bacd" then replace "cd" with "ab".
  queries[4]: substring = "abcda", could be changed to "abcba" which is palidrome.

  Input: s = "lyb",
  queries = [[0,1,0],[2,2,1]]
  Output: [false,true]

  Constraints:

      1 <= s.length, queries.length <= 105
      0 <= lefti <= righti < s.length
      0 <= ki <= s.length
      s consists of lowercase English letters.

     */
  /*
   Idea:
     1>
       let x is the number of char whose frequency is odd in the prefix substring.
       x can be 1, 2, 3, 4, 5, ......
       then need at lest to remove even number < x to make it possible to form a palindrome
                0  2  2  4  4, ......
       one operation can remove 2 this kind of char. so the at lest operation number is
                0  1  1  2  2, ......
     2> string is consists of lower english letters
        so use number of an Integer's 1 bit
        to represent number of char whose frequency is odd in prefix sub string

        the ^ operation make it possible to calculate any substring's x value
        from the value of 2 prefix:
         l == 0 ? odds[r] : (odds[l - 1] ^ odds[r]);
       E.g.:  odds of prefix [0, 9] is 101
              odds of prefix [0, 5] is 010
         then odds of [6,9] = 010 ^ 101 = 111
  */
  public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
    int n = s.length();
    int c = 0;
    int[] odds = new int[n];
    // `odds`: its 1 bit number is the counter of char which has odd frequency in prefix sub string
    // [0,i]
    for (int i = 0; i < s.length(); i++) {
      c ^= 1 << s.charAt(i) - 'a';
      odds[i] = c;
    }

    List<Boolean> r = new ArrayList<Boolean>(queries.length);
    for (int i = 0; i < queries.length; i++)
      r.add(f(odds, queries[i][0], queries[i][1], queries[i][2]));
    return r;
  }

  private static boolean f(int[] odds, int l, int r, int k) {
    int mask = l == 0 ? odds[r] : (odds[l - 1] ^ odds[r]); // l-1>=0 so l>=1
    int sum = 0;
    while (mask != 0) {
      mask &= (mask - 1);
      sum++;
    }
    return k >= sum / 2;
  }
}
