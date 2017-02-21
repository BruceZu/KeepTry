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

package tree.binarytree;

import org.junit.Assert;
import org.junit.Test;

public class ValidPreOrderArrayTest {

    @Test(timeout = 100l, expected = Test.None.class)
    public void ValidPreOrderArrayTest() {

        Assert.assertTrue(ValidPreOrderArray.valid(null));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', 'l', '#', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', '#', 'r', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', '#', 'r', 'l', '#', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', '#', 'r', 'l', '#', 'r', '#', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'a', 'l', 'l', '#', 'r', '#', '#', '#', '#'}));
        Assert.assertTrue(ValidPreOrderArray.valid(new char[]{'9', '3', '4', '#', '#', '1', '#', '#', '2', '#', '6', '#', '#'}));

        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a','b'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '3', '4', '#', '#', '1', '#', '#', '2', '#', '6', '#', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '3', '4', '#', '#', '1', '#', '#', '2', '#', '#', '6', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '3', '4', '#', '#', '1', '#', '#', '#', '2', '#', '6', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '3', '4', '#', '#', '#', '1', '#', '#', '2', '#', '6', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '3', '#', '4', '#', '#', '#', '1', '#', '2', '#', '6', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'9', '#', '3', '4', '#', '#', '#', '1', '#', '2', '#', '6', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'m', 'a', 'l', 'l', '#', 'r', '#', '#', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a', '#', 'm', 'r', 'l', '#', 'r', '#', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a', '#', 'r', 'l', '#', 'm', 'r', '#', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a', '#', '#', 'r', 'l', '#', 'r', '#', '#', '#'}));
        Assert.assertFalse(ValidPreOrderArray.valid(new char[]{'a', '#', 'r', '#', 'l', '#', 'r', '#', '#', '#'}));
    }
}
