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

package LRU_cache;

import java.util.HashMap;


/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and set.
 * <a href="https://leetcode.com/problems/lru-cache/">leetcode</a>
 */

class SearchedResult {
    SearchedResult prev;
    SearchedResult next;
    Integer key;
    Integer value;

    SearchedResult() { // used only for sentinel sortedSearched head and tail

    }

    public SearchedResult(int key, int value) { // make sure the input does not have null key and value, the null be used as
        // 'not found' in searchedBy
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}

class SortedSearchResults {
    // double linked list, with it to know which should be removed
    private SearchedResult olderSentinel;
    private SearchedResult recentSentinel;

    private void remove(SearchedResult n) {
        SearchedResult pre = n.prev;
        SearchedResult next = n.next;

        pre.next = next;
        next.prev = pre;
    }

    public SortedSearchResults() {
        //  left olderSentinel <->recentSentinel  right
        olderSentinel = new SearchedResult();
        recentSentinel = new SearchedResult();
        olderSentinel.next = recentSentinel;
        recentSentinel.prev = olderSentinel;
    }

    SearchedResult removeOldest() {
        SearchedResult removed = olderSentinel.next;
        remove(removed);
        return removed;
    }

    void addNew(SearchedResult newResult) {
        SearchedResult pre = recentSentinel.prev;
        SearchedResult next = recentSentinel;

        pre.next = newResult;
        newResult.next = next;

        next.prev = newResult;
        newResult.prev = pre;
    }

    void accessed(SearchedResult n) {
        remove(n);
        addNew(n);
    }
}

// with HashMap and DoubleLinkedList
class LRUCache {
    private int capacity;
    private HashMap<Integer, SearchedResult> searchedBy;
    private SortedSearchResults sortedSearched;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        searchedBy = new HashMap();
        sortedSearched = new SortedSearchResults();
    }

    // get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
    // otherwise return -1.
    public Integer get(int key) {
        SearchedResult n = searchedBy.get(key);
        if (n == null) {
            return -1; // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
        }

        sortedSearched.accessed(n);
        return n.value;
    }

    // set or insert the value if the key is not already present.
    // When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
    public void set(int key, int value) {
        // set
        Integer v = get(key);
        if (get(key) != -1) { // it should be null, but leetcode expected it to be -1, thus the value would never be -1;
            searchedBy.get(key).value = value;
            return;
        }

        // insert
        if (searchedBy.size() == capacity) {
            searchedBy.remove(sortedSearched.removeOldest().key);
        }

        SearchedResult newNode = new SearchedResult(key, value);
        searchedBy.put(key, newNode);
        sortedSearched.addNew(newNode);
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
