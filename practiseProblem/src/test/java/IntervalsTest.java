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

import org.junit.Assert;
import org.junit.Test;

public class IntervalsTest {

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testHavePermutation() {
        Intervals is = new Intervals();
        is.add(6, 7);
        is.add(3, 5);
        Assert.assertEquals(is.toString(), "[[3, 5], [6, 7]]");

        is.add(8, 9);
        Assert.assertEquals(is.toString(), "[[3, 5], [6, 7], [8, 9]]");

        is.add(7, 9);
        Assert.assertEquals(is.toString(), "[[3, 5], [6, 9]]");

        is.add(1, 19);
        Assert.assertEquals(is.toString(), "[[1, 19]]");

        Assert.assertEquals(is.query(4), true);
        Assert.assertEquals(is.query(0), false);
        Assert.assertEquals(is.query(20), false);
    }
}
