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

package backtracing;

public class Leetcode37SudokuSolver {
  /*
  37. Sudoku Solver

  Write a program to solve a Sudoku puzzle by filling the empty cells.

  A sudoku solution must satisfy all of the following rules:

      Each of the digits 1-9 must occur exactly once in each row.
      Each of the digits 1-9 must occur exactly once in each column.
      Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.

  The '.' character indicates empty cells.


  Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
  Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
  Explanation: The input board is shown above and the only valid solution is shown below:

  Constraints:

      board.length == 9
      board[i].length == 9
      board[i][j] is a digit or '.'.
      It is guaranteed that the input board has only one solution.
   */
  /*
   Backtracking
  */
  public void solveSudoku(char[][] B) {
    if (B == null || B.length == 0) return;
    solve(B);
  }

  public boolean solve(char[][] B) {
    for (int i = 0; i < B.length; i++) {
      for (int j = 0; j < B[0].length; j++) {
        if (B[i][j] == '.') { // empty cell
          for (char c = '1'; c <= '9'; c++) { // trial. Try 1 through 9
            if (isValid(B, i, j, c)) {
              B[i][j] = c; // Put c for this cell
              if (solve(B)) return true; // If it's the solution return true
              else B[i][j] = '.'; // Otherwise go back
            }
          }
          return false;
        }
      }
    }
    return true; // never come here.
  }

  private boolean isValid(char[][] board, int r, int col, char c) {
    for (int i = 0; i < 9; i++) {
      if (board[i][col] != '.' && board[i][col] == c) return false; // check row
      if (board[r][i] != '.' && board[r][i] == c) return false; // check column
      char bc = board[3 * (r / 3) + i / 3][3 * (col / 3) + i % 3]; // check 3*3 block
      if (bc != '.' && bc == c) return false;
    }
    return true;
  }
}
