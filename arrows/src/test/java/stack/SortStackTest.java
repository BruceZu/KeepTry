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

package stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class SortStackTest {
    private Stack stackOf(int[] ar) {
        Stack s = new Stack();
        for (int i = 0; i < ar.length; i++) {
            s.push(ar[i]);
        }
        return s;
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void testBubbleSort() {
        SortStack ss = new SortStack();

        Assert.assertEquals(null, ss.sortAscending(null));

        Stack empty = new Stack();
        Assert.assertEquals(empty, ss.sortAscending(empty));

        Stack one = new Stack();
        one.push(1);
        Assert.assertEquals(one, ss.sortAscending(one));

        Stack expected = stackOf(new int[]{5, 4, 3, 2, 1});

        Stack ascending = stackOf(new int[]{1, 2, 3, 4, 5});
        ss.sortAscending(ascending);
        Assert.assertEquals(expected, ascending);

        Stack descending = stackOf(new int[]{5, 4, 3, 2, 1});
        ss.sortAscending(descending);
        Assert.assertEquals(expected, descending);

        Stack anyOrder = stackOf(new int[]{3, 2, 1, 5, 4});
        ss.sortAscending(anyOrder);
        Assert.assertEquals(expected, anyOrder);
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void testInsertSort() {
        SortStack ss = new SortStack();

        Assert.assertEquals(null, ss.sortAscendingByInsertSort(null));

        Stack empty = new Stack();
        Assert.assertEquals(empty, ss.sortAscendingByInsertSort(empty));

        Stack one = new Stack();
        one.push(1);
        Assert.assertEquals(one, ss.sortAscendingByInsertSort(one));

        Stack expected = stackOf(new int[]{5, 4, 3, 2, 1});

        Stack ascending = stackOf(new int[]{1, 2, 3, 4, 5});
        ss.sortAscendingByInsertSort(ascending);
        Assert.assertEquals(expected, ascending);

        Stack descending = stackOf(new int[]{5, 4, 3, 2, 1});
        ss.sortAscendingByInsertSort(descending);
        Assert.assertEquals(expected, descending);

        Stack anyOrder = stackOf(new int[]{3, 2, 1, 5, 4});
        ss.sortAscendingByInsertSort(anyOrder);
        Assert.assertEquals(expected, anyOrder);
    }
}
