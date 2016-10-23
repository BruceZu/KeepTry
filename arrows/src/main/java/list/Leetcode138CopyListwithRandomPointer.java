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
