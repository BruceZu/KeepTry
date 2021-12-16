//  Copyright 2021 The KeepTry Open Source Project
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

package java_util_concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leetcode1188DesignBoundedBlockingQueue {

  /*
  1188. Design Bounded Blocking Queue

    Implement a thread-safe bounded blocking queue that has the following methods:

        BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.
        void enqueue(int element) Adds an element to the front of the queue.
                                 If the queue is full, the calling thread is blocked until the
                                 queue is no longer full.
        int dequeue() Returns the element at the rear of the queue and removes it.
                      If the queue is empty, the calling thread is blocked until the queue is no longer empty.
        int size() Returns the number of elements currently in the queue.

    Your implementation will be tested using multiple threads at the same time.
    Each thread will either be a producer thread that only makes calls to the enqueue method or
    a consumer thread that only makes calls to the dequeue method.
    The size method will be called after every test case.

    Please do not use built-in implementations of bounded blocking queue
    as this will not be accepted in an interview.

    Input:
    1
    1
    ["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
    [[2],[1],[],[],[0],[2],[3],[4],[]]

    Output:
    [1,0,2,2]

    Explanation:
    Number of producer threads = 1
    Number of consumer threads = 1

    BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.

    queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
    queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
    queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
    queue.enqueue(0);   // The producer thread enqueues 0 to the queue.
                           The consumer thread is unblocked and returns 0 from the queue.
    queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
    queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
    queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
    queue.dequeue();    // The consumer thread returns 2 from the queue.
                           The producer thread is unblocked and enqueues 4 to the queue.
    queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.


    Input:
    3
    4
    ["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
    [[3],[1],[0],[2],[],[],[],[3]]
    Output:
    [1,0,2,1]

    Explanation:
    Number of producer threads = 3
    Number of consumer threads = 4

    BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.

    queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
    queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
    queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
    queue.dequeue();    // Consumer thread C1 calls dequeue.
    queue.dequeue();    // Consumer thread C2 calls dequeue.
    queue.dequeue();    // Consumer thread C3 calls dequeue.
    queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
    queue.size();       // 1 element remaining in the queue.

    Since the number of threads for producer/consumer is greater than 1,
    we do not know how the threads will be scheduled in the operating system,
    even though the input seems to imply the ordering. Therefore, any of
    the output [1,0,2] or [1,2,0] or [0,1,2] or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.

    Constraints:

        1 <= Number of Prdoucers <= 8
        1 <= Number of Consumers <= 8
        1 <= size <= 30
        0 <= element <= 20
        The number of calls to enqueue is greater than or equal to the number of calls to dequeue.
        At most 40 calls will be made to enque, deque, and size.

  */
}

class BoundedBlockingQueue {
  /*
  Actually this is case in Condition class

    1. with one lock: why
       `signal()  precondition: the current thread which is calling this method
                       must hold the lock associated with this Condition
                       Else an exception such as IllegalMonitorStateException will be thrown
       So this requires the `full` and `empty` condition must be created by the same lock.
       that means only one thread of all write threads and read threads can hold the lock at
       any give time point.
       So the `size` need not a AtomicInteger type

   Note:
     - await() is used for Condition, wait() is used for object with inner monitor
     - lock.lock() same effective with `while (!lock.tryLock());`
     - once operation w/r is done. update the size and move the new w/r index =(w/r+1)%length
     -  know the queue it is full or empty by comparing the size with length|0
     -  when wake up from wait double check it is empty or full
  */
  private ReentrantLock lock = new ReentrantLock();
  private Condition full = lock.newCondition();
  private Condition empty = lock.newCondition();

  private int[] q;
  private int /* index or read and write */ r = 0, w = 0, size = 0;

  public BoundedBlockingQueue(int capacity) {
    q = new int[capacity];
  }

  public void enqueue(int element) throws InterruptedException {
    while (!lock.tryLock())
      ;
    try {
      while (size == q.length) { // not if, double check
        full.await(); // not wait().
      }

      q[w++] = element;
      size++;
      w %= q.length; // not w++;
      empty.signal();
    } finally {
      lock.unlock();
    }
  }

  public int dequeue() throws InterruptedException {
    lock.lock();
    try {
      while (size == 0) {
        empty.await();
      }
      int a = q[r];
      r++;
      r %= q.length;
      size--;

      full.signal();
      return a;
    } finally {
      lock.unlock();
    }
  }

  public int size() throws InterruptedException {
    lock.lock();
    try {
      return this.size;
    } finally {
      lock.unlock();
    }
  }
}
