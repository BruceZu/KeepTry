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

public class Leetcode133CloneGraph_ShiftVersion_0 {
    public static Node cloneGraph(Node node) {
        if (node == null) { // corner case
            return null;
        }
        // Set<Node> visited = new HashSet(); // used to avoid run in endless circle it can be replaced by 'map'
        // map: used to from circle later and keep visited nodes
        Map<Node, Node> map = new HashMap<>();
        Node nodeClone = new Node();
        map.put(node, nodeClone);
        deepClone(node.neighbors, nodeClone, map);
        return nodeClone;
    }

    private static void deepClone(List<Node> curNexts, Node curClone, Map<Node, Node> map) {
        if (curNexts.isEmpty()) {
            return;
        }

        for (Node curNext : curNexts) {
            if (!map.containsKey(curNext)) {
                Node curCloneNext = new Node();
                curClone.neighbors.add(curCloneNext);

                map.put(curNext, curCloneNext); // care

                deepClone(curNext.neighbors, curCloneNext, map);
            } else {
                // need a map. map node to its clone. used here to form the circle in clone graph
                curClone.neighbors.add(map.get(curNext));
            }
        }
    }

    /* -------------------------------------------------------------------------------------- */
    public static void main(String[] args) {
        Node root = new Node();
        Node one = new Node();
        Node two = new Node();
        root.neighbors.add(one);
        root.neighbors.add(two);
        one.neighbors.add(two);
        two.neighbors.add(two);
        Node r = cloneGraph(root);
        System.out.println(r);
    }
}
