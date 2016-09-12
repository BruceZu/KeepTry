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

package binarysearch;

// Divide two integers without using multiplication, division and mod operator.
// If it is overflow, return MAX_INT.
// https://leetcode.com/problems/divide-two-integers/

public class Leetcode29DivideTwoIntegers {
    public static int divide(int dividend, int divisor) {
        if (0 == divisor) {
            return Integer.MAX_VALUE;
        }
        if (Integer.MIN_VALUE == divisor) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        if (0 == dividend) {
            return 0;
        }
        if (Integer.MIN_VALUE == dividend) {
            if (divisor == Integer.MIN_VALUE) {

                return 1;
            }
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            int abs = (divisor ^ (divisor >> 31)) - (divisor >> 31);
            return divide(Integer.MIN_VALUE + abs, divisor)
                    + divide(-abs, divisor);
        }
        boolean numberIsPositive = true;
        boolean divisorIsPositive = true;
        if (dividend < 0) {
            dividend = ~dividend + 1;
            numberIsPositive = false;
        }
        if (divisor < 0) {
            divisor = ~divisor + 1;
            divisorIsPositive = false;
        }

        if (dividend < divisor) {
            return 0;
        }

        int times = 0;
        while (dividend >= divisor) {
            int i = 1;
            int shiftedDivisor = divisor;

            while (true) {
                int nextDivisor = shiftedDivisor << 1;
                if (nextDivisor < 0 || dividend < nextDivisor) {
                    break;
                }

                i = i << 1;
                shiftedDivisor = nextDivisor;
            }

            times += i;
            dividend = dividend - shiftedDivisor;
        }
        if (!numberIsPositive && !divisorIsPositive) {
            return times;
        }
        if (!numberIsPositive || !divisorIsPositive) {
            return -times;
        }
        return times;
    }
}
