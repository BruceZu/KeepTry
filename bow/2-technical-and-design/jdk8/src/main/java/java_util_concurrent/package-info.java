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

/**
 * <pre>
 *
 * What is deadlock
 *
 * how to detect it?
 *
 * how to avoid it
 *
 * Starvation and livelock
 *  how to avoid starvation:
 *  if you are using ReentrantLock, create it using parameter fair policy: 'new ReentrantLock(true)'
 * == Thread vs Process
 * http://beginnersbook.com/2015/01/what-is-the-difference-between-a-process-and-a-thread-in-java/
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/procthread.html
 * {@link process.HookTask }
 * A thread can communicate with other thread (of the same process) directly by using methods
 * like wait(), notify(), notifyAll().
 * A process can communicate with other process by using inter-process communication.
 *
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html">Thread </a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/starvelive.html">Starvation and livelock</a>
 *
 *
 * ConcurrentHashMap: arbitrary number of reader threads and  limited number of writer
 * LinkedBlocingQueue: can optionaly  be fixed in size
 *
 * ArrayBlockingQueue: consumers/producers relations
 *
 * ConcurentLinkedDeque:
 * ConcurentLinkedQueue:  can grow dynamically
 *
 * ConcurrentSkipListMap: sorted by key
 * ConcurrentSkipListSet: sorted
 *
 * CopyOnWriteArrayList:
 * DelayQueue  variable-size queue containing Delayed object, a object can be removed only after its delay has expired
 *
 * LinkedBlockingDeque: double-ended, can be optionaly be fixed in size.
 *
 * LinkedTransferQueue:  interface TransferQueue.  each producer has a option of waiting for a consumer to take an
 *                       element being inserted (via method transfer) or simply placing the element into the queue( via
 *                       the method put). Also provides overloaded method tryTransfer to immedietly transfer an element
 *                       to a waiting consumer or to do so within a specified timeout period.  if the transfer cannot
 *                       be completed, the element is not placed in the queue.
 *                       used in applications that pass messages between threads.
 *
 * ProorityBlockingQueue:  variable-length priority-based blocking queue like a PriorityQueue
 *
 * SynchronousQueue: have no internal capacity. must wait each other to insert or remove.
 *
 *
 */
package java_util_concurrent;