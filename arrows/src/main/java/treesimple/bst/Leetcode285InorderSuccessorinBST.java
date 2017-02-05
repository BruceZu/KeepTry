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

package treesimple.bst;

/**
 * <pre>
 *   <a href='https://leetcode.com/problems/inorder-successor-in-bst/'> leetcode </a>
 *
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 * Note: If the given node has no in-order successor in the tree, return null.
 */
public class Leetcode285InorderSuccessorinBST {
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

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (p.right != null) {
            TreeNode n = p.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        }
        TreeNode successorParentCandidator = null;
        while (root.val != p.val) {
            if (root.val > p.val) {
                successorParentCandidator = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return successorParentCandidator;
    }
}
