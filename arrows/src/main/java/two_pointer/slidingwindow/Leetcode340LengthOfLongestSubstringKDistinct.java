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

package two_pointer.slidingwindow;

import java.util.HashMap;

/*
Leetcode 340. Longest Substring with At Most K Distinct Characters

Given a string s and an integer k, return the length of the longest substring of
s that contains at most k distinct characters.


Input: s = "eceba", k = 2
Output: 3
Explanation: The substring is "ece" with length 3.
Example 2:

Input: s = "aa", k = 1
Output: 2
Explanation: The substring is "aa" with length 2.


Constraints:
1 <= s.length <= 5 * 104
0 <= k <= 50
*/

public class Leetcode340LengthOfLongestSubstringKDistinct {

  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int l = 0;
    int Len = 0;
    HashMap<Character, Integer> map = new HashMap();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      map.put(c, map.getOrDefault(c, 0) + 1);
      if (map.keySet().size() > k) {
        while (map.keySet().size() != k) {
          char lc = s.charAt(l);
          map.put(lc, map.get(lc) - 1);
          if (map.get(lc) == 0) map.remove(lc);
          l++;
        }
      }
      Len = Math.max(Len, i - l + 1);
    }
    return Len;
  }

  public int lengthOfLongestSubstringKDistinct2(String s, int k) {
    // ASCII printable code chart  [32 - 126]
    // new int [256] do not affect while in this case
    int[] distinctCharCount = new int[256];
    int num = 0, i = 0, maxSubStrLength = 0;

    for (int j = 0; j < s.length(); j++) {
      char currentChar = s.charAt(j);
      if (distinctCharCount[currentChar] == 0) {
        num++;
      }
      distinctCharCount[currentChar]++;

      // update startIndex
      if (num > k) {
        while (true) {
          char startChar = s.charAt(i);
          --distinctCharCount[startChar];
          i++;
          if (distinctCharCount[startChar] == 0) {
            break;
          }
        }
        num--;
      }
      // record the maxSubStrLength
      // it is over kill to tuning by adding 'if (j == s.length() - 1 || count[s.charAt(j + 1)] ==
      // 0)'
      maxSubStrLength = Math.max(maxSubStrLength, j - i + 1);
    }
    return maxSubStrLength;
  }
}
