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

import java.util.*;

public class ShortestPathVisitedAllNodes {
  /*General question:
   Finding the Shortest Path in a Graph Visiting All Nodes

    Suppose we have an undirected graph, G, that contains V nodes numbered from 0 to V - 1.
    In addition, we have E edges that connect these nodes.
    Each of these edges has a weight associated with it,
    representing the cost to use this edge.

    Our task is to find the shortest path that goes through all nodes in the given graph.
    In this task, we look for all the paths that cover all possible starting and ending nodes.
    Besides, we can visit the same node more than once, and do not have to return to the start node
  */
  /*
  Understanding
  The Travelling Salesman Problem:
   - each node can only be visited once and that the path has to return to where it started.
   - do not use queue, instead go along the bitmask, visited cities, space

  Minimal Spanning Trees:
   - only provide the tree, have no loops, not a minimal path.
   - use a min heap, Priority Queue.
   */

  /*
    Idea A is better than B
    Idea A: see Leetcode847ShortestPathVisitingAllNodes
    Idea B: Transforming the graph so than any 2 nodes are connected directly:
               - compute the minimum distance for every pair of nodes. By e.g. Floyd-Warshall algorithm, and keep
                 result in E.g. 2-D array g,  g[u][v] is the shortest path between u and v
               - construct the complete graph where the edge between nodes u and v is the minimum cost from u to v.
            Now, don't have to revisit nodes anymore, that's already hidden in the costs of the edges.
            next step: Tracking the one with the smallest cost by looping each of all permutation:
            O(N!+N^3) time.
            O(N^2)space
            Note: It does not work to apply the first part idea of Travelling Salesman Problem (TSP) algorithm here.
                 Because: TSP is a find a circle, so from any city to start does not affect result
                          Here is to calculate a path, the start city will affect the result
                             o
                           /  \       default the edge length is 1
                          1    2
                          = Floyd Warshall ==>

                               o
                             /   \
                          (1)     (1)
                          /        \
                         1---(2)--- 2
                       if start from city 0 then the shortest path will be 3, not 2
                       if start from city 1 or city 2, the shortest path is 2.


           Also see:
             0
            /|\
           1 2 3

           0 ---- 1
           |  \/  |
           |  /\  |
           2 ---- 3

           3-2 : 2
           2-1:  2

  */

  /*
  g[u] is an array keeps [v1, v2, v3] that there is edge u-v1, u-v2, u-v3.
   The weight of any edge is 1
  */
  public static int shortestPathLength(int[][] d) {
    int N = d.length;
    if (N <= 1) return 0;
    int[][] g = new int[N][N];
    // default for no edge for u and v
    for (int u = 0; u < N; u++) Arrays.fill(g[u], N);
    // Do not user Integer.MAX_VALUE. it will make
    //  g[u][k] + g[k][v] become a negative number
    for (int u = 0; u < N; u++) g[u][u] = 0;

    // Apply u-v from provided g2
    for (int u = 0; u < N; u++) {
      for (int v : d[u]) {
        g[u][v] = 1;
        g[v][u] = 1;
      }
    }

    // calculate the shortest distance between u-v
    for (int k = 0; k < N; k++) { // from 0.  shortestPath (i,j,0)=w(i,j)
      for (int u = 0; u < N; u++) {
        for (int v = 0; v < N; v++) {
          if (g[u][v] > g[u][k] + g[k][v]) {
            g[u][v] = g[u][k] + g[k][v]; // requires no negative cycles
            if (u == v && g[u][v] < 0) throw new RuntimeException("a native circle is found");
          }
        }
      }
    }
    Set<Integer> visited = new HashSet<>();
    r = Integer.MAX_VALUE;
    dfs(-1, 0, visited, g);
    return r;
  }

  static int r;
  /*
  pre is the label of node which is the previous node in current permutation
   */
  private static void dfs(int pre, int pathLen, Set<Integer> visited, int[][] g) {
    if (visited.size() == g.length) {
      r = Math.min(r, pathLen);
      return;
    }

    for (int i = 0; i < g.length; i++) {
      if (visited.contains(i)) continue;
      visited.add(i);
      dfs(i, pre == -1 ? 0 : pathLen + g[pre][i], visited, g);
      visited.remove(i);
    }
  }

  public static void main(String[] args) {
    System.out.println(shortestPathLength(new int[][] {{1, 2, 3}, {0}, {0}, {0}}));
    System.out.println(shortestPathLength(new int[][] {{1, 2}, {0}, {0}}));
    System.out.println(shortestPathLength(new int[][] {{}}));
    System.out.println(
        shortestPathLength(
            new int[][] {
              {1, 4, 6, 8, 9}, {0, 6}, {9}, {5}, {0}, {7, 3}, {0, 1}, {9, 5}, {0}, {0, 2, 7}
            }));
  }
}
