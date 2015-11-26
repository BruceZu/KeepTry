// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class MyLinkedListEqualsTest {
    private MyLinkedList test;

    @Parameterized.Parameters
    public static Iterable<MyLinkedList> data() {
        MyLinkedList doublyLinkedList2 = new DoublyLinkedList2();
        doublyLinkedList2.add("end");
        doublyLinkedList2.add(2);
        doublyLinkedList2.add(1);

        return Arrays.asList(new MyLinkedList[]{doublyLinkedList2});
    }

    public MyLinkedListEqualsTest(MyLinkedList test) {
        this.test = test;
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void equalsTest() {
        Assert.assertFalse(test.equals(null));
        Assert.assertFalse(test.equals(new SinglyLinkedList<String>()));

        Assert.assertTrue(test.equals(test));
        MyLinkedList clone = test.clone();
        Assert.assertTrue(test.equals(clone));
        Assert.assertTrue(clone.equals(test));


        clone.updateHead("1");
        Assert.assertNotEquals(test, clone);

        clone.updateHead(1);
        Assert.assertEquals(test, clone);
        clone.add("head");
        Assert.assertNotEquals(test, clone);
    }
}
