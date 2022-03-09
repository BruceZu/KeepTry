//  Copyright 2021 The KeepTry Open Source Project
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

package binarysearch;

public class Leetcode4MedianofTwoSortedArrays {
  /*
   Leetcode 4. Median of Two Sorted Arrays
   Given two sorted arrays nums1 and nums2 of size m and n respectively,
   return the median of the two sorted arrays.
   The overall run time complexity should be O(log (m+n)).

    Constraints:
    nums1.length == m
    nums2.length == n
    0 <= m <= 1000
    0 <= n <= 1000
    1 <= m + n <= 2000
    -10^6 <= nums1[i], nums2[i] <= 10^6


          nums1 = [1,3],
          nums2 = [2]
          Output: 2.00000

          nums1 = [1,2],
          nums2 = [3,4]
          Output: 2.50000

          nums1 = [3,4],
          nums2 = [2]
          Output: 3.00000

          nums1 = [2, 2, 4, 4],
          nums2 = [2, 2, 4, 4],
          Output: 3.00000

          nums1 = [9],
          nums2 = [7,8],
          Output: 8.00000

          nums1 = [6,7,8,9,10,12],
          nums2 = [1],
          Output: 8.00000

          nums1 = [1,2,3,4,5,7],
          nums2 = [6],
          Output: 4.00000

          nums1 = [0,0],
          nums2 = [0,0]
          Output: 0.00000

          nums1 = [],
          nums2 = [1]
          Output: 1.00000

          nums1 = [2],
          nums2 = []
          Output: 2.00000

          nums1 = [1,3],
          nums2 = [2，2，2]
          Output: 2.00000

          nums1 = [],
          nums2 = []
          Output: this will not happen as '1 <= m + n <= 2000'

          nums1 = null,
          nums2 = []
          Output: this will not happen as
          ' 0 <= m <= 1000
            0 <= n <= 1000'
  */
  /* --------------------------------------------------------------------------
    General idea: Keep value then check count
      Runtime: O((logM)(logN)) not reach required  O(log (m+n))
      Space O(1)
    https://imgur.com/lTxLjOn
    https://imgur.com/jvMbfTi
    use same median equation:  not matter total length a + b  is even or odd
    1-based count1 =  ((m+n)-1)/2+1;
    1-based count2 =  ((m+n))/2+1;
    find out the count1_th number v1 and count2_th number v2
    median = (v1+v2)/2
    E.g.
    [1,2]
    [3,4]

    4/2+1 =  3
    (4-1)/2+1 =  2

    [1]
    [3,4]

    3/2+1 = 2
    (3-1)/2+1 =  2

    now question is how to find out the number with given count ( 1-based)
    in  array a valid index scope [i, ie] to get the number v= a[mi]; mi= i+ie>>>1
    in  array b valid index scope [j, je] to find the right most index mj, to make b[mj] <=v; which can be j-1.
    not get total tmp_count=  mi-i +1  +  mj-j+1;

    not compare the tmp_count with the given count
      - tmp_count <  count: cut tmp_count numbers from a and b; continue find
                            from a valid index scope [mi+1, ie] and b valid index scope [ mj+1, je] to find number with given
                            count-tmp_count; if either array has not valid index scope now, question is convert to find the number
                            from the left one array;
      - tmp_count ==   count:  find the answer and it is the max { a[i+ie>>>1], b[mj]}
      - tmp_count >   count:  cutout half number but note here:
                              from the valid scope also need delete the max one, to avoid endless loop
                             a    [9]  | cut nothing
                             b   [7,8] | cut nothing
                             Note mj can be j-1 with current valid index scope[j, je]. E.g.
                             find number with given count=4 => 1
                             a    [1, 2, 3 |  4, 5, 7]  => [4,5| 7]       => [4]
                             b    [6]                   => [6]   mj =-1   => []

  */
  public double findMedianSortedArrays__(int[] a, int[] b) {
    /* 0 <= m <= 1000, 0 <= n <= 1000, 1 <= m + n <= 2000 */
    int tc = a.length + b.length;
    int c1 = (tc - 1) / 2 + 1;
    int c2 = tc / 2 + 1;
    int v2 = findCount(a, 0, a.length - 1, b, 0, b.length - 1, c2);
    if (c1 != c2) {
      int vl = findCount(a, 0, a.length - 1, b, 0, b.length - 1, c1);
      return (vl + v2) * 1.0 / 2;
    }
    return v2;
  }

