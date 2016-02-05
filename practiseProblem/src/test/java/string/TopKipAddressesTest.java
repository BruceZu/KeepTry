package string;//  Copyright 2016 The Minorminor Open Source Project
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

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TopKipAddressesTest {
    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {
        StringBuilder b = new StringBuilder()
                .append("Abdfdfd\\tdfdfd\\t1.23.132.21bdfdfd\\tdfdfd\\t1.23.192.299\n")
                .append("bsdasfsadfbdfdfd\\tdfdfd\\t11.23.132.21tdfdfd\\t1.23.192.292\n")
                .append("sadf\\asder\\asdfadsf\\tasd99.889.38.99tdfdfd\\t1.23.192.294\n")
                .append("werw\\asdr\\asdf87.654.22.233tdfdfd\\t1.23.192.293\n")
                .append("sdf\\sadf\\sdfsadf\\asdf44.99.23.887\n")
                .append("asdf\\asdf\\asdf\\a\\hh12.773.887.112\n")
                .append("asdf\\asdf\\ewrwr\\werwe\\wee88.234.111.342\n")
                .append("asdf\\asdf\\asdf\\as\n")
                .append("ew\\wqer\\qew\\qewasd\\323.99.323.\n")
                .append("asdewr\\adrwq\\aer\\12.234.\n")
                .append("ads\\ata\6.\n")
                .append("aoero\\asdrwe\\we192.168.0.8\n")
                .append("ads\\ata\4\n")
                .append("aoero\\asdrwe\\we192.168.0.11\n");
        List<String> expected = new LinkedList<>();
        Collections.addAll(expected,
                "1.23.192.299",
                "1.23.192.292",
                "1.23.192.294",
                "1.23.192.293",
                "4.99.23.887",
                "2.773.887.112",
                "8.234.111.342",
                "2.168.0.8",
                "2.168.0.11"
        );

        /**
         * $ cat test.txt
         Abdfdfd\tdfdfd\t1.23.132.21bdfdfd\tdfdfd\t1.23.192.299
         bsdasfsadfbdfdfd\tdfdfd\t11.23.132.21tdfdfd\t1.23.192.292
         sadf\asder\asdfadsf\tasd99.889.38.99tdfdfd\t1.23.192.294
         werw\asdr\asdf87.654.22.233tdfdfd\t1.23.192.293
         sdf\sadf\sdfsadf\asdf44.99.23.887
         asdf\asdf\asdf\a\hh12.773.887.112
         asdf\asdf\ewrwr\werwe\wee88.234.111.342
         asdf\asdf\asdf\as
         ew\wqer\qew\qewasd\323.99.323.
         asdewr\adrwq\aer\12.234.
         ads\ata\6.
         aoero\asdrwe\we192.168.0.8
         ads\ata\4
         aoero\asdrwe\we192.168.0.11
         */
        //Assert.assertEquals(TopKIPAddresses.getTopKIP(23, "/Users/brucezu/Documents/minorminor/test.txt"), expected);
        Assert.assertEquals(TopKIPAddresses.getTopKIP2(23, b.toString()), expected);
    }
}
