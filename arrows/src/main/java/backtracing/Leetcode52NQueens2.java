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

public class Leetcode52NQueens2 {
    public int totalNQueens(int n) {
        int[] resolutions = new int[1];
        backtracking(0, n, resolutions, new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1]);
        return resolutions[0];
    }

    private void backtracking(int row, int N, int[] solutions,
                              boolean[] occupiedCol, boolean[] occupiedDiagonals135, boolean[] occupiedDiagonals45) {
        if (row == N) {
            solutions[0]++;
            return;
        }

        int delta, sum;
        for (int optionalCol = 0; optionalCol < N; optionalCol++) {
            delta = N - 1 + optionalCol - row;
            sum = row + optionalCol;
            if (occupiedCol[optionalCol] || occupiedDiagonals135[delta] || occupiedDiagonals45[sum]) continue;

            occupiedCol[optionalCol] = occupiedDiagonals135[delta] = occupiedDiagonals45[sum] = true;
            backtracking(row + 1, N, solutions, occupiedCol, occupiedDiagonals135, occupiedDiagonals45);
            occupiedCol[optionalCol] = occupiedDiagonals135[delta] = occupiedDiagonals45[sum] = false;
        }
    }
}
