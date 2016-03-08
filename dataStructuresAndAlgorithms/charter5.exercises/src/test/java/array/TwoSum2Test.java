package array;//  Copyright 2016 The Sawdust Open Source Project
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
public class TwoSum2Test {

    @Parameterized.Parameters(name = "{index}: array is {0}, k and expected index i and j is {1}")
    public static Iterable<int[][]> data() {
        return Arrays.asList(new int[][][]{
                {{4, 10, 15, 2, 7, 1, 20, 25}, {7, 1, 2}},
                {{4, 10, 15, 2, 7, 1, 10, 125}, {0, -1, -1}},
                {{0, 8, 8}, {2, 0, 1}},
                {{1, 8, 8, 16}, {3, 1, 2}}
        });
    }

    private int[] array;
    private int k;
    private int i, j;

    public TwoSum2Test(int[] array, int[] targetAndExpectedResult) {
        this.array = array;
        this.k = targetAndExpectedResult[0];
        this.i = targetAndExpectedResult[1];
        this.j = targetAndExpectedResult[2];
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        int[] result = TwoSum2.check(array);
        Assert.assertEquals(result[2], k);
        Assert.assertEquals(result[0], i);
        Assert.assertEquals(result[1], j);

        result = TwoSum2.check2(array);
        Assert.assertEquals(result[2], k);
        Assert.assertEquals(result[0], i);
        Assert.assertEquals(result[1], j);

        result = TwoSum2.check3(array);
        Assert.assertEquals(result[2], k);
        Assert.assertEquals(result[0], i);
        Assert.assertEquals(result[1], j);
    }
}
