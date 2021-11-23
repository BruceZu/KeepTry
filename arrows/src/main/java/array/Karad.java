//  Copyright 2021 The KeepTry Open Source Project
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

package array;

public class Karad {

  /*
    A nonogram is a logic puzzle, similar to a crossword, in which the player is given a blank grid and has to color it according to some instructions. Specifically, each cell can be either black or white, which we will represent as 'B' for black and 'W' for white.

    +------------+
    | W  W  W  W |
    | B  W  W  W |
    | B  W  B  B |
    | W  W  B  W |
    | B  B  W  W |
    +------------+

    For each row and column, the instructions give the lengths of contiguous runs of black ('B') cells. For example, the instructions for one row of [ 2, 1 ] indicate that there must be a run of two black cells, followed later by another run of one black cell, and the rest of the row filled with white cells.

    These are valid solutions: [ W, B, B, W, B ] and [ B, B, W, W, B ] and also [ B, B, W, B, W ]
    This is not valid: [ W, B, W, B, B ] since the runs are not in the correct order.
    This is not valid: [ W, B, B, B, W ] since the two runs of Bs are not separated by Ws.

    Your job is to write a function to validate a possible solution against a set of instructions. Given a 2D matrix representing a player's solution; and instructions for each row along with additional instructions for each column; return True or False according to whether both sets of instructions match.

    Example instructions #1

    matrix1 = [[ W, W, W, W ],
               [ B, W, W, W ],
               [ B, W, B, B ],
               [ W, W, B, W ],
               [ B, B, W, W ]]
    rows1_1    =  [], [1], [1,2], [1], [2]
    columns1_1 =  [2,1], [1], [2], [1]
    validateNonogram(matrix1, rows1_1, columns1_1) => True

    Example solution matrix:
    matrix1 ->
                                       row
                    +------------+     instructions
                    | W  W  W  W | <-- []
                    | B  W  W  W | <-- [1]
                    | B  W  B  B | <-- [1,2]
                    | W  W  B  W | <-- [1]
                    | B  B  W  W | <-- [2]
                    +------------+
                      ^  ^  ^  ^
                      |  |  |  |
      column       [2,1] | [2] |
      instructions      [1]   [1]


    Example instructions #2

    (same matrix as above)
    rows1_2    =  [], [], [1], [1], [1,1]
    columns1_2 =  [2], [1], [2], [1]
    validateNonogram(matrix1, rows1_2, columns1_2) => False

    The second and third rows and the first column do not match their respective instructions.

    Example instructions #3

    (same matrix as above)
    rows1_3    = [], [1], [3], [1], [2]
    columns1_3 = [3], [1], [2], [1]
    validateNonogram(matrix1, rows1_3, columns1_3) => False

    The third row and the first column do not match their respective instructions.

    Example instructions #4

    (same matrix as above)
    rows1_4    =  [], [1,1], [1,2], [1], [2]
    columns1_4 =  [2,1], [1], [2], [1]
    validateNonogram(matrix1, rows1_4, columns1_4) => False

    The second row and the first column do not match their respective instructions


    Example instructions #5

    matrix2 = [
     [ W, W ],
     [ B, B ],
     [ B, B ],
     [ W, B ]
    ]
    rows2_1    = [], [2], [2], [1]
    columns2_1 = [1, 1], [3]
    validateNonogram(matrix2, rows2_1, columns2_1) => False

    The black cells in the first column are not separated by white cells.

    Example instructions #6

    (same matrix as above)
    rows2_2    = [], [2], [2], [1]
    columns2_2 = [3], [3]
    validateNonogram(matrix2, rows2_2, columns2_2) => False

    The first column has the wrong number of black cells.

    Example instructions #7

    (same matrix as above)
    rows2_3    = [], [], [], []
    columns2_3 = [], []
    validateNonogram(matrix2, rows2_3, columns2_3) => False

    All of the instructions are empty

    n: number of rows in the matrix
    m: number of columns in the matrix
  */

