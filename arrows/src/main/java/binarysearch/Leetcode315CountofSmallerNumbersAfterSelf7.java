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

public class Leetcode315CountofSmallerNumbersAfterSelf7 {
    //  Segment tree
    static class STNode {
        int range_l, range_r;
        int numOfExistingInRange;
        STNode l_child, r_child;

        public int leftMedian() {
            return (range_r + range_l) / 2;
        }

        public STNode(int from, int to) {
            this.range_l = from;
            this.range_r = to;
        }
    }

    // O(nlogn)
    static public List<Integer> countSmaller(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted); // O(nlogn)
        for (int i = 0; i < nums.length; i++) {// O(nlogn)
            nums[i] = Arrays.binarySearch(sorted, nums[i]);
        }

        STNode root = new STNode(0, nums.length - 1);
        Integer[] result = new Integer[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = sumFromStartToIndexOf(nums[i] - 1, root);
            addExistingAtIndexOf(nums[i], root);
        }

        return Arrays.asList(result);
    }

    static private int sumFromStartToIndexOf(int x, STNode root) {
        if (root == null) return 0; // for min index=0, out of range

        if (root.range_r <= x) {
            return root.numOfExistingInRange;
        } else {
            int mid = root.leftMedian();
            if (x <= mid) {
                return sumFromStartToIndexOf(x, root.l_child);
            } else {
                return sumFromStartToIndexOf(x, root.l_child) + sumFromStartToIndexOf(x, root.r_child);
            }
        }
    }

    static private void addExistingAtIndexOf(int x, STNode parent) {
        parent.numOfExistingInRange++;
        if (parent.range_r == parent.range_l) return; // bottom

        int mid = parent.leftMedian();
        if (x <= mid) {
            if (parent.l_child == null) {
                parent.l_child = new STNode(parent.range_l, mid);
            }
            addExistingAtIndexOf(x, parent.l_child);
        } else {
            if (parent.r_child == null) {
                parent.r_child = new STNode(mid + 1, parent.range_r);
            }
            addExistingAtIndexOf(x, parent.r_child);
        }
    }
}
