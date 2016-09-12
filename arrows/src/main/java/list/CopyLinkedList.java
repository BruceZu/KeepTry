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
import java.util.Set;

class RandomListNode {
    RandomListNode random;
    RandomListNode next;
    int label;

    RandomListNode(int x) {
        this.label = x;
    }
}

public class CopyLinkedList {

    /**
     * <pre>
     * Given a singly Linked list of nodes, where each node has a random pointer in addition to the next pointer. Write a function to
     * create a copy of this linked list .
     * There is circle case
     * 1 -> 2 -> 3 -> 4
     * |<---|
     * |-------->|
     * <p>
     * 1'->2'->3'->4'
     * |------>|
     * |<--|
     * <p>
     */
    static public RandomListNode copyOf(RandomListNode n) {
        if (n == null) {
            return n;
        }
        RandomListNode head = new RandomListNode(n.label);

        Map<RandomListNode, RandomListNode> cache = new HashMap();
        cache.put(n, head);

        execute(n, n.random, cache, true);
        execute(n, n.next, cache, false);
        return head;
    }

    static private void execute(RandomListNode parent,
                                RandomListNode n,
                                Map<RandomListNode, RandomListNode> cache,
                                boolean isRandomChild) {
        // check input
        if (n == null) {
            return;
        }

        Set<RandomListNode> olds = cache.keySet();
        if (!olds.contains(n)) {
            // create copy
            RandomListNode copy = new RandomListNode(n.label);
            cache.put(n, copy);

            //link the copy to its parent(copy);
            RandomListNode parentCopy = cache.get(parent);
            if (isRandomChild) {
                parentCopy.random = copy;
            } else {
                parentCopy.next = copy;
            }
            //recursive
            execute(n, n.random, cache, true);
            execute(n, n.next, cache, false);
        } else {// do not need create, just link it
            RandomListNode copy = cache.get(n);
            // link the copy to its parent(copy);
            RandomListNode parentCopy = cache.get(parent);
            if (isRandomChild) {
                parentCopy.random = copy;
            } else {
                parentCopy.next = copy;
            }
        }
    }

    public static void print(Node n) {
        // TODO: add print() and test case.
    }

    public static void main(String[] args) {
        //  1 -> 2 -> 3 -> 4
        //  |<---|
        //  |-------->|

        RandomListNode a = new RandomListNode(1);
        RandomListNode b = new RandomListNode(2);
        RandomListNode c = new RandomListNode(3);
        RandomListNode d = new RandomListNode(4);

        a.next = b;
        b.random = c;
        c.next = d;

        a.random = c;
        b.next = a;
        RandomListNode copy = copyOf(a);
        System.out.println(copy); //For Adding Break Point. The main() will be replaced by test case one print() is ready
    }
}
