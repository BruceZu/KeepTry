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

import java.util.*;

public class Leetcode52NQueens {
    private Set<Integer> occupiedCol = new HashSet();
    private Set<Integer> occupiedDiagonals135 = new HashSet();
    private Set<Integer> occupiedDiagonals45 = new HashSet();

    public int totalNQueens(int n) {
        int[] resolutions = new int[1];
        backtracking(0, n, resolutions);
        return resolutions[0];
    }

    private void backtracking(int row, int N, int[] solutions) {
        if (row == N) {
            solutions[0]++;
            return;
        }

        int delta, sum;
        for (int optionalCol = 0; optionalCol < N; optionalCol++) {
            if (occupiedCol.contains(optionalCol)) continue;

            delta = row - optionalCol;
            if (occupiedDiagonals135.contains(delta)) continue;

            sum = row + optionalCol;
            if (occupiedDiagonals45.contains(sum)) continue;
            //
            occupiedCol.add(optionalCol);
            occupiedDiagonals135.add(delta);
            occupiedDiagonals45.add(sum);
            backtracking(row + 1, N, solutions);

            occupiedCol.remove(optionalCol);
            occupiedDiagonals135.remove(delta);
            occupiedDiagonals45.remove(sum);
        }
    }
}
