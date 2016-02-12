package datastructure.stack;//  Copyright 2016 The Sawdust Open Source Project
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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class IStackTest {
    @Parameterized.Parameters(name = "{index} is IStack implementation {0}")
    public static Iterable<IStack> data() {
        return Arrays.asList(new IStack[]{
                new ArrayStack<>(),
                new LinkedListStack<>()
        });
    }

    private IStack<Integer> s = new ArrayStack<>();

    public IStackTest(IStack<Integer> s) {
        this.s = s;
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        s.push(5);
        s.push(3);
        Assert.assertEquals(s.toString(), "[3, 5]");
        Assert.assertEquals(s.size(), 2);
        Assert.assertEquals(s.pop(), 3, 0);
        Assert.assertEquals(s.isEmpty(), false);
        Assert.assertEquals(s.toString(), "[5]");
        Assert.assertEquals(s.pop(), 5, 0);
        Assert.assertEquals(s.isEmpty(), true);
        Assert.assertEquals(s.pop(), null);
        s.push(7);
        s.push(9);
        Assert.assertEquals(s.toString(), "[9, 7]");
        Assert.assertEquals(s.peek(), 9, 0);
        s.push(4);
        Assert.assertEquals(s.size(), 3);
        Assert.assertEquals(s.pop(), 4, 0);
        s.push(6);
        Assert.assertEquals(s.toString(), "[6, 9, 7]");
        s.push(8);
        Assert.assertEquals(s.pop(), 8, 0);
    }
}
