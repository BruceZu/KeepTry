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

package stack;

import java.util.Arrays;
import java.util.Stack;

public class Leetcode85MaximalRectangle {
    /**
     * <pre>
     *     <a href="https://leetcode.com/problems/maximal-rectangle/?tab=Description"> LeetCode </a>
     * performance improvement:
     * keep both of calculating the max area and updating the heights[] together step by step.
     *
     * skill:  int[] heights = new int[cols+1];
     * heights[cols] =0;
     *
     * O(M*N)
     */
    static public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int cols = matrix[0].length;
        int rows = matrix.length;

        int[] heights = new int[cols];
        for (int i = 0; i < cols; i++) { // first line
            if (matrix[0][i] == '1') heights[i] = 1;
        }

        int result = maximalRectangleOf(heights); // first line
        for (int r = 1; r < rows; r++) {
            setHeightsWithBottomRowOf(matrix, r, heights);
            result = Math.max(result, maximalRectangleOf(heights));
        }

        return result;
    }

    static private void setHeightsWithBottomRowOf(char[][] matrix, int row, int[] height) {
        for (int col = 0; col < height.length; col++) {
            height[col] = matrix[row][col] == '1' ? height[col] + 1 : 0;
        }
    }

    static public int maximalRectangleOf(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> indexs = new Stack();
        int result = 0;

        for (int i = 0; i <= heights.length; i++) {
            int iheight = (i == heights.length ? 0 : heights[i]);
            while (!indexs.isEmpty() && heights[indexs.peek()] > iheight) {

                int h = heights[indexs.pop()];
                int w = indexs.isEmpty() ? i : i - indexs.peek() - 1;
                result = Math.max(result, h * w);
            }
            indexs.push(i);
        }
        return result;
    }

    // -----------------------------------------------------------------------------------
    public static void main(String[] args) {
        char[][] test = new char[][]{
                "10100".toCharArray(),
                "10111".toCharArray(),
                "11111".toCharArray(),
                "10010".toCharArray()};
        System.out.println(maximalRectangle_dp(test));
        System.out.println(maximalRectangle(test));
        test = new char[][]{
                "00100".toCharArray(),
                "11110".toCharArray(),
                "00100".toCharArray(),
                "00100".toCharArray(),
                "00100".toCharArray()};
        System.out.println(maximalRectangle_dp(test));
        System.out.println(maximalRectangle(test));
        test = new char[][]{
                "0111110".toCharArray(),
                "0011100".toCharArray(),
                "0001000".toCharArray()};
        System.out.println(maximalRectangle_dp(test));
        System.out.println(maximalRectangle(test));
        test = new char[][]{
                "11100000".toCharArray(),
                "11111111".toCharArray(),
                "00001111".toCharArray(),
                "00001111".toCharArray()};
        System.out.println(maximalRectangle_dp(test));
        System.out.println(maximalRectangle(test));
        test = new char[][]{
                "00100".toCharArray(),
                "00100".toCharArray(),
                "00100".toCharArray(),
                "01110".toCharArray(),
                "01110".toCharArray()};
        System.out.println(maximalRectangle_dp(test));
        System.out.println(maximalRectangle(test));
    }

    /**
     * -------------DP
     * index
     * 0 1 2 3 4
     * <p>
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     * 由最高高度所表示矩形 and its 最左边界和最右边界得出
     * <p>
     * -- height
     * 1 0 1 0 0
     * 2 0 2 1 1
     * 3 1 3 2 2
     * 4 0 0 3 0
     * <p>
     * --left bounder (including)
     * 0 0 2 0 0
     * 0 0 2 2 2
     * 0 0 2 2 2
     * 0 0 0 3 0
     * <p>
     * --right bounder (excluding):
     * 1 5 3 5 5
     * 1 5 3 5 5
     * 1 5 3 5 5
     * 1 5 5 4 5
     * <p>
     * O（M*N）
     */

    static int maximalRectangle_dp(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] recLeftBi = new int[cols];    // 以该点为底的连续的1的高决定的向左边和右边最大能延展的 rectangle's left bounder index, included
        int[] recRightBi = new int[cols];   // 以该点为底的连续的1的高决定的向左边和右边最大能延展的 rectangle's right bounder index, excluded
        int[] recMaxHeight = new int[cols]; // 以该点为底的连续的1的高决定的向左边和右边最大能延展的 rectangle 的高
        Arrays.fill(recRightBi, Integer.MAX_VALUE);
        Arrays.fill(recLeftBi, -1);
        int MaxRectangleArea = 0;

        for (int r = 0; r < rows; r++) {
            int nextLeftBounderIndexCandidate = 0;
            for (int i = 0; i < cols; i++) {
                recMaxHeight[i] = matrix[r][i] == '1' ? recMaxHeight[i] + 1 : 0;
                //---
                if (matrix[r][i] == '1') {// need calculate. get the right most one:
                    recLeftBi[i] = Math.max(recLeftBi[i], nextLeftBounderIndexCandidate);
                } else { // 0 do not need calculate,
                    recLeftBi[i] = -1; // no height ,set to default 0 used for next row if next row this column is 1.
                    nextLeftBounderIndexCandidate = i + 1; // update
                }
            }
            //  left <---- right
            int nextRightBounderIndexCandidate = cols; //
            for (int i = cols - 1; i >= 0; i--) {
                if (matrix[r][i] == '1') {
                    recRightBi[i] = Math.min(recRightBi[i], nextRightBounderIndexCandidate);
                } else { //
                    recRightBi[i] = Integer.MAX_VALUE;
                    nextRightBounderIndexCandidate = i;
                }
            }

            for (int col = 0; col < cols; col++) {
                int currentRectangleArea = (recRightBi[col] - recLeftBi[col]) * recMaxHeight[col];
                MaxRectangleArea = Math.max(MaxRectangleArea, currentRectangleArea);
            }
        }
        return MaxRectangleArea;
    }

    // --------------------- DP2
    // O(M*N*?)
    // http://www.sigmainfy.com/blog/leetcode-maximal-rectangle.html
    static int maximalRectangle_dp2(char[][] matrix) {
        int rows = matrix.length;
        if (rows < 1)
            return 0;
        int cols = matrix[0].length;

        // The consecutive 1′s in row i from point (i, j) until the last consecutive 1 to the right
        int[][] widths = new int[rows][cols];
        for (int row = 0; row < rows; ++row) {
            widths[row][cols - 1] = ('0' == matrix[row][cols - 1]) ? 0 : 1;
            for (int c = cols - 2; c >= 0; --c) {
                widths[row][c] = ('0' == matrix[row][c]) ? 0 : 1 + widths[row][c + 1]; // DP!
            }
        }

        // take each point (i, j) as the top left corner of the rectangle and to get the maximal rectangle
        int maxArea = 0;
        for (int row = 0; row < rows; ++row)
            for (int c = 0; c < cols; ++c) {
                int width = widths[row][c];
                for (int r = row; r < rows && width > 0; r++) {
                    width = Math.min(width, widths[r][c]);
                    maxArea = Math.max(maxArea, (r - row + 1) * width);
                }
            }
        return maxArea;
    }
}
