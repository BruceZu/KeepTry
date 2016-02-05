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

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class SaveInReverseQueueTest {

    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {

        SaveInReverseQueue q = new SaveInReverseQueue();
        q.push(1);
        q.push(2);

        LinkedList mockAboveQueue = new LinkedList();
        mockAboveQueue.push(1);
        mockAboveQueue.push(2);

        Assert.assertEquals(q.toString(), mockAboveQueue.toString());

        Queue queue = new LinkedList();
        queue.add(1);
        queue.add(2);
        Assert.assertNotEquals(q.toString(), queue.toString());
    }
}
