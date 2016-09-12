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

import java.util.ArrayList;
import java.util.List;

/**
 * 247. Strobogrammatic Number II
 * https://leetcode.com/problems/strobogrammatic-number-ii/
 * <pre>
 *     Difficulty: Medium
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 *
 * Find all strobogrammatic numbers that are of length = n.
 *
 * For example,
 * Given n = 2, return ["11","69","88","96"].
 *
 * Hint:
 *
 * Try to use recursion and notice that it should recurse with n - 2 instead of n - 1.
 * Hide Company Tags: Google
 * Hide Tags: Math, Recursion
 * Hide Similar Problems
 * (E) Strobogrammatic Number
 * (H) Strobogrammatic Number III
 *
 * </pre>
 */
public class LC247StrobogrammaticNumberII {
//the fast one beat 98% no code shared
    /**
     * beat 90.67%
     * recursive
     */
    char[][] dict = new char[][]{{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

    public List<String> findStrobogrammatic(int n) {
        List<String> res = new ArrayList<>();
        char[] sofar = new char[n];
        findS(n, n, 0, sofar, res);
        return res;
    }

    private void findS(int n, int m, int j, char[] soFar, List<String> res) {
        if (m <= 0) {
            res.add(new String(soFar));
            return;
        }
        for (int i = 0; i < dict.length; i++) {
            if ((m == 1 && (dict[i][0] == '6' || dict[i][0] == '9')) ||
                    (m > 1 && j == 0 && dict[i][0] == '0')) continue;
            soFar[j] = dict[i][0];
            soFar[n - j - 1] = dict[i][1];
            findS(n, m - 2, j + 1, soFar, res);
        }
    }
    /**
     * other idea: Java Backtracking with StringBuilder
     */
}
