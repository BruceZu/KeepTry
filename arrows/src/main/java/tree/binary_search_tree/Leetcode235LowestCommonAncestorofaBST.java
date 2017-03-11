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

/**
 * <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/">leetcode</a>
 * Pocket Gems
 */
public class Leetcode235LowestCommonAncestorofaBST {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    // O(lgN)
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        while ((root.val - p.val) * (root.val - q.val) > 0) {
            root = p.val < root.val ? root.left : root.right;
        }
        return root;
    }

    //O(N)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left == null && right == null) {
            return root == q || root == p ? root : null;
        }

        if (left == null) {
            return root == q || root == p ? root : right;
        }
        if (right == null) {
            return root == q || root == p ? root : left;
        }
        return root;
    }
}
