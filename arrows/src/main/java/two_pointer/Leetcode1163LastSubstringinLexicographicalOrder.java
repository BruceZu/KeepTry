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

package two_pointer;

public class Leetcode1163LastSubstringinLexicographicalOrder {
  /*
    return the last substring of s in lexicographical order.

    1 <= s.length <= 4 * 10^5
    s contains only lowercase English letters.

    Input: s = "abab"
    Output: "bab"

    Input: s = "leetcode"
    Output: "tcode"

    Input: s = "eced"
    Output: "ed"

    Input: s =  "babcbd"
    Output: d

    Input: s = "xxxxaaaaaaxxxxz"
    Output: "z"

    Input: s = xabcdy
    Output: y

    Input: s = dcdcdx
    Output: x

    Input: s = aaaaaaaaax
    Output: x

    Input: s = xxxxxxxxa
    Output: xxxxxxxxa

    Input: s = xabcdefgxabz
    Output: xabz

    Input: s = "vmjtxddvzm
    Output: zm


  Understand:
    The answer is always a suffix of the given string
  */

  /*
  Idea:
    last substring in lexicographical order is always a suffix array
    so need to compare all suffix array.
    Note can skip some suffix array if its index is common parts when comparing 2 other pre
    suffix array
    Details:
    initially i=0,j=1.
      i is always the start index of the result of compared ones,
      j is always the start index of the next not compared one
      l is the length of common prefix of i and j.
        if l is not 0, it means there are some chars are common prefix of 2 suffix array start with i and j
        s[i + l] is same as s[j + l]; then l++;
        s[i + l] is not same as s[j + l]:
         - if (s[i + l] > s[j + l]) { j = j + l + 1; l=0; }
           e.g. xxxxxxxxa? or xabcdefgxaba?
                xxxxxxxx      xabc
          index i             i
                xxxxxxxa      xaba
          index j             j
                xxxxxxa       aba
                xxxxxa        ba
                xxxxa         a
                xxxa          ?
                xxa
                xa
                a
                ?
              j skip some following index and jump to index of ?(any char) to restart compare with i

          - if (s[i + l] < s[j + l]) {
                i = Math.max(i + l + 1, j);
                j = i + 1;
                l=0
             }
            E.g.:
              a> There is overlap: aaaaaaxxxxz
                         aaaaaa
                  index  i
                         aaaaax
                  index  j
                         aaax
                         aax
                         ax
                         x
                       i skip some following index and jump to index of x whose index is i+l+1>=j

              b> There is no overlap xabcdefgxabz
                         xabc
                index    i
                         xabz
                index    j
                       i skip some following index and jump to index j, j>=i+l+1.
           Why j is i+1. is there a way to improve it?
                  seem j=j+l  ? but see `vmjtxddvzm`, l can be 0 then i==j at x it is wrong, j must > i.
                       j=j+l+1? but see `babcbd`
                                  index  012345
                                when i=1, j=2, l=1,
                                next i=2 it is wrong j=4, result is bd, not d

  O(N) time, i, j always move forward, never step back.
     some char can be revisited at most twice, once by l another by j.
     because this only happen when there is no overlap of 2 suffix array starting at index i and j
               e.g.   xabcdefgxabzmn
               index  0123456789
               when   xabc
               index  i
                      xabz, abz has been visited by l.
               index  j
               next:
                    i is at index 8
                    j is at index i+1=9,
                    note 'ab' is less than x, so j will visited ab and skip them
      So at most each char will be visited twice.
    O(1) space
    */
  public static String lastSubstring(String Str) {
    // 1 <= s.length
    char[] s = Str.toCharArray();
    int i = 0, j = i + 1, l = 0, N = s.length;
    while (j + l < N) {
      if (s[i + l] == s[j + l]) {
        l++;
        continue;
      } else if (s[i + l] > s[j + l]) j = j + l + 1;
      else {
        i = Math.max(i + l + 1, j);
        j = i + 1;
      }
      l = 0;
    }
    return Str.substring(i);
  }
}
