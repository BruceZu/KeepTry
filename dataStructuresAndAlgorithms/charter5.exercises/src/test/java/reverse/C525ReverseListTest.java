package reverse;//  Copyright 2016 The Sawdust Open Source Project
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

import datastructure.list.ListNode;
import datastructure.list.ListRecursonImplementation;
import org.junit.Assert;
import org.junit.Test;

public class C525ReverseListTest {

    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        int[] l = new int[]{1, 2, 3, 4};
        ListNode test = new ListNode(l[0]);
        for (int i = 1; i < l.length; i++) {
            ListRecursonImplementation.insertRear(test, l[i]);
        }
        Assert.assertEquals(ListRecursonImplementation.toString(test), "1 2 3 4 ");

        ListNode reversed = C525ReverseList.reverse(test);
        Assert.assertEquals(ListRecursonImplementation.toString(reversed), "4 3 2 1 ");

        reversed = C525ReverseList.reverse2(reversed);
        Assert.assertEquals(ListRecursonImplementation.toString(reversed), "1 2 3 4 ");
    }
}

