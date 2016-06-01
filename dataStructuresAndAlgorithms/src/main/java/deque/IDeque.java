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

package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface IDeque<T> {
    /**
     * @throws NullPointerException  if the specified element is null and this
     *                               deque does not permit null elements
     * @throws IllegalStateException if the element cannot be added at this queue
     */
    void addFirst(T e);

    /**
     * @throws NullPointerException  if the specified element is null and this
     *                               deque does not permit null elements
     * @throws IllegalStateException if the element cannot be added at this queue
     */
    void addLast(T e);

    /**
     * @throws NullPointerException     if the specified element is null and this
     *                                  deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it from being added to this deque
     */
    boolean offerFirst(T e);

    /**
     * prevents it from being added to this deque
     *
     * @throws NullPointerException     if the specified element is null and this
     *                                  deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it from being added to this deque
     */
    boolean offerLast(T e);

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    T removeFirst();

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    T removeLast();

    /**
     * @return the head of this deque, or null   if this deque is empty
     */
    T pollFirst();

    /**
     * @return the tail of this deque, or null   if this deque is empty
     */
    T pollLast();

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    T getFirst();

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    T getLast();

    /**
     * @return the head of this deque, or null   if this deque is empty
     */
    T peekFirst();

    /**
     * @return the tail of this deque, or null   if this deque is empty
     */
    T peekLast();

    /**
     * Removes the first element e such that
     * (o==null ? e==null : o.equals(e))
     * (if such an element exists).
     *
     * @throws NullPointerException if the specified element is null and this
     *                              deque does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * Removes the last element e such that
     * (o==null ? e==null : o.equals(e))
     * (if such an element exists).
     *
     * @throws NullPointerException if the specified element is null and this
     *                              deque does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeLastOccurrence(Object o);

    /**
     * @return the number of elements in this deque
     */
    public int size();

    public boolean isEmpty();

    /**
     * @return an iterator over the elements in this deque in order from first (head) to last (tail)
     */
    Iterator<T> iterator();

    /**
     * @return an iterator over the elements in this deque in reverse
     * sequence
     */
    Iterator<T> descendingIterator();
}
