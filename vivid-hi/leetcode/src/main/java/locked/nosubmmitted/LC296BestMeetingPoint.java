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

package locked.nosubmmitted;

/**
 * Difficulty: Hard
 * 296. Best Meeting Point
 * <p/><pre>
 * A group of two or more people wants to meet and minimize the total travel distance.
 * You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group.
 * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
 * https://en.wikipedia.org/wiki/Taxicab_geometry
 * <p/>
 * For example, given three people living at (0,0), (0,4), and (2,2):
 * <p/> <pre>
 * 1 - 0 - 0 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.
 * <p/>
 * Show Hint
 * Hide Company Tags Twitter
 * Hide Tags Math Sort
 * Hide Similar Problems (H) Shortest Distance from All Buildings
 */
public class LC296BestMeetingPoint {
    public int minTotalDistance(int[][] grid) {
        return 9;
    }
// the fast one is 99.6% not shared

    ///////////Sol. 1 Median Point ////////////    2ms  beat 83.9%

    public int minTotalDistance1(int[][] g) {
        int n = g.length, m = g[0].length, nc = 0, nr = 0;
        int[] col = new int[n * m], row = new int[n * m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (g[i][j] > 0) col[nc++] = i;

        for (int j = 0; j < m; j++)
            for (int i = 0; i < n; i++)
                if (g[i][j] > 0) row[nr++] = j;

        return minD(col, nc) + minD(row, nr);
    }

    private int minD(int[] d, int n) {
        int ans = 0;
        for (int i = 0, j = n - 1; i < j; i++, j--) ans += d[j] - d[i];
        return ans;
    }

    // same as above with description

    /**
     * <pre>
     * Before solving the 2D problem we first consider a 1D case. The solution is quite simple.
     * Just find the median of all the x coordinates and calculate the distance to the median.
     *
     * Alternatively, we can also use two pointers to solve the 1D problem. left and right are
     * how many people one left/right side of coordinates i/j. If we have more people on the left
     * we let j decrease otherwise increase i. The time complexity is O(n) and space is O(1).
     *
     * To be more clear, a better view is we can think i and j as two meet points. All the people
     * in [0, i] go to meet at i and all the people in [j, n - 1] meet at j.
     * We let left = sum(vec[:i+1]), right = sum(vec[j:]), which are the number of people at each meet point,
     * and d is the total distance for the left people meet at i and right people meet at j.
     *
     * Our job is to let i == j with minimum d.
     *
     * If we increase i by 1, the distance will increase by left since there are 'left' people at
     * i and they just move 1 step. The same applies to j, when decrease j by 1, the distance will
     * increase by right. To make sure the total distance d is minimized we certainly want to move
     * the point with less people. And to make sure we do not skip any possible meet point options
     * we need to move one by one.
     *
     * For the 2D cases we first need to sum the columns and rows into two vectors and call the 1D
     * algorithm. The answer is the sum of the two. The time is then O(mn) and extra space is O(m+n)
     *
     * Moreover, the solution is still O(mn) with the follow up:
     *
     * @param grid
     * @return
     */
    public int minTotalDistance2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] row_sum = new int[n], col_sum = new int[m];

        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j) {
                row_sum[j] += grid[i][j];
                col_sum[i] += grid[i][j];
            }

        return minDistance1D(row_sum) + minDistance1D(col_sum);
    }

    public int minDistance1D(int[] vector) {
        int i = -1, j = vector.length;
        int d = 0, left = 0, right = 0;

        while (i != j) {
            if (left < right) {
                d += left;
                left += vector[++i];
            } else {
                d += right;
                right += vector[--j];
            }
        }
        return d;
    }
}