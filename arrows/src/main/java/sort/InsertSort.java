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

import static common_lib.Common.lessThan;
import static common_lib.Common.swap;

public class InsertSort {
  /*
   Ascending order
   used for partially-sorted arrays.
  */
  public static <T extends Comparable<T>> void insertSort(T[] arr) {
    if (arr == null || arr.length <= 1) return;
    insertSort(arr, 0, arr.length - 1);
  }

  public static <T extends Comparable<T>> void insertSort(T[] arr, int l, int r) {
    if (arr == null || arr.length <= 1 || r == l) return;
    for (int i = l + 1; i <= r; i++) {
      int j = i;
      while (l < j && lessThan(arr[j], arr[j - 1])) {
        swap(arr, j, j - 1);
        j--;
      }
    }
  }

  // insert sort sub-array [l, r]
  public static void insertSort(int[] a, int l, int r) {
    if (a == null || a.length <= 1 || r == l) return;
    for (int i = l + 1; i <= r; i++) {
      int j = i;
      while (j > l && a[j] < a[j - 1]) {
        swap(a, j, j - 1);
        j--;
      }
    }
  }
}
