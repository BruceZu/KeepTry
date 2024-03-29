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

package tree.binarytree.binary_balance_tree;

import java.util.ArrayList;

public class Leetcode1382BalanceaBinarySearchTree {
  /*
    Leetcode 1382. Balance a Binary Search Tree

    Given the root of a binary search tree, return a balanced binary search
    tree with the same node values.
    If there is more than one answer, return any of them.

    A binary search tree is balanced if the depth of the two subtrees of every node never
    differs by more than 1.


    Input: root = [1,null,2,null,3,null,4,null,null]
    Output: [2,1,3,null,null,null,4]
    see the picture in Leetcode web site
    Explanation: This is not the only correct answer, [3,1,4,null,2] is also correct.

    Input: root = [2,1,3]
    Output: [2,1,3]

    The number of nodes in the tree is in the range [1, 10^4].
    1 <= Node.val <= 10^5
  */
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
  /*---------------------------------------------------------------------------
  inorder => array
  array => rebuild balanced binary tree
  O(N) time and space
   */
  public TreeNode balanceBST_(TreeNode root) {
    ArrayList<TreeNode> a = new ArrayList();
    inorder(root, a);
    return make(0, a.size() - 1, a);
  }

  private void inorder(TreeNode n, ArrayList<TreeNode> a) {
    if (n == null) return;
    inorder(n.left, a);
    a.add(n);
    inorder(n.right, a);
  }

  private TreeNode make(int l, int r, ArrayList<TreeNode> a) {
    if (l > r) return null;
    int m = l + r >>> 1;
    TreeNode n = a.get(m);
    n.left = make(l, m - 1, a);
    n.right = make(m + 1, r, a);
    return n;
  }
  /*---------------------------------------------------------------------------
      refer https://leetcode.com/problems/balance-a-binary-search-tree/discuss/541785/C%2B%2BJava-with-picture-DSW-O(n)orO(1)
            https://courses.cs.vt.edu/cs2604/spring05/mcpherson/note/BalancingTrees.pdf
      wiki:
        The Day–Stout–Warren (DSW) algorithm:
        - Unlike a self-balancing binary search tree, it does not do this incrementally during each operation.

        - O(n) time, in-place O(1) space
       https://imgur.com/COmmiya
       Steps
       1>  convert the tree into a vine (like linked list) using right rotations:
              in advance, need a dumb node, let node be the dumb's right child,
                   dumb
                      \
                    cur node
                    /     \
                   left   right
                  /  \
                 ll   lr
              if cur node has left child, right rotations result:

               - dumb.right is old cur node's left child
               - old cur node becomes it's left child' right child
               - the left child's Right sub tree become node Left sub-tree

                   dumb
                       \
                     left ( new cur node)
                    /     \
                   ll    old cur node
                         /   \
                        lr   right

               continue from the left child which is current node
               else dumb = dumb.right child. if dumb is null break the loop

               During the process when dumb switch to dumb.right, count the total number
               of nodes in variable cnt.

           Watch: The head is the former leftmost node, tail is former rightmost node.
           At last dumb.right will be the left most node of the original tree

       2>  balance the list using left rotation
           rotation https://hyperleap.com/topic/Tree_rotation
           right rotation see http://web.stanford.edu/class/archive/cs/cs161/cs161.1166/lectures/lecture7.pdf
           left rotation is the movement of a x
                                X
                             /     \
                            L       R
                                  /   \
                                 RL   RR

                      =>             R
                                  /    \
                                 X      RR
                               /  \
                              L    RL

           left rotation assumes that X has a right child (or subtree).

           see https://web.stanford.edu/class/archive/cs/cs166/cs166.1146/lectures/02/Small02.pdf
           The height of a binary search tree is the length of
           the longest path from the root to a leaf, measured in the number of edges.
           A tree with one node has height 0.
           A tree with no nodes has height -1, by convention
           high = (int)log(n) , layers = high+1

           A perfect binary tree is a binary tree in which all interior nodes
           have two children and all leaves have the same depth or same level.

       -----------------------------------------------
                BST value              m      high   number of nodes at current layer
                  8                    1      0       1
             4            12           3      1       2
          2    6      10      14       7      2       4
         1 3  5 7   9   11  13   15   15      3       8
                                                     16
       -----------------------------------------------

       let n is the BST nodes number,
           m be the total nodes of the closest perfect binary tree
       relation between n and m:
       E.g. for n in [8,14] m=7, top 3 layers
            for n=15, m=15
                m = [2^((int)log(n+1)]-1
      -----------------------------------------------

                    m                     high
                   1                       0
             2             3               1
          4    5       6       7           2
         8 9  10 11  12 13  14   15        3

      So the node difference between the perfect tree and the tree to be balanced can be calculated by
       n-m = n - ([2^((int)log(n+1)]-1)
     dumb
       \
         1
          \
            2
              \
                3
                  \
                   4
                     \
                       5
                        \
                         6
                          \
                           7
                            \
                             8
                             \
                              9
    n=9,m=7,diff=2;
    In order to get away with this excess 2 nodes, not in the closest perfect tree,
    left rotation on every SECOND node on 1 and 3: it becomes:
    Need know why every SECOND to flatten the tree.

        dumb
           \
           2
        /    \
      1       4
           /    \
         3       5
                    \
                     6
                       \
                        7
                         \
                          8
                           \
                           9
     Now let 2-4-5-6-7-8-9, in total 7 nodes form the closest perfect binary tree
     while cur node x!= null && x.right!=null, left rotation on every SECOND node of the list m/2 times
     m/2=3 times on 2 5 7

     becomes:

        dumb
         \
          4
        /  \
       2    6
      /\   / \
     1  3 5   8
             / \
            7   9

   m=m/2=3
   continue left rotation m/2=1 times on 4
          dumb
             \
              6
            /   \
          4      8
        /  \   /  \
       2    5 7    9
      /\
     1  3

   m=m/2=1
   next left rotation times is m/2=0 stop
  */

