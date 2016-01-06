//  Copyright 2016 The Minorminor Open Source Project
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

package jdktest.bitwise;

import org.junit.Assert;
import org.junit.Test;

public class BitMaskTest {
    static final int flagbit2 = 2;

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testSwap() {
        int a = Integer.valueOf("1100", 2), b = Integer.valueOf("0101", 2);
        a ^= b;
        Assert.assertEquals(a, Integer.valueOf("1001", 2).intValue());
        b ^= a;
        Assert.assertEquals(b, Integer.valueOf("1100", 2).intValue());
        a ^= b;
        Assert.assertEquals(a, Integer.valueOf("0101", 2).intValue());
    }


    @Test(timeout = 3000L, expected = Test.None.class)
    public void testMask() {
        int a = Integer.valueOf("011", 2).intValue();
        Assert.assertEquals(a & flagbit2, flagbit2);
    }

    private String format(String in) {
        if (32 - in.length() > 0)
            return String.format("%0" + (32 - in.length()) + "d%" + in.length() + "s", 0, in);
        return in;
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testShift() {
        Assert.assertEquals(1 << 65, 1 << 65 % 32);
        Assert.assertEquals(-1 << 65, -1 << 65 % 32);

        Assert.assertEquals(8 >> 1, 8 / 2);
        System.out.println("----  -1 >> 1  --------");
        Assert.assertEquals(-1 >> 1, -1);
        System.out.println(format(Integer.toBinaryString(-1)));
        System.out.println(format(Integer.toBinaryString(-1 >> 1)));

        Assert.assertEquals(8 >>> 1, 8 / 2); //Unsigned right shift  >>>
        Assert.assertEquals(-1 >>> 1, Math.pow(2, 31) - 1, 0);
        System.out.println("----  -1 >>> 1  --------");
        System.out.println(format(Integer.toBinaryString(-1)));
        System.out.println(format(Integer.toBinaryString(-1 >>> 1)));

        // shift - distance = sift (32 - distance)
        Assert.assertEquals(2 << -30, 2 << (32 - 30));
        Assert.assertEquals(2 >> -30, 2 >> (32 - 30));
        Assert.assertEquals(2 >>> -30, 2 >>> (32 - 30));
        Assert.assertEquals(-2 << -30, -2 << (32 - 30));
        Assert.assertEquals(-2 >> -30, -2 >> (32 - 30));
        Assert.assertEquals(-2 >>> -30, -2 >>> (32 - 30));
    }
}
