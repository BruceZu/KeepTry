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

package graph.shortest_path_tree;

public class ShortestPathTree {
  /*
   an edge-weighted directed graph and a vertex s,
   a shortest-paths tree for a source s is a subgraph containing s
   and all the vertices reachable from s that forms a directed tree rooted at s
   such that every tree path is a shortest path in the digraph.

   For any other node v in graph G, the shortest path between s and v is
   a path such that the total weight of the edges along this path is minimized.
   Therefore, the objective of the shortest path tree problem is to find a spanning
   tree such that the path from the source node s to any other node v is the
   shortest one in G.


   undirected graph can be converted to be as a digraph graph

  Idea:
     based on a simple operation known as relaxation.

    Ex 1. Dijkstra's algorithm (nonnegative weights).
    Ex 2. Topological sort algorithm (no directed cycles). (with negative weights allowed)
          weights are negated
    Ex 3. Bellman-Ford algorithm (no negative cycles).

  */
  /*
  An undirected connected graph with edge weight as 1.
  It can have more minimum spanning tree.

  For a given minimum spanning tree. the shortest path across all vertex
  can be got by starting from a vertex which determined tree has the highest height
  */

}
