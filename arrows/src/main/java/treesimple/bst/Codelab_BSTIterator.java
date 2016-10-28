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

package treesimple.bst;

import java.util.Stack;

/**
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 * <pre>
 * The first call to next() will return the smallest number in BST.
 * Calling next() again will return the next smallest number in the BST, and so on.
 *
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory,
 * where h is the height of the tree.
 * Try to optimize the additional space complexity apart from the amortized time complexity.
 *
 * @see <a href="https://codelab.interviewbit.com/problems/treeiterator/">codelab</a>
 */
public class Codelab_BSTIterator {
    static class Solution {

        private Stack<TreeNode> track;

        public Solution(TreeNode root) {
            track = new Stack<>();
            TreeNode node = root;
            while (node != null) { // care
                track.push(node);
                node = node.left;
            }
        }

        // whether we have a next smallest number
        public boolean hasNext() {
            return !track.isEmpty();
        }

        // the next smallest number
        public int next() {

            if (!hasNext()) {
                return -1; // ???
            }

            TreeNode node = track.pop();
            int r = node.val;

            node = node.right;
            while (node != null) {
                track.push(node);
                node = node.left;
            }
            return r;
        }
    }

    /*---------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        TreeNode root = TreeNode.testTree();
        Solution i = new Solution(root);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}


