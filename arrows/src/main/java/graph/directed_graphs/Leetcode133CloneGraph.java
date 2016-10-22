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
