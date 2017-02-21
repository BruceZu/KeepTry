//  Copyright 2017 The keepTry Open Source Project
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

package tree.heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <a href="https://leetcode.com/problems/trapping-rain-water-ii/?tab=Description">LeetCode</a>
 */
public class Leetcode407TrappingRainWaterII {

    class Cell {
        int r;
        int column;
        int height;

        public Cell(int row, int col, int height) {
            this.r = row;
            this.column = col;
            this.height = height;
        }
    }

    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int rows = heightMap.length;
        int columns = heightMap[0].length;
        boolean[][] visited = new boolean[rows][columns];
        Queue<Cell> circle = new PriorityQueue(1, new Comparator<Cell>() {
            public int compare(Cell a, Cell b) {
                return a.height - b.height;
            }
        }); // water level
        // initial the border circle
        for (int r = 0; r < rows; r++) {
            visited[r][0] = true;
            visited[r][columns - 1] = true;
            circle.offer(new Cell(r, 0, heightMap[r][0]));
            circle.offer(new Cell(r, columns - 1, heightMap[r][columns - 1]));
        }

        for (int col = 0; col < columns; col++) {
            visited[0][col] = true;
            visited[rows - 1][col] = true;
            circle.offer(new Cell(0, col, heightMap[0][col]));
            circle.offer(new Cell(rows - 1, col, heightMap[rows - 1][col]));
        }

        // Get out the current water level cell, BFS to its unvisited neighbors:
        // record visited.
        // if the neighbor is lower,:
        //      sure it will keep water,
        //      assume it is filled water, update its level to current water level.
        // add the neighbors to the bounder circle.
        int[][] toNeighber = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int res = 0;
        while (!circle.isEmpty()) {
            Cell waterLevelCell = circle.poll();
            // -- BFS
            for (int[] toNeighbor : toNeighber) {
                int row = waterLevelCell.r + toNeighbor[0];
                int col = waterLevelCell.column + toNeighbor[1];
                if (row >= 0 && row < rows && col >= 0 && col < columns && !visited[row][col]) {
                    visited[row][col] = true;
                    if (waterLevelCell.height > heightMap[row][col]) {// sure there is water
                        res += waterLevelCell.height - heightMap[row][col];
                    }
                    circle.offer(new Cell(row, col, Math.max(heightMap[row][col], waterLevelCell.height)));
                }
            }
        }
        return res;
    }
    // improvement: when visited neighbor is lower the current water level cell
    // continue BFS from there  to save the process of put it in circle and fetch it out again.
}
