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

import static array.IdenticalStrings.allStringSetsIdentical;

public class IdenticalStringsTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void allStringSetsIdenticalTest() {
        Assert.assertTrue(allStringSetsIdentical(new String[][]{{"a", "b"}, {"b", "b", "a"}, {"b", "a"}}));
        Assert.assertTrue(allStringSetsIdentical(null));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{null, null}));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{{null}, {null, null}}));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{}));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{{}, {}}));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{{null, ""}, {"", null, null}}));
        Assert.assertTrue(allStringSetsIdentical(new String[][]{{"\\uD841\\uDF0E ", null, "", "ǅ", "ʵ"},
                                                                {"", "ǅ", null, "ʵ", "\\uD841\\uDF0E "}}));

        Assert.assertFalse(allStringSetsIdentical(new String[][]{{"a", "b"}, {"a"}, {"b"}}));
        Assert.assertFalse(allStringSetsIdentical(new String[][]{null, {}}));
        Assert.assertFalse(allStringSetsIdentical(new String[][]{{null}, {null, ""}}));
        Assert.assertFalse(allStringSetsIdentical(new String[][]{{}, {""}}));
        Assert.assertFalse(allStringSetsIdentical(new String[][]{{null, ""}, {null, null}}));
        Assert.assertFalse(allStringSetsIdentical(new String[][]{{"\\uD841\\uDF0E ", null, "", "ǅ", "ʵ"},
                                                                 {" \\uD841\\uDF0E", "", "ǅ", null, "ʵ"}}));
    }
}
