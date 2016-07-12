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

package locked.nosubmmitted;

import java.util.Iterator;
import java.util.List;

/**
 * 281. Zigzag Iterator
 * https://leetcode.com/problems/zigzag-iterator/
 * <p/>
 * Difficulty: Medium <pre>
 * Given two 1d vectors, implement an iterator to return their elements alternately.
 * <p/>
 * For example, given two 1d vectors:
 * <p/>
 * v1 = [1, 2]
 * v2 = [3, 4, 5, 6]
 * By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].
 * <p/>
 * Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?
 * <p/>
 * Clarification for the follow up question - Update (2015-09-18):
 * The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases.
 * If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example,
 * given the following input:
 * <p/>
 * [1,2,3]
 * [4,5,6,7]
 * [8,9]
 * It should return [1,4,8,2,5,9,3,6,7].
 * Hide Company Tags: Google
 * Hide Tags: Design
 * Hide Similar Problems :
 * (M) Binary Search Tree Iterator
 * (M) Flatten 2D Vector
 * (M) Peeking Iterator
 * (M) Flatten Nested List Iterator
 */
public class LC281ZigzagIterator {
    /**
     * Your ZigzagIterator object will be instantiated and called as such:
     * ZigzagIterator i = new ZigzagIterator(v1, v2);
     * while (i.hasNext()) v[f()] = i.next();
     */


    class ZigzagIterator {
        // the fast one does not share code

        // beat 72.86
        private Iterator<Integer> i, j, tmp;

        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            i = v2.iterator();
            j = v1.iterator();
        }

        public int next() {
            if (j.hasNext()) {
                tmp = j;
                j = i;
                i = tmp;
            }
            return i.next();
        }

        public boolean hasNext() {
            return i.hasNext() || j.hasNext();
        }
    }
}
