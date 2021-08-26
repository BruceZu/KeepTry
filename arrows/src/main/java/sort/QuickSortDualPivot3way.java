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

import static common_lib.Common.greatThan;
import static common_lib.Common.lessThan;
import static common_lib.Common.swap;
import static sort.InsertSort.insertSort;

public class QuickSortDualPivot3way {

  /*
  sub sub-array[l, r]
        select 2 pivots, pv1<pv2 and initially at both side of sub-array,
        [pv1, l+1...r-1, pv2]
        then with them split the sub-array, and at last locate them to both sides of middle way
        [l, <pv1], [pv1, >=pv1 && <=pv2 , pv2] [>pv1, r]
        for each way recursively call this method, till the way length is 1;
   */
  private static <T extends Comparable<T>> void quickSort2p(T[] arr, int l, int r) {
    if (l >= r) return;

    if (arr.length <= 5) {
      insertSort(arr);
      return;
    }

    // 1
    int[] p2i = partition(arr, l, r);
    int pi1 = p2i[0], pi2 = p2i[1];

    // 2
    // both sides way
    quickSort2p(arr, l, pi1 - 1);
    quickSort2p(arr, pi2 + 1, r);

    // middle way, skip if pv1 == pv2
    if (lessThan(arr[pi1], arr[pi2])) quickSort2p(arr, pi1 + 1, pi2 - 1);
  }

  public static <T extends Comparable<T>> void quickSortDualPivot(T[] arr) {
    if (arr == null || arr.length <= 1) return;
    quickSort2p(arr, 0, arr.length - 1);
  }

  private static <T extends Comparable<T>> int[] partition(T[] a, int l, int r) {
    // 1.
    if (greatThan(a[l], a[r])) swap(a, l, r);
    T pv1 = a[l];
    T pv2 = a[r];

    // 2.
    // pv1<pv2
    // [pv1, l+1...r-1, pv2]
    // The left and right index, included, of p1 <= e <= pv2
    int s = l + 1, b = r - 1;
    // s and b are next index swap the smaller than pv1 and bigger than pv2 into
    int i = l + 1;
    while (i <= b) {
      if (lessThan(a[i], pv1)) swap(a, i++, s++);
      else if (greatThan(a[i], pv2)) swap(a, i, b--); // i do not move,  need check the new arr[i]
      else i++; // pv1 <= a[i] <= pv2
    }

    // 3.
    swap(a, l, s - 1);
    swap(a, r, b + 1);
    return new int[] {s - 1, b + 1};
  }
}
