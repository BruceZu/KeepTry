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
 * Assume s and n will never be null.
 *
 * Tags: Binary Search Array Divide and Conquer
 *
 *
 * @see <a href="https://leetcode.com/problems/median-of-two-sorted-arrays/"> link to leetcode </a>
 */
public class Leetcode4MedianofTwoSortedArrays {
}

class Solution {

    /**
     * <pre>
     * Idea:
     * Compare 2 arrays's (left) medians.
     * If they are no same, cut the same numbers of elements(not include current medians), translates to new arrays with same result.
     * till the shorter array have 2 or 1 element left.
     * Focus on medians.
     *
     * 1> keep the shorter array at left, thus only check its length.
     * 2> when it is 2 elements.
     *  when short array length ==2 and m+n is even, 2 cases where we can not divide and conquer:
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
     * @param s  short length array
     * @param is start index of array s
     * @param l  longer array
     * @param il start index of array l
     * @return
     * @see <br>
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html"> Operator Precedence </a>
     */
    public double exe(int[] s, int is, int m, int[] l, int il, int n) {
        if (m > n) {
            return exe(l, il, n, s, is, m);
        }
        if (m == 0) { // start check.
            if ((n & 1) == 1) {
                return l[n >> 1];
            }
            return (l[n / 2 - 1] + l[n / 2]) * 0.5;
        }
        boolean lIsOdd = (n & 1) == 1;
        int mIndex = il + (lIsOdd ? n >> 1 : n / 2 - 1);
        int lRightMIndex = il + n / 2;
        if (m == 1) { // only check the left shorter array
            int v = s[is];
            if (lIsOdd) {
                if (n == 1 || l[mIndex - 1] <= v && v <= l[mIndex + 1]) {
                    return (v + l[mIndex]) * 0.5; //double
                }
                if (v > l[mIndex + 1]) {
                    return (l[mIndex] + l[mIndex + 1]) * 0.5;
                }
                return (l[mIndex - 1] + l[mIndex]) * 0.5;
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
                    return (s[is] + s[is + 1]) * 0.5;
                }

                if (n == 2 && s[is] <= l[il] && l[il + 1] <= s[is + 1]) {
                    return (l[il] + l[lRightMIndex]) * 0.5;
                }
            }

            if (s[is] < l[mIndex]) {
                return exe(s, is + 1, m - 1, l, il, n - 1);
            }
            return exe(s, is, m - 1, l, il + 1, n - 1);
        }

        if (s[smIndex] == l[mIndex]) {
            if (!sIsOdd && !lIsOdd) {
                return (s[smIndex] + (s[smIndex + 1] < l[mIndex + 1] ? s[smIndex + 1] : l[mIndex + 1])) * 0.5;
            }
            return s[smIndex];
        }
        int cut = smIndex - is;
        m = m - cut;
        n = n - cut;
        if (s[smIndex] < l[mIndex]) {
            is = is + cut;
        } else {
            il = il + cut;
        }
        return exe(s, is, m, l, il, n);
    }

    public double findMedianSortedArrays(int[] s, int[] l) {
        assert (s != null);
        assert (l != null);
        return exe(s, 0, s.length, l, 0, l.length);
    }
}

class SolutionByFindKthElement {

