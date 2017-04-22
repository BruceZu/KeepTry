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

package cache;

import java.util.HashMap;


/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and set.
 * <a href="https://leetcode.com/problems/lru-cache/">leetcode</a>
 */

class Node {
    Node prev;
    Node next;
    Integer key;
    Integer value;

    Node() { // used only for sentinel sortedSearched head and tail

    }

    public Node(int key, int value) { // make sure the input does not have null key and value, the null be used as
        // 'not found' in searchedBy
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}

class DoubleList {
    // double linked list, with it to know which should be removed
    private Node olderSentinelLeft;
    private Node recentSentinelRight;

    private void remove(Node n) {
        Node pre = n.prev;
        Node next = n.next;

        pre.next = next;
        next.prev = pre;
    }

    public DoubleList() {
        olderSentinelLeft = new Node();
        recentSentinelRight = new Node();

        olderSentinelLeft.next = recentSentinelRight;
        recentSentinelRight.prev = olderSentinelLeft;
    }

    Node removeOldest() {
        Node removed = olderSentinelLeft.next;
        remove(removed);
        return removed;
    }

    void addNew(Node newResult) {
        Node pre = recentSentinelRight.prev;
        Node next = recentSentinelRight;

        pre.next = newResult;
        newResult.next = next;

        next.prev = newResult;
        newResult.prev = pre;
    }

    void accessed(Node n) {
        remove(n);
        addNew(n);
    }
}

// with HashMap and DoubleLinkedList
class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private DoubleList list;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap();
        list = new DoubleList();
    }

    // get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
    // otherwise return -1.
    public Integer get(int key) {
        Node n = map.get(key);
        if (n == null) {
            return -1; // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
        }

        list.accessed(n);
        return n.value;
    }

    // set or insert the value if the key is not already present.
    // When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
    public void set(int key, int value) {
        // set
        Integer v = get(key);
        if (get(key) != -1) { // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
            map.get(key).value = value;
            return;
        }

        // insert
        if (map.size() == capacity) {
            map.remove(list.removeOldest().key);
        }

        Node newNode = new Node(key, value);
        map.put(key, newNode);
        list.addNew(newNode);
    }
}

// -------------------------------------------------------------------------------------
public class Leetcode146LRUCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.get(1);
        cache.set(3, 2);
    }
}
