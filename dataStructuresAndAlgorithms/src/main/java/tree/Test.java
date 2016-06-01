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

package tree;

import java.util.Stack;

class Node {
    int v;
    Node left;
    Node right;
    Node parent;

    public Node(int v) {
        v = v;
    }
}

public class Test {
    // http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversal-set-2/
    // BST | preorder traversal | stack (left +; right ----+;)
    // BST  <-> Serialize and Deserialize <-> preorder traversal
    public Node test(int[] arr) {
        // input check
        if (arr == null || arr.length == 0) {
            return null;
        }
        if (arr.length == 1) {
            return new Node(arr[0]);
        }
        // end input check

        Stack<Node> st = new Stack();
        Node root = new Node(arr[0]);
        st.push(root);

        Node pre = null;

        for (int i = 1; i < arr.length; i++) {
            int cv = arr[i]; // current value
            if (st.peek().v > cv) {
                Node newLeft = new Node(cv);
                st.peek().left = newLeft;
                st.push(newLeft);
                continue;
            }
            // todo: when  ==v ???
            while (st.peek().v < cv && !st.empty()) {
                pre = st.pop();
            }
            pre.right = new Node(cv);
            st.push(pre.right);
        }
        return root;
    }
}

// http://www.geeksforgeeks.org/merging-intervals/
// 1 sort firstly;
// 2 using stack
// 3 judge by "overlaps  and ending time of current interval is more than that of stack top, update stack top with the ending
//  time of current interval."
