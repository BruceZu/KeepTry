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

package stack;

import org.junit.Assert;
import org.junit.Test;

public class ValidDelimitersTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void test() {
        StringBuilder str = new StringBuilder();
        str.append("{---");
        Assert.assertFalse(ValidDelimiters.isValidDelimiters(str.toString()));
        str.append("<body >")
                .append("<center >")
                .append("<h1> The Little Boat</h1>")
                .append("</center >")
                .append("<p> The storm tossed the little")
                .append("boat like a cheap sneaker in an")
                .append("old washing machine.The three")
                .append("drunken fishermen were used to")
                .append(" such treatment, of course, but")
                .append("not the tree salesman, who even as")
                .append("a stowaway now felt that he")
                .append("had overpaid for the voyage.</p >")
                .append("<ol >")
                .append("<li > Will the salesman die ?</li >")
                .append("<li > What color is the boat?</li >")
                .append("<li > And what about Naomi ?</li >")
                .append("</ol >")
                .append("</body >");
        Assert.assertFalse(ValidDelimiters.isValidDelimiters(str.toString()));
        str.append("---}");
        Assert.assertTrue(ValidDelimiters.isValidDelimiters(str.toString()));
    }
}
