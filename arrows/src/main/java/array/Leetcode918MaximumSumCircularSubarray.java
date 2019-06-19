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

package array;

/**
 * <pre>
 * <a href='https://leetcode.com/problems/maximum-sum-circular-subarray/'>leetcode 918 Maximum Sum
 * Circular Subarray</a>
 *
 *  in the circle,
 *  - the min can be a part of max when A[i] > 0
 *  - the max can be a part of min when A[i] < 0
 *  - Assume it is clear to know the both borders of the max related sub array,
 *    then the other elements of A will compose a sub-array with sum <= 0, in it if there is
 *    a x sub-array with sum > 0 then the sum < max and either sub-array between x and max
 *    with sum < 0 and |sum| >= x.
 */
public class Leetcode918MaximumSumCircularSubarray {
    public static int maxSubarraySumCircular(int[] A) {
        if (A == null || A.length == 0) return 0;

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        int maxi, mini, total; // max or min array sum in the scope A[0]~ A[i]
        maxi = mini = total = A[0];
        for (int i = 1; i < A.length; i++) {
            maxi = Math.max(maxi + A[i], A[i]);
            max = Math.max(max, maxi); // note

            mini = Math.min(mini + A[i], A[i]);
            min = Math.min( min,mini);
            total += A[i];
        }

        return max < 0 ? max : Math.max(max, total - min);
    }
}
