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

package enums;

import org.junit.Assert;
import org.junit.Test;

public class EnumTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void createStateSelectListTest() {
        String expected = "<select name=\"state\">\n"
                + "<option value=\"Alabama\">Alabama</option>\n"
                + "<option value=\"Alaska\">Alaska</option>\n"
                + "<option value=\"Arizona\">Arizona</option>\n"
                + "<option value=\"Arkansas\">Arkansas</option>\n"
                + "<option value=\"California\">California</option>\n"
                // more states here
                + "</select>\n";
        Assert.assertEquals(expected, Enums.createStateSelectList());
    }

    @Test(timeout = 160L, expected = Test.None.class)
    public void parseSelectedStateTest() {
        Assert.assertEquals("AL", Enums.parseSelectedState("Alabama"));
        Assert.assertEquals("AK", Enums.parseSelectedState("Alaska"));
        Assert.assertEquals("AZ", Enums.parseSelectedState("Arizona"));
        Assert.assertEquals("AR", Enums.parseSelectedState("Arkansas"));
        Assert.assertEquals("CA", Enums.parseSelectedState("California"));
        Assert.assertEquals(null, Enums.parseSelectedState("Beijing"));
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void displayStateFullNameTest() {
        Assert.assertEquals("Alabama", Enums.displayStateFullName("AL"));
        Assert.assertEquals("Alaska", Enums.displayStateFullName("AK"));
        Assert.assertEquals("Arizona", Enums.displayStateFullName("AZ"));
        Assert.assertEquals("Arkansas", Enums.displayStateFullName("AR"));
        Assert.assertEquals("California", Enums.displayStateFullName("CA"));
        Assert.assertEquals(null, Enums.displayStateFullName("BJ"));
    }
}
