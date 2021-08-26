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
import static common_lib.Common.same;
import static common_lib.Common.swap;
import static sort.InsertSort.insertSort;

/*
2 pivot can separate out at most 5 way
- pivot1 == pivot2, at most 3 way
- pivot1 != pivot2, possible 4 way.
each loop clear out 2 way, need 2 loops

Refer java.util.Arrays with new Dual-Pivot Quicksort
  public static void sort(int[] a) {
       DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
  }
*/
public class QuickSortDualPivot5way {
  public static <T extends Comparable<T>> void quickSortDualPivot(T[] a) {
    if (a == null || a.length <= 1) return;
    quicksort2p5way(a, 0, a.length - 1);
  }

  private static <T extends Comparable<T>> void quicksort2p5way(T[] a, int l, int r) {
    if (l >= r) return;

    if (a.length <= 5) {
      insertSort(a);
      return;
    }
    // 5 way range    [<pv1] [pv1] [ pv1< e <pv2] [pv2] [>pv2]
    // g index value  l      0      2          3     1      r
    int[] g = partition(a, l, r);
    quicksort2p5way(a, l, g[0] - 1); // left side way
    quicksort2p5way(a, g[1] + 1, r); // right side way
    quicksort2p5way(a, g[2], g[3]);
  }

  /*
  After the first loop
          [< pv1], [ pv1 <= e <= pv2], [> pv2]
                      |           |
                      s           b
                     g[0]        g[1]

  If need the second loop, After the second loop
         [< pv1],  [pv1....pv1], [ pv1< e < pv2], [pv2....pv2],  [> pv2]
                                  |         |
                                  s         b
                                 g[2]      g[3]
   */
  private static <T extends Comparable<T>> int[] partition(T[] a, int l, int r) {
    if (greatThan(a[l], a[r])) swap(a, l, r);
    T pv1 = a[l];
    T pv2 = a[r];

    int s = l, b = r;
    int i = l;
    while (i <= b) {
      if (lessThan(a[i], pv1)) swap(a, i++, s++);
      else if (greatThan(a[i], pv2)) swap(a, i, b--);
      else i++;
    }

    int[] result = new int[4];
    result[0] = s;
    result[1] = b;

    if (same(pv1, pv2)) return result;
    i = s;
    while (i <= b) {
      if (same(a[i], pv1)) swap(a, i++, s++);
      else if (same(a[i], pv2)) swap(a, i, b--);
      else i++;
    }
    result[2] = s;
    result[3] = b;
    return result;
  }
}
