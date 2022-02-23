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

import java.util.LinkedList;
import java.util.List;

public class TwitterEngagementNumber {

  static class Node {
    public List<Node> children;
    char v;

    public Node(char v) {
      set(v);
      children = new LinkedList<>();
    }

    public void set(char v) {
      this.v = v;
    }

    public List<Node> add(Node n) {
      this.children.add(n);
      return children;
    }
  }
  /*
  https://imgur.com/a/XmK4PV5
  Note
    A-A is not a valid engagement.
    engagement has no overlap.
    A-A-A-B-A: valid engagement is A-B-A.
    you can assume the tree has no loop.
    tree not must a binary tree, each node can have 0-n children.
   */
  /*
  dfs:
  always restore to the original status before step in
  */
  static int sum;

  public static int engagementNum(Node root) {
    sum = 0;
    List<Character> path = new LinkedList<>();
    dfs(root, root.v, path);
    return sum;
  }

  private static void dfs(Node n, char u, List<Character> path) {
    if (n == null) return;
    path.add(n.v); // --

    if (n.v == u) {
      if (isValid(path, u)) {
        // System.out.println(path);
        sum++;
      }
      List<Character> newpPath = new LinkedList<>();
      newpPath.add(u); // ----
      for (Node c : n.children) dfs(c, u, newpPath);
      newpPath.remove(newpPath.size() - 1); // ----
    } else {
      for (Node c : n.children) dfs(c, u, path);
    }

    path.remove(path.size() - 1); // --
  }

  static boolean isValid(List<Character> path, char user) {
    if (path.size() <= 2) return false;
    for (char c : path) {
      if (c != user) return true;
    }
    return false;
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    /* A-B-C-A
      \
       C
        \
         A - D
          \
           C
            \
             A
    */
    Node A1 = new Node('A');
    Node B = new Node('B');
    Node C = new Node('C');
    Node A2 = new Node('A');
    Node C2 = new Node('C');
    Node A3 = new Node('A');
    Node D = new Node('D');
    Node C3 = new Node('C');
    Node A4 = new Node('A');
    A1.add(B);
    B.add(C);
    C.add(A2);

    A1.add(C2);
    C2.add(A3);
    A3.add(C3);
    C3.add(A4);

    A3.add(D);
    System.out.println(engagementNum(A1));

    C2.set('A');
    System.out.println(engagementNum(A1));
  }
}
