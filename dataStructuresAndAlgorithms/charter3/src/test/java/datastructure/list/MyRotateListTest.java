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

package datastructure.list;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class MyRotateListTest {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<MyRotateList> data() {
        return Arrays.asList(new MyRotateList[]{
                new CircularlyLinkedList(),
                new CircularlyLinkedList2(),
                new SinglyLinkedList<>()
        });
    }

    protected MyRotateList list;

    public MyRotateListTest(MyRotateList list) {
        this.list = list;
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void testRotate() {
        list.addToTheHead("head");
        list.addAfter("second", 0);
        list.appendToTheEnd("end");
        Truth.assertThat(list.get(0)).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("second");
        Truth.assertThat(list.get(2)).isEqualTo("end");

        list.rotate();
        list.rotate();
        list.rotate();
        Truth.assertThat(list.get(0)).isEqualTo("head");
        Truth.assertThat(list.get(1)).isEqualTo("second");
        Truth.assertThat(list.get(2)).isEqualTo("end");
    }
}
