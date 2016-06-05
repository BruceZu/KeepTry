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

import static array.Common.merge;

public class MergeSortNoRecursion {

    /**
     * Merge sort an array without recursion. no thread safe
     *
     * Idea: <pre>
     * 1>   decide sub array size from 1, then 2, 4 ,8, 16 .... while the size <= arr.length()
     * 2>   with each size, loop arr from left:
     *   21>     calculate the index of 2 sub arrays: [l, mid] and [mid+1, r]
     *   22>     merge them
     * <p>
     */
    public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr may be empty array: {}
            return;
        }
        Comparable[] tmp = new Comparable[arr.length];

        int len = 1;
        while (len <= arr.length) {
            int l = 0;
            int mid = l + len - 1;
            int r = mid + len;
            // -> [l, mid] and [mid+1, r]
            int arrLastIndex = arr.length - 1;
            while (arrLastIndex > mid) { // adjust when the len is very big or at the end of arr.
                r = r <= arrLastIndex ? r : arrLastIndex;
                merge(arr, l, mid, r, tmp);

                l = r + 1;
                mid = l + len - 1;
                r = mid + len;
            }
            len = len << 1;
        }
    }
}
