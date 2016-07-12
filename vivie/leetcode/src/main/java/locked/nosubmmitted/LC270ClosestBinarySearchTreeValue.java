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

package locked.nosubmmitted;

/**
 * 270. Closest Binary Search Tree Value
 * https://leetcode.com/problems/closest-binary-search-tree-value/
 * <p/>
 * Difficulty: Easy <pre>
 * Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
 * <p/>
 * Note:
 * Given target value is a floating point.
 * You are guaranteed to have only one unique value in the BST that is closest to the target.
 * Hide Company Tags Microsoft Google Snapchat
 * Hide Tags Tree Binary Search
 * Hide Similar Problems (M) Count Complete Tree Nodes (H) Closest Binary Search Tree Value II
 */
public class LC270ClosestBinarySearchTreeValue {
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class Solution {
        /**
         * beat 26.76%
         *
         * @param root
         * @param target
         * @return attention to (int) cast problem
         * can not check(int) target == root.val here first, (int) will cast down the target value;
         */
        public int closestValue(TreeNode root, double target) {
            if (root.val < target && root.right != null) {
                int rightClosest = closestValue(root.right, target);
                return Math.abs(rightClosest - target) > Math.abs(root.val - target) ? root.val : rightClosest;
            } else if (root.val > target && root.left != null) {
                int leftClosest = closestValue(root.left, target);
                return Math.abs(leftClosest - target) > Math.abs(root.val - target) ? root.val : leftClosest;
            }

            return root.val;
        }

        /**
         * another save speed
         */
        public int closestValue2(TreeNode root, double target) {
            return closest(root, target, root.val);
        }

        private int closest(TreeNode node, double target, int val) {
            if (node == null) return val;
            if (Math.abs(node.val - target) < Math.abs(val - target)) val = node.val;
            if (node.val < target) val = closest(node.right, target, val);
            else if (node.val > target) val = closest(node.left, target, val);
            return val;
        }

        /**
         * another save speed
         */
        public int closestValue3(TreeNode root, double target) {
            TreeNode parent = root;
            int res = -1;
            if (root.left == null && root.right == null) return parent.val;
            if (parent.val == target
                    || (parent.val > target && parent.left == null)
                    || (parent.val < target && parent.right == null)
                    || (root.left == null && root.right == null)) return parent.val;
                //search left
            else if (parent.val > target) {
                res = closestValue3(parent.left, target);
                //search right
            } else {
                res = closestValue3(parent.right, target);
            }
            //return the closest value
            return Math.abs(res - target) > Math.abs(target - parent.val) ? parent.val : res;
        }

        /**
         * another save speed
         */
        public int closestValue4(TreeNode root, double target) {
            int ret = root.val;
            while (root != null) {
                if (Math.abs(target - root.val) < Math.abs(target - ret)) {
                    ret = root.val;
                }
                root = root.val > target ? root.left : root.right;
            }
            return ret;
        }

        /**
         * another save speed
         */
        public int closestValue5(TreeNode root, double target) {
            int a = root.val;
            TreeNode kid = target < a ? root.left : root.right;
            if (kid == null) return a;
            int b = closestValue(kid, target);
            return Math.abs(a - target) < Math.abs(b - target) ? a : b;
        }
    }
}
