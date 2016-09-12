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

public class InsertSort {
    private static <T extends Comparable> boolean lessThan(T i, T j) {
        return i.compareTo(j) < 0;
    }

    public static <T extends Comparable> void sortByInsert(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) {
            return;
        }
        //ascending
        for (int k = 1; k < arr.length; k++) {
            if (lessThan(arr[k], arr[k - 1])) {
                int p = k;
                T v = arr[k];
                do {
                    p--;
                    arr[p + 1] = arr[p]; // move back
                } while (p - 1 >= 0 && lessThan(v, arr[p - 1]));
                arr[p] = v; // find the right place.
            }
        }
    }

    /**
     * The runtime is depends on the input. If the input is sorted already its runtime is O(n);
     * It is used for partially-sorted arrays.
     */
    public static <T extends Comparable<T>> void insertSort(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && lessThan(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
        }
    }
}
