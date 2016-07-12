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

package nosubmmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * Difficulty: Hard
 * <pre>
 * A 2d grid map of m rows and n columns is initially filled with water.
 * We may perform an addLand operation which turns the water at position (row, col)
 * into a land. Given a list of positions to operate, count the number of islands
 * after each addLand operation. An island is surrounded by water and is formed by
 * connecting adjacent lands horizontally or vertically. You may assume all four
 * edges of the grid are all surrounded by water.
 *
 * Example:
 *
 * Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
 * Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).
 *
 * 0 0 0
 * 0 0 0
 * 0 0 0
 * Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.
 *
 * 1 0 0
 * 0 0 0   Number of islands = 1
 * 0 0 0
 * Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.
 *
 * 1 1 0
 * 0 0 0   Number of islands = 1
 * 0 0 0
 * Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.
 *
 * 1 1 0
 * 0 0 1   Number of islands = 2
 * 0 0 0
 * Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.
 *
 * 1 1 0
 * 0 0 1   Number of islands = 3
 * 0 1 0
 * We return the result as an array: [1, 1, 2, 3]
 *
 * Challenge:
 *
 * Can you do it in time complexity O(k log mn), where k is the length of the positions?
 *
 * Hide Company Tags Google
 * Hide Tags Union Find
 * Hide Similar Problems (M) Number of Islands
 */
public class LC305NumberofIslandsII {
    public List<Integer> numIslands2_my(int m, int n, int[][] positions) {
        return null;
    }

    // 97.84
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) grid[i][j] = -1;
        }
        int nOI = 0;
        List<Integer> result = new ArrayList<Integer>();
        for (int[] pos : positions) {
            nOI = add(pos[0], pos[1], grid, nOI);
            result.add(nOI);
        }
        return result;
    }

    int add(int x, int y, int grid[][], int nOI) {
        ++nOI;
        grid[x][y] = getID(x, y, grid);
        nOI = union(x - 1, y, x, y, grid, nOI);
        nOI = union(x + 1, y, x, y, grid, nOI);
        nOI = union(x, y - 1, x, y, grid, nOI);
        nOI = union(x, y + 1, x, y, grid, nOI);
        return nOI;
    }

    boolean neighborExists(int oX, int oY, int[][] grid) {
        if (oX < 0 || oX >= grid.length || oY < 0 || oY >= grid[0].length) return false;
        return true;
    }

    int union(int oX, int oY, int x, int y, int grid[][], int nOI) {
        //if(x ==0 && y == 1) System.out.println(grid[x][y-1]);
        if (!neighborExists(oX, oY, grid) || grid[oX][oY] == -1) return nOI;

        int oRoot = getRoot(oX, oY, grid);
        int root = getRoot(x, y, grid);
        if (oRoot == root) return nOI;
        int[] rootCoords = getCoords(root, grid);
        grid[rootCoords[0]][rootCoords[1]] = oRoot; // set this root's parent to be other root

        return --nOI;
    }

    int getRoot(int x, int y, int grid[][]) {
        int id = getID(x, y, grid);
        int parentId = grid[x][y];
        if (parentId == id) return id;
        int[] parentCoords = getCoords(parentId, grid);
        int rootId = getRoot(parentCoords[0], parentCoords[1], grid);
        grid[x][y] = rootId;
        return rootId;
    }

    int getID(int x, int y, int grid[][]) {
        int n = grid[0].length;
        return x * n + y;
    }

    int[] getCoords(int id, int grid[][]) {
        //if(id < 0) System.out.println("yup Id was -ve");
        int n = grid[0].length;
        int x = id / n;
        int y = id % n;
        //System.out.println(id + " " + x  + " " + y);
        int[] result = {x, y};
        return result;
    }

    // 97.84
//  Inspired by the other union find solution, I set array of a size(m*n+1) to avoid the initialization,
// Set the union function to return a boolean instead of void, just to make the code cleaner,
// Personally I think realistically, it's better to create a class of something like
//"class island{int parent, int rank}", I didn't do that just for the speed.

    public List<Integer> numIslands2_2(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<Integer>();
        int[] parent = new int[m * n + 1], rank = new int[m * n + 1];
        int numIslands = 0;

        for (int i = 0; i < positions.length; i++) {
            int x = positions[i][0];
            int y = positions[i][1];
            int offset = x * n + y + 1;
            parent[offset] = offset;
            numIslands++;

            if (x > 0 && parent[offset - n] != 0 && union(parent, rank, offset, offset - n)) {
                numIslands--;    //check the grid on top of current grid
            }
            if (x < m - 1 && parent[offset + n] != 0 && union(parent, rank, offset, offset + n)) {
                numIslands--; // check the grid below current grid
            }
            if (y > 0 && parent[offset - 1] != 0 && union(parent, rank, offset, offset - 1)) {
                numIslands--; // check the grid to the left of the current grid
            }
            if (y < n - 1 && parent[offset + 1] != 0 && union(parent, rank, offset, offset + 1)) {
                numIslands--; // check the grid to the right of the current grid
            }
            ans.add(numIslands);
        }
        return ans;
    }

    private int find(int[] parent, int x) {
        if (parent[x] == x) {
            return x;
        }
        return find(parent, parent[x]);
    }

    private boolean union(int[] parent, int[] rank, int x, int y) {
        int xparent = find(parent, x);
        int yparent = find(parent, y);
        if (xparent == yparent) {
            return false;
        }
        if (rank[xparent] == rank[yparent]) {
            parent[xparent] = yparent;
            rank[yparent]++;
        } else if (rank[xparent] < rank[yparent]) {
            parent[xparent] = yparent;
        } else {
            parent[yparent] = xparent;
        }

        return true;
    }

