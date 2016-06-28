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
 * disjoint-set data structure
 *
 * 1 Tag nodes to ids from 0
 * 2 Using Array to map index(id) to next node id
 * 3 Checking 2 nodes in same tree by compare their end/root
 * 4 Union by point one node's end/root to that of another
 * 5 Improvement:
 *     a. Link lower height tree to higher tree
 *     b. Pass compress. make the tree height to be 1 at last.
 */
public class UnionFind {

    /**
     * Assume the element of ids is:
     * 1 distinguish
     * 2  >=0
     * 3 that mas element value is ids.length - 1.
     * In a short word, it is the result of tagging nodes.
     */
    private final int[] next;
    private int[] treeHeight;

    /**
     * <pre>
     * Just make it easy. only 100 nodes and their ids are ready.
     * Initially let each node as a tree, the root/end is itself.
     * bad case runtime O(logN).
     *
     *              n
     *          /  / |
     *        n   n  n
     *      / |   |
     *     n  n   n
     *     |
     *     n
     *
     *  root ---- bottom     N( nodes number)   tree height (logN)
     *        1                1      2^0         0
     *       1 1               2      2^1         1
     *      1 2 1              4      2^2         2
     *     1 3 3 1             8      2^3         3
     *    1 4 6 4 1            16     2^4         4
     */
    public UnionFind() {
        next = new int[100];
        treeHeight = new int[next.length];
        for (int i = 0; i < 100; i++) {
            next[i] = i;
            treeHeight[i] = 0;
        }
    }

    public UnionFind(String[] nodes) {
        // todo: Tag the nodes firstly.
        // The nodes may be too big to load into memory
        this.next = null;
        this.treeHeight = null;
    }

    /**
     * <pre>
     * Each loop, break up 1/2 path and height of the id being searched
     * By firstly update the next mapping before next update id.
     * todo: how to update the height of tree after path compression
     */
    public int findRoot(int id) {
        while (id != next[id]) {
            next[id] = next[next[id]];
            id = next[id];
        }
        return id;
    }

    /**
     * <pre>
     * 1> let the smaller tree connect to the bigger one.
     *
     * 2> Once a root pint to another root, this root is not root anymore
     * and its tree height value has no meaning any more
     * so it is better to let its tree height value to be -1.
     */
    public void union(int ida, int idb) {
        int roota = findRoot(ida);
        int rootb = findRoot(idb);
        if (roota != rootb) {
            if (treeHeight[roota] < treeHeight[rootb]) {
                next[roota] = rootb;
            } else if (treeHeight[rootb] < treeHeight[roota]) {
                next[rootb] = roota;
            } else {
                next[roota] = rootb;
                treeHeight[rootb]++;
            }
        }
    }

    public int treeHeight(int id) {
        return treeHeight[findRoot(id)];
    }
}
