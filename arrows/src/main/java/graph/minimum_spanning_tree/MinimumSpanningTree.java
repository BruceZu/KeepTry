//  Copyright 2019 The KeepTry Open Source Project
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

package graph.minimum_spanning_tree;

import graph.Edge;
import graph.IGraph;
import graph.MinimumSpanningTreePrimShortestPathDijkstra;
import java.util.Arrays;
import java.util.Collection;

/**
 * <pre>
 * graph:
 *   connected, edge-weighted, undirected
 *
 * A> Greedy algorithms:
 *
 *  M=|E|, N=|V|
 *  Pre work:
 *    - Remove all self-loops edges for any vertex.
 *    - Remove parallel edges for any 2 vertexes to keep only the one with smallest weigh.
 *    - Mark all vertex with a Id from 0 to |V|-1
 *
 *  1> Kruskal's Spanning Tree Algorithm. O(|E|log|V|).
 *      {@link MinimumSpanningTreeKruskal#kruskal(int, Edge[])}
 *  2> Prim's Minimum Spanning Tree Algorithm.
 *      {@link MinimumSpanningTreePrimShortestPathDijkstra#mstOrSp(IGraph)}
 *
 * B> Todo: improve to O(|E|), if the graph is dense (i.e. m/n ≥ log log log n)  with no isolated vertices
 *
 */
public class MinimumSpanningTree {
    // ------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        /* <A> use edges to present graph and assume there is not isolated parts
                 10
            0---------1
            |  \     |
           6|   5    15
            |      \ |
            2--------3
                4
        */

        Edge[] edges = new Edge[5];
        // assume there is not parallel edges and self-loops
        edges[0] = new Edge(0, 1, 10);
        edges[1] = new Edge(0, 2, 6);
        edges[2] = new Edge(0, 3, 5);
        edges[3] = new Edge(1, 3, 15);
        edges[4] = new Edge(2, 3, 4);
        printResult(MinimumSpanningTreeKruskal.kruskal(4, edges), "Kruskal");
        int[][] graph =
                new int[][] {
                    {0, 10, 6, 5}, {10, 0, 0, 15}, {6, 0, 0, 4}, {5, 15, 4, 0},
                };
        printResult(
                MinimumSpanningTreePrimShortestPathDijkstra.mstOrSp(
                        new IGraphWithMatrixPrimImp(graph)),
                "Prim");

        /* <B> use matrix to represent graph
            2    3
         (0)--(1)--(2)
         |    / \   |
        6| 8/    \5 |7
         | /      \ |
         (3)-------(4)
              9
        */
        edges = new Edge[7];
        // assume there is not parallel edges and self-loops
        edges[0] = new Edge(0, 1, 2);
        edges[1] = new Edge(0, 3, 6);
        edges[2] = new Edge(1, 3, 8);
        edges[3] = new Edge(1, 4, 5);
        edges[4] = new Edge(1, 2, 3);
        edges[5] = new Edge(2, 4, 7);
        edges[6] = new Edge(3, 4, 9);

        printResult(MinimumSpanningTreeKruskal.kruskal(5, edges), "Kruskal");
        graph =
                new int[][] {
                    {0, 2, 0, 6, 0},
                    {2, 0, 3, 8, 5},
                    {0, 3, 0, 0, 7},
                    {6, 8, 0, 0, 9},
                    {0, 5, 7, 9, 0},
                };
        printResult(
                MinimumSpanningTreePrimShortestPathDijkstra.mstOrSp(
                        new IGraphWithMatrixPrimImp(graph)),
                "Prim");

        /*
           _____________
        (0)|\          |\(3)
           | \         | \
           |  \        |  \
        (1)----(2)   (4)----(5)

        */

        edges = new Edge[7];
        // assume there is not parallel edges and self-loops
        edges[0] = new Edge(0, 1, 3);
        edges[1] = new Edge(1, 2, 4);
        edges[2] = new Edge(0, 2, 5);
        edges[3] = new Edge(0, 3, 50);
        edges[4] = new Edge(3, 4, 6);
        edges[5] = new Edge(4, 5, 8);
        edges[6] = new Edge(3, 5, 10);
        printResult(MinimumSpanningTreeKruskal.kruskal(6, edges), "Kruskal");
        graph =
                new int[][] {
                    {0, 3, 5, 50, 0, 0},
                    {3, 0, 4, 0, 0, 0},
                    {5, 4, 0, 0, 0, 0},
                    {50, 0, 0, 0, 6, 10},
                    {0, 0, 0, 6, 0, 8},
                    {0, 0, 0, 10, 8, 0},
                };

        printResult(
                MinimumSpanningTreePrimShortestPathDijkstra.mstOrSp(
                        new IGraphWithMatrixPrimImp(graph)),
                "Prim");
    }

    private static void printResult(Collection<Edge> mst, String algorithmName) {
        System.out.println(algorithmName);
        Object[] r = mst.toArray();
        Arrays.sort(r);
        Arrays.stream(r).forEach(i -> System.out.println(i.toString()));
    }
}