  /*
  Get 1 based `count`th number from a with index range [i, ie] and b with index range [j, je].
  any array with invalid index scope will enable converting question to
  finding `count`th number from the other array which at least has 1 element as
  1 <= m + n <= 2000, so from + count-1 is valid

  O(logM) time M is the long of array A
  O(1) space
  */
  public int findCount(int[] a, int i, int ie, int[] b, int j, int je, int count) {
    if (ie < i || je < j) {
      int[] A = ie < i ? b : a;
      int from = ie < i ? j : i;
      return A[from + count - 1];
    }
    // both has at least 1 number
    int mi = i + ie >>> 1;
    int mj = rightBoundary(b, j, je, a[mi]);
    int LC = mi - i + 1 + mj - j + 1;
    if (LC < count) return findCount(a, mi + 1, ie, b, mj + 1, je, count - LC);
    else if (LC == count) return mj == j - 1 ? a[mi] : Math.max(a[mi], b[mj]);
    else {
      if (mj == j - 1 || b[mj] < a[mi]) return findCount(a, i, mi - 1, b, j, mj, count);
      return findCount(a, i, mi, b, j, mj - 1, count);
    }
  }

  /* find right boundary index mj, A[mj]<=v from A index range [l, r],  mj can l-1 or r
   when b is [], l=0, r=-1;
   O(logN) time. N is the long of array B
   O(1) space
  */
  public int rightBoundary(int[] A, int l, int r, int v) {
    if (A[r] <= v) return r;
    if (A[l] > v) return l - 1;
    // now  l<=v  and v<r
    while (l + 1 < r) {
      int m = l + r >>> 1;
      int mv = A[m];
      if (mv <= v) l = m;
      else r = m;
    }
    return l;
  }
  /*---------------------------------------------------------------------------
   keep count condition, then check value condition.
   then using binary search to find the expected allocation of count: i in A,
   which then decide allocation of count: j in B.

   i and j is count in A and B. i+j= total count/2; it is
     - half when total count is even
     - small half when total count is odd
   possible value of count i if A is  [0, Math.min(A.length, total count/2)]

   There is only one solution match the value condition
   https://imgur.com/XyINItc.png

     Total count C=A.length+B.length;
     half count  H=A.length+B.length>>>1;
        when C is even, H = C - H, both half has the same count => find the max left half and min right half => median
        when C is odd,  H is the shorter half =>  median is the min{right half}

     possible part of C in A:
      count i:  0 or 1,    2 , ....   min(A.length,H )
     then left part will be in B
      count j:  C or C-1, C-2, ...   C-min(A.length,H ) which can be 0

     Now the count is guaranteed, there are many above options, but there is only one option who can match
     value restriction. try ci using binary search to find it.
      A     i |  i+1
      B     j |  j+1
   Assume: 0 <= m <= 1000, 0 <= n <= 1000, 1 <= m + n <= 2000
           neither A and B is null. total count is at least 1.
   O(log(C)) =  O(log (m+n)) time,
   O(1) space;
  */
  /*
  in this method 6 variables are 1-based idx or count, not 0-based index:
    - C,H   : for merged A and B
    - l,r,i : in A
    - j     : in B
  */
  public double findMedianSortedArrays_(int[] A, int[] B) {
    int C = A.length + B.length;
    int H = A.length + B.length >>> 1;
    int l = 0, r = Math.min(A.length, H); // un-tried valid count scope

    int i = 0, j = 0;
    while (l <= r) { // un-tried
      i = l + r >>> 1; // try to allocate part  i of H in A
      j = H - i; //  j is the left part of H in B
      // count view:
      // A:     i | next: i+1_th.   right part in A: A.length - i
      // B:     j | next: j+1_th.   right part in B: B.length - j
      //        H | C-H
      if (v(A, i) <= v(B, j + 1) && v(B, j) <= v(A, i + 1))
        break; // expected allocation of i and j. Only one solution.
      // current location does not work
      if (v(A, i) > v(B, j + 1)) r = i - 1; // <- ci
      else l = i + 1; // v(B,cj)>v(A,ci+1)): ci->
    }
    if ((C & 1) == 1) return Math.min(v(A, i + 1), v(B, j + 1));
    return (Math.max(v(A, i), v(B, j)) + Math.min(v(A, i + 1), v(B, j + 1))) * 0.5;
  }

