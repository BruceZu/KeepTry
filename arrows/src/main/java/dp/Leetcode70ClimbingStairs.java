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
 * 70. Climbing Stairs
 * Difficulty: Easy
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps.
 * In how many distinct ways can you climb to the top?
 *
 * Subscribe to see which companies asked this question
 *
 * Tags Dynamic Programming
 */
public class Leetcode70ClimbingStairs {
    /**
     * <pre>
     * draw a picture, watch and and easy to see
     * for i
     * there 2 possible way to reach
     *      from i-2: climb  2 steps directly
     *      from i-1: climb  1 step  directly
     *
     *    so :
     *       max resolutions[i] = max resolutions[i-2]+max resolutions[i-1]
     */
    public int climbStairs(int n) {
        int r1 = 1;
        int r2 = 2;
        int r3 = 0;
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        for (int i = 3; i <= n; i++) {
            r3 = r1 + r2;
            r1 = r2;
            r2 = r3;
        }
        return r3;
    }
}
