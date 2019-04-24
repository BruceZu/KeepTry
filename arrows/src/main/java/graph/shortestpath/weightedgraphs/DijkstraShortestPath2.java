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

import static graph.shortestpath.weightedgraphs.DijkstraShortestPath.getShortestPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * Difference with DijkstraShortestPath
 *
 * put all nodes in heap in advance
 *
 * pros:
 *
 * -- no 'Set<Node> evaluated'
 * -- need not know which is the start node
 *
 * cons:
 * -- initially all nodes are entered into the priority queue. This is, however, not necessary
 *    especially when memory is not enough.
 */
public class DijkstraShortestPath2 {

    public static boolean hashShortestPath(Set<Node> graph, Node end) {
        IBinaryHeap evaluating = new IBinaryHeap(32);

        for (Node n : graph) {
            evaluating.offer(n);
        }
        while (!evaluating.isEmpty()) {
            Node toBeSttled = evaluating.poll();
            if (toBeSttled.shortDisFromStart == Integer.MAX_VALUE) {
                return false;
            }
            if (toBeSttled == end) {
                return true;
            }
            for (Node neighbor : toBeSttled.neighborDistance.keySet()) {
                int alt = toBeSttled.shortDisFromStart + toBeSttled.neighborDistance.get(neighbor);
                if (alt < 0) {
                    alt = Integer.MAX_VALUE;
                }
                if (alt < neighbor.shortDisFromStart) {
                    neighbor.shortDisFromStart = alt;
                    neighbor.pre = toBeSttled;
                    if (evaluating.index(neighbor) == null) {
                        evaluating.offer(neighbor); // O(logN)
                    } else {
                        evaluating.shiftUp(neighbor, evaluating.index(neighbor)); // O(logN)
                    }
                }
            }
        }
        return false;
    }

    // Assume all nodes has been initialed
    // calculate shortest path from start node to end node
    // if there is not path between them, return false.
    public static String shortestPath(Set<Node> graph, Node start, Node end) {
        boolean hasPath = hashShortestPath(graph, end);
        if (hasPath) {
            return getShortestPath(end);
        } else {
            return "this not path betwen start " + start.name + " and end " + end.name;
        }
    }

    // -------------------------------------------------------------
    public static void main(String[] args) {
        // todo test dircted graph;
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
        Map<Node, Integer> startNodeDistanceTo = new HashMap();
        Map<Node, Integer> bNodeDistanceTo = new HashMap();
        Map<Node, Integer> cNodeDistanceTo = new HashMap();
        Map<Node, Integer> dNodeDistanceTo = new HashMap();
        Map<Node, Integer> eNodeDistanceTo = new HashMap();
        Map<Node, Integer> fNodeDistanceTo = new HashMap();

        Node start = new Node("a", startNodeDistanceTo, 0);
        Node b = new Node("b", cNodeDistanceTo);
        Node e = new Node("e", eNodeDistanceTo);
        Node c = new Node("c", bNodeDistanceTo);
        Node f = new Node("f", fNodeDistanceTo);
        Node end = new Node("d", dNodeDistanceTo);

        startNodeDistanceTo.put(b, 7);
        startNodeDistanceTo.put(c, 9);
        startNodeDistanceTo.put(f, 14);

        bNodeDistanceTo.put(start, 7);
        // bNodeDistanceTo.put(c, 10);
        bNodeDistanceTo.put(c, Integer.MAX_VALUE); // TEST 1
        bNodeDistanceTo.put(end, 15);
        // bNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2   this not path between start a
        // and end d

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

        System.out.println(
                shortestPath(new HashSet<>(Arrays.asList(start, e, end, b, c, f)), start, end));
    }
}
