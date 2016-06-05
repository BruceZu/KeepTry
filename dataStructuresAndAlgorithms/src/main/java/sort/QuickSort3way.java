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

import static array.Common.greatThan;
import static array.Common.initPivotUsingMedianOf3;
import static array.Common.lessThan;
import static array.Common.swap;
import static sort.InsertSort.insertSort;

/**
 * <pre>
 * @see
 *   <br><a href="http://www.sorting-algorithms.com/quick-sort-3-way">
 *      Quick Sort (3 Way Partition)
 *      </a>
 *   <br><a href="http://www.sorting-algorithms.com/static/QuicksortIsOptimal.pdf">
 *      QUICKSORT IS OPTIMAL Robert Sedgewick Jon Bentley
 *      </a>
 *   <br><a href="https://en.wikipedia.org/wiki/Radix_sort">
 *       wiki: Radix sort
 *   </a>
 */
public class QuickSort3way {
    /**
     * @param l   Left index
     * @param r   Right index
     * @return Left and right index of center pivot area
     */
    private static <T extends Comparable<T>> int[] locatePivotIndex3Way(T[] arr, int l, int r) {
        // Improvement
        initPivotUsingMedianOf3(arr, l, r, (l + r) / 2);

        int lp = l - 1; // right index of left pivots area
        int rp = r + 1; // left index of right pivots area
        /**<pre>
         * meaning of lp and rp:
         *
         *  pppp, < p ,  p , > p, pppp
         *     |                  |
         *    lp                 rp
         *
         *  init value:
         *
         *       [p a b c d e f]
         *     |                 |
         *    lp                 rp
         *
         *  general:
         *
         *     p p p, 1, 2, p, 6, p, 17, 18, 19, p p p
         *         |        |     |              |
         *         lp           other            rp
         */

        int p = l, other = r;

        while (p != other) {
            // equal
            if (arr[p] == arr[other]) {
                if (other > p) {
                    swap(arr, other, --rp);
                } else { // Note: must 'else', else run both of them.
                    swap(arr, other, ++lp);
                }
            } else if (p < other && greatThan(arr[p], arr[other])
                    || p > other && lessThan(arr[p], arr[other])) {
                swap(arr, p, other);
                // also swap index variable
                p ^= other;
                other ^= p;
                p ^= other;
            }
            if (p < other) {
                other--;
            } else {
                other++;
            }
        }

        /**
         * <pre>
         *  Now: p, p, p | < p | p | > p | p, p, p
         *             |         |         |
         *            lp                   rp
         */


        // Move both sides pivot value into the center
        int pr = p; // center pivots area right index
        if (lp != l - 1) {
            while (lp >= l) {
                swap(arr, --p, lp--);
            }
        }

        if (rp != r + 1) {
            while (rp <= r) {
                swap(arr, ++pr, rp++);
            }
        }

        //Now 3 way:
        //           <p  |  =p  |   >p
        return new int[]{p, pr};
    }

    private static <T extends Comparable<T>> void call(T[] arr, int l, int r) {
        // Range check.  Note: java.lang.ArrayIndexOutOfBoundsException
        if (l >= r) {
            return;
        }
        // Improvement: for tiny array using  Insertion sort
        if (arr.length <= 4) {
            insertSort(arr);
            return;
        }

        int[] p = locatePivotIndex3Way(arr, l, r);
        call(arr, l, p[0] - 1); // Note: not 0
        call(arr, p[1] + 1, r); // Note: not arr.length
    }

    /**
     * Idea: <pre>
     * Improves quick sort in presence of duplicate keys.
     * The difference with {@link sort.QuickSort QuickSort} is in the
     * {@link #locatePivotIndex3Way(T[] arr, int l, int r) locatePivotIndex3Way} method.
     * <p>
     * where taking in account the equal relation:
     * <p>
     *   1 Move all duplicated pivots to both sides
     *   2 After loop, at las move them from both sides into center.
     */
    public static <T extends Comparable<T>> void quickSort3Way(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        call(arr, 0, arr.length - 1);
    }
}
