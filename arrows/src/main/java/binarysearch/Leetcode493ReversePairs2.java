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

// let insertToBST return root to make code short
public class Leetcode493ReversePairs2 {
    /**
     * O(nlgn) If it is self-balancing BST. worse O(N^2).
     * Time Limit Exceeded in leetcode
     */
    public int reversePairs(int[] nums) {
        int totalPairNum = 0;
        Node root = null;
        for (int num : nums) {
            totalPairNum += towardLeftCountEqualOrGreatThanFrom(root, 2L * num + 1);
            root = insertToBSTFrom(root, num);
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

    // return root
    private Node insertToBSTFrom(Node root, int v) {
        if (root == null) {
            return new Node(v);
        } else if (v < root.v) {
            root.l_child = insertToBSTFrom(root.l_child, v);
        } else {
            root.numOfRootAndRightSubTree++;
            if (v > root.v) {
                root.r_child = insertToBSTFrom(root.r_child, v);
            }
        }
        return root;
    }
}
