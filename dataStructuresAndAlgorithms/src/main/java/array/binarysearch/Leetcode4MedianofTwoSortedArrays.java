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
 * The overall run time complexity should be <strong> O(log (m+n)) </strong>.
 *
 * <strong>Assume s and n will never be null</strong>
 *
 * Company Tags: Google Zenefits Microsoft Apple Yahoo Dropbox Adobe
 * Tags:
 *      Binary Search
 *      Array
 *      Divide and Conquer
 *
 *
 * ==========================================================================
 *
 *
 *    Compare medians of 2 arrays(left median if its length is even). [Binary Search]
 *         if '=': Done. may be need calculate the right median.
 *         else '<' or '>': translate the same question to new pairs arrays with same answer by cutting
 *              same size elements from both arrays:[Divide and Conquer]
 *
 *              numbers to cut: the smaller arrays's half, not include current median.
 *                              when the shorter array has 2 elements left, there is 2 exception
 *                              cases A, B as following.
 *              where to cut: from the side where it is sure the answer does not exist.
 *
 *              Till can not cut again, it means current median in the smaller array will still
 *              be median in next recursion, it means only 1 or 2 elements left in the shorter
 *              array.
 *              Then calculate the answers
 *              e.g.
 *              shorter:  1 2 3
 *                          |
 *              longer:         4 5 6 7 8 9
 *                                  |
 *              after cut:
 *              shorter:    2 3
 *                          |
 *              longer:         4 5 6 7 8
 *                                  |
 * Runtime:  each time cut half of odd shorter array, or half-1 of even shorter array.
 *           Big O(logm), m is the size of shorter array.
 * Skill:
 * 1> Focus on medians.
 *                  length:    even            odd           next-even
 *                   e.g.:      4               5              6
 *       the kth of median    length/2       (length+1)/2   length/2
 *          or left median:     or              or           or
 *                            (length+1)/2   (length+1)/2   (length+1)/2
 *
 *       its index is kth -1:       (length+1)/2 -1
 *
 * 1> keep the shorter array at left:
 *         thus know which is the shorter, and only check its length to calculate the cut size for the longer array.
 *
 * 2> Cut members:
 *   -  why cut members should not include the current median? the median maybe the answer.
 *   -  2 exceptions when short array has only 2 elements left
 *                        &&
 *                        longer+shorter = even
 *      A> the answer: two median are in short array.
 *         can continue cut, but do not do it, if cut 4, it will be wrong.
 *
 *                3 4
 *                | |
 *           1  2     5  6
 *              |     |
 *
 *
 *     B> the answer: two medians are in long array which only have 2 elements.
 *        cannot continue cut, but if longer have more than 3, as the answer is in the longer, still can cut.
 *
 *           1      4
 *           |      |
 *             2  3
 *             |  |
 *
 *  Other cases: can continue divide and conquer half size on both arrays, translate it to next sub question
 *     2   4                 2  3                1  2
 *     |   |                 |  |                |  |
 *
 *   1   3   5  6          1       4   5  6              3  4   5  6
 *       |   |                     |   |                    |   |
 *
 *  3> compare two medians, when same:
 *      if lsize+ ssize is odd, sure one array is odd and one is even
 *                       even, odd, even, odd
 *                       even, even odd   odd
 *                       --------------------
 *                       even  odd  odd   even
 *
 *      take shorter and longer in account:
 *
 *                       xs     x         x
 *                       xb     xo        x
 *            result: (x+s)/2   x         x
 *  4> when only 1 element left for shorter:
 *   shorter and odd longer:
 *       answer is average of 2 medians
 *       v may be in 3 periods
 *       Note: corner case: <strong>only m in longger </strong>
 *
 *         1   [   2      ]   3
 *            pre  m  next
 *
 *  shorter and even longer:
 *      answer is one media
 *      v may be in 3 periods
 *
 *       1  [     2        ]  3
 *          m   rightm(next)
 *
 * other ideas
 * {@link Leetcode4MedianofTwoSortedArrays2},
 * {@link Leetcode4MedianofTwoSortedArrays3},
 * {@link Leetcode4MedianofTwoSortedArrays2_2}
 * @see <br>
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html"> Operator Precedence </a>
 * @see <a href="https://leetcode.com/problems/median-of-two-sorted-arrays/"> link to leetcode </a>
 * <br>
 *
 * todo: see
 * @see <a href ="https://leetcode.com/discuss/41621/very-concise-iterative-solution-with-detailed-explanation"> Idea 1 </a>
 * <a href="https://leetcode.com/discuss/15790/share-my-o-log-min-m-n-solution-with-explanation"> Idea 2 </a>
 */
