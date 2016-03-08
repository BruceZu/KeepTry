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
import org.junit.Test;

import java.util.Arrays;

public class OddAheadEvenArrayTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        int[] test = new int[]{2, 6, 8, 1, 3, 4, 5, 6, 7, 8, 12, 1, 22, 9, 9, 9};
        int[] expected = new int[]{1, 3, 5, 7, 1, 9, 9, 9, 2, 8, 12, 6, 22, 4, 8, 6};
        OddAheadEvenArray.rearranges(test);
        Assert.assertEquals(Arrays.equals(test, expected), true);

        test = new int[]{2, 6, 8, 1, 3, 4, 5, 6, 7, 8, 12, 1, 22, 9, 9, 9};
        OddAheadEvenArray.rearranges2(test);
        Assert.assertEquals(Arrays.equals(test, expected), true);
    }
}
