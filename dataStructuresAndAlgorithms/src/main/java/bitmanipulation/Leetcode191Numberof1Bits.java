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

package bitmanipulation;

/**
 * <pre>191. Number of 1 Bits
 *
 * Difficulty: Easy
 * Write a function that takes an unsigned integer and returns the number of ’1' bits it has
 * (also known as the Hamming weight).
 *
 * For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011,
 * so the function should return 3.
 *
 *  Company Tags Microsoft Apple
 *  Tags Bit Manipulation
 *  Similar Problems (E) Reverse Bits (E) Power of Two (M) Counting Bits
 *  =====================================================================
 *  1 operations precedence: + <<<<  & ^ |
 *
 *  2 who need >>> and who not. The unsigned right shift operator ">>>" shifts a zero into the leftmost position
 *
 *  3 number of hexadecimal
 *
 *  4 if >>> first then need only one hexadecimal mask number
 *
 *  5  1 shift, mask both with 1, add  got at most number 2, 0b10, so need 2 valid bits.
 *               |
 *             0x55555555 mask is needed in advance.
 *
 *     2 shift, mask both with 2, add. got at most number 4, 0b100,
 *               |
 *             got 4 valid bits.
 *
 *       e.g. result of -1
 *          0100  0100;  0100  0100;  0100  0100;  0100  0100
 *           |||   |||    |||   |||    |||   |||    |||   |||
 *
 *     4 shift will get at most 8, Ob1000, so 4 valid bits is still enough.
 *       so do 4 shift, both without mask 4, add.
 *       e.g. result of -1
 *          0100  1000;  0100  0100;  0100  0100;  0100  0100
 *           x    ||||   x     ||||    x    ||||    x    ||||
 *
 *       for next add, mask the dirty parts by mask it with 0x0f0f0f0f
 *          0000  1000;  0000  0100;  0000  0100;  0000  0100
 *                ||||         ||||         ||||         ||||
 *
 *       after shift 8 and 16, at most got number 32.  0b 0010 0000.
 *          0000  1000;  0000  0100;  0000  0100;  0000  0100
 *                  x           x            x       ||  ||||
 *
 *       mask with with is 0b0011 1111, 0x3f
 *
 *   7   i                - ((i >>> 1) & 0x55555555);  is same as
 *       (i & 0x55555555) + ((i >>> 1) & 0x55555555);
 *
 *       count the bits of our two-bit number i by  i-j,
 *              j = ((i >> 1) & 0b01)
 *              i           j         i - j
 *              ----------------------------------
 *              0 = 0b00    0 = 0b00    0 = 0b00
 *              1 = 0b01    0 = 0b00    1 = 0b01
 *              2 = 0b10    1 = 0b01    1 = 0b01
 *              3 = 0b11    1 = 0b01    2 = 0b10
 *   8   0x01010101 = 0b 0000 0001 0000 0001 0000 0001 0000 0001
 *                  = (1 << 24) + (1 << 16) + (1 << 8) + 1
 *
 *  @see <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html">operations precedence</a>
 *  @see java.lang.Integer#bitCount(int)
 *
 *  @see <a href="variable-precision SWAR algorithm to perform a tree reduction adding the bits in a 32-bit value">
 *      SWAR</a>
 * @see <a href="http://stackoverflow.com/questions/22081738/how-variable-precision-swar-algorithm-works">SWAR</a>
 * @see <a href="https://en.wikipedia.org/wiki/SWAR">SWAR</a>
 */
public class Leetcode191Numberof1Bits {

    // you need to treat n as an unsigned value
    public static int hammingWeight(int i) {
        i = (i & 0x55555555) + ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = i + (i >>> 4);
        i &= 0x0f0f0f0f;
        i = i + (i >>> 16);
        i = i + (i >>> 8);
        return i & 0x3f;
    }

    public static int bitCount(int i) {
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        return (i + (i >>> 16)) & 0x3f;
    }

    public static int hammingWeight2(int i) {
        i = i - ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        i = (((i + (i >> 4)) & 0x0f0f0f0f) * 0x01010101) >> 24;
        return i;
    }

    //  when the number of '1' is far less than that of '0'
    public static int hammingWeight3(int i) {
        int count = 0;
        while (i != 0) {
            count++;
            i = i & (i - 1);
        }
        return count;
    }

    public static int hammingWeight4(int i) {
        int count = 0;
        do {
            if ((i & 1) == 1) {
                count++;
            }
            i = i >>> 1;
        } while (i != 0);
        return count;
    }

    public static int hammingWeight5(int i) {
        int count = 0;
        for (int step = 0; step <= 31; step++) {
            if ((i & (1 << step)) != 0)
                count++;
        }
        return count;
    }

    private static byte[] map = new byte[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2,
            3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3,
            4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3,
            4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3,
            4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5,
            6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3,
            4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5,
            6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
            5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5,
            6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5,
            6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6,
            7, 6, 7, 7, 8};
    public static int hammingWeight6(int n) {
        // map: number to its Population Count size = 256; max value 2^8-1=255
        int count = map[n & 0xff] // 0xff = 0b00000000000000000000000011111111
                + map[(n >> 8) & 0xff]
                + map[(n >> 16) & 0xff]
                + map[(n >> 24) & 0xff];
        return count;
    }
}
