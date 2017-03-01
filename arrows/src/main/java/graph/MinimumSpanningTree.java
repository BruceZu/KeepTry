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

package graph;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Edge implements Comparable<Edge> {
    int v1ID, v2ID, weight;

    public Edge(int id, int id2, int weight) {
        this.v1ID = id;
        this.v2ID = id2;
        this.weight = weight;
    }

    @Override
    public int compareTo(@NotNull Edge o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return v1ID + "-" + v2ID + ", weight: " + weight;
    }
}

/**
 * <pre>
 * <a href="https://en.wikipedia.org/wiki/Minimum_spanning_tree"> wiki</a>
 * what ===
 * graph:
 *   connected, edge-weighted, undirected
 *
 * Minimum_spanning_tree: edges,
 *   connects all the vertices together.
 *   without any cycles
 *   with the minimum possible total edge weight.
 *
 * Properties===
 *   1 n vertices in the graph, then each spanning tree has n − 1 edges
 *   2 If each edge has a distinct weight then there will be only one, unique minimum spanning tree.
 *     If edges can have equal weights, the minimum spanning tree may not be unique
 *   3 If the weights are positive, then a minimum spanning tree is a minimum-cost subgraph connecting all vertices
 *   4 For any cycle C in the graph, if the weight of an edge e of C is larger than the individual weights
 *     of all other edges of C, then this edge cannot belong to a MST.
 *   5 For any cut C of the graph, if the weight of an edge e in the cut-set of C is strictly smaller
 *     than the weights of all other edges of the cut-set of C, then this edge belongs to all MSTs of the graph.
 *   6 If the minimum cost edge e of a graph is unique, then this edge is included in any MST.
 *   7 for a MST, adding an edge that connects two vertices in a tree creates a unique cycle.
 *     Removing an edge from a MST breaks it into two separate subtrees.
 *
 * how  ===
 * greedy algorithms:
 * Kruskal's Spanning Tree Algorithm. O(m log n) m is number of edges and n is number of vertex
 *      1 - Remove all self-loops and Parallel Edges
 *      2 - Arrange all edges in their increasing order of weight
 *      3 - Add the edge which has the least weightage, until there are (V-1) edges in the spanning tree.
 *           keep checking that the spanning properties remain intact.
 *           e.g. ignore/avoid all edges that create a circuit.
 *
 * Prim's Spanning Tree Algorithm.  similar with the shortest path first algorithms.
 *     it grows the MST (T) one edge at a time. Initially,
 *     T contains an arbitrary vertex. In each step,
 *     T is augmented with a least-weight edge (x,y) such that
 *     x is in T and y is not yet in T.
 *     By the Cut property, all edges added to T are in the MST.
 *     Its run-time is either O(m log n) or O(m + n log n), depending on the data-structures used.
 *
 *     Step 1 - Remove all self-loops and Parallel Edges
 *     Step 2 - Choose any arbitrary node as root node
 *     Step 3 - Check outgoing edges,cut set of edges, and select the one with less cost, always taking next the minimum-weight edge
 *              that connects a vertex on the tree to a vertex not yet on the tree
 *              (a crossing edge for the cut defined by tree vertices).
 *
 *    Lazy implementation. We use a priority queue to hold the crossing edges, cut set of edges, and find one of minimal weight.
 *    Each time that we add an edge to the tree, we also add a vertex to the tree.
 *
 *    To maintain the set of crossing edges, we need to add to the priority queue all crossing edges,
 *    (with exactly one endpoint in MST) from that vertex to any non-tree vertex,
 *    so ignore edges connecting two tree vertices. our only interest is in the minimal edge
 *    from each non-tree vertex to a tree vertex
 */
public class MinimumSpanningTree {
    // ----------------------------------  Kruskal's algorithm  -------------------------------
    // http://www.geeksforgeeks.org/greedy-algorithms-set-2-kruskals-minimum-spanning-tree-mst/
    static private int find(int[] rootOf, int vID) {
        //  path compression
        if (rootOf[vID] != vID) {
            rootOf[vID] = find(rootOf, rootOf[vID]);
        }
        return rootOf[vID];
    }

    // using union-found to check circle
    static public Edge[] getOneMST_Kruskal_UnionFound(int numberOfV,
                                                      Edge[] sortedEges) { // data structure 1
        Edge[] MST = new Edge[numberOfV - 1];

        Arrays.sort(sortedEges);
        // O(MlgM) M is the number of edges. the M at most is N^2, N is the number of vertex

        int[] rootOf = new int[numberOfV]; // data structure 2
        for (int v = 0; v < numberOfV; ++v) {
            rootOf[v] = v;
        }

        int i = 0;
        int size = 0;
        do { // O(number of V)
            Edge curEdge = sortedEges[i];
            int x = find(rootOf, curEdge.v1ID);
            int y = find(rootOf, curEdge.v2ID);
            if (x != y) {
                MST[size++] = curEdge;
                rootOf[x] = y; // union
            }
            i++;
        } while (size < numberOfV - 1);
        return MST;
    }

