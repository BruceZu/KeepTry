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

package a_star_search;

import java.util.*;

public class Leetcode505TheMazeII {
  /* Leetcode 505. The Maze II
    a ball in a maze with empty spaces (represented as 0) and walls (represented as 1).
    The ball can go through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
    When the ball stops, it could choose the next direction
    return the shortest distance for the ball to stop at the destination.
    If the ball cannot stop at destination, return -1.
    The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).

    assume that the borders of the maze are all walls


    m == maze.length
    n == maze[i].length
    1 <= m, n <= 100
    maze[i][j] is 0 or 1.
    start.length == 2
    destination.length == 2
    0 <= startrow, destinationrow <= m
    0 <= startcol, destinationcol <= n
    Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
    The maze contains at least 2 empty spaces.

    [0,0,1,0,S]
    [0,0,0,0,0]
    [0,0,0,1,0]
    [1,1,0,1,1]
    [0,0,0,0,T]
    Output: 12

    [0,0,1,0,S]
    [0,0,0,0,0]
    [0,0,0,1,0]
    [1,1,T,1,1]
    [0,0,0,0,0]
    Output: -1

    [0,T,0,0,0]
    [1,1,0,0,1]
    [0,0,0,0,0]
    [0,1,0,0,1]
    [0,1,0,S,0],
    Output: -1

    [0,0,1,0,S]
    [0,0,T,0,0]
    [0,0,0,1,0]
    [1,1,0,1,1]
    [0,0,0,0,0]
    Output: 9

  */
  /*---------------------------------------------------------------------------
  Understand:
    note:
    - 'it won't stop rolling until hitting a wall.'
    -  If the ball cannot stop at destination, return -1.
    -  it is possible to skip over target cell for the one direction but stop
       at the target cell from another direction later, see the above last case

   Idea: A* search
    with heuristic to improve the BFS.
    heuristic needs priority queue to keep status:
       - x,
       - y,
       - active type: 0 stop, 1 move up, 2 move left, 3 move down, 4 move right
       - steps
    heuristic by the result of comparing  steps + Manhattan distance of current
    location with target location

    If current active type is 0 stop, it will select all possible directions
    else it will try to continue current direction to move, till stop.
    when it stop check if it is target cell.
    if all possible cell is visited, BFS, and did not ever stop at target cell, return -1

    visited: same as BFS, used to keep BFS exploiting out forward.
             use `cell location +  horizon | vertical direction | no direction just stop there`
             to mark a cell is visited or not.
             add it to visited before in queue.
             current\visited   0  v h null
                0              x  y y  y
                v              x  x y  y
                h              x  y x  y
            One cell can be visited at most twice v,0; h,0; 0.
    process each status before in queue, because need to know if current cell active type
            1|2|3|4 can continue or have to stop 0,
            active status conversion:
            - active status 0 -> active status 1,2,3,4
            - active status1,2,3,4 -> stop 1,2,3,4 | active status 0

   This algorithm check heuristic on each step, so even 'it won't stop rolling until hitting a wall.'
   while in this algorithm it is possible some move is hold on the half, without reaching a stop cell

   X=M*N
   O(XlogX) time and O(X)space.
  */
  public int shortestDistance1(int[][] maze, int[] S, int[] T) {
    Map<Integer, int[]> d4 = new HashMap();
    d4.put(1, new int[] {1, 0}); // up
    d4.put(2, new int[] {0, -1}); // left
    d4.put(3, new int[] {-1, 0}); // down
    d4.put(4, new int[] {0, 1}); // right
    // (y,x) y is row, x is column

    int M = maze.length, N = maze[0].length;
    //  1 <= m, n <= 100
    Queue<int[]> q =
        new PriorityQueue<>(
            Comparator.comparingInt(a -> a[3] + Math.abs(a[0] - T[0]) + Math.abs(a[1] - T[1])));

    Set<String> visited = new HashSet();

    String k = S[0] + "-" + S[1] + "-" + 0;
    visited.add(k);
    q.offer(new int[] {S[0], S[1], 0, 0});
    while (!q.isEmpty()) {
      int[] c = q.poll();
      if (c[2] != 0) {
        int y = c[0] + d4.get(c[2])[0], x = c[1] + d4.get(c[2])[1];
        if (y >= 0 && x >= 0 && y < M && x < N && maze[y][x] != 1) {
          k = y + "-" + x + "-" + ((c[2] & 1) == 1 ? "v" : "h");
          String k0 = y + "-" + x + "-" + 0;
          if (!visited.contains(k) && !visited.contains(k0)) {
            visited.add(k);
            q.offer(new int[] {y, x, c[2], c[3] + 1});
          }
        } else {
          if (c[0] == T[0] && c[1] == T[1]) return c[3];
          k = c[0] + "-" + c[1] + "-" + 0;
          if (!visited.contains(k)) {
            visited.add(k);
            q.offer(new int[] {c[0], c[1], 0, c[3]});
          }
        }

      } else {
        for (int i = 1; i <= 4; i++) {
          int[] d = d4.get(i);
          int y = c[0] + d[0], x = c[1] + d[1];
          if (y >= 0 && x >= 0 && y < M && x < N && maze[y][x] != 1) {
            k = y + "-" + x + "-" + ((i & 1) == 1 ? "v" : "h");
            String k0 = y + "-" + x + "-" + 0;
            if (!visited.contains(k) && !visited.contains(k0)) {
              visited.add(k);
              q.offer(new int[] {y, x, i, c[3] + 1});
            }
          }
        }
      }
    }
    return -1;
  }

