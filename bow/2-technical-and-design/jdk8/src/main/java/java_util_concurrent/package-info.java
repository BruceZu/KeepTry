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
 */
package java_util_concurrent;