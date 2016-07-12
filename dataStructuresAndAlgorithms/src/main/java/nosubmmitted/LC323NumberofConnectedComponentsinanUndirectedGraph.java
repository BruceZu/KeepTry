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
 * 323. Number of Connected Components in an Undirected Graph
 * https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
 * Difficulty: Medium <pre>
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 * write a function to find the number of connected components in an undirected graph.
 * <p/>
 * Example 1:
 * 0          3
 * |          |
 * 1 --- 2    4
 * Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
 * <p/>
 * Example 2:
 * 0           4
 * |           |
 * 1 --- 2 --- 3
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
 * <p/>
 * Note:
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected,
 * [0, 1] is the same as [1, 0] and thus will not appear together in edges.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Depth-first Search, Breadth-first Search, Graph, Union Find
 * Hide Similar Problems (M) Number of Islands (M) Graph Valid Tree
 */
public class LC323NumberofConnectedComponentsinanUndirectedGraph {


    // beat 87%
    public int countComponents(int n, int[][] edges) {
        if (n <= 1) {
            return n;
        }
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
        for (int[] edge : edges) {
            int x = find(roots, edge[0]);
            int y = find(roots, edge[1]);
            if (x != y) {
                roots[x] = y;
                n--;
            }
        }

        return n;
    }

    public int find(int[] roots, int id) {
        int x = id;
        while (roots[id] != id) {
            id = roots[id];
        }
        while (roots[x] != id) {
            int fa = roots[x];
            roots[x] = id;
            x = fa;
        }

        return id;
    }


    //

    /*
    This is 1D version of Number of Islands II. For more explanations, check out this 2D Solution.

n points = n islands = n trees = n roots.
With each edge added, check which island is e[0] or e[1] belonging to.
If e[0] and e[1] are in same islands, do nothing.
Otherwise, union two islands, and reduce islands count by 1.
Bonus: path compression can reduce time by 50%.
     */


    // other beat 98

    /**
     * Union-Find: basic idea is that initially we have n components,
     * if two node have different id, we unites them,
     * and the number of components would decrease by one.
     *
     * @param n
     * @param edges
     * @return
     */
    public int countComponents3(int n, int[][] edges) {
        int[] id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        int res = n;
        for (int[] edge : edges) {
            if (unite(edge[0], edge[1], id)) {
                res--;
            }
        }
        return res;
    }

    private boolean unite(int p, int q, int[] id) {
        int pId = find(p, id);
        int qId = find(q, id);
        if (pId == qId) {
            return false;
        } else {
            id[pId] = qId;
            return true;
        }
    }

    private int find(int p, int[] id) {
        while (id[p] != p) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
}
