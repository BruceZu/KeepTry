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
 * <pre>
 * 250. Count Univalue Subtrees
 * <a href="https://leetcode.com/problems/count-univalue-subtrees/">leetcode </a>
 * Difficulty: Medium
 * Given a binary tree, count the number of uni-value subtrees.
 *
 * A Uni-value subtree means all nodes of the subtree have the same value.
 *
 * For example:
 * Given binary tree,
 *
 *      5
 *     / \
 *    1   5
 *   / \   \
 *   5  5   5
 *
 *
 * return 4.
 *
 * Tags Tree
 * ---------------------------------------------------------------------------------------------------
 * Follow up:  A Uni-value subtree means all nodes of the subtree have the value in a given scope.
 */

public class Leetcode250CountUnivalueSubtrees {

    public static int countUnivalSubtrees(TreeNode root) {
        int[] r = new int[1];
        postRecursion(root, r);
        return r[0];
    }

    private static boolean postRecursion(TreeNode n, int[] r) {
        if (n == null) {
            return true; // what is the "same value"? same as left or null
        }
        boolean match = n.left == null && n.right == null // leaf
                || n.left != null && n.right != null && n.left.val == n.val && n.right.val == n.val //
                || n.left != null && n.right == null && n.left.val == n.val //
                || n.left == null && n.right != null && n.right.val == n.val; //
        // same as  (n.left == null || n.left.val == n.val) && (n.right == null || n.right.val == n.val);
        if (postRecursion(n.left, r) && postRecursion(n.right, r) && match) {
            r[0]++;
            return true;
        } else {
            return false;
        }
    }
    //or use not match idea
}
