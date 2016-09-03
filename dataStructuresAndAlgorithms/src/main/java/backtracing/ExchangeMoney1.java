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
 * Thanks a lot to Wen, Jie.
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
                                                ArrayList<Integer> smallestResolution,
                                                int left, int from) {
        if (left == 0) {
            if (smallestResolution.size() == 0 || smallestResolution.size() > curResolution.size()) {
                smallestResolution = new ArrayList(curResolution);
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

    private int[] conins;
    private int solutonsNum;


    public ExchangeMoney1(int[] currencys) {
        this.conins = currencys;
    }

    private void selectWithDetail(int remain, int left, List solution) {
        if (left == 0) {
            solutonsNum++;
            System.out.println(solution.toString());
            return;
        }
        for (int i = remain; i < conins.length; i++) {
            if (left < conins[i]) {
                return;
            }
            solution.add(conins[i]);
            selectWithDetail(i, left - conins[i], solution);
            solution.remove(solution.size() - 1);
        }
    }

    private void select(int from, int left) {
        if (left == 0) {
            solutonsNum++;
            return;
        }

        for (int i = from; i < conins.length; i++) {
            if (left < conins[i]) {
                return;
            }
            select(i, left - conins[i]);
        }
    }

    public void changeWays(int target, boolean withDetail) {
        Arrays.sort(conins); // ascending order
        solutonsNum = 0;

        if (withDetail) {
            List<Integer> solution = new LinkedList<>();
            selectWithDetail(0, target, solution);

        } else {
            select(0, target);
        }

        System.out.println("in total:" + solutonsNum);
    }

    /**
     * <pre>
     * [1, 1, 1, 1, 1, 1, 1]
     * [1, 1, 1, 1, 1, 2]
     * [1, 1, 1, 2, 2]
     * [1, 1, 5]
     * [1, 2, 2, 2]
     * [2, 5]
     */
    public static void main(String[] args) {
        int[] conins = new int[]{1, 5, 10, 25};
        int target = 67;

        ExchangeMoney1 cm = new ExchangeMoney1(conins);

        cm.changeWays(target, true);
        cm.changeWays(target, false);

        // smallest resolution
        Arrays.sort(conins);
        ArrayList<Integer> smallestResolution = new ArrayList();
        selectSmallestResolution(conins, //ascending
                new ArrayList<>(), smallestResolution,
                target, 0);
        System.out.println("smallest resolution: " + Arrays.toString(smallestResolution.toArray()));
    }
}
