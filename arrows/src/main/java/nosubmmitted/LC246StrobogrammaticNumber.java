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

package nosubmmitted;

/**
 * 246. Strobogrammatic Number
 * https://leetcode.com/problems/strobogrammatic-number/
 * <pre>
 *
 * Difficulty: Easy
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 *
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string.
 *
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 *
 * Hide Company Tags: Google
 * Hide Tags: Hash Table, Math
 * Hide Similar Problems (M) Strobogrammatic Number II (H) Strobogrammatic Number III
 *
 * </pre>
 */
public class LC246StrobogrammaticNumber {
    boolean isStrob(char ch1, char ch2) {
        switch (ch1) {
            case '0':
                return ch2 == '0';
            case '1':
                return ch2 == '1';
            case '6':
                return ch2 == '9';
            case '9':
                return ch2 == '6';
            case '8':
                return ch2 == '8';
            default:
                return false;
        }
    }

    public boolean isStrobogrammatic(String num) {

        for (int x = 0, y = num.length() - 1; x <= y; x++, y--) {
            if (!isStrob(num.charAt(x), num.charAt(y))) {
                return false;
            }
        }

        return true;
    }

    /**
     * another case
     */
    public boolean isStrobogrammatic2(String num) {
        int low = 0, high = num.length() - 1;
        while (low <= high) {
            int clow = num.charAt(low) - '0';
            int chigh = num.charAt(high) - '0';
            if (((2 <= clow && clow <= 5) || clow == 7) || ((2 <= chigh && chigh <= 5) || chigh == 7)) {
                return false;
            } else {
                if (clow == 6) {
                    if (chigh != 9) return false;
                } else if (clow == 9) {
                    if (chigh != 6) return false;
                } else {
                    if (clow != chigh) return false;
                }
            }
            low++;
            high--;

        }
        return true;
    }
}
