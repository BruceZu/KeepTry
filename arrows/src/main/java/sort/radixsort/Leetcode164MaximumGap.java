//  Copyright 2022 The KeepTry Open Source Project
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

package sort.radixsort;

import java.util.*;

public class Leetcode164MaximumGap {
  /*
    Leetcode 164. Maximum Gap

    Given an integer array nums, return the maximum difference between
    two successive elements in its sorted form.
    If the array contains less than two elements, return 0.

    You must write an algorithm that runs in linear time and uses linear extra space.

    Input: nums = [3,6,9,1]
    Output: 3
    Explanation: The sorted form of the array is [1,3,6,9],
    either (3,6) or (6,9) has the maximum difference 3.

    Input: nums = [10]
    Output: 0
    Explanation: The array contains less than 2 elements, therefore return 0.


    Constraints:

    1 <= nums.length <= 10^5
    0 <= nums[i] <= 10^9
  */
  /*
  O(d⋅(n+R))≈O(n) time
  O(n+R)≈O(n) space, R is a small fixed constant
  n is the array length
  d is the max number digit width
  R is the radix, here it is 10, it can be 2 or 16
    when R is 16,  if e is greater than Integer.MAX_VALUE it can be 0
      16
      256
      4096
      65536
      1048576
      16777216
      268435456
      0
      -----------------------------------
      268435456*16         = 4,294,967,296
      Integer.MAX_VALUE    = 2,147,483,647
      2* Integer.MAX_VALUE = 4,294,967,294

  https://en.wikipedia.org/wiki/Counting_sort
  */
  public static int maximumGap(int[] A) {
    if (A == null || A.length < 2) return 0;
    int N = A.length;

    int max = Arrays.stream(A).max().getAsInt();

    int e = 1; // exp: 1, 10, 100, 1000 ...
    int R = 10; // radix/base 10 system, it can be 2 or 16

    int[] sorted = new int[N];

    /* LSD Radix Sort */
    while (e > 0 && max / e > 0) { // Go through all digits from LSD to MSD
      int[] bin = new int[R]; // count
      for (int i = 0; i < N; i++) { // Counting sort
        bin[(A[i] / e) % R]++;
      }
      // sort == start
      for (int i = 1; i < bin.length; i++) { // prefix sum
        bin[i] += bin[i - 1];
      }
      // a prefix sum in order to determine, for each key, the position range
      // where the items having that key should be placed;
      for (int i = N - 1; i >= 0; i--) {
        sorted[--bin[(A[i] / e) % R]] = A[i]; // stable algorithm
      }
      // sorted by current digit
      for (int i = 0; i < N; i++) {
        A[i] = sorted[i];
      }
      /* alternative:
       int[] tmp=A;
       A=sorted;
       sorted=tmp;
      */
      e *= R;
      if (e <= 0) break;
    }

    int ans = 0;
    for (int i = 0; i < N - 1; i++) ans = Math.max(A[i + 1] - A[i], ans);
    return ans;
  }
  /*---------------------------------------------------------------------------
      The Pigeonhole Principle states that if nn items are put into m containers,
      with n > m, then at least one container must contain more than one item.

      if we used buckets instead of individual elements as our base for comparison,
      the number of comparisons would reduce if we could accommodate more than one
      element in a single bucket if able to avoid compare elements within a bucket.

      if able to distribute the elements to the right buckets and ordered in bucket
      then only need to compare among buckets

      setting every bucket size integer t to be 1 <= t ≤ (max−min)/(n−1)
      then gaps (between elements) within the same bucket would only be ≤ t,
      the maximal gap would occur only between two adjacent buckets.

       bucket size t is integer:
         It is floor of (max−min)/(n−1)
       relation within bucket:
         avoid overlapping
         The gap between buckets would be 1 integer unit wide.
         two adjacent buckets of size 3 could hold integers with values say, 3 4 5  and 6 7 8
         element is integer.

      the size of a bucket is its holding capacity, the range of values the bucket can
      represent (or hold). the actual extent of the bucket are determined by
      the values of the maximum and minimum element in the bucket:
      a bucket of size 5 hold 5 values 6 7 8 9 10
      However, if it only holds the elements 7, 8, 9,
      then its actual size is 3

      t=⌊(max−min)/(n−1)⌋
      E.g. array [1,5]
           bucket size t = max{1, ⌊(5-1)/(2-1)⌋} = 4
           need bucket number: (5-1+1)/4 + (5-1+1) % 4==0?0:1 = 2
           [1,2,3,4] [5]

           array [1,1,1,1]
           bucket size t=max{1, 0} = 1;
           need bucket number: (1-1+1)/1 + (1-1+1) % 1==0?0:1 = 1
           [1]
           default preMax= min value = 1
           so maxgap = 0

           array [1,2,3]
           t=1
           bucket number: 3

      compare about twice the number of buckets
      (the minimum and maximum elements of each bucket).

      b is bucket number, n is array number
      Time complexity:  O(n+b)≈O(n).
      Space complexity: O(2⋅b)≈O(b) extra space. Each bucket stores a maximum and a minimum element.
  */

