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

package graph.shortestpath;

import java.util.Set;
import java.util.Stack;

/**
 * <pre>
 * 1. If a given node has not path to his neighbor node, set the distance to be infinity, Integer.MAX_VALUE;
 * 2. Set start node 'td', 'tentative distance', as 0; 'from node' as null.
 *    Set other node's 'td', 'tentative distance', as infinity, Integer.MAX_VALUE; 'from node' as null.
 *
 * Idea:
 * From start node recursively call:
 *  1 Calculate all its neighbor nodes' td and from. keep the one with smallest td
 *  2 Mark current node as visited
 *  3 Check if it is the time to stop
 *     a. 'to' node is visited
 *     or
 *     b 'std is still Integer.MAX_VALUE', when there is no connection between the start node and
 *     remaining unvisited nodes.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm"> wiki</a>
 */


public class DijkstraShortestPath {
    private static void visit(Node cur, Node to, Set<Node> visted) {
        // Smallest tentative distance from start node
        int std = Integer.MAX_VALUE;
        Node stdn = null;

        for (Node c : cur.nds.keySet()) {
            if (!visted.contains(c)) {
                int td = cur.nds.get(c) + cur.td;
                if (c.td > td) {
                    c.td = td;
                    c.from = cur;
                }
                if (c.td < std) {
                    std = c.td;
                    stdn = c;
                }
            }
        }

        visted.add( cur);
        if (!(cur == to || std == Integer.MAX_VALUE)) {
            visit(stdn, to, visted);
        }
    }

    public static String shortestPath(Node from, Node to, Set<Node> unvisit) {
        from.from = null;

        visit(from, to, unvisit);

        // trace back to from along the result road
        Stack<String> path = new Stack();
        path.push(to.name);
        Node n = to;
        while (n.from != null) {
            path.push(n.from.name);
            n = n.from;
        }

        // print result
        StringBuilder r = new StringBuilder();
        r.append("Shortest path is ");
        boolean first = true;
        while (!path.empty()) {
            if (first) {
                r.append(path.pop());
                first = false;
            }
            r.append(" -> ").append(path.pop());
        }
        return r.toString();
    }
}
