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
 * Basic implementation of quick sort for a given array. <pre>
 * @see <a href="https://en.wikipedia.org/wiki/Quicksort">
 *     wiki reference
 *     </a>
 */
public class QuickSort {
    /**
     * <pre>
     * Idea:
     *    Make the one in l as pivot index firstly,
     *    Dance with the one in r, make sure the 2 in ascending order
     *    Continue to dance with next r.
     *
     * @param l   left index included, as pivot index firstly
     * @param r   right index included
     * @return    index of pivot
     */
    private static <T extends Comparable<T>> int locatePivotIndex(T[] arr, int l, int r) {
        int p = l, other = r;
        initPivotUsingMedianOf3(arr, p, other, (p + other) / 2);

        while (p != other) {
            if (p < other && greatThan(arr[p], arr[other])
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
        return p;
    }

    /**
     * <pre>
     * Idea:
     *   Take the one in l as pivot
     *   Locate the index of pivot in the sorted array.
     *   recursively call on the left 2 parts, till the length of the left part is 0 or 1
     *
     * O(N^2) , average O(NlgN)
     *
     * @param l   left index included
     * @param r   right index included
     */
    private static <T extends Comparable<T>> void call(T[] arr, int l, int r) {
        // Range check in recursion.  Note: java.lang.ArrayIndexOutOfBoundsException
        if (l >= r) {
            return;
        }
        // Improvement: for tiny array using  Insertion sort
        if (arr.length <= 4) {
            insertSort(arr);
            return;
        }

        int p = locatePivotIndex(arr, l, r);
        call(arr, l, p - 1); // Note: not 0
        call(arr, p + 1, r); // Note: not arr.length
    }

    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        call(arr, 0, arr.length - 1); // Note
    }
}