    // using set to check circle
    static public Edge[] getOneMST_Kruskal_Set(int numberOfV,
                                               Edge[] sortedEges) {
        Edge[] MST = new Edge[numberOfV - 1];

        Arrays.sort(sortedEges);

        Set<Integer> MSTVs = new HashSet<>();
        int i = 0;
        int size = 0;
        do {
            Edge curEdge = sortedEges[i];
            if (MSTVs.contains(curEdge.v1ID) && MSTVs.contains(curEdge.v2ID)) {
                i++;
                continue;
            }
            MSTVs.add(curEdge.v1ID);
            MSTVs.add(curEdge.v2ID);
            MST[size++] = curEdge;
            i++;
        } while (size < numberOfV - 1);
        return MST;
    }
    // ----------------------------------  Prim's algorithm -------------------------------
    // http://www.geeksforgeeks.org/greedy-algorithms-set-5-prims-minimum-spanning-tree-mst-2/

    /**
     * 四人帮的book:
     * The {@code PrimMST} class represents a data type for computing a
     * <em>minimum spanning tree</em> in an edge-weighted graph.
     * The edge weights can be positive, zero, or negative and need not
     * be distinct. If the graph is not connected, it computes a <em>minimum
     * spanning forest</em>, which is the union of minimum spanning trees
     * in each connected component. The {@code weight()} method returns the
     * weight of a minimum spanning tree and the {@code edges()} method
     * returns its edges.
     * <p>
     * This implementation uses <em>Prim's algorithm</em> with an indexed
     * binary heap.
     * The constructor takes time proportional to <em>E</em> log <em>V</em>
     * and extra space (not including the graph) proportional to <em>V</em>,
     * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
     * Afterwards, the {@code weight()} method takes constant time
     * and the {@code edges()} method takes time proportional to <em>V</em>.
     * <p>
     */

    //  graph represented by adjacency matrix
    static public int[] getOneMSTOf(int edges[][]) { // data structure A, -> List<E>[]
        int numberOfV = edges.length;

        int otherVInMST_MinEdgeWeigh_From[] = new int[numberOfV]; // data structure 1
        int weighOf[] = new int[numberOfV]; // data structure 2.  can be a heap
        boolean isInMST[] = new boolean[numberOfV]; // data structure 3

        for (int i = 0; i < numberOfV; i++) {
            weighOf[i] = Integer.MAX_VALUE;
            isInMST[i] = false;
        }
        int startV = 0;
        weighOf[startV] = 0;
        otherVInMST_MinEdgeWeigh_From[startV] = -1; // special one

        // O(V^2)
        for (int MSTEdgesNumber = 0; MSTEdgesNumber < numberOfV - 1; MSTEdgesNumber++) {
            int selectedV = vOfMinmumEInCutSet(weighOf, isInMST);
            isInMST[selectedV] = true;

            for (int toV = 0; toV < numberOfV; toV++) {
                if (edges[selectedV][toV] != 0 /* there is edge */
                        && isInMST[toV] == false /* is in cut set */
                        && edges[selectedV][toV] < weighOf[toV]/*  */) {
                    weighOf[toV] = edges[selectedV][toV];
                    otherVInMST_MinEdgeWeigh_From[toV] = selectedV;
                }
            }
        }
        return otherVInMST_MinEdgeWeigh_From;
    }

    // assume v is labeled from 0~numberOfV-1;
    // O(V)
    // if using heap-> O(logV)
    static private int vOfMinmumEInCutSet(int weighOf[], boolean isInMST[]) {
        int min_weigh = Integer.MAX_VALUE;
        int min_v = -1;

        for (int v = 0; v < weighOf.length; v++) {
            if (isInMST[v] == false && weighOf[v] < min_weigh) {
                min_weigh = weighOf[v];
                min_v = v;
            }
        }
        return min_v;
    }

    // ------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        /*
                 10
            0---------1
            |  \     |
           6|   5    15
            |      \ |
            2--------3
                4
        */
        int numberOfV = 4;
        Edge[] edges = new Edge[5];
        // assume there is not parallel edges and self-loops
        edges[0] = new Edge(0, 1, 10);
        edges[1] = new Edge(0, 2, 6);
        edges[2] = new Edge(0, 3, 5);
        edges[3] = new Edge(1, 3, 15);
        edges[4] = new Edge(2, 3, 4);
        Edge[] MST = getOneMST_Kruskal_Set(numberOfV, edges);
        System.out.println("One Constructed MST: ");
        for (int i = 0; i < numberOfV - 1; ++i) {
            System.out.println(MST[i]);
        }

        /*
           2    3
        (0)--(1)--(2)
        |    / \   |
       6| 8/    \5 |7
        | /      \ |
        (3)-------(4)
             9
       */
        int[][] graph = new int[][]{{0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0},
        };
        int[] rootOf = getOneMSTOf(graph);
        System.out.println("Edge   Weight");
        for (int i = 1; i < graph.length; i++)
            System.out.println(rootOf[i] + " - " + i + "    " +
                    graph[i][rootOf[i]]);
    }
}
