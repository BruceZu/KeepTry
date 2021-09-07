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

import java.util.ArrayList;
import java.util.List;

public class Leetcode438FindAllAnagramsinaString {
  /*
  438. Find All Anagrams in a String

    Given two strings s and p, return an array of
    all the start indices of p's anagrams in s.
    You may return the answer in any order.
    An Anagram is a word or phrase formed by
    rearranging the letters of a different word or phrase,
    typically using all the original letters exactly once.

    Input: s = "cbaebabacd", p = "abc"
    Output: [0,6]
    Explanation:
    The substring with start index = 0 is "cba", which is an anagram of "abc".
    The substring with start index = 6 is "bac", which is an anagram of "abc".

    Input: s = "abab", p = "ab"
    Output: [0,1,2]
    Explanation:
    The substring with start index = 0 is "ab", which is an anagram of "ab".
    The substring with start index = 1 is "ba", which is an anagram of "ab".
    The substring with start index = 2 is "ab", which is an anagram of "ab".

    Constraints:
        1 <= s.length, p.length <= 3 * 104
        s and p consist of lowercase English letters.
  */
  public static List<Integer> findAnagrams2(String s, String p) {
    /*
    Idea:
      use fixed length sliding window
      target string and sliding window can be view as char:frequencey and use data structure:
      - int[26], then plus a `int sum`to trace the sum of each valid char's frequency
               when a valid char frequency
                  - increase 1 from [0, int[char]-1]  to [1,int[char]]
                  - decrease 1 from [1,int[char]] to [0, int[char]-1]
      - map<Character, Integer>, then use a `int num` to trace the number of valid char
               which just has the same frequency `map.get(char)`

    O(N) time, O(1) space.
    N is length of S.
    */
    int N = p.length();
    int[] P = new int[26];
    for (int i = 0; i < p.length(); i++) P[p.charAt(i) - 'a']++;
    int l = 0;
    int[] W = new int[26];
    List<Integer> r = new ArrayList();
    for (int i = 0; i < s.length(); i++) {
      int c = s.charAt(i) - 'a';
      if (P[c] != 0) {
        if (0 <= W[c] && W[c] <= P[c] - 1)
          l++; // increase 1 from [0, int[char]-1]  to [1,int[char]]
        W[c]++;
      }
      int start = i - N;
      if (start >= 0) {
        c = s.charAt(start) - 'a';
        if (P[c] != 0) {
          if (1 <= W[c] && W[c] <= P[c]) l--; // decrease 1 from [1,int[char]] to [0, int[char]-1]
          W[c]--;
        }
      }
      if (l == N) r.add(start + 1);
    }
    return r;
  }
  /*
  `int[] W = new int[26];` can be saved
  */
  public static List<Integer> findAnagrams(String s, String p) {
    int[] R = new int[26];
    char[] chars = p.toCharArray();
    for (char c : chars) {
      R[c - 'a']++;
    }
    int L = p.length(); // required left valid length.
    List<Integer> r = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      int c = s.charAt(i) - 'a';
      if (R[c] >= 1) L--;
      R[c]--; // step in char: always ++
      int start = i - p.length();
      if (start >= 0) {
        c = s.charAt(start) - 'a';
        if (R[c] >= 0) L++;
        R[c]++; // step out char: always --;
      }
      if (L == 0) r.add(start + 1);
    }
    return r;
  }
}
