//  Copyright 2017 The KeepTry Open Source Project
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
import java.util.List;

public class Leetcode322CoinChange1 {

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0; // just for pass test in leetcode.
        if (coins == null || coins.length == 0 || amount < 1) return -1;
        Arrays.sort(coins);
        int[] r = new int[] {Integer.MAX_VALUE};
        backtracking(coins, amount, 0, new ArrayList(coins.length), r);
        return r[0] == Integer.MAX_VALUE ? -1 : r[0];
    }

    private static void backtracking(int[] coins, int left, int from, List cur, int[] r) {
        // if (left < 0) return;
        if (left == 0) {
            // System.out.println(Arrays.toString(cur.toArray()));
            r[0] = Math.min(r[0], cur.size());
            return;
        }

        for (int i = from; i < coins.length; i++) {
            if (left < coins[i]) break; // stop continue and stop next recursion
            cur.add(coins[i]);
            backtracking(coins, left - coins[i], i, cur, r);
            cur.remove(cur.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("result: " + coinChange(new int[] {1}, 0));
        System.out.println("result: " + coinChange(new int[] {1, 2, 4, 5}, 8));
        System.out.println("result: " + coinChange(new int[] {2}, 3));
        System.out.println("result: " + coinChange(new int[] {1, 2, 5}, 11));
        System.out.println("result: " + coinChange(new int[] {1}, 2));
        System.out.println("result: " + coinChange(new int[] {470, 35, 120, 81, 121}, 9825));
    }
}
