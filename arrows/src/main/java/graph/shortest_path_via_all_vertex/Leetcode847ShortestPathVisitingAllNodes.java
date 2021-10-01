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

public class Leetcode847ShortestPathVisitingAllNodes {
  /*
  847. Shortest Path Visiting All Nodes

  You have an undirected, connected graph of n nodes labeled from 0 to n - 1.
  You are given an array graph where graph[i] is a list of all the nodes connected with node i by an edge.

  Return the length of the shortest path that visits every node.
  You may start and stop at any node, you may revisit nodes multiple times, and you may reuse edges.

  Input: graph = [[1,2,3],[0],[0],[0]]
  Output: 4
  Explanation: One possible path is [1,0,2,0,3]
  Example 2:


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

  /*
  g[u] is an array keeps [v1, v2, v3] that there is edge u-v1, u-v2, u-v3.
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
    // Queue<int[]> q = new LinkedList();
    // this works why? see the while loop condition (!q.isEmpty()) and queue offer condition
    for (int u = 0; u < N; u++) {
      cost[u][1 << u] = 0; // !Note: initial value
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
  //
  /*
  General Idea from https://www.baeldung.com/cs/shortest-path-visiting-all-nodes
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


  X=V*2^V,  V is number of vertex
  O(X log(X)) time,
  O(X) time,  with `Queue<int[]> q = new LinkedList();`
  O(X) space. used by cost array

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
   Graph: g[u] is an int array list, each array keeps [v, u-v]
   */
  public int modifiedDijkstra(List<int[]>[] g) {
    int N = g.length;
    int[][] cost = new int[N][1 << N];
    for (int u = 0; u < N; u++) {
      // Arrays.fill() only works for 1-D array
      Arrays.fill(cost[u], Integer.MAX_VALUE);
    }

    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> cost[a[0]][a[1]]));
    // Queue<int[]> q = new LinkedList();
    // this works why? see the while loop condition (!q.isEmpty()) and queue offer condition

    for (int u = 0; u < N; u++) {
      cost[u][1 << u] = 0; // !Note: initial value
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
}
