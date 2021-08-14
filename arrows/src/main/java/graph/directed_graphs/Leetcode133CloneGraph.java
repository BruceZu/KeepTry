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

package graph.directed_graphs;

import java.util.*;

public class Leetcode133CloneGraph {

  static class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
      val = 0;
      neighbors = new ArrayList<>();
    }

    public Node(int _val) {
      val = _val;
      neighbors = new ArrayList<>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
      val = _val;
      neighbors = _neighbors;
    }
  }
  // implement ------------------------------------------------------------------
  /*
  1 <= Node.val <= 100
  Node.val is unique for each node.
  Number of Nodes will not exceed 100.
  There is no repeated edges and no self-loops in the graph.
  The Graph is connected and all nodes can be visited starting from the given node.
  */
  /*
  Understanding
    it may have circle(s),
    it is undirected graph
  Idea:
   DFS. With a created map of `node:its clone` to avoid duplicated clone
   O(N) time and space

   without Map, only a Set<Node> visited, no way to know if a given node is cloned or not
   dfs return cloned node
     - new root
     - as neighbor of above layer cloned node
     no matter it is one has been cloned or just cloned, need return to the above layer to
     maintain the edges especially in circle
  */
  public Node cloneGraph(Node node) {
    return dfs(node, new HashMap<>());
  }

  // clone graph with root n
  private Node dfs(Node n, Map<Integer, Node> map) {
    if (n == null) return null;
    if (map.containsKey(n.val)) return map.get(n.val);

    Node c = new Node(n.val);
    map.put(n.val, c);
    for (Node nei : n.neighbors) c.neighbors.add(dfs(nei, map));

    return c;
  }
}
