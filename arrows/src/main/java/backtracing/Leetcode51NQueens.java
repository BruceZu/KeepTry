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

package backtracing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode51NQueens {
    // O()?
    static public List<List<String>> solveNQueens(int n) {
        char[][] chessboard = new char[n][n];
        List<List<String>> results = new ArrayList<>();
        List<String> result = new ArrayList<>(n);
        backtracking(chessboard, results, result, 0, n);
        return results;
    }

    // As it need get all solutions. it could be void method
    static boolean backtracking(char[][] chessboard, List<List<String>> results, List<String> result, int rowQ, int colRowNum) {
        if (rowQ == colRowNum) {
            results.add(new ArrayList<>(result));
            return true;
        }

        for (int col = 0; col < colRowNum; col++) {
            char mark = (char) (rowQ + 1 + (int) '0');
            if (currentOptionIsValidAndMark(chessboard, rowQ, col, mark)) {
                result.add(getCurrentRowQInStr(col, colRowNum));
                backtracking(chessboard, results, result, rowQ + 1, colRowNum);

                revokeMarkInvalidOptionForLater(chessboard, rowQ, col, mark);
                result.remove(result.size() - 1);
            }
        }
        return false;
    }

    // O(N)
    static String getCurrentRowQInStr(int y, int colNum) {
        char[] r = new char[colNum];
        Arrays.fill(r, '.');
        r[y] = 'Q';
        return new String(r);
    }

    // O(N)
    static boolean currentOptionIsValidAndMark(char[][] chessboard, int x, int y, char value) {
        if (chessboard[x][y] != '\u0000') {
            return false;
        }
        MarkWithUnder(chessboard, x, y, value, '\u0000');
        return true;
    }

    // O(N)
    static void revokeMarkInvalidOptionForLater(char[][] chessboard, int x, int y, char value) {
        MarkWithUnder(chessboard, x, y, '\u0000', value);
    }

    // O(N)
    static void MarkWithUnder(char[][] chessboard, int x, int y, char withChar, char curChar) {
        int rows = chessboard.length;
        int cols = chessboard[0].length;
        // col
        for (int row = x; row < rows; row++) {
            if (chessboard[row][y] == curChar) chessboard[row][y] = withChar;
        }
        // back slash down half side
        int curRow = x, curCol = y;
        while (0 <= curRow && curRow < rows && 0 <= curCol && curCol < cols) {
            if (chessboard[curRow][curCol] == curChar) chessboard[curRow][curCol] = withChar;
            curRow++;
            curCol++;
        }
        // slash down half side
        curRow = x;
        curCol = y;
        while (0 <= curRow && curRow < rows && 0 <= curCol && curCol < cols) {
            if (chessboard[curRow][curCol] == curChar) chessboard[curRow][curCol] = withChar;
            curRow++;
            curCol--;
        }
    }

    // ------------
    public static void main(String[] args) {
        solveNQueens(4);
    }
}
