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
 * http://stackoverflow.com/questions/5374455/what-does-java-option-xmx-stand-for
 * <pre>
 *
 * java-memory-model
 * <a href="http://tutorials.jenkov.com/java-concurrency/java-memory-model.html">Java Memory Model</a>
 * Only local variables are stored on the thread stack.
 *
 * On the hardware, both the thread stack and the heap are located in main memory.
 * Parts of the thread stacks and heap may sometimes be present in CPU caches and
 * in internal CPU registers.
 *
 * When objects and variables can be stored in various different memory areas in the computer,
 * certain problems may occur. The two main problems are:
 *
 * 1 Visibility of thread updates (writes) to shared variables.
 *
 * To solve this problem you can use Java's volatile keyword.
 * The volatile keyword can make sure that a given variable is read directly from main memory,
 * and always written back to main memory when updated.
 *
 * 2 Race conditions when reading, checking and writing shared variables.
 *
 * use a Java synchronized block. A synchronized block guarantees that only one thread can
 * enter a given critical section of the code at any given time.
 * Synchronized blocks also guarantee that all variables accessed inside the
 * synchronized block will be read in from main memory, and when the thread exits
 * the synchronized block, all updated variables will be flushed back to main memory again,
 * regardless of whether the variable is declared volatile or not.
 *
 *
 * If your computer contains more than one CPU, each thread may run on a different CPU.
 * That means, that each thread may copy the variables into the CPU cache of different CPUs.
 *
 * Volatile variables store in Main Memory.
 * if one thread modified this value then other thread need to get this value from main memory
 * not in Java memory model(Stack and heap).
 *
 * In some cases simply declaring a variable volatile may be enough to assure that multiple
 * threads accessing the variable see the latest written value.
 *
 * In the situation with the two threads reading and writing the same variable,
 * simply declaring the variable volatile is not enough.
 * Thread 1 may read the counter value 0 into a CPU register in CPU 1. At the same time
 * Thread 2 may read the counter value 0 into a CPU register in CPU 2.
 * Both threads have read the value directly from main memory.
 * Now both variables increase the value and writes the value back to main memory.
 * They both increment their register version of counter to 1,
 * and both write the value 1 back to main memory.
 *
 * The problem with multiple threads that do not see the latest value of a variable because
 * that value has not yet been written back to main memory by another thread,
 * is called a "visibility" problem.
 * The updates of one thread are not visible to other threads.
 *
 *
 *
 *
 *
 */
package JVM;
