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

// mock HashMap, maintenance the size in LRUCache
// others parts are same as Leetcode146LRUCache
public class Leetcode146LRUCache2 {
    class LRUCache {
        private class MockHashMap {
            private class Entry {
                Node searched;
                Entry next;

                Entry(Node n) {
                    this.searched = n;
                }
            }

            // hash
            private int index(int key) {
                return key % bucks.length;
            }

            private Entry[] bucks;

            public MockHashMap(int capacity) {
                bucks = new Entry[capacity];
            }

            public Node get(int key) {
                int index = index(key);
                Entry entry = bucks[index];

                if (entry == null) {
                    return null;
                }

                while (entry != null) {
                    if (entry.searched.key == key) {
                        return entry.searched;
                    }
                    entry = entry.next;
                }
                return null;
            }

            // Assume the added entry is new one.
            public void putNew(int k, Node v) {
                Entry added = new Entry(v);

                int index = index(k);
                Entry n = bucks[index];
                if (n != null) {
                    added.next = n;

                }
                bucks[index] = added;
            }

            // Assume the entry to be removed exists
            public void removeOld(int key) {
                int index = index(key);
                Entry pre = bucks[index];
                if (pre.searched.key == key) {
                    bucks[index] = pre.next;
                    return;
                }
                while (pre.next.searched.key != key) {
                    pre = pre.next;
                }
                pre.next = pre.next.next;
            }
        }

        private int capacity;
        private MockHashMap searchedBy;
        private DoubleList sorted;
        private int size;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            searchedBy = new MockHashMap(capacity);
            sorted = new DoubleList();
            size = 0;
        }

        // get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
        // otherwise return -1.
        public Integer get(int key) {
            Node n = searchedBy.get(key);
            if (n == null) {
                return -1; // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
            }

            sorted.accessed(n);
            return n.value;
        }

        // Set or insert the value if the key is not already present.
        // When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
        public void set(int key, int value) {
            // set
            Integer v = get(key);
            if (get(key) != -1) { // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
                // update existing one
                searchedBy.get(key).value = value; // update from map is enough.
                return;
            }

            // insert
            if (size == capacity) {
                searchedBy.removeOld(sorted.removeOldest().key);
                size--;
            }

            Node newResult = new Node(key, value);
            searchedBy.putNew(key, newResult);
            sorted.addNew(newResult);
            size++;
        }
    }
}
