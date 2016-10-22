package LRU_cache;

// mock HashMap, maintenance the size in LRUCache
// others parts are same as Leetcode146LRUCache
public class Leetcode146LRUCache2 {
    class LRUCache {
        private class MockHashMap {
            private class Entry {
                Node n;
                Entry next;

                Entry(Node n) {
                    this.n = n;
                }
            }

            private int index(int key) {
                return key % bucks.length;
            }

            Entry[] bucks;

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
                    if (entry.n.key == key) {
                        return entry.n;
                    }
                    entry = entry.next;
                }
                return null;
            }

            // Assume the added entry is new one.
            public void put(int k, Node v) {
                Entry added = new Entry(v);

                int index = index(k);
                Entry n = bucks[index];
                if (n != null) {
                    added.next = n;

                }
                bucks[index] = added;
            }

            // Assume the entry to be removed exists
            public void remove(int key) {
                int index = index(key);
                Entry pre = bucks[index];
                if (pre.n.key == key) {
                    bucks[index] = pre.next;
                    return;
                }
                while (pre.next.n.key != key) {
                    pre = pre.next;
                }
                pre.next = pre.next.next;
            }
        }

        private int capacity;
        private MockHashMap map;
        private Order order;
        private int size;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            map = new MockHashMap(capacity);
            order = new Order();
            size = 0;
        }

        // get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
        // otherwise return -1.
        public Integer get(int key) {
            Node n = map.get(key);
            if (n == null) {
                return -1; // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
            }

            order.access(n);
            return n.value;
        }

        // Set or insert the value if the key is not already present.
        // When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
        public void set(int key, int value) {
            // set
            Integer v = get(key);
            if (get(key) != -1) { // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
                map.get(key).value = value;
                return;
            }

            // insert
            if (size == capacity) {
                map.remove(order.rm_oldest().key);
                size--;
            }

            Node insert = new Node(key, value);
            map.put(key, insert);
            order.addNew(insert);
            size++;
        }
    }
}
