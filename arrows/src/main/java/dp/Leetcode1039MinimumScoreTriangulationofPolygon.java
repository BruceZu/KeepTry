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

import string.MaxMatchNumberOfPatterns;

import java.util.Arrays;

public class Leetcode1039MinimumScoreTriangulationofPolygon {
  public static int minScoreTriangulation(int[] values) {
    /*
    n == values.length
    3 <= n <= 50
    1 <= values[i] <= 100
    values[i] is the value of the ith vertex (i.e., clockwise order).
    */
    /*
    Idea:
    For a given convex n-sided polygon and one of its cutting solution.
    A vertex can belong to one triangle or more triangles.
    While an edge belongs to only one triangle for a given cutting solution.

    In a given convex n-sided polygon with edges n > 3.
     <1> Select an edge, it can be in n-2 triangles, and each triangle can cut the polygon into 2 or 3 parts including itself. Each part with edges m > 3
         can going on along this way, till all cut parts are a triangle.
     <2> In step <1>, for a given convex n-sided polygon, there are n options to select
         the edge with its triangle to cut the polygon. The last possible cut resolutions
         are same.
         But the benefit of selecting the edge represented by the start vertex and end vertex
         in the given convex n-sided polygon represented by vertex value: int[] values,
         values[i] is the value of the ith vertex (i.e., clockwise order), is each cut part(s),
         excepting the triangle used to cut, still can be represented by a subarray of
         the int[] values.
     Then the status translation equation comes from here
       dp[i][j] is the smallest possible total score can achieved
                with some triangulation of the convex n-sided polygon represented by vertex
                value: int[] values, values[i] is the value of the ith vertex
                (i.e., clockwise order).
       dp[i][j] = min{values[i]*value[j]*value[x]+ dp[i][x]+dp[x][j]}
                  x is the index, i< x < j, 0<= x + 2 <= j <= n-1

       Initial value of dp[i][j]:
         according to the equation
         dp[i][j] = 0 when j-i < 2. default value.
         dp[i][j] = MAX_VALUE when j-i >= 2.
     */
    int N = values.length;
    int[][] dp = new int[N][N];
    // O(N^3) time, O(N^2) space
    for (int p = 3; p <= N; p++) {
      for (int s = 0; s + p - 1 < N; s++) {
        int e = s + p - 1;
        dp[s][e] = Integer.MAX_VALUE;
        for (int m = s + 1; m < e; m++) {
          int tri = values[s] * values[e] * values[m];
          dp[s][e] = Math.min(dp[s][e], dp[s][m] + tri + dp[m][e]);
        }
      }
    }
    return dp[0][N - 1];
  }

  public static void main(String[] args) {
    System.out.println(minScoreTriangulation(new int[] {1, 2, 3}) == 6);
  }
}
