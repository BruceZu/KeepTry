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

import static Common.greatThan;
import static Common.lessThan;
import static Common.same;
import static Common.swap;
import static sort.InsertSort.insertSort;

/**
 * <pre>
 * Improvement based on {@link QuickSortDualPivot  QuickSortDualPivot},
 * The improved content A and B are in {@link #improvedSelectAndLocateDualPivots(Comparable[], int, int) improvedSelectAndLocateDualPivots()} method
 *
 *  Improvement A:  find element <= pv2 before swap.
 *                  note: when there is no e <=pv2 between i and er, as current value is > pv2 so need er --;
 *
 *  Improvement B:  find out duplicated elements as pv1 or pv2
 *                  Note: At last, it is still 3 way. but using le, greatPv1, less Pv2, er to split
 *
 * @see <br><a href="http://permalink.gmane.org/gmane.comp.java.openjdk.core-libs.devel/2628">
 * Replacement of Quicksort in java.util.Arrays with new Dual-Pivot Quicksort
 * </a>
 */
public class QuickSortDualPivotImprovement {

    private static <T extends Comparable<T>> void doDualPivotQuickSort(T[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        if (arr.length <= 5) {
            insertSort(arr);
            return;
        }

        int[] dualPivots = improvedSelectAndLocateDualPivots(arr, l, r);
        int p1 = dualPivots[0], p2 = dualPivots[1];
        int greatPv1 = dualPivots[2], lessPv2 = dualPivots[3];

        doDualPivotQuickSort(arr, l, p1 - 1);
        doDualPivotQuickSort(arr, p2 + 1, r);
        if (lessThan(arr[p1], arr[p2])) {
            doDualPivotQuickSort(arr, greatPv1, lessPv2);
        }
    }

    public static <T extends Comparable<T>> void quickSortDualPivot(T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        doDualPivotQuickSort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> int[] improvedSelectAndLocateDualPivots(T[] arr, int l, int r) {
        // todo:  Improvement of how to select pivots
        if (greatThan(arr[l], arr[r])) {
            swap(arr, l, r);
        }
        T pv1 = arr[l];
        T pv2 = arr[r];

        int le = l + 1, er = r - 1;
        // 2
        for (int i = le; i <= er; i++) {
            Comparable v = arr[i];
            if (lessThan(v, pv1)) {
                swap(arr, i, le++);
            } else if (greatThan(v, pv2)) {
                // Improvement A:
                while (er > i
                        && greatThan(arr[er], pv2)) {
                    er--;
                }
                if (er == i) {
                    er--; // note:
                    break;
                }

                swap(arr, i, er--);

                if (lessThan(arr[i], pv1)) {
                    swap(arr, i, le++);
                }
            }
        }
        /**<pre>
         * now:
         *                    pv1,  < pv1,   pv1 <= e <= pv2,   > pv2, pv2
         *                                    |            |
         *                                   le            er
         */

        // 3
        swap(arr, l, --le);
        swap(arr, r, ++er);

        /**
         * <pre>
         *     Now:
         *                    < pv1,  pv1,   pv1 <= e <= pv2,   pv2,   > pv2
         *                             |      |            |     |
         *                            le     ic            ci    er
         *
         *
         * Improvement B:
         * aim
         *
         *                   < pv1,    pv1....pv1,   pv1< e < pv2,   pv2....pv2,  > pv2
         *                              |              |        |            |
         *                             le              ic       ci          er
         *
         */
        int ic = le + 1, ci = er - 1; // index of center field  pv1< e < pv2, included
        if (lessThan(pv1, pv2) && er - le > 2) {
            for (int i = ic; i <= ci; i++) {
                Comparable v = arr[i];
                if (same(v, pv1)) {
                    swap(arr, i, ic++);
                } else if (same(v, pv2)) {
                    while (ci > i && same(arr[ci], pv2)) {
                        ci--;
                    }
                    if (ci == i) {
                        ci--;
                        break;
                    }

                    swap(arr, i, ci--);

                    if (same(arr[i], pv1)) {
                        swap(arr, i, ic++);
                    }
                }
            }
        }
        return new int[]{le, er, ic, ci};
    }
}
