//  Copyright 2019 The KeepTry Open Source Project
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

package bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 317. Shortest Distance from All Buildings
 * https://leetcode.com/problems/shortest-distance-from-all-buildings/
 * Difficulty: Hard <pre>
 * You want to build a house on an empty land which reaches all buildings
 * in the shortest amount of distance. You can only move up, down, left and right.
 * You are given a 2D grid of values 0, 1 or 2, where:
 *
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through.
 * Each 2 marks an obstacle which you cannot pass through.
 * For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
 *
 * 1 - 0 - 2 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
 *
 * Note:
 * There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
 *
 *  Company Tags Google Zenefits
 *  Tags Breadth-first Search
 *  Similar Problems (M) Walls and Gates (H) Best Meeting Point
 */
public class Leetcode317ShortestDistancefromAllBuildings {

    /**
     * <pre>
     * Solution 1
     * Runtime: O(m^2 * n^2)?
     */
    public static int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        // distance from a spot to all the buildings
        int[][] dinstancesToBuildings = new int[m][n];
        // how many buildings a spot has connects to
        int[][] reachedBuildingsNum = new int[m][n];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    bfsSpacePotDistancesAndFoundBuildingsNum(
                            grid, i, j, dinstancesToBuildings, reachedBuildingsNum);
                }
            }
        }

        // buildings in grid
        // O(m*n)
        int buildingsNum = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    buildingsNum++;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        boolean findOne = false;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    if (reachedBuildingsNum[i][j] == buildingsNum) {
                        findOne = true;
                        min = Math.min(min, dinstancesToBuildings[i][j]);
                    }
                }
            }
        }
        return findOne ? min : -1;
    }

    /**
     * <pre>
     * // -  for performance: counts buildings numbers found during the bfs
     *        If a building cannot connect to all the other buildings. Then no solution, return -1.
     * -  for found space pot:
     *      - add the distances from current building
     *      - add the building nums reached from this space pot. check it is valid or not later
     */
    private static void bfsSpacePotDistancesAndFoundBuildingsNum(
            int[][] grid,
            int _x,
            int _y,
            int[][] dinstancesToBuildings,
            int[][] reachedBuildingsNum) {

        int m = grid.length, n = grid[0].length;

        Queue<Integer> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];

        queue.offer(_x * n + _y);
        visited[_x][_y] = true;
        int[] dxy = {-1, 0, 1, 0, -1}; // directions
        // All buildings should connect to each other. Else so solution.
        // int foundBuildingsNum = 1; // include itself. for performance

        int steps = 0;
        while (!queue.isEmpty()) {
            steps++;
            int size = queue.size();
            while (size-- != 0) {
                Integer cur = queue.poll();
                _y = cur % n;
                _x = cur / n;
                // try 4 directions
                for (int i = 0; i < 4; ++i) {
                    int x = _x + dxy[i], y = _y + dxy[i + 1];
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        continue;
                    }
                    //                    // a building: performance
                    //                    if (grid[x][y] == 1 && !visited[x][y]) {
                    //                        visited[x][y] = true;
                    //                        foundBuildingsNum++;
                    //                    }
                    // a space spot
                    if (grid[x][y] == 0 && !visited[x][y]) {
                        visited[x][y] = true;
                        queue.offer(x * n + y);
                        dinstancesToBuildings[x][y] += steps;
                        reachedBuildingsNum[x][y]++;
                    }
                }
            }
        }
    }

    // solution 2 -----------------------------------
    // O(m^2 * n^2)
    public static int shortestDistance3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int buildingsNum = 0;
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    buildingsNum++;
                }
            }
        }

        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    // from each space point to calculate all distance from it to houses
                    // if it cannot reach all houses, return MAX value to be ignored
                    minLen = Math.min(minLen, bfsDistancesFromSpacePoint(grid, i, j, buildingsNum));
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    private static int bfsDistancesFromSpacePoint(int[][] grid, int _x, int _y, int buildingsNum) {
        Queue<Integer> q = new LinkedList<>();
        int[] dxy = {0, 1, 0, -1, 0}; // directions
        Set<Integer> v = new HashSet<>(); // visited

        //
        int allDistancesToHouses = 0;
        int foundBuildingsNum = 0;
        int steps = 0;
        //
        int m = grid.length, n = grid[0].length;
        q.offer(_x * n + _y);
        v.add(_x * n + _y);
        while (!q.isEmpty()) {
            steps++;
            int size = q.size();
            while (size-- != 0) {
                int cur = q.poll();
                _x = cur / n;
                _y = cur % n;
                for (int k = 0; k < 4; ++k) {
                    int x = _x + dxy[k], y = _y + dxy[k + 1];
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        continue;
                    }
                    if (!v.contains(x * n + y) && grid[x][y] != 2) {
                        v.add(x * n + y);
                        if (grid[x][y] == 1) {
                            foundBuildingsNum++;
                            allDistancesToHouses += steps;
                            continue;
                        }
                        if (grid[x][y] == 0) {
                            q.offer(x * n + y);
                        }
                    }
                }
            }
        }
        if (foundBuildingsNum != buildingsNum) return Integer.MAX_VALUE;
        return allDistancesToHouses;
    }

    // test -----------------------------------
    public static void main(String[] args) {
        System.out.println(shortestDistance(null) == 0);
        System.out.println(shortestDistance3(null) == 0);

        System.out.println(shortestDistance(new int[][] {}) == 0);
        System.out.println(shortestDistance3(new int[][] {}) == 0);

        // There is circle where there is building and space pot
        int[][] grid =
                new int[][] {
                    {1, 0, 2, 2, 2, 0},
                    {0, 0, 2, 0, 2, 0},
                    {1, 0, 2, 1, 2, 0},
                    {0, 0, 2, 2, 2, 0},
                    {1, 0, 0, 0, 0, 0}
                };
        System.out.println(shortestDistance(grid) == -1);
        System.out.println(shortestDistance3(grid) == -1);
        // case in Leetcode
        grid =
                new int[][] {
                    {1, 0, 2, 0, 1},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0}
                };
        System.out.println(shortestDistance(grid) == 7);
        System.out.println(shortestDistance3(grid) == 7);
        // There are circle where there are space points
        // no space pot can connect to all buildings.
        grid =
                new int[][] {
                    {1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 2, 2, 2, 0, 0},
                    {0, 1, 2, 0, 2, 0, 0},
                    {0, 0, 2, 2, 2, 0, 1},
                    {0, 0, 0, 0, 0, 0, 1}
                };
        System.out.println(shortestDistance(grid) == -1);
        System.out.println(shortestDistance3(grid) == -1);
        // There are circle where there are space points
        // Not all space pots cannot connect to all buildings.
        grid =
                new int[][] {
                    {1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 2, 2, 2, 0, 0},
                    {0, 0, 2, 0, 2, 0, 0},
                    {0, 0, 2, 2, 2, 0, 1},
                    {0, 0, 0, 0, 0, 0, 1}
                };
        System.out.println(shortestDistance(grid) == 31);
        System.out.println(shortestDistance3(grid) == 31);
    }
}
