//  Copyright 2016 The Sawdust Open Source Project
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

package nosubmmitted;

/**
 * 308. Range Sum Query 2D - Mutable
 * https://leetcode.com/problems/range-sum-query-2d-mutable/
 * Difficulty: Hard <pre>
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined
 * by its upper left corner (row1, col1) and lower right corner (row2, col2).
 * <p/>
 * <p/>
 * Range Sum Query 2D
 * The above rectangle (with the red border) is defined by (row1, col1) = (2, 1)
 * and (row2, col2) = (4, 3), which contains sum = 8.
 * <p/>
 * Example:
 * Given matrix = [
 * [3, 0, 1, 4, 2],
 * [5, 6, 3, 2, 1],
 * [1, *2, 0, 1, 5],
 * [4, 1, 0, 1, 7],
 * [1, 0, 3, *0, 5]
 * ]
 * <p/>
 * sumRegion(2, 1, 4, 3) -> 8
 * update(3, 2, 2)
 * sumRegion(2, 1, 4, 3) -> 10
 * Note:
 * The matrix is only modifiable by the update function.
 * You may assume the number of calls to update and sumRegion function is distributed evenly.
 * You may assume that row1 ≤ row2 and col1 ≤ col2.
 *      x------>
 *   y
 *   |
 *   |
 *   V
 * <p/>
 * Hide Company Tags Google
 * <p/>
 * <p/>
 * Hide Tags Binary Indexed Tree Segment Tree
 *  https://en.wikipedia.org/wiki/Fenwick_tree
 *  https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
 *  http://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 *  http://www.cnblogs.com/xudong-bupt/p/3484080.html
 *  https://www.youtube.com/watch?v=kqjOYGc-Wvg
 * <p/>
 * <p/>
 *  1> build BIT array:
 *    tree[idx] = sum of array[i], i is [(idx - 2^r + 1) ~ idx] ;
 *                                 2^r is the number of 0 of bit format of index, 2^r  = (idx & (-idx));
 *              =  sum of array[i], i is [(idx - (idx & (-idx)) + 1) ~ idx] ;
 * <p/>
 *  2> update BIT array: <pre>
 *     void update (int i,int dx)  {
 *        while (i <=maxlen){ // maxlen is the length of array.
 *          tree[i] += dx;   //add value dx to array[i]
 *          i += (i & -i);
 *        }
 *    }
 * 1>  build BIT array by update it: <pre>
 *      void build () {
 *         int [] tree = new tree[N];
 *         for (int i = 1; i <=N; ++i){
 *            update (i,a[i]);
 *         }
 *      }
 * <p/>
 * 3> range sum query by BIT array:
 *    int getSum (int i) {
 *        int sum = 0;
 *        while (i > 0) {
 *          sum += tree[i];
 *          i -= (i & -i);
 *       }
 *       return sum;
 *    }
 * <p/>
 * <p/>
 * RMQ（Range Minimum/Maximum Query）
 * <p/>
 * <p/>
 * Hide Similar Problems (M) Range Sum Query 2D - Immutable (M) Range Sum Query - Mutable
 */
public class LC308RangeSumQuery2DMutable {
// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.update(1, 1, 10);
// numMatrix.sumRegion(1, 2, 3, 4);

    class NumMatrix {

        public void NumMatrix1(int[][] matrix) {

        }

        public void update1(int row, int col, int val) {

        }

        public int sumRegion1(int row1, int col1, int row2, int col2) {
            return 0;
        }

        // the fast one beat 97% . not share code

        // not completely one
        //maintain additional array sum, for sum[i][j], it is the sum for region (0,0,i,j)
        //
        int[][] sum;
        boolean empty = false;

        public void NumMatrix2(int[][] matrix) { // cut the void when run this case
            if (matrix.length == 0 || matrix[0].length == 0) {
                empty = true;
            } else {
                sum = new int[matrix.length + 1][matrix[0].length + 1];
                sum[1][1] = matrix[0][0];
                for (int i = 2; i < sum[0].length; i++) {
                    sum[1][i] = matrix[0][i - 1] + sum[1][i - 1];
                }
                for (int i = 2; i < sum.length; i++) {
                    sum[i][1] = matrix[i - 1][0] + sum[i - 1][1];
                }
                for (int i = 2; i < sum.length; i++) {
                    for (int j = 2; j < sum[0].length; j++) {
                        sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i - 1][j - 1];
                    }
                }
            }
        }

        public int sumRegion2(int row1, int col1, int row2, int col2) {
            return empty ? 0 : sum[row2 + 1][col2 + 1] - sum[row1][col2 + 1] - sum[row2 + 1][col1] + sum[row1][col1];
        }

        public void update2(int row, int col, int val) {
            // ????
        }

        // beat 74.69%
        // Using 2D Binary Indexed Tree, 2D BIT Def:
        // bit[i][j] saves the rangeSum of [i-(i&-i), i] x [j-(j&-j), j]
        // note bit index == matrix index + 1
        int n, m;
        int[][] bit, a;

        public NumMatrix(int[][] matrix) {
            if (matrix.length < 1) return;
            n = matrix.length;
            m = matrix[0].length;
            bit = new int[n + 1][m + 1];
            a = new int[n][m];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    update(i, j, matrix[i][j]);
        }

        public void update(int row, int col, int val) {
            int diff = val - a[row][col];
            a[row][col] = val;
            for (int i = row + 1; i <= n; i += i & -i)
                for (int j = col + 1; j <= m; j += j & -j)
                    bit[i][j] += diff;
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
        }

        public int sum(int row, int col) {
            int tot = 0;
            for (int i = row + 1; i > 0; i -= i & -i)
                for (int j = col + 1; j > 0; j -= j & -j)
                    tot += bit[i][j];
            return tot;
        }
    }
}