  public static void main(String[] argv) {
    char[][] matrix1 = {
      {'W', 'W', 'W', 'W'},
      {'B', 'W', 'W', 'W'},
      {'B', 'W', 'B', 'B'},
      {'W', 'W', 'B', 'W'},
      {'B', 'B', 'W', 'W'}
    };

    int[][] rows1_1 = {{}, {1}, {1, 2}, {1}, {2}};
    int[][] columns1_1 = {{2, 1}, {1}, {2}, {1}};

    int[][] rows1_2 = {{}, {}, {1}, {1}, {1, 1}};
    int[][] columns1_2 = {{2}, {1}, {2}, {1}};

    int[][] rows1_3 = {{}, {1}, {3}, {1}, {2}};
    int[][] columns1_3 = {{3}, {1}, {2}, {1}};

    int[][] rows1_4 = {{}, {1, 1}, {1, 2}, {1}, {2}};
    int[][] columns1_4 = {{2, 1}, {1}, {2}, {1}};

    char[][] matrix2 = {
      {'W', 'W'},
      {'B', 'B'},
      {'B', 'B'},
      {'W', 'B'}
    };

    int[][] rows2_1 = {{}, {2}, {2}, {1}};
    int[][] columns2_1 = {{1, 1}, {3}};

    int[][] rows2_2 = {{}, {2}, {2}, {1}};
    int[][] columns2_2 = {{3}, {3}};

    int[][] rows2_3 = {{}, {}, {}, {}};
    int[][] columns2_3 = {{}, {}};
    System.out.println(isStructionsGood(matrix1, rows1_1, columns1_1));
    System.out.println(isStructionsGood(matrix1, rows1_2, columns1_2));
    System.out.println(isStructionsGood(matrix1, rows1_3, columns1_3));
    System.out.println(isStructionsGood(matrix1, rows1_4, columns1_4));

    System.out.println(isStructionsGood(matrix2, rows2_1, columns2_1));
    System.out.println(isStructionsGood(matrix2, rows2_2, columns2_2));
    System.out.println(isStructionsGood(matrix2, rows2_3, columns2_3));
  }

  /*
    All sub-arrays of the 2D array `rows` or `columns` do not have the same length.
    O(N*M)  time,
    O(1) extra space
    how to avoid duplicate code to save interview coding time?
  */
  public static boolean isStructionsGood(char[][] grid, int[][] rows, int[][] cols) {
    if (grid == null || grid[0].length == 0) return true;
    int N = grid.length, M = grid[0].length;
    // check rows instructions
    for (int i = 0; i < N; i++) {
      int n = 0; // number of B
      int[] inst = rows[i];
      int idx = 0;
      for (int j = 0; j < M; j++) {
        // from this line till end of outer loop: code is duplicated with cols checking
        char v = grid[i][j];
        if (v == 'W') {
          if (n != 0) {
            if (idx == inst.length || idx < inst.length && n != inst[idx]) return false;
            idx++;
            n = 0;
          }
        } else {
          n++;
        }
      }
      if (n != 0) {
        if (idx == inst.length || idx < inst.length && n != inst[idx]) return false;
        idx++;
      }
      if (idx != inst.length) return false;
    }
    // check column instructions
    for (int j = 0; j < M; j++) {
      int n = 0; // number of B
      int idx = 0;
      int[] inst = cols[j];
      for (int i = 0; i < N; i++) {
        // from this line till end of outer loop: code is duplicated with rows checking
        char v = grid[i][j];
        if (v == 'W') {
          if (n != 0) {
            if (idx == inst.length || n != inst[idx]) return false;
            idx++;
            n = 0;
          }
        } else {
          n++;
        }
      }
      if (n != 0) {
        if (idx == inst.length || idx < inst.length && n != inst[idx]) return false;
        idx++;
      }
      if (idx != inst.length) return false;
    }

    return true;
  }
}
