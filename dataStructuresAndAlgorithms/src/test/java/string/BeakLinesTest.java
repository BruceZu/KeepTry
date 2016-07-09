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

import static string.BreakLines.wrapText;

public class BeakLinesTest {

    @Test(timeout = 400L, expected = Test.None.class)
    public void wrapTextTest() {
        String in = " 1234 67\n 0 ";
        String expected = "[1234\n67\n0]";
        int maxCharsPerLine = 5;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        expected = "[1234\n67\n0]";
        maxCharsPerLine = 4;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        maxCharsPerLine = 12;
        expected = "[1234 67\n0]";
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = " \n \n ";
        maxCharsPerLine = 10;
        expected = "[\n\n]";
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = " 1234";
        expected = "[1234]";
        maxCharsPerLine = 4;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = "";
        expected = "[]";
        maxCharsPerLine = 4;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = null;
        maxCharsPerLine = 4;
        Assert.assertEquals(null, wrapText(in, maxCharsPerLine));

        //    01234567890123
        in = "this is a word";
        expected = "[this\nis a\nword]";
        maxCharsPerLine = 6;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        //    01234567890123
        in = "this is a word";
        expected = "[this is\na word]";
        maxCharsPerLine = 7;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        //    012345678901234
        in = "a nice day";
        expected = "[a nice\nday]";
        maxCharsPerLine = 6;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        //    0123456789012
        in = "a nice day en";
        expected = "[a nice\nday en]";
        maxCharsPerLine = 6;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        //    0123456789012
        in = "a engineering ";
        expected = "java.lang.IllegalArgumentException: The max line length is too short: 2";
        maxCharsPerLine = 2;
        try {
            Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(expected, e.toString());
        }

        in = "I have more \n \n here ";
        expected = "[I\nhave\nmore\n\nhere]";
        maxCharsPerLine = 5;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = "I have more \n \n  ";
        expected = "[I\nhave\nmore\n\n]";
        maxCharsPerLine = 5;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");

        in = "I have more \n \n  ";
        expected = "[I have more\n\n]";
        maxCharsPerLine = 100;
        Assert.assertEquals(expected, "[" + wrapText(in, maxCharsPerLine) + "]");
    }
}
