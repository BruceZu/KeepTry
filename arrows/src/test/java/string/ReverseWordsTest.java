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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class ReverseWordsTest {

    @Parameterized.Parameters(name = "")
    public static Iterable<String[]> data() {
        return Arrays.asList(new String[][]{
                {" I have  36 books, 40 pens2, and 1 notebook.",
                        " I evah  36 skoob, 40 2snep, dna 1 koteboon."},
                {null, null},
                {"", ""},
                {"I", "I"},
        });
    }

    private String test ;
    private String expected;

    public ReverseWordsTest(String  test,String  expected) {
        this.test = test;
        this.expected =expected;
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void testPalindrome() {
        Assert.assertEquals(expected, ReverseWords.reverseWords(test));
    }
}
