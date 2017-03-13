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

package binarysearch;

/**
 * <pre>
 *
 * <a href="https://leetcode.com/problems/reverse-pairs/?tab=Description">LeetCode</a>
 */
class Node {
    int v, numOfRootAndRightSubTree;
    Node l_child, r_child;

    Node(int val) {
        this.v = val;
        this.numOfRootAndRightSubTree = 1;
    }
}

public class Leetcode493ReversePairs {
    /**
     * <pre>
     * array would be perfect to do the binary search. However,After the j-th element is processed,
     * we need to add it to the searching space. for balance between searching and insertion operations.
     * BST or BIT prevail,
     *
     * O(nlgn) If it is self-balancing BST. worse O(N^2).
     * Time Limit Exceeded in leetcode
     *
     * BST. find and insert in independent methods
     */
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int totalPairNum = 0;
        Node root = new Node(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            totalPairNum += towardLeftCountEqualOrGreatThanFrom(root, 2L * nums[i] + 1);
            insertToBST(root, nums[i]);
        }
        return totalPairNum;
    }

    // >= 2x+1
    private int towardLeftCountEqualOrGreatThanFrom(Node root, long val) {
        if (root == null) {
            return 0;
        }
        if (val < root.v) {
            return root.numOfRootAndRightSubTree + towardLeftCountEqualOrGreatThanFrom(root.l_child, val);
        }
        if (val == root.v) {
            return root.numOfRootAndRightSubTree;
        }
        return towardLeftCountEqualOrGreatThanFrom(root.r_child, val);
    }

    private void insertToBST(Node root, int v) {
        if (v == root.v) {
            root.numOfRootAndRightSubTree++;
        } else if (v < root.v) {
            if (root.l_child == null) {
                root.l_child = new Node(v);
            } else {
                insertToBST(root.l_child, v);
            }
        } else {
            root.numOfRootAndRightSubTree++;
            if (root.r_child == null) {
                root.r_child = new Node(v);
            } else {
                insertToBST(root.r_child, v);
            }
        }
    }
}
