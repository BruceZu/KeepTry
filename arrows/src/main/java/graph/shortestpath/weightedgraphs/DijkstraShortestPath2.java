//  Copyright 2017 The keepTry Open Source Project
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

package graph.shortestpath.weightedgraphs;

import graph.IBinaryHeap;
import graph.IVertex;
import graph.Node;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *
 * Difference with DijkstraShortestPath
 *
 *
 * put all nodes in heap in advance
 *
 * pros:
 *    no 'Set<Node> evaluated' Because visited nodes has got the shortest
 *    distance from the start node to it and will not be changed. And most
 *    importantly current node will never add the neighbor node in the heap
 *    again.
 *
 * cons:
 *    initially all nodes are entered into the priority queue. This is, however, not necessary
 *    especially when memory is not enough.
 */
public class DijkstraShortestPath2 {

  public static boolean hashShortestPath(Set<Node> graph, Node end, Node start) {
    IBinaryHeap<Node> q = new IBinaryHeap(32);
    graph.stream().forEach(n -> n.setShortestDistanceToI(Integer.MAX_VALUE));
    start.setShortestDistanceToI(0);

    for (Node n : graph) q.offer(n);

    while (!q.isEmpty()) {
      Node f = q.poll();
      if (f.getShortestDistanceToI() == Integer.MAX_VALUE) return false;
      if (f == end) return true;

      for (Node t : f.getNeighborWeighMap().keySet()) {
        int cost = f.getShortestDistanceToI() + f.getNeighborWeighMap().get(t);
        if (cost < 0) cost = Integer.MAX_VALUE;
        if (cost < t.getShortestDistanceToI()) {
          t.setShortestDistanceToI(cost);
          t.setVertexToI(f);
          q.shiftUp(t); // O(logN)
        }
      }
    }
    return false;
  }

  static String getShortestPath(Node end) {
    // trace back to start node along the shortest path from end
    StringBuilder r = new StringBuilder();
    r.append(end.getName());
    IVertex n = end;
    while (n.getVertexToI() != null) {
      r.append(n.getVertexToI().getName());
      n = n.getVertexToI();
    }
    return r.reverse().toString();
  }

  // Assume all nodes has been initialed
  // calculate shortest path from start node to end node
  // if there is not path between them, return false.
  public static String shortestPath(Set<Node> graph, Node start, Node end) {
    boolean hasPath = hashShortestPath(graph, end, start);
    if (hasPath) return getShortestPath(end);
    else return "this not path betwen start " + start.getName() + " and end " + end.getName();
  }

  // -------------------------------------------------------------
  public static void main(String[] args) {
    // todo test directed graph;
    // todo test: it will be wrong if does not resort the border nodes

    /**
     * <pre>
     *          f    -- 9 --  e
     *        /   \             \
     *       /     \             \
     *     14       2            6
     *     /         \            \
     *  a   - 9 -     b   - 11 -   d
     *   \           /           /
     *    7        10          15
     *      \      /        /
     *       \    /      /
     *            c
     */
    Map<Node, Integer> aNei = new HashMap();
    Map<Node, Integer> cNei = new HashMap();
    Map<Node, Integer> bNei = new HashMap();
    Map<Node, Integer> dNei = new HashMap();
    Map<Node, Integer> eNei = new HashMap();
    Map<Node, Integer> fNei = new HashMap();

    Node a = new Node("a", aNei);
    Node b = new Node("b", bNei);
    Node c = new Node("c", cNei);
    Node d = new Node("d", dNei);
    Node e = new Node("e", eNei);
    Node f = new Node("f", fNei);

    aNei.put(f, 14);
    aNei.put(b, 9);
    aNei.put(c, 7);

    bNei.put(a, 9);
    bNei.put(f, 2);
    bNei.put(d, 11);
    bNei.put(c, 10);

    cNei.put(a, 7);
    cNei.put(b, 10);
    cNei.put(d, 15);

    dNei.put(c, 15);
    dNei.put(b, 11);
    dNei.put(e, 6);

    eNei.put(f, 9);
    eNei.put(d, 6);

    fNei.put(e, 9);
    fNei.put(b, 2);
    fNei.put(a, 14);

    System.out.println(
        shortestPath(new HashSet<>(Arrays.asList(a, e, d, b, c, f)), a, d).equals("abd"));
  }
}
