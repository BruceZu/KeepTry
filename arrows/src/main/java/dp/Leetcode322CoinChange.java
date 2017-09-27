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

import java.util.Arrays;

public class Leetcode322CoinChange {
    // Runtime complexity O(A*coins.length)
    public static int coinChange(int[] coins, int A) {
        if (A < 1) return 0;
        int[] min = new int[A + 1];
        for (int i = 1; i <= A; i++) {
            min[i] = Integer.MAX_VALUE; // from i=1, default to no resolution
        }
        boolean sort = false;
        if (Math.log(coins.length) / Math.log(2) <= A) {
            Arrays.sort(coins);
            sort = true;
        }

        // min[0] = 0;  used to return the min[left] = 0, indirectly help the min[each coin] == 1

        for (int Ai = 0; Ai < A; Ai++) {
            if (min[Ai] == Integer.MAX_VALUE) continue; // has solution. next line will add 1 to it.
            for (int coin : coins) {
                if (0 <= Ai + coin && Ai + coin <= A)
                    min[Ai + coin] = Math.min(min[Ai + coin], min[Ai] + 1);
                else if (sort) break;
            }
        }
        return min[A] == Integer.MAX_VALUE ? -1 : min[A];
    }
    //-----------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("result: " + coinChange(new int[] {1}, 0));
        System.out.println("result: " + coinChange(new int[] {1, 2, 4, 5}, 8));
        System.out.println("result: " + coinChange(new int[] {2}, 3));
        System.out.println("result: " + coinChange(new int[] {1, 2, 5}, 11));
        System.out.println("result: " + coinChange(new int[] {1}, 2));
        System.out.println("result: " + coinChange(new int[] {470, 35, 120, 81, 121}, 9825));
        System.out.println("result: " + coinChange(new int[] {1, 2147483647}, 2));
    }
}
