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

package leetcode;

import java.util.Arrays;

/**
 * 286. Walls and Gates
 * <p/>
 * https://leetcode.com/problems/walls-and-gates/
 * <p/>
 * <p/>
 * You are given a m x n 2D grid initialized with these three possible values.
 * <p/>
 * -1 - A wall or an obstacle.<pre>
 * 0 - A gate.
 * INF - Infinity means an empty room. We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
 * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
 * <p/>
 * For example, given the 2D grid:<pre>
 * INF   -1   0   INF
 * INF  INF  INF   -1
 * INF   -1  INF   -1
 * 0     -1  INF   INF<pre>
 * After running your function, the 2D grid should be:<pre>
 * 3  -1   0   1
 * 2   2   1  -1
 * 1  -1   2  -1
 * 0  -1   3   4<pre>
 * Hide Company Tags Google Facebook
 * Hide Tags Breadth-first Search
 * Hide Similar Problems (M) Surrounded Regions (M) Number of Islands (H) Shortest Distance from All Buildings
 */
public class LC286WallsandGates {
    //fast solution which beat 99% . no share coce
    // this beat 87%
    private void bfs(int[][] rooms, int i, int j, int distance) {
        if (i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length) {
            return;
        }
        if (rooms[i][j] < distance) {
            return;
        } else {
            rooms[i][j] = distance;
            bfs(rooms, i + 1, j, distance + 1);
            bfs(rooms, i - 1, j, distance + 1);
            bfs(rooms, i, j + 1, distance + 1);
            bfs(rooms, i, j - 1, distance + 1);
        }
    }

    public void wallsAndGates2(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            return;
        }
        for (int i = 0; i < rooms.length; ++i) {
            for (int j = 0; j < rooms[0].length; ++j) {
                if (rooms[i][j] == 0) {
                    bfs(rooms, i, j, 0); // blast from every door.
                }
            }
        }
    }

    // beat 7 bad
    public void wallsAndGates3(int[][] rooms) {
        int d /* compared with*/ = 0;
        while (true) {
            boolean noINFLeft = true;
            boolean noDoor = true;
            for (int i = 0; i < rooms.length; i++) {
                int[] cl /**current line*/ = rooms[i];
                for (int j = 0; j < cl.length; j++) {
                    int v = rooms[i][j];
                    if (v == 2147483647) {
                        if (noINFLeft == true) {
                            noINFLeft = false;
                        }
                        if (i - 1 >= 0 && rooms[i - 1][j] == d
                                || i + 1 < rooms.length && rooms[i + 1][j] == d
                                || j - 1 >= 0 && rooms[i][j - 1] == d
                                || j + 1 < cl.length && rooms[i][j + 1] == d) {
                            if (noDoor == true) {
                                noDoor = false;
                            }
                            rooms[i][j] = d + 1;
                        }
                    }
                }
                System.out.println(Arrays.toString(cl));
            }
            if (noINFLeft || noDoor) {
                break;
            }
            d++; // do not forget here
        }
    }
}
