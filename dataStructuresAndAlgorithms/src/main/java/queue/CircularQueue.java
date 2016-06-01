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

package queue;

public interface CircularQueue<E> extends FIFOQueue<E> {

    /**
     * Rotates the head element of the queue to the back of the queue.
     * This does nothing if the queue is empty.
     *
     * For
     * -- multiplayer
     * -- turn-based games
     * -- round-robin scheduling of computing processes.
     */
    void rotate();
}
