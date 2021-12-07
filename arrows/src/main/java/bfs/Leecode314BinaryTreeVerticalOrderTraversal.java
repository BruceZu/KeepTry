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

package bfs;

import java.util.*;

public class Leecode314BinaryTreeVerticalOrderTraversal {
  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  /*
  314. Binary Tree Vertical Order Traversal

  Given the root of a binary tree, return the vertical order traversal of its nodes' values.
  (i.e., from top to bottom, column by column).

  If two nodes are in the same row and column, the order should be from left to right.


   *      3
   *     /\
   *    /  \
   *    9  20
   *       /\
   *      /  \
   *     15   7
  Input: root = [3,9,20,null,null,15,7]
  Output: [[9],[3,15],[20],[7]]


   *      3
   *     / \
   *    /   \
   *    9    8
   *   /\    /\
   *  /  \  /  \
   * 4   0 1    7
  Input: root = [3,9,8,4,0,1,7]
  Output: [[4],[9],[3,0,1],[8],[7]]


   *      3
   *     /\
   *    /  \
   *    9   8
   *   /\   /\
   *  /  \ /  \
   *  4  01   7
   *     /\
   *    /  \
   *    5   2
  Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
  Output: [[4],[9,5],[3,0,1],[8,2],[7]]
  Example 4:

  Input: root = []
  Output: []


  Constraints:

  The number of nodes in the tree is in the range [0, 100].
  -100 <= Node.val <= 100
  */

  /*
    Given the above definitions, we can now formulate the problem as a task to
    order the nodes based on the 2-dimensional coordinates column-wise order and
    row-wise order
    - ordered by column first,
    - the nodes on the same column should be ordered vertically based on their row indices.
    - for nodes on the same row and columns? two nodes might share the same <column, row>, in the case,
      the order between these two nodes should be from left to right as we did for BFS traversals.
             o
           /   \
         o       o
           \   /
            o o
        [is using DFS:
            - ensure such a priority, in DFS make sure to visit the left child node before the right child node
            - need sort this column node list with row index.
        ]
   BFS traversal naturally guarantee the vertical order of the visits
   BFS without Sorting the column: need to know the range of the column index (i.e. [min_column, max_column]).
  O(N) time and space
  */

  static class NCol {
    public TreeNode n;
    int col;

    public NCol(TreeNode n, int col) {
      this.n = n;
      this.col = col;
    }
  }

  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> r = new ArrayList();
    if (root == null) return r;

    Map<Integer, List> map = new HashMap();

    Queue<NCol> q = new LinkedList<>();
    q.offer(new NCol(root, 0));

    int L = 0, R = 0;

    while (!q.isEmpty()) {
      NCol p = q.poll();
      TreeNode n = p.n;
      int col = p.col;

      if (n != null) {
        map.putIfAbsent(col, new ArrayList<Integer>());
        map.get(col).add(n.val);

        L = Math.min(L, col);
        R = Math.max(R, col);

        q.offer(new NCol(n.left, col - 1));
        q.offer(new NCol(n.right, col + 1));
      }
    }

    for (int i = L; i < R + 1; ++i) {
      r.add(map.get(i));
    }

    return r;
  }
  /* --------------------------------------------------------------------------
  DFS
  Time O(W⋅HlogH) W is the width of the binary tree, H is the height of the tree.
  O(N) space
  */
  static class NRow {
    public TreeNode n;
    int row;

    public NRow(int row, TreeNode n) {
      this.n = n;
      this.row = row;
    }
  }

  Map<Integer, ArrayList<NRow>> map = new HashMap();
  int L = 0, R = 0;

  private void dfs(TreeNode node, Integer r, Integer col) {
    if (node == null) return;
    map.putIfAbsent(col, new ArrayList<NRow>());

    this.map.get(col).add(new NRow(r, node));
    this.L = Math.min(L, col);
    this.R = Math.max(R, col);

    this.dfs(node.left, r + 1, col - 1);
    this.dfs(node.right, r + 1, col + 1);
  }

  public List<List<Integer>> verticalOrder2(TreeNode root) {
    List<List<Integer>> a = new ArrayList();
    if (root == null) return a;
    dfs(root, 0, 0);

    // Retrieve the resuts, by ordering by column and sorting by row
    for (int i = L; i < R + 1; ++i) {
      Collections.sort(map.get(i), Comparator.comparingInt(p -> p.row));

      // convert Nrow to node value
      List<Integer> sorted = new ArrayList();
      for (NRow p : map.get(i)) sorted.add(p.n.val);

      a.add(sorted);
    }

    return a;
  }
}
