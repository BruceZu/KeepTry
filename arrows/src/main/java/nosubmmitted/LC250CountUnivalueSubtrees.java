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
 * 250. Count Univalue Subtrees
 * https://leetcode.com/problems/count-univalue-subtrees/
 * <pre>Difficulty: Medium
 * Given a binary tree, count the number of uni-value subtrees.
 * <p/>
 * A Uni-value subtree means all nodes of the subtree have the same value.
 * <p/>
 * For example:
 * Given binary tree,
 * <p/>
 *      5
 *     / \
 *    1   5
 *   / \   \
 *   5  5   5
 * <p/>
 * <p/>
 * return 4.
 * <p/>
 * Hide Tags Tree
 * </pre>
 */
public class LC250CountUnivalueSubtrees {

    /**
     * The basic idea is just to iterate all the possible subtrees and increase the total
     * number by 1 when we encounter a uni-val subtree. The time complexity is O(n)
     * where n is the total number of nodes.
     */

    /**
     * Definition for a binary tree node.
     */
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        int total;

        public int countUnivalSubtrees(TreeNode root) {
            total = 0;
            univalTree(root);
            return total;
        }

        private boolean univalTree(TreeNode node) {
            if (node == null) return true;
            boolean left = univalTree(node.left);
            boolean right = univalTree(node.right);
            if (left && right && (node.left == null || node.val == node.left.val) &&
                    (node.right == null || node.val == node.right.val)) {
                total++;
                return true;
            }
            return false;
        }

        /**
         * another case
         */

        int count = 0;

        public int countUnivalSubtrees2(TreeNode root) {
            if (root == null)
                return 0;
            helper(root);
            return count;
        }

        private boolean helper(TreeNode root) {
            if (root == null)
                return true;
            boolean left = helper(root.left);
            boolean right = helper(root.right);
            if (left && right && (root.left == null || root.left.val == root.val) && (root.right == null ||
                    root.right.val == root.val)) {
                count++;
                return true;
            }
            return false;
        }

        /**
         * other
         */
        public int countUnivalSubtrees3(TreeNode root) {
            int[] arr = new int[1];
            postOrder(arr, root);
            return arr[0];
        }

        public boolean postOrder(int[] arr, TreeNode node) {
            if (node == null) return true;
            boolean left = postOrder(arr, node.left);
            boolean right = postOrder(arr, node.right);
            if (left && right) {
                if (node.left != null && node.left.val != node.val) return false;
                if (node.right != null && node.right.val != node.val) return false;
                arr[0]++;
                return true;
            }
            return false;
        }

        /**
         *
         */
        int count3 = 0;

        public int countUnivalSubtrees4(TreeNode root) {
            return (root == null || isUni(root)) ? count3 : count3;
        }

        public boolean isUni(TreeNode root) {
            boolean left = root.left == null || isUni(root.left) && root.val == root.left.val;
            boolean right = root.right == null || isUni(root.right) && root.val == root.right.val;
            return left && right && ++count3 == count3;
        }

        /**
         *
         */
        public int countUnivalSubtrees5(TreeNode root) {
            int[] count = new int[1];
            helper(root, count);
            return count[0];
        }

        private boolean helper(TreeNode node, int[] count) {
            if (node == null) {
                return true;
            }
            boolean left = helper(node.left, count);
            boolean right = helper(node.right, count);
            if (left && right) {
                if (node.left != null && node.val != node.left.val) {
                    return false;
                }
                if (node.right != null && node.val != node.right.val) {
                    return false;
                }
                count[0]++;
                return true;
            }
            return false;
        }

        /**
         * Helper all tells whether all nodes in the given tree have the given value.
         * And while doing that, it also counts the uni-value subtrees.
         */
        int count2 = 0;

        boolean all(TreeNode root, int val) {
            if (root == null)
                return true;
            if (!all(root.left, root.val) | !all(root.right, root.val))
                return false;
            count2++;
            return root.val == val;
        }

        public int countUnivalSubtrees6(TreeNode root) {
            all(root, 0);
            return count2;
        }
    }
}
