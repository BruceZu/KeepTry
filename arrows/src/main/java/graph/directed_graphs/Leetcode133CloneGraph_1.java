package graph.directed_graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/clone-graph/">leetcode</a>
 */
public class Leetcode133CloneGraph_1 {
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) { // care
            return null;
        }
        // map: used to from circle later and keep visited nodes
        return cloneOf(node, new HashMap<>());
    }

    // deep clone by deep fist search
    private static UndirectedGraphNode cloneOf(UndirectedGraphNode cur,
                                               Map<UndirectedGraphNode, UndirectedGraphNode> map) {
        if (!map.containsKey(cur)) {
            UndirectedGraphNode curClone = new UndirectedGraphNode(cur.label);
            map.put(cur, curClone);

            for (UndirectedGraphNode curNext : cur.neighbors) {
                curClone.neighbors.add(cloneOf(curNext, map));
            }
        }
        return map.get(cur);
    }
}
