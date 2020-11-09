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
 * refer paper <<Travelling Salesman Problem andBellman-Held-Karp AlgorithmQuang Nhat NguyenMay 10, 2020>>
 * http://www.math.nagoya-u.ac.jp/~richard/teaching/s2020/Quang1.pdf
 */
public class TSPBellmanHeldKarp {
    // runtime O(2^n*n^2)
    public static double solution(double[][] dis) {
        if (dis == null) return 0;
        // dis[from][to] is the smallest distance from city 'from' to city 'to'
        // is there is no way between city i to city j then the dis[i][j] will be Double.MAX_VALUE
        int N = dis.length; // city number.  city is tag with number: from 0 to dis.length-1
        int M = (1 << N); //possible number of compression status, sub set of cities, in total 2^N status, 0～2^N -1

        // dp[x][y]:
        // minimum distance from start city 0, visit each city in x and end at city y.
        // when there is only one city, it is city 0, x is 2^0=1.
        // x always include city 0: compression status x start from 2^0=1; valid x always is odd number
        // when x={city 0} dp[1][0]=0;
        // when x has more cities than city 0, city 0 will not be the candidate of end city
        // dp[x][y] is distance, not cycle.
        // the start city 0 can be any city, but it does not change the result. So let city 0 as the start city.
        double[][] dp = new double[M][N];

        // initial
        for (int i = 0, l = dp.length; i < l; i++) {
            Arrays.fill(dp[i], Double.MAX_VALUE);
        }

        int y = 0;
        int x = (int) Math.pow(2, y);
        dp[x][y] = 0; // x only has the start city 0, 1 is 2^0, 0 is start city 0.

        // bottom-up
        for (; x < M; x++) { //O(2^n)
            // x always has the start city 0. so x is invalid when it is even number
            if ((x & 1) != 1) continue;

            //O(n^2)
            for (int ec = 1; ec < N; ec++) {// all possible end city, and not in x
                if ((x & (1 << ec)) != 0) continue; // Note '!=0" does not mean '==1"
                for (int nec = 0; nec < N; nec++) {
                    // in sub problem, next possible end city, it should be in x
                    // - when x={city 0}, dp[x][0] = 0; nec is start city 0
                    // - when x has other city than city 0. In this case the dp[x][0] = double.MAX_VALUE.
                    //   nec should not be city 0
                    if ((x & (1 << nec)) != 0) {
                        dp[x | (1 << ec)][ec] =
                                Math.min(
                                        dp[x | (1 << ec)][ec], dp[x][nec] + dis[nec][ec] // change to sub problem
                                );
                    }
                }
            }
        }
        if (N == 1) return 0;
        double result = Double.MAX_VALUE;
        for (int ec = 1; ec < N; ec++) {
            result = Math.min(dp[M - 1][ec] + dis[ec][0], result); // add distance from end city i to start city 0, become cycle
        }

        return result;
    }

    //--------------------------------------------------------------------------------
    public static double solution2(double[][] dis) {
        if (dis == null) return 0;
        int N = dis.length;
        int M = (1 << N);
        double[][] dp = new double[M][N];
        for (int i = 0, l = dp.length; i < l; i++) {
            Arrays.fill(dp[i], Double.MAX_VALUE);
        }
        int y = 0;
        int x = (int) Math.pow(2, y);
        dp[x][y] = 0;
        for (; x < M; x++) {
            if ((x & 1) != 1) continue;

            for (int ec = 1; ec < N; ec++) {
                if ((x & (1 << ec)) != 0) continue;
                // in sub problem, next possible end city, it should be in x
                if (x == 1) {
                    // - when x={city 0}, x=1, dp[x][0] = dp[1][0]=0; nec is start city 0
                    dp[1 | (1 << ec)][ec] = dis[0][ec];
                } else {
                    // -  when x has other city than city 0. In this case the dp[x][0] = double.MAX_VALUE.
                    //   nec should not be start city 0
                    for (int nec = 1; nec < N; nec++) {
                        if ((x & (1 << nec)) != 0) {
                            dp[x | (1 << ec)][ec] =
                                    Math.min(
                                            dp[x | (1 << ec)][ec], dp[x][nec] + dis[nec][ec] // change to sub problem
                                    );
                        }
                    }
                }
            }
        }
        if (N == 1) return 0;
        double result = Double.MAX_VALUE;
        for (int ec = 1; ec < N; ec++) {
            result = Math.min(dp[M - 1][ec] + dis[ec][0], result);
        }

        return result;
    }

    //--------------------------------------------------------------------------------
    public static void main(String[] args) {
        // city 0 -> city 1 -> city 2 -> city 3 -> city 0
        //        3         2         2        3
        double[][] dis = new double[][]{
                {0, 3, 6, 7},
                {5, 0, 2, 3},
                {6, 4, 0, 2},
                {3, 7, 5, 0},
        };
        System.out.println(
                solution(
                        dis) == 10);
        System.out.println(
                solution2(
                        dis) == 10);
        dis = new double[][]{
                {0, 5, 10, 16, 11},
                {9, 0, 7, 12, Double.MAX_VALUE},
                {12, 5, 0, 15, Double.MAX_VALUE},
                {15, 14, 9, 0, 9},
                {9, Double.MAX_VALUE, Double.MAX_VALUE, 12, 0}
        };
        // c0 → c2 → c1 → c3 → c4 → c0
        // or
        // c0 → c1 → c2 → c3 → c4 → x0
        System.out.println(
                solution(
                        dis) == 45);
        System.out.println(
                solution2(
                        dis) == 45);


    }
}
