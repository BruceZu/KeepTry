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

package greedy;

/**
 * <pre>
 *     <a href="https://leetcode.com/problems/gas-station/?tab=Description">Leetcode</a>
 */
public class Leetcode134GasStation {
    /**
     * If the total number of gas is bigger than the total number of cost. There must be a solution.
     * who is the start station?
     */
    static public int canCompleteCircuit2(int[] gas, int[] cost) {
        int indexOfStart = -1;
        int gasLeft = 0;
        int minGasLeft = Integer.MAX_VALUE;
        for (int i = 0; i < gas.length; i++) {
            gasLeft += gas[i] - cost[i];
            if (gasLeft < minGasLeft) {
                indexOfStart = i;
                minGasLeft = gasLeft;
            }
        }
        if (gasLeft >= 0) {
            return indexOfStart == gas.length - 1 ? 0 : indexOfStart + 1;
        } else {
            return -1;
        }
    }

    static public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0, calculatedGasInNeed = 0, ContinuedLeftGas = 0;
        for (int i = 0; i < gas.length; i++) {
            int currentLeftOrNeed = gas[i] - cost[i];
            ContinuedLeftGas += currentLeftOrNeed;
            if (ContinuedLeftGas < 0) {
                start = i + 1;
                calculatedGasInNeed += ContinuedLeftGas;
                ContinuedLeftGas = 0;
            }
        }
        return (calculatedGasInNeed + ContinuedLeftGas < 0) ? -1 : start;
    }

    public static void main(String[] args) {
        System.out.println(canCompleteCircuit(
                new int[]{5},
                new int[]{4})); // 0
        System.out.println(canCompleteCircuit(
                new int[]{1, 2, 3, 3},
                new int[]{2, 1, 5, 1})); // 3
        System.out.println(canCompleteCircuit(
                new int[]{6, 1, 4, 3, 5},
                new int[]{3, 8, 2, 4, 2})); // 2
    }
}
