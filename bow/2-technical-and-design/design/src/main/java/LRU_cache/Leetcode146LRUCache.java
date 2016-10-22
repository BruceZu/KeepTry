package LRU_cache;

import java.util.HashMap;
import java.util.InputMismatchException;
//with HashMap and DoubleLinkedList

public class Leetcode146LRUCache {
    class LRUCache {
        private class Node {
            Node prev;
            Node next;
            Integer key;
            Integer value;

            private Node() { // used only for sentinel nodes head and tail

            }

            public Node(int key, int value) { // make sure the input does not have null key and value, the null be used as
                // 'not found' in map
                this.key = key;
                this.value = value;
                this.prev = null;
                this.next = null;
            }
        }

        private void rm_oldest_from_list_map() {
            map.remove(old_left.next.key);

            old_left.next = old_left.next.next;
            old_left.next.prev = old_left;
        }

        private void updateOrder(int key) {
            add_to_list_tail(rmFromOrder(key));
        }

        private Node rmFromOrder(int key) {

            Node current = map.get(key);
            current.prev.next = current.next;
            current.next.prev = current.prev;
            return current;
        }

        private void add_to_list_tail(Node current) {
            current.prev = new_right.prev;
            new_right.prev = current;
            current.prev.next = current;
            current.next = new_right;
        }

        private int capacity;
        private HashMap<Integer, Node> map = new HashMap<Integer, Node>();

        // double linked list
        private Node old_left = new Node();
        private Node new_right = new Node();

        public LRUCache(int capacity) {
            this.capacity = capacity;
            new_right.prev = old_left;
            old_left.next = new_right;
        }

        public Integer get(int key) {
            if (!map.containsKey(key)) {
                return null; // leetcode expected it to be -1, thus the value would never be -1;
            }

            updateOrder(key);
            return map.get(key).value;
        }

        public void set(int key, int value) {
            Integer v = get(key);
            if (get(key) != null) { // leetcode expected it to be -1, thus the value would never be -1;
                map.get(key).value = value;
                return;
            }

            if (map.size() == capacity) {
                rm_oldest_from_list_map();
            }

            Node insert = new Node(key, value);

            map.put(key, insert);
            add_to_list_tail(insert);
        }
    }
}
