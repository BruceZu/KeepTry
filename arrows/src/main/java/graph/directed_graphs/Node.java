package graph.directed_graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * may have circle
 */
public class Node {
    //int label; // This is just for test. It can be saved
    List<Node> neighbors;

    Node() {
        neighbors = new ArrayList();
    }
}
