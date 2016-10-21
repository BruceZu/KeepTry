package graph.directed_graphs;

import java.util.*;

/**
 * Created by brucezu on 10/21/16.
 */
public class Leetcode133CloneGraph_ShiftVersion {
    // circle may have in undirectedGraphNode
    static class Node {
        int label; // This is just for test. It can be saved
        List<Node> neighbors;

        Node(int label) {
            this.label = label;
            neighbors = new ArrayList();
        }
    }

    public static Node cloneGraph(Node node) {
        if (node == null) { // corner case
            return null;
        }

        Set<Node> visited = new HashSet(); // used to avoid run in endless circle
        Map<Node, Node> mapForFormCircle = new HashMap<>(); // used to from circle in clone graph

        Node cloneStart = new Node(node.label);

        visited.add(node);
        mapForFormCircle.put(node, cloneStart);

        deepClone(node.neighbors, cloneStart, visited, mapForFormCircle);

        return cloneStart;
    }

    private static void deepClone(List<Node> nexts, Node clone,
                                  Set<Node> visited, Map<Node, Node> mapForFormCircle) {

        if (nexts == null || nexts.isEmpty()) {
            return;
        }

        for (Node curNext : nexts) {
            if (!visited.contains(curNext)) {

                Node curCloneNext = new Node(curNext.label);
                clone.neighbors.add(curCloneNext);

                visited.add(curNext); // care
                mapForFormCircle.put(curNext, curCloneNext); // care

                deepClone(curNext.neighbors, curCloneNext, visited, mapForFormCircle);
            } else {
                // Here is why need a map between node and its clone to
                // form the circle in clone graph
                clone.neighbors.add(mapForFormCircle.get(curNext));
            }
        }
    }

    public static void main(String[] args) {
        Node root = new Node(0);
        Node one = new Node(1);
        Node two = new Node(2);
        root.neighbors.add(one);
        root.neighbors.add(two);
        one.neighbors.add(two);
        two.neighbors.add(two);
        Node r = cloneGraph(root);
        System.out.println(r);
    }
}
