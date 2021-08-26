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

import static common_lib.Common.swap;
import static sort.InsertSort.insertSort;

public class QuickSelect {

  /*
    Select a pivot based on median of medians strategy like the nth_element() int c++.
    implementation base on intro sort
    return the index of median or left median which is as the pivot
  */
  public static int pivotByMedianOfMedians(int[] arr, int l, int r) {
    if (r - l < 5) return pivot5(arr, l, r);
    // otherwise, move the medians of five-element subgroups to the first n/5 positions
    int into = l; // next index swap the next median of five-element into
    for (int i = l; i <= r; i = i + 5) {
      int medianIndex = pivot5(arr, i, i + 4 > r ? r : i + 4);
      swap(arr, medianIndex, into++);
    }
    // compute the median or left median of the n/5 medians-of-five
    into--;
    return introSelectKth(arr, l, into, l + ((into - l) >> 1));
  }

  /*
  get final index of (left)median
  For elements number r - l +1 <= 5 implement this using insertion sort
  */
  private static int pivot5(int[] arr, int l, int r) {
    insertSort(arr, l, r);
    return l + ((r - l) >> 1);
  }

  /*
  get the k-th smallest element of list within left..right inclusive
  O(n) time
  o(1) extra space?? o(lgN)??
  */
  private static int introSelectKth(int[] arr, int l, int r, int k) {
    while (l < r) {
      int pi = pivotByMedianOfMedians(arr, l, r);
      int[] range = partition(arr, l, r, pi); // pivotal value index range
      // now the pivot is in its final sorted position.
      if (range[0] <= k && k <= range[1]) return k;
      if (k < range[0]) r = range[0] - 1;
      else l = range[1] + 1;
    }
    // left == right
    return l;
  }
  /*
    move all elements of sub-array [l,r] in 3-ways by pivotal value
     [l,<pv] [=pv] [>pv,r]
  */
  private static int[] partition(int[] a, int l, int r, int pi) {
    int pv = a[pi];
    int s = l; // index swap Next small into
    int b = r; // index swap Next big into
    int i = l;
    while (i <= b) {
      if (a[i] < pv) swap(a, i++, s++);
      else if (a[i] == pv) i++;
      else swap(a, i, b--); // i not move
    }
    return new int[] {s, i - 1};
    //  i-1 will always be the right morst index of pivot value
  }
}
