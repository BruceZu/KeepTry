package sort;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class WinggleSortTest {
    @Parameterized.Parameters(name = "test with {index} {0}")
    public static Iterable<Object[]> data() {
        List r = Arrays.asList(new Comparable[][][]{
                {{8, 7, 10, 9, 6, 5, 3, 2, 4},
                        {6, 9, 4, 8, 2, 7, 5, 10, 3}},
                {{8, 7, 10, 6, 9, 5, 3, 2, 4, 1},
                        {5, 8, 3, 10, 2, 9, 4, 6, 1, 7}},
                {{1, 5, 1, 1, 6, 4},
                        {1, 6, 1, 4, 1, 5}},
                {{4, 5, 5, 6},
                        {5, 6, 4, 5}},
                {{2, 4, 5, 1, 2, 4, 1, 1, 3, 3, 1, 2, 4, 3},
                        {2, 4, 2, 4, 2, 3, 1, 5, 1, 3, 1, 3, 1, 4}},
                {{0, 1, 2, 3, 4, 5},
                        {2, 3, 1, 4, 0, 5}},
                {{1, 1, 2, 1, 2, 2, 1},
                        {1, 2, 1, 2, 1, 2, 1}},
                {{4, 5, 5, 6},
                        {5, 6, 4, 5}},
                {{4, 5, 5, 5, 5, 6, 6, 6},
                        {5, 6, 5, 6, 5, 6, 4, 5}},
        });
        return r;
    }

    private Comparable[] arr;
    private Comparable[] sorted;

    private int[] toInt(Comparable[] arr) {
        int[] r = new int[arr.length];
        int i = 0;
        for (Comparable c : arr) {
            r[i++] = (Integer) c;
        }
        return r;
    }

    public WinggleSortTest(Comparable[] arr, Comparable[] sorted) {
        this.arr = arr;
        this.sorted = sorted;
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void winggleSortTest() {
        int[] ar = toInt(Arrays.copyOf(arr, arr.length));
        // start test
        Leetcode324WiggleSortII2.wiggleSort(ar);
        System.out.println(Arrays.toString(ar));
        Assert.assertTrue(Arrays.equals(toInt(sorted), ar));
    }
}
