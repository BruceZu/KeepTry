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

package tree.binary_search_tree;

/**
 * <pre>
 *
 * The next largest value comes from one of two places:
 * First, if the current node has a right child.
 *              1 move to that right child then,
 *              2 as long as you can see a left child, move to it.
 *
 *              In other words (S and D are the source and destination):
 *
 *                 S2
 *               /               \
 *             1                  7
 *                           /       \
 *                           5       8
 *                       /    \
 *                     D3      6
 *                       \
 *                        4
 *
 *  Otherwise (the current node has no right child).
 *         move up to the parent continuously (so nodes need a right, left and parent pointer)
 *         until the node you moved from was a left child.
 *
 *         if reach root and you still haven't moved up from a left child, your original node was already the highest
 *
 *                1
 *                          \
 *                                   D6
 *                       /             \
 *                    2                  7
 *                          \
 *                           4
 *                         /   \
 *                        3     S5
 */
public class TreeNodeWithP {
    int v;
    TreeNodeWithP parent;
    TreeNodeWithP left;
    TreeNodeWithP right;

    public TreeNodeWithP(TreeNodeWithP parent, int v) {
        this.parent = parent;
        this.v = v;
    }

    public TreeNodeWithP(int v) {
        this(null, v);
    }

    public static TreeNodeWithP testTreeNodeWithP() {
        /**
         *  <pre>
         *                                  17
         *                     /
         *               11
         *            /       \
         *          9         13
         *        /   \      /       \
         *       8    10   12        15
         *                         /    \
         *                        14    16
         *
         * ------8  9 10 11 12 13 14 15 16 17---------------
         *
         */
        TreeNodeWithP root = new TreeNodeWithP(17);
        TreeNodeWithP n11 = new TreeNodeWithP(root, 11);
        TreeNodeWithP n9 = new TreeNodeWithP(n11, 9);
        TreeNodeWithP n13 = new TreeNodeWithP(n11, 13);
        TreeNodeWithP n8 = new TreeNodeWithP(n9, 8);
        TreeNodeWithP n10 = new TreeNodeWithP(n9, 10);
        TreeNodeWithP n12 = new TreeNodeWithP(n13, 12);
        TreeNodeWithP n15 = new TreeNodeWithP(n13, 15);
        TreeNodeWithP n14 = new TreeNodeWithP(n15, 14);
        TreeNodeWithP n16 = new TreeNodeWithP(n15, 16);
        root.left = n11;
        n11.left = n9;
        n11.right = n13;
        n9.left = n8;
        n9.right = n10;
        n13.left = n12;
        n13.right = n15;
        n15.left = n14;
        n15.right = n16;
        return root;
    }
}
