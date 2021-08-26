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

import static common_lib.Common.*;
import static sort.InsertSort.insertSort;

public class QuickSortOnePivot3way {

  /*
  when there are more duplicate keys.
  */
  public static <T extends Comparable<T>> void quickSort3Way(T[] arr) {
    if (arr == null || arr.length <= 1) return;
    quicksort(arr, 0, arr.length - 1);
  }

  private static <T extends Comparable<T>> void quicksort(T[] arr, int l, int r) {
    if (l >= r) return;

    if (arr.length <= 4) {
      insertSort(arr);
      return;
    }

    int[] p = partition(arr, l, r);
    quicksort(arr, l, p[0] - 1);
    quicksort(arr, p[1] + 1, r);
  }

  private static <T extends Comparable<T>> int[] partition(T[] arr, int l, int r) {
    T pv = arr[getPivotIndexUsingMedianOf3(arr, l, r, (l + r) / 2)];
    int s = l, g = r;  // next smaller index s and next greater index g.
    int i = l;
    while (i <= g) {
      if (lessThan(arr[i], pv)) swap(arr, i++, s++);
      else if (same(pv, arr[i])) i++;
      else swap(arr, i, g--); // i do not move
    }
    return new int[] {s, g};
  }
}
