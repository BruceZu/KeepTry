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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *      QuestionEditorial Solution
 * Difficulty: Medium
 *
 *
 * Company Tags Google
 * Tags
 *      Dynamic Programming
 *      Backtracking
 * ===================================================================================
 * ??
 *     1 union find? no, it is DFS
 *     2 for a given resolution, the  all selected key must be connected? Yes
 *     3 The order of keys used matters.
 *     4 why the condition is m ≤ n, a scope, not a number? e.g. n or m? no meaning, the later is better. maybe just for
 *     performance checking
 *     5 how to DP?
 *     //todo
 *
 *          1    2    3
 *
 *          4    5    6
 *
 *          7    8    9
 *
 *  Note:
 *    1-8, 1-6 is direct relation, just like 1-2, 1-5 and 1-4.
 *    DFS idea:
 *      how to define next steps?
 *          may run into problem of circle or circles. while from DFS perspective it looks simple.
 *          next steps also include those keys to which can jumping from current key.
 *    performance:
 *         watch symmetric the keys 1,3,7,9;  2,4,6,8.
 *
 * @see <a href="https://leetcode.com/problems/android-unlock-patterns/">leetcode</a>
 */
public class Leetcode351AndroidUnlockPatterns {
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

    private static Set<Integer> nexts(int cur, List<Integer> selected) {
        Set<Integer> r = new HashSet<>();
        r.addAll(directTos.get(cur));

        for (int key : selected) {
            if (canJumpTos[cur][key] != 0) {
                r.add(canJumpTos[cur][key]);
            }
        }
        r.removeAll(selected);
        return r;
    }

    private static void recursion(int cur, List selected, int sum, int[] counts) {
        if (selected.size() == sum) {
            counts[0]++;
            return;
        }
        Set<Integer> options = nexts(cur, selected);
        for (int key : options) {
            selected.add(key);
            recursion(key, selected, sum, counts);
            selected.remove(selected.size() - 1);
        }
    }

    private static int waysOf(int keys) {
        int[] sum = new int[1];
        List<Integer> selected = new ArrayList<>();

        selected.add(1);
        recursion(1, selected, keys, sum);
        selected.remove(selected.size() - 1);

        selected.add(2);
        recursion(2, selected, keys, sum);
        selected.remove(selected.size() - 1);

        sum[0] *= 4;

        selected.add(5);
        recursion(5, selected, keys, sum);
        selected.remove(selected.size() - 1);

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
