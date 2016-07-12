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
 * 248. Strobogrammatic Number III
 * https://leetcode.com/problems/strobogrammatic-number-iii/
 * <pre>
 *
 *  Difficulty: Hard
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 *
 * Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
 *
 * For example,
 * Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.
 *
 * Note:
 * Because the range might be a large number, the low and high numbers are represented as string.
 *
 * Hide Tags: Math, Recursion
 * Hide Similar Problems (E) Strobogrammatic Number (M) Strobogrammatic Number II
 *
 * </pre>
 */
public class LC248StrobogrammaticNumberIII {


    /**
     * beat 99.52
     * ans = all strobogrammatic of [low.length~high.length-1] + all strobogrammatic of of
     * high.length and no larger than high - all strobogrammatic of of low.length and smaller than low.
     */
    private static final char[][] stroPairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

    public int strobogrammaticInRange(String low, String high) {
        char[] h = high.toCharArray(), l = low.toCharArray();
        h[h.length - 1]++;
        if (h.length < l.length || (h.length == l.length && comp(l, h, 0) == 0)) return 0; // low > high
        int sum = 0;
        for (int len = low.length(); len < high.length(); sum += stroN(len), len++) ;
        return sum + stroSmallerThan(h) - stroSmallerThan(l);
    }

    private int stroFullN(int len) {
        if (len == 0) return 1; // ""
        if (len == 1) return 3; // 0,1,8
        return 5 * stroFullN(len - 2); // 0...0,1...1,8...8,6...9,9...6
    }

    private int stroN(int len) {
        if (len < 2) return stroFullN(len);
        return 4 * stroFullN(len - 2);
    }

    private int stroSmallerThan(char[] limit) { //count the stros WITH limit's length and SMALLER THAN limit.
        int len = limit.length;
        char[] cur = new char[len];
        return stroSmallerThan(0, len - 1, cur, limit);
    }

    private int stroSmallerThan(int i, int j, char[] cur, char[] limit) {
        int sum = 0;
        if (j < i)
            return comp(cur, limit, i);
        if (j == i) {
            for (char[] pair : stroPairs)
                if (pair[0] == pair[1] && pair[0] <= limit[i])
                    if (pair[0] < limit[i])
                        sum++;
                    else {
                        cur[i] = pair[0];
                        sum += comp(cur, limit, i);
                    }
            return sum;
        }

        for (char[] pair : stroPairs) {
            if (pair[0] < limit[i]) {
                if (i != 0 || pair[0] != '0')
                    sum += stroFullN(j - i - 1);
            } else if (pair[0] == limit[i]) {
                cur[i] = pair[0];
                cur[j] = pair[1];
                sum += stroSmallerThan(i + 1, j - 1, cur, limit);
            }
        }
        return sum;
    }

    int comp(char[] cur, char[] limit, int st) { //return 1 if cur < limit else 0
        for (int i = st; i < cur.length; i++) {
            if (cur[i] < limit[i]) return 1;
            else if (cur[i] > limit[i]) return 0;
        }
        return 0;
    }

    /**
     * same as above Basic Idea:
     * return all valid nums under upper (inclusive) - all valid nums under low (exclusive).
     * Suppose upper has length len.
     * The numbers of valid nums of len_i's < len, can be very efficiently computed using recursion or Math.
     * pow();.
     * For valid nums with len, construct them all and aggressively discard them if they are higher than upper (pruning).
     * After all, char array comparison is cheap : if(compareCharArray(chs, upper, i) > 0) break;
     */
    private static char[][] pairs = new char[][]{{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

    public int strobogrammaticInRange2(String low, String high) {
        if (low.length() > high.length() || low.length() == high.length() && high.compareTo(low) < 0) return 0;
        return strobogrammaticInRangeFrom0(high, true) - strobogrammaticInRangeFrom0(low, false);
    }

    private int strobogrammaticInRangeFrom0(String num, boolean inclusive) {
        int len = num.length();
        if (len == 1) {
            if (num.charAt(0) == '0') return inclusive ? 1 : 0;       // 0?
            else if (num.charAt(0) == '1') return inclusive ? 2 : 1;       // 0,1?
            else if (num.charAt(0) < '8') return 2;                       // 0,1
            else if (num.charAt(0) == '8') return inclusive ? 3 : 2;       // 0,1,8?
            else return 3;                       // 0,1,8
        }
        int sum = 0;
        for (int i = 1; i < len; i++)
            sum += strobogrammaticDigit(i, true);
        sum += strobogrammaticInRangeSameDigits(new char[len], 0, len - 1, num.toCharArray(), inclusive);
        return sum;
    }

    private int strobogrammaticInRangeSameDigits(char[] chs, int i, int j, char[] upper, boolean inclusive) {
        int sum = 0;
        if (i > j) {
            if (inclusive && compareCharArray(upper, chs, chs.length - 1) >= 0 || !inclusive && compareCharArray(upper, chs, chs.length - 1) > 0)
                return 1;
            else return 0;
        }
        for (char[] pair : pairs) {
            if (i == 0 && pair[0] == '0' || i == j && (pair[0] == '6' || pair[0] == '9')) continue;
            chs[i] = pair[0];
            chs[j] = pair[1];
            if (compareCharArray(chs, upper, i) > 0) break;
            sum += strobogrammaticInRangeSameDigits(chs, i + 1, j - 1, upper, inclusive);
        }
        return sum;
    }

    private int strobogrammaticDigit(int digit, boolean outside) {
        if (digit == 0) return 1;
        if (digit == 1) return 3;
        return outside ? strobogrammaticDigit(digit - 2, false) * 4 : strobogrammaticDigit(digit - 2, false) * 5;
    }

    private int compareCharArray(char[] arr1, char[] arr2, int idx) {
        for (int i = 0; i <= idx; i++)
            if (arr1[i] == arr2[i]) continue;
            else if (arr1[i] > arr2[i]) return 1;
            else return -1;
        return 0;
    }

    /**
     * other idea
     * The idea is based on the fact that it's very easy to compute the count for any given length without a limit.
     * That's just 4 * 5 ^ (len / 2 - 1), multiply by 3 if the length is odd. Or 5 ^ (len / 2) if a leading zero is allowed,
     * again multiply by 3 if necessary.

     This is all good for lengths between low.length() and high.length(), exclusive.
     But what if there is a limit? Well, first let's calculate the count without any limits.
     Then subtract the count of numbers that do not satisfy the limit. But how do we compute that?

     Suppose it's the low limit. Then we need to find numbers below it, so it becomes a high limit.
     OK, so we can take any strobo numbers with an MSD less than its MSD and they will all satisfy the limit.
     And these numbers are composed by substituting any (no limit) strobo numbers,
     including ones starting with 0, in the middle and adjusting the LSD.

     Then we have to add the count of numbers with the same MSD, but also satisfying the limit.
     This can be done by counting numbers of length decreased by 2 with the limit being the middle part of our limit.
     It can become an inclusive limit depending on the value of MSD, but it can be done nevertheless.

     About half of the code is for a special case of 1-2 digits. It can probably be made more concise,
     but it's not that important since it's isolated. It didn't even fit within post limit, so I left it out.
     It can be replaced by pre-computed arrays for further performance improvements.

     Reentrant caching of powers of 5 is also included, although I doubt it gives any real performance boost.

     ==
     other idea:
     The basic idea is to find generate a list of strobogrammatic number with the length between the length of lower
     bound and the length of upper bound.
     Then we pass the list and ignore the numbers with the same length of lower bound or upper bound but not in the range.
     */
}
