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

package treesimple.bst;

import java.util.Stack;

public class BSTNextBiggest {
    /**
     * when the TreeNode has no parent pointer
     */
    static class SolutionA {

        private Stack<TreeNode> track;

        public SolutionA(TreeNode root) {
            track = new Stack<>();
            TreeNode node = root;
            while (node != null) { // care
                track.push(node);
                node = node.right;
            }
        }

        // whether we have a next biggest number
        public boolean hasNext() {
            return !track.isEmpty();
        }

        //the next biggest number
        public int next() {

            if (!hasNext()) {
                return -1; // ???
            }

            TreeNode node = track.pop();
            int r = node.val;

            node = node.left;
            while (node != null) {
                track.push(node);
                node = node.right;
            }
            return r;
        }
    }

    /*---------------------------------------------------------------------------------------------------------*/
    static class SolutionB {
        public static TreeNode nextLargestNode(TreeNode given, TreeNode root) {
            if (given.right != null) {
                TreeNode n = given.right;
                while (n.left != null) {
                    n = n.left;
                }
                return n; // the node with minimum key value in right subtree of given node.
            }

            TreeNode parentAlongLeft = null;
            while (true) { // Assume the give node is sure exists in the given BST
                if (given.val == root.val) {
                    return parentAlongLeft;
                }

                if (given.val < root.val) {
                    parentAlongLeft = root;
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------
     * when the TreeNode has parent pointer
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
     * Complexity:
     *  The effort is proportional to the height of the tree,
     * 1 balanced tree (such as red-black, 2-3-4 and AVL): O(log N)
     *   since the height has a logN relationship to the number of items.
     *
     * 2 non-balanced trees, worst case:  O(n)
     */
    public static TreeNodeWithP nextLargestNode(TreeNodeWithP given) {
        if (given.right != null) {
            TreeNodeWithP n = given.right;
            while (n.left != null) {
                n = n.left;
            }
            return n; // the node with minimum key value in right subtree of given node.
        }
        while (given.parent != null) {
            if (given == given.parent.left) {
                return given.parent;
            }
            given = given.parent;
        }
        return null; // given is biggest one of this tree
    }

    /*---------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        TreeNode root = TreeNode.testTree();
        SolutionA i = new SolutionA(root);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
