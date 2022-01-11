//  Copyright 2022 The KeepTry Open Source Project
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

package intervals;

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
 *   current jumped scope left index = last jumped scope right index +1
 *
 *  DP idea see {@link Leetcode45JumpGameII2}
 */
public class Leetcode45JumpGameII {
    // assume that you can always reach the last index.
    public static int jump(int[] A) {
        if (A.length <= 1) {
            return 0;
        }

        int jumps = 1;
        int l = 1, r = A[0]; //BFS: pre all possible jump reached 'NEW' scope, 'NEW' means out of the left old scope.
        if (r >= A.length - 1) {
            return 1;
        }

        while (true) {
            int nextR = -1;
            for (int cur = l; cur <= r; cur++) {
                nextR = Math.max(nextR, cur + A[cur]);
                if (nextR >= A.length - 1) {
                    return jumps + 1;
                }
            }
            // next
            jumps++;
            l = r + 1;
            r = nextR;
        }
    }

    // simple
    public static int jump2(int[] A) {
        int jumpTimes = 0;
        int lastOfCurJumpCandidators = 0;
        int possibleReachedTo = 0;

        for (int i = 0; i <= A.length - 1; i++) {
            if (i > lastOfCurJumpCandidators) {
                jumpTimes++;
                lastOfCurJumpCandidators = possibleReachedTo;
            }
            int reachedToFromCur = i + A[i];
            possibleReachedTo = reachedToFromCur > possibleReachedTo ? reachedToFromCur : possibleReachedTo;
        }
        return jumpTimes;
    }
}