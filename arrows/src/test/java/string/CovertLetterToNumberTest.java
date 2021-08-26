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

package string;

import org.junit.Assert;
import org.junit.Test;

import static string.CovertLetterToNumber.convert;

public class CovertLetterToNumberTest {
    @Test(timeout = 100L, expected = Test.None.class)
    public void testSpecialPalindrome() {
        Assert.assertEquals(null, convert(null));
        Assert.assertEquals("", convert(""));
        Assert.assertEquals("1, 1, 2, 2,  , 3", convert("a1b2 c"));
        Assert.assertEquals("1, 1, 2, 2,  , 3", convert("A1B2 C")); // UPPERCASE_LETTER
        Assert.assertEquals("1, 车", convert("a车"));
        Assert.assertEquals("1, ǅ", convert("aǅ"));// TITLECASE_LETTER
        Assert.assertEquals("1, ʵ", convert("aʵ"));// MODIFIER_LETTER
        Assert.assertEquals("1, \uD841\uDF0E", convert("1\uD841\uDF0E")); //supplementary characters
    }
}
