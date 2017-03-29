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
import java.util.List;

public class Leetcode51NQueens2 {
    public List<List<String>> solveNQueens2(int n) {
        int[] queenPositioAtRow = new int[n];
        List<List<String>> result = new ArrayList();
        backtracking(queenPositioAtRow, result, n, 0, 0, 0, 0);
        return result;
    }

    // replace 3 boolean array with 3 number in binary perspective
    private void backtracking(int[] queenPositioAtRow, List<List<String>> results,
                              int N, int row,
                              int columnFlag, int slashFlag, int backSlashFlag) {
        int validPosition = ((1 << N) - 1) & ~(columnFlag | slashFlag | backSlashFlag);//
        while (validPosition != 0) {
            int Qposition = validPosition & -validPosition;
            validPosition ^= Qposition;
            queenPositioAtRow[row] = Qposition; // 2^column
            if (row == N - 1) {
                getOnResult(queenPositioAtRow, results, N);
            } else {
                // slash = row + col;
                // backSlash = n - 1 + col - row;
                backtracking(queenPositioAtRow, results,
                        N, row + 1,
                        columnFlag ^ Qposition,
                        (slashFlag ^ Qposition) >> 1,
                        (backSlashFlag ^ Qposition) << 1);
            }
        }
    }

    private void getOnResult(int[] queenPositioAtRow, List<List<String>> res, int N) {
        List<String> list = new ArrayList();
        for (int i = 0; i < N; i++) {
            list.add(getQueenRowStr(i, queenPositioAtRow, N));
        }
        res.add(list);
    }

    private String getQueenRowStr(int row, int[] queenPositioAtRow, int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if ((1 << i) == queenPositioAtRow[row]) sb.append("Q");
            else sb.append(".");
        }
        return sb.toString();
    }
}
