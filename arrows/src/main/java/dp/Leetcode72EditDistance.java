//  Copyright 2022 The KeepTry Open Source Project
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

public class Leetcode72EditDistance {
  /*
   Leetcode 72. Edit Distance
   Given two strings word1 and word2, return the minimum number of operations
   required to convert word1 to word2.

    You have the following three operations permitted on a word:

    Insert a character
    Delete a character
    Replace a character

    Input: word1 = "horse", word2 = "ros"
    Output: 3
    Explanation:
    horse -> rorse (replace 'h' with 'r')
    rorse -> rose (remove 'r')
    rose -> ros (remove 'e')


    Input: word1 = "intention", word2 = "execution"
    Output: 5
    Explanation:
    intention -> inention (remove 't')
    inention -> enention (replace 'i' with 'e')
    enention -> exention (replace 'n' with 'x')
    exention -> exection (replace 'n' with 'c')
    exection -> execution (insert 'u')


    Constraints:
    0 <= word1.length, word2.length <= 500
    word1 and word2 consist of lowercase English letters.
  */

  /*
  Levenshtein_distance
  https://en.wikipedia.org/wiki/Levenshtein_distance#Recursive

  Iterative with full matrix
  Main article: Wagner–Fischer algorithm
  (https://en.wikipedia.org/wiki/Wagner–Fischer_algorithm)
  This algorithm, an example of bottom-up DP is discussed, with variants, in the 1974 article
  The String-to-string correction problem
  (https://en.wikipedia.org/wiki/String-to-string_correction_problem)
  by Robert A. Wagner and Michael J. Fischer

 Proof of correctness see
 https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm


   dp[i][j]: min operations for word1 ( 1-ith index) and word 2( 1-jth index)
   - initial: min operations for empty word1 and word2 with lengh L: is L;
   - if 2 current chars are same then dp[i][j] = dp[i-1][j-1]+1
   - else try 3 possible operation select min way to covert current problem to
          previous known problem of prefix sub-strings
          -- general idea: min{ deletion, insertion, substitution}
          -- improved:
             when current 2 chars are same
               d[i][j] = d[i - 1][j - 1]
             else not same:
              remove i    d[i-1][j]   + 1 delete operation
              remove j    d[i][j-1]   + 1 insert operation
              replace     d[i-1][j-1] + 1 replace operation
              (remove     d[i-1][j-1] + 1 delete + 1 insert (x) )
              d[i][j] = 1 + Math.min(Math.min(d[i - 1][j],
                                               d[i][j - 1]),
                                               d[i - 1][j - 1]);
   */
  public int minDistance(String W1, String W2) {
    int n = W1.length();
    int m = W2.length();

    // if one of the strings is empty
    if (n * m == 0) return n + m;

    // array to store the convertion history
    int[][] d = new int[n + 1][m + 1];

    // init boundaries
    for (int i = 0; i < n + 1; i++) {
      d[i][0] = i; // add operation
    }
    for (int j = 0; j < m + 1; j++) {
      d[0][j] = j; // add operation
    }

    // DP compute
    // i and j is 1 based index for DP
    for (int i = 1; i < n + 1; i++) {
      for (int j = 1; j < m + 1; j++) {
        // general idea: min{deletion,  insertion ,  substitution}
        int substitutionCost = W1.charAt(i - 1) == W2.charAt(j - 1) ? 0 : 1;
        d[i][j] =
            Math.min(
                d[i - 1][j] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j - 1] + substitutionCost));
      }
    }
    return d[n][m];
  }
  // follow up: Iterative with two matrix rows
  //            https://en.wikipedia.org/wiki/Levenshtein_distance#Iterative%20with%20full%20matrix
}
