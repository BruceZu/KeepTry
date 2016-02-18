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

package datastructure.queue;

import java.util.NoSuchElementException;

public interface FIFOQueue<E> {
    /**
     * @throws IllegalStateException if the element cannot be added at this queue
     * @throws NullPointerException  if the specified element is null
     */
    boolean add(E e);

    /**
     * @return true if the element was added to this queue, else false
     * @throws NullPointerException if the specified element is null
     */
    boolean offer(E e);

    /**
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E remove();

    /**
     * Retrieves and removes the head of this queue,  or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    E poll();

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E element();

    /**
     * @return the head of this queue, or null if this queue is empty
     */
    E peek();

    boolean isEmpty();

    int size();
}
