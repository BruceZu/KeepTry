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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode315CountofSmallerNumbersAfterSelf8 {
    // selft balancing BST
    // http://www.programmercoach.com/2017/03/programming-interview-pearls-count.html
    // todo: draft JAVA version without test on leetcode yet
    static class TreeNodeWithCounter {
        int val;
        TreeNodeWithCounter left, right;
        int cnt_left = 0, cnt_right = 0, cnt_val = 1;

        public void deleteChild() {
            left = null;
            right = null;
        }

        TreeNodeWithCounter(int v) {
            this.val = v;
        }

        int insert(int v, TreeNodeWithCounter thisNode) {
            int inversions = cnt_left;
            if (v == val) ++cnt_val;
            else if (v < val) {
                ++cnt_left;
                if (left == null) left = new TreeNodeWithCounter(v);
                else inversions = left.insert(v, left);
            } else {
                ++cnt_right;
                inversions += cnt_val;
                if (right == null) right = new TreeNodeWithCounter(v);
                else inversions += right.insert(v, right);
            }
            if (cnt_left > cnt_right + 2) { // right rotation
                thisNode = left;
                left = left.right;
                thisNode.right = this;
                this.cnt_left = thisNode.cnt_right;
                thisNode.cnt_right += this.cnt_val + this.cnt_right;
            } else if (cnt_left + 2 < cnt_right) { // left rotation
                thisNode = right;
                right = right.left;
                thisNode.left = this;
                this.cnt_right = thisNode.cnt_left;
                thisNode.cnt_left += this.cnt_val + this.cnt_left;
            }
            return inversions;
        }
    }

    static public List<Integer> countSmaller(int[] nums) {
        Integer[] res = new Integer[nums.length];
        if (nums.length > 1) {
            TreeNodeWithCounter root = new TreeNodeWithCounter(nums[nums.length - 1]);
            for (int i = nums.length - 2; i >= 0; --i) res[i] = root.insert(nums[i], root);
            root.deleteChild();
        }
        System.out.println(Arrays.toString(res));
        return Arrays.asList(res);
    }
    //--------------------------------------------------------
    public static void main(String[] args) {
        countSmaller(new int[]{2, 0, 1, 0});
        countSmaller(new int[]{5, 2, 2, 6, 1});

        //                      0  1  2  3  4  5
        countSmaller(new int[]{5, 2, 3, 7, 6, 1});
        //                      3  1  1  2  1  0

        countSmaller(new int[]{1, 7, 9});
        countSmaller(new int[]{1, 1});
    }
}
