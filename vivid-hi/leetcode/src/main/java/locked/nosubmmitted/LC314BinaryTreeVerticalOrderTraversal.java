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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 314. Binary Tree Vertical Order Traversal
 * https://leetcode.com/problems/binary-tree-vertical-order-traversal/
 * <p/>
 * Difficulty: Medium
 * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
 * <p/>
 * If two nodes are in the same row and column, the order should be from left to right.
 * <p/>
 * Examples:
 * <p/>
 * Given binary tree [3,9,20,null,null,15,7], <pre>
 *      3
 *     /\
 *    /  \
 *    9  20
 *       /\
 *      /  \
 *     15   7
 * return its vertical order traversal as:
 * [
 * [9],
 * [3,15],
 * [20],
 * [7]
 * ]
 * Given binary tree [3,9,8,4,0,1,7],
 *      3
 *     / \
 *    /   \
 *    9    8
 *   /\    /\
 *  /  \  /  \
 * 4   0 1    7
 * return its vertical order traversal as:
 * [
 * [4],
 * [9],
 * [3,0,1],
 * [8],
 * [7]
 * ]
 * Given binary tree [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5),
 *      3
 *     /\
 *    /  \
 *    9   8
 *   /\   /\
 *  /  \ /  \
 *  4  01   7
 *     /\
 *    /  \
 *    5   2
 * return its vertical order traversal as:
 * [
 * [4],
 * [9,5],
 * [3,0,1],
 * [8,2],
 * [7]
 * ]
 * Hide Company Tags Google Snapchat Facebook
 * Hide Tags Hash Table
 * Hide Similar Problems (E) Binary Tree Level Order Traversal
 */
public class LC314BinaryTreeVerticalOrderTraversal {


    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */

    class Solution {
        // second level case beat 87% no map needed
        public List<List<Integer>> verticalOrder(TreeNode root) {
            List<List<Integer>> rst = new ArrayList<>();
            if (root == null) return rst;
            List<Integer> zeroCol = new ArrayList<>();
            rst.add(zeroCol);
            int minCol = 0; //use minCol and maxCol to help insert List at right position (no need for map)
            int maxCol = 0;

            Queue<TreeNode> level = new LinkedList<TreeNode>();
            Queue<Integer> levelCol = new LinkedList<Integer>();
            level.add(root);
            levelCol.add(0);

            while (!level.isEmpty() && !levelCol.isEmpty()) {
                TreeNode curr = level.poll();
                int currCol = levelCol.poll();

                if (currCol < minCol) { //create new List when new column found
                    List<Integer> newCol = new ArrayList<>();
                    newCol.add(curr.val);
                    rst.add(0, newCol); //new leftmost column
                    minCol = currCol;
                } else if (currCol > maxCol) {
                    List<Integer> newCol = new ArrayList<>();
                    newCol.add(curr.val); // new rightmost column
                    rst.add(maxCol - minCol + 1, newCol);
                    maxCol = currCol;
                } else {
                    rst.get(currCol - minCol).add(curr.val);
                }

                if (curr.left != null) {
                    level.add(curr.left);
                    levelCol.add(currCol - 1);
                }

                if (curr.right != null) {
                    level.add(curr.right);
                    levelCol.add(currCol + 1);
                }
            }

            return rst;
        }

        // Here is an example of [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15].
        // Notice that every child access changes one column bucket id.
        // So 12 actually goes ahead of 11. see pickture on desktop


        public List<List<Integer>> verticalOrder2(TreeNode root) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            if (root == null)
                return result;
            Hashtable<TreeNode, Integer> ht = new Hashtable<>();
            int col = 0;
            ht.put(root, col);
            Queue<TreeNode> qu = new LinkedList<TreeNode>();
            Queue<TreeNode> next = new LinkedList<TreeNode>();
            qu.add(root);
            int add = 0;
            while (!qu.isEmpty()) {
                TreeNode curr = qu.poll();
                int curr_col = ht.get(curr);
                if (curr_col == -1) {
                    result.add(0, new ArrayList<Integer>());
                    add = 1;
                } else if (curr_col + add >= result.size()) {
                    result.add(new ArrayList<Integer>());
                }
                result.get(curr_col + add).add(curr.val);

                if (curr.left != null) {
                    next.add(curr.left);
                    ht.put(curr.left, curr_col + add - 1);
                }
                if (curr.right != null) {
                    next.add(curr.right);
                    ht.put(curr.right, curr_col + add + 1);
                }

                if (qu.isEmpty()) {
                    Queue<TreeNode> tmp = qu;
                    qu = next;
                    next = tmp;
                    add = 0;
                }
            }
            return result;
        }
    }
}