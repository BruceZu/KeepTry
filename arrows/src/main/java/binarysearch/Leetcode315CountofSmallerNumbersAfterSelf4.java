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


class BSTNode {
    final int v;
    int way1Num;   // left Sub tree nodes account

    BSTNode left;
    BSTNode right;

    public BSTNode(int val) {
        this.v = val;
    }
}

public class Leetcode315CountofSmallerNumbersAfterSelf4 {

    // BST
    // Access and build BST with loop
    // O(nlogn) if it is balance tree
    public List<Integer> countSmaller(int[] nums) {
        Integer[] result = new Integer[nums.length];
        if (nums == null || nums.length == 0) {
            return Arrays.asList(result);
        }

        BSTNode root = new BSTNode(nums[nums.length - 1]);
        result[nums.length - 1] = 0; // Integer default is null

        for (int i = nums.length - 2; i >= 0; i--) {
            insert(root, result, i, nums);
        }
        return Arrays.asList(result);
    }

    private void insert(BSTNode parent, Integer[] smallNumThanElementAtIndexOf, int i, int[] array) {
        int cur = array[i];
        int way2 = 0;
        while (true) {
            if (cur <= parent.v) {
                parent.way1Num++;
                if (parent.left == null) {
                    parent.left = new BSTNode(cur);
                    smallNumThanElementAtIndexOf[i] = way2;
                    break;
                }
                parent = parent.left;
            } else {
                way2 += parent.way1Num + 1; // (elements in the parent's left subtree) plus one (the parent element itself).
                if (parent.right == null) {
                    parent.right = new BSTNode(cur);
                    smallNumThanElementAtIndexOf[i] = way2;
                    break;
                }
                parent = parent.right;
            }
        }
    }
}
