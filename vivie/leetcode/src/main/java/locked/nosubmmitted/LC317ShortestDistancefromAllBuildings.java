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

import java.util.LinkedList;
import java.util.Queue;

/**
 * 317. Shortest Distance from All Buildings
 * https://leetcode.com/problems/shortest-distance-from-all-buildings/
 * Difficulty: Hard <pre>
 * You want to build a house on an empty land which reaches all buildings
 * in the shortest amount of distance. You can only move up, down, left and right.
 * You are given a 2D grid of values 0, 1 or 2, where:
 * <p/>
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through.
 * Each 2 marks an obstacle which you cannot pass through.
 * For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
 * <p/>
 * 1 - 0 - 2 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
 * <p/>
 * Note:
 * There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
 * <p/>
 * Hide Company Tags Google Zenefits
 * Hide Tags Breadth-first Search
 * Hide Similar Problems (M) Walls and Gates (H) Best Meeting Point
 */
public class LC317ShortestDistancefromAllBuildings {
    // beat 100%

    /**
     * Basic idea: compute the distance from each building to all the spots,
     * use two 2D array to store the total distance of all the buildings it
     * connects to and how many buildings it connects to accordingly.
     * <p/>
     * When we find a building cannot connect to all the other buildings,
     * we will not find a solution so return -1 right away
     *
     * @param grid
     * @return
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int buildings = 0;
        //Find how many buildings in grid
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (grid[i][j] == 1) {
                    ++buildings;
                }
            }
        }
        //Store the distance for a spot to all the buildings
        int[][] distance = new int[rows][cols];
        //Store how many buildings a spot connects to
        int[][] count = new int[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (grid[i][j] == 1) {
                    //If one building cannot connect to all the other buildings
                    //Then we cannot find a solution
                    if (!bfs(grid, i, j, distance, count, buildings)) {
                        return -1;
                    }
                }
            }
        }
        int min = Integer.MAX_VALUE;
        boolean findOne = false;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (grid[i][j] == 0) {
                    if (count[i][j] == buildings) {
                        findOne = true;
                        min = min < distance[i][j] ? min : distance[i][j];
                    }
                }
            }
        }
        return findOne ? min : -1;
    }

    private class Point {
        int x;
        int y;
        int distance;

        Point(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.distance = d;
        }
    }

    //Return true if the current building can connect to all the other buildings, false other wid=se
    private boolean bfs(int[][] grid, int startX, int startY, int[][] distance, int[][] count, int buildings) {
        boolean[][] flag = new boolean[grid.length][grid[0].length];
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(startX, startY, 0));
        flag[startX][startY] = true;
        int find = 1; //All buildings should connect to each other
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            for (int i = 0; i < 4; ++i) {
                int x = dx[i] + cur.x;
                int y = dy[i] + cur.y;
                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
                    continue;
                }
                //Connect to a building
                if (grid[x][y] == 1 && !flag[x][y]) {
                    flag[x][y] = true;
                    ++find;
                }
                //Connect to a spot
                if (grid[x][y] == 0 && !flag[x][y]) {
                    flag[x][y] = true;
                    queue.offer(new Point(x, y, cur.distance + 1));
                    distance[x][y] += cur.distance + 1;
                    ++count[x][y];
                }

            }
        }
        return find == buildings;
    }
}
