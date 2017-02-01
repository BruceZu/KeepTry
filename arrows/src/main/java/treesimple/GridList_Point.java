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
 * <pre>
 * www.hackerrank.com test used by TrialPay a Visa company
 *
 *
 * Number of layers, followed by a list of new nodes for each layer and finally the number that should
 * be found.
 * 4
 *
 * 8
 * 28 52
 * 25 81
 * 5  33 55 70 83
 *
 * 55
 *
 * All of the values are integers, larger than 0.
 * Values for each layer are guaranteed to be in ascending order and be integers, larger than 0.
 *
 * H --------> 8 ------------------------------------------------------------> null
 * |           |
 * H --------> 8 ----------> 28 ---------> 52 -------------------------------> null
 * |           |             |             |
 * H --------> 8 --> 25  --> 28 ---------> 52 ---------------->  81----------> NULL
 * |           |     |       |             |                     |
 * H ----> 5-> 8 --> 25  --> 28 --> 33 --> 52 --> 55 --> 70 -->  81 --> 83 --> NULL
 *
 * Grid List :
 *   go from home toward right or down side.
 *   value of each layer is ascending
 *   each node's below points to the node on the blow layer with the same value
 * Benefit:
 *   search quickly
 */
public class GridList_Point {

    // Grid List Node
    static class Node {
        int value; // what is the value for home node
        Node next;
        Node below;
    }

    static int hops = 0;

    // from the start node to search the node with the value
    // return the minimal number of hops that are needed in order to either reach the node with
    // that valuer or to determine that it does not exist in the data structure
    //
    static int searchNodes(Node start, int value) {
        // check start node
        // - start node will not reach to the node with the specific value
        // - the node with the value does not exist.
        // - Note this could not be a jump result.
        if (start == null || start.value >= value) {
            return hops; // 0
        }
        // need hop:
        // right first, check right node
        Node nextNode = start.next;
        if (nextNode != null && nextNode.value <= value) {
            hops++; // hop
            return searchNodes(nextNode, value);
        }

        Node belowNode = start.below;
        if (belowNode != null) {
            hops++;
            return searchNodes(belowNode, value);
        }
        return hops;
    }
}
