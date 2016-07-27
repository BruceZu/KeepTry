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

package array.binarysearch;

/**
 * <pre>
 * Same like {@link Leetcode4MedianofTwoSortedArrays2} but without corner case check
 *   Ki is median or right median
 *   match:
 *            A[i-1]    A[i]    2 4
 *       B[i-1]      B[i]      1 3
 *       or
 *           A[i-1]    A[i]       1 4
 *              B[i-1]   B[i]      2 3
 *   else if: binary search left half
 *
 *                    | A[i-1]  A[i]
 *       B[i-1]  B[i] |
 *
 *       else: binary search right half
 *
 *            A[i-1]  A[i] |
 *                         | B[i-1]  B[i]
 */
public class Leetcode4MedianofTwoSortedArrays2_2 {
    public static  double findMedianSortedArrays(int[] A, int[] B) {
        int Ki = (A.length + B.length) / 2;
        int Ai = 0, Bi = Ki;
        int aFrom = Math.max(-1, Ki - B.length), aTo = Math.min(Ki, A.length);

        while (true) {
            Ai = (aTo + aFrom) / 2;
            Bi = Ki - Ai;

            if (v(B, Bi - 1) <= v(A, Ai)) {
                if (v(A, Ai - 1) <= v(B, Bi)) {
                    break;
                } else {
                    aTo = Ai - 1;
                }
            } else {
                aFrom = Ai + 1;
            }
        }

        if ((A.length + B.length & 1) == 1) {
            return Math.min(v(A, Ai), v(B, Bi));
        }
        return (double) (Math.min(v(A, Ai), v(B, Bi)) + Math.max(v(A, Ai - 1), v(B, Bi - 1))) / 2;
    }

    private static  int v(int[] nums, int i) {
        if (i <= -1) {
            return Integer.MIN_VALUE;
        }
        if (i >= nums.length) {
            return Integer.MAX_VALUE;
        }
        return nums[i];
    }
}
