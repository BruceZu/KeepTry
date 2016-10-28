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

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import static array.KthMostFrequentNumber.NTimes.getFrequent;

public class KthMostFrequentNumber3 {
    /**
     * heap <pre> (Min Heap )
     * a href="https://en.wikipedia.org/wiki/Binary_heap">binary heap</a>
     *     Insert	O(1)	    O(log n)
     *    Delete	O(log n)	O(log n)
     */
    static class FixSizedHeap extends PriorityQueue {
        private int capacity;

        public FixSizedHeap(int capacity, Comparator smallerFirst) {
            super(capacity, smallerFirst);
            this.capacity = capacity;
        }

        @Override
        public boolean offer(Object o) {
            int s = size();
            if (s < capacity) {
                return super.offer(o);
            }
            if (s == capacity && comparator().compare(o, peek()) > 0) {
                poll();
                return super.offer(o);
            }
            return false;
        }
    }

    //O(nlgk) with O(n) space
    public static Integer usingFixSizeHeap(int[] arr, int kth, Comparator smallerFirst) {
        Map<Integer, NTimes> map  = getFrequent(arr);

        Queue<NTimes> queue = new FixSizedHeap(kth, smallerFirst);
        Collection<NTimes> nts = map.values();
        for (NTimes nt : nts) {
            queue.offer(nt);
        }
        return queue.size() < kth ? null : queue.peek().v;
    }
}
