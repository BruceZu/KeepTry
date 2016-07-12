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

package nosubmmitted;

/**
 * 261. Graph Valid Tree
 * https://leetcode.com/problems/graph-valid-tree/
 * <p/>
 * <p/>
 * Difficulty: Medium <pre>
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 * write a function to check whether these edges make up a valid tree.
 * <p/>
 * For example:
 * <p/>
 * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
 * <p/>
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
 * <p/>
 * Hint:
 * <p/>
 * Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], what should your return?
 * Is this case a valid tree?
 * According to the definition of tree on Wikipedia:
 * “a tree is an undirected graph in which any two vertices are connected by exactly one path.
 * In other words, any connected graph without simple cycles is a tree.”
 * Note: you can assume that no duplicate edges will appear in edges.
 * Since all edges are undirected, [0, 1] is the same as [1, 0] and
 * thus will not appear together in edges.
 * <p/>
 * Hide Company Tags Google Facebook Zenefits
 * Hide Tags: Depth-first Search, Breadth-first Search, Graph, Union Find
 * Hide Similar Problems (M) Course Schedule (M) Number of Connected Components in an Undirected Graph
 */
public class LC261GraphValidTree {

    // the fast one beat 99%

    /**
     * beat 72.09%
     */

        public boolean validTree3(int n, int[][] edges) {
            if (n <= 1) return true;
            int[] parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            for (int[] edge : edges) {
                int x = find3(parent, edge[0]);
                int y = find3(parent, edge[1]);
                if (x == y) return false;
                parent[y] = x;
            }

            return edges.length == n - 1;
        }

        public int find3(int[] parent, int i) {
            if (parent[i] != i) {
                parent[i] = find(parent, parent[i]);
            }
            return parent[i];
        }


    /**
     * same as above
     *
     * @param n
     * @param edges
     * @return Union-find with compressed find
     * Simple but sophisticated java union find solution.
     * With path compression and union by rank
     */
    public boolean validTree(int n, int[][] edges) {
        if (n < 2) return true;
        if (edges.length != n - 1) return false;

        int[] parents = new int[n];                         //parents[i]: i'th node's parent
        int[] ranks = new int[n];                           //depth of trees
        for (int i = 0; i < n; i++) parents[i] = i;     //isolated forests, each node is a tree

        for (int i = 0; i < edges.length; i++) {
            int parent1 = find(parents, edges[i][0]);       //find set parent
            int parent2 = find(parents, edges[i][1]);       //find set parent
            if (parent1 == parent2) return false;        //vertices of an an edge in the same set
            union(parent1, parent2, parents, ranks);     //union tree with parent1 and tree with parent2 by rank
        }
        return true;    //if edges.len==n-1 && no loop exists, must be valid: only 1 connected component
    }

    private int find(int[] parents, int node) {
        if (parents[node] != node)
            parents[node] = find(parents, parents[node]);                       //path compression
        return parents[node];
    }

    private void union(int parent1, int parent2, int[] parents, int[] ranks) {   //union by rank
        if (ranks[parent1] > ranks[parent2])
            parents[parent2] = parent1;
        else if (ranks[parent1] < ranks[parent2])
            parents[parent1] = parent2;
        else {
            parents[parent1] = parent2;
            ranks[parent2]++;
        }
    }

    /**
     * same as above
     *
     * @param n
     * @param edges
     * @return
     */
    public boolean validTree2(int n, int[][] edges) {
        if (n == 1) {
            return true;
        }
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
        for (int[] edge : edges) {
            int xp = find2(roots, edge[0]);
            int yp = find2(roots, edge[1]);
            if (xp == yp) {
                return false;
            } else {
                roots[xp] = yp;
            }
        }

        return edges.length == n - 1;
    }

    public int find2(int[] roots, int id) {
        int input = id;
        while (roots[id] != id) {
            id = roots[id];
        }
        while (roots[input] != id) {
            int fa = roots[input];
            roots[input] = id;
            input = fa;
        }

        return id;
    }
}
