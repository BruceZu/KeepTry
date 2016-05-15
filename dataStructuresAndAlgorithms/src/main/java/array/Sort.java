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

package array;

public class Sort {
    private static boolean lessThan(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * For the ith iterator，select the minimun one from the left member (un-sorted).
     * swap the ith member with the selected one.
     * run time is O(n^2).
     * 1 > independent of the array is sorted or not.
     * 2 > swap times number is the smallest one.
     *
     * @param arr
     */
    public static void selectionSort(Comparable[] arr) {
        // input check
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

    /**
     * The runtime is depends on the input. if the input is sorted already. its runtime is O(n);
     * It is used for partially-sorted arrays.
     *
     * @param arr
     */
    public static void insertSort(Comparable[] arr) {
        // input check
        if (arr == null) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && lessThan(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
        }
    }

    /**
     * http://www.tutorialspoint.com/data_structures_algorithms/shell_sort_algorithm.htm
     */
    public static void shellSort(Comparable[] ar) {
        // input check
        if (ar == null) {
            return;
        }
        int gap = ar.length >> 1;
        while (gap >= 1) {
            for (int i = 0 + gap; i < ar.length; i++) { // note: back step to check
                if (lessThan(ar[i], ar[i - gap])) {
                    int j = i;
                    while (j - gap >= 0 && lessThan(ar[j], ar[j - gap])) {
                        swap(ar, j, j - gap);
                        j = j - gap;
                    }
                }
            }
            gap = gap / 2; // ??
        }
    }
}
