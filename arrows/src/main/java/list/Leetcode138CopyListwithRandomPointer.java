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
 * <img src="../../resources/graph_list_have_circle_clone.png" height="400" width="600"><br>
 * <img src="../../resources/graph_list_have_circle_clone2.png" height="250" width="600">
 * <a href="https://leetcode.com/problems/copy-list-with-random-pointer/">leetcode</a>
 */
public class Leetcode138CopyListwithRandomPointer {
    public static RandomListNode copyRandomList(RandomListNode head) {
        return cloneOf(head, new HashMap()); // map: form circle later and keep visited nodes
    }

    private static RandomListNode cloneOf(RandomListNode node, HashMap<RandomListNode, RandomListNode> nodeToClone) {
        if (node == null) {
            return null;
        }
        if (!nodeToClone.containsKey(node)) {
            RandomListNode nClone = new RandomListNode(node.label);
            nodeToClone.put(node, nClone);

            nClone.random = cloneOf(node.random, nodeToClone);
            nClone.next = cloneOf(node.next, nodeToClone);
        }
        return nodeToClone.get(node);
    }

    /*--------------------------Test-----------------------------------------------------*/
    public static void main(String[] args) {
        test();
        test1();
    }

    private static void test() {
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

    private static void test1() {

        //            |--->|
        //            <--
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
        three.random = three;

        RandomListNode copy = copyRandomList(one);
        System.out.println(copy); // For Adding Break Point. The main() will be replaced by test case one print() is ready

    }
}
