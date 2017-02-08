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
 * <pre>
 *   DP
 *  "Time Limit Exceeded " by leetcode test data,
 *  @see <a href="http://www.jiuzhang.com/solutions/jump-game-ii/">reference jiuzhang</a>
 *
 *  @see <a href="https://discuss.leetcode.com/topic/47144/java-solution-beating-100">
 *       best performance based on leetcode test data</a>
 */
public class Leetcode45JumpGameII2 {

    public int jump(int[] A) {
        int[] minumJumpsFromStartTo = new int[A.length];
        minumJumpsFromStartTo[0] = 0;
        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                if (j + A[j] >= i) {
                    minumJumpsFromStartTo[i] = minumJumpsFromStartTo[j] + 1;
                    break;// just select the first one from left side
                }
            }
        }
        return minumJumpsFromStartTo[A.length - 1];
    }

    public static int jump2(int[] A) {
        int[] minumJumpsToEndFrom = new int[A.length];
        minumJumpsToEndFrom[A.length - 1] = 0;
        for (int i = A.length - 2; i >= 0; i--) {
            int min = minumJumpsToEndFrom[i + 1];
            for (int j = i + 1; j <= i + A[i] && j < A.length; j++) {
                min = Math.min(minumJumpsToEndFrom[j], min);
            }
            minumJumpsToEndFrom[i] = min + 1;
        }
        return minumJumpsToEndFrom[0];
    }
}
