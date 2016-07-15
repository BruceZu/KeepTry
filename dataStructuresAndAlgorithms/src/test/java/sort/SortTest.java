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
public class SortTest {

    @Parameterized.Parameters(name = "test with {index} {0}")
    public static Iterable<Object[]> data() {
        List r = Arrays.asList(new Comparable[][][]{
                {{2, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6}, {0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9}},
                {{2, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6}, {0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9}},
                {{9, 7, 6, 4, 3, 3}, {3, 3, 4, 6, 7, 9}},
                {{7, 2, 3, 4, 7, 8, 7, 9, 7, 9, 8, 9, 13, 12, 10, 9}, {2, 3, 4, 7, 7, 7, 7, 8, 8, 9, 9, 9, 9, 10, 12, 13}},
                {{1}, {1}},
                {{9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 4}, {0, 1, 2, 3, 4, 4, 5, 6, 7, 8, 9}},
                {{4, 5, 6, 7, 8, 9}, {4, 5, 6, 7, 8, 9}},
                {{4, 9, 4, 4, 1, 4, 4, 4, 9, 4, 4, 1, 4}, {1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9, 9}},
                {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9}},
                {{9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9}},
                {{'b', 'a', 'z'}, {'a', 'b', 'z'}},
                {null, null}
        });
        return r;
    }

    private Comparable[] arr;
    private Comparable[] sorted;

    private Comparable[] clone(Comparable[] arr) {
        if (arr == null) {
            return null;
        }
        Comparable[] r = new Comparable[arr.length];
        for (int i = 0; i < arr.length; i++) {
            r[i] = arr[i];
        }
        return r;
    }

    public SortTest(Comparable[] arr, Comparable[] sorted) {
        this.arr = arr;
        this.sorted = sorted;
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void selectSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        SelectionSort.selectionSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void insertSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        InsertSort.insertSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void insertSortTest2() {
        Comparable[] ar = clone(arr);
        // start test
        InsertSort.sortByInsert(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        ShellSort.shellSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 20l, expected = Test.None.class)
    public void mergeSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        MergeSortRecursionMultiThreads.mergeSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 20l, expected = Test.None.class)
    public void mergeSortTest2() {
        Comparable[] ar = clone(arr);
        // start test
        MergeSortRecursionMultiThreads2.mergeSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void mergeSortRecursionTest() {
        Comparable[] ar = clone(arr);
        // start test
        MergeSortRecursionSingleThread.mergeSortRecursion(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void mergeSortNoRecursionTest() {
        Comparable[] ar = clone(arr);
        // start test
        MergeSortNoRecursion.mergeSortNoRecursion(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void quickSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        QuickSort.quickSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void quickSort3wayTest() {
        Comparable[] ar = clone(arr);
        // start test
        QuickSort3way.quickSort3Way(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void quickSortDualPivotTest() {
        Comparable[] ar = clone(arr);
        // start test
        QuickSortDualPivot.quickSortDualPivot(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }


    @Test(timeout = 10l, expected = Test.None.class)
    public void quickSortDualPivotImprovementTest() {
        Comparable[] ar = clone(arr);
        // start test
        QuickSortDualPivotImprovement.quickSortDualPivot(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }
}
