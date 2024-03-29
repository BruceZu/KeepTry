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

package java_util_concurrent.countDownLatchCyclicBarrierSemaphore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class SleepSort {
  public static void main(String[] args) {
    sleepSort(Arrays.asList(3, 2, 1, 5, 8, 3, 2));
  }
  // non-negative integers
  public static List<Integer> sleepSort(List<Integer> num) {
    final List<Integer> sortedResult = Collections.synchronizedList(new ArrayList());
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch finish = new CountDownLatch(num.size());

    CyclicBarrier barrier =
        new CyclicBarrier(
            num.size(),
            () -> {
              System.out.println("Done");
              System.out.println(Arrays.toString(sortedResult.toArray()));
            });
    for (Integer n : num) {
      new Thread(
              new Runnable() {
                private CountDownLatch start;
                private CountDownLatch finish;
                private CyclicBarrier barrier;
                private Integer n;

                public Runnable set(
                    Integer n, CountDownLatch start, CountDownLatch finish, CyclicBarrier barrier) {
                  this.n = n;
                  this.start = start;
                  this.finish = finish;
                  this.barrier = barrier;
                  return this;
                }

                @Override
                public void run() {
                  try {
                    start.await();
                    Thread.currentThread().sleep(n);
                    sortedResult.add(n);
                    finish.countDown();
                    barrier.await();
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }.set(n, start, finish, barrier))
          .start();
    }
    start.countDown();
    try {
      finish.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return sortedResult;
  }
}
