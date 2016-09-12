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

import java.util.LinkedList;
import java.util.List;

/**
 * 272. Closest Binary Search Tree Value II
 * https://leetcode.com/problems/closest-binary-search-tree-value-ii/
 * Difficulty: Hard <pre>
 * Given a non-empty binary search tree and a target value,
 * find k values in the BST that are closest to the target.
 * <p/>
 * Note:
 * Given target value is a floating point.
 * You may assume k is always valid, that is: k ≤ total nodes.
 * You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
 * Follow up:
 * Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
 * <p/>
 * Hint:
 * <p/>
 * 1 Consider implement these two helper functions:
 *    --getPredecessor(N), which returns the next smaller node to N.
 *    --getSuccessor(N), which returns the next larger node to N.
 * 2 Try to assume that each node has a parent pointer, it makes the problem much easier.
 * 3 Without parent pointer we just need to keep track of the path from the root to the current node using a stack.
 * 4 You would need two stacks to track the path in finding predecessor and successor node separately.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Tree Stack
 * Hide Similar Problems (M) Binary Tree Inorder Traversal (E) Closest Binary Search Tree Value
 */
public class LC272ClosestBinarySearchTreeValueII {

    class Solution {
        /**
         * beat 92.27%
         * This solution is maintaining a linkedlist and break the travelsal when the rightmost is larger than the leftmost.
         *
         * @param root
         * @param target
         * @param k
         * @return
         */
        public List<Integer> closestKValues(TreeNode root, double target, int k) {
            List<Integer> res = new LinkedList<Integer>();
            helper(root, target, k, res);
            return res;
        }

        private void helper(TreeNode root, double target, int k, List<Integer> res) {
            if (root == null) {
                return;
            }
            helper(root.left, target, k, res);
            if (res.size() < k) {
                res.add(root.val);
            } else {
                if (Math.abs(res.get(0) - target) > Math.abs(root.val - target)) {
                    res.remove(0);
                    res.add(root.val);
                } else {
                    return;
                }
            }
            helper(root.right, target, k, res);
        }

        /**
         * save speed as above
         */
        public List<Integer> closestKValues2(TreeNode root, double target, int k) {
            LinkedList<Integer> list = new LinkedList<Integer>();
            closestKValuesHelper(list, root, target, k);
            return list;
        }

        /**
         * @return <code>true</code> if result is already found.
         */
        private boolean closestKValuesHelper(LinkedList<Integer> list, TreeNode root, double target, int k) {
            if (root == null) {
                return false;
            }

            if (closestKValuesHelper(list, root.left, target, k)) {
                return true;
            }

            if (list.size() == k) {
                if (Math.abs(list.getFirst() - target) < Math.abs(root.val - target)) {
                    return true;
                } else {
                    list.removeFirst();
                }
            }

            list.addLast(root.val);
            return closestKValuesHelper(list, root.right, target, k);
        }
    }
}

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

