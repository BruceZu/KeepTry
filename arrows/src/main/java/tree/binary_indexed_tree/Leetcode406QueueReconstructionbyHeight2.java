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
import java.util.Comparator;

public class Leetcode406QueueReconstructionbyHeight2 {
    /**
     * <pre>
     * O（n*logn*logn）   BIT
     * 1> Sort by height, if height is equal, sort by second item
     * [4,4], [5,0], [5,2], [6,1], [7,0], [7,1]
     * 2> initialize all position with 1 in flat array.
     * 3> find location
     *
     * _       _      _      _      _      _
     *
     * _       _      _      _    [4,4]    _
     *
     * [5,0]    _      _      _    [4,4]    _
     *
     * [5,0]    _    [5,2]    _    [4,4]    _
     *
     * [5,0]    _    [5,2]  [6,1]  [4,4]    _
     *
     * [5,0]  [7,0]  [5,2]  [6,1]  [4,4]    _
     *
     * [5,0]  [7,0]  [5,2]  [6,1]  [4,4]  [7,1]
     * 需要数空位置数 用BIT
     * [4,4], [5,0], [5,2], [6,1], [7,0], [7,1]
     * 0      1      2      3      4     5  index in sorted array
     * 4      0      2      3      1     5  index in result
     *
     * 1   1   1   1   1   1
     * 1   1   1   1   0   1     // find [4,4]   by sum 5 or 5th empty chair,  index = 4, and convert 1 to 0
     * 1   1   1   1   0   1     // find [5,0]   by sum 1 or 1th empty chair,  index = 0, * DON NOT *  convert 1 to 0  ---1 care
     * 0   1   0   1   0   1     // find [5,2]   by sum 3 or 3th empty chair,  index = 2, and convert 1 to 0
     * 0   1   0   0   0   1     // find [6,1]   by sum 2 or 2th empty chair,  index = 3, and convert 1 to 0
     * 0   1   0   0   0   1     // find [7,0]   by sum 1 or 1th empty chair,  index = 1, * DO NOT * convert 1 to 0
     * binary search, firstly reach index 2. It is invalid, continue binary search   -------------------------------------------2 care
     * 0   0   0   0   0   0     // find [7,1] pos by 2th  empty chair,  pos = 6, convert 1 to 0
     *
     * [5,0], [7,0],  [5,2], [6,1], [4,4], [7,1]
     *  0     1       2      3      4      5
     */
    static public int[][] reconstructQueue(int[][] people) {

        int[][] result = new int[people.length][2];
        Arrays.parallelSort(people,
                // should using compare, do not use minus operation
                (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int[] BIT = new int[people.length + 1];
        for (int i = 0; i < people.length; i++) {
            update(BIT, i, 1);
        }

        boolean[] notAvailableChair = new boolean[people.length]; // flat array
        int[] targetIndexOf = new int[people.length];

        for (int i = 0; i < people.length; i++) {
            int chairsBefore = people[i][1];
            int targetIndex = binarySearchTargetIndexByBIT(BIT, 0, people.length - 1, chairsBefore + 1, notAvailableChair);
            targetIndexOf[i] = targetIndex;
            updateChairStatus(i, people, targetIndexOf, notAvailableChair, BIT);
        }

        for (int i = 0; i < people.length; i++) {
            result[targetIndexOf[i]] = people[i];
        }
        return result;
    }

    static void updateChairStatus(int i, int[][] people, int[] targetIndexOf, boolean[] notAvailableChair, int[] BIT) {
        notAvailableChair[targetIndexOf[i]] = true;
        // update BIT. Amortized o(lgn)
        if (i < people.length - 1 && people[i][0] != people[i + 1][0]) {
            int height = people[i][0];
            while (i >= 0 && people[i][0] == height) {
                update(BIT, targetIndexOf[i], -1);
                i--;
            }
        }
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
