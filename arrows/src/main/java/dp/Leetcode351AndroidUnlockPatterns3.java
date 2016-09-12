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

package dp;

/**
 * <pre>
 * DFS
 * othere idea of deciding next key i:
 * If key i is not visited && (cur and i are adjacent || not adjacent but the skipped key is already selected)
 *
 * what a given 2 keys are adjacent or not is decided there value in skipped array;
 *
 * @see <a href="https://discuss.leetcode.com/topic/46260/java-dfs-solution-with-clear-explanations-and-optimization-beats-97-61-12ms">reference</a>
 */
public class Leetcode351AndroidUnlockPatterns3 {
    // remain: the steps remaining
    int DFS(boolean selected[], int[][] skiped, int cur, int remain) {
        if (remain < 0) return 0;
        if (remain == 0) return 1;
        selected[cur] = true;
        int rst = 0;
        for (int i = 1; i <= 9; ++i) {
            int skippedKeyBetweenCurAndi = skiped[cur][i];
            boolean iIsadjacent = skippedKeyBetweenCurAndi == 0;
            if (!selected[i] && (iIsadjacent || (selected[skippedKeyBetweenCurAndi]))) {
                rst += DFS(selected, skiped, i, remain - 1);
            }
        }
        selected[cur] = false;
        return rst;
    }

    public int numberOfPatterns2(int m, int n) {
        /**
         *   adjacent or not
         *    1    2    3
         *    4    5    6
         *    7    8    9
         */
        int skiped[][] = new int[10][10];
        skiped[1][3] = skiped[3][1] = 2;
        skiped[1][7] = skiped[7][1] = 4;
        skiped[3][9] = skiped[9][3] = 6;
        skiped[7][9] = skiped[9][7] = 8;
        skiped[1][9] = skiped[9][1]
                = skiped[2][8] = skiped[8][2]
                = skiped[3][7] = skiped[7][3]
                = skiped[4][6] = skiped[6][4] = 5;
        boolean selected[] = new boolean[10];
        int rst = 0;

        for (int i = m; i <= n; ++i) {
            rst += DFS(selected, skiped, 1, i - 1) * 4;
            rst += DFS(selected, skiped, 2, i - 1) * 4;
            rst += DFS(selected, skiped, 5, i - 1);
        }
        return rst;
    }
}
