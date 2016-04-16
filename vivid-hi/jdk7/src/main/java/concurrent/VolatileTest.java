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

package concurrent;

public class VolatileTest {
    //  http://tutorials.jenkov.com/java-concurrency/volatile.html

    // concurrent programming. three categories: atomicity, visibility, or ordering.
    // volatile only guarantee the visibility, and kind of ordering, but not sure the atomic.

    volatile int v = 0;
    volatile int a = 0;

    public void increase() {
        v++; // not atomic operation
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
