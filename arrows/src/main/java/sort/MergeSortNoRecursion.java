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

import static common_lib.Common.mergeInsort;

public class MergeSortNoRecursion {

    /**
     * Merge sort an array without recursion. no thread safe
     * <pre>
     * Idea:
     * 1>   decide the size of sub array from 1, then 2, 4 ,8, 16 .... while the size <= arr.length()
     * 2>   with each size, loop arr from left:
     *   21>     calculate the index of 2 sub arrays: [l, mid] and [mid+1, r]
     *   22>     merge them in sort
     * <a href="http://softwareengineering.stackexchange.com/questions/297160/why-is-mergesort-olog-n">O(NLOGN)</a>
     */
    public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr may be empty array: {}
            return;
        }
        Comparable[] helper = new Comparable[arr.length];

        int arrLastIndex = arr.length - 1;
        int subArraySize = 1;
        while (subArraySize <= arr.length) {
            int l = 0;
            int mid = l + subArraySize - 1;
            int rAfterMerge = mid + subArraySize;
            // -> [l, mid] and [mid+1, r]

            while (mid < arrLastIndex) { // adjust when the len is very big or at the end of arr.
                rAfterMerge = rAfterMerge <= arrLastIndex ? rAfterMerge : arrLastIndex;
                mergeInsort(arr, l, mid, rAfterMerge, helper);

                l = rAfterMerge + 1;
                mid = l + subArraySize - 1;
                rAfterMerge = mid + subArraySize;
            }
            subArraySize = subArraySize << 1;
        }
    }
}
