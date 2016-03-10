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

package tree;

public abstract class AbstractTree<T extends TreeNode<T, E>, E> implements Tree<T, E> {

    private int childrenMaxDepth(T n, int ancs /* number of ancestors */) {

        int maxChildDepth = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                maxChildDepth = Math.max(maxChildDepth, childrenMaxDepth(c, ancs + 1));
            }
            // skip leaf as it is not the child with max depth
        }

        return maxChildDepth == 0 ? ancs : maxChildDepth;
    }

    @Override
    public boolean isLeaf(T n) {
        return n.getChildren() == null || n.childrenSize() == 0;
    }

    // Tree' height :
    //  - if the tree is empty or a leaf: 0
    //  - the maximum of the depth of its nodes
    // O(N)
    @Override
    public int height() {
        T r = root();
        if (r == null || isLeaf(r)) {
            return 0;
        }
        return childrenMaxDepth(r, 1);
    }

    // Tree' height :
    //   - if root is empty or a leaf: 0
    //   - else: 1 + max(the height of subtrees).
    // O(N)
    @Override
    public int height(T n) {
        if (n == null || isLeaf(n)) {
            return 0;
        }
        int subTreeHeight = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                subTreeHeight = Math.max(subTreeHeight, height(c));
            }
            // skip leaf as it is not the child with max depth
        }
        return 1 + subTreeHeight;
    }

    // The depth of a node: the number of ancestors of node, other than node itself.
    @Override
    public int depth(T n) {
        if (n.getParent() == null) {
            return 0;
        }
        return 1 + depth(n.getParent());
    }
}
