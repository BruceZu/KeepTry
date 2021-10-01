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

/*
  refer paper <<travelling salesman problem and Bellman-Held-Karp Algorithm Quang Nhat Nguyen May
  10, 2020>> http://www.math.nagoya-u.ac.jp/~richard/teaching/s2020/Quang1.pdf
*/
public class TravellingSalesmanProblemBellmanHeldKarp {
  /*
  "Given
  - a list of cities
  - the distances between each pair of cities

  what is the shortest possible route that visits each city exactly once and returns to the origin city?"
   */

  /*
  Understanding
    -  any 2 nodes has a edge make them connected,  This make is possible
       to start from any node visited to a new node

  */

  /*
   Input: double[][] D.
         D[i][j] the smallest distance of city i and j.
         if D[i][j] is Double.MAX_VALUE, means no way between city i to city j
   city is tagged with number: from 0 to N-1
   city number: N = D.length.
        possible number of compression status, sub set of cities, in total 2^N status: 0～2^N -1

   Start city:
     Any city can be start city, but it does not change the result. So let city 0 as the
     start city.
   dp[s][e]:
       s: visited cities
       e: end city
       dp[s][e]: the minimum distance of path starting from city 0
                 via visited cities represented with bitmask s,
                 end at city e.
                 is distance value, not cycle value.


    always include city 0, so valid s is always an odd number
    city 0 is the start city 0
    when s={city 0} dp[1][0]=0; other dp[s][end city] initial value is MAX_VALUE

    E.g.:
          Initialized value: dp[1][0] is 0;  dp[s=2^i>1][0] is MAX_VALUE,  1 is 2^(city index i = 0)
          ...
          dp[1101][0] is MAX_VALUE (initialized)
          dp[1101][2] is city 0 -> vis city 3-> reach end city 2
          dp[1101][3] is city 0 -> vis city 2-> reach end city 3 = min{dp[101][0]+D[0][3],
                                                                       dp[101][2]+D[2][3]}

          dp[101101][2] is city 0 -> vis city 3,5-> reach end city 2
          dp[101101][3] is city 0 -> vis city 2,5-> reach end city 3
          dp[101101][5] is city 0 -> vis city 3,2-> reach end city 5 = min{dp[1101][0]+D[0][5],
                                                                           dp[1101][2]+D[2][5],
                                                                           dp[1101][3]+D[3][5]};
    End city:
     when s has more cities than city 0, city 0 will not be the candidate of end city

  O(2^n*n^2) time

     c: all possible end city, and not in s
     `(s & (1 << c)) != 0` means city `c` is in compress status `s`
     sc: city is in s and is the end city
           when sc is 0, dp[s>1][0] is MAX_VALUE;  dp[1][0] is 0,
           sc is end city make sense only
           when s==1 or s include only the start city 0

     At the end:
        add distance from end city i to start city 0, become cycle
        then return the shortest one
        Note: the min{ cost[(1 << N) - 1][i] | i is [1, N]} is the
              shortest path from vertex 0 visiting all vertex and end at x vertex.
  */
  public static double solution(double[][] g) {
    if (g == null || g.length == 1) return 0; // Assume at lest there are 2 cities.
    int N = g.length, M = 1 << N;

    double[][] cost = new double[M][N];
    //  Do not user Integer.MAX_VALUE: cost[bit][f] + g[f][t] -> negative number
    for (int i = 0; i < M; i++) Arrays.fill(cost[i], Double.MAX_VALUE);
    cost[1][0] = 0; // (int) Math.pow(2, 0), city 0

    // bottom-up
    for (int bit = 1; bit < M; bit++) { // O(2^n)
      if ((bit & 1) != 1) continue;

      // O(n^2)  number of f + number of t = number of all vertex n
      for (int t = 1; t < N; t++) { // `to` city
        if ((bit & (1 << t)) != 0) continue;

        for (int f = 0; f < N; f++) { // `from` city, dp[s][0] ==MAX_VALUE
          if ((bit & (1 << f)) != 0) {
            int next = bit | (1 << t);
            cost[next][t] = Math.min(cost[next][t], cost[bit][f] + g[f][t]);
          }
        }
      }
    }

    double r = Double.MAX_VALUE;
    // Assume at lest there are 2 cities.
    for (int e = 1; e < N; e++) r = Math.min(cost[M - 1][e] + g[e][0], r);
    return r;
  }

  /*   draw and watch a case:
             0
           /   \
          1  -  2
      Assume: start vertex is 0, any edge weight is 1

             end    bitmask   shorted cost
       init   0      001        0  ( other cost[v][bitmask] is MAX_VALUE)
       start from 001, city 0, to city 1 and city 2.
              1      011        1
              2      101        1
       -----------------------------
        next loop from now on, never from city 0.
              010 (x)
              011
          =>  2      111       min{MAX, cost[1][011]+1}=2 from city 1 to city 2
       -----------------------------
        next loop
              100 (x)
              101
          =>   1     111       min{MAX,cost[2][101]+1}=2 from city 2 to city 1
       -----------------------------
        next loop
              110 (X)
              111
            not `to` city
       -----------------------------
        next loop
             1000 not < 10000 end loop

       check min { cost[1][111], cost[2][111]} = 2
  */
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
