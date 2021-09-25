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
  /*
  847. Shortest Path Visiting All Nodes

  You have an undirected, connected graph of n nodes labeled from 0 to n - 1.
  You are given an array graph where graph[i] is a list of all the nodes connected
  with node i by an edge.
  Return the length of the shortest path that visits every node.
  You may start and stop at any node, you may revisit nodes multiple times,
  and you may reuse edges.

  Input: graph = [[1,2,3],[0],[0],[0]]
  Output: 4
  Explanation: One possible path is [1,0,2,0,3]


  Input: graph = [[1],[0,2,4],[1,3,4],[2],[1,2]]
  Output: 4
  Explanation: One possible path is [0,1,4,2,3]



  Constraints:
      n == graph.length
      1 <= n <= 12
      0 <= graph[i].length < n
      graph[i] does not contain i.
      If graph[a] contains b, then graph[b] contains a.
      The input graph is always connected.
   */
  /*===========================================================================*/
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
  Idea A: Transforming the graph so than any 2 nodes are connected directly:
             - compute the minimum distance for every pair of nodes. By e.g. Floyd-Warshall algorithm, and keep
               result in E.g. 2-D array g,  g[u][v] is the shortest path between u and v
             - construct the complete graph where the edge between nodes u and v is the minimum cost from u to v.
          Now, don't have to revisit nodes anymore, that's already hidden in the costs of the edges.
          then apply Travelling Salesman Problem (TSP) algorithm

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
  Floyd–Warshall algorithm https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
  finding shortest paths in a directed weighted graph with positive
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
      - a path that does not go through k, only uses vertices in the set { 1, … , k − 1 }
      or
      - a path that does go through k: from i to k, and then from k to j
        both only using intermediate vertices in  { 1 , … , k − 1 }


    the shortest path from i to j (only using intermediate vertices 1 through k − 1}
    is defined by shortestPath(i,j,k-1),
    and it is clear that if there was a better path from i to k, k to j, then the length of
    this path would be the concatenation of
      the shortest path from i to k (only using intermediate vertices in { 1 , … , k − 1 } and
      the shortest path from k to j (only using intermediate vertices in { 1 , … , k − 1 }

   If w(i,j) is the weight of the edge between vertices i and j
   we can define shortestPath(i,j,k) in terms of the following recursive formula:
   the base case is
     shortestPath (i,j,0)=w(i,j)
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
   then we have found the shortest path for
   all (i,j) pairs using any intermediate vertices.
   */

  // g[u] is [v1,w1],[v2,w2],.... w>0 is weight of edge u-v;
  // vertex ID : 0~V-1
  public void shortestPath(int V, List<int[]>[] g) {
    int[][] d = new int[V][V];
    for (int u = 0; u < V; u++) {
      for (int v = 0; v < V; v++) {
        d[u][v] = Integer.MAX_VALUE; // default for no edge for u and v
      }
    }
    for (int u = 0; u < V; u++) {
      d[u][u] = 0;
    }

      //
    for (int k = 0; k < V; k++) {
      for (int u = 0; u < V; u++) {
        for (int v = 0; v < V; v++) {
          if (d[u][u] > d[u][k] + d[k][v]) {
            d[u][u] = d[u][k] + d[k][v]; // requires no negative cycles
          }
        }
      }
    }
  }

  /*
  Idea B: from https://www.baeldung.com/cs/shortest-path-visiting-all-nodes
      modified Dijkstra algorithm.
      require weight >=0
      For each state, keep track of all the visited nodes + the current one with bitmask

      In the end, answer will be the minimum value among all nodes’ values that
      have a bitmask full of '1' (visited all nodes).

     Data Structure
       int [v][bitmask] keep the shortest cost of path visited cities represented by bitmask and end
                        at 'v'.
                        the path can start from any node except 'v'
                          o
                         /\
                        1  2
                        end  bitmask   cost
                        1    111        2 start from 2, not 0
                        0    111        3 start from 1 or 2
                        2    111        2 start from 1, not 0
                        This requires not start from only one node.
                        Initial value of shortest_cost[v][bitmask] is MAX_VALUE

       queue<[v,bitmask]> minHeap keep the 'v' and 'bitmask', sorted by cost[v][bitmask] in ascending order

       3 ports:
          - v
          - bitmask
          - cost[v][bitmask]
       so the node status not only include v but also include the bitmask,visited nodes

    Algorithm:
     start from each node: adding each node to the priority queue and turning their bit on.
     Then, Dijkstra algorithm.

     from current node `u` to check each node `v` of his child nodes.
     This is not same as that for TSP where any pair of nodes are connected directly

     if
       shortest_cost[u][current bitmask] + edge-weight[u][v] < shortest_cost[v][current bitmask | 2^v ]

     then need to update the cost value of the child node status cost[v][current bitmask | 2^v ]
     by
       1. update the cost[v][current bitmask | 2^v ]= cost[u][current bitmask] + edge-weight[u][v]
       2. add a new entry [v][current bitmask | 2^v ] to the priority queue.


  O(X log(X)) time, X=V*2^V  V is number of vertex
  O(X) space used by cost array

  Watch a case in the main() and observe:
    - how does it end the loop
    - walk back to revisit: end node is updated, cost is increased, bitmask is same
    - the shortest path is not only one.
    - entry is polled out from queue then cose[v][bitmask] will not change
      this is Dijkstra algorithm property.
  */

  /*
  Assume:
   Edge weight is int type, but it could be double
   Vertex ID is 0~V-1;
   Graph: g[u] is an array int[] keeps v and weight of u-v
   */
  public int modifiedDijkstra(List<int[]>[] g) {
    int N = g.length;
    int[][] cost = new int[N][1 << N];
    // Arrays.fill() only works for 1-D array
    for (int u = 0; u < N; u++) {
      for (int b = 0; b < 1 << N; b++) {
        cost[u][b] = Integer.MAX_VALUE;
      }
    }

    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> cost[a[0]][a[1]]));
    for (int u = 0; u < N; u++) {
      cost[u][1 << u] = 0; // fuck
      q.offer(new int[] {u, 1 << u});
    }

    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int u = cur[0], mask = cur[1];
      for (int[] vw : g[u]) {
        int v = vw[0], w = vw[1], vMask = mask | 1 << v;
        if (cost[v][vMask] > cost[u][mask] + w) {
          cost[v][vMask] = cost[u][mask] + w;
          q.offer(new int[] {v, vMask});
        }
      }
    }
    int r = Integer.MAX_VALUE;
    int fullMask = (1 << N) - 1;
    for (int u = 0; u < N; u++) {
      r = Math.min(r, cost[u][fullMask]);
    }
    return r;
  }
  /*===========================================================================*/
  /*
  The weight of any edge is 1
  */
  public static int shortestPathLength(int[][] g) {
    int N = g.length;
    int[][] cost = new int[N][1 << N];
    // Arrays.fill() only works for 1-D array
    for (int u = 0; u < N; u++) {
      for (int b = 0; b < 1 << N; b++) {
        cost[u][b] = Integer.MAX_VALUE;
      }
    }

    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> cost[a[0]][a[1]]));
    for (int u = 0; u < N; u++) {
      cost[u][1 << u] = 0; // fuck
      q.offer(new int[] {u, 1 << u});
    }

    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int u = cur[0], mask = cur[1];
      for (int v : g[u]) {
        int w = 1, vMask = mask | 1 << v;
        if (cost[v][vMask] > cost[u][mask] + w) {
          cost[v][vMask] = cost[u][mask] + w;
          q.offer(new int[] {v, vMask});
        }
      }
    }
    int r = Integer.MAX_VALUE;
    int fullMask = (1 << N) - 1;
    for (int u = 0; u < N; u++) {
      r = Math.min(r, cost[u][fullMask]);
    }
    return r;
  }

  /* --------------------------------------------------------------------------
  watch a case with the log:
   */
  public static void main(String[] args) {
    shortestPathLength(
        new int[][] {
          {1, 2, 3},
          {0},
          {0},
          {0},
        });

    /*
               0
              /|\
             1 2 3
        Init:
        vertex: 1 mask:0010  cost: 0.
        vertex: 2 mask:0100  cost: 0.
        vertex: 3 mask:1000  cost: 0.
        vertex: 0 mask:0001  cost: 0.

        Start Loop
        vertex: 1 mask:0011  cost: 1.
        vertex: 2 mask:0101  cost: 1.
        vertex: 3 mask:1001  cost: 1.
        vertex: 0 mask:1001  cost: 1.
        vertex: 0 mask:0011  cost: 1.
        vertex: 0 mask:0101  cost: 1.
        vertex: 1 mask:0011  cost: 1. not continue
        vertex: 2 mask:0111  cost: 2.
        vertex: 3 mask:1011  cost: 2.
        vertex: 1 mask:0111  cost: 2.
        vertex: 2 mask:0101  cost: 1. not continue
        vertex: 3 mask:1101  cost: 2.
        vertex: 0 mask:1001  cost: 1. not continue
        vertex: 0 mask:0011  cost: 1. not continue
        vertex: 0 mask:0101  cost: 1. not continue
        vertex: 1 mask:1011  cost: 2.
        vertex: 2 mask:1101  cost: 2.
        vertex: 3 mask:1001  cost: 1. not continue
        vertex: 0 mask:1101  cost: 3.
        vertex: 0 mask:1101  cost: 3. not continue
        vertex: 0 mask:0111  cost: 3.
        vertex: 0 mask:1011  cost: 3.
        vertex: 0 mask:1011  cost: 3. not continue
        vertex: 0 mask:0111  cost: 3. not continue
        vertex: 1 mask:1111  cost: 4.
        vertex: 2 mask:1101  cost: 2. not continue
        vertex: 3 mask:1101  cost: 2. not continue
        vertex: 1 mask:1011  cost: 2. not continue
        vertex: 2 mask:1111  cost: 4.
        vertex: 3 mask:1011  cost: 2. not continue
        vertex: 1 mask:0111  cost: 2. not continue
        vertex: 2 mask:0111  cost: 2. not continue
        vertex: 3 mask:1111  cost: 4.
        vertex: 0 mask:1111  cost: 5.
        vertex: 0 mask:1111  cost: 5. not continue
        vertex: 0 mask:1111  cost: 5. not continue
        vertex: 1 mask:1111  cost: 4. not continue
        vertex: 2 mask:1111  cost: 4. not continue
        vertex: 3 mask:1111  cost: 4. not continue
    */
  }
}