  /*
  Algorithm between A* and Dijkstra short path, but not is any of both.
       'it won't stop rolling until hitting a wall.' so if taking continuous moves
       as one step. then only cell stop there will be taken in account.

       cons:  It is not possible to check heuristic on each move, as a result code can not
       step forward with right heuristic, that means no guarantee the path firstly reach
       a cell is the shortest path to this cell, in this case a cell can be visited more than
       once with different steps from start cell.

       the cell poll out from the queue still has the shortest path from the start cell
       so it does not affect the rightness of result. the redundant record(s) of the same cell
       will be left in queue.

       So use cell location + steps to avoid repeat operation.
       each time after calculate a distance/steps for a cell, need check if the cell has already
       been visited and has an distance. if it is yes, need compare the new distance and the old
       one, only when new one is smaller than the old one. add a new identify of the cell
       in queue.
       use a steps[][] to keep distance/steps to save space for this cases. initial value is max possible
       steps m*n.
       cons: it can be a waste space if there is rare his kind of cases, and keep distance/steps
       with cell location in Map<location string, Integer> visited.

   X=M*N*sqr(M*N)
   O(XlogX) time and O(X)space. Refer to that of  BFS
  */
  public static int shortestDistance(int[][] maze, int[] S, int[] T) {
    int M = maze.length, N = maze[0].length;
    int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    int[][] l = new int[M][N];
    for (int[] r : l) Arrays.fill(r, 10000); // 1 <= m, n <= 100
    l[S[0]][S[1]] = 0;

    Queue<int[]> q =
        new PriorityQueue<>(
            (a, b) ->
                l[a[0]][a[1]]
                    + Math.abs(a[0] - T[0])
                    + Math.abs(a[1] - T[1])
                    - l[b[0]][b[1]]
                    - Math.abs(b[0] - T[0])
                    - Math.abs(b[1] - T[1]));
    q.offer(S);
    while (!q.isEmpty()) {
      int[] c = q.poll();
      if (c[0] == T[0] && c[1] == T[1]) return l[c[0]][c[1]];
      for (int[] d : d4) {
        int y = c[0], x = c[1], step = 0;
        while (y + d[0] >= 0
            && x + d[1] >= 0
            && y + d[0] < M
            && x + d[1] < N
            && maze[y + d[0]][x + d[1]] == 0) {
          y = y + d[0];
          x = x + d[1];
          step++;
        }
        int newl = l[c[0]][c[1]] + step;
        if (newl < l[y][x]) {
          l[y][x] = newl;
          q.offer(new int[] {y, x});
        }
      }
    }
    return -1;
  }
  /* --------------------------------------------------------------------------
  Idea:
   Dijkstra's shorted path

   X=M*N*sqr(M*N)
   O(XlogX) time and O(X)space. Refer to that of  BFS
  */
  public int shortestDistance_(int[][] maze, int[] S, int[] T) {
    int M = maze.length, N = maze[0].length;
    int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    int[][] l = new int[M][N];
    for (int[] r : l) Arrays.fill(r, 10000);
    l[S[0]][S[1]] = 0;

    Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> l[a[0]][a[1]]));

    q.offer(S);
    while (!q.isEmpty()) {
      int[] c = q.poll();
      if (c[0] == T[0] && c[1] == T[1]) return l[c[0]][c[1]];
      for (int[] d : d4) {
        int y = c[0], x = c[1], step = 0;
        while (y + d[0] >= 0
            && x + d[1] >= 0
            && y + d[0] < M
            && x + d[1] < N
            && maze[y + d[0]][x + d[1]] == 0) {
          y += d[0];
          x += d[1];
          step++;
        }
        int newl = l[c[0]][c[1]] + step;
        if (newl < l[y][x]) {
          l[y][x] = newl;
          q.offer(new int[] {y, x});
        }
      }
    }
    return -1;
  }

  /* --------------------------------------------------------------------------
  BFS
  BFS is not guaranteed the path first reached the target is the shortest path
  BFS need check all possible path, after that get the shortest one by comparing
  all possible path form a tree with root of start cell.

  watch:
  - the tree has at most 2 branches except the start cell.
  - 2 sibling cells share at most one child.
    At most there are M*N stop cells in the tree
    the deep of the tree is O(sqr(M*N))
    So a cell can be visited at most O(sqr(M*N)) times
       O                       1
      O O                    1   1
     O O O                 1   2   1
    O O O O              1   3   3   1

   X=M*N*sqr(M*N)
   O(XlogX) time and O(X)space.
  */
  public int shortestDistance__(int[][] maze, int[] S, int[] T) {
    int M = maze.length, N = maze[0].length;
    int[][] d4 = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    int[][] l = new int[M][N];
    for (int[] r : maze) Arrays.fill(r, 10000);
    l[S[0]][S[1]] = 0;

    Queue<int[]> q = new LinkedList<>();

    q.offer(S);
    int r = 10000;
    while (!q.isEmpty()) {
      int[] c = q.remove();

      if (c[0] == T[0] && c[1] == T[1]) r = Math.min(r, l[c[0]][c[1]]);

      for (int[] d : d4) {
        int y = c[0], x = c[1], step = 0;
        while (y + d[0] >= 0
            && x + d[1] >= 0
            && y + d[0] < M
            && x + d[1] < N
            && maze[y + d[0]][x + d[1]] == 0) {
          y = y + d[0];
          x = x + d[1];
          step++;
        }

        int newl = l[c[0]][c[1]] + step;
        if (newl < l[y][x]) {
          q.add(new int[] {y, x});
          l[y][x] = newl;
        }
      }
    }
    return r < Integer.MAX_VALUE ? r : -1;
  }
}
