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

package treesimple.binarytree;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * <pre>
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia:
 * “The lowest common ancestor is defined between two nodes v and w as the lowest node in
 * T that has both v and w as descendants (where we allow a node to be a descendant of itself).”
 *
 *          _______3______
 *         /              \
 *     ___5__            ___1__
 *    /      \          /      \
 *   6       _2        0       8
 *          /  \
 *         7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
 * Another example is LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 *
 * Subscribe to see which companies asked this question
 *
 * Hide Tags Tree
 * Hide Similar Problems (E) Lowest Common Ancestor of a Binary Search Tree
 *
 *     </>
 */
public class Leetcode236LowestCommonAncestorofaBinaryTree {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
        private boolean done = false;
        private TreeNode result = null;

        // post order recursive to find and check
        // if the tree is very deep and the p and q is on the shallow level,
        // maybe we need bfs is better which need a queue of list,
        // that means we need more space, it is a trade off.
        public TreeNode lowestCommonAncestor1(TreeNode node, TreeNode p, TreeNode q) {
            // input check
            if (node == null) {
                return null;
            }

            // return time
            // current : what to do before recursive down
            // current : after return back, how to precess the return value
            if (done) {
                return result;
            }

            TreeNode leftSubTreeResult = null;
            TreeNode rightSubTreeResult = null;
            if (node.left != null) {
                leftSubTreeResult = lowestCommonAncestor1(node.left, p, q);
            }
            if (node.right != null) {
                rightSubTreeResult = lowestCommonAncestor1(node.right, p, q);
            }

            //  neither node is found
            if (leftSubTreeResult == null && rightSubTreeResult == null && node != p && node != q) {
                return null;
            }

            // find both nodes
            if (leftSubTreeResult != null && rightSubTreeResult != null
                    || leftSubTreeResult != null && (node == p || node == q)
                    || rightSubTreeResult != null && (node == p || node == q)) {

                done = true;
                return node;
            }


            // find one node
            if (leftSubTreeResult != null) {
                return leftSubTreeResult;
            }
            if (rightSubTreeResult != null) {
                return rightSubTreeResult;
            }
            return node;
        }


        /**
         * improved : <pre>
         * <p>
         * 1. post order -> pre order . find one from current node then return it at once.
         * because: if the other node is a one children node, then current node is the result.
         *          else the other node and current node will be in the left subtree and right subtree.
         * <p>
         * 2.  find both, find one, find none logic are merge into one line code
         * 3.  do not need variable to keep done and result because hard to check which is the result till as last.
         */

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // input check
            if (root == null) {
                return null;
            }

            if (root == p || root == q) {
                return root;
            }
            // above can be merged into one

            TreeNode leftSubTreeResult = lowestCommonAncestor(root.left, p, q);
            TreeNode rightSubTreeResult = lowestCommonAncestor(root.right, p, q);

            return leftSubTreeResult != null && rightSubTreeResult != null
                    ? root
                    : leftSubTreeResult != null ? leftSubTreeResult : rightSubTreeResult;
        }
    }
}
