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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class UniqueCharsTest {
    @Parameterized.Parameters(name = "test")
    public static Iterable<String[]> data() {
        return Arrays.asList(new String[][]{
                {"", ""},
                {null, null},
                {"ABA", "AB"},
                {"aaaaa", "a"},
                {"aaabbbb", "ab"},
        });
    }

    private String test, expected;

    public UniqueCharsTest(String data, String expected) {
        this.test = data;
        this.expected = expected;
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testGetUniqueChars() {

        Assert.assertEquals(UniqueChars.getUniqueChars(test), expected);
        Assert.assertEquals(UniqueChars.getUniqueCharsBitWise(test), expected);
    }
}
