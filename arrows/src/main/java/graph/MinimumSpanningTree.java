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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

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
 *    - Mark all vertex with a Id from 0 to N-1
 *
 *  1> Kruskal's Spanning Tree Algorithm. O(MlogN).
 *      1 - sort edges in increasing order of weight.
 *      2 - Add the edge which has the least weight, until there are (V-1) edges in the spanning tree.
 *          keep checking that the spanning properties remain intact.
 *          e.g. ignore/avoid all edges that create a circuit.
 *
 *  2> Prim's Spanning Tree Algorithm.
 *   TODO improvement to O(MlogN), O((M+N)logN), O(MlogN + logN!)
 *   - 1 customize a priority queue (with a binary heap and an inner array mapping the index(vertex Id)
 *   to its index in binary heap. O(1)).
 *   implements only
 *    - decreaseAt(int index)
 *    - pop()
 *   This requires updating the array every time moving an item.
 *   decreaseAt(int index) runtime complexity is O(logSize). In fact the binary heap size
 *   will be N....1 during the loop.
 *
 *   - 2 Using adjacent nodes structure to represent graph.
 *
 *   Todo: improve to O(M+NlogN) using Fibonacci heap and adjacent nodes
 *   Todo: for Fibonacci heap the deCrease(key) need firstly find the target's key.
 *
 * B> Todo: improve to O(M), if the graph is dense (i.e. m/n ≥ log log log n)  with no isolated vertices
 *
 */
public class MinimumSpanningTree {

    private static class Edge implements Comparable<Edge> {
        int from, to, w;

        public Edge(int vertex1Id, int vertex2Id, int weight) {
            this.from = vertex1Id;
            this.to = vertex2Id;
            this.w = weight;
        }

        @Override
        public int compareTo(@NotNull Edge o) {
            return this.w - o.w;
        }

        @Override
        public String toString() {
            return from + "-" + to + ", weight: " + w;
        }
    }

    /**
     * Using both path compression and union by rank ensures that the amortized time per operation
     * is only O(alpha (n)) alpha (n) is the inverse Ackermann function. This function has a value
     * alpha (n)<5 for any value of n that can be written in this physical universe, so the
     * disjoint-set operations take place in essentially constant time.
     */
    // O(alpha (n))
    // prefer path compression to path halving or path splitting
    public static int root(int[] next, int v) {
        if (next[v] != v) {
            next[v] = root(next, next[v]);
        }
        return next[v];
    }
    // O(alpha (n))
    // union by rank
    public static void merge(int next[], int rank[], int r1 /*root of one*/, int r2) {
        int el /* equal or less than*/, o /* other*/;
        if (rank[r1] <= rank[r2]) {
            el = r1;
            o = r2;
        } else {
            el = r2;
            o = r1;
        }

        next[el] = o;
        if (rank[el] == rank[o]) {
            rank[o]++;
        }
    }

    /**
     * @param N = |V|, M = |E|
     * @param edges graph represented by edges array
     */
    public static Collection<Edge> mstKruskal(int N, Edge[] edges) {

        List<Edge> r = new ArrayList();

        Arrays.sort(edges); // O(MlogM). M <= N(N-1)<N^2, O(MlogM)-> O(MlogN)

        // data structure 2: union-find checks circle
        int[] next = new int[N];
        int[] rank = new int[N];
        // the rank is not depth or height because path compression will change the tree's heights
        // over time. it is the history max depth or height.

        for (int v = 0; v < N; ++v) {
            next[v] = v; // index is vertex ID from 0 to N-1.
        }
        Arrays.fill(rank, 0);

        int ei = 0; // index of edges
        Edge e; // current edge
        do { //  O(M)
            e = edges[ei];
            int v1Root = root(next, e.from), v2Root = root(next, e.to);
            if (v1Root != v2Root) {
                // skip when v1Root == v2Root, else it will create circle
                r.add(e);
                merge(next, rank, v1Root, v2Root);
            }
            ei++;
        } while (r.size() < N - 1);
        return r;
    }

    /**
     * <pre>
     *  O(N^2)
     * @return
     */
    public static int[] mstPrim(int graph[][]) {
        int V = graph.length;

        int vTo[] = new int[V]; // the vertex in MST from where the shortest edge exits
        Arrays.fill(vTo, -1);

        int distTo[] = new int[V]; // the shortest edge length from MST
        Arrays.fill(distTo, Integer.MAX_VALUE);

        boolean relax[] = new boolean[V];
        Arrays.fill(relax, false);

        distTo[0] = 0;
        for (int i = 0; i < V; i++) {
            int min = Integer.MAX_VALUE;
            int v = -1;

            // select out the shortest edge in the cut, relax a vertex.
            for (int vi = 0; vi < V; vi++) {
                if (!relax[vi] && distTo[vi] < min) {
                    min = distTo[vi];
                    v = vi;
                }
            }

            relax[v] = true;

            // Update cut with the relax vertex.
            for (int vi = 0; vi < V; vi++) {
                if (!relax[vi] /* is in cut set */
                        && graph[v][vi] != 0 /* there is edge */
                        && graph[v][vi] < distTo[vi] /* now the v contributes
                        the shortest edge from MST to the vi which is not in MST */) {
                    distTo[vi] = graph[v][vi];
                    vTo[vi] = v;
                }
            }
        }
        return vTo;
    }

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
        printResult(mstKruskal(4, edges));
        int[][] graph =
                new int[][] {
                    {0, 10, 6, 5}, {10, 0, 0, 15}, {6, 0, 0, 4}, {5, 15, 4, 0},
                };
        printResult(mstPrim(graph), graph);

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

        printResult(mstKruskal(5, edges));
        graph =
                new int[][] {
                    {0, 2, 0, 6, 0},
                    {2, 0, 3, 8, 5},
                    {0, 3, 0, 0, 7},
                    {6, 8, 0, 0, 9},
                    {0, 5, 7, 9, 0},
                };
        printResult(mstPrim(graph), graph);

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
        printResult(mstKruskal(6, edges));
        graph =
                new int[][] {
                    {0, 3, 5, 50, 0, 0},
                    {3, 0, 4, 0, 0, 0},
                    {5, 4, 0, 0, 0, 0},
                    {50, 0, 0, 0, 6, 10},
                    {0, 0, 0, 6, 0, 8},
                    {0, 0, 0, 10, 8, 0},
                };

        printResult(mstPrim(graph), graph);
    }

    private static void printResult(int[] otherV, int[][] graph) {
        System.out.println("One MST, Prim's algorithm");
        for (int vi = 1; vi < otherV.length; vi++)
            System.out.println(vi + "-" + otherV[vi] + ", weight:" + graph[vi][otherV[vi]]);
    }

    private static void printResult(Collection<Edge> MST) {

        System.out.println("One MST with Kruskal's algorithm, using union find checking circle: ");
        Iterator<Edge> it = MST.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
