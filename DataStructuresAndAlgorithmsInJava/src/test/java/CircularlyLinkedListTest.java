import charter3.CircularlyLinkedList;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class CircularlyLinkedListTest {
    private CircularlyLinkedList list;

    @Before
    public void setup() {
        list = new CircularlyLinkedList();
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testAdd() {
        list.addAfter(3, 3);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testDelete() {
        list.delete(0);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testUpdate() {
        list.update(3, 3);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testGetOf() {
        list.get(2);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testEndNodeAfterDeleteHead() {
        list.add("head");
        Truth.assertThat(list.deleteHead()).isEqualTo("head");
        Truth.assertThat(list.getEnd()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testHeadNodeAfterDeleteEnd() {
        list.add("end");
        Truth.assertThat(list.deleteEnd().equals("end"));
        Truth.assertThat(list.getHead()).isNull();
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testWithString() {
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
        Truth.assertThat(list.get(2)).isEqualTo("end");
        Truth.assertThat(list.get(1)).isEqualTo("second");

        Truth.assertThat(list.get(2)).isEqualTo("end");
        list.addBefore("third", 2);
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

        list.rotate();
        list.rotate();
        list.rotate();
        Truth.assertThat(list.get(0)).isEqualTo("zero");
        Truth.assertThat(list.get(1)).isEqualTo("third");
        Truth.assertThat(list.get(2)).isEqualTo("last");

        Truth.assertThat(list.deleteEnd()).isEqualTo("last");
        Truth.assertThat(list.getEnd()).isEqualTo("third");

        Truth.assertThat(list.deleteHead()).isEqualTo("zero");
        Truth.assertThat(list.getEnd()).isEqualTo("third");
        Truth.assertThat(list.getHead()).isEqualTo("third");

        Truth.assertThat(list.update(0, "any")).isEqualTo("third");
    }
}