    /**
     * <pre>
     * Get the kth element of the ordered merged result of array s and l.
     * If sum is even, then also get the (k+1)th one,
     * but the performance in Leetcode show it is not efficient.
     *
     * Always assume: kth >=1 and kth <= m+n
     *
     * Notes:
     *  1> cases where we should not cut the shorter array: {3}, {1,2,4,5,6}
     *  2> cut and recursive till kth == 1, return the smaller first element of current 2 arrays.
     *     - from left only.
     *     - cut only one array thus the cut number will never include the one in result.
     *     - include the checker to cut, to be able to continue {1,2},{3,4,5,6}
     *  3> keep the shorter array on left, check it is empty firstly.
     *
     *  Focus on kth
     */
    public double[] call(int[] s, int is, int[] l, int il, int kth, boolean sumIsOdd) {
        int m = s.length - is;
        int n = l.length - il;
        if (m > n) {
            return call(l, il, s, is, kth, sumIsOdd);
        }

        if (m == 0 && sumIsOdd) {
            return new double[]{l[il + kth - 1] * 1d};
        }
        if (m == 0) {
            return new double[]{l[il + kth - 1] * 1d, l[il + kth] * 1d};
        }
        if (kth == 1) {
            double first = s[is] < l[il]
                    ? s[is] * 1d
                    : l[il] * 1d;
            Double second = null;
            if (!sumIsOdd) {
                if (first == s[is]) {
                    if (m == 1) {
                        second = l[il] * 1d;
                    } else {
                        second = s[is + 1] < l[il] ? s[is + 1] * 1d : l[il] * 1d;
                    }
                } else {
                    if (n == 1) {
                        second = s[is] * 1d;
                    } else {
                        second = l[il + 1] < s[is] ? l[il + 1] * 1d : s[is] * 1d;
                    }
                }
            }
            return new double[]{first, second};
        }
        int scut = kth * m / (m + n);
        scut = scut < 1 ? 1 : scut;
        int sCheckIndex = is + scut - 1;
        int lCheckIndex = il + kth - scut - 1;

        if (s[sCheckIndex] == l[lCheckIndex]) {
            return new double[]{s[sCheckIndex] * 1d,
                    s[sCheckIndex + 1] < l[lCheckIndex + 1]
                            ? s[sCheckIndex + 1] * 1d
                            : l[lCheckIndex + 1] * 1d};
        }

        if (s[sCheckIndex] < l[lCheckIndex]) {
            is = is + scut;
            kth = kth - scut;
        } else if (l[lCheckIndex] < s[sCheckIndex]) {
            il = il + kth - scut;
            kth = scut;
        }
        return call(s, is, l, il, kth, sumIsOdd);
    }

    public double findMedianSortedArrays(int[] s, int[] l) {
        assert (s != null);
        assert (l != null);
        int m = s.length, n = l.length;
        boolean sumIsOdd = ((m + n) & 1) == 1;
        int kth = sumIsOdd ? (m + n) / 2 + 1 : (m + n) / 2; // (left) median element
        double[] r = call(s, 0, l, 0, kth, sumIsOdd);
        return sumIsOdd ? r[0] : (r[0] + r[1]) * 0.5;
    }
}

/**
 * Improved:
 * <pre>
 * 1> Change the way of select checker in arrays, both arrays select the k/2th element to compare.
 *    As results:
 *    -  Do not need check sv == lv. Because
 *       when k is odd, kth/2 == (kth -1)/2. The sum of index(sv)+1 and index(lv) +1 does not equal to kth.
 *       so when even sv == lv, sv is not the result.
 *       e.g. k = 3, {1,2}{1,2}
 *    -  Do not need length variables m and n.
 *
 * 2> If sum is even, get the (k+1)th one by run again separately.
 * 3> do not keep the shorter array at left, thus check both arrays' valid length is 0 or not firstly.
 *
 * Other:
 *    convert int to double: using 1d to replace 1D, using 0.5 to replace 1d/2
 */
class SolutionByFind1KthElementImproved {
    public double call(int[] s, int is, int[] l, int il, int kth) {
        if (is == s.length) {
            return l[il + kth - 1] * 1d;
        }
        if (il == l.length) {
            return s[is + kth - 1] * 1d;
        }
        if (kth == 1) {
            return s[is] < l[il] ? s[is] * 1d : l[il] * 1d;
        }
        int sv = is + kth / 2 - 1 < s.length ? s[is + kth / 2 - 1] : Integer.MAX_VALUE;
        int lv = il + kth / 2 - 1 < l.length ? l[il + kth / 2 - 1] : Integer.MAX_VALUE;
        if (sv < lv) {
            return call(s, is + kth / 2, l, il, kth - kth / 2);
        }
        return call(s, is, l, il + kth / 2, kth - kth / 2);
    }

    public double findMedianSortedArrays(int[] s, int[] l) {
        assert (s != null);
        assert (l != null);
        int m = s.length, n = l.length;
        boolean sumIsOdd = ((m + n) & 1) == 1;
        int kth = sumIsOdd ? (m + n) / 2 + 1 : (m + n) / 2; // (left) median element
        double median = call(s, 0, l, 0, kth);
        if (!sumIsOdd) {
            return (median + (call(s, 0, l, 0, kth + 1))) / 2;
        }
        return median;
    }
}
