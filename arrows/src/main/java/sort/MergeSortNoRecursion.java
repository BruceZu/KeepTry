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

package sort;

import common_lib.Common;
import common_lib.Merger;

import java.util.Arrays;

public class MergeSortNoRecursion {
  private static Merger merger = new Common();

  public MergeSortNoRecursion(Merger merger) {
    this.merger = merger;
  }

  /*
  Merge sort an array in Java use TimSort
  @see sort.Leetcode912SortanArray

  O(NlogN) runtime, O(N) space.
  */
  public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] A) {
    if (A == null || A.length <= 1) return;
    Comparable[] tmp = new Comparable[A.length];
    int L = 1;
    while (L < A.length) {
      int s = 0;
      while (s + L <= A.length - 1) {
        merger.mergeInsort(A, s, s + L - 1, Math.min(s + 2 * L - 1, A.length - 1), tmp);
        s += 2 * L;
      }
      L = L << 1;
    }
  }

  /* --------------------------------------------------------------------------
  merge sort in-place
  https://imgur.com/p37ZJSf

  compare 2 groups by 2 groups, each group size is L: 1 ,2, 4, .... till L< array length or there are 2 groups
  [s, s+L-1]
  [s+L, min{s+2L-1,A.length-1}]

  s: start index of first group
  end = s + L - 1,
  start2= s + L

  merge from temp array
    use System.arraycopy(A, s, tmp, s, e2 - s + 1);
    not use
         int [] t= a;
         a=tmp;
         tmp=t;
         if the left part size == L
         then it is left in array not merge happen

    use int k = s; // not k=0;
  */

  /*----------------------------------------------------------------------------
   My version
  */
  public static int[] mergeSortInPlaceMy(int[] A) {
    if (A == null || A.length <= 1) return A;
    int[] tmp = new int[A.length];
    int L = 1;
    while (L < A.length) {
      // s2 = s+L; e2 is min{s+2*L-1, A.length-1};
      for (int s = 0; s + L < A.length; s = s + 2 * L) { // 2 groups by 2 groups
        int e2 = Math.min(s + 2 * L - 1, A.length - 1);
        System.arraycopy(A, s, tmp, s, e2 - s + 1);
        int k = s, i = s, j = s + L;
        while (i < s + L && j <= e2) {
          if (tmp[i] < tmp[j]) A[k++] = tmp[i++];
          else A[k++] = tmp[j++];
        }
        while (i < s + L) A[k++] = tmp[i++];
        while (j <= e2) A[k++] = tmp[j++];
      }
      L <<= 1;
    }
    return A;
  }

  /*
       while (!(i == s + L && j == e2 + 1)) {
         if (j == e2 + 1 || i <= s + L - 1 && tmp[i] < tmp[j]) A[k++] = tmp[i++];
         else A[k++] = tmp[j++];
       }
  */

  public static void main(String[] args) {

    System.out.println(Arrays.toString(mergeSortInPlaceMy(new int[] {3, 2, 4})));
    System.out.println(Arrays.toString(mergeSortInPlaceMy(new int[] {3, 2, 2, 4, 4})));
    System.out.println(Arrays.toString(mergeSortInPlaceMy(new int[] {3, 2, 2, 3, 4, 4})));
    System.out.println(Arrays.toString(mergeSortInPlaceMy(new int[] {1, 2, 3, 4})));
    System.out.println(Arrays.toString(mergeSortInPlaceMy(new int[] {4, 3, 2, 1})));
  }
}
