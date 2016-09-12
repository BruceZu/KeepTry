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

public class StartNodeOfCircleInList {
    /**
     * <pre>
     *   Assume the input is a circle liked list.
     *   start node of the circle: start
     *   head of the list: head
     *   first meet node by one-step and 2-step: meet.
     *      If they init from start node, sure they will meet at the start node again
     *      after the one-step finish 1 circle and the 2-step finish 2 circle.
     *
     *   out length: out
     *   circle length: circle
     *   length from meet to start: offset
     *
     *   out % circle = offset
     *
     *                     n - n
     *                    /     \
     *                    n      n
     *                    \     /
     *   h - n - n - n ... s - n
     */
    public static Node startPoint(Node head) {
        Node oneStep = head;
        Node twoStep = head;
        do {
            oneStep = oneStep.next;
            twoStep = twoStep.next.next;

        } while (oneStep != twoStep);

        Node oneStep2 = head;
        do {
            oneStep = oneStep.next;
            oneStep2 = oneStep2.next;

        } while (oneStep != oneStep2);

        return oneStep;
    }
}
