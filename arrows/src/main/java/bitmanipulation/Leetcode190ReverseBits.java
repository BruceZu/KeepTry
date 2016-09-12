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
 * <pre>
 *  190. Reverse Bits
 *
 * Difficulty: Easy
 * Reverse bits of a given 32 bits unsigned integer.
 *
 * For example, given input 43261596 (represented in binary as 00000010100101000001111010011100),
 *                  return 964176192 (represented in binary as 00111001011110000010100101000000).
 *
 * Follow up:
 * If this function is called many times, how would you optimize it?
 *
 * Related problem: Reverse Integer
 *
 *  Company Tags Apple Airbnb
 *  Tags Bit Manipulation
 *  Similar Problems
 *  ===========================================================================================
 *
 *  Idea 1 when it is to move 16 bit columns, need not mask
 *  Idea 2 when it is to move 8 bit columns,
 *
 *     ???? ????, ???? ????, ???? ?????,  ???? ????
 *         l         lm          mr           r
 *
 *     0xff00:
 *
 *     0000 0000, 0000 0000, 1111 1111, 0000 0000
 *
 *     it can be done in one line:
 *     n = (n << 24) | ((n & 0xff00) << 8) | ((n >>> 8) & 0xff00) | (n >>> 24);
 *
 * Idea 3 mask and step are variables idea:
 *    In the loop each time using
 *    n >>> steps & mask | (n & mask) << steps;
 *    do the reverse.
 *    or
 *    n >>> steps & mask | n << steps & ~mask;
 *
 *    init mask:-1 or ~0
 *    1111 1111   1111 1111   1111 1111   1111 1111   mask
 *
 *      loop #1
 *            move steps: half 0f 32: 16;
 *           mask ^= (mask << steps);
 *          0000 0000   0000 0000   1111 1111   1111 1111  mask.  half of old.
 *
 *      loop #2
 *           steps: half of 16: 8;
 *           mask ^= (mask << s);
 *           0000 0000   1111 1111   0000 0000   1111 1111  mask.  half of old.
 *
 *      loop #3
 *           steps: half of 8: 4;
 *          mask ^= (mask << s);
 *           0000 1111   0000 1111   0000 1111   0000 1111 mask.  half of old.
 *
 *      loop #4
 *          steps: half of 4: 2;
 *           mask ^= (mask << s);
 *           0011 0011   0011 0011   0011 0011   0011 0011 mask.  half of old.
 *
 *      loop #5
 *           steps: half of 2: 1;
 *           mask ^= (mask << s);
 *          0101 0101   0101 0101   0101 0101   0101 0101 mask.  half of old.
 *      Done
 *
 * Idea 4:
 *    only reverse the parts from the first '1' bits in binary. then move left bound.
 *
 *    n =  0000 0000 0000 0000, 0000 0000 0000 1010.
 *                                             |  |
 *                                             only reverse this parts
 *    r = n;
 *    Init value:
 *        r has the rightmost one of n on the index of 0, the rightmost one of r.
 *        the steps r need shift the one that ever was the rightmost one of n to left to get reversed one.
 *
 *    seems: r is pulling n, one by one, arond the right bound of the 32 length array towards the r's left bound .
 *    n shift to right one by one. <strong>Note using >>> </strong>. r accept it one by one. till n is 0;
 *
 *    n = 0000 0000 0000 0000, 0000 0000 0000 0000.   |
 *    r = 0000 0000 0000 0000, 0000 0000 0000 0101. <--
 *  index 31                                  3210
 *
 *    now r need shift to left 31-3 = 28 bits to get word done.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html">Java Operator Precedence</a>
 */
public class Leetcode190ReverseBits {
    // you need treat n as an unsigned value
    public int reverseBits4(int n) {
        // O(lg(N))
        n = ((n & 0x55555555) << 1) + (n >>> 1 & 0x55555555);
        n = ((n & 0x33333333) << 2) + (n >>> 2 & 0x33333333);
        n = ((n & 0x0f0f0f0f) << 4) + (n >>> 4 & 0x0f0f0f0f);
        n = ((n & 0x00ff00ff) << 8) + (n >>> 8 & 0x00ff00ff);
        n = (n << 16) + (n >>> 16);
        return n;
    }

    public int reverseBits3(int n) {
        n = ((n & 0x55555555) << 1) + (n >>> 1 & 0x55555555);
        n = ((n & 0x33333333) << 2) + (n >>> 2 & 0x33333333);
        n = ((n & 0x0f0f0f0f) << 4) + (n >>> 4 & 0x0f0f0f0f);
        n = (n << 24) | ((n & 0xff00) << 8) |
                ((n >>> 8) & 0xff00) | (n >>> 24);
        return n;
    }

    public int reverseBits2(int n) {
        // O(lg(N))
        int mask = -1;
        int steps = 32;
        while ((steps >>= 1) > 0) {
            mask ^= mask << steps;
            n = n >>> steps & mask | (n & mask) << steps;
        }
        return n;
    }

    public int reverseBits(int n) {
        int r = n;
        n >>>= 1;   // reversed result
        int s = 31; // steps

        while (n != 0) {
            r = r << 1 | n & 1;
            s--;

            n >>>= 1;
        }
        r <<= s;
        return r;
    }
}
