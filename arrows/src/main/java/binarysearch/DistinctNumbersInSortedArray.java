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

package binarysearch;

public class DistinctNumbersInSortedArray {
  /*
    Given an array of integers, determine the number of distinct integers in the array

  1. No restriction in the input
     Deduplicate with a set, O(n) time and space

  2. Input array is already sorted. Can we do better than before?
     Deduplicate by looking at prevoius number, O(n) time and O(1) space

  3. Input array is already sorted. Additionally, the number of distinct integers is very small relative to the size of the array

     e.g. array with millions of integers, but only a few distinct integers
     Example: [1,1,..,1,2,4,4,4,...,4,17]

     */
  /*
    For question 3
    Run time is O(logN),and O(1) extra space
  */

  public int distinctNumberInSortedArrayWithSmallValueSet(int[] sorted) {
    if (sorted == null || sorted.length == 0) {
      return 0;
    }
    int sum = 0;
    int l = 0; // left index of current value scope
    // find the right index of current value scope
    while (l < sorted.length) {
      int r = bs(l, sorted);
      sum++;
      l = r + 1;
    }

    return sum;
  }

  /*
  In a sorted array to find the right boundary index of value which left index is given

  Assume the left index is valid
  Idea: Binary Search
  As to find the right boundary index, need keep the left index of the value: l=mi
  But when there only 2 element then the left index can not move forward, run into endless loop.

  Solution is just take it out of loop with condition: while (r > l + 1)
  */
  private int bs(int leftIndex, int[] sorted) {
    int v = sorted[leftIndex], l = leftIndex, r = sorted.length - 1;
    if (sorted[r] == v) return r;

    while (r > l + 1) {
      int mi = l + r >>> 1;
      int mv = sorted[mi];
      if (mv > v) r = mi - 1;
      else l = mi;
    }
    // [l, r] l is known with expected value. r in unknown.
    if (sorted[r] == v) return r;
    return l;
  }

  public static void main(String[] args) {
    DistinctNumbersInSortedArray t = new DistinctNumbersInSortedArray();
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(null) == 0);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {}) == 0);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1}) == 1);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1, 2}) == 2);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1, 1}) == 1);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1, 2, 2, 3}) == 3);
    System.out.println(t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1, 2, 3}) == 3);
    System.out.println(
        t.distinctNumberInSortedArrayWithSmallValueSet(new int[] {1, 1, 1, 1, 1, 1, 1}) == 1);
  }
}
