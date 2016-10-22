package graph.directed_graphs;

import java.util.HashMap;
import java.util.Map;

public class Leetcode133CloneGraph_ShiftVersion_1 {
    public static Node cloneGraph(Node node) {
        if (node == null) { // care
            return null;
        }
        // map: used to from circle later and keep visited nodes  
        return cloneOf(node, new HashMap<>());
    }

    // deep clone by deep fist search
    private static Node cloneOf(Node cur, Map<Node, Node> map) {
        if (!map.containsKey(cur)) {
            Node curClone = new Node();
            map.put(cur, curClone);

            for (Node curNext : cur.neighbors) {
                curClone.neighbors.add(cloneOf(curNext, map));
            }
        }
        return map.get(cur);
    }
}
