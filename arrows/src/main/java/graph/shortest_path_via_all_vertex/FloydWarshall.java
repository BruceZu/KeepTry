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

package graph.shortest_path_via_all_vertex;

import java.util.Arrays;
import java.util.List;

public class FloydWarshall {
  /*
   Floyd–Warshall algorithm https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
   finding the shortest paths in a directed weighted graph with positive
   or negative edge weights
    - with no negative cycles
    - does not return details of the paths themselves, it is possible
      to reconstruct the paths with simple modifications to the algorithm

    - an example of dynamic programming, published by Robert Floyd in 1962.
    - compares all possible paths through the graph between each pair of vertices
    - It does so by incrementally improving an estimate on the shortest path between
      two vertices, until the estimate is optimal

    given graph G with vertices V numbered 1 through  N
    given function shortestPath(i,j,k)  that returns the shortest possible path from
      i to j  using vertices only from the set { 1 , 2 , … , k } as intermediate
      points along the way.

   goal is to find the shortest path from each i  to each j using any vertex in
   { 1 , 2 , … , N }
   Watch, for any vertex i and j
     shortestPath(i,j,k)} could be either
       - a path that does not go through k, only uses part or all vertices in the set { 1, … , k − 1 }
       or
       - a path that does go through k: from i to k, and then from k to j
         both only using part or all intermediate vertices in  { 1 , … , k − 1 }


     the shortest path from i to j (only using intermediate vertices 1 through k − 1}
     is defined by shortestPath(i,j,k-1),
     and it is clear that if there was a better path from i to k, k to j, then the length of
     this path would be the concatenation of
       the shortest path from i to k (only using intermediate vertices in { 1 , … , k − 1 } and
       the shortest path from k to j (only using intermediate vertices in { 1 , … , k − 1 }

    If w(i,j) is the weight of the edge between vertices i and j
    we can define shortestPath(i,j,k) in terms of the following recursive formula:
    the base case is
      shortestPath (i,j,0)=w(i,j); without any intermediate vertice
    the recursive case is
    shortestPath(i,j,k)= min { shortestPath(i,j,k-1),
                               shortestPath(i,k,k-1) + shortestPath(k,j,k-1)}.

    so for k==1:
    shortestPath(i,j,1)= min { shortestPath(i,j,0),
                               shortestPath(i,1,0) + shortestPath(1,j,0)}.
    k==2:
    shortestPath(i,j,2)= min { shortestPath(i,j,1),
                               shortestPath(i,2,1) + shortestPath(2,j,1)}.

    The algorithm works by first computing shortestPath(i,j,k) for
    all (i,j) pairs for k=1, then
    all (i,j) pairs for k=2, and so on.
    This process continues until k=N,
    when k=N then we have found the shortest path for all (i,j) pairs using any intermediate vertices.
    as any pair vertices in the graph is connected un-directly or directly

    Implementation:
       Watch from the conversion rule:
        - need only 2-d array
   O(V^3) time
   O(V^2) space
     limitation:
         A negative cycle is a cycle whose edges sum to a negative value
         that make this algorithm does not reach the shortest path for u-v from d[u][v][k]
         But this algorithm can detect negative circle: after algorithm is done to checking
         if there is negative d[u][u][k].
         To avoid overflow/underflow problems,  should check for negative cycle within the
         inner for loop of the algorithm
         In an undirected graph a negative edge creates a negative cycle
  The  shortest path:  With simple modifications, it is possible to  reconstruct
         the actual path between any two endpoint vertices with the shortest path length
    */

  // d[u] is [v1,w1],[v2,w2],.... w>0 is weight of edge u-v and v-u.
  // d represents a un-directed graph
  // vertex ID : 0~V-1
  public long[][] shortestPath(List<int[]>[] d) {
    int N = d.length;
    if (N <= 1) return null;
    long[][] g = new long[N][N];
    // directed graph
    // why long: default value is Integer.MAX_VALUE
    // avoid g[u][k] + g[k][v] become a negative number
    // default for no edge for u and v
    for (int u = 0; u < N; u++) Arrays.fill(g[u], Integer.MAX_VALUE);
    for (int u = 0; u < N; u++) g[u][u] = 0;

    // Apply u-v from provided g2
    for (int u = 0; u < N; u++) {
      for (int[] vw : d[u]) {
        g[u][vw[0]] = vw[1];
        g[vw[0]][u] = vw[1];
      }
    }
    // above is initialization defined by shortest[u][v][0] =w(u,v)
    // where node labeled from 1 to n, 0 means without any intermediate vertices

    // here the node is labeled from 0 to n-1 in Java 0-indexed

    // calculate the shortest distance between u-v
    for (int k = 0; k < N; k++) { // from 0.  shortestPath (i,j,0)=w(i,j)
      for (int u = 0; u < N; u++) {
        for (int v = 0; v < N; v++) {
          if (g[u][v] > g[u][k] + g[k][v]) {
            g[u][v] = g[u][k] + g[k][v];
            // requires no negative cycles
            if (u == v && g[u][v] < 0) throw new RuntimeException("a native circle is found");
          }
        }
      }
    }
    return g;
  }
}
