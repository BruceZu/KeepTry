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

package chart3;

import charter3.CircularlyLinkedList;
import charter3.CircularlyLinkedList2;
import charter3.DoublyLinkedList;
import charter3.DoublyLinkedList2;
import charter3.MyLinkedList;
import charter3.SinglyLinkedList;
import com.google.common.truth.Truth;
import junit.runner.Version;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class MyLinkedListTest {
    private static Logger log = LoggerFactory.getLogger(EntiesTest.class);

    @Parameters(name = "{index}:  concrete linked list: {0} ")
    public static Iterable<MyLinkedList> data() {
        return Arrays.asList(new MyLinkedList[]{new SinglyLinkedList(),
                        new CircularlyLinkedList(),
                        new CircularlyLinkedList2(),
                        new DoublyLinkedList(),
                        new DoublyLinkedList2()
                }
        );
    }

    private MyLinkedList list;

    public MyLinkedListTest(MyLinkedList list) {
        this.list = list;
    }

    @After
    public void teardown() {
        list.clean();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testVersion() {
        org.junit.Assert.assertEquals("4.12", Version.id());
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testAdd() {
        list.addAfter(0, 1);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testDelete() {
        list.delete(0);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testUpdate() {
        list.update(1, 3);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testGetOf() {
        list.get(0);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testEndNodeAfterDeleteHead() {
        Truth.assertThat(list.size()).isEqualTo(0);
        list.add("head");
        Truth.assertThat(list.deleteHead()).isEqualTo("head");
        Truth.assertThat(list.size()).isEqualTo(0);
        Truth.assertThat(list.getEnd()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testHeadNodeAfterDeleteEnd() {
        list.add("end");
        Truth.assertThat(list.deleteEnd().equals("end"));
        Truth.assertThat(list.getHead()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void test() {
        list.add("head");
        Truth.assertThat(list.getHead()).isEqualTo("head");
        Truth.assertThat(list.getEnd()).isEqualTo("head");

        Truth.assertThat(list.deleteHead()).isEqualTo("head");
        list.appendToTheEnd("head");
        Truth.assertThat(list.getHead()).isEqualTo("head");
        Truth.assertThat(list.getEnd()).isEqualTo("head");

        list.appendToTheEnd("end");
        Truth.assertThat(list.getEnd()).isEqualTo("end");
        Truth.assertThat(list.getHead()).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("end");
        Truth.assertThat(list.get(0)).isEqualTo("head");

        list.addAfter("second", 0);
        Truth.assertThat(list.get(0)).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("second");
        Truth.assertThat(list.get(2)).isEqualTo("end");

        list.addBefore("third", 2);
        Truth.assertThat(list.get(0)).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("second");
        Truth.assertThat(list.get(2)).isEqualTo("third");
        Truth.assertThat(list.get(3)).isEqualTo("end");

        Truth.assertThat(list.delete(1)).isEqualTo("second");
        Truth.assertThat(list.delete(0)).isEqualTo("head");
        list.add("head");

        Truth.assertThat(list.delete(2)).isEqualTo("end");
        list.appendToTheEnd("end");

        Truth.assertThat(list.get(0)).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("third");
        Truth.assertThat(list.get(2)).isEqualTo("end");

        Truth.assertThat(list.updateHead("zero")).isEqualTo("head");
        Truth.assertThat(list.updateEnd("last")).isEqualTo("end");

        Truth.assertThat(list.get(0)).isEqualTo("zero");
        Truth.assertThat(list.get(1)).isEqualTo("third");
        Truth.assertThat(list.get(2)).isEqualTo("last");

        Truth.assertThat(list.deleteEnd()).isEqualTo("last");
        Truth.assertThat(list.getEnd()).isEqualTo("third");

        Truth.assertThat(list.deleteHead()).isEqualTo("zero");
        Truth.assertThat(list.getEnd()).isEqualTo("third");
        Truth.assertThat(list.getHead()).isEqualTo("third");

        Truth.assertThat(list.update(0, "only you")).isEqualTo("third");
        Truth.assertThat(list.getEnd()).isEqualTo("only you");
        Truth.assertThat(list.getHead()).isEqualTo("only you");

        Truth.assertThat(list.updateHead(5)).isEqualTo("only you");
        Truth.assertThat(list.getEnd()).isEqualTo(5);
        Truth.assertThat(list.getHead()).isEqualTo(5);
    }
}
