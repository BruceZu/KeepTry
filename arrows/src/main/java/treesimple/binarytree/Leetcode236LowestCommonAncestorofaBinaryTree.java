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
 * <a href = "https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree">leetcode</a>
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
 * Tags Tree
 * Similar Problems (E) Lowest Common Ancestor of a Binary Search Tree
 *
 * Pocket Gems use it
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

        // post order find one node from current node then return it at once.
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // input check
            if (root == null) {
                return null;
            }

            if (root == p || root == q) {
                return root;
            }
            // above can be merged into one

            // now root is not null, p, q.
            TreeNode rl = lowestCommonAncestor(root.left, p, q);
            TreeNode rr = lowestCommonAncestor(root.right, p, q);
            return rl != null && rr != null
                    ? root
                    : rl != null ? rl : rr;
            /**
             compare to null, not p or q

             1  1
             1  0
             0  1
             0  0

             p, q, null, root

             left  right   return (root, q, p, null)
             p     q       root
             p     null    p
             q     p       root
             q     null    q
             root  null    root
             null  null    null
             null  p       p
             null  q       q
             null  root    root
             */
        }

        public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) {
                return root;
            }
            TreeNode left = lowestCommonAncestor2(root.left, p, q);
            TreeNode right = lowestCommonAncestor2(root.right, p, q);
            return left == null ? right : right == null ? left : root;
        }
    }
}
