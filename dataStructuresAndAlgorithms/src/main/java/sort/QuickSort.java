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
        initPivotUsingMedianOf3(arr, l, r, (l + r) / 2);

        while (l != r) {
            if (l < r && greatThan(arr[l], arr[r])
                    || l > r && lessThan(arr[l], arr[r])) {
                swap(arr, l, r);
                // also swap index variable
                l ^= r;
                r ^= l;
                l ^= r;
            }
            if (l < r) {
                r--;
            } else {
                r++;
            }
        }
        return l;
    }

    /**
     * <pre>
     * Idea:
     *   Take the one in l as pivot
     *   Locate the index of pivot in the sorted array.
     *   recursively call on the left 2 parts, till the length of the left part is 0 or 1
     *
     * O(N^2)
     *  1  n-1  1 9 8 7 6 5 4 3 2
     *  2  n-2  1 2 9 8 7 6 5 4 3
     *  3  n-3  1 2 3 9 8 7 6 5 4
     *  n-1 1   1 2 3 4 5 6 7 8 9
     *
     * average O(NlgN)
     *  1    n                       1
     *  2    n/2 + n/2               2
     *  3    n/4 + n/4 + n/4 + n/4   4
     *
     *  lgn  n                       n
     * @see <a href="https://en.wikipedia.org/wiki/Quicksort">wiki Big O</a>
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
