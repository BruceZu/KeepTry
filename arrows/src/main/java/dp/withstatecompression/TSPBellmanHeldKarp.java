//  Copyright 2020 The KeepTry Open Source Project
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

package dp.withstatecompression;

import java.util.Arrays;

/**
 * refer paper <<Travelling Salesman Problem and Bellman-Held-Karp Algorithm Quang Nhat Nguyen May
 * 10, 2020>> http://www.math.nagoya-u.ac.jp/~richard/teaching/s2020/Quang1.pdf
 */
public class TSPBellmanHeldKarp {
  /*
   Input: double[][] D. the smallest distance from any city to other cities
         D[i][j] is the smallest distance from city 'from' to city 'to'
         if D[i][j] is Double.MAX_VALUE, means  no way between city i to city j

   city number: N = D.length.
   city is tagged with number: from 0 to N-1
        possible number of compression status, sub set of cities, in total 2^N status: 0～2^N -1

   dp[s][e]
    The minimum distance from start city 0, via each city in s and end at city e.
    dp[s][e]  is distance value, not cycle value.
    E.g.:
          dp[1][0] is 0; dp[x>1][0] is MAX_VALUE
          ...
          dp[1101][0] is MAX_VALUE
          dp[1101][2] is city 0 -> vis city 3-> reach end city 2
          dp[1101][3] is city 0 -> vis city 2-> reach end city 3
                      = min{dp[101][0]+D[0][3],
                            dp[101][2]+D[2][3]}

          dp[101101][2] is city 0 -> vis city 3,5-> reach end city 2
          dp[101101][3] is city 0 -> vis city 2,5-> reach end city 3
          dp[101101][5] is city 0 -> vis city 3,2-> reach end city 5

          dp[101101][5] = min{dp[1101][0]+D[0][5],
                              dp[1101][2]+D[2][5],
                              dp[1101][3]+D[3][5]};

    s: compression status,
    always include city 0,when there is only one city, it is the start city 0, s is 2^0=1.
    so valid s is always an odd number

    when s={city 0} dp[1][0]=0; other dp[s][end city] initial value is MAX_VALUE

    End city:
     when s has more cities than city 0, city 0 will not be the candidate of end city
    Start city:
     Any city can be start city, but it does not change the result. So let city 0 as the
     start city.

  O(2^n*n^2) time

     c: all possible end city, and not in s
     `(s & (1 << c)) != 0` means city `c` is in compress status `s`
     sc: city is in s and is the end city
           when sc is 0, dp[s>1][0] is MAX_VALUE;  dp[1][0] is 0,
           sc is end city make sense only
           when s==1 or s include only the start city 0
  */
  public static double solution(double[][] D) {
    if (D == null || D.length == 1) return 0;
    int N = D.length, M = (1 << N);

    double[][] dp = new double[M][N];
    for (int i = 0; i < M; i++) Arrays.fill(dp[i], Double.MAX_VALUE);
    dp[1][0] = 0; // (int) Math.pow(2, 0), city 0

    // bottom-up
    for (int s = 1; s < M; s++) { // O(2^n)
      if ((s & 1) != 1) continue;

      // O(n^2)
      for (int c = 1; c < N; c++) {
        if ((s & (1 << c)) != 0) continue;

        for (int sc = 0; sc < N; sc++) { // dp[s][0] ==MAX_VALUE
          if ((s & (1 << sc)) != 0) {
            int ns = s | (1 << c);
            dp[ns][c] = Math.min(dp[ns][c], dp[s][sc] + D[sc][c]);
          }
        }
      }
    }

    double r = Double.MAX_VALUE;
    // add distance from end city i to start city 0, become cycle
    for (int e = 1; e < N; e++) r = Math.min(dp[M - 1][e] + D[e][0], r);
    return r;
  }

  // --------------------------------------------------------------------------------
  public static void main(String[] args) {
    // city 0 -> city 1 -> city 2 -> city 3 -> city 0
    //        3         2         2        3
    double[][] dis =
        new double[][] {
          {0, 3, 6, 7},
          {5, 0, 2, 3},
          {6, 4, 0, 2},
          {3, 7, 5, 0},
        };
    System.out.println(solution(dis) == 10);

    dis =
        new double[][] {
          {0, 5, 10, 16, 11},
          {9, 0, 7, 12, Double.MAX_VALUE},
          {12, 5, 0, 15, Double.MAX_VALUE},
          {15, 14, 9, 0, 9},
          {9, Double.MAX_VALUE, Double.MAX_VALUE, 12, 0}
        };
    // c0 → c2 → c1 → c3 → c4 → c0
    // or
    // c0 → c1 → c2 → c3 → c4 → x0
    System.out.println(solution(dis) == 45);
  }
}
