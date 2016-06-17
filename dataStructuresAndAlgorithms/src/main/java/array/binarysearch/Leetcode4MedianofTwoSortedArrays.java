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

package array.binarysearch;

/**
 * <pre>
 * Difficulty: Hard
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 *
 * Tags: Binary Search Array Divide and Conquer
 */
public class Leetcode4MedianofTwoSortedArrays {
}

/**
 * <pre>
 *
 *  Idea: find the middle element(left element when it is even array).
 *  compare, divide and conquer. translated to updated arrays with same median(s)
 *  till the shorter array have 2 or 1 element.
 *
 *  Why care about the 2 elements shorter array?
 *  when short array length ==2 and m+n is even, 2 cases where we can not divide and conquer :
 *  A> Two median are in short array
 *        3 4
 *        | |
 *
 *   1  2     5  6
 *      |     |
 *
 *
 *  B> Two medians are in long array which only have 2 elements.
 *
 *     1     4
 *     |     |
 *
 *       2  3
 *       |  |
 *  Other cases: can divide and conquer one for both arrays and translate it to next sub question:
 *  0 or one the expected two median are in short array
 *
 *     2   4                 2  3                1  2
 *     |   |                 |  |                |  |
 *
 *   1   3   5  6          1       4   5  6              3  4   5  6
 *       |   |                     |   |                    |   |
 */
class Solution {

    /**
     * @param s  short length array
     * @param is start index of array s
     * @param m  length of s
     * @param l  longer array
     * @param il start index of array l
     * @param n  length of array l
     * @return
     * @see <br>
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html">
     * Operator Precedence </a>
     */
    public double exe(int[] s, int is, int m, int[] l, int il, int n) {
        boolean lIsOdd = (n & 1) == 1;
        int mIndex = il + (lIsOdd ? n >> 1 : n / 2 - 1);
        int lRightMIndex = il + n / 2;
        if (m == 1) {
            int v = s[is];
            if (lIsOdd) {
                if (n == 1 || l[mIndex - 1] <= v && v <= l[mIndex + 1]) {
                    return (v + l[mIndex]) * 1d / 2; //double
                }
                if (v > l[mIndex + 1]) {
                    return (l[mIndex] + l[mIndex + 1]) * 1d / 2;
                }
                return (l[mIndex - 1] + l[mIndex]) * 1d / 2;
            } else {
                if (l[mIndex] <= v && v <= l[lRightMIndex]) {
                    return v;
                }
                if (l[lRightMIndex] < v) {
                    return l[lRightMIndex];
                }
                return l[mIndex];
            }
        }
        // median index, when the length is even using the left median index
        boolean sIsOdd = (m & 1) == 1;
        int smIndex = is + (sIsOdd ? m >> 1 : m / 2 - 1);
        if (m == 2) {
            if (!lIsOdd) {
                if (l[mIndex] <= s[is] && s[is + 1] <= l[lRightMIndex]) {
                    return (s[is] + s[is + 1]) * 1d / 2;
                }

                if (n == 2 && s[is] <= l[il] && l[il + 1] <= s[is + 1]) {
                    return (l[il] + l[lRightMIndex]) * 1d / 2;
                }
            }

            if (s[is] < l[mIndex]) {
                return exe(s, is + 1, 1, l, il, n - 1);
            }
            return exe(s, is, 1, l, il + 1, n - 1);
        }

        int cut = smIndex - is;
        if (s[smIndex] < l[mIndex]) {
            return exe(s, smIndex, m - cut, l, il, n - cut);
        } else if (s[smIndex] > l[mIndex]) {
            return exe(s, is, m - cut, l, il + cut, n - cut);
        } else { // Same
            if (!sIsOdd && !lIsOdd) {
                return (s[smIndex] + (s[smIndex + 1] < l[mIndex + 1] ? s[smIndex + 1] : l[mIndex + 1])) * 1d / 2;
            }
            return s[smIndex];
        }
    }

    public double findMedianSortedArrays(int[] s, int[] l) {
        int m = s.length, n = l.length;
        int lmIndex;
        int rmIndex;
        if (s != null && (l == null || n == 0)) {
            if ((m & 1) == 1) {
                return s[m >> 1];
            }
            lmIndex = m / 2 - 1;
            rmIndex = m / 2;
            return (s[lmIndex] + s[rmIndex]) * 1d / 2;
        }
        if ((s == null || m == 0) && l != null) {
            if ((n & 1) == 1) {
                return l[n >> 1];
            }
            lmIndex = n / 2 - 1;
            rmIndex = n / 2;
            return (l[lmIndex] + l[rmIndex]) * 1d / 2;
        }
        return m <= n
                ? exe(s, 0, m, l, 0, n)
                : exe(l, 0, n, s, 0, m);
    }
}
