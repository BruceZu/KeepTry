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

import java.util.Stack;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/binary-search-tree-iterator/">LeetCode</a>
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory,
 * where h is the height of the tree.
 */
public class Leetcode173BinarySearchTreeIterator {
    /**
     * Definition for binary tree
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // top down in ascending order .  Care not descending
    class BSTIterator {
        private Stack<TreeNode> nodesStackAscending = new Stack();

        public BSTIterator(TreeNode root) {
            while (root != null) {
                nodesStackAscending.push(root);
                root = root.left;
            }
        }

        /**
         * @return whether we have a next smallest number
         * care:  this means ascending top down
         */
        public boolean hasNext() {
            return !nodesStackAscending.empty(); // care: need !
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            if (!hasNext()) { // care: check hasNext() firstly
                return -1;
            }
            TreeNode node = nodesStackAscending.pop(); // care: not peek
            int v = node.val;

            //
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    nodesStackAscending.push(node);
                    node = node.left;
                }
            }

            return v;
        }
    }
}