  public static TreeNode balanceBST(TreeNode root) {
    TreeNode dumb = new TreeNode(0); //  1 <= Node.val <= 10^5
    dumb.right = root;
    int sum = rightRotationToList(dumb);
    //  m: the number of closet perfect tree nodes number
    //  m = [2^((int)log(n+1)]-1
    int m = (int) Math.pow(2, (int) (Math.log(sum + 1) / Math.log(2))) - 1;
    leftRotationEverySecond(dumb, sum - m); // flat diff nodes times every second nodes

    for (m >>>= 1; m > 0; m >>>= 1) {
      leftRotationEverySecond(dumb, m);
    }
    return dumb.right;
  }

  // and return the number of all nodes
  // at last d.right will be the left most node of the original tree
  static int rightRotationToList(TreeNode dumb) {
    int sum = 0;
    TreeNode n = dumb.right;
    while (n != null) { // when n == null then get the total number of the nodes 'sum'
      if (n.left != null) {
        TreeNode p = n; // pointer to n
        n = n.left;

        p.left = n.right;
        n.right = p;
        dumb.right = n;
      } else {
        // not change the tree structure, just go through along right sub-tree, so the outer
        // dumb.right will be the left most node of the original tree
        sum++;
        dumb = n;
        n = n.right;
      }
    }
    return sum;
  }

  // left rotation with given times,  performed on every second node of the list
  static void leftRotationEverySecond(TreeNode dumb, int times) {
    TreeNode n = dumb.right; // start from node n
    while (times-- > 0) {
      TreeNode p = n;
      n = n.right;

      dumb.right = n;
      p.right = n.left;
      n.left = p;

      dumb = n;
      n = n.right;
    }
  }

  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    /*
                  cur node4
                 /     \
                left2   right5
               /  \
              ll1   lr3
    */
    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(2);
    root.right = new TreeNode(5);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(3);
    TreeNode dumb = new TreeNode(0); //  1 <= Node.val <= 10^5
    dumb.right = root;
    int sum = rightRotationToList(dumb);
  }

  int toListNoDumb(TreeNode cur) {
    int sum = 0;
    while (cur != null) {
      if (cur.left != null) {
        TreeNode l = cur.left;
        cur.left = l.right;
        l.right = cur;
        cur = l;
      } else {
        sum++;
        cur = cur.right;
      }
    }
    return sum;
  }
}
