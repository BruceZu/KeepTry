//  Copyright 2017 The KeepTry Open Source Project
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

package dp.partition;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestPartitiontoKEqualSumSubsets {
    @Parameterized.Parameters(name = "{index}: nums is {0}, k is {1}")
    public static Iterable<Object[]> data() {

        return Arrays.asList(
                new Object[][] {
                    {new int[] {1, 2, 3, 4, 5, 6}, 3, Boolean.TRUE},
                    {new int[] {4, 2, 2, 3, 6, 6, 1}, 4, Boolean.TRUE},
                    {new int[] {2, 1, 2, 2, 1, 1}, 3, Boolean.TRUE},
                    {new int[] {2, 2, 2, 2, 2, 2}, 3, Boolean.TRUE},
                    {new int[] {3, 1, 1, 2, 1, 1}, 3, Boolean.TRUE},
                    {new int[] {2, 2, 2, 1, 1, 1}, 3, Boolean.TRUE},
                    {new int[] {3, 1, 1, 2, 1, 1}, 7, Boolean.FALSE},
                    {new int[] {3, 1, 1, 2, 1, 1}, 0, Boolean.FALSE},
                    {null, 2, Boolean.FALSE},
                    {new int[] {3, 1, 1, 2, 1, 1}, 2, Boolean.FALSE},
                    {
                        new int[] {5, 5, 5, 5, 5, /**/ 5, 5, 5, 5, 5, /**/ 5, 5, 5, 5, 5, /**/ 5},
                        5,
                        Boolean.FALSE
                    },
                });
    }

    private int[] data;
    int k;
    boolean expected;

    public TestPartitiontoKEqualSumSubsets(int[] data, int k, boolean expected) {
        this.data = data;
        this.k = k;
        this.expected = expected;
    }

    @Test(timeout = 50000l, expected = Test.None.class)
    public void Test() {
        Assert.assertEquals(
                expected, Leetcode698PartitiontoKEqualSumSubsets.canPartitionKSubsets(data, k));

        Assert.assertEquals(
                expected, Leetcode698PartitiontoKEqualSumSubsets2.canPartitionKSubsets(data, k));

        Assert.assertEquals(
                expected, Leetcode698PartitiontoKEqualSumSubsets4.canPartitionKSubsets(data, k));
        Assert.assertEquals(
                expected, Leetcode698PartitiontoKEqualSumSubsets4.canPartitionKSubsets2(data, k));
    }
}
