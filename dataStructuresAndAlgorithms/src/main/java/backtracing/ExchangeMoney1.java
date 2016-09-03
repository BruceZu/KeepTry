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

package backtracing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Thanks a lot to Wen, Jie and 阿杰
 * <pre>
 * Note:
 *
 *       1. Sort in ascending order before search.
 *       2. Next loop will start from current index. avoid repeated solution
 *       3. When backtracking happen, solution need restore to original status.
 *
 * To make the loop easy, keep the currency types and solutions number in global variable
 *
 *    coins:    1    5   10   25   100 // ascending order
 */
public class ExchangeMoney1 {
    public static void selectSmallestResolution(int[] sortedCoins, //ascending
                                                ArrayList<Integer> curResolution,
                                                ArrayList<Integer>[] smallestResolution,
                                                int left, int from) {
        if (left == 0) {
            if (smallestResolution[0] == null || smallestResolution[0].size() > curResolution.size()) {
                smallestResolution[0] = new ArrayList(curResolution);
            }
            return;
        }
        for (int i = from; i < sortedCoins.length; i++) {
            if (left < sortedCoins[i]) {
                break;
            }
            curResolution.add(sortedCoins[i]);
            selectSmallestResolution(sortedCoins, curResolution, smallestResolution, left - sortedCoins[i], i);
            curResolution.remove(curResolution.size() - 1);
        }
    }

    private static void selectAndPrintEachSolution(int[] coins, int remain, int left, int[] solutionsNum, List solution) {
        if (left == 0) {
            solutionsNum[0]++;
            System.out.println(solution.toString());
            return;
        }
        for (int i = remain; i < coins.length; i++) {
            if (left < coins[i]) {
                return;
            }
            solution.add(coins[i]);
            selectAndPrintEachSolution(coins, i, left - coins[i], solutionsNum, solution);
            solution.remove(solution.size() - 1);
        }
    }

    private static void select(int[] conins, int from, int left, int[] solutonsNum) {
        if (left == 0) {
            solutonsNum[0]++;
            return;
        }

        for (int i = from; i < conins.length; i++) {
            if (left < conins[i]) {
                return;
            }
            select(conins, i, left - conins[i], solutonsNum);
        }
    }

    public static int changeWaysNumber(int[] coins, int target, boolean printEachSolution) {
        Arrays.sort(coins); // ascending order
        int[] solutonsNum = new int[1];

        if (printEachSolution) {
            List<Integer> solution = new LinkedList<>();
            selectAndPrintEachSolution(coins, 0, target, solutonsNum, solution);

        } else {
            select(coins, 0, target, solutonsNum);
        }
        return solutonsNum[0];
    }

    public static void exchange(int[] coins, int target) {
        // changeWaysNumber(target, true);
        System.out.println("number of solutions: " + changeWaysNumber(coins, target, false));

        // smallest resolution
        Arrays.sort(coins);
        ArrayList<Integer>[] smallestResolution = new ArrayList[1];

        selectSmallestResolution(coins, //ascending
                new ArrayList<>(), smallestResolution,
                target, 0);
        System.out.println("smallest resolution: " + Arrays.toString(smallestResolution[0].toArray()));
    }

    public static void main(String[] args) {
        int[] conins = new int[]{1, 5, 10, 25};
        int target = 67;
        exchange(conins, target);

        conins = new int[]{3, 10, 50};
        target = 62;
        exchange(conins, target);

        conins = new int[]{10, 5, 4, 1};
        target = 18;
        exchange(conins, target);
    }
}
