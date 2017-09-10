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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SleepSortTest {
    @Test(timeout = 300L, expected = Test.None.class)
    public void testConcurrentHashMap() {
        List<Integer> r = SleepSort.sleepSort(Arrays.asList(14, 6, 24, 18, 29, 1, 31, 90));
        Assert.assertArrayEquals(new Integer[] {1, 6, 14, 18, 24, 29, 31, 90}, r.toArray());
    }
}
