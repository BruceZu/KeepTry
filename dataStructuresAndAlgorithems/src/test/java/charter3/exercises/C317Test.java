// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3.exercises;

import org.junit.Test;

public class C317Test {
    @Test(timeout = 10000L, expected = Test.None.class)
    public void test() {
        int[] test = new int[]{1, 2, 3, 4, 4};
        org.junit.Assert.assertEquals(C317.getTheRepeatedInteger(test), 4);
        test = new int[]{4, 4, 1, 2, 3,};
        org.junit.Assert.assertEquals(C317.getTheRepeatedInteger(test), 4);
    }

}
