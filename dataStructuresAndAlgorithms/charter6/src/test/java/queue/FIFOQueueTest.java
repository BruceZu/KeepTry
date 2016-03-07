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

package queue;

import deque.ArrayIDeque;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class FIFOQueueTest {
    @Parameterized.Parameters(name = "test with {index} {0}")
    public static Iterable<FIFOQueue> data() {
        return Arrays.asList(new FIFOQueue[]{ // R-6.11
                new ArrayFIFOQueue<>(),
                new ArrayFIFOQueue2<>(1),
                new ArrayCircularQueue<>(),
                new FIFOQueue() {
                    private ArrayIDeque<Object> deque = new ArrayIDeque();

                    @Override
                    public boolean add(Object o) {
                        deque.addLast(o);
                        return true;
                    }

                    @Override
                    public boolean offer(Object o) {
                        return deque.offerLast(o);
                    }

                    @Override
                    public Object remove() {
                        return deque.removeFirst();
                    }

                    @Override
                    public Object poll() {
                        return deque.pollFirst();
                    }

                    @Override
                    public Object element() {
                        return deque.getFirst();
                    }

                    @Override
                    public Object peek() {
                        return deque.peekFirst();
                    }

                    @Override
                    public boolean isEmpty() {
                        return deque.isEmpty();
                    }

                    @Override
                    public int size() {
                        return deque.size();
                    }

                    public String toString() {
                        return deque.toString();
                    }
                }
        });
    }

    private FIFOQueue<Integer> q;

    public FIFOQueueTest(FIFOQueue<Integer> q) {
        this.q = q;
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        q.offer(5);
        q.offer(3);
        Assert.assertEquals(q.toString(), "[5, 3]");
        Assert.assertEquals(q.size(), 2);
        Assert.assertEquals(q.poll(), 5, 0);
        Assert.assertEquals(q.isEmpty(), false);
        Assert.assertEquals(q.toString(), "[3]");
        Assert.assertEquals(q.poll(), 3, 0);
        Assert.assertEquals(q.isEmpty(), true);
        Assert.assertEquals(q.poll(), null);
        q.offer(7);
        q.offer(9);
        Assert.assertEquals(q.toString(), "[7, 9]");
        Assert.assertEquals(q.peek(), 7, 0);
        q.offer(4);
        Assert.assertEquals(q.size(), 3);
        Assert.assertEquals(q.poll(), 7, 0);
        q.offer(6);
        Assert.assertEquals(q.toString(), "[9, 4, 6]");
        q.offer(8);
        Assert.assertEquals(q.poll(), 9, 0);
        Assert.assertEquals(q.toString(), "[4, 6, 8]");
        if (CircularQueue.class.isInstance(q)) {
            // CircularQueue.class.isAssignableFrom(q.getClass())
            try {
                CircularQueue.class.getMethod("rotate").invoke(q);
                Assert.assertEquals(q.toString(), "[6, 8, 4]");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