// other ideas . but code is not fast <pre>

    /**
     * This is a basic union-find problem. Given a graph with points being added, we can at least solve:
     * <p/>
     * How many islands in total?
     * Which island is pointA belonging to?
     * Are pointA and pointB connected?
     * The idea is simple. To represent a list of islands, we use trees. i.e., a list of roots.
     * This helps us find the identifier of an island faster. If roots[c] = p means the parent of node c is p,
     * we can climb up the parent chain to find out the identifier of an island, i.e., which island this point belongs to:
     * <p/>
     * Do root[root[roots[c]]]... until root[c] == c;
     * To transform the two dimension problem into the classic UF, perform a linear mapping:
     * <p/>
     * int id = n * x + y;
     * Initially assume every cell are in non-island set {-1}. When point A is added, we create a new root,
     * i.e., a new island. Then, check if any of its 4 neighbors belong to the same island. If not,
     * union the neighbor by setting the root to be the same. Remember to skip non-island cells.
     * <p/>
     * UNION operation is only changing the root parent so the running time is O(1).
     * <p/>
     * FIND operation is proportional to the depth of the tree. If N is the number of points added,
     * the average running time is O(logN), and a sequence of 4N operations take O(NlogN).
     * If there is no balancing, the worse case could be O(N^2).
     * <p/>
     * Remember that one island could have different roots[node] value for each node.
     * Because roots[node] is the parent of the node, not the highest root of the island.
     * To find the actually root, we have to climb up the tree by calling findIsland function.
     * <p/>
     * Here I've attached my solution. There can be at least two improvements:
     * union by rank & path compression. However I suggest first finish the basis,
     * then discuss the improvements.
     * <p/>
     * other idea 2
     * The basic idea is the Union-Find approach. We assign a root number for each island,
     * and use an array to record this number. For each input, we check its four neighbor cells.
     * If the neighbor cell is an island, then we retrieve the root number of this island.
     * If two neighbor cells belong to two different islands, then we union them and therefore
     * the total number of islands will become one less.
     */
    private void other() {
    }

    // 81.5
    /**
     * Union Find is an abstract data structure supporting find and unite on disjointed sets
     * of objects, typically used to solve the network connectivity problem. <pre>
     * <p/>
     * The two operations are defined like this:
     * <p/>
     * find(a,b) : are a and b belong to the same set?
     * <p/>
     * unite(a,b) : if a and b are not in the same set, unite the sets they belong to.
     * <p/>
     * With this data structure, it is very fast for solving our problem. Every position
     * is an new land, if the new land connect two islands a and b, we combine them to
     * form a whole. The answer is then the number of the disjointed sets.
     * <p/>
     * The following algorithm is derived from Princeton's lecture note on Union Find in
     * Algorithms and Data Structures It is a well organized note with clear illustration
     * describing from the naive QuickFind to the one with Weighting and Path compression.
     * With Weighting and Path compression, The algorithm runs in O((M+N) log* N) where M
     * is the number of operations ( unite and find ), N is the number of objects, log* is
     * iterated logarithm while the naive runs in O(MN).
     * <p/>
     * For our problem, If there are N positions, then there are O(N) operations and N objects
     * then total is O(N log*N), when we don't consider the O(mn) for array initialization.
     * <p/>
     * Note that log*N is almost constant (for N = 265536, log*N = 5) in this universe, so the
     * algorithm is almost linear with N.
     * <p/>
     * However, if the map is very big, then the initialization of the arrays can cost a lot of
     * time when mn is much larger than N. In this case we should consider using a
     * hashmap/dictionary for the underlying data structure to avoid this overhead.
     * <p/>
     * Of course, we can put all the functionality into the Solution class which will make
     * the code a lot shorter. But from a design point of view a separate class dedicated
     * to the data sturcture is more readable and reusable.
     * <p/>
     * I implemented the idea with 2D interface to better fit the problem.
     */
    private int[][] dir = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public List<Integer> numIslands2_5(int m, int n, int[][] positions) {
        UnionFind2D islands = new UnionFind2D(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            int x = position[0], y = position[1];
            int p = islands.add(x, y);
            for (int[] d : dir) {
                int q = islands.getID(x + d[0], y + d[1]);
                if (q > 0 && !islands.find(p, q))
                    islands.unite(p, q);
            }
            ans.add(islands.size());
        }
        return ans;
    }


    class UnionFind2D {
        private int[] id;
        private int[] sz;
        private int m, n, count;

        public UnionFind2D(int m, int n) {
            this.count = 0;
            this.n = n;
            this.m = m;
            this.id = new int[m * n + 1];
            this.sz = new int[m * n + 1];
        }

        public int index(int x, int y) {
            return x * n + y + 1;
        }

        public int size() {
            return this.count;
        }

        public int getID(int x, int y) {
            if (0 <= x && x < m && 0 <= y && y < n)
                return id[index(x, y)];
            return 0;
        }

        public int add(int x, int y) {
            int i = index(x, y);
            id[i] = i;
            sz[i] = 1;
            ++count;
            return i;
        }

        public boolean find(int p, int q) {
            return root(p) == root(q);
        }

        public void unite(int p, int q) {
            int i = root(p), j = root(q);
            if (sz[i] < sz[j]) { //weighted quick union
                id[i] = j;
                sz[j] += sz[i];
            } else {
                id[j] = i;
                sz[i] += sz[j];
            }
            --count;
        }

        private int root(int i) {
            for (; i != id[i]; i = id[i])
                id[i] = id[id[i]]; //path compression
            return i;
        }
    }
}