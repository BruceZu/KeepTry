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

package string;import org.junit.Assert;
import org.junit.Test;


public class SToFTest {
    private static final double DELTA = 0;

    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {
        try {
            String[] ins = new String[]{
                    // String.valueOf(Double.MIN_NORMAL), // scientific notation 'E'
                    // String.valueOf(Double.MAX_VALUE),
                    "+9999999999999999",
                    "0.999999999999999",
                    "9999999999999999.99999999999999",
                    "-9999999999999999",
                    "-0.999999999999999",
                    "-9999999999999999.99999999999999",
            };
            for (String in : ins) {
                Assert.assertEquals(new SToF().toFloat(in), Double.valueOf(in).doubleValue(), DELTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
