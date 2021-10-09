//  Copyright 2016 The Sawdust Open Source Project
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

package bfs;

import java.util.*;

/*
BFS from both side, each time select the side with smaller size layer,
select string(s) in next layer from D or from other side layer
does not change the time complexity.
*/

public class Leetcode126WordLadderII_2 {
  // map of string : transformed(s) or current layer string: adjacency next layer string(s)  1:n
  private Map<String, Set<String>> graph;
  private String begin, end;
  private String[] tmpPath;
  private List<List<String>> shortests;

  public List<List<String>> findLadders(String begin, String end, List<String> wordList) {
    shortests = new ArrayList<>();
    Set<String> D = new HashSet<>(wordList);
    if (!D.contains(end)) return shortests;
    // 1 build graph
    this.begin = begin;
    this.end = end;
    graph = new HashMap<>();
    if (!bfsBuildDAG(D)) return shortests;

    // 2 calculate the shortest path(s)
    tmpPath = new String[wordList.size()];
    tmpPath[0] = begin;
    dfsPaths(begin, 1);

    return shortests;
  }

  private boolean bfsBuildDAG(Set<String> D) {
    // layer
    Set<String> L = new HashSet<>(), R = new HashSet<>();
    L.add(begin);
    R.add(end);
    D.remove(begin);
    D.remove(end);

    boolean meet = false;
    boolean isL = true;

    while (!L.isEmpty()) {
      if (L.size() > R.size()) {
        Set<String> temp = L;
        L = R;
        R = temp;
        isL = !isL;
      }

      Set<String> newl = new HashSet<>();
      for (String str : L) {
        Set<String> ts = transformed(str, D, R);
        for (String t : ts) {
          if (R.contains(t)) meet = true; // continue next string at the same layer
          // build graph
          String from = isL ? str : t, to = isL ? t : str;
          graph.putIfAbsent(from, new HashSet<>());
          graph.get(from).add(to);
        }
        if (!meet) newl.addAll(ts);
      }

      if (meet) return true;
      D.removeAll(newl);
      L = newl;
    }
    return false;
  }
  // Adjacency strings str and tra, str is in Left layer, tra is in right layer,
  // tra is not in dictionary. but it is right layer, need it else no way to let meet happen
  private Set<String> transformed(String word, Set<String> D, Set<String> ol) { // other Side Layer
    Set<String> ts = new HashSet<>();
    char ar[] = word.toCharArray();
    for (int i = 0; i < word.length(); i++) {
      char ci = ar[i];
      for (char c = 'a'; c <= 'z'; c++) {
        if (c == ci) continue;
        ar[i] = c;
        String t = String.valueOf(ar);
        if (D.contains(t) || ol.contains(t)) ts.add(t);
      }
      ar[i] = ci;
    }
    return ts;
  }
  // i is the index in tmpPath for the next string in path
  private void dfsPaths(String key, int size) {
    if (key.equals(end)) shortests.add(Arrays.asList(Arrays.copyOf(tmpPath, size)));
    if (graph.get(key) == null) return;
    for (String t : graph.get(key)) {
      tmpPath[size] = t;
      dfsPaths(t, size + 1);
    }
  }
}
