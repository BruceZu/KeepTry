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

package string.palindrome;

import java.util.stream.IntStream;

public class Leetcode132PalindromePartitioningII {
  // -------------------based on DP. O(N^2) time and space-------------------
  public int minCutUnderstandVersion(String s) {
    if (s == null || s.length() <= 1) return 0;

    int N = s.length();
    // is sub-string [l,r] Palindrome?
    // calculate is[][]. O(N^2) time and space.
    boolean is[][] = new boolean[N][N]; // default false
    for (int r = 0; r < N; r++) {
      for (int l = r; 0 <= l; l--) {
        if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || is[l + 1][r - 1])) {
          is[l][r] = true;
        }
      }
    }

    // min cuts number
    int min[] = new int[N];
    // init value is length -1. or index value
    for (int r = 0; r < N; r++) {
      min[r] = r;
    }

    for (int r = 0; r < N; r++) {
      for (int l = r; 0 <= l; l--) {
        if (is[l][r]) {
          if (l == 0) {
            min[r] = 0;
          } else {
            min[r] = Math.min(min[l - 1] + 1, min[r]);
          }
        }
      }
    }
    return min[N - 1];
  }

  public int minCut(String s) {
    if (s == null || s.length() <= 1) return 0;
    // calculate is[][]. O(N^2) time and space
    int N = s.length();
    // min cuts number
    int min[] = new int[N];
    // is sub-string [l,r] Palindrome?
    boolean is[][] = new boolean[N][N]; // default false

    for (int r = 0; r < N; r++) {
      min[r] = r; // init value is length -1. or index value
      for (int l = r; 0 <= l; l--) {
        if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || is[l + 1][r - 1])) {
          is[l][r] = true;
          min[r] = l == 0 ? 0 : Math.min(min[l - 1] + 1, min[r]);
        }
      }
    }
    return min[N - 1];
  }
}
