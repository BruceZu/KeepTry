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
 * 285. Inorder Successor in BST
 * https://leetcode.com/problems/inorder-successor-in-bst/
 * Difficulty: Medium
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * <p/>
 * Note: If the given node has no in-order successor in the tree, return null.
 * <p/>
 * Hide Company Tags Pocket Gems Microsoft Facebook
 * Hide Tags Tree
 * Hide Similar Problems (M) Binary Tree Inorder Traversal , (M) Binary Search Tree Iterator
 * <p/>
 * http://stackoverflow.com/questions/5471731/in-order-successor-in-binary-search-tree
 */
public class LC285InorderSuccessorinBST {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {

        if (p == null || root == null) {
            return null;
        }

        TreeNode inorderSuccessor = null;
        if (root.val <= p.val) {
            inorderSuccessor = inorderSuccessor(root.right, p);
        } else {
            inorderSuccessor = inorderSuccessor(root.left, p);
        }

        return (inorderSuccessor == null && root.val > p.val) ? root : inorderSuccessor;
    }

    // same fast as above

    /**
     * The idea is to compare root's value with p's value if root is not null, and consider the following two cases:
     * <p/>
     * root.val > p.val. In this case, root can be a possible answer, so we store the root node first and call it res.
     * However, we don't know if there is anymore node on root's left that is larger than p.val.
     * So we move root to its left and check again.
     * <p/>
     * root.val <= p.val. In this case, root cannot be p's inorder successor, neither can root's left child.
     * So we only need to consider root's right child, thus we move root to its right and check again.
     * <p/>
     * We continuously move root until exhausted. To this point, we only need to return the res in case 1.
     *
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        TreeNode res = null;
        while (root != null) {
            if (root.val > p.val) {
                res = root;
                root = root.left;
            } else root = root.right;
        }
        return res;
    }

    /**
     * The inorder traversal of a BST is the nodes in ascending order. To find a successor,
     * you just need to find the smallest one that is larger than the given value since
     * there are no duplicate values in a BST. It just like the binary search in a sorted list.
     * The time complexity should be O(h) where h is the depth of the result node.
     * succ is a pointer that keeps the possible successor. Whenever you go left
     * the current root is the new possible successor, otherwise the it remains the same.
     * Only in a balanced BST O(h) = O(log n). In the worst case h can be as large as n.
     */
    public TreeNode inorderSuccessor3(TreeNode root, TreeNode p) {
        TreeNode succ = null;
        while (root != null) {
            if (p.val < root.val) {
                succ = root;
                root = root.left;
            } else
                root = root.right;
        }
        return succ;
    }
}
