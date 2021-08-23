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

package tree.trie;

import java.util.*;

public class Leetcode1178NumberofValidWordsforEachPuzzle {
  /*
     1178. Number of Valid Words for Each Puzzle

      With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:

        - word contains the first letter of puzzle.
        - For each letter in word, that letter is in puzzle.
          For example, if the puzzle is "abcdefg", then valid words are
          "faced", "cabbage", and "baggage"; while invalid words are
          "beefed" (doesn't include "a") and "based" (includes "s" which isn't in the puzzle).

        Return an array answer, where answer[i] is the number of words in the given word list words
        that are valid with respect to the puzzle puzzles[i].


    Input:
    words = ["aaaa","asas","able","ability","actt","actor","access"],
    puzzles = ["aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"]
    Output: [1,1,3,2,4,0]
    Explanation:
    1 valid word for "aboveyz" : "aaaa"
    1 valid word for "abrodyz" : "aaaa"
    3 valid words for "abslute" : "aaaa", "asas", "able"
    2 valid words for "absoryz" : "aaaa", "asas"
    4 valid words for "actresz" : "aaaa", "asas", "actt", "access"
    There're no valid words for "gaswxyz" cause none of the words in the list contains letter 'g'.

    Constraints:

        1 <= words.length <= 10^5
        4 <= words[i].length <= 50
        1 <= puzzles.length <= 10^4
        puzzles[i].length == 7
        words[i][j], puzzles[i][j] are English lowercase letters.
        Each puzzles[i] doesn't contain repeated characters.
  */

  /*
  Idea:
     "words[i][j], puzzles[i][j] are English lowercase letters.
     <1>
      "1 <= words.length <= 10^5
      4 <= words[i].length <= 50"
      So, convert string to integer with bit manipulation,
      reduce the duplicated char in word and in sorted order

     <2>
       "Each puzzles[i] doesn't contain repeated characters.
       1 <= puzzles.length <= 10^4
       puzzles[i].length == 7"
       So, a puzzle has 7 distinguish letters which in total has 2^7 possible composition
       0~127 including empty subset 0. but 0 and any subset without header letter is not valid
       in current scenario.
       127< words.length=10^5

      M is size of words, N is size of puzzles
      O(M*N) time used in comparing words with a puzzles can be improved to be
      O(N)   time. a puzzle has 127 subset. check each subset with words(HashSet) 127*N

   In total
   O(L+N) time L is all words length
   O(M) space

     */
  public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
    Map<Integer, Integer> ws = new HashMap();
    for (String w : words) {
      int k = toInt(w);
      ws.put(k, ws.getOrDefault(k, 0) + 1);
    }
    List<Integer> r = new ArrayList(puzzles.length);
    for (String p : puzzles) {
      int h = 1 << p.charAt(0) - 'a', pi = toInt(p);
      int ri = 0;
      int mask = pi;
      while (pi != 0) {
        if ((h & pi) == h && ws.containsKey(pi)) ri += ws.get(pi);
        pi = (pi - 1) & mask;
      }
      r.add(ri);
    }
    return r;
  }

  private int toInt(String w) {
    int r = 0;
    for (int i = 0; i < w.length(); i++) r |= 1 << w.charAt(i) - 'a';
    return r;
  }
  /*---------------------------------------------------------------------------
  Trie
    Understanding
   - for each word, the ordering and the count of elements does not matter.
     keep all words in a sorted order and avoid the duplicate letters to make
     the maximum size of the word to be 26
   - the ordering of words in the puzzle doesn't matter, keep our puzzle in sorted manner.
   - The bool isEndOfWord field is modified to store the number of words ending at that node.
   - For any puzzle, traverse the tree and get the answer. need to go at most 7 levels deep.
   - If did not see the first char of a puzzle string, not cumulate the count.
   */

  class Trie {
    Trie[] next;
    int count;
  }

  /*
  it is not comparing string (sorted distinct) from trie that
  is same completely with a given puzzle string (sorted distinct)
  it is checking if there is string(s)(sorted distinct) in trie whose
  letter are included in puzzle string (sorted distinct).

  if current char is `m`, then next[i] i<='m'-'a' is null
  for i > 'm'-'a' only when next[i] is included in puzzle string (sorted distinct)
  go ahead.
  O(1) time. 127. number of all subset of 7 elements
  */
  private int validWords(int i, Trie n, Set<Character> puzzle, boolean seen, char hchar) {
    if (n == null) return 0;

    int sum = 0;
    if (seen) sum += n.count;

    for (char c : puzzle) {
      if (hchar == c) sum += validWords(i + 1, n.next[c - 'a'], puzzle, true, hchar);
      else sum += validWords(i + 1, n.next[c - 'a'], puzzle, seen, hchar);
    }
    return sum;
  }
}
