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

import static array.Common.lessThan;
import static array.Common.swap;

public class SelectionSort {
    /**
     * Idea:
     * For each loop，select the minimum one from the left member (un-sorted).
     * Swap the ith member with the selected one.
     * Run time is O(n^2).
     * 1 > independent of the array is sorted or not.
     * 2 > the number of swap times is the smallest one.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        // Input check
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int tmp = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (lessThan(arr[j], arr[tmp])) {
                    tmp = j;
                }
            }
            if (tmp != i) {
                swap(arr, tmp, i);
            }
        }
    }
}
