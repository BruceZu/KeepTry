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
import java.util.Stack;

public class Leetcode230KthSmallestElementInaBST3 {
    // Enumerate in-order BST with Stack; DFS
    // O(n)  count the nodes for once
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> leftsStack = new ArrayDeque<>();
        while (root != null) {
            leftsStack.push(root);
            root = root.left;
        }

        while (true) {
            TreeNode n = leftsStack.pop();

            k--;
            if (k == 0) return n.val;

            TreeNode right = n.right;
            while (right != null) {
                leftsStack.push(right);
                right = right.left;
            }
        }
    }
}
