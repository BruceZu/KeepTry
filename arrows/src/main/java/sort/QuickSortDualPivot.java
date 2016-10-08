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

/**
 * <pre>
 *  @see
 *   <br><a href="https://dzone.com/articles/algorithm-week-quicksort-three">
 *      Algorithm of the Week: Quicksort - Three-way vs. Dual-pivot
 *      </a>
 *   <br><a href="http://www.isical.ac.in/~pdslab/2014/slides/23Quicksort.pdf">
 *       QUICK SORT
 *       </a>
 */
public class QuickSortDualPivot {

    /**
     * <pre>
     * Idea:
     *  1.  Select 2 pivots and make sure pv1 <= pv2
     *  2.  Locate indexes of sorted pivots by moving out e < pv1 to left and e > pv2 to right
     *  3.  Move pivots on both sides into center area
     *
     *      before step 2:
     *                          |              others             |
     *                    pv1,  e1,  ...        ei         ...   en, pv2
     *                          |                                 |
     *                          le                               er
     *
     *
     *      after step 2:
     *                    pv1,  < pv1,   pv1 <= e <= pv2,   > pv2, pv2
     *                                    |            |
     *                                   le            er
     *
     *
     *      after step 3:
     *                    < pv1,  pv1,   pv1 <= e <= pv2,   pv2,   > pv2
     *                                    |            |
     *                                   le           er
     *
     *                  Note: does not process duplicate elements ==pv1 or == pv2.
     *                        with pv1 and pv2 get another 3 way: < pv1; pv1 <= e <= pv2; > pv2
     *
     *      after step 2 if:
     *         A: no element match the center area: pv1 <= e <= pv2;
     *            then le > el.
     *            as for the last element, it is
     *                                            le
     *                                            |
     *                                           last
     *                                            |
     *                                            er
     *
     *
     *            after the last is 'moved':
     *                when the last < pv1
     *                                                  le
     *                                                   |
     *                                           last,  > pv2
     *                                            |
     *                                            er
     *
     *
     *
     *                when the last > pv2
     *                                            le
     *                                            |
     *                                   < pv1,  last
     *                                      |
     *                                      er
     *         B: there is only one element match.
     *            then: le = re
     *         c: there more than one elements match.
     *            then: le < re
     *
     * @return indexes of sorted dual pivots
     */
    private static <T extends Comparable<T>> int[] selectAndLocateDualPivots(T[] arr, int l, int r) {
        //1.
        if (greatThan(arr[l], arr[r])) {
            swap(arr, l, r);
        }
        T pv1 = arr[l];
        T pv2 = arr[r];

        //2.
        // The left and right index, included, of p1 <= e <= pv2
        int le = l + 1, er = r - 1;

        for (int i = le; i <= er; i++) {
            if (lessThan(arr[i], pv1)) {
                swap(arr, i, le++);
            } else if (greatThan(arr[i], pv2)) {
                swap(arr, i, er--);
                i--; // note: after swap, need check the new value of arr[i]
            }
            // pv1 <= e <= pv2
        }

        // 3.
        swap(arr, l, --le);
        swap(arr, r, ++er);
        return new int[]{le, er};
    }

    /**
     * For a give array, sort sub array[l, r]
     * <pre>
     * Idea:
     *  1. select 2 pivots and located their index after in sorted result.
     *  2. with these 2 pivots, split array into 2 way,
     *     for each way recursively call this method, till the way length is 1;
     *
     * @param arr
     * @param l  left index, included
     * @param r right index, included
     * @param <T>
     */
    private static <T extends Comparable<T>> void call(T[] arr, int l, int r) {
        // Input check
        if (l >= r) {
            return;
        }
        // Improvement: insertion sort for tiny array, 27 is experience point.
        if (arr.length <= 5) {
            insertSort(arr);
            return;
        }

        // 1
        int[] dualPivots = selectAndLocateDualPivots(arr, l, r);
        int p1 = dualPivots[0], p2 = dualPivots[1]; // index of pivot

        // 2
        // both sides way
        call(arr, l, p1 - 1);
        call(arr, p2 + 1, r);

        // middle way
        if (lessThan(arr[p1], arr[p2])) { // Improvement check if pv1 == pv2
            call(arr, p1 + 1, p2 - 1);
        }
    }

    public static <T extends Comparable<T>> void quickSortDualPivot(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        call(arr, 0, arr.length - 1);
    }
}