  private double v(int[] A, int count) {
    int i = count - 1; // idx-> index
    if (i < 0) return Integer.MIN_VALUE;
    if (i >= A.length) return Integer.MAX_VALUE;
    return A[i];
  }

  /*---------------------------------------------------------------------------
  Idea:
   https://imgur.com/CRwAFyY
   using virtual enhanced array by adding `#`, after that, total count is even
   keep left count M+N, right count M+N, cut use 2 virtual element
   then check value condition using original value
   original left value  <= cut value  ( == : when cut is not on #)
   original right value >= cut value  ( == : when cut is not on #)

   Details:
    no matter the total count is even or odd. median can be represented as (left one +  right one)/2
    0-based index of left and right median :  (m+n-1)/2,   (m+n)/2
    1-based idx or count of them           :  (m+n-1)/2+1, (m+n)/2+1

    A[] with length M
    B[] with length N
    Assume B is the shorter one:  N<=M,

    possible location to cut in B:
     -a- N+1 position: before after and within number, assume N>=2
                     E.g.:
                          B[]: [1,2] | max
                          A[]:   [3, | 4,5,6]
     -b- N position:  on number itself, assume N>=1, means median is the number itself = (it + it)/2
                    E.g.:  B  [2]
                           A [1, 3]
    In total 2*N+1
    cut position in A is decided the position in B

    Enhanced B-> `EB`, A->`EA` by adding `#`
      - no scenario -a-
      - total counts is always even

    case in -a- becomes:
        EB[]: [#,1,#,2,#] max
                       | cut on '#'

        EA[]:   [#,3,#,4,#,5,#,6,#]
                     | cut on '#'
        M+N is even: both cut on #)
   case in -b- becomes:
       E.g.:
        B[]:   [#,3,#]
                  | cut on 3 itself
                  | cut on '#'
        A[]: [#,1,#,2,#]
        M+N is odd: there is a cut on value number

     enhanced 2 arrays total length is 2*M+2*N+2
     EA length: 2*M+1, cut hold on a element, left are 2M
     EB length: 2*N+1, cut hold on a element, left are 2N

               j (index)
               |
     EB   ..., X, ...
     EA   ..., Y, ...
               |
               i (index)

    in EB and EA:  keep both sides elements count as M+N
      enhanced array B: the count of elements left to j is j;  cut index is j
      enhanced array A: the count of elements left to i is i;  cut index is i

     original array index = enhanced array index/2
     E.g.:  original array index  :    0      1      2      3
                                    #  v,  #  v,  #  v,  #  v,  #
            enhanced array index j: 0  1,  2  3,  4  5,  6  7,  8

    L is value in original array of
       - cut index j: j is odd at `v`
       - left to cut index j: j is even at `#`
    R is value in original array of
       - cut index j: j is odd at `v`
       - right to cut index j: j is even at `#`

    When cut on v, the L = R = j original value. share it.
    when cut on #, the L and R are 2 neighbor of j original value.

   O(log(M+N)) time
   O(1) space
  */
  static double findMedianSortedArrays(int[] A, int[] B) {
    int M = A.length, N = B.length;
    if (M < N) return findMedianSortedArrays(B, A);
    // N<=M. B is the shorter one

    int l = 0, r = 2 * N; // untried possible cut location index in enhanced B
    while (l <= r) {
      int j = l + r >>> 1;
      double L = (j == 0) ? Integer.MIN_VALUE : B[j - 1 >>> 1];
      double R = (j & 1) == 1 ? B[j >>> 1] : (j == 2 * N) ? Integer.MAX_VALUE : B[j + 1 >>> 1];

      int i = M + N - j;
      double La = (i == 0) ? Integer.MIN_VALUE : A[i - 1 >>> 1];
      double Ra = (i & 1) == 1 ? A[i >>> 1] : (i == 2 * M) ? Integer.MAX_VALUE : A[i + 1 >>> 1];

      if (La > R) l = j + 1;
      else if (L > Ra) r = j - 1;
      else return (Math.max(La, L) + Math.min(Ra, R)) / 2;
    }
    return -1;
  }

  public static void main(String[] args) {
    findMedianSortedArrays(new int[] {2, 4}, new int[] {2, 2, 4, 4});
  }
}
