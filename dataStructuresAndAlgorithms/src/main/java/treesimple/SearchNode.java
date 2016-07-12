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

package treesimple;

/**
 * www.hackerrank.com test used by TrialPay a Visa company
 */
public class SearchNode {
    /**
     * Number of layers, followed by a list of new nodes for each layer and finally the number that should
     * be found. All of the values are integers, larger than 0. Values for each layer are guaranteed to be
     * in ascending order.
     * 4
     * 8
     * 28 52
     * 25 81
     * 5  33 55 70 83
     * 55
     * <p>
     * H --------> 8 ------------------------------------------------------------> null
     * |
     * H --------> 8 ----------> 28 ---------> 52 -------------------------------> null
     * |           |             |             |
     * H --------> 8 --> 25  --> 28 ---------> 52 ---------------->  81----------> NULL
     * |           |     |       |             |                     |
     * H ----> 5-> 8 --> 25  --> 28 --> 33 --> 52 --> 55 --> 70 -->  81 --> 83 --> NULL
     */
    static class Node {
        int value;
        Node next;
        Node below;
    }

    static int hops = 0;

    static int searchNodes(Node root, int value) {
        if (root == null || root.value >= value) {
            return hops;
        }

        Node next = root.next;
        if (next != null && next.value <= value) {
            hops++; // hop
            return searchNodes(root.next, value);
        }

        if (root.below != null) {
            hops++;
            return searchNodes(root.below, value);
        }
        return hops;
    }
}
