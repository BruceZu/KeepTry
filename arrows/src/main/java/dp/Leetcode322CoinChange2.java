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

public class Leetcode322CoinChange2 {
    // ---------------------------------------------------------------------------
    public static int coinChange(int[] coins, int amount) {
        return leftCoinsNumSumFor(0, coins, amount);
    }

    // Space complexity : O(n) In the worst case the maximum depth of recursion is n
    private static int leftCoinsNumSumFor(int from, int[] coins, int left) {
        if (left == 0) return 0; // valid left firstly then valid from?
        if (from == coins.length) return -1;

        int minSumX = Integer.MAX_VALUE;
        for (int x = 0; x <= left / coins[from]; x++) {
            int otherXSum = leftCoinsNumSumFor(from + 1, coins, left - x * coins[from]);
            if (otherXSum == -1) continue;
            minSumX = Math.min(minSumX, x + otherXSum);
        }
        return (minSumX == Integer.MAX_VALUE) ? -1 : minSumX;
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
