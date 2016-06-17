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
    public static void main(String[] args) {
        System.out.println(new SolutionByFindKthElement().findMedianSortedArrays(
                new int[]{1, 4, 5, 6}, new int[]{2, 3, 7, 8}
        ));
    }
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
            return exe(s, is + cut, m - cut, l, il, n - cut);
        } else if (s[smIndex] > l[mIndex]) {
            return exe(s, is, m - cut, l, il + cut, n - cut);
        } else { // Same
            if (!sIsOdd && !lIsOdd) {
                return (s[smIndex] + (s[smIndex + 1] < l[mIndex + 1] ? s[smIndex + 1] : l[mIndex + 1])) * 1d / 2;
            }
            return s[smIndex];
        }
    }

    //Assume s and n are null at the same time.
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

class SolutionByFindKthElement {

    /**
     * <pre>
     * Get the kth element of the ordered merged result of array s and l.
     * if sumIsOdd is false then also get the (k+1)th one.
     *
     * Assume the kth >=1 and kth <= m+n
     *
     * 1> cases where we should not cut the shorter array: {3}, {1,2,4,5,6}
     * 2> always calculate from left, thus the cut number will never great than kth
     * 3> always include the checker to cut, to be able to continue {1,2},{3,4,5,6}
     * 4> keep the shorter array on left, check it is empty firstly
     */
    public Double[] call(int[] s, int is, int m, int[] l, int il, int n, int kth, boolean sumIsOdd) {
        if (m == 0 && sumIsOdd) {
            return new Double[]{l[il + kth - 1] * 1D};
        }
        if (m == 0) {
            return new Double[]{l[il + kth - 1] * 1D, l[il + kth] * 1D};
        }
        if (kth == 1) {
            double first = s[is] < l[il]
                    ? s[is] * 1D
                    : l[il] * 1D;
            Double second = null;
            if (!sumIsOdd) {
                if (first == s[is]) {
                    if (m == 1) {
                        second = l[il] * 1D;
                    } else {
                        second = s[is + 1] < l[il] ? s[is + 1] * 1D : l[il] * 1D;
                    }
                } else {
                    if (n == 1) {
                        second = s[is] * 1D;
                    } else {
                        second = l[il + 1] < s[is] ? l[il + 1] * 1D : s[is] * 1D;
                    }
                }
            }
            return new Double[]{first, second};
        }
        int numberS = kth * m / (m + n);
        numberS = numberS < 1 ? 1 : numberS;
        int sCheckIndex = is + numberS - 1;
        int lCheckIndex = il + kth - numberS - 1;

        int cutsum;
        if (s[sCheckIndex] < l[lCheckIndex]) {
            cutsum = sCheckIndex - is + 1;
            return m - cutsum <= n
                    ? call(s, is + cutsum, m - cutsum, l, il, n, kth - cutsum, sumIsOdd)
                    : call(l, il, n, s, is + cutsum, m - cutsum, kth - cutsum, sumIsOdd);
        } else if (l[lCheckIndex] < s[sCheckIndex]) {
            cutsum = lCheckIndex - il + 1;
            return m <= n - cutsum
                    ? call(s, is, m, l, il + cutsum, n - cutsum, kth - cutsum, sumIsOdd)
                    : call(l, il + cutsum, n - cutsum, s, is, m, kth - cutsum, sumIsOdd);
        } else { // Same
            return new Double[]{s[sCheckIndex] * 1D,
                    s[sCheckIndex + 1] < l[lCheckIndex + 1]
                            ? s[sCheckIndex + 1] * 1D
                            : l[lCheckIndex + 1] * 1D};
        }
    }

    //Assume s and n are null at the same time.
    public double findMedianSortedArrays(int[] s, int[] l) {
        int m = s.length, n = l.length;
        boolean sumIsOdd = ((m + n) & 1) == 1;
        int kth = sumIsOdd ? (m + n) / 2 + 1 : (m + n) / 2; // (left) median element
        Double[] median = m <= n
                ? call(s, 0, m, l, 0, n, kth, sumIsOdd)
                : call(l, 0, n, s, 0, m, kth, sumIsOdd);
        return sumIsOdd ? median[0] : (median[0] + median[1]) * 1d / 2;
    }
}

