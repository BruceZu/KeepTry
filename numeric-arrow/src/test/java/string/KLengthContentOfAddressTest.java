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

public class KLengthContentOfAddressTest {
    @Test(timeout = 3000L, expected = Test.None.class)
    public void testHavePermutation() {
        String address = "213MaryAve,Chalrottesville,VA 22903";
        Assert.assertEquals(KLengthContentOfAddress.getSubStr(3, address),
                "213");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr(6, address),
                "213229");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr(10, address),
                "21322903Ma");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr(30, address),
                "21322903MaryAveChalrottesville");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr(32, address),
                "21322903MaryAveChalrottesvilleVA");

        Assert.assertEquals(KLengthContentOfAddress.getSubStr2(3, address),
                "213");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr2(6, address),
                "213229");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr2(10, address),
                "21322903Ma");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr2(30, address),
                "21322903MaryAveChalrottesville");
        Assert.assertEquals(KLengthContentOfAddress.getSubStr2(32, address),
                "21322903MaryAveChalrottesvilleVA");
    }
}
