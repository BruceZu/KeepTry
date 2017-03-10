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

public class Leetcode315CountofSmallerNumbersAfterSelf5 {
    //  Access and build BST with recursion
    //  O(nlogn) if it is balance tree
    static public List<Integer> countSmaller(int[] nums) {
        Integer[] result = new Integer[nums.length];
        BSTNode root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert(root, i, 0, result, nums);
        }
        return Arrays.asList(result);
    }

    static private BSTNode insert(BSTNode parent, int index, int way2Num,
                                  Integer[] numOfNodeSmallerThanNodeAtIndexOf, int[] array) {
        if (parent == null) {
            parent = new BSTNode(array[index]);
            numOfNodeSmallerThanNodeAtIndexOf[index] = way2Num;
        } else if (array[index] <= parent.v) {
            parent.way1Num++;
            parent.left = insert(parent.left, index, way2Num, numOfNodeSmallerThanNodeAtIndexOf, array);
        } else {
            parent.right = insert(parent.right, index, way2Num + parent.way1Num + 1,
                    numOfNodeSmallerThanNodeAtIndexOf, array);
        }
        return parent;
    }
}
