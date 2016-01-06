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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentHashMapTest {
    private Map<String, AtomicLong> accounts = new ConcurrentHashMap();

    public ConcurrentHashMapTest() {
        accounts.put("account1", new AtomicLong(0l));
        accounts.put("account2", new AtomicLong(0l));
        accounts.put("account3", new AtomicLong(0l));
    }

    public void increaseIt() {
        for (int i = 0; i < 50; i++) {
            for (String acount : accounts.keySet()) {
                //accounts.get(acount).getAndIncrement();
                accounts.put(acount, new AtomicLong(accounts.get(acount).longValue()+1));
            }
        }
    }

    public void test() {
        ExecutorService tpool = Executors.newFixedThreadPool(2);
        tpool.submit(new Runnable() {
            @Override
            public void run() {
                increaseIt();
            }
        });
        tpool.submit(new Runnable() {
            @Override
            public void run() {
                increaseIt();
            }
        });
        try {
            tpool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tpool.shutdown();
        System.out.println(accounts);

    }

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (i < 10) {
            new ConcurrentHashMapTest().test();
            i++;
        }
    }
}
