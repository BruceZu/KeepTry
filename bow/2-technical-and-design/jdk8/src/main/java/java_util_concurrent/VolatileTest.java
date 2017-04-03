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

package java_util_concurrent;

/**
 * volatile keyword tells JVM and Threads that don’t cache the value, always read from main memory.
 * volatile variable is visible across all threads. All threads will always gets updated values.
 *
 * "Nonatomic 64-bit operations."  in Java 64-bit, long and double values were treated as two 32-bit values.
 * That means, a 64-bit write operation is basically performed as two separate 32-bit operations.
 * This behavior can result in indeterminate values being read in code and that lacks atomicity.
 * <pre>
 * According to the Java Language Specification (JSL-17.7)
 * <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.7">SL-17.7)</a>
 *
 * For the purposes of the Java programming language memory model,
 * a single write to a non-volatile long or double value is treated as two separate writes:
 * one to each 32-bit half. This can result in a situation where a thread sees
 * the first 32 bits of a 64-bit value from one write, and the second 32 bits from another write.
 *
 * Writes and reads of volatile long and double values are always atomic.
 *
 * Writes to and reads of references are always atomic,
 * regardless of whether they are implemented as 32-bit or 64-bit values.
 *
 * Some implementations may find it convenient to divide a single write action on a 64-bit long or double value
 * into two write actions on adjacent 32-bit values.
 * For efficiency’s sake, this behavior is implementation-specific; an implementation of the Java Virtual Machine
 * is free to perform writes to long and double values atomically or in two parts.
 *
 * Implementations of the Java Virtual Machine are encouraged to avoid splitting 64-bit values where possible.
 * Programmers are encouraged to declare shared 64-bit values as volatile or synchronize their programs correctly
 * to avoid possible complications.
 *
 * So point is, in Java, long and double aren’t thread safe. When multiple threads are going to access a long or
 * a double value without synchronization, it can cause problems.
 *
 * To ensure atomic/thread safety, it is essential to use volatile to ensure changes made by one thread are visible
 * to other threads. That’s how we can ensure the read/write is atomic and thread safe.
 *
 * However, if all reads and writes of 64-bit long and double values occur within a synchronized block,
 * the atomicity of the read/write is guaranteed.
 */
public class VolatileTest {
    //  http://tutorials.jenkov.com/java-concurrency/volatile.html

    // concurrent programming. three categories: atomicity, visibility, or ordering.
    // volatile only guarantee the visibility, and kind of ordering, but not sure the atomic.

    volatile int v = 0;
    volatile int a = 0;

    public void increase() {
        v++; // not atomic operation
    }

    public void myName() {
        System.out.println("This is " + VolatileTest.class.getName());
    }

    public synchronized void syncincrease() {
        a++; // with 'syncronized' to make sure atomic operation
    }

    public static void main(String[] args) throws InterruptedException {
        final VolatileTest t = new VolatileTest();
        int testNumber = 1000;
        final Thread[] group = new Thread[testNumber];
        for (int i = 0; i < testNumber; i++) {
            group[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(100l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t.increase();
                    t.syncincrease();
                }
            }, "thread " + i);
            group[i].start();
        }
        System.out.println("v: " + t.v);
        System.out.println("a: " + t.a);

        for (int i = 0; i < testNumber; i++) {
            // System.out.println("wait it done: " + group[i].getName());
            group[i].join();
        }

        for (int i = 0; i < testNumber; i++) {
            if (group[i].isAlive()) {
                System.out.println("strange");
            }
        }
        System.out.println("estimate live threads: " + Thread.activeCount());
        System.out.println("v: " + t.v);
        System.out.println("a: " + t.a);
    }
}
