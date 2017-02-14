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

package graph.shortestpath.weightedgraphs;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * <pre>
 * Scenario:
 * provide: start node and end node;
 *          It maybe a directed graph or undirected graph.
 * confirm if it is not a spanning tree: it there is circle or the end node is a single vertex
 *          (A spanning tree is a subset of Graph G, which has all the vertices covered with
 *          minimum possible number of edges.
 *          Hence, a spanning tree does not have cycles and it cannot be disconnected.)
 *
 * Find shortest path or check if there is path between the start node and the end node.
 *
 * Data structure
 * Node:
 *    map: immediate neighbors and the edge length/distance.
 *    int and node: keep tentative shortest path' distance from start node to this node
 *                  and the related predecessor node on the shortest path.
 *
 *    Initial:
 *     -- map: If a given node has not path to his neighbor node, set the distance to be infinity: Integer.MAX_VALUE;
 *     -- int and node:
 *         for start node: 'td', 'tentative shortest distance', is 0;
 *                         'predecessor node' is null.
 *         for other node: 'td', 'tentative shortest distance', is Integer.MAX_VALUE;
 *                         'predecessor node' is null.
 *
 * Set<Node>:  settled nodes, have found the shortest paths from start node to these nodes .
 * Heap<Node>: sortedBorder, keep nodes under evaluating. a border between settled node and left nodes. Set<Node> borders
 *             is for performance concern.
 * Stack<Node> or StringBuilder:  used to calculate the shortest path.
 *
 * Algorithm:
 *
 * From start node recursively:
 *  1 Calculate current node neighbor nodes' tentative shortest distance and predecessor node, put them in bounders
 *  2 put current node into settled nodes
 *  3 if 'current node' is end node. stop
 *  4 get the top of head as the next current node
 *      if its tentative shortest path' distance is still Integer.MAX_VALUE',
 *      that means here is no connection between the start node and the remaining unvisited nodes.
 *      return "there is no path to end node"
 *      else continue loop
 *
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm"> wiki</a>
 */

public class DijkstraShortestPath {
    private static boolean calculateShortestPath(Node start, Node end) {
        Queue<Node> sortedBorderNodes = new PriorityQueue(); // top is with shortest distance from start
        Set<Node> borderNodes = new HashSet<>();
        
        Set<Node> settled = new HashSet();

        sortedBorderNodes.offer(start);
        while (true) {
            if (sortedBorderNodes.isEmpty()) {
                return false;
            }
            Node toBeSettled = sortedBorderNodes.poll();
            borderNodes.remove(toBeSettled);

            // uniform-cost search
            if (toBeSettled.tentativeShortestDistanceFromStart == Integer.MAX_VALUE) {
                return false;   // check this before the following check
            }

            if (toBeSettled == end) {
                return true;
            }
            // update neighbors tentative shortest distance from start
            for (Node neighbor : toBeSettled.distanceToAdjacentNode.keySet()) {
                if (!settled.contains(neighbor)) {
                    int viaCur =
                            toBeSettled.tentativeShortestDistanceFromStart
                                    + toBeSettled.distanceToAdjacentNode.get(neighbor);
                    if (viaCur < 0) { // distance may be MAX_VALUE
                        viaCur = Integer.MAX_VALUE;
                    }
                    if (viaCur < neighbor.tentativeShortestDistanceFromStart) {
                        neighbor.tentativeShortestDistanceFromStart = viaCur;
                        neighbor.predecessorNode = toBeSettled;
                    }
                    if (!borderNodes.contains(neighbor)) {   //it maybe has been there. avoid repeating calculate
                        sortedBorderNodes.offer(neighbor);
                        borderNodes.add(neighbor);
                    }
                }

            }
            // settled
            settled.add(toBeSettled);
        }
    }

     static String getShortestPath(Node end) {
        // trace back to start node along the shortest path from end
        StringBuilder r = new StringBuilder();
        r.append(end.name);
        Node n = end;
        while (n.predecessorNode != null) {
            r.append(n.predecessorNode.name);
            n = n.predecessorNode;
        }
        return r.reverse().toString();
    }

    // Assume all nodes has been initialed
    // calculate shortest path from start node to end node
    // if there is not path between them, return false.
    public static String shortestPath(Node start, Node end) {
        boolean hasPath = calculateShortestPath(start, end);
        if (hasPath) {
            return getShortestPath(end);
        } else {
            return "this not path betwen start " + start.name + " and end " + end.name;
        }
    }

    // -------------------------------------------------------------
    public static void main(String[] args) {
        // todo test dircted graph;

        /**
         * <pre>
         *          f    -- 9 --  e
         *        /   \             \
         *       /     \             \
         *     14       2            6
         *     /         \            \
         *  a   - 9 -     c   - 11 -   d
         *   \           /           /
         *    7        10          15
         *      \      /        /
         *       \    /      /
         *            b
         */
        Map<Node, Integer> startNodeDistanceTo = new HashMap();
        Map<Node, Integer> bNodeDistanceTo = new HashMap();
        Map<Node, Integer> cNodeDistanceTo = new HashMap();
        Map<Node, Integer> dNodeDistanceTo = new HashMap();
        Map<Node, Integer> eNodeDistanceTo = new HashMap();
        Map<Node, Integer> fNodeDistanceTo = new HashMap();

        Node start = new Node("a", startNodeDistanceTo, 0);
        Node b = new Node("b", bNodeDistanceTo);
        Node c = new Node("c", cNodeDistanceTo);
        Node e = new Node("e", eNodeDistanceTo);
        Node f = new Node("f", fNodeDistanceTo);
        Node end = new Node("d", dNodeDistanceTo);

        startNodeDistanceTo.put(b, 7);
        startNodeDistanceTo.put(c, 9);
        startNodeDistanceTo.put(f, 14);

        bNodeDistanceTo.put(start, 7);
        bNodeDistanceTo.put(c, 10);
        // bNodeDistanceTo.put(c, Integer.MAX_VALUE); TEST 1
        bNodeDistanceTo.put(end, 15);
        // bNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2   this not path between start a and end d

        cNodeDistanceTo.put(start, 9);
        cNodeDistanceTo.put(b, 10);
        cNodeDistanceTo.put(end, 11);
        // cNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2
        cNodeDistanceTo.put(f, 2);

        fNodeDistanceTo.put(start, 14);
        fNodeDistanceTo.put(c, 2);
        fNodeDistanceTo.put(e, 9);

        eNodeDistanceTo.put(f, 9);
        eNodeDistanceTo.put(end, 6);
        // eNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2

        dNodeDistanceTo.put(e, 6);
        // dNodeDistanceTo.put(e, Integer.MAX_VALUE); // TEST 2
        dNodeDistanceTo.put(c, 11);
        // dNodeDistanceTo.put(c, Integer.MAX_VALUE); // TEST 2
        dNodeDistanceTo.put(b, 15);
        // dNodeDistanceTo.put(b, Integer.MAX_VALUE); // TEST 2

        System.out.println(DijkstraShortestPath.shortestPath(start, end));
    }
}
