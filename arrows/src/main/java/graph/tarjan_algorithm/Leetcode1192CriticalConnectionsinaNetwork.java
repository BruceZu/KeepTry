//  Copyright 2022 The KeepTry Open Source Project
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

package graph.tarjan_algorithm;

import javax.print.DocFlavor;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Leetcode1192CriticalConnectionsinaNetwork {
  /*
  1192. Critical Connections in a Network

  There are n servers numbered from 0 to n - 1 connected by
  undirected server-to-server connections
  forming a network where connections[i] = [ai, bi] represents a connection
  between servers ai and bi.
  Any server can reach other servers directly or indirectly through the network.

  A critical connection is a connection that, if removed,
  will make some servers unable to reach some other server.

  Return all critical connections in the network in any order.


  Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
  Output: [[1,3]]
  Explanation: [[3,1]] is also accepted.
  Example 2:

  Input: n = 2, connections = [[0,1]]
  Output: [[0,1]]


  Constraints:

  2 <= n <= 10^5
  n - 1 <= connections.length <= 10^5
  0 <= ai, bi <= n - 1
  ai != bi
  There are no repeated connections.
  */

  /*
       Idea
       given graph is connected graph
       all circles.
       edges in all back edge (or the loop/circle)
       left edges
       spanning tree


       Tarjan's algorithm=> get all strongly connected components in directed graph
       https://en.wikipedia.org/wiki/Bridge_%28graph_theory%29#Tarjan's_bridge-finding_algorithm
       The first linear time algorithm for finding the bridges in a graph. Robert Tarjan, in 1974.

       - "bridge": an edge is a bridge if and only if it is not contained in any cycle.
       - "cut": For a connected graph, a bridge can uniquely determine a cut.
       - "cut-set": Any cut determines a cut-set, the set of edges that have one endpoint in each subset of the partition.
         These edges are said to cross the cut. In a connected graph, each cut-set determines a unique cut,
         and in some cases cuts are identified with their cut-sets rather than with their vertex partitions.
       - "trees": A graph with n nodes can contain at most n-1 bridges, since adding additional edges must create a cycle.
         The graphs with exactly n-1 bridges are exactly the trees, and the graphs in which every edge is
         a bridge are exactly the forests.
       - "articulation vertices": The two endpoints of a bridge are articulation vertices unless they have a degree of 1

    vertex (plural: vertices)

    https://imgur.com/2QlvBoF.png
  */
  interface Graph {
    // Map<Integer, List<Integer>> g;
    /*  return vertex neighbor */
    List<Integer> adj(int v);
    /* return vertex number */
    int V();
  }

  int bridges;
  int[] hi; // current subtree reachable highest layer visited vertex in dfs path
  int[] vis;
  int cnt; // visited vertex is given a number

  public int components(Graph G) {
    hi = new int[G.V()];
    vis = new int[G.V()];
    for (int v = 0; v < G.V(); v++) hi[v] = -1;
    for (int v = 0; v < G.V(); v++) vis[v] = -1;

    for (int v = 0; v < G.V(); v++) {
      if (vis[v] == -1) {
        dfs(G, v, v); // not check the entry vertex is bridge or not.
      }
    }
    return bridges + 1; // spanning tree
  }

  /*
  pre: visited vertex ID
  v: not visited next vertex ID
  3 vertex:   pre -  v - w
  */
  private void dfs(Graph G, int pre, int v) {
    hi[v] = vis[v] = cnt++;
    for (int w : G.adj(v)) {
      if (vis[w] == -1) {
        dfs(G, v, w);
        hi[v] = Math.min(hi[v], hi[w]);
        if (hi[w] == vis[w]) {
          // check unvisited w not v, means an edge to w will be a bridge. The highest vertex we can
          // reach from
          // v is v itself.
          System.out.println(v + "-" + w + " is a bridge");
          bridges++;
        }
      } else if (w != pre) {
        // w is visited and is not the neighbor vertex of v in indirection graph. ignore `reverse of
        // edge leading to v`
        hi[v] = Math.min(hi[v], vis[w]);
      }
    } // end for
  }
}
