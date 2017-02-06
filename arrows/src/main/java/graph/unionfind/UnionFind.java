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

package graph.unionfind;

/**
 * <pre>
 *     scenario: disjoint sets. e.g. islands, groups of machines
 *     data structure:
 *                int[] or HashMap to keep:
 *                       1 mapping relation: index is node id/tag -> value is nextNodeIdOf node's id/tag it is pointing to.
 *                       2 all nodes' id/tag: 0 ~ array length -1
 *     operations:
 *            search: root(int nodeId)
 *                    root node id represents the id of its set / island/ group of machine
 *                    this method is used to check if 2 given nodes are in the same set/island/group of machine.
 *            update: union(int nodeAid, int nodeBid)
 *                    if 2 given nodes are not in the same set/island/group of machine.
 *                    link together their sets/islands/groups of machine
 *                    for performance concern, link the one with lower tree height to the other.
 *                    but if the root() implements Pass Compress, just link any one to the other,
 *                    and need not to maintain the tree height.
 *
 * with Pass Compress the tree will be updated eventually to be end up with tree height of 1.
 *        n1
 *      / |  \
 *  n2 n3 .... n7 n8
 *
 */
public class UnionFind {

    // Assume all nodes have been tagged with digits,
    // So: ids are distinguish, 0 ~ n-1,
    private final int[] nextNodeIdOf;

    // Tag nodes with digits,
    public UnionFind(String[] nodes) {
        // Just make a easy cases, only 100 nodes.
        // Initially let each node point to itself, circle, to form a tree
        // so the root/end is itself. Each node represents a islands/groups of machines
        //
        // if the root points to any other node in its islands/groups of machines.
        // it will be a graph like a black hole shape. It has only one circle, as for any node there is
        // only one 'come out' edge.
        nextNodeIdOf = new int[100];
        for (int i = 0; i < 100; i++) {
            nextNodeIdOf[i] = i;
        }
    }

    // Pass Compress: in each search, break up 1/2 path.
    public int rootOf(int nodeId) {
        while (nodeId != nextNodeIdOf[nodeId]) {
            nextNodeIdOf[nodeId] = nextNodeIdOf[nextNodeIdOf[nodeId]];
            nodeId = nextNodeIdOf[nodeId];
        }
        return nodeId;
    }

    public void union(int ida, int idb) {
        int roota = rootOf(ida);
        int rootb = rootOf(idb);
        if (roota != rootb) {
            nextNodeIdOf[roota] = rootb;
        }
    }
}
