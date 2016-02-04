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
package map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentHashMapTest {
    private Map<String, AtomicLong> accounts = new ConcurrentHashMap(6);

    public ConcurrentHashMapTest() {
        accounts.put("account1", new AtomicLong(0l));
        accounts.put("account2", new AtomicLong(0l));
        accounts.put("account3", new AtomicLong(0l));
    }

    public void increaseIt(CountDownLatch start, CountDownLatch done) {
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 50; i++) {
            for (String acount : accounts.keySet()) {
                //accounts.get(acount).getAndIncrement();
                synchronized (acount) {
                    accounts.put(acount, new AtomicLong(accounts.get(acount).longValue() + 1));
                }
            }
        }
        done.countDown();
    }

    public String test() {
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(2);
        ExecutorService tpool = Executors.newFixedThreadPool(2);
        tpool.submit(new Runnable() {
            @Override
            public void run() {
                increaseIt(start, done);
            }
        });
        tpool.submit(new Runnable() {
            @Override
            public void run() {
                increaseIt(start, done);
            }
        });

        start.countDown();
        try {
            done.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tpool.shutdown();
        return accounts.toString();
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testConcurrentHashMap() {
        int i = 0;
        while (i < 10) {
            Assert.assertEquals(new ConcurrentHashMapTest().test(), "{account3=100, account1=100, account2=100}");
            i++;
        }
    }
}
