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

package nosubmmitted;

import java.util.Iterator;
import java.util.List;

/**
 * 251. Flatten 2D Vector
 * https://leetcode.com/problems/flatten-2d-vector/
 * <pre>Difficulty: Medium
 * Implement an iterator to flatten a 2d vector.
 * <p/>
 * For example,
 * Given 2d vector =
 * <p/>
 * [
 * [1,2],
 * [3],
 * [4,5,6]
 * ]
 * By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].
 * <p/>
 * Hint:
 * <p/>
 * How many variables do you need to keep track?
 * Two variables is all you need. Try with x and y.
 * Beware of empty rows. It could be the first few rows.
 * To write correct code, think about the invariant to maintain. What is it?
 * The invariant is x and y must always point to a valid point in the 2d vector.
 * Should you maintain your invariant ahead of time or right when you need it?
 * Not sure? Think about how you would implement hasNext(). Which is more complex?
 * Common logic in two different places should be refactored into a common method.
 * Follow up:
 * As an added challenge, try to code it using only iterators in C++ or iterators in Java.
 * <p/>
 * Hide Company Tags: Google Airbnb Twitter Zenefits
 * Hide Tags: Design
 * Hide Similar Problems
 * (M) Binary Search Tree Iterator
 * (M) Zigzag Iterator
 * (M) Peeking Iterator
 * (M) Flatten Nested List Iterator
 * </pre>
 */
public class LC251Flatten2DVector {

    /**
     * Your Vector2D object will be instantiated and called as such:
     * Vector2D i = new Vector2D(vec2d);
     * while (i.hasNext()) v[f()] = i.next();
     */


    class Vector2D implements Iterator<Integer> {

        /**
         * beat 95%
         */

        private List<List<Integer>> vec2d;

        private int x = -1;
        private int y = 0;

        public Vector2D(List<List<Integer>> vec2d) {
            this.vec2d = vec2d;
            findNext();
        }

        @Override
        public Integer next() {
            // get the current result
            int result = this.vec2d.get(x).get(y);

            // calculate the next position
            if ((this.y + 1) >= this.vec2d.get(x).size()) {
                findNext();
            } else {
                this.y++;
            }

            return result;
        }

        @Override
        public void remove() {

        }

        @Override
        public boolean hasNext() {
            if (this.x >= this.vec2d.size()) {
                return false;
            }

            return true;
        }

        private void findNext() {
            this.x++;
            for (; this.x < this.vec2d.size(); this.x++) {
                if (!this.vec2d.get(this.x).isEmpty()) {
                    this.y = 0;
                    break;
                }
            }
        }
    }

    /**
     * beat 58%
     */
    class Vector2D2 implements Iterator<Integer> {
        private Iterator<List<Integer>> iterL;
        private Iterator<Integer> iter;

        public Vector2D2(List<List<Integer>> vec2d) {
            iterL = vec2d != null ? vec2d.iterator() : null;
            iter = (iterL != null && iterL.hasNext()) ? iterL.next().iterator() : null;
        }

        @Override
        public Integer next() {
            return iter.next();
        }

        @Override
        public void remove() {
        }

        @Override
        public boolean hasNext() {
            if (iterL == null || iter == null) return false;
            while (!iter.hasNext() && iterL.hasNext()) iter = iterL.next().iterator();
            return iter.hasNext();
        }
    }

    /**
     * other idea
     * votes
     476 views
     I first hold the 2D List inside a Iterator of List this allows me to operate on the subsequent list once needed.
     I then remove the first list from the 2D List and store as row which is evaluated when next() & hasNext() are called.
     I then want to ensure row iterator is pointing to a none empty list so i call the getNextRow() method.
     next() and hashNext() are now very simple. next() returns the next element of the current list then ensures row isn't empty.
     hasNext() checks row isn't null and has a next value.
     */
}
