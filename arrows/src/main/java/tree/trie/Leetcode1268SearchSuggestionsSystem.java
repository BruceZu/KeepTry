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

public class Leetcode1268SearchSuggestionsSystem {
  /*
      1268. Search Suggestions System

      an array of strings products and a string searchWord.
      suggests at most three product names from products after each character of searchWord is typed.
      Suggested products should have common prefix with the searchWord.
      If there are more than three products with a common prefix
      return the three lexicographically minimums products.

      Return list of lists of the suggested products after each character of searchWord is typed.


    Input: products = ["mobile",
                       "mouse",
                       "moneypot",
                       "monitor",
                       "mousepad"],
          searchWord = "mouse"
    Output: [
    ["mobile","moneypot","monitor"],
    ["mobile","moneypot","monitor"],
    ["mouse","mousepad"],
    ["mouse","mousepad"],
    ["mouse","mousepad"]
    ]

    Input: products = ["havana"],
          searchWord = "havana"
    Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]

    Input: products = ["bags",
                       "baggage",
                       "banner",
                       "box",
                       "cloths"],
          searchWord = "bags"
    Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]

    Input: products = ["havana"],
          searchWord = "tatiana"
    Output: [[],[],[],[],[],[],[]]

    Constraints:

        1 <= products.length <= 1000
        There are no repeated elements in products.
        1 <= Σ products[i].length <= 2 * 10^4
        All characters of products[i] are lower-case English letters.
        1 <= searchWord.length <= 1000
        All characters of searchWord are lower-case English letters.
  */

  /*---------------------------------------------------------------------------
  Idea: sort + binary search
    n: products length
    m: searchWord length
    O(nlogn+ mlogn) time, not take in account the time of String function substring() and compareTo()
    O(n) space used by sort, In Java sorting String[] use TimSort
   */
  public List<List<String>> suggestedProducts__(String[] products, String searchWord) {
    List<List<String>> r = new ArrayList();
    Arrays.sort(products);
    for (int i = 0; i < searchWord.length(); i++) {
      String head = searchWord.substring(0, i + 1);
      // There is overlap here, there is way to improve it
      // but compareTo() in binary search can not be avoided
      int s = lower_bound(head, products);
      List<String> l = new ArrayList(3);
      r.add(l);
      if (s != -1) {
        int t = 3;
        while (t-- >= 1 && s < products.length && products[s].startsWith(head))
          l.add(products[s++]);
      }
    }
    return r;
  }

  /*
  Find the left most one of strings in array a that >= s,
  not prefix, so both of them may be just same
  not starts with relation, so they may have not common prefix
  */
  private int lower_bound(String s, String[] a) {
    if (a[a.length - 1].compareTo(s) < 0) return -1;
    // now the result is guarantee in the [l,r]
    int l = 0, r = a.length - 1; // possible element
    while (l < r) {
      int m = l + r >>> 1;
      if (a[m].compareTo(s) < 0) l = m + 1;
      else r = m; // not l
    }
    return l;
  }
  /*---------------------------------------------------------------------------
  Idea: Sort + binary search
  use TreeSet
  need either of
  - try catch to ignore IllegalArgumentException fromKey>toKey
  - floor.compareTo(ceiling) < 0
  Because in this case there is no matched result.
  e.g.  products: ["ab","cd"], search "abc"
        ceiling of "abc" is "cd"
        floor of "abc{" is "ab"
  */
  public List<List<String>> suggestedProducts___(String[] pa, String w) {
    TreeSet<String> set = new TreeSet<>();
    for (int i = 0; i < pa.length; i++) set.add(pa[i]);

    String h = "";
    List<List<String>> r = new ArrayList<>();
    for (char c : w.toCharArray()) {
      List<String> l = new ArrayList<>();
      r.add(l);

      h += c;
      String ceiling = set.ceiling(h), floor = set.floor(h + "{");
      if (ceiling == null || floor.compareTo(ceiling) < 0) continue;

      Iterator<String> ite = set.subSet(ceiling, true, floor, true).iterator();
      int t = 3;
      while (t-- > 0 && ite.hasNext()) l.add(ite.next());
    }
    return r;
  }
  /*---------------------------------------------------------------------------
  Idea:
    sort + prefix character
    the above ideas does not use the property of searching words
    there is prefixed words relation within the searching words
    - reuse last prefix found lower bound index as start index for searching current prefix lower bound index
    - if current prefix is not found then the left prefix will not be found too.
    So each product is accessed only once if not taking in time used to collect at most 3 suggestions.
    in total O(nlogn) time
    O(n) space used by sort, In Java sorting String[] use TimSort
  */
  public List<List<String>> suggestedProducts_(String[] p, String w) {
    List<List<String>> r = new ArrayList();
    Arrays.sort(p); // O(nlogn) time
    int f = 0;
    String h = "";
    boolean lastFound = true;
    for (int i = 0; i < w.length(); i++) { // O(n) time
      List<String> l = new ArrayList(3);
      r.add(l);
      if (!lastFound) continue;

      h = h + w.charAt(i);
      while (f < p.length && !p[f].startsWith(h)) f++;
      if (f == p.length) {
        lastFound = false;
        continue;
      }
      // found
      int s = f, t = 3;
      while (t-- > 0 && s < p.length && p[s].startsWith(h)) l.add(p[s++]);
    }
    return r;
  }
  /*---------------------------------------------------------------------------
  Idea: Trie + prefix
  searching a prefix string in the Trie.
  If it is find then from its last node
  to do dfs to pick up at most 3 lexicographically minimums strings.
  And the next time searching start from last end node
  `a`
  `ab` from node `a` to search `b` and from `b` to dfs collection
  `abc` from node `b` to search `c` and from `c` to dfs collection

   The searching only search from child directly, not iterator or recursion

   O(L + m) time, L is all products' length. m is length of searchWord
   take it as O(1) To search at most 3 suggestions for each prefix of searchWord
   O(x) space, x is nodes of trie
   the pro of Tire here is it is easy to maintain new added product or typed key word in browser
  */
  public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    Trie root = new Trie();
    for (String p : products) build(root, p);

    Trie end = root; // previous prefix end char node
    boolean findLast = true; // is the previous prefix last char node found?

    List<List<String>> r = new ArrayList<>();
    for (int i = 0; i < searchWord.length(); i++) {
      List<String> l = new ArrayList<>();
      r.add(l);
      if (!findLast) continue;

      end = end.next[searchWord.charAt(i) - 'a'];
      if (end != null) collect(l, end, 3);
      else findLast = false;
    }
    return r;
  }

  // build Tire, string s is lower-case English letters
  private void build(Trie n, String s) {
    int i = 0;
    while (i < s.length()) {
      int idx = s.charAt(i) - 'a';
      if (n.next[idx] == null) n.next[idx] = new Trie();
      n = n.next[idx];
      i++;
    }
    n.isEnd = true;
    n.str = s;
  }

  /*
  Return true if collected enough as required 3 suggestions
  `int atMost`. do not use `int left` and `left--` in dfs because it can not calculate
   all collections size.
   */
  private boolean collect(List<String> l, Trie n, int atMost) {
    if (n.isEnd) {
      l.add(n.str);
      if (l.size() == atMost) return true;
    }
    for (int i = 0; i < 26; i++)
      if (n.next[i] != null && collect(l, n.next[i], atMost)) return true;
    return false;
  }

  static class Trie {
    boolean isEnd;
    String str;
    Trie[] next = new Trie[26];
  }
}
