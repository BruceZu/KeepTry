//  Copyright 2016 The Minorminor Open Source Project
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

package jdktest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// HashSet implementation is not synchronized. If multiple threads access a hash map concurrently, and at least one of the threads
// modifies the map structurally, it must be synchronized externally.
public class HashSetTest {
    private Set<Integer> accounts = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>(10000));
    // Collections.synchronizedSet(new HashSet<Integer>(10000));

    public void addElement(CountDownLatch startRun, CountDownLatch done, int init) {
        try {
            startRun.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = init; i < init + 5000; i++) {
            // synchronized (accounts) {
            accounts.add(i);
            // }
        }
        done.countDown();
    }

    public int test() {
        final CountDownLatch startRun = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(2);
        ExecutorService executors = Executors.newFixedThreadPool(2);
        executors.submit(new Runnable() {
            @Override
            public void run() {
                addElement(startRun, done, 0);
            }
        });
        executors.submit(new Runnable() {
            @Override
            public void run() {
                addElement(startRun, done, 5000);
            }
        });

        startRun.countDown();

        try {
            done.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executors.shutdown();
        return accounts.size();
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testHashSetTest() {
        int i = 0;
        while (i < 10) {
            Assert.assertEquals(new HashSetTest().test(), 10000);
            i++;
        }
    }
}
