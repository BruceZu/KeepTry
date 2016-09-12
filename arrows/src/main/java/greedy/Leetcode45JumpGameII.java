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

package greedy;

/**
 * <pre>  45. Jump Game II
 * Difficulty: Hard
 *
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Your goal is to reach the last index in the minimum number of jumps.
 *
 * For example:
 * Given array A = [2,3,1,1,4]
 *
 * The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
 *
 * Note:
 * You can assume that you can always reach the last index.
 *
 *
 *  Tags Array Greedy
 *
 *  ================================================================
 *   idea BFS
 *
 *   make it simple
 *   current jumped scope left index = last jumped scope right index +1
 *
 *  if A[0]=3, A[2]=3;
 *
 *
 *  array A[0]  A[1]  A[2]  A[3]  A[4]
 *  index   0     1     2     3     4
 *         []   [              ]
 *  jumps  0          1
 *
 *  DP idea see {@link Leetcode45JumpGameII2}
 */
public class Leetcode45JumpGameII {
    public static int jump(int[] A) {
        if (A.length <= 1) {
            return 0;
        }

        int jumps = 1;
        int curJpReachL = 1, curJpReachR = A[0]; // first jump
        if (curJpReachR >= A.length - 1) {
            return 1;
        }

        int index, next;
        while (true) {
            next = 0;
            index = curJpReachL;
            while (index <= curJpReachR) {
                next = Math.max(next, index + A[index]);
                if (next >= A.length - 1) {
                    return jumps + 1;
                }
                index++;
            }
            jumps++;
            curJpReachL = curJpReachR + 1;
            curJpReachR = next;
        }
    }

    // simple
    public static int jump2(int[] A) {
        int jumps = 0;
        int right = 0;
        int nextRight = 0;

        for (int i = 0; i <= A.length - 1; i++) {
            if (i > right) {
                jumps++;
                right = nextRight;
            }
            nextRight = nextRight > i + A[i] ? nextRight : i + A[i];
            //if (nextRight >= A.length - 1) return A.length == 1 ? 0 : jumps + 1;   // just for performance
        }
        return jumps;
    }
}