//  Copyright 2016 The Minorminor Open Source Project
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

package charter3.exercises;

import charter3.CircularlyLinkedList2;
import charter3.DoublyLinkedList2;
import charter3.MyLinkedList;
import charter3.SinglyLinkedList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class R39R310R311Test {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Iterable<MyLinkedList> data() {
        return Arrays.asList(new MyLinkedList[]{
                        new SinglyLinkedList(),
                        new CircularlyLinkedList2(),
                        new DoublyLinkedList2()}
        );
    }

    private MyLinkedList test;

    public R39R310R311Test(MyLinkedList test) {
        this.test = test;
    }

    @Test(timeout = 10000L, expected = Test.None.class)
    public void test() {
        CircularlyLinkedList2 test = new CircularlyLinkedList2();
        Assert.assertSame(test.size(), test.size2());
        Assert.assertSame(0, test.size2());
        test.add("1");
        Assert.assertSame(test.size(), test.size2());
        Assert.assertSame(1, test.size2());
        test.add("2");
        Assert.assertSame(test.size(), test.size2());
        Assert.assertSame(2, test.size2());
    }
}
