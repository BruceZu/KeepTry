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

import org.junit.Assert;
import org.junit.Test;

public class C522SumTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        int[] r = C522Sum.find(new int[]{1, 9, 11, 21, 23}, 20);
        Assert.assertEquals(r[0], 1);
        Assert.assertEquals(r[1], 2);
        r = C522Sum.find(new int[]{1, 9, 11, 21, 23}, 19);
        Assert.assertEquals(r[0], -1);
        Assert.assertEquals(r[1], -1);
    }
}
