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

package list;

import junit.runner.Version;
import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class MyLinkedListTest {
    private static Logger log = LoggerFactory.getLogger(MyLinkedListTest.class);

    @Rule
    public MethodRule test3 = new MethodRule() {
        @Override
        public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, Object o) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    statement.evaluate();
                }
            };
        }
    };

    @Rule
    public TestRule test2 = new TestRule() {
        @Override
        public Statement apply(final Statement statement, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    statement.evaluate();
                }
            };
        }
    };

    @ClassRule
    public static TestRule test = new TestRule() {
        @Override
        public Statement apply(final Statement statement, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    statement.evaluate();
                }
            };
        }
    };

    @Parameters(name = "test with {index} {0}")
    public static Iterable<MyLinkedList> data() {
        return Arrays.asList(
                new SinglyLinkedList(),
                new CircularlyLinkedList(),
                new CircularlyLinkedList2(),
                new DoublyLinkedList(),
                new DoublyLinkedList2(),
                new DoublyLinkedListWithOneSentinel()
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

    @Test(timeout = 20L, expected = Test.None.class)
    public void testVersion() {
        org.junit.Assert.assertEquals("4.12", Version.id());
    }

    @Test(timeout = 20L, expected = IndexOutOfBoundsException.class)
    public void testAdd() {
        list.addAfter(0, 1);
    }

    @Test(timeout = 20L, expected = IndexOutOfBoundsException.class)
    public void testDelete() {
        list.delete(0);
    }

    @Test(timeout = 20L, expected = IndexOutOfBoundsException.class)
    public void testUpdate() {
        list.update(1, 3);
    }

    @Test(timeout = 200L, expected = IndexOutOfBoundsException.class)
    public void testGetOf() {
        list.get(0);
    }

    @Test(timeout = 200L, expected = Test.None.class)
    public void testEndNodeAfterDeleteHead() {
        assertThat(list.size()).isEqualTo(0);
        list.addBeforeHead("head");
        assertThat(list.deleteHead()).isEqualTo("head");
        assertThat(list.size()).isEqualTo(0);
        assertThat(list.getEnd()).isNull();
    }

    @Test(timeout = 200L, expected = Test.None.class)
    public void testHeadNodeAfterDeleteEnd() {
        list.addBeforeHead("end");
        assertThat(list.deleteEnd().equals("end"));
        assertThat(list.getHead()).isNull();
    }

    @Test(timeout = 40L, expected = Test.None.class)
    public void addTest() {
        list.addBeforeHead("middle");
        list.addAfter("afterMiddle", 0);
        list.addBefore("head", 0);
        list.appendAfterEnd("end");
        assertThat(list.get(0)).isEqualTo("head");
        assertThat(list.get(1)).isEqualTo("middle");
        assertThat(list.get(2)).isEqualTo("afterMiddle");
        assertThat(list.get(3)).isEqualTo("end");

        list.clean();
        list.appendAfterEnd("middle");
        list.addBeforeHead("head");
        list.addAfter("end", 1);
        list.addBefore("afterMiddle", 2);
        assertThat(list.get(0)).isEqualTo("head");
        assertThat(list.get(1)).isEqualTo("middle");
        assertThat(list.get(2)).isEqualTo("afterMiddle");
        assertThat(list.get(3)).isEqualTo("end");
    }

    @Test(timeout = 200L, expected = Test.None.class)
    public void deleteTest() {
        list.addBeforeHead("5");
        list.addBeforeHead("4");
        list.addBeforeHead("3");
        list.addBeforeHead("2");
        list.addBeforeHead("1");
        list.addBeforeHead("0");
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

    @Test(timeout = 200L, expected = Test.None.class)
    public void updateTest() {
        list.addBeforeHead("4");
        list.addBeforeHead("3");
        list.addBeforeHead("2");
        list.addBeforeHead("1");
        list.addBeforeHead("0");

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

    @Test(timeout = 200L, expected = Test.None.class)
    public void getTest() {
        list.addBeforeHead("4");
        list.addBeforeHead("3");
        list.addBeforeHead("2");
        list.addBeforeHead("1");
        list.addBeforeHead("0");

        assertThat(list.getHead()).isEqualTo("0");
        assertThat(list.getEnd()).isEqualTo("4");
        assertThat(list.get(2)).isEqualTo("2");
    }

    @Test(timeout = 200L, expected = Test.None.class)
    public void equalsTest() {
        Assert.assertFalse(list.equals(null));

        Assert.assertTrue(list.size() == 0);
        Assert.assertTrue(list.equals(list.clone()));

        list.addBeforeHead("end");
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.equals(list.clone()));

        list.addBeforeHead(2);
        list.addBeforeHead(new String[][]{{"NameA", "NameB"}, {"scoreA", "scoreB"}});
        list.addBeforeHead(new boolean[]{true, false});
        list.addBeforeHead(1);


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

        clone.addBeforeHead("head");
        Assert.assertNotEquals(list, clone);
    }
}
