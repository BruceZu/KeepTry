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
 * 338. Counting Bits
 * Difficulty: Medium
 *
 * Given a non negative integer number num.
 * For every numbers i in the range 0 ≤ i ≤ num
 * calculate the number of 1's in their binary representation and return them as an array.
 *
 * Example:
 * For num = 5 you should return [0,1,1,2,1,2].
 *
 * Follow up:
 *
 *      It is very easy to come up with a solution with run time O(n*sizeof(integer)).
 *      But can you do it in linear time <strong>O(n)</strong>. /possibly in a single pass?
 *      Space complexity should be <strong>O(n)</strong>.
 *
 * Can you do it like a boss?
 * Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
 *
 * Hint:
 *
 *      You should make use of what you have produced already.
 *      Divide the numbers in ranges like [2-3], [4-7], [8-15] and so on. And try to generate new range from previous.
 *      Or does the odd/even status of the number help you in calculating the number of 1s?
 *
 * Tags
 *   Dynamic Programming
 *   Bit Manipulation
 * Similar Problems (E) Number of 1 Bits
 * ==================================================================================================================
 * // watch and find rules by:
 * <code>
 *
 *              for (int i = 1; i < 256; i++) {
 *                  System.out.println(String.format("%31s", Integer.toBinaryString(i)));
 *              }
 *
 * </code>
 */
public class Leetcode338CountingBits {
    public static int[] countBits(int num) {
        if (num == 0) {
            return new int[]{0};
        }
        int[] r = new int[num + 1];
        r[0] = 0;

        double power = 0;
        boolean done = false;
        while (!done) {
            int from = (int) Math.pow(2d, power);
            int to = (int) Math.pow(2d, power + 1) - 1;
            for (int cur = from; cur <= to; cur++) {
                int bits = 1 + r[cur - from];
                r[cur] = bits;
                if (cur == num) {
                    done = true;
                    break;
                }
            }
            power++;
        }

        return r;
    }

    public static int[] countBits2(int num) {
        int[] r = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            r[i] = r[i >> 1] + (i & 1);
        }
        return r;
    }

    public static int[] countBits3(int num) {
        int[] r = new int[num + 1];
        int p = 1; // pow of 2
        for (int i = 1; i <= num; i++) {
            if (i == p * 2) {
                p = p * 2;
            }
            r[i] = 1 + r[i - p];
        }
        return r;
    }

    public static int[] countBits4(int num) {
        int[] r = new int[num + 1];
        for (int i = 1; i < r.length; i++) {
            r[i] = r[i & (i - 1)] + 1;
        }
        return r;
    }
}
