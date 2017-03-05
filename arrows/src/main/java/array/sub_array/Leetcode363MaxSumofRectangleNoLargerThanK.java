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

package array.sub_array;

import java.util.TreeSet;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/max-sum-of-sub-matrix-no-larger-than-k/?tab=Description">LeetCode</a>
 * @see java.util.TreeMap#getCeilingEntry()
 */
public class Leetcode363MaxSumofRectangleNoLargerThanK {
    static public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix.length == 0) return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int lessThanOrEqualToK = Integer.MIN_VALUE;

        for (int colFrom = 0; colFrom < cols; colFrom++) {
            int[] rowAddeds = new int[rows];
            for (int colTo = colFrom; colTo < cols; colTo++) {
                for (int row = 0; row < rows; row++) {
                    rowAddeds[row] += matrix[row][colTo];
                }

                TreeSet<Integer> treeSet_presums = new TreeSet();
                treeSet_presums.add(0); // (i, j] when the o row is of the result rectangle
                int cumulativeSum = 0;

                for (int rowAdded : rowAddeds) {
                    cumulativeSum += rowAdded;
                    Integer ceiling = treeSet_presums.ceiling(cumulativeSum - k); // O(logN) ?
                    if (ceiling != null) {
                        lessThanOrEqualToK = Math.max(lessThanOrEqualToK, cumulativeSum - ceiling);
                        if (lessThanOrEqualToK == k) return k;// peformance

                    }
                    treeSet_presums.add(cumulativeSum);
                }
            }
        }
        return lessThanOrEqualToK;
    }

    public static void main(String[] args) {
        maxSumSubmatrix(new int[][]{{0, 1}, {-2, 3}}, 2);
        maxSumSubmatrix(new int[][]{{1, 0, 1}, {0, -2, 3}}, 2);
        //I find the 'The rectangle inside the matrix must have an area > 0.'  does exist in test case.
        // e.g.
        maxSumSubmatrix(new int[][]{{1, 0}}, 1);
        maxSumSubmatrix(new int[][]{{1}}, 1);
        maxSumSubmatrix(new int[][]{{1, 2, 1, 3}, {0, 3, 0, 2}}, 10);
    }
}

