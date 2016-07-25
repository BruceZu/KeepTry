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
 * 201. Bitwise AND of Numbers Range
 *
 * Difficulty: Medium
 * Given a range [m, n] where 0 <= m <= n <= 2147483647,
 * return the bitwise AND of all numbers in this range, inclusive.
 *
 * For example, given the range [5, 7], you should return 4.
 *
 * Tags Bit Manipulation
 * ==============================================================
 * Integer.MAX_VALUE = 2147483647
 * if the result is not 0, then sure m and n are in same field of 2^i~2^(i+1)-1
 *                                   and on the left they have same 0 or more bits
 */
public class Leetcode201BitwiseANDofNumbersRange {

    public int rangeBitwiseAnd(int m, int n) {
        if (m == 0 || m == n) {
            return m;
        }
        int steps = 0;
        while (true) {
            m >>>= 1;
            n >>>= 1;
            steps++;
            if (m == n) {
                return m << steps;
            }
            if (m == 0) {
                return 0;
            }
        }
    }

    // slow
    public int rangeBitwiseAnd2(int m, int n) {
        while (n > m) {
            n &= n - 1; //  n -= (n & -n);
        }
        return n;
    }

    public int rangeBitwiseAnd3(int m, int n) {
        int a = m & n;
        if (a == 0 || m == n) return a;
        int x = m ^ n;
        int steps = 0;
        while (x != 0) {
            x >>>= 1;
            steps++;
        }
        return m >>> steps << steps;
    }
}
