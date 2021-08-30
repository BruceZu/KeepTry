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

package queue;

import java.util.*;

public class Leetcode692TopKFrequentWords {
  /*
    692. Top K Frequent Words

    Given an array of strings words and an integer k, return the k most frequent strings.
    Return the answer sorted by the frequency from highest to lowest.
    Sort the words with the same frequency by their lexicographical order.

    Input: words = ["i","love","leetcode","i","love","coding"], k = 2
    Output: ["i","love"]
    Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.

    Input: words = ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4
    Output: ["the","is","sunny","day"]
    Explanation: "the", "is", "sunny" and "day" are the four most frequent words, with the number of occurrence being 4, 3, 2 and 1 respectively.

    Constraints:
        1 <= words.length <= 500
        1 <= words[i] <= 10
        words[i] consists of lowercase English letters.
        k is in the range [1, The number of unique words[i]]

  */
  /*==========================================================================
    Idea:
       k min heap of <key, frequency>
       top  -> bottom
       -  frequency:   small  -> bigger
       - key string:  bigger -> small

      When compare current entry with minheap top:
        - compare frequency:  keep bigger.   1, 2
        - if frequency are same: compare key string:   keep the smaller.  `love`, `i`
        skill
    ```
    int size = 0;
    for (Map.Entry<String, Integer> e : m.entrySet()) {
      min.offer(e);
      size++;
      if (size > k) min.poll();
    }
    ```

  O(nlogK) time
  O(n) space
  */
  public List<String> topKFrequent(String[] words, int k) {
    Map<String, Integer> m = new HashMap();
    for (String w : words) m.put(w, m.getOrDefault(w, 0) + 1);

    Queue<Map.Entry<String, Integer>> min =
        new PriorityQueue<>(
            (a, b) -> {
              if (a.getValue() == b.getValue()) return b.getKey().compareTo(a.getKey());
              else return a.getValue() - b.getValue();
            });

    int size = 0;
    for (Map.Entry<String, Integer> e : m.entrySet()) {
      min.offer(e);
      size++;
      if (size > k) min.poll();
    }

    String[] r = new String[k];
    int i = k - 1;
    while (i >= 0) r[i--] = min.poll().getKey();
    return Arrays.asList(r);
  }

  /*==========================================================================
  map<key, frequency>
  Bucket sort:
  1 List<String>[] + sort
  2 TreeSet<String>[]
  3 Tire[]: index is frequency, value is Tire. DFS order is just the lexicographical order
  */
  /*---------------------------------------------------------------------------
   3 Tire[]: index is frequency, value is Tire

   Take Tire operation addWord() and collectWords() as O(1) time
   O(n) time
   (n*26^10) space
  */
  public List<String> topKFrequent2(String[] words, int k) {
    Map<String, Integer> m = new HashMap<>();
    for (String word : words) m.put(word, m.getOrDefault(word, 0) + 1);

    TrieNode[] t = new TrieNode[words.length + 1];
    for (String w : m.keySet()) {
      int f = m.get(w);
      if (t[f] == null) t[f] = new TrieNode();
      addWord(t[f], w); // 10 time
    }

    List<String> r = new LinkedList<>();
    for (int i = t.length - 1; i >= 1 && r.size() < k; i--) {
      if (t[i] == null) continue;
      collectWords(t[i], r, k);
    }
    return r;
  }

  // DFS: 26^10 time, space the order is just the lexicographical order
  private boolean collectWords(TrieNode n, List<String> r, int k) {
    if (n.w != null) {
      r.add(n.w);
      if (r.size() == k) return true;
    }

    for (int i = 0; i < 26; i++) {
      if (n.next[i] != null) {
        if (collectWords(n.next[i], r, k)) return true;
      }
    }
    return false;
  }

  private void addWord(TrieNode n, String w) {
    for (char c : w.toCharArray()) {
      if (n.next[c - 'a'] == null) n.next[c - 'a'] = new TrieNode();
      n = n.next[c - 'a'];
    }
    n.w = w;
  }

  class TrieNode {
    TrieNode[] next;
    String w;

    TrieNode() {
      this.next = new TrieNode[26];
      this.w = null;
    }
  }

  /*---------------------------------------------------------------------------
    2 TreeSet<String>[]
  */
  public List<String> topKFrequent3(String[] words, int k) {
    Map<String, Integer> m = new HashMap<>();
    for (String w : words) {
      m.put(w, m.getOrDefault(w, 0) + 1);
    }
    TreeSet<String>[] b = new TreeSet[words.length];
    for (Map.Entry<String, Integer> e : m.entrySet()) {
      String w = e.getKey();
      int f = e.getValue();
      if (b[f] == null) {
        b[f] = new TreeSet<>(String::compareTo);
      }
      b[f].add(w);
    }

    List<String> r = new LinkedList<>();
    for (int i = b.length - 1; i >= 0; i--) {
      if (b[i] != null) {
        TreeSet<String> set = b[i];
        if (set.size() < k) {
          k = k - set.size();
          while (set.size() > 0) r.add(set.first());
        } else {
          while (k > 0) {
            r.add(set.first());
            k--;
          }
          break;
        }
      }
    }
    return r;
  }
  /*==========================================================================
   Quick select first k number entry<key, frequency> with bigger frequency
   then sort them.

   O(n+klogk) time
   O(n) space
  */

  public List<String> topKFrequent4(String[] words, int k) {
    Map<String, Integer> map = new HashMap<>();
    for (String w : words) map.put(w, map.getOrDefault(w, 0) + 1);

    Map.Entry<String, Integer>[] arr = new Map.Entry[map.size()];
    int i = 0;
    for (Map.Entry<String, Integer> e : map.entrySet()) arr[i++] = e;

    quickSelect(arr, 0, arr.length - 1, k);

    List<Map.Entry<String, Integer>> list = new LinkedList<>();
    for (int j = 0; j < k; j++) list.add(arr[j]);

    Collections.sort(list, (a, b) -> compare(a, b));

    List<String> ans = new LinkedList<>();
    for (Map.Entry<String, Integer> e : list) ans.add(e.getKey());
    return ans;
  }

  private void quickSelect(Map.Entry<String, Integer>[] a, int l, int r, int k) {
    while (l < r) {
      int pi = partition(a, l, r);
      if (pi == k - 1) break;
      else if (pi < k - 1) l = pi + 1;
      else r = pi - 1;
    }
  }
  // Descending order
  private int partition(Map.Entry<String, Integer>[] a, int l, int r) {
    int pi = l + r >>> 1;
    Map.Entry<String, Integer> pv = a[pi];
    swap(a, pi, l);
    int i = l + 1, n = l + 1;
    while (i <= r) {
      if (compare(a[i], pv) < 0) swap(a, i++, n++);
      else i++;
    }
    n--; // note here
    swap(a, n, l);
    return n;
  }

  private void swap(Map.Entry<String, Integer>[] a, int l, int r) {
    Map.Entry<String, Integer> tmp = a[l];
    a[l] = a[r];
    a[r] = tmp;
  }

  // descending order by frequency. same frequency: in lexicographical order
  public static int compare(Map.Entry<String, Integer> l, Map.Entry<String, Integer> r) {
    String lw = l.getKey(), rw = r.getKey();
    int lf = l.getValue(), rf = r.getValue();
    return (lf == rf) ? lw.compareTo(rw) : rf - lf;
  }
}
