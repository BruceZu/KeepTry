package graph.directed_graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * It may have circle
 */
public class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;

    UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList(); // so it will never be null
    }
}