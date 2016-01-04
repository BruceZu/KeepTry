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

package bitwise;

import org.junit.Assert;
import org.junit.Test;

public class BitMask {
    //  Constants to hold bit masks for desired flags
    static final int flagAllOff = 0;  //         000...00000000 (empty mask)
    static final int flagbit1 = 1;    // 2^^0    000...00000001
    static final int flagbit2 = 2;    // 2^^1    000...00000010
    static final int flagbit3 = 4;    // 2^^2    000...00000100
    static final int flagbit4 = 8;    // 2^^3    000...00001000
    static final int flagbit5 = 16;   // 2^^4    000...00010000
    static final int flagbit6 = 32;   // 2^^5    000...00100000
    static final int flagbit7 = 64;   // 2^^6    000...01000000
    static final int flagbit8 = 128;  // 2^^7    000...10000000
    // ...
    static final int flagbit31 = (int) Math.pow(2, 30);   // 2^^30


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

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testShift() {
        int a = Integer.valueOf("01", 2).intValue();
        int b = Integer.valueOf("010", 2).intValue();
        int c = Integer.valueOf("00", 2).intValue();
        int A = 1;
        Assert.assertEquals(a, A);
        Assert.assertEquals(a << 1, A * 2);
        Assert.assertEquals(a >> 1, A / 2);
    }

}
