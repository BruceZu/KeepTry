//
//  Copyright (C) 2015 The Minorminor Open Source Project
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package charter3;

import junit.runner.Version;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class MyLinkedListTest {
    private static Logger log = LoggerFactory.getLogger(EntiesTest.class);

    @Parameters(name = "{index}:  concrete linked list: {0} ")
    public static Iterable<MyLinkedList> data() {
        return Arrays.asList(new MyLinkedList[]{
                        new SinglyLinkedList(),
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
        assertThat(list.size()).isEqualTo(0);
        list.add("head");
        assertThat(list.deleteHead()).isEqualTo("head");
        assertThat(list.size()).isEqualTo(0);
        assertThat(list.getEnd()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testHeadNodeAfterDeleteEnd() {
        list.add("end");
        assertThat(list.deleteEnd().equals("end"));
        assertThat(list.getHead()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void addTest() {
        list.add("middle");
        list.addAfter("afterMiddle", 0);
        list.addBefore("head", 0);
        list.appendToTheEnd("end");
        assertThat(list.get(0)).isEqualTo("head");
        assertThat(list.get(1)).isEqualTo("middle");
        assertThat(list.get(2)).isEqualTo("afterMiddle");
        assertThat(list.get(3)).isEqualTo("end");

        list.clean();
        list.appendToTheEnd("middle");
        list.add("head");
        list.addAfter("end", 1);
        list.addBefore("afterMiddle", 2);
        assertThat(list.get(0)).isEqualTo("head");
        assertThat(list.get(1)).isEqualTo("middle");
        assertThat(list.get(2)).isEqualTo("afterMiddle");
        assertThat(list.get(3)).isEqualTo("end");
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void deleteTest() {
        list.add("5");
        list.add("4");
        list.add("3");
        list.add("2");
        list.add("1");
        list.add("0");
        assertThat(list.size()).isEqualTo(6);

        assertThat(list.delete(5)).isEqualTo("5");
        assertThat(list.delete(0)).isEqualTo("0");

        assertThat(list.get(0)).isEqualTo("1");
        assertThat(list.get(1)).isEqualTo("2");
        assertThat(list.get(2)).isEqualTo("3");
        assertThat(list.get(3)).isEqualTo("4");

        assertThat(list.delete(2)).isEqualTo("3");
        assertThat(list.deleteHead()).isEqualTo("1");
        assertThat(list.deleteEnd()).isEqualTo("4");
        assertThat(list.delete(0)).isEqualTo("2");
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void updateTest() {
        list.add("4");
        list.add("3");
        list.add("2");
        list.add("1");
        list.add("0");

        assertThat(list.update(0, "head")).isEqualTo("0");
        assertThat(list.update(2, "middle")).isEqualTo("2");
        assertThat(list.update(4, "end")).isEqualTo("4");

        assertThat(list.get(0)).isEqualTo("head");
        assertThat(list.get(1)).isEqualTo("1");
        assertThat(list.get(2)).isEqualTo("middle");
        assertThat(list.get(3)).isEqualTo("3");
        assertThat(list.get(4)).isEqualTo("end");


        assertThat(list.updateEnd("4")).isEqualTo("end");
        assertThat(list.updateHead("0")).isEqualTo("head");

        assertThat(list.get(0)).isEqualTo("0");
        assertThat(list.get(1)).isEqualTo("1");
        assertThat(list.get(2)).isEqualTo("middle");
        assertThat(list.get(3)).isEqualTo("3");
        assertThat(list.get(4)).isEqualTo("4");
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void getTest() {
        list.add("4");
        list.add("3");
        list.add("2");
        list.add("1");
        list.add("0");

        assertThat(list.getHead()).isEqualTo("0");
        assertThat(list.getEnd()).isEqualTo("4");
        assertThat(list.get(2)).isEqualTo("2");
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void equalsTest() {
        Assert.assertFalse(list.equals(null));

        Assert.assertTrue(list.size() == 0);
        Assert.assertTrue(list.equals(list.clone()));

        list.add("end");
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.equals(list.clone()));

        list.add(2);
        list.add(new String[][]{{"NameA", "NameB"}, {"scoreA", "scoreB"}});
        list.add(new boolean[]{true, false});
        list.add(1);


        Assert.assertFalse(list.equals(new SinglyLinkedList<String>()));

        Assert.assertTrue(list.equals(list));
        MyLinkedList clone = list.clone();
        Assert.assertTrue(list.equals(clone));
        Assert.assertTrue(clone.equals(list));

        clone.updateHead("1");
        Assert.assertNotEquals(list, clone);
        clone.updateHead(1);
        Assert.assertEquals(list, clone);

        clone.updateEnd(3);
        Assert.assertNotEquals(list, clone);
        clone.updateEnd("end");
        Assert.assertEquals(list, clone);

        String[][] originalElement = (String[][]) clone.update(2, new String[][]{{"NameA", "NameB"}, {"scoreA", "scoreB"}});
        Assert.assertEquals(list, clone);
        clone.update(2, originalElement);
        Assert.assertEquals(list, clone);

        boolean[] oe = (boolean[]) clone.update(1, new boolean[]{true, false});
        Assert.assertEquals(list, clone);
        clone.update(1, oe);
        Assert.assertEquals(list, clone);

        clone.add("head");
        Assert.assertNotEquals(list, clone);
    }
}
