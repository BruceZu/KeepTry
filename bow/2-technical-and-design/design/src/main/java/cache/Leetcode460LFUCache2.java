//  Copyright 2017 The keepTry Open Source Project
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
import java.util.LinkedHashSet;

public class Leetcode460LFUCache2 {
    static class LFUCache {
        static class FreNode {
            public int freq = 0;
            public LinkedHashSet<Integer> keysSet = null;

            public FreNode prev = null, next = null;

            public FreNode(int freq, FreNode prev, FreNode next) {
                this.freq = freq;
                keysSet = new LinkedHashSet();

                this.prev = prev;
                this.next = next;

                prev.next = this;
                next.prev = this;
            }

            private FreNode() { // for sentinel only
                this.freq = -1;
            }
        }

        private FreNode centinelSmallerSide;
        private FreNode centinelBiggerSide;
        private int capacity;

        private HashMap<Integer, Integer> keyToValue = null;
        private HashMap<Integer, FreNode> keyToFreNode = null;

        private void unlink(FreNode node) {
            FreNode pre = node.prev;
            FreNode next = node.next;

            pre.next = next;
            next.prev = pre;

            node.prev = node.next = null;
        }

        private void increaseFreFor(int key) {
            FreNode freqNode = keyToFreNode.get(key);
            if (freqNode.next.freq != freqNode.freq + 1) {
                new FreNode(freqNode.freq + 1, freqNode, freqNode.next);
            }

            freqNode.keysSet.remove(key);
            freqNode.next.keysSet.add(key);

            keyToFreNode.put(key, freqNode.next);

            if (freqNode.keysSet.isEmpty()) {
                unlink(freqNode);
            }
        }

        public LFUCache(int capacity) {
            this.capacity = capacity;

            keyToFreNode = new HashMap();
            centinelSmallerSide = new FreNode();
            centinelBiggerSide = new FreNode();
            centinelSmallerSide.next = centinelBiggerSide;
            centinelBiggerSide.prev = centinelSmallerSide;

            keyToValue = new HashMap();
        }

        public int get(int key) {
            Integer v = keyToValue.get(key);
            if (v != null) {
                increaseFreFor(key);
                return v;
            }
            return -1;
        }

        // remove the least recent one of lest frequency
        // after remove if the preNode's list is empty, unlink this preNode
        private void removeLeastRecentOfLestFrequencyAndUpdateFreqList() {
            int beRemovedKey = 0;
            for (int key : centinelSmallerSide.next.keysSet) {
                beRemovedKey = key;
                break;
            }

            centinelSmallerSide.next.keysSet.remove(beRemovedKey);
            keyToValue.remove(beRemovedKey);
            keyToFreNode.remove(beRemovedKey);

            if (centinelSmallerSide.next.keysSet.isEmpty()) {
                unlink(centinelSmallerSide.next);
            }
        }

        // insert new key-value to preNode with freq=0
        // if the preNode does not exist, create it.
        private void insertToZeroFreqNode(int key, int value) {
            if (centinelSmallerSide.next.freq != 0) {
                new FreNode(0, centinelSmallerSide, centinelSmallerSide.next);
            }
            centinelSmallerSide.next.keysSet.add(key);
            keyToFreNode.put(key, centinelSmallerSide.next);
            keyToValue.put(key, value);
        }

        public void put(int key, int value) {

            if (capacity == 0) {
                return;
            }
            Integer oldV = keyToValue.get(key);
            // update
            if (oldV != null) {
                keyToValue.put(key, value);
                increaseFreFor(key);
                return;
            }

            // add
            //    full then delete one firstly
            if (keyToValue.size() == this.capacity) {
                removeLeastRecentOfLestFrequencyAndUpdateFreqList();
            }
            // add to freNode with frequency =0;
            insertToZeroFreqNode(key, value);
        }
    }
}
