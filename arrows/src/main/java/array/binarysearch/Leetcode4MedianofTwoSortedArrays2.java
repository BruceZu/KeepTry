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
 * Idea: see the link, two arrays A and B. n = A.length+B.length
 *      check whether an element A[i] is the kth =(n+1)/2
 *
 *     How to select a member from A to check?
 *     Firstly assume the kth is in A array.
 *     select median from valid index scope: [aFrom, aTo]
 *          E.g.:
 *          A:   e1 e2 e3 e4 e5    e6 e e e e
 *                         |        |
 *                        aFrom    aTo
 *          B:   b1 b2
 *          kth = 6
 *
 *
 *     aFrom init value:
 *          aFrom + b.length = kth.
 *          aFrom = kth - b.length
 *          when i is aFrom, j+1==B length.
 *          see:
 *           A:  [...........................left boundary.....]
 *                                                      |
 *                                                     A[i]
 *          B:   [.....b.length-1],   b.length
 *                                      |
 *                           B[j]     B[j+1]
 *
 *
 *     aTo init value: kth.
 *          when i is aTo, j==-1
 *          see:
 *           A:  [... right boundary....]
 *                           |
 *                         A[i]
 *          B:                       -1  [0, 2, ....]
 *                                    |
 *                                   B[j] B[j+1]
 *   How to check?
 *     compare it to B[j] and B[j+1] to see if it match the condition:
 *          j is deduced by i: j =  n/2-(i+1)-1
 *                                  B[j]             B[j+1]
 *          Suppose kth is just A[i].
 *          Since the array is sorted,
 *          it is >= exactly i elements in array A. Also
 *          it is >= exactly j = kth − (i + 1) elements in B.
 *          thus make sure the numbers is right
 *
 *     compare them to check match condition:
 *                   B[j] ≤ A[i] ≤ B[j + 1]
 *
 *            1       [       match     ]     3
 *                    B[j]        B[j+1]
 *
 *
 *     thus make sure the element is the right one.
 *     when i is initial aFrom or aTo
 *         Match if  B[j] <= A[i] && B[j+1]== b.length
 *         Match if  A[i]<=B[j+1] <=  && B[j]== -1
 *     match conditons:
 *     (Bj == -1 || validBj && b[Bj] <= ai) && (validBj1 && ai <= b[Bj1] || Bj1 == b.length)
 *  Stop or continue:
 *     match then stop.
 *     else A[i] is not the median, then.
 *   4> next, binary search next A[i]. update the valid aFrom or aTo.
 *     if left > right, that means result is sure in B array.
 *     switch them and search from new A.
 *     <strong>Note</strong>
 *     1  recalculate valid boundary of new A
 *     2  especially when need calculate right median, need also recalculate K.
 *
 *    corner case one array may be empty. Skill:
 *    it may be A,
 *    or
 *    it may be B:
 *    so need valid Bj and Bj1 in 3 match conditions, then after switch it became new A.
 *
 * Runtime:
 *    in worse case binary search A and B, logM+ logN . Big O(log(M+N))
 *
 *  @see <a href="http://www2.myoops.org/twocw/mit/NR/rdonlyres/Electrical-Engineering-and-Computer-Science/6-046JFall-2005/30C68118-E436-4FE3-8C79-6BAFBB07D935/0/ps9sol.pdf"
 * > link </a>
 */
public class Leetcode4MedianofTwoSortedArrays2 {
  private static int[] A, B;
  private static int k; // kth

  public static double kthValue(int aFrom, int aTo) {
    // stop
    if (aFrom > aTo) {
      int[] t = A;
      A = B;
      B = t;
      aFrom = Math.max(k - B.length, 1) - 1;
      aTo = Math.min(A.length, k) - 1;
    }
    if (A.length == 0) {
      return B[k - 1];
    }

    //start
    int i = (aFrom + aTo) / 2;
    int n = i + 1;
    int j = k - n - 1;
    int j1 = j + 1;
    boolean jValid = 0 <= j && j < B.length;
    boolean j1Valid = 0 <= j1 && j1 < B.length;
    int v = A[i];
    if (j1Valid && j == -1 && v <= B[j1]
        || j1Valid && jValid && B[j] <= v && v <= B[j1]
        || jValid && B[j] <= v && j1 == B.length) {
      return v;
    }
    if (jValid && v < B[j]) {
      return kthValue(i + 1, aTo);
    }
    return kthValue(aFrom, i - 1);
  }

  public static double findMedianSortedArrays(int[] a, int[] b) {
    A = a;
    B = b;
    k = (A.length + B.length + 1) / 2;

    int aFrom = Math.max(k - B.length, 1) - 1;
    int aTo = Math.min(A.length, k) - 1;
    double l = kthValue(aFrom, aTo);
    if ((A.length + B.length & 1) == 0) {
      k = k + 1;
      aFrom = Math.max(k - B.length, 1) - 1;
      aTo = Math.min(A.length, k) - 1;
      return 0.5 * (l + kthValue(aFrom, aTo));
    }
    return l;
  }
}
