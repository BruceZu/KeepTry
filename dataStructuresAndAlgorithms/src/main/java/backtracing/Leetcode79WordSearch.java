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
 * [
 *      ['A','B','C','E'],
 *      ['S','F','C','S'],
 *      ['A','D','E','E']
 * ]
 *
 *      word = "ABCCED",       -> returns true,
 *      word = "SEE",          -> returns true,
 *      word = "FSA",          -> returns true,
 *      word = "ABCCFSADEESE", -> returns true,
 *
 *      word = "ABCB", -> returns false.
 *      word = "FSE",  -> returns false.
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
 */
public class Leetcode79WordSearch {
    private static boolean rightWay(int i, int j, boolean[][] used, char[][] board, char aim) {
        return 0 <= i && i < used.length
                && 0 <= j && j < used[0].length
                && used[i][j] == false
                && board[i][j] == aim;
    }

    //@param from  original = 0; left = 1; right = 2; top = 3; down = 4;
    private static boolean go(int si, int sj, char[] arr, int index, char[][] board, boolean[][] used, int from) {
        if (index == arr.length) {
            return true;
        }

        //try right
        if (from != 2 && rightWay(si, sj + 1, used, board, arr[index])) {
            sj = sj + 1;
            used[si][sj] = true;
            if (go(si, sj, arr, index + 1, board, used, 1)) {
                return true;
            }
            used[si][sj] = false;
            sj = sj - 1;
        }

        // try top
        if (from != 3 && rightWay(si - 1, sj, used, board, arr[index])) {
            si = si - 1;
            used[si][sj] = true;
            if (go(si, sj, arr, index + 1, board, used, 4)) {
                return true;
            }
            used[si][sj] = false;
            si = si + 1;
        }

        // try down
        if (from != 4 && rightWay(si + 1, sj, used, board, arr[index])) {
            si = si + 1;
            used[si][sj] = true;
            if (go(si, sj, arr, index + 1, board, used, 3)) {
                return true;
            }
            used[si][sj] = false;
            si = si - 1;
        }
        // try left
        if (from != 1 && rightWay(si, sj - 1, used, board, arr[index])) {
            sj = sj - 1;
            used[si][sj] = true;
            if (go(si, sj, arr, index + 1, board, used, 2)) {
                return true;
            }
            used[si][sj] = false;
            sj = sj + 1;
        }
        return false;
    }

    public static boolean exist(char[][] board, String word) {

        boolean[][] used = new boolean[board.length][board[0].length];
        char[] arr = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length;
                 j++) {
                if (board[i][j] == arr[0]) {
                    used[i][j] = true;
                    if (go(i, j, arr, 1, board, used, 0)) {
                        return true;
                    }
                    used[i][j] = false;
                }
            }
        }
        return false;
    }
}
