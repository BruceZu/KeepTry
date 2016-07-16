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
 * <pre>191. Number of 1 Bits  QuestionEditorial Solution  My Submissions
 * Total Accepted: 101492
 * Total Submissions: 269663
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
 *  2 who need >>> and who not
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
 *  @see <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html">operations precedence</a>
 *  @see java.lang.Integer#bitCount(int)
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

    // refer Integer.bitCount(int);
    public static int bitCount(int i) {
        i = i - ((i >>> 1) & 0x55555555);
        i = ((i >>> 2) & 0x33333333) + (i & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        return (i + (i >>> 16)) & 0x3f;
    }
}
