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

import java.util.Arrays;

import static common_lib.Common.*;
import static common_lib.Common.same;
import static sort.InsertSort.insertSort;

/**
 * <pre>
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
 *
 *  Improvement: for tiny array using  Insertion sort
 * @see <a href="https://en.wikipedia.org/wiki/Quicksort">wiki Big O</a>
 */
public class QuickSortOnePivot2way {
    // select pivot and put it on the left side
    // partition other items to 2 way: < pivot |  >= pivot
    // allocate the pivot itself to its place in ascending ordered result.
    //  < pivot, pivot, >= pivot
    private static <T extends Comparable<T>> int locatePivotIndex(T[] arr, int l, int r) {
        int pivotIndex = getPivotIndexUsingMedianOf3(arr, l, r, (l + r) / 2);
        T pivot = arr[pivotIndex];

        swap(arr, l, pivotIndex);
        //Care:  now the pivot index is l
        pivotIndex = l;

        int nextLestIndex = l + 1;
        for (int i = l + 1; i <= r; i++) {
            if (lessThan(arr[i], pivot)) {
                swap(arr, i, nextLestIndex++);
            }
        }
        int locatedPivotIndex = nextLestIndex-1;
        swap(arr, pivotIndex, locatedPivotIndex);
        return locatedPivotIndex;
    }

    /**
     * @param l   left index included
     * @param r   right index included
     */
    private static <T extends Comparable<T>> void call(T[] arr, int l, int r) {
        // Range check in recursion.  Note: java.lang.ArrayIndexOutOfBoundsException
        if (l >= r) {
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
