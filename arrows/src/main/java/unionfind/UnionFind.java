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

package unionfind;

/**
 * <pre>
 *     scenario: disjoint sets. e.g. islands, groups of machines
 *     data structure:
 *                int[] or HashMap to keep:
 *                all nodes' id/tag: 0 ~ array length -1
 *
 * with Pass Compress the tree will be updated eventually to be end up with tree height of 1.
 *        n1
 *      / |  \
 *  n2 n3 .... n7 n8
 *  <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure">wiki<a/>
 *  "Using both path compression, splitting, or halving and union by rank or size ensures
 *  that the amortized time per operation is only alpha (n)
 *  which is optimal, where  alpha (n) is the inverse Ackermann function.
 *  This function has a value  alpha (n)<5
 *  for any value of n that can be written in this physical universe,
 *  so the disjoint-set operations take place in essentially constant time."
 */
public class UnionFind {

    // Assume all nodes have been tagged with digits,
    // So: ids are distinguish, 0 ~ n-1,
    private final int[] parent; // next node id of
    private final int[] rank;

    // Tag nodes with digits,
    public UnionFind(String[] nodes) {
        // Just make a easy cases, only 100 nodes.
        // Initially let each node point to itself, circle, to form a tree
        // so the root/end is itself. Each node represents a islands/groups of machines
        //
        // if the root points to any other node in its islands/groups of machines.
        // It has only one circle, as for any node there is
        // only one 'come out' edge.
        parent = new int[100];
        rank = new int[100];
        for (int i = 0; i < 100; i++) {
            parent[i] = i;
        }
    }

    /**
     * Path splitting makes every node on the path point to its grandparent. O(alpha (n))
     *
     * @see graph.MinimumSpanningTree#root(int[], int)
     */
    public int root(int i) {
        while (parent[i] != i) {
            int p = parent[i];
            parent[i] = parent[p];
            i = p;
        }
        return i;
    }

    /**
     * O(alpha (n))
     *
     * @see graph.MinimumSpanningTree#merge(int[], int[], int, int)
     */
    public void union(int ida, int idb) {
        int r1 = root(ida);
        int r2 = root(idb);

        int el /* equal or less than*/, o /* other*/;
        if (rank[r1] <= rank[r2]) {
            el = r1;
            o = r2;
        } else {
            el = r2;
            o = r1;
        }

        parent[el] = o;
        if (rank[el] == rank[o]) {
            rank[o]++;
        }
    }
}
