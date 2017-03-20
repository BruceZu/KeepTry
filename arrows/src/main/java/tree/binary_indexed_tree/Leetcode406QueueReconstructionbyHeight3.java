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

package tree.binary_indexed_tree;

import java.util.Arrays;

public class Leetcode406QueueReconstructionbyHeight3 {
    /**
     * <pre>
     * Sort is not same as  Leetcode406QueueReconstructionbyHeight2
     * This make it possible update the status, chair is occupied, at once
     * O（n*logn*logn）   BIT
     */
    static public int[][] reconstructQueue(int[][] people) {

        int[][] result = new int[people.length][2];
        Arrays.parallelSort(people,
                // should using compare, do not use minus operation
                (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] BIT = new int[people.length + 1];
        for (int i = 0; i < people.length; i++) {
            update(BIT, i, 1);
        }

        boolean[] notAvailableChairAt = new boolean[people.length]; // flat array
        for (int i = 0; i < people.length; i++) {
            int chairsBefore = people[i][1];
            int targetIndex = binarySearchTargetIndexByBIT(BIT, 0, people.length - 1, chairsBefore + 1, notAvailableChairAt);
            notAvailableChairAt[targetIndex] = true;
            update(BIT, targetIndex, -1);
            result[targetIndex] = people[i];
        }
        return result;
    }

    // O(lgn*lgn)
    static int binarySearchTargetIndexByBIT(int[] BIT, int lIndexInFlatArray, int rIndexInFlatArray, int key, boolean[] notAvailableChair) {
        // sure it will be found in this scenario
        while (lIndexInFlatArray <= rIndexInFlatArray) {
            int m = (lIndexInFlatArray + rIndexInFlatArray) >>> 1;
            long midVal = sum(BIT, m);// care  o(lgN)
            if (midVal < key)
                lIndexInFlatArray = m + 1;
            else if (midVal > key || midVal == key && notAvailableChair[m]) // care duplicates
                rIndexInFlatArray = m - 1;
            else
                return m; // key found
        }
        return lIndexInFlatArray;
    }

    static int sum(int[] BIT, int index) {
        int idx = index + 1;
        int sum = 0;
        while (idx > 0) {
            sum += BIT[idx];
            idx -= idx & (-idx);
        }
        return sum;
    }

    static void update(int[] BIT, int index, int delta) {
        int idx = index + 1;
        while (idx < BIT.length) {
            BIT[idx] += delta;
            idx += idx & (-idx);
        }
    }

    public static void main(String[] args) {
        int[][] people = new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        reconstructQueue(people);
    }
}
