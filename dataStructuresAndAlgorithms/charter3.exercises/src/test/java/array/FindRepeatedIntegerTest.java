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

import org.junit.Test;
import  static  org.junit.Assert.assertEquals;

public class FindRepeatedIntegerTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        int[] test = new int[]{1, 2, 3, 4, 4};
        assertEquals(FindRepeatedInteger.getTheRepeatedInteger(test), 4);
        assertEquals(FindRepeatedInteger.getTheRepeatedInteger2(test), 4);
        test = new int[]{4, 4, 1, 2, 3,};
        assertEquals(FindRepeatedInteger.getTheRepeatedInteger(test), 4);
        assertEquals(FindRepeatedInteger.getTheRepeatedInteger2(test), 4);
    }

}
