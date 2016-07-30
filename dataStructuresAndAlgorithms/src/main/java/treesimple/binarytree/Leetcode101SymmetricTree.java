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
 * 101. Symmetric Tree
 * Difficulty: Easy
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *       1
 *      / \
 *     2   2
 *    / \ / \
 *   3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 *
 * Company Tags
 * LinkedIn Bloomberg Microsoft
 * Tags
 *          Tree Depth-first Search
 *          Breadth-first Search
 * ====================================================================
 *                   r
 *                /      \
 *
 *     ---
 *              l         r
 *            /   |     /    |
 *           ll    lr  rl     rr
 *
 *     ---
 *          /  \  / \  /  \  /  \
 *        5    4 3  2 2   3 4   5
 *
 * Note:
 *    2 notes as parameters
 *    check val
 *
 * iteratively see {@link Leetcode101SymmetricTree2}
 */

public class Leetcode101SymmetricTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // recursively
    private boolean isSymmetric(TreeNode l, TreeNode r) {
        if (l == null || r == null) {
            return l == r;
        }
        return l.val == r.val && isSymmetric(l.left, r.right) && isSymmetric(l.right, r.left);
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }
}
