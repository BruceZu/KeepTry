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

import java.util.HashMap;
import java.util.Map;

class RandomListNode {
    int label;
    RandomListNode random;
    RandomListNode next;

    RandomListNode(int x) {
        this.label = x;
    }
}

/**
 * <img src="../../resources/graph_list_have_circle_clone.png" height="400" width="600">
 * <a href="https://leetcode.com/problems/copy-list-with-random-pointer/">leetcode</a>
 */
public class Leetcode138CopyListwithRandomPointer3 {
    static public RandomListNode copyRandomList(RandomListNode n) {
        if (n == null) {
            return n;
        }
        RandomListNode nClone = new RandomListNode(n.label);

        Map<RandomListNode, RandomListNode> map = new HashMap(); // form circle later and keep visited nodes
        map.put(n, nClone);

        cloneChild(n, n.random, map);
        cloneChild(n, n.next, map);
        return nClone;
    }

    static private void cloneChild(RandomListNode parent, RandomListNode n, Map<RandomListNode, RandomListNode> map) {
        // check input
        if (n == null) {
            return;
        }
        RandomListNode pClone = map.get(parent);
        if (!map.containsKey(n)) {
            // need clone
            RandomListNode nClone = new RandomListNode(n.label);
            map.put(n, nClone);

            if (n == parent.random) {
                pClone.random = nClone;
            } else {
                pClone.next = nClone;
            }

            cloneChild(n, n.random, map);
            cloneChild(n, n.next, map);
        } else {
            // form the circle
            RandomListNode nClone = map.get(n);
            if (n == parent.random) {
                pClone.random = nClone;
            } else {
                pClone.next = nClone;
            }
        }
    }

    /*-------------------------------------------------------------------------------*/
    public static void print(Node n) {
        // TODO: add print() and test case.
    }

    public static void main(String[] args) {
        //  1 -> 2 -> 3 -> 4
        //  |<---|
        //  |-------->|

        RandomListNode one = new RandomListNode(1);
        RandomListNode two = new RandomListNode(2);
        RandomListNode three = new RandomListNode(3);
        RandomListNode four = new RandomListNode(4);

        one.next = two;
        one.random = three;

        two.next = one;
        two.random = three;

        three.next = four;

        RandomListNode copy = copyRandomList(one);
        System.out.println(copy); // For Adding Break Point. The main() will be replaced by test case one print() is ready
    }
}
