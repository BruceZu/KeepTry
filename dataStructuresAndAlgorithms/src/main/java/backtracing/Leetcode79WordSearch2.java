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
 *
 *          1   if the letter is limited to 26 alphabets, in ASCII Characters (0~127) scope.
 *              Extended ASCII Codes (128~255)
 *
 *          2   the content of board will be changed.
 *
 *          Pros:
 *              save the space.  this belongs a Limited implement:
 *              and compare the used letter with next letter of word.
 *         Cons:
 *              ^ operation is slower than comparing 2 boolean variable or comparing 'board[i][j] < 0b100000000;'.
 *         discard step 1.
 *
 *  step2 :
 *         put the common part 'validing the i, j' ahead, thus it is possible to use || to make recursion simple;
 *         Just make code simple;
 */
public class Leetcode79WordSearch2 {
    // @param from  original = 0; left = 1; right = 2; top = 3; down = 4;
    private static boolean go(int i, int j, char[] arr, int index, char[][] board, boolean[][] used, int from) {
        if (index == arr.length) {
            return true;
        }
        if (0 <= i && i < used.length && 0 <= j && j < used[0].length
                && used[i][j] == false && board[i][j] == arr[index]) {
            used[i][j] = true;
            if (from != 2 && go(i, j + 1, arr, index + 1, board, used, 1)
                    || from != 1 && go(i, j - 1, arr, index + 1, board, used, 2)
                    || from != 3 && go(i - 1, j, arr, index + 1, board, used, 4)
                    || from != 4 && go(i + 1, j, arr, index + 1, board, used, 3)) {
                return true;
            }
            used[i][j] = false;
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
                    if (go(i, j, arr, 0, board, used, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