  static class Bucket {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
  }
  /*
  t: bucket size or capacity, it is integer,  at least is 1, t should not be 0
  */
  public static int maximumGap_(int[] A) {
    if (A == null || A.length < 2) return 0;
    int minv = Arrays.stream(A).min().getAsInt();
    int maxv = Arrays.stream(A).max().getAsInt();

    int t = Math.max(1, (maxv - minv) / (A.length - 1));
    int bucketNum = (maxv - minv + 1) / t + ((maxv - minv + 1) % t == 0 ? 0 : 1);

    Bucket[] bins = new Bucket[bucketNum];
    // do not use: Arrays.fill(bins, new Bucket());

    for (int v : A) {
      int i = (v - minv) / t;
      if (bins[i] == null) bins[i] = new Bucket();
      bins[i].min = Math.min(v, bins[i].min);
      bins[i].max = Math.max(v, bins[i].max);
    }

    int preMax = minv, maxGap = 0;
    for (Bucket bin : bins) {
      if (bin == null) continue;
      maxGap = Math.max(maxGap, bin.min - preMax);
      preMax = bin.max;
    }
    return maxGap;
  }

  // ---------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(maximumGap_(new int[] {1, 2, 3}));
    System.out.println(maximumGap_(new int[] {1, 1, 1}));
    System.out.println(maximumGap_(new int[] {1, 5}));
    System.out.println(maximumGap_(new int[] {3, 6, 9, 1}));
    System.out.println(maximumGap(new int[] {3, 6, 6, 9, 1, 10}));

    System.out.println("======");
    int[] a = new int[] {3, 2, 9, 22, 6, 78, 1};
    MsdRadixSortInPlace(a);
    System.out.println(Arrays.toString(a));
  }
  /* --------------------------------------------------------------------------
    MSD Radix sort in place
    Assume
       - the array A is positive number
       - sort in ascending order

    swap(): determine the algorithm is not stable sorting algorithm.
            it can be fixed  by using a buffer array
  */
  public static void MsdRadixSortInPlace(int[] A) {
    int max = Arrays.stream(A).max().getAsInt();
    int w = 0; // number of 1s and 0s of max value in binary format
    while (max > 0) {
      max >>= 1;
      w++;
    }
    dfs(0, A.length - 1, w - 1, A);
  }
  /*
   l and r is next insert index in array A
   separate array in 2 ways using current bit
  */
  private static void dfs(int from, int to, int MSB, int[] A) {
    if (MSB == -1 || from == to) return;
    int e = 1 << MSB;
    int i = from, l = from, r = to;
    while (i <= r) {
      int bit = (A[i] / e) % 2;
      if (bit == 1) swap(A, i, r--);
      else swap(A, i++, l++);
    }
    dfs(from, l - 1, MSB - 1, A);
    dfs(r + 1, to, MSB - 1, A);
  }

  private static void swap(int[] A, int i, int j) {
    if (i != j) {
      int tmp = A[i] ^ A[j];
      A[i] = A[i] ^ tmp;
      A[j] = A[j] ^ tmp;
    }
  }
}
