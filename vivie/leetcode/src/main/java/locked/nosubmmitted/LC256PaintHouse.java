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

package locked.nosubmmitted;

/**
 * 256. Paint House
 * https://leetcode.com/problems/paint-house/
 * Difficulty: Medium <pre>
 * There are a row of n houses, each house can be painted with one of the
 * three colors: red, blue or green. The cost of painting each house with
 * a certain color is different. You have to paint all the houses such that
 * no two adjacent houses have the same color.
 * <p/>
 * The cost of painting each house with a certain color is represented
 * by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting
 * house 0 with color red; costs[1][2] is the cost of painting house 1 with
 * color green, and so on... Find the minimum cost to paint all houses.
 * <p/>
 * Note:
 * All costs are positive integers.
 * <p/>
 * Hide Company Tags LinkedIn
 * Hide Tags: Dynamic Programming
 * Hide Similar Problems (E) House Robber (M) House Robber II (H) Paint House II (E) Paint Fence
 */
public class LC256PaintHouse {
    /**
     * The basic idea is when we have painted the first i houses,
     * and want to paint the i+1 th house, we have 3 choices:
     * paint it either red, or green, or blue.
     * If we choose to paint it red, we have the follow deduction:
     * paintCurrentRed = min(paintPreviousGreen,paintPreviousBlue) + costs[i+1][0]
     * Same for the green and blue situation.
     * And the initialization is set to costs[0], so we get the code:
     *
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        if (costs.length == 0) return 0;
        int lastR = costs[0][0];
        int lastG = costs[0][1];
        int lastB = costs[0][2];
        for (int i = 1; i < costs.length; i++) {
            int curR = Math.min(lastG, lastB) + costs[i][0];
            int curG = Math.min(lastR, lastB) + costs[i][1];
            int curB = Math.min(lastR, lastG) + costs[i][2];
            lastR = curR;
            lastG = curG;
            lastB = curB;
        }
        return Math.min(Math.min(lastR, lastG), lastB);
    }

    /**
     * other idea , the code is not fast
     * <p/>
     * The 1st row is the prices for the 1st house,
     * we can change the matrix to present sum of prices from the 2nd row.
     * i.e, the costs[1][0] represent minimum price to paint the second house red plus the 1st house.
     */
    public int minCost2(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        for (int i = 1; i < costs.length; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][1], costs[i - 1][0]);
        }
        int n = costs.length - 1;
        return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
    }
}
