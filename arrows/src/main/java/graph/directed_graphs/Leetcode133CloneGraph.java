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

/**
 * <img src="../../../resources/graph_list_have_circle_clone.png" height="400" width="600">
 * <a href="https://leetcode.com/problems/clone-graph/">leetcode</a>
 */
public class Leetcode133CloneGraph {

    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) { // care
            return null;
        }
        // map: used to from circle later and keep visited nodes
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

        UndirectedGraphNode nodeClone = new UndirectedGraphNode(node.label);
        map.put(node, nodeClone);

        cloneNexts(node.neighbors, nodeClone, map);
        return nodeClone;
    }

    // DFS deep clone neighbors
    private static void cloneNexts(List<UndirectedGraphNode> curNexts, UndirectedGraphNode curClone,
                                   Map<UndirectedGraphNode, UndirectedGraphNode> map) {
        if (curNexts.isEmpty()) {
            return;
        }

        for (UndirectedGraphNode curNext : curNexts) {
            if (!map.containsKey(curNext)) {
                UndirectedGraphNode curNextClone = new UndirectedGraphNode(curNext.label);
                map.put(curNext, curNextClone);

                curClone.neighbors.add(curNextClone);
                cloneNexts(curNext.neighbors, curNextClone, map);
            } else {
                // need a map. map node to its clone. used here to form the circle in clone graph
                curClone.neighbors.add(map.get(curNext));
            }
        }
    }
}
