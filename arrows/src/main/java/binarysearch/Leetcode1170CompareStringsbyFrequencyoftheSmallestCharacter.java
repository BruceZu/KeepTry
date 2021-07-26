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

package binarysearch;

import java.util.Arrays;
import java.util.TreeSet;

public class Leetcode1170CompareStringsbyFrequencyoftheSmallestCharacter {
  /*
  f(s) be the frequency of the lexicographically smallest character in a non-empty string s.
  given an array of strings words and queries.
  For each query queries[i], count the number of words in words such that
    f(queries[i]) < f(W) for each W in words.
  Return an integer array answer, where each answer[i] is the answer to the ith query.

  Input: queries = ["cbd"], words = ["zaaaz"]
  Output: [1]

  Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
  Output: [1,2]


      1 <= queries.length <= 2000
      1 <= words.length <= 2000
      1 <= queries[i].length, words[i].length <= 10
      queries[i][j], words[i][j] consist of lowercase English letters.
     */
  /*
  M,N is length of queries and words
  Idea:
  'lowercase English letters' -> f(i) is O(1) time with array int[26]

  use red_black tree to keep each f(w) with O(NlogN)
     - for not merging duplicated element, sort by comparator
          (a, b) -> a >= b ? 1 : -1
       because objective will be get tail set
  NOTE  with this comparator, get tail set to make set[i] > x will be
       tree.tailSet(x).size() not
       tree.tailSet(x+1).size()

       tree.tailSet(x) function is for returning tail set,  set[i]>=x
       E.g.
         x = 2,  tree is 1,2,2,3,4,4,4
        without the specified comparator, return [2,2,3,4,4,4]
        with    the specified comparator, return [3,4,4,4]
  then for each f(query) check tailSet(f(query)).size()
  set.headSet(e) is O(1),  but SortedSet<E>.size() is O(1) or O(N)

  O(max{NlogN,MN}) time
  O(M+N) space
  */
  public int[] numSmallerByFrequency__(String[] queries, String[] words) {
    TreeSet<Integer> tree = new TreeSet<>((a, b) -> a >= b ? 1 : -1);
    for (String s : words) tree.add(leadf(s));
    int[] r = new int[queries.length];
    for (int i = 0; i < queries.length; i++) r[i] = tree.tailSet(leadf(queries[i])).size();
    return r;
  }

  // 1 <= queries[i].length, words[i].length <= 10
  private int leadf(String s) {
    int[] f = new int[26];
    for (int i = 0; i < s.length(); i++) f[s.charAt(i) - 'a']++;
    int leaf = 0;
    for (int i = 0; i < 26; i++)
      if (f[i] != 0) {
        leaf = f[i];
        break;
      }
    return leaf;
  }
  /*
  Idea
  use a list to keep f(w)
  binary search result[i] for each f(queries[i])
  need manually provide binary search function because duplicate elements

  O(max{NlogN,MlogN}) time
  O(M+N) space
   */
  public int[] numSmallerByFrequency_(String[] queries, String[] words) {
    int[] l = new int[words.length];
    for (int i = 0; i < words.length; i++) l[i] = leadf(words[i]);
    Arrays.sort(l);

    int[] re = new int[queries.length];
    for (int i = 0; i < queries.length; i++) re[i] = bs(l, leadf(queries[i]));
    return re;
  }

  // get number of elements in sorted array whose value is great than that of o
  private int bs(int[] a, int o) {
    int l = 0, r = a.length - 1;
    while (l < r) {
      int m = l + r >>> 1;
      if (a[m] <= o) l = m + 1;
      else r = m;
    }
    if (r != a.length - 1) return a.length - r;
    if (a[r] > o) return 1;
    return 0;
  }

  /*
  Idea
  note:   1 <= queries[i].length, words[i].length <= 10
  so the f(string) value range is [0,10]
  use a array to keep suffix sum of value frequency
  O(max{M,N}) time
  O(M) space
   */
  public int[] numSmallerByFrequency(String[] queries, String[] words) {
    int[] ss = new int[11]; // suffix sum of value frequency
    for (int i = 0; i < words.length; i++) ss[leadf(words[i])]++;
    for (int i = 9; i >= 0; i--) ss[i] = ss[i] + ss[i + 1];

    int[] re = new int[queries.length];
    for (int i = 0; i < queries.length; i++) {
      int v = leadf(queries[i]);
      re[i] = v == 10 ? 0 : ss[v + 1];
    }
    return re;
  }
}
