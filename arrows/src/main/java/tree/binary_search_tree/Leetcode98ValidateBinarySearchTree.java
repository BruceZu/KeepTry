//  Copyright 2017 The keepTry Open Source Project
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

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.com/problems/validate-binary-search-tree/?tab=Description">LeetCode</a>
 */
public class Leetcode98ValidateBinarySearchTree {

    /**
     * <pre>
     * Assume a BST is defined as follows:
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     *                NO equal
     */
    static public boolean isValidBST_InOrder(TreeNode root) {
        if (root == null) {
            return true;
        }
        Deque<TreeNode> leftsStack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null) {
            leftsStack.push(node);
            node = node.left;
        }
        TreeNode pre = null;
        while (!leftsStack.isEmpty()) {
            TreeNode top = leftsStack.poll();

            //-----validate
            if (pre != null && top.val <= pre.val) {
                return false;
            }
            pre = top;
            // ----
            if (top.right != null) {
                node = top.right;
                while (node != null) {
                    leftsStack.push(node);
                    node = node.left;
                }
            }
        }

        return true;
    }

    // top-down -------------------------------------------------------------------
    static public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static public boolean isValidBST(TreeNode root, int min, int max) {
        if (root == null) {
            return true;
        }
        if (root.val == Integer.MAX_VALUE && root.right != null) {
            return false;
        }
        if (root.val == Integer.MIN_VALUE && root.left != null) {
            return false;
        }

        return min <= root.val && root.val <= max
                && isValidBST(root.left, min, root.val - 1)
                && isValidBST(root.right, root.val + 1, max);
    }

    // bottom_up -------------------------------------------------------------------
    static public boolean _isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return _isValidBST(root, new int[2]);
    }

    static private boolean _isValidBST(TreeNode root, int[] lAndR) {
        int[] lAndROfLeft = new int[2];
        int[] lAndROfRight = new int[2];

        if (root.left == null && root.right == null) {// leaf
            lAndR[0] = lAndR[1] = root.val;
            return true;
        }
        if (root.left == null) {// one child right
            if (_isValidBST(root.right, lAndROfRight) && root.val < lAndROfRight[0]) {
                lAndR[0] = root.val;
                lAndR[1] = lAndROfRight[1];
                return true;
            }
            return false;
        }
        if (root.right == null) { // one child left
            if (_isValidBST(root.left, lAndROfLeft) && lAndROfLeft[1] < root.val) {
                lAndR[0] = lAndROfLeft[0];
                lAndR[1] = root.val;
                return true;
            }
            return false;
        }

        if (_isValidBST(root.left, lAndROfLeft) && _isValidBST(root.right, lAndROfRight)
                && lAndROfLeft[1] < root.val && root.val < lAndROfRight[0]) { // 2 children
            lAndR[0] = lAndROfLeft[0];
            lAndR[1] = lAndROfRight[1];
            return true;
        }
        return false;
    }
    //-------------------------------------------legend
    static public boolean _isValidBST_legend(TreeNode root) {
        if (root == null) {
            return true;
        }
        return _isValidBST_legend(root, new int[2]);
    }

    static private boolean _isValidBST_legend(TreeNode root, int[] lAndR) {
        int[] lAndRForLeft = new int[2];
        int[] lAndRForRight = new int[2];

        // right end
        if (root.val == Integer.MAX_VALUE) {
            if (root.right != null) {
                return false;
            }
            if (root.left == null) {
                lAndR[0] = lAndR[1] = root.val;
                return true;
            }

            if (_isValidBST_legend(root.left, lAndRForLeft) && lAndRForLeft[1] < root.val) {
                lAndR[0] = lAndRForLeft[0];
                lAndR[1] = root.val;
                return true;
            }
            return false;
        }

        // left end
        if (root.val == Integer.MIN_VALUE) {
            if (root.left != null) {
                return false;
            }
            if (root.right == null) {
                lAndR[0] = lAndR[1] = root.val;
                return true;
            }

            if (_isValidBST_legend(root.right, lAndRForRight) && root.val < lAndRForRight[0]) {
                lAndR[0] = root.val;
                lAndR[1] = lAndRForLeft[0];
                return true;
            }
            return false;
        }

        // common
        boolean leftIsBST, rightIsBST;
        if (root.left == null) {
            lAndRForLeft[0] = root.val;
            lAndRForLeft[1] = root.val - 1;
            leftIsBST = true;
        } else {
            leftIsBST = _isValidBST_legend(root.left, lAndRForLeft);
        }
        // if (!leftIsBST) return false; // performance
        if (root.right == null) {
            lAndRForRight[1] = root.val;
            lAndRForRight[0] = root.val + 1;
            rightIsBST = true;
        } else {
            rightIsBST = _isValidBST_legend(root.right, lAndRForRight);
        }

        if (leftIsBST
                && rightIsBST
                && lAndRForLeft[1] < root.val
                && root.val < lAndRForRight[0]) {
            lAndR[0] = lAndRForLeft[0];
            lAndR[1] = lAndRForRight[1];
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------
    public static void main(String[] args) {
//        System.out.println(Integer.MAX_VALUE + 1);
//        // Integer.MAX_VALUE + 1 = Integer.MIN_VALUE
//
//        System.out.println(Integer.MIN_VALUE + Integer.MAX_VALUE);
//        // Integer.MAX_VALUE + Integer.MIN_VALUE = -1
//
//        System.out.println(Integer.MIN_VALUE - 1);
//        // Integer.MIN_VALUE - 1 = Integer.MAX_VALUE
//
//        System.out.println("MAX  " + Integer.toBinaryString(Integer.MAX_VALUE));
//        System.out.println("MIN " + Integer.toBinaryString(Integer.MIN_VALUE));
//        System.out.println("-1: " + Integer.toBinaryString(-1));


        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));

        root = new TreeNode(2147483647);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));

        root = new TreeNode(-2147483648);
        root.left = new TreeNode(-2147483648);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));

        // [10,5,15,null,null,6,20]
        root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));
        // [2147483647,null,2147483647]
        root = new TreeNode(2147483647);
        root.right = new TreeNode(2147483647);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));

        // [25,-22,60,null,null,36]
        root = new TreeNode(25);
        root.left = new TreeNode(-22);
        root.right = new TreeNode(60);
        root.right.left= new TreeNode(36);
        System.out.println(_isValidBST_legend(root));
        System.out.println(_isValidBST(root));
        System.out.println(isValidBST(root));
        System.out.println(isValidBST_InOrder(root));
    }
}
