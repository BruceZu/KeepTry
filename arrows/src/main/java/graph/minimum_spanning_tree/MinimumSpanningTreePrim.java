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

package graph.minimum_spanning_tree;

import java.util.*;

public class MinimumSpanningTreePrim {

  /*
    Prim's Minimum Spanning Tree (MST) algorithm.
      https://en.wikipedia.org/wiki/Prim%27s_algorithm
      https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-046j-design-and-analysis-of-algorithms-spring-2015/lecture-notes/MIT6_046JS15_lec12.pdf
      https://algs4.cs.princeton.edu/43mst/
      binary heap and adjacency list 	O(( | V | + | E | ) log | V | ) = O ( | E | log | V | )

  Prim's algorithm with an indexed binary heap.
  The constructor takes Θ(E log V) time in the worst case,
   V is the number of vertices
   E is the number of edges.

   Each instance method takes Θ(1) time, Θ(V) extra space (not including the edge-weighted graph).

   weight() method correctly computes the weight of the MST  if all
   arithmetic performed is without floating-point rounding error or arithmetic overflow.
   This is the case if all edge weights are non-negative integers and the weight of the MST does not exceed 252.
   Documentation, see Section 4.3  of Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne.
   For alternate implementations, see LazyPrimMST, KruskalMST, and BoruvkaMST.
   */

  /*
     Assume any 2 vertexes are connected ( else need to try starting from each vertex to ge minimum spanning forest)
     Details:
     input:
       V is vertex number, each vertex is labeled from 0~V-1;
       Map<Integer, Integer>[] g;  g[i] is vertex i and edges i-k with wight v
                                    weight is Integer here, it can be Double

     shortWto[v]: The weight of edge u-v
       v is not in current MST, u is in current MST
       u-v is the shortest edges who are in the cut and with v as one vertex.
       initial value is Integer.MAX_VALUE;
       this is to help to reduce the min heap size
     inMST[v] = true if v in MST
     Priority<int[]> queue keep [u, v, weight], in wight ascending order. min heap
     List<int[]> kee minimum spanning tree edges

    Start from any vertex to find minimum spanning tree.

          0  ---- 1
          |   \/  |
          |   /\  |
          2  ---- 3
  */

  public List<int[]> PrimMST(Map<Integer, Integer>[] g, int V) {
    Integer[] shortWto = new Integer[V];
    for (int v = 0; v < V; v++) shortWto[v] = Integer.MAX_VALUE;

    boolean[] inMST = new boolean[V];
    PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

    int s = 0;
    inMST[s] = true;

    for (Map.Entry<Integer, Integer> te : g[s].entrySet()) {
      int t = te.getKey(), w = te.getValue();
      q.offer(new int[] {s, t, w});
      shortWto[t] = w;
    }

    List<int[]> r = new ArrayList<>();
    while (!q.isEmpty()) { // or r.size()<V-1
      int[] e = q.poll();
      if (inMST[e[0]] && inMST[e[1]]) continue;
      int f = inMST[e[0]] ? e[1] : e[0];
      inMST[f] = true;
      r.add(new int[] {Math.min(e[1], e[0]), Math.max(e[1], e[0]), e[2]});

      for (Map.Entry<Integer, Integer> te : g[f].entrySet()) {
        int t = te.getKey(), w = te.getValue();
        if (!inMST[t] && w < shortWto[t]) {
          q.offer(new int[] {f, t, w});
          shortWto[t] = w;
        }
      }
    }
    return r;
  }
}
