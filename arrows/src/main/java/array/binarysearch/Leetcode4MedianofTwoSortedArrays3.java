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
 * runtime:  the first left is (m+n+1)/2; each time got left/2; log(m+n+1); Big O(log(m+n))
 * Improved: Find Kth Element
 * <pre>
 *     each time compare A[Kth/2] with B[Kth/2]
 *
 *     View the question as finding the kth element of all of 2 sorted arrays.
 *     kth = median, left median; then right median if length(m+n) is even.
 *
 *     the result is =,<,>
 *     care the less than part only, as it is sure those kth/2 elements will be in
 *     the [1th ~ kth];
 *     A  [ a1, a2, a3, ... akth/2 ...  am]
 *     B  [ b1, b2, b3, ... bkth/2... ... ... bn]
 *                           |
 *                          compare
 *     After got kth/2 elements, left = kth-kth/2
 *     Question is translated to next one:
 *
 *     Find the left-th member in the new pair arrays:
 *     - One array - kth/2 elements
 *     - other untouched array
 *  Why it is kth/2?
 *
 *  Corner cases:
 *  <1> aStart == A.length
 *   length 3:   [1 2 3]
 *   length 8:   [2 3 4 5 6 7 8 9]
 *   left = 6th
 *   half =3th
 *   ==>  left = 3th
 *        half= 1th
 *               []
 *               [2 3 4 5 6 7 8 9]
 *        aStart == A.length, so get 3th from B, 4 is the answer.
 *        <strong> it may be directly as input </strong>
 *  <2>left == 1
 *   length 2:   [7 8]
 *   length 6:   [1 2 3 4 5 6]
 *   left = 4th
 *   half = 2th
 *   ==>  left = 2th
 *        half = 1th
 *               [7 8]
 *               [3 4 5 6]
 *        ==> left = 1th
 *               [7 8]
 *               [4 5 6]
 *            Answer is 4. the right median is 5.
 *  <3> kth/2 > A.length
 *   length 1:   [1]
 *   length 8:   [2 3 4 5 6 7 8 9]
 *   left = 5th
 *   half = 2th;
 *   ==> left = 3th
 *       half = 1th
 *         [1]
 *         [4 5 6 7 8 9]
 *       ==> left =2
 *           half = 1
 *             []
 *             [4 5 6 7 8 9]
 *             5 is answer
 */
public class Leetcode4MedianofTwoSortedArrays3 {

  private static int[] A, B;

  public static double findMedianSortedArrays(int[] a, int[] b) {
    A = a;
    B = b;
    double l = getkth(0, 0, (A.length + B.length + 1) / 2);
    if ((A.length + B.length & 1) == 0) {
      return 0.5 * (l + getkth(0, 0, (A.length + B.length + 2) / 2));
    }
    return l;
  }

  public static double getkth(int aStart, int bStart, int left) {
    if (aStart == A.length) {
      return B[bStart + left - 1];
    }
    if (bStart == B.length) {
      return A[aStart + left - 1];
    }
    if (left == 1) {
      return A[aStart] < B[bStart] ? A[aStart] : B[bStart];
    }
    int compareA = left / 2 > (A.length - aStart) ? Integer.MAX_VALUE : A[aStart + left / 2 - 1];
    int compareB = left / 2 > (B.length - bStart) ? Integer.MAX_VALUE : B[bStart + left / 2 - 1];

    if (compareA < compareB) {
      return getkth(aStart + left / 2, bStart, left - left / 2);
    }
    return getkth(aStart, bStart + left / 2, left - left / 2);
  }
}
