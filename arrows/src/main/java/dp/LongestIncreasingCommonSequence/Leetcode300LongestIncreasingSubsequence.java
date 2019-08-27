//  Copyright 2018 The KeepTry Open Source Project
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

package dp.LongestIncreasingCommonSequence;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/longest-increasing-subsequence/description/">leetcode</a>
 */
public class Leetcode300LongestIncreasingSubsequence {

    /**
     * Print one LIS in O(nlogn)
     *
     * @param A
     * @return
     */
    static void printOneLIS(int A[]) {
        if (A == null || A.length == 0) {
            System.out.println("");
            return;
        }

        int[] M = new int[A.length];
        // The M[j] is the index of element in A, the element A[M[j]] is the end element
        // of IS with length j. During the process may there are many IS with the length of j.
        // the later produced one always has a smaller end element than before.
        int L; // length of the longest IS.

        int[] P = new int[A.length];
        // Using the P to create the pre relation of elements of array A via their index for
        // tracking the history value of M[j-1] when the A[M[j]] is just added to position j as the
        // end of a IS. At that moment the element A[P[M[j]]] is just in the front of element
        // A[M[j]] in the IS, and  P[M[j]] == M[j-1]. but the equation may be changed soon if the IS
        // with length of j-1  get an small end element in he following loop steps
        //
        // the P[0] has not pre element. let it is -1, which will never be used, just for debug

        M[0] = 0;
        L = 1;
        P[0] = -1;

        if (A.length == 1) {
            System.out.println(Arrays.toString(A));
            return;
        }
        for (int i = 1; i < A.length; i++) { // O(n)
            // if (A[i - 1] == A[i]) continue; // continued duplicated number, performance.
            if (A[i] < A[M[0]]) { // new smallest value.
                M[0] = i;
            } else if (A[i] > A[M[L - 1]]) { // new one.
                M[L] = i;
                P[i] = M[L - 1];
                L++;

            } else { // find and replace. A[M[0]] < = A[i] < = A[M[L-1]]
                int insertJ = ceilIndexJ(A, M, A[i], 0, L - 1);

                // if (A[M[insertJ]] == A[i]) {
                //     continue; // make sure no duplicated numbers in increasing
                // sequence
                // }
                M[insertJ] = i;
                if (insertJ == 0) {
                    P[i] = -1;
                } else {
                    P[i] = M[insertJ - 1];
                }
            }
        }

        int[] result = new int[L];

        int indexA = M[L - 1]; // from M
        result[L - 1] = A[indexA];

        for (int i = 2; i <= L; i++) {
            indexA = P[indexA]; // loop with P
            result[L - i] = A[indexA];
        }
        System.out.println(Arrays.toString(result));
    }

    /**
     * <pre>
     *
     * Returns the index of the least element in this Array greater than or equal to the given element, or
     * -1 if there is no such element.
     *
     * @param M Assume the Array: A[M[i]], i from l to r, is sorted array.
     * @param t target value
     * @param l index scope's left index
     * @param r
     *
     */
    public static int ceilIndexJ(int[] A, int[] M, int t, int l, int r) {
        if (M == null || M.length == 0 || l < 0 || r > M.length - 1) {
           throw new RuntimeException("invalid Array or index bound");
        }
        // now the Array may have 1,2,3 or more elements
        if (t <= A[M[l]]) {
            return 0;
        }

        if (t > A[M[r]]) {
            return -1;
        }
        // now t is in the (l, r], and Array may have 2, 3 or more elements.
        // if it is 2 element, then while loop will be skipped
        int m;

        while (r - l > 1) {
            m = (r + l) / 2;
            int mv = A[M[m]];

            if (mv < t) {
                l = m;
            } else if (t < mv) {
                r = m;
            } else {
                return m;
            }
        }
        // not found or while loop is skipped;
        // only current l and r is left;
        // v maybe be equal to the A[M[r]]
        return r;
    }

    // -----------------------------------------------------
    /**
     * <pre>
     *
     * Returns the index of the least element in this Array greater than or equal to the given element, or
     * -1 if there is no such element.
     *
     * @param A Assume the A is sorted array.
     * @param t target value
     * @param l index scope's left index
     * @param r
     *
     */
    public static int ceilIndex(int[] A, int t, int l, int r) {
        if (A == null || A.length == 0 || l < 0 || r > A.length - 1) {
            throw new RuntimeException("invalid Array or index bound");
        }
        // now the A may have 1,2,3 or more elements
        if (t <= A[l]) {
            return 0;
        }

        if (t > A[r]) {
            return -1;
        }
        // now t is in the (l, r], and A may have 2, 3 or more elements.
        // if it is 2 element, then while loop will be skipped
        int m;

        while (r - l > 1) {
            m = (r + l) / 2;
            int mv = A[m];

            if (mv < t) {
                l = m;
            } else if (t < mv) {
                r = m;
            } else {
                return m;
            }
        }
        // not found or while loop is skipped;
        // only current l and r is left;
        // v maybe be equal to the A[r]
        return r;
    }

    /**
     * return the length of the LIS in O(nlogn)
     *
     * @param A
     * @return
     */
    static int lengthOfLIS(int A[]) {
        if (A == null || A.length == 0) return 0;
        int[] tails = new int[A.length];
        int size;

        tails[0] = A[0];
        size = 1;
        for (int i = 1; i < A.length; i++) { // O(n)
            // if (A[i - 1] == A[i]) continue; // continued duplicated number, performance
            if (A[i] < tails[0]) { // new smallest value.
                tails[0] = A[i];
            } else if (A[i] > tails[size - 1]) { // new one.
                tails[size++] = A[i];
            } else { // find and replace.
                int ceilIndex = ceilIndex(tails, A[i], 0, size - 1);

                // if (tails[ceilIndex] == A[i]) {
                // continue; // make sure no duplicated numbers in increasing sequence
                // }
                tails[ceilIndex] = A[i];
            }
        }
        return size;
    }

