//  Copyright 2019 The KeepTry Open Source Project
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
import java.util.Stack;

/**
 * <pre>
 * Given an array of integers A, find the sum of min(B),
 * where B ranges over every (contiguous) subarray of A.
 *
 * Since the answer may be large, return the answer modulo 10^9 + 7.
 *
 *
 *
 * Example 1:
 *
 * Input: [3,1,2,4]
 * Output: 17
 * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
 *
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * 1 <= A[i] <= 30000
 *
 *
 *
 */
public class Leetcode907SumOfSubarrayMinimums {

    public static int sumSubarrayMins(int[] A) {
        if (A == null || A.length == 0) return 0;

        double[] dp = new double[A.length];
        // monotonously increasing index stack
        Stack<Integer> stack = new Stack<>();

        // init
        dp[0] = A[0];
        stack.push(0);

        for (int i = 1; i < A.length; i++) {
            while (!stack.empty() && A[i] <= A[stack.peek()]) {
                stack.pop();
            }

            if (stack.empty()) {
                dp[i] = A[i] * (i + 1);
            } else {
                int j = stack.peek();
                dp[i] = A[i] * (i - j) + dp[j];
            }

            // for next
            stack.push(i);
        }

        return (int) (Arrays.stream(dp).sum() % (Math.pow(10, 9) + 7));
    }

    public static void main(String[] args) {
        System.out.println(sumSubarrayMins(new int[] {3, 1, 2, 4}) == 17);
        System.out.println(sumSubarrayMins(new int[] {51, 29}) == 109);
    }
}
