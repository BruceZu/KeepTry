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
package list;

// C-7.44 Describe a method for performing a card shuffle of a list of 2n elements, by
// converting it into two lists. A card shuffle is a permutation where a list L is cut
// into two lists, L1 and L2, where L1 is the first half of L and L2 is the second half
// of L, and then these two lists are merged into one by taking the first element in
// L1, then the first element in L2, followed by the second element in L1, the second
// element in L2, and so on.
class Node<T> {
    T e;
    Node next;

    public Node(T e, Node next) {
        this.e = e;
        this.next = next;
    }
}

public class ShuffleList {
    public static Node shuffle(Node l, Node r) {
        if (l == null && r == null) {
            return null;
        }
        if (l == null) {
            return r;
        }
        // above is only init logic
        if (r == null) {
            return l;
        }

        l.next = shuffle(r, l.next);
        return l;
    }

    public static void toString(Node list, StringBuilder sb) {
        if (list != null) {
            toString(list.next, sb.append(list.e.toString()));
        }
    }
}
