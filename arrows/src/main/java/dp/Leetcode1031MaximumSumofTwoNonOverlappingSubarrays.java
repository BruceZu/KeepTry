//  Copyright 2021 The KeepTry Open Source Project
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
/*
Given an array A of non-negative integers,
return the maximum sum of elements in two non-overlapping (contiguous) subarrays,
which have lengths L and M.
(For clarification, the L-length subarray could occur before or after the M-length subarray.)

Formally, return the largest V for which
 V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1])
  and either:

    0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
    0 <= j < j + M - 1 < i < i + L - 1 < A.length.

 */
public class Leetcode1031MaximumSumofTwoNonOverlappingSubarrays {

  // O(N^2)
  public int maxSumTwoNoOverlap2(int[] A, int L, int M) {
    /*
    L >= 1
    M >= 1
    L + M <= A.length <= 1000
    0 <= A[i] <= 1000
    */
    // ’int[] A, i L, int M‘ means they are integer.
    // To make calculating sub-array sum easy to prepare presum in O(N)
    int N = A.length;
    int[] s = new int[N];
    s[0] = A[0];
    for (int i = 1; i < N; i++) {
      s[i] += s[i - 1] + A[i];
    }

    int dp[] = new int[N];
    dp[L + M - 1] = s[L + M - 1];

    // now each A[i], i in range L+M, N-1], will introduce new
    // L and M sub-array, or M and L length sub-array and related new sum.
    // - one new L sub-array which need combine with all old M and one new M sub-array to form new
    // sum
    // - one new M sub-array which need combine with all old L and one new L sub-array to
    // form new sum
    //
    int max = 0;
    for (int i = L + M; i < N; i++) {
      // order M L
      int newL = s[i] - s[i - L];
      for (int m = M - 1; m <= i - L; m++) {
        int eachM = m == M - 1 ? s[M - 1] : s[m] - s[m - M];
        max = Math.max(max, eachM + newL);
      }

      // order L M
      int newM = s[i] - s[i - M];
      for (int l = L - 1; l <= i - M; l++) {
        int eachL = l == L - 1 ? s[L - 1] : s[l] - s[l - L];
        max = Math.max(max, eachL + newM);
      }
      dp[i] = Math.max(max, dp[i - 1]);
    }
    return dp[N - 1];
  }

  // improve O(N) by:
  // When order is L M: keep current the max sum of the L length sub array
  // When order is M L: keep current the max sum of the M length sub array
  // Thus the inner loop is saved
  public int maxSumTwoNoOverlap(int[] A, int L, int M) {
    int N = A.length;
    int[] s = new int[N];
    s[0] = A[0];
    for (int i = 1; i < N; i++) {
      s[i] += s[i - 1] + A[i];
    }

    int result = s[L + M - 1];
    int Lmax = s[L - 1], Mmax = s[M - 1];
    for (int i = L + M; i < N; i++) {
      // order M L
      int newL = s[i] - s[i - L];
      Mmax = Math.max(s[i - L] - s[i - L - M], Mmax);
      // order L M
      int newM = s[i] - s[i - M];
      Lmax = Math.max(s[i - M] - s[i - M - L], Lmax);

      result = Math.max(result, Mmax + newL);
      result = Math.max(result, Lmax + newM);
    }
    return result;
  }
}
