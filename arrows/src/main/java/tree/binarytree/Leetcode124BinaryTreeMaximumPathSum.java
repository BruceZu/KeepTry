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

package tree.binarytree;

public class Leetcode124BinaryTreeMaximumPathSum {
  /*
    124. Binary Tree Maximum Path Sum

    A path in a binary tree is a sequence of nodes where
    each pair of adjacent nodes in the sequence has an edge connecting them.
    A node can only appear in the sequence at most once.
    Note that the path does not need to pass through the root.

    The path sum of a path is the sum of the node's values in the path.
    Given the root of a binary tree, return the maximum path sum of any path.

    Input: root = [1,2,3]
    Output: 6
    Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

    Input: root = [-10,9,20,null,null,15,7]
    Output: 42
    Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.



    Constraints:
        The number of nodes in the tree is in the range [1, 3 * 104].
        -1000 <= Node.val <= 1000

  */
  public static class TreeNode {
    int v;
    TreeNode l;
    TreeNode r;
    boolean isAliveNode;

    TreeNode() {}

    TreeNode(int val) {
      this.v = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.v = val;
      this.l = left;
      this.r = right;
    }
  }
  // implement--------------------------------------------------------------------------
  /*
  Idea:
    Note: value can be negative
   So there are in total 6 possible path length
    1 v
    2 l (compared with max in the sub-tree, ignore it)
    3 r (compared with max in the sub-tree, ignore it))
    4 l+v
    5 r+v
    6 l+v+r
    among then the 1, 4, 5 starting from current node, the max of them will be return
    and used for above level

    then the bigger one of result and l+v+r will be used to update max=Math.max(max,l+v+r )

   O(N) time, O(H) space
   N is number of nodes
   H is a tree height
  */
  int max = Integer.MIN_VALUE;

  public int maxPathSum(TreeNode root) {
    help(root);
    return max;
  }

  private Integer help(TreeNode n) {
    if (n == null) return 0;
    int l = help(n.l);
    int r = help(n.r);
    int forAbove = Math.max(Math.max(r, l) + n.v, n.v);
    max = Math.max(max, Math.max(forAbove, l + r + n.v));
    return forAbove;
  }
  /*
  alive node can only be leaf nodes and marked with asterisk mask
  return the max length of path within 2 alive nodes
  Idea:
  compare the max with path passing the current node
  */
  int maxLength;

  public int maxLengthPathWithin2AliveNodes(TreeNode n) {
    f(n);
    return maxLength;
  }

  private int f(TreeNode n) {
    if (n == null) return 0;
    int l = f(n.l), r = f(n.r);
    maxLength = Math.max(maxLength, l + r + n.v);
    return Math.max(l, r) + n.v;
  }
  /*
  Followup: alive node can be any node and marked with asterisk mask
   a question here is the path with an alive node at one end can include one or more alive nodes within both sides?
   e.g.            a*
                 /   |
              b*    c*
        b*---a*---c* is valid path                (A)
        -2    3   4
        only b*---a*, or  a*---c*?  is valid path (B)
  */
  /*
  For scenario B:
  the fB() return the max length of path from alive node nearest to the current node to current node
  Details:
  return null if the current tree is null or the current tree does not have alive node.
  else there is at least one alive nodes in current tree:
   return the max path length:
     - current node is alive
           return n.v;
     - current node is not alive and one or both child contains alive node


  compare max with
     - current node is alive:
           0, 1 or 2 child contains alive node
           when l!=null || r!=null ...

     - current node is not alive
          1 or 2 child contains alive node
          when l!=null && r!=null ...
             max= Math.max( max,    l+r+n.v)

              5
          /      \
        2*         0
       / \        / \
      100* 50*  14* 15*
   */
  private Integer fB(TreeNode n) {
    if (n == null) return null;
    Integer l = fB(n.l), r = fB(n.r);
    if (l == null && r == null && !n.isAliveNode) return null;
    // at least contains one alive node
    int tmp = Integer.MIN_VALUE;
    if (l != null) tmp = Math.max(tmp, l);
    if (r != null) tmp = Math.max(tmp, r);
    if (n.isAliveNode) {
      if (tmp != Integer.MIN_VALUE) max = Math.max(max, tmp + n.v);
      return n.v;
    } else { // at least one child tree contains one alive node
      if (l != null && r != null) max = Math.max(max, l + r + n.v);
      return tmp + n.v;
    }
  }

  /*
  return the max length of path: from an alive node to current node
     the alive node may/may not be root of the tree,
     the alive node may/may not be the nearest alive node to the root
     the alive node may/may not be alive node most far away to the root
     the root may/may not be an alive node

     on the path there maybe 0 or more alive node within the alive node and the root
     node value is integer
                          5
                       /      \
                     2*         -5
                     / \       /   \
                 -20* -3*   14*    -5*

                          5
                       /      \
                     2*         -5*
                     / \       /   \
                 -20  -3    14     -5*


  */
  private Integer fA(TreeNode n) {
    if (n == null) return null;
    Integer l = fA(n.l), r = fA(n.r);
    if (l == null && r == null && !n.isAliveNode) return null;

    int c = Integer.MIN_VALUE; // max length got from child tree that contains alive node
    if (l != null) c = Math.max(c, l);
    if (r != null) c = Math.max(c, r);

    if (l != null && r != null) max = Math.max(max, l + n.v + r);
    if (n.isAliveNode && c != Integer.MIN_VALUE) max = Math.max(max, c + n.v);

    return n.v + (n.isAliveNode ? Math.max(c, 0) : c);
  }
}
