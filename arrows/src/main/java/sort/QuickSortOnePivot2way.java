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

public class QuickSortOnePivot2way {

  public static <T extends Comparable<T>> void quickSort(T[] arr) {
    if (arr == null || arr.length <= 1) return;
    quicksort(arr, 0, arr.length - 1);
  }
  // recursion
  private static <T extends Comparable<T>> void quicksort(T[] arr, int l, int r) {
    if (l >= r) return;
    int pi = partition(arr, l, r);
    quicksort(arr, l, pi - 1);
    quicksort(arr, pi + 1, r);
  }
  /*
    sort sub-array [l,r]
       select pivot and put it on the left side
       partition other items to 2 way: < pivot and  >= pivot
       allocate the pivot itself to its place in ascending ordered result.
       [l,< pivot], pivot itself,[>= pivot,r]
  */
  private static <T extends Comparable<T>> int partition(T[] a, int l, int r) {
    int pi = getPivotIndexUsingMedianOf3(a, l, r, (l + r) / 2);
    T pv = a[pi]; // keep pivot value before swap it to index l;
    swap(a, l, pi);

    int s = l + 1; // index, swap next less than pv value into
    for (int i = l + 1; i <= r; i++) if (lessThan(a[i], pv)) swap(a, i, s++);
    swap(a, l, s - 1);
    return s - 1;
  }
}
