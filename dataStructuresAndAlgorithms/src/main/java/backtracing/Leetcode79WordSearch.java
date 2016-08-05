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

package backtracing;

/**
 * <pre>
 *     79. Word Search
 * Difficulty: Medium
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * For example,
 * Given board =
 *
 *     [
 *      ['A','B','C','E'],
 *      ['S','F','C','S'],
 *      ['A','D','E','E']
 *     ]
 *
 *           word                return
 *          ----------------------------
 *          "ABCCED",       ->   true,
 *          "SEE",          ->   true,
 *          "FSA",          ->   true,
 *          "ABCCFSADEESE", ->   true,
 *
 *          "ABCB",         ->   false.
 *          "FSE",          ->   false.
 *
 *
 *       Given board =  [[ 'A' ]]
 *           word                return
 *          ----------------------------
 *          "A"             ->   true
 *
 * Subscribe to see which companies asked this question
 *
 *  Tags Array Backtracking
 *  Similar Problems (H) Word Search II
 *  =================================================================
 *
 *        Note:
 *           1 backtracking need recursion, loop can not keep status.
 *           2 the order of change when changes has dependant relation:
 *           backtracking -> change 1  -> change2 -> change3  -> false
 *                        <- change 1  <- change2 <- change3
 *           3 backtracking means it is as nothing happened when got back to original point.
 *
 *  Runtime 6ms, worse case, Big(m*n*l). m is board lines,n is columns, l is word length.
 */
public class Leetcode79WordSearch {
    //@param from  original = 0; left = 1; right = 2; top = 3; down = 4;
    private static boolean go(int i, int j,
                              char[] arr, int index,
                              char[][] board, boolean[][] used,
                              int from, int lines, int columns) {
        if (index == arr.length) {
            return true;
        }

        if (from != 1 && j - 1 >= 0 && used[i][j - 1] == false && board[i][j - 1] == arr[index]) {
            used[i][j - 1] = true;
            if (go(i, j - 1, arr, index + 1, board, used, 2, lines, columns)) {
                return true;
            }
            used[i][j - 1] = false;
        }

        if (from != 2 && j + 1 < columns && used[i][j + 1] == false && board[i][j + 1] == arr[index]) {
            used[i][j + 1] = true;
            if (go(i, j + 1, arr, index + 1, board, used, 1, lines, columns)) {
                return true;
            }
            used[i][j + 1] = false;
        }

        if (from != 3 && i - 1 >= 0 && used[i - 1][j] == false && board[i - 1][j] == arr[index]) {
            used[i - 1][j] = true;
            if (go(i - 1, j, arr, index + 1, board, used, 4, lines, columns)) {
                return true;
            }
            used[i - 1][j] = false;
        }

        if (from != 4 && i + 1 < lines && used[i + 1][j] == false && board[i + 1][j] == arr[index]) {
            used[i + 1][j] = true;
            if (go(i + 1, j, arr, index + 1, board, used, 3, lines, columns)) {
                return true;
            }
            used[i + 1][j] = false;
        }
        return false;
    }

    public static boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        if (board == null || board.length == 0 || board.length * board[0].length < word.length()) {
            return false;
        }
        int lines = board.length;
        int columns = board[0].length;

        boolean[][] used = new boolean[lines][columns];
        char[] arr = word.toCharArray();

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == arr[0]) {
                    used[i][j] = true;
                    if (go(i, j, arr, 1, board, used, 0, lines, columns)) {
                        return true;
                    }
                    used[i][j] = false;
                }
            }
        }
        return false;
    }
}
