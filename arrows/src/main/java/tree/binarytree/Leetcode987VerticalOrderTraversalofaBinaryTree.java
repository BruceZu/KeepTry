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

package tree.binarytree;

import tree_legend.Tree;

import java.util.*;

public class Leetcode987VerticalOrderTraversalofaBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
  /*
   987. Vertical Order Traversal of a Binary Tree

    Given the root of a binary tree, calculate the vertical order traversal of the binary tree.
    For each node at position (row, col), its left and right children will be at positions
    (row + 1, col - 1) and (row + 1, col + 1)
    respectively. The root of the tree is at (0, 0).
    The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each
    column index starting from the leftmost column and ending on the rightmost column.
    There may be multiple nodes in the same row and same column. In such a case,
    sort these nodes by their values.

    Return the vertical order traversal of the binary tree.

   Input: root = [3,9,20,null,null,15,7]
   Output: [[9],[3,15],[20],[7]]
   Explanation:
   Column -1: Only node 9 is in this column.
   Column 0: Nodes 3 and 15 are in this column in that order from top to bottom.
   Column 1: Only node 20 is in this column.
   Column 2: Only node 7 is in this column.



   Input: root = [1,2,3,4,5,6,7]
   Output: [[4],[2],[1,5,6],[3],[7]]
   Explanation:
   Column -2: Only node 4 is in this column.
   Column -1: Only node 2 is in this column.
   Column 0: Nodes 1, 5, and 6 are in this column.
             1 is at the top, so it comes first.
             5 and 6 are at the same position (2, 0), so we order them by their value, 5 before 6.
   Column 1: Only node 3 is in this column.
   Column 2: Only node 7 is in this column.



   Input: root = [1,2,3,4,6,5,7]
   Output: [[4],[2],[1,5,6],[3],[7]]
   Explanation:
   This case is the exact same as example 2, but with nodes 5 and 6 swapped.
   Note that the solution remains the same since 5 and 6 are in the same location and should be ordered by their values.


   Constraints:

   The number of nodes in the tree is in the range [1, 1000].
   0 <= Node.val <= 1000
  */
  /*
  DFS
  O(NlogN) time,
  O(N) space
  */

  private void dfs(TreeNode n, int c, int r) {
    if (n == null) return;
    q.offer(new int[] {c, r, n.val});
    dfs(n.left, c - 1, r + 1);
    dfs(n.right, c + 1, r + 1);
  }

  Queue<int[]> q;

  public List<List<Integer>> verticalTraversal_(TreeNode root) {
    q =
        new PriorityQueue<>(
            (a, b) -> {
              for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                  return a[i] - b[i]; // int[] keeps  column, row, value
                }
              }
              return 0;
            });
    dfs(root, 0, 0);
    List<List<Integer>> r = new ArrayList();

    Integer col = null;
    while (!q.isEmpty()) {
      if (col == null || q.peek()[0] != col) {
        r.add(new ArrayList<>());
        col = q.peek()[0];
      }
      r.get(r.size() - 1).add(q.poll()[2]);
    }

    return r;
  }

  /*
    Partition Sorting: only sort each column
      if the keys of a HashMap
      -  is known in a range [l, r], and
      -  it can fulfill the range without gap left
      Then it is possible to access the Map entries in order without sorting them.

      MashMap<column, PriorityQueue<int[row, node value]>>
      or
      MashMap<column, List<int[row, node value]>>  then sort the list later

      Why it is better?
      Although we would still need to sort the subgroups respectively,
      it would be faster to sort a series of subgroups than sorting them all together in a single group.
      Here is a not-so-rigid proof:
      Suppose that we have a list of N elements, it would then take O(NlogN) time to sort this list.
      Next, we divide the list into k sublists equally. Each list would contain N/K elements.
      Similarly, it would take
        O( k*(N/K)*log(N/K))
      = O( Nlog(N/K))
      which is less than O(NlogN)

    Time Complexity: O(Nlog(N/k)
         k is the width of the tree, k is also the number of columns in the result
    Space O(N)
  */
  public List<List<Integer>> verticalTraversal(TreeNode root) {
    Map<Integer, Queue<int[]>> map = new HashMap<>();

    dfs(root, 0, 0, map);
    int L = Collections.min(map.keySet());
    int R = Collections.max(map.keySet());
    List<List<Integer>> a = new ArrayList<>(map.size());
    for (int i = L; i <= R; i++) {
      List<Integer> col = new LinkedList<>();
      Queue<int[]> q = map.get(i);
      while (!q.isEmpty()) {
        col.add(q.poll()[1]);
      }
      a.add(col);
    }
    return a;
  }

  private void dfs(TreeNode node, int r, int col, Map<Integer, Queue<int[]>> map) {
    if (node == null) return;
    map.putIfAbsent(
        col,
        new PriorityQueue<>(
            (a, b) -> {
              // 0: row, 1: value
              if (a[0] != b[0]) return a[0] - b[0];
              return a[1] - b[1];
            }));
    map.get(col).offer(new int[] {r, node.val});
    dfs(node.left, r + 1, col - 1, map);
    dfs(node.right, r + 1, col + 1, map);
  }
}
