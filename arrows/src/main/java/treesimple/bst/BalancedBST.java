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

package treesimple.bst;

public class BalancedBST {
    static class Node {
        int v;
        Node l;
        Node r;

        public Node(int v) {
            this.v = v;
        }
    }

    /**
     * @param arr sorted array
     * @return head of balanced search tree
     */
    public static Node bstOf(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        if (arr.length == 1) {
            return new Node(arr[0]);
        }

        return recurse(arr, 0, arr.length - 1);
    }

    private static Node recurse(int[] arr, int from, int to) {
        if (from > to) {
            return null;
        }
        int headIndex = (from + to + 1) / 2;
        Node head = new Node(arr[headIndex]);
        head.l = recurse(arr, from, headIndex - 1);
        head.r = recurse(arr, headIndex + 1, to);
        return head;
    }
}
