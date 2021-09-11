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

package array;

public class HackerrankLexicographicPaths {
  /*
    Lexicographic paths
       https://www.hackerrank.com/challenges/lexicographic-steps/problem

      go to the point (x,y) from (0, 0) in the same plane using only
      horizontal and vertical moves of unit.
      use 'H','V' represent the path, 'H': move right, V': move down.
      All path in lexicographically increasing order
      print out the kth path, k is 0-based


     Constraints
        1< x,y < 10
        0 <= k < total number of paths
  */
  /*
   Idea:
      use a 2-d array `p` to keep the sum of paths from cell(j,i) to (x,y)
      any cell(j,i) has 2 options to go: right side and downside, so for
      valid cells there is relation:
       p[i][j] = p[i + 1][j] +  p[i][j + 1];
      with the array `p`, go from the (0,0) to locate the kth path, in
      lexicographically increasing order, in 2 options going right or down
      and record the path.
      continue this process till reach (x,y). The idea is same as that
      in segment tree.

  Code Details：
       x,y is column and row index
       p[i][j] keep sum of paths to (x,y) from (j,i)
       need converted 0-based kth path to 1-based
       lexicographically increasing order means going firstly to the right has higher priority
       than going down.
  O(M*N) time and space, M is y+1 row number, N is x+1, column number.
    */
  public static String solve(int x, int y, int k) {
    int R = y + 1, C = x + 1; // x,y is column and row index,
    int p[][] = new int[R][C]; // p[i][j] keep sum of paths to (x,y) from (j,i)
    p[R - 1][C - 1] = 1;
    for (int i = R - 1; i >= 0; i--) {
      for (int j = C - 1; j >= 0; j--) {
        if (i == R - 1 && j == C - 1) continue;
        int r = (i + 1) == R ? 0 : p[i + 1][j];
        int d = (j + 1) == C ? 0 : p[i][j + 1];
        p[i][j] = r + d;
      }
    }
    StringBuilder a = new StringBuilder();
    int i = 0, j = 0;
    k++;
    while (true) {
      int right = (j + 1) == C ? 0 : p[i][j + 1];
      if (k <= right) {
        a.append("H");
        j++;
      } else {
        a.append("V");
        i++;
        k = k - right;
      }
      if (i == y && j == x) break;
    }
    return a.toString();
  }
}