    /**
     * @param A
     * @return return the length of the LCS in O(n^2)
     *     <pre>Other idea of DP is: sort the given array, O(nlogn)
     *     and trim duplicated members, O(n), get a new array with length m
     *     then using DP solution for LCS, O(n*m)
     */
    public static int DP(int[] A) {
        if (A == null || A.length == 0) return 0;
        int[] LISLength = new int[A.length];
        // length of the LIS(longest increasing sequence)
        // which ending at element with index i
        LISLength[0] = 1;
        int max = 1;

        for (int i = 1; i < A.length; i++) {
            int l = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (A[j] < A[i] /*increasing*/) {
                    l = Math.max(l, LISLength[j] + 1);
                }
            }
            LISLength[i] = l;
            max = Math.max(max, l);
        }
        return max;
    }

    // Runtime O(n^2)
    public static void DPWithPrintOneOfResult(int[] A) {
        if (A == null || A.length == 0) return;
        int[] LISLength = new int[A.length];
        LISLength[0] = 1;
        int max = 1; // it is 1 not MIN_VALUE
        int maxEndIndex = 0; // using 0 as initial value

        int[] preIndex = new int[A.length];

        for (int i = 1; i < A.length; i++) {
            int l = 1; // initial value of DP[i]
            for (int j = i - 1; j >= 0; j--) {
                if (A[j] < A[i] && LISLength[j] + 1 > l) {
                    l = LISLength[j] + 1;
                    preIndex[i] = j;
                }
            }
            LISLength[i] = l;
            if (max < l) {
                max = l;
                maxEndIndex = i;
            }
        }
        // print out one
        int[] LIS = new int[max];
        LIS[max - 1] = A[maxEndIndex];
        max--;
        int pre = preIndex[maxEndIndex];
        while (max-- > 0) {
            LIS[max] = A[pre];
            pre = preIndex[pre];
        }
        System.out.println(Arrays.toString(LIS));
    }

    // -----------------------------------------------
    public static void main(String[] args) {
        System.out.println(lengthOfLIS(null) == 0);
        System.out.println(lengthOfLIS(new int[] {}) == 0);
        System.out.println(lengthOfLIS(new int[] {1}) == 1);
        System.out.println(lengthOfLIS(new int[] {1, 2}) == 2);
        System.out.println(lengthOfLIS(new int[] {2, 1}) == 1);
        System.out.println(lengthOfLIS(new int[] {2, 2}) == 1);
        System.out.println(lengthOfLIS(new int[] {2, 2, 2, 2, 3}) == 2);
        System.out.println(lengthOfLIS(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6}) == 6);
        System.out.println(lengthOfLIS(new int[] {10, 9, 2, 5, 3, 7, 101, 18}) == 4);
        System.out.println(lengthOfLIS(new int[] {4, 10, 4, 3, 8, 9}) == 3);
        System.out.println(lengthOfLIS(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12}) == 6);
        // DP
        System.out.println("DP====");
        System.out.println(DP(null) == 0);
        System.out.println(DP(new int[] {}) == 0);
        System.out.println(DP(new int[] {1}) == 1);
        System.out.println(DP(new int[] {1, 2}) == 2);
        System.out.println(DP(new int[] {2, 1}) == 1);
        System.out.println(DP(new int[] {2, 2}) == 1);
        System.out.println(DP(new int[] {2, 2, 2, 2, 3}) == 2);
        System.out.println(DP(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6}) == 6);
        System.out.println(DP(new int[] {10, 9, 2, 5, 3, 7, 101, 18}) == 4);
        System.out.println(DP(new int[] {4, 10, 4, 3, 8, 9}) == 3);
        System.out.println(DP(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12}) == 6);

        // DPWithPrintOneOfResult
        System.out.println("DPWithPrintOneOfResult====");
        DPWithPrintOneOfResult(null);
        DPWithPrintOneOfResult(new int[] {});
        DPWithPrintOneOfResult(new int[] {1});
        DPWithPrintOneOfResult(new int[] {1, 2});
        DPWithPrintOneOfResult(new int[] {2, 1});
        DPWithPrintOneOfResult(new int[] {2, 2});
        DPWithPrintOneOfResult(new int[] {2, 2, 2, 2, 3});
        DPWithPrintOneOfResult(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6});
        DPWithPrintOneOfResult(new int[] {10, 9, 2, 5, 3, 7, 101, 18});
        DPWithPrintOneOfResult(new int[] {4, 10, 4, 3, 8, 9});
        DPWithPrintOneOfResult(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12});
        DPWithPrintOneOfResult(new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});

        System.out.println("printOneLIS====");
        printOneLIS(null);
        printOneLIS(new int[] {});
        printOneLIS(new int[] {1});
        printOneLIS(new int[] {1, 2});
        printOneLIS(new int[] {2, 1});
        printOneLIS(new int[] {2, 2});
        printOneLIS(new int[] {2, 2, 2, 2, 3});
        printOneLIS(new int[] {2, 0, 5, 3, 7, 11, 8, 10, 13, 6});
        printOneLIS(new int[] {10, 9, 2, 5, 3, 7, 101, 18});
        printOneLIS(new int[] {4, 10, 4, 3, 8, 9});
        printOneLIS(new int[] {3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12});
        printOneLIS(new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});
    }
}
