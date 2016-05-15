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

package array;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class SortTest {

    @Parameterized.Parameters(name = "test with {index} {0}")
    public static Collection<Object[]> data() {
        List r = Arrays.asList(new Comparable[][][]{
                {{3, 1, 4, 6, 7, 9}, {1, 3, 4, 6, 7, 9}},
                {{}, {}},
                {{1}, {1}},
                {{9, 8, 7, 6, 5, 4, 0}, {0, 4, 5, 6, 7, 8, 9}},
                {{4, 5, 6, 7, 8, 9}, {4, 5, 6, 7, 8, 9}},
                {{6, 5, 2}, {2, 5, 6}},
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
        Sort.selectionSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void insertSortTest() {
        Comparable[] ar = clone(arr);
        // start test
        Sort.insertSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testSort() {
        Comparable[] ar = clone(arr);
        // start test
        Sort.shellSort(ar);
        Assert.assertTrue(Arrays.equals(ar, sorted));
    }
}
