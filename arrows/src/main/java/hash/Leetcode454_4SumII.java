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

package hash;

import java.util.HashMap;
import java.util.Map;

/*
Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l)
there are such that A[i] + B[j] + C[k] + D[l] is zero.

All A, B, C, D have same length of N, 0 ≤ N ≤ 500.
All integers are in the range of -2^28 to 2^28 - 1
the result is guaranteed to be at most 2^31 - 1.
 */
public class Leetcode454_4SumII {
  // O(N^2) time and space
  public int fourSumCount2(int[] A, int[] B, int[] C, int[] D) {
    // 'All integers are in the range of -2^28 to 2^28 - 1'
    // So sum of 4*i is still be use presented with integer
    int N = A.length;
    // use hash map to keep sum of 2 number and its counts
    // the value of sum can be repeated.
    // A and B
    Map<Integer, Integer> up = new HashMap<Integer, Integer>();
    // C and D
    Map<Integer, Integer> down = new HashMap<Integer, Integer>();

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        up.put(A[i] + B[j], up.getOrDefault(A[i] + B[j], 0) + 1);
        down.put(C[i] + D[j], down.getOrDefault(C[i] + D[j], 0) + 1);
      }
    }

    int r = 0; // result

    for (HashMap.Entry<Integer, Integer> e : up.entrySet()) {
      if (down.containsKey(-e.getKey())) r += e.getValue() * down.get(-e.getKey());
    }
    return r;
  }

  // save one variable
  // O(N^2) time and space
  public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    int N = A.length;
    Map<Integer, Integer> up = new HashMap<Integer, Integer>();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        up.put(A[i] + B[j], up.getOrDefault(A[i] + B[j], 0) + 1);
      }
    }
    int r = 0; // result
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        r += up.getOrDefault(-(C[i] + D[j]), 0);
        //        if (up.containsKey(-(C[i] + D[j]))) {
        //          r += up.get(-(C[i] + D[j]));
        //        }
      }
    }
    return r;
  }
}
