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

public class Leetcode138CopyListwithRandomPointer {
    public static RandomListNode copyRandomList(RandomListNode head) {
        return cloneOf(head, new HashMap()); // map: form circle later and keep visited nodes
    }

    private static RandomListNode cloneOf(RandomListNode n, HashMap<RandomListNode, RandomListNode> map) {
        if (n == null) {
            return null;
        }
        if (!map.containsKey(n)) {
            RandomListNode nClone = new RandomListNode(n.label);
            map.put(n, nClone);

            nClone.random = cloneOf(n.random, map);
            nClone.next = cloneOf(n.next, map);
        }
        return map.get(n);
    }
}
