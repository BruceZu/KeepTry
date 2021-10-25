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
            compare all suffix substrings.
            last substring in lexicographical order is always a suffix array
            so need to compare all suffix array.
            Observe: some suffices array can be skipped if its index belongs the common parts when comparing 2 other previous
            suffix array
            Details:
            initially i=0,j=1.
              i is always the start index of the last sub-string in lexicographical order by far
              j is always the start index of the current suffix to be compared
              l is the length of common prefix of i and j.
                if l is not 0, it means there are some chars are common prefix of 2 suffix array start with i and j and
                s[i] is the max char in the common prefix
              operation:
               if s[i + l] is same as s[j + l]; then l++;
               else
                 - if (s[i + l] > s[j + l]) { j = j + l + 1; l=0; }
                   e.g. xabcxaba
                        i   j
                        l=3
                        x=a[i]>= any value in scope a[index i...j+l]

                  -else if (s[i + l] < s[j + l])
                    E.g.:
                      a> s[j+l] > s[i]
                                 a a a a a a z
                          index  i j
                          next:
                             i=j+l then j=i+1, l=0;

                      b>   s[j+l] > s[i+l] but  s[j+l] <=s[i]
       i+l<j         ----------------------   （1）
       let i=j.then j=j+l+1, l=3;
                           xabaaxaby
                           i    j
                         next
                                i   j
                      another case:
                           "cacb"
                            i j
                              cacb
                            l=1
                         next:
                            "cacb"
                               i j

      i+l>j         ----------------------   （2）
      let period=j-i;
      let i=j+(l/p)*p
      then j=j+l+1, l=4;
                           "m a a m a a m b"
                            i     j
                                  m a a m b
                           "m a a m a a m a b"
                          next          i     j

     Note when i+l ==j, then only there is s[i+l]==s[j]
       m a m a a
           m a a

    O(N) time,  watch l or j always move forward, never step back.
    O(1) space

  */
  public static String lastSubstring(String Str) {
    char[] s = Str.toCharArray();
    int i = 0, j = i + 1, l = 0, N = s.length;
    while (j + l < N) {
      if (s[i + l] == s[j + l]) {
        l++; // O(N)
        continue;
      } else {
        if (s[i + l] <= s[j + l]) {
          // find new last substring in lexicographical order
          if (s[j + l] > s[i]) i = j + l;
          else { // 2 scenarios: i+l+1 < j or i+l+1 > j
            int p = j - i;
            i = j + (l / p) * p;
          }
        }
        j = j + l + 1; // O(N)
        l = 0;
      }
    }
    return Str.substring(i);
  }
}
