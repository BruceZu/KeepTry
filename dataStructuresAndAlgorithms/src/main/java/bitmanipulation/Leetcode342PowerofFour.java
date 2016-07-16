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
 * Given an integer (signed 32 bits), write a function to check whether it is a power of 4.
 *
 * Example:
 * Given num = 16, return true. Given num = 5, return false.
 *
 * Follow up: Could you solve it without loops/recursion?
 *
 *
 *  Company Tags Two Sigma
 *  Tags Bit Manipulation
 *  Similar Problems (E) Power of Two (E) Power of Three
 *  ===================================================================================
 *  4^0  0000 0000 0000 0000 0000 0000 0000 0001
 *                                             |
 *  4^1  0000 0000 0000 0000 0000 0000 0000 0100
 *                                           |
 *  4^2  0000 0000 0000 0000 0000 0000 0001 0000
 *                                        |
 *  4^3  0000 0000 0000 0000 0000 0000 0100 0000
 *                                      |
 *  4^4  0000 0000 0000 0000 0000 0001 0000 0000
 *                                   |
 *  4^5  0000 0000 0000 0000 0000 0100 0000 0000
 *                                 |
 *  4^6  0000 0000 0000 0000 0001 0000 0000 0000
 *                              |
 *  4^7  0000 0000 0000 0000 0100 0000 0000 0000
 *                            |
 *  0x55555555
 *       0101 0101 0101 0101 0101 0101 0101 0101
 *  0xAAAAAAAA
 *       1010 1010 1010 1010 1010 1010 1010 1010
 */
public class Leetcode342PowerofFour {
    public static boolean isPowerOfFour(int num) {
        return num > 0 && ((num - 1) & num) == 0 && (num & 0x55555555) == num;
        //return num > 0 && ((num - 1) & num) == 0 && Integer.numberOfTrailingZeros(num) % 2 == 0;
    } 
}
