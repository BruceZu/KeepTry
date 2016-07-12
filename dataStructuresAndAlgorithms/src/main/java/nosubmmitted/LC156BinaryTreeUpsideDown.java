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
 * 156. Binary Tree Upside Down
 * https://leetcode.com/problems/binary-tree-upside-down/
 * <p/>
 * <pre>
 *     Difficulty: Medium
 * Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.
 *
 * For example:
 * Given a binary tree {1,2,3,4,5},
 *      1
 *     / \
 *    2  3
 *   / \
 *  4   5
 * return the root of the binary tree [4,5,2,#,#,3,1].
 *      4
 *     / \
 *    5   2
 *       / \
 *      3   1
 * confused what "{1,#,2,3}" means?
 * read more on how binary tree is serialized on OJ.
 *
 * OJ's Binary Tree Serialization:
 * The serialization of a binary tree follows a level order traversal, where '#' signifies a path terminator where no node exists below.
 *
 * Here's an example:
 *     1
 *    / \
 *   2   3
 *      /
 *     4
 *      \
 *      5
 * The above binary tree is serialized as "{1,2,3,#,#,4,#,#,5}".
 * Hide Company Tags LinkedIn
 * Hide Tags Tree
 * Hide Similar Problems (E) Reverse Linked List
 *
 * </pre>
 */
public class LC156BinaryTreeUpsideDown {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * the fast one currently beat 95.10
     * Before changing the left and right child of current subtree, store the next subtree's left and right child.
     * Note that you should nullify root.left and root.right in the beginning.
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) return null;
        if (root.left == null) return root;

        TreeNode curRoot = root, curLeft = null, curRight = null,
                nextLeft = root.left, nextRight = root.right;
        root.left = null;
        root.right = null;
        while (nextLeft != null) {
            curLeft = nextLeft;
            curRight = nextRight;
            nextLeft = curLeft.left;
            nextRight = curLeft.right;

            curLeft.left = curRight;
            curLeft.right = curRoot;
            curRoot = curLeft;
        }
        return curLeft;
    }

    /**
     * below is second level
     *
     * @param root
     * @return
     */
    public TreeNode upsideDownBinaryTree3(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        upsideDownBinaryTreeRec(root);
        return curr;

    }

    public void upsideDownBinaryTreeRec(TreeNode root) {
        if (root.left != null) {

            upsideDownBinaryTreeRec(root.left);
            root.left.right = root;
            root.left.left = root.right;
            root.left = null;
            root.right = null;
        } else {
            return;
        }
    }

    /**
     * another
     */
    public TreeNode upsideDownBinaryTree2(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }  // the new root of the upside-down tree
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        TreeNode newParent = root.left;  // the new parent of current level is originally the left child
        newParent.left = root.right;
        newParent.right = root;
        newParent.right.left = null;  // set original parent's left and right pointer to null, otherwise there will be a loop at the last backtracking level
        newParent.right.right = null;
        return newRoot;
    }

    /**
     * another
     */
    public TreeNode upsideDownBinaryTree5(TreeNode root) {
        TreeNode prev = null, right = null, next = null;
        while (root != null) {
            next = root.left;
            root.left = right;
            right = root.right;
            root.right = prev;
            prev = root;
            root = next;
        }
        return prev;
    }

    /**
     * another
     */
    public TreeNode upsideDownBinaryTree9(TreeNode root) {
        if (root == null || root.left == null && root.right == null)
            return root;

        TreeNode newRoot = upsideDownBinaryTree(root.left);

        root.left.left = root.right;
        root.left.right = root;

        root.left = null;
        root.right = null;


        return newRoot;
    }

}
