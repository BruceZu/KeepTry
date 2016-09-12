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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * replace ArrayList selected with boolean[] selected
 */
public class Leetcode351AndroidUnlockPatterns2 {
    private static Map<Integer, Set<Integer>> directTos = new HashMap();
    private static int[][] canJumpTos = new int[10][10];

    static {
        canJumpTos[1][2] = 3;
        canJumpTos[1][5] = 9;
        canJumpTos[1][4] = 7;

        canJumpTos[2][5] = 8;

        canJumpTos[3][2] = 1;
        canJumpTos[3][5] = 7;
        canJumpTos[3][6] = 9;

        canJumpTos[6][5] = 4;

        canJumpTos[9][8] = 7;
        canJumpTos[9][5] = 1;
        canJumpTos[9][6] = 3;

        canJumpTos[8][5] = 2;

        canJumpTos[7][8] = 9;
        canJumpTos[7][5] = 3;
        canJumpTos[7][4] = 1;

        canJumpTos[4][5] = 6;


        directTos.put(1, new HashSet(Arrays.asList(2, 4, 5, 6, 8)));
        directTos.put(2, new HashSet(Arrays.asList(1, 3, 4, 5, 6, 7, 9)));
        directTos.put(3, new HashSet(Arrays.asList(2, 5, 6, 4, 8)));
        directTos.put(6, new HashSet(Arrays.asList(2, 3, 5, 8, 9, 1, 7)));
        directTos.put(9, new HashSet(Arrays.asList(5, 6, 8, 2, 4)));
        directTos.put(8, new HashSet(Arrays.asList(4, 5, 6, 7, 9, 1, 3)));
        directTos.put(7, new HashSet(Arrays.asList(4, 5, 8, 2, 6)));
        directTos.put(4, new HashSet(Arrays.asList(1, 2, 5, 7, 8, 3, 9)));
        directTos.put(5, new HashSet(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9)));
    }

    private static Set<Integer> nexts(int cur, boolean[] selected) {
        Set<Integer> r = new HashSet<>();
        r.addAll(directTos.get(cur));
        for (int key = 1; key <= 9; key++) {
            if (selected[key]) {
                r.remove(key);
            }
        }
        for (int key = 1; key <= 9; key++) {
            if (selected[key] && canJumpTos[cur][key] != 0 && !selected[canJumpTos[cur][key]]) {
                r.add(canJumpTos[cur][key]);
            }
        }

        return r;
    }

    private static void recursion(int cur, boolean[] selected, int remain, int[] counts) {
        if (remain == 0) {
            counts[0]++;
            return;
        }
        Set<Integer> options = nexts(cur, selected);
        for (int key : options) {
            selected[key] = true;
            recursion(key, selected, remain - 1, counts);
            selected[key] = false;
        }
    }

    private static int waysOf(int keys) {
        int[] sum = new int[1];
        boolean[] selected = new boolean[10];

        selected[1] = true;
        recursion(1, selected, keys - 1, sum);
        selected[1] = false;

        selected[2] = true;
        recursion(2, selected, keys - 1, sum);
        selected[2] = false;

        sum[0] *= 4;

        selected[5] = true;
        recursion(5, selected, keys - 1, sum);
        selected[5] = false;

        return sum[0];
    }

    public static int numberOfPatterns(int m, int n) {
        int r = 0;
        for (int i = m; i <= n; i++) {
            r += waysOf(i);
        }
        return r;
    }
}
