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

package unionfind;

import java.util.*;

public class Leetcode1202SmallestStringWithSwaps {
  /*
   Leetcode 1202. Smallest String With Swaps

  You are given a string s, and an array of pairs of indices in the string pairs where
  pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.

  You can swap the characters at any pair of indices in the given pairs any number of times.
  Return the lexicographically smallest string that s can be changed to after using the swaps.

  Input: s = "dcab", pairs = [[0,3],[1,2]]
  Output: "bacd"
  Explaination:
  Swap s[0] and s[3], s = "bcad"
  Swap s[1] and s[2], s = "bacd"
  Example 2:

  Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
  Output: "abcd"
  Explaination:
  Swap s[0] and s[3], s = "bcad"
  Swap s[0] and s[2], s = "acbd"
  Swap s[1] and s[2], s = "abcd"
  Example 3:

  Input: s = "cba", pairs = [[0,1],[1,2]]
  Output: "abc"
  Explaination:
  Swap s[0] and s[1], s = "bca"
  Swap s[1] and s[2], s = "bac"
  Swap s[0] and s[1], s = "abc"


  Constraints:

  1 <= s.length <= 10^5
  0 <= pairs.length <= 10^5
  0 <= pairs[i][0], pairs[i][1] < s.length
  s only contains lower case English letters.


  Hints
  Think of it as a graph problem.
  Consider the pairs as connected nodes in the graph, what can you do with a connected component of indices ?
  We can sort each connected component alone to get the lexicographically minimum string.
  */

  /*
   Idea comes from https://leetcode.com/problems/smallest-string-with-swaps/discuss/388257/C%2B%2B-with-picture-union-find
   And the provided picture


   Time Limit Exceeded
   How to improve it
   Runtime: O(n log n)
   Memory: O(n)
  */

  public static String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
    int N = s.length();
    int[] rank = new int[N];
    int[] root = new int[N];
    for (int i = 0; i < N; i++) {
      root[i] = i;
    }
    for (List<Integer> pair : pairs) {
      int a = pair.get(0), b = pair.get(1);
      union(rank, root, find(root, a), find(root, b));
    }
    // Runtime will be O(N*N*logN)
    //    Set<Integer> visitedRank = new HashSet<>();
    //    char[] a = s.toCharArray();
    //    for (int i = 0; i < N; i++) {
    //      int r = find(root, i);
    //      if (!visitedRank.contains(r)) {
    //        visitedRank.add(r);
    //        sortSameRank(i, a, r, root); // find all chars with the same rank r and sort and
    //                                     // setback in O(NlogN) time
    //      }
    //    }
    //
    //    return new String(a);
    // Improve the runtime
    // root: sorted chars with same root
    Map<Integer, Queue<Character>> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      map.computeIfAbsent(find(root, i), k -> new PriorityQueue<>()).offer(s.charAt(i));
    }

    StringBuilder res = new StringBuilder();
    for (int i = 0; i < root.length; i++) {
      res.append(map.get(root[i]).poll()); // skill
    }
    return res.toString();
  }

  private static void union(int[] rank, int[] root, int ra, int rb) {
    if (ra == rb) return;
    if (rank[ra] < rank[rb]) root[ra] = rb; // it is ra not a
    else if (rank[ra] > rank[rb]) root[rb] = ra; // it is rb not b
    else {
      root[ra] = rb; // it is ra not a
      rank[rb]++; // it is rb not a and ra
    }
  }

  private static int find(int[] root, int i) {
    int x = i;
    while (root[i] != i) {
      i = root[i];
    }
    // i is result now, before returning it update all path
    while (root[x] != i) {
      int n = root[x];
      root[x] = i;
      x = n;
    }
    return i;
  }
}
