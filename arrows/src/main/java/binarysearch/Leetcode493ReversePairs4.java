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

import java.util.Arrays;
import java.util.List;

public class Leetcode493ReversePairs4 {
    //  toward right to count node whose value is equal or less than target value
    //  keep the count number of left subtree nodes.
    //  Access and build BST
    //  O(nlogn) if it is balance tree
    //  loop
    //  Time Limit Exceeded
    static public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        BSTNode root = new BSTNode(nums[nums.length - 1]);
        int totalPairNum = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            totalPairNum += towardRightCountEqualLessThanFrom(root,
                    (int) (Math.floor((nums[i] - 1) * 0.5d))); // pros: do not need long
            insertToBSTFrom(root, nums[i]);
        }

        return totalPairNum;
    }

    static private int towardRightCountEqualLessThanFrom(BSTNode root, int v) {
        int result = 0;
        while (true) {
            if (root == null) {
                return result;
            }
            if (v == root.v) {
                return result + root.leftSubTreeNodesNum + 1;
            }

            if (v < root.v) {
                root = root.left;
            } else {
                result += root.leftSubTreeNodesNum + 1;
                root = root.right;
            }
        }
    }

    static private void insertToBSTFrom(BSTNode root, int cur) {
        while (true) {
            if (cur <= root.v) {
                root.leftSubTreeNodesNum++;
                if (root.left == null) {
                    root.left = new BSTNode(cur);
                    break;
                }
                root = root.left;
            } else {
                if (root.right == null) {
                    root.right = new BSTNode(cur);
                    break;
                }
                root = root.right;
            }
        }
    }
}
