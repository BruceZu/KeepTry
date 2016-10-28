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

package array.KthMostFrequentNumber;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

import static array.KthMostFrequentNumber.NTimes.getFrequent;

public class KthMostFrequentNumber2 {
    /**
     * ----------------------------------------------------------------------------------------
     * <pre>
     *     <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">red back tree</a>
     *     <a href="http://infotechgems.blogspot.com/2011/11/java-collections-performance-time.html">runtime</a>
     *     Red–black tree
     * 	         Average	Worst case
     * Space	O(n)	    O(n)
     * Search	O(log n) 	O(log n)
     * Insert	O(log n) 	O(log n)
     * Delete	O(log n) 	O(log n)
     *  <strong>
     *      take care to confirm how to process those numbers with same times
     *  </strong>
     */
    //O(nlgk) solution with O(n) space
    static class FixSizeTreeSet extends TreeSet {
        private int capacity;

        public FixSizeTreeSet(int capacity, Comparator smallerFirst /* smallest fist */) {
            super(smallerFirst);
            this.capacity = capacity;
        }

        // true: added, false; not added.
        public boolean add(Object o) {
            int s = size();
            if (s < capacity) {
                return super.add(o);
            }
            if (s == capacity && comparator().compare(o, this.first()) > 0) {
                pollFirst();
                return super.add(o);
            }
            return false;
        }
    }

    public static Integer usingFixSizeTreeSet(int[] arr, int kth, Comparator smallerFirst) {
        Map<Integer, NTimes> map = getFrequent(arr);

        TreeSet<NTimes> s = new FixSizeTreeSet(kth, smallerFirst);
        s.addAll(map.values());
        return s.size() == kth ? s.first().v : null; // if it is full return the top.
    }
}
