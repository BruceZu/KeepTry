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

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * <pre>
 * Idea: see the link, check whether an element A[i] is the median.
 *
 *     two arrays A and B. n = A.length+B.length
 *     check whether an element A[i] is the median, or left median in constant time.
 *     Suppose that the median is A[i].
 *     Since the array is sorted, it is >= exactly i values in array A.
 *     it is also greater than exactly j = [n/2] − (i + 1) elements in B.
 *
 *    1> Let i =
 *
 *          index of A:   0 1 2 3 ... ...   i
 *                                        A[i]
 *          index of B:   0 1 2 3 ... n/2-(i+1)-1
 *
 *    2> So j =
 *                                  j=n/2-(i+1)-1
 *     value of B:                  B[j]        B[j+1]
 *
 *     i+1 + j+1 = i+1 + n/2-(i+1)= n/2
 *     thus make sure the number is right
 *
 *    3> compare:
 *
 *            1       [       match     ]     3
 *                    B[j]        B[j+1]
 *
 *     It requires constant time to check if it matchs B[j] ≤ A[i] ≤ B[j + 1].
 *     match then done.
 *     thus make sure the element is the right one.
 *     else A[i] is not the median, then
 *
 *   4> next
 *      binary search for A[i] in (lg n) worst-case time.
 *
 *      This is wrong: when found the answer is not in the A. then
 *      if find this from the right end : get the (n/2-A.length)th from B
 *      else find this from the left end: get n/2th directly from B
 *
 *      Need take boundary in account:
 *      Always assume the kth is in 'a' array
 *      Valid left boundary index and right boundary index in A:
 *
 *     A:  [0, 2, ...........................left boundary .
 *                                              |
 *                                             A[i]
 *     B:  [0.... ..b.length-1],   b.length
 *                                 |
 *                        B[j]     B[j+1]
 *     left boundary + b.length = kth.
 *     So left = kth - b.length
 *     Match if  B[j] <= A[i] && B[j+1]== b.length
 *
 *     A:  [0, 2, ... right boundary....
 *                       |
 *                      A[i]
 *     B:                       -1  [0, 2, ....b.length
 *                              |
 *                             B[j] B[j+1]
 *
 *     right boundary= kth
 *     Match if  A[i]<=B[j+1] <=  && B[j]== -1
 *
 *     So there are in total 3 match cases:
 *     (Bj == -1 || validBj && b[Bj] <= ai) && (validBj1 && ai <= b[Bj1] || Bj1 == b.length)
 *
 *     till left > right,
 *     that means result is sure in B array
 *     <strong>Note</strong>
 *     1  recalculate valid boundary of new A
 *     2  especially when need calculate right median, need also recalculate K.
 *
 *   Corner case one array may be empty. Skill:
 *    it may be A,
 *    or
 *    it may be B:
 *         so need valid Bj and Bj1, then after swtich it became new A.
 * Runtime:
 *    in worse case binary search A and B, logM+ logN . Big O(log(M+N))
 *
 *  @see <a href="http://www2.myoops.org/twocw/mit/NR/rdonlyres/Electrical-Engineering-and-Computer-Science/6-046JFall-2005/30C68118-E436-4FE3-8C79-6BAFBB07D935/0/ps9sol.pdf"
 * > link </a>
 */
public class Leetcode4MedianofTwoSortedArrays2 {

    public static double searchKthValue(int aFrom, int aTo) {
        if (aFrom > aTo) {
            int[] t = A;
            A = B;
            B = t;
            aTo = min(k, A.length) - 1;
            aFrom = max(1, k - B.length) - 1;
        }
        if (A.length == 0) {
            return B[k - 1];
        }

        int Ai = (aFrom + aTo) / 2;
        int Bj = k - (Ai + 1) - 1;
        int Bj1 = Bj + 1;
        int ai = A[Ai];  
        boolean validBj = 0 <= Bj && Bj < B.length;
        boolean validBj1 = 0 <= Bj1 && Bj1 < B.length;

        if (validBj1 && Bj == -1 && ai <= B[Bj1]
                || validBj && validBj1 && B[Bj] <= ai && ai <= B[Bj1]
                || validBj && Bj1 == B.length && B[Bj] <= ai) {
            return ai;
        } else if (validBj && ai <= B[Bj]) {
            return searchKthValue(Ai + 1, aTo);
        }
        return searchKthValue(aFrom, Ai - 1);
    }

    private static int[] A, B;
    private static int k; // kth

    public static double findMedianSortedArrays(int[] a, int[] b) {
        A = a;
        B = b;
        k = (A.length + B.length + 1) / 2;

        int aFrom = max(1, k - B.length) - 1;
        int aTo = min(k, A.length) - 1;
        double lm = searchKthValue(aFrom, aTo);
        if (((A.length + B.length) & 1) == 0) {
            k = k + 1;
            aFrom = max(1, k - B.length) - 1;
            aTo = min(k, A.length) - 1;
            return 0.5 * (lm + searchKthValue(aFrom, aTo));
        }
        return lm;
    }
}