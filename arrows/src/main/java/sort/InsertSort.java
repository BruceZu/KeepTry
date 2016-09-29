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

import static array.Common.swap;
import static array.Common.lessThan;

public class InsertSort {
    /**
     * Ascending order
     * The runtime is depends on the input. If the input is sorted already its runtime is O(n);
     * It is used for partially-sorted arrays.
     */
    public static <T extends Comparable<T>> void insertSort(T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        insertSort(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> void insertSort(T[] arr, int left, int right) {
        // Input check
        if (arr == null || arr.length <= 1 || right == left) {
            return;
        }
        T iv;
        int curIndex;
        for (int i = left + 1; i <= right; i++) {
            iv = arr[i];
            curIndex = i;
            while (left < curIndex && lessThan(iv, arr[curIndex - 1])) {
                swap(arr, curIndex, curIndex - 1);
                curIndex--;
            }
        }
    }
}