public class Leetcode4MedianofTwoSortedArrays {
    private static int[] s, l;

    private static double resultWhen2ElementInShorter(int is, int ssize,
                                                      int il, int lsize, int lmIndex, boolean lIsOdd) {
        if (!lIsOdd) {
            int lrmIndex = il + lsize / 2;
            // A
            if (l[lmIndex] <= s[is] && s[is + 1] <= l[lrmIndex]) {
                return (s[is] + s[is + 1]) * 0.5;
            }
            // B
            if (lsize == 2 && s[is] <= l[il] && l[il + 1] <= s[is + 1]) {
                return (l[il] + l[lrmIndex]) * 0.5;
            }
        }
        // Other cases
        if (s[is] < l[lmIndex]) {
            return cutHalfToNewPair(is + 1, ssize - 1, il, lsize - 1);
        }
        return cutHalfToNewPair(is, ssize - 1, il + 1, lsize - 1);
    }

    // @param lmIndex index of long array median, or left median
    private static double resultWhenOneElementInShorter(int is, int il, int lsize, int lmIndex, boolean lIsOdd) {
        int v = s[is];
        int m = l[lmIndex];
        if (lsize == 1) {
            return (v + m) * 0.5; //double
        }
        int next = l[lmIndex + 1];
        if (lIsOdd) {
            int pre = l[lmIndex - 1];
            if (pre <= v && v <= next) {
                return (v + m) * 0.5; //double
            }
            if (v > next) {
                return (m + next) * 0.5;
            }
            return (pre + m) * 0.5;
        }
        // even
        if (m <= v && v <= next) {
            return v;
        }
        if (next < v) {
            return next;
        }
        return m;
    }

    /**
     * @param is start index of array s
     * @param il start index of array l
     */
    private static double cutHalfToNewPair(int is, int ssize,
                                           int il, int lsize) {
        if (ssize > lsize) {
            int[] t = s;
            s = l;
            l = t;
            return cutHalfToNewPair(il, lsize, is, ssize);
        }
        // input check.
        if (ssize == 0) {
            int lmIndex = (lsize + 1) / 2 - 1;
            if ((lsize & 1) == 1) {
                return l[lmIndex];
            }
            return (l[lmIndex] + l[lmIndex + 1]) * 0.5;
        }
        // stop check
        boolean lIsOdd = (lsize & 1) == 1;
        int lmIndex = il + (lsize + 1) / 2 - 1;
        if (ssize == 1) {
            return resultWhenOneElementInShorter(is,
                    il, lsize, lmIndex, lIsOdd);
        }
        if (ssize == 2) {
            return resultWhen2ElementInShorter(is, ssize,
                    il, lsize, lmIndex, lIsOdd);
        }

        // binary search, divide and conquer
        boolean sIsOdd = (ssize & 1) == 1;
        int cutNum = (ssize + 1) / 2 - 1;
        int smIndex = is + cutNum;
        if (s[smIndex] == l[lmIndex]) {
            if (!sIsOdd && !lIsOdd) {
                return 0.5 * (s[smIndex] + (s[smIndex + 1] < l[lmIndex + 1]
                        ? s[smIndex + 1]
                        : l[lmIndex + 1]));
            }
            return s[smIndex];
        }

        ssize = ssize - cutNum;
        lsize = lsize - cutNum;
        if (s[smIndex] < l[lmIndex]) {
            is = is + cutNum;
        } else {
            il = il + cutNum;
        }
        return cutHalfToNewPair(is, ssize, il, lsize);
    }

    public static double findMedianSortedArrays(int[] a, int[] b) {
        s = a;
        l = b;
        assert (s != null);
        assert (l != null);
        return cutHalfToNewPair(0, s.length, 0, l.length);
    }
}
