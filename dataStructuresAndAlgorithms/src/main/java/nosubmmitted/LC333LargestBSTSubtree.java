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
 * 333. Largest BST Subtree
 * <p/>
 * https://leetcode.com/problems/largest-bst-subtree/
 * <p/>
 * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.
 * <p/>
 * Note:
 * A subtree must include all of its descendants.
 * Here's an example:
 * //    10
 * //   / \
 * //   5  15
 * //  / \   \
 * // 1   8   7
 * The Largest BST Subtree in this case is the highlighted one.
 * The return value is the subtree's size, which is 3.
 * Hint:
 * <p/>
 * You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, which will result in O(nlogn) time complexity.
 * Follow up:
 * Can you figure out ways to solve it with O(n) time complexity?
 * <p/>
 * Hide Company Tags Microsoft
 * Hide Tags Tree
 */
public class LC333LargestBSTSubtree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    int r = 0;

    /**
     * @param root
     * @return int[0]:-1 if it is not BST;
     * int[1]:nodes number of sub tree
     * int[2]:min value of sub tree
     * int[3]:max value of sub tree
     */
    private int[] subTreeIsBST(TreeNode root) {
        if (root.left == null && root.right == null) {
            // leaf
            r = Math.max(r, 1);  // record it at once, else maybe no chance
            int[] re = new int[4];
            re[1] = 1;
            re[2] = re[3] = root.val;
            return re;
        }

        if (root.left != null && root.right == null) {
            int[] leftSubBTRe = subTreeIsBST(root.left);
            if (leftSubBTRe[0] == -1) {
                return new int[]{-1, 0, 0, 0};
            }

            if (leftSubBTRe[3] < root.val) {
                r = Math.max(r, leftSubBTRe[1] + 1);
                return new int[]{0, leftSubBTRe[1] + 1, leftSubBTRe[2], root.val};
            }

            return new int[]{-1, 0, 0, 0};
        }

        if (root.left == null && root.right != null) {
            int[] rightSubBTRe = subTreeIsBST(root.right);
            if (rightSubBTRe[0] == -1) {
                return rightSubBTRe;
            }
            if (rightSubBTRe[2] > root.val) {
                r = Math.max(r, rightSubBTRe[1] + 1);
                return new int[]{0, rightSubBTRe[1] + 1, root.val, rightSubBTRe[3]};
            }

            return new int[]{-1, 0, 0, 0};
        }


        int[] leftSubBTRe = subTreeIsBST(root.left);
        int[] rightSubBTRe = subTreeIsBST(root.right);
        if (leftSubBTRe[0] != -1
                && rightSubBTRe[0] != -1
                && leftSubBTRe[3] < root.val
                && root.val < rightSubBTRe[2]) {

            int u = leftSubBTRe[1] + rightSubBTRe[1] + 1;
            r = Math.max(r, u);
            return new int[]{0, u, leftSubBTRe[2], rightSubBTRe[3]};
        }

        return new int[]{-1, 0, 0, 0};
    }

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        subTreeIsBST(root);
        return r;
    }
}