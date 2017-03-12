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
 * 230. Kth Smallest Element in a BST
 * <pre>
 *
 *     Difficulty: Medium
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 *
 * Follow up:
 * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?
 *
 * Hint:
 *
 * Try to utilize the property of a BST.
 * What if you could modify the BST node's structure?
 * The optimal runtime complexity is O(height of BST).
 *
 * Credits:
 * Special thanks to @ts for adding this problem and creating all test cases.
 *
 * Subscribe to see which companies asked this question
 *
 * Hide Tags: Binary Search Tree
 * Hide Similar Problems: (M) Binary Tree Inorder Traversal
 *
 * </pre>
 */
public class Leetcode230KthSmallestElementInaBST {
    // O(N) if it is balancing BST
    static public int kthSmallest(TreeNode root, int k) {
        int leftSubTreeNodesNum = countNodes(root.left);
        if (k <= leftSubTreeNodesNum) return kthSmallest(root.left, k);
        if (k == leftSubTreeNodesNum + 1) return root.val;
        return kthSmallest(root.right, k - 1 - leftSubTreeNodesNum);
    }

    static public int countNodes(TreeNode n) {
        if (n == null) return 0;
        return 1 + countNodes(n.left) + countNodes(n.right);
    }
}
