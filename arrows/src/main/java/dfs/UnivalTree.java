//  Copyright 2022 The KeepTry Open Source Project
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

package dfs;

/*
  Unival tree: tree where all nodes have the same value

      1
    1   1

Unival tree?  true

      1
   2
 1

Unival tree?  false

*/

class Node {
  String name;
  int v;
  Node left, right;

  public Node(int value, String name) {
    this.v = value;
    this.name = name;
  }

  public Node(int value, Node l, Node r, String name) {
    this.v = value;
    this.left = l;
    this.right = r;
    this.name = name;
  }
}

class R {
  public boolean is; // current node related substree is unival tree of not
  public int number; // number of unival trees

  public R(boolean is, int n) {
    this.is = is;
    this.number = n;
  }
}

/*

Unival subtree: subtree where all the children have the same value as the parent
leaf is a Unival subtree

     1
  1     1

 ans: 3

     1
   2   1
     1   1

ans: 4

           a1
     b1          c1
   d2   e1
     f1   g1

 ans: 5 : with root at node g, f, e, d,  c
*/

public class UnivalTree {
  public static int countUnivalSubtrees(Node t) {
    return dfs(t).number;
  }
  // Assume t is not null
  private static R dfs(Node t) {
    if (t == null) return new R(true, 0);
    R l = dfs(t.left), r = dfs(t.right);
    // check children tree and children node value with current node value
    boolean is =
        l.is
            && r.is
            && (t.left == null || t.v == t.left.v)
            && (t.right == null || t.v == t.right.v);
    return new R(is, (is ? 1 : 0) + (l == null ? 0 : l.number) + (r == null ? 0 : r.number));
  }

  public static void main(String[] ps) {
    System.out.println(countUnivalSubtrees(null) == 0);
    /*
       a1
     b1   c1
    answer is 3
     */
    Node b = new Node(1, "b");
    Node c = new Node(1, "c");
    Node a = new Node(1, b, c, "a");
    System.out.println(countUnivalSubtrees(a) == 3);

    /*
        a1
      b2
    c1
    answer is 1
    */
    b = new Node(2, c, null, "b");
    a = new Node(1, b, null, "a");
    System.out.println(countUnivalSubtrees(a) == 1);
    /*
               a1
       b1             c1
     d2   e1
       f1   g1

    ans: 5 : g, f, e, d,  c
    */

    Node g = new Node(1, "g");
    Node f = new Node(1, "f");
    Node e = new Node(1, f, g, "e");

    Node d = new Node(2, "d");
    b = new Node(1, d, e, "b");
    a = new Node(1, b, c, "a");

    System.out.println(countUnivalSubtrees(b) == 4); //  answer is 4
    System.out.println(countUnivalSubtrees(a) == 5); //  answer is 5
  }
}
