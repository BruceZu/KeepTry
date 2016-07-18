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
 * 231. Power of Two
 *
 * Difficulty: Easy
 * Given an integer, write a function to determine if it is a power of two.
 *
 * Company Tags Google
 * Tags Math Bit Manipulation
 * Similar Problems
 * (E) Number of 1 Bits
 * (E) Power of Three
 * (E) Power of Four
 * =======================================================================
 * Note the ()
 */
public class Leetcode231PowerofTwo {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && ((n & (n - 1)) == 0);
    }
}
