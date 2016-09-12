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
 *  improve:
 *   Step 1:
 *      Pros:
 *              Instead using boolean char[][]. using '\0' to mark the used letter as word should not contains
 *              '\0'.
 *              Thus make it possible to compare the used letter with next letter of word directly,
 *              without double check. Saved 1 ms.
 *              space is also saved without adding more variable to recursion. Big O(1)
 *              replace
 *                  used[i][j] = true;
 *                  // recursion
 *                  used[i][j] = false;
 *              by:
 *                  board[i][j] = '\0';
 *                  // recursion
 *                  board[i][j] = word[index];
 *     Cons:
 *              boolean operation is fast, so in total sacrifice some performance.
 *  step2 :
 *      Pros:
 *              put the common part 'validing' ahead, thus it is possible to use || to make recursion simple;
 *              Just make code simple;
 *      Cons:
 *              sacrifice some performance. 6ms -> 8ms
 */
public class Leetcode79WordSearch2 {

    // @param from  original = 0; left = 1; right = 2; top = 3; down = 4;
    private static boolean go(int i, int j, char[] arr, int index, char[][] board, int from) {
        if (board[i][j] != '\0' && board[i][j] == arr[index]) {
            if (index == arr.length - 1) {
                return true;
            }
            board[i][j] = '\0';
            if (from != 2 && j + 1 < board[0].length
                    && go(i, j + 1, arr, index + 1, board, 1)
                    || from != 1 && j - 1 >= 0
                    && go(i, j - 1, arr, index + 1, board, 2)
                    || from != 3 && i - 1 >= 0
                    && go(i - 1, j, arr, index + 1, board, 4)
                    || from != 4 && i + 1 < board.length
                    && go(i + 1, j, arr, index + 1, board, 3)) {
                return true;
            }
            board[i][j] = arr[index];
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
        // boolean[][] used = new boolean[board.length][board[0].length];
        char[] arr = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length;
                 j++) {
                if (board[i][j] == arr[0]) {
                    if (go(i, j, arr, 0, board, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
