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

import java.util.concurrent.CountDownLatch;

public class Sort {
    private static <T extends Comparable<T>> boolean lessThan(T a, T b) {
        return a.compareTo(b) < 0;
    }

    private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * For each loop，select the minimun one from the left member (un-sorted).
     * Swap the ith member with the selected one.
     * Run time is O(n^2).
     * 1 > independent of the array is sorted or not.
     * 2 > the number of swap times is the smallest one.
     *
     * @param arr
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

    /**
     * The runtime is depends on the input. If the input is sorted already its runtime is O(n);
     * It is used for partially-sorted arrays.
     *
     * @param arr
     */
    public static <T extends Comparable<T>> void insertSort(T[] arr) {
        // Input check
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
     * https://en.wikipedia.org/wiki/Shellsort
     * This is not the bast way to calculate gap/interval, only easy to remember.
     *
     * @param arrLength
     * @return
     */
    private static int initGap(int arrLength) {
        int gap = 1;
        while (gap < arrLength / 3) {
            gap = gap * 3 + 1;
        }
        return gap;

    }

    private static int nextGap(int oldGap) {
        return oldGap / 3;
    }

    /**
     * Worst-case time complexity Theta (N^{3/2})
     *
     * @param ar
     */
    public static <T extends Comparable<T>> void shellSort(T[] ar) {
        // Input check
        if (ar == null) {
            return;
        }
        int gap = initGap(ar.length);
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
            gap = nextGap(gap);
        }
    }

    /**
     * Divide arr into 2 halves
     */
    private static <T extends Comparable<T>> Comparable[][] divide(T[] arr) {
        Comparable[][] re = new Comparable[2][];

        Comparable[] l = new Comparable[arr.length / 2];
        Comparable[] r = new Comparable[arr.length - arr.length / 2];
        for (int i = 0; i < arr.length / 2; i++) {
            l[i] = arr[i];
        }
        for (int i = arr.length / 2, j = 0; i < arr.length; i++, j++) {
            r[j] = arr[i]; // note here
        }
        re[0] = l;
        re[1] = r;
        return re;
    }

    /**
     * Merge l and r into re
     *
     * @param l  sorted left half
     * @param r  sorted right half
     * @param re result
     */
    private static <T extends Comparable<T>> void merge(T[] l, T[] r, T[] re) {
        // System.out.format("\nmerge %s and %s\n", Arrays.toString(l), Arrays.toString(r));

        int i = 0, j = 0;
        int k = 0;
        while (true) {
            if (i == l.length && j == r.length) {
                break;
            } else if (i < l.length && (j == r.length || j < r.length && lessThan(l[i], r[j]))) {
                re[k++] = l[i++];

            } else { // Without else, k will trigger java.lang.ArrayIndexOutOfBoundsException
                re[k++] = r[j++];
            }
        }
        // System.out.format("\n     -->   %s\n", Arrays.toString(re));
    }

    /**
     * Merge sort an array concurrently
     *
     * @param arr
     * @return
     */
    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) {
            return;
        }
        // 1 Divide into 2 halves
        final Comparable[][] halves = divide(arr);

        // 2 Sort each halves
        final CountDownLatch cdLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (halves[0].length > 1) {
                    mergeSort(halves[0]);
                } // Else stop recursion

                cdLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (halves[1].length > 1) {
                    mergeSort(halves[1]);
                } // Else stop recursion

                cdLatch.countDown();
            }
        }).start();

        // 3 Merge them back into one.
        try {
            cdLatch.await(); // Simple than join() and CyclicBarrier
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        merge(halves[0], halves[1], arr);
    }

    /**
     * Merge sorted sub-array [l, mid] and [mid+1, r] into [l, r]
     *
     * @param ar  array
     * @param l   left index
     * @param mid index
     * @param r   right index
     */
    private static <T extends Comparable<T>> void merge2(T[] ar, int l, int mid, int r, T[] tmp) {
        // System.out.format("\nmerge %s : index scope[%s, %s] and  index scope [%s, %s]", Arrays.toString(ar), l, mid, mid + 1, r);

        // Improved
        if (lessThan(ar[mid], ar[mid + 1])) {
            return;
        }


        for (int i = l; i <= r; i++) {
            tmp[i] = ar[i];
        }
        // Start merge. Rewrite arr from l
        int i = l, j = mid + 1;
        int k = l;
        while (true) {
            if (i == mid + 1 && j == r + 1) {
                /* Both have no member */
                break;
            } else if (i <= mid && (j == r + 1 || j <= r && lessThan(tmp[i], tmp[j]))) {
                ar[k++] = tmp[i++];
            } else { // Without else, k will trigger java.lang.ArrayIndexOutOfBoundsException
                ar[k++] = tmp[j++];
            }
        }
        // System.out.format(" -->   %s \n", Arrays.toString(ar));
    }

    /**
     * Merge sort array arr's scope [l, r].
     *
     * @param arr array
     * @param l   left bound index, included.
     * @param r   right bound index, included.
     * @param tmp with it, do not need new tmp array every time when merge and care its length
     */
    private static <T extends Comparable<T>> void mergeSortRecursionHelp(T[] arr, int l, int r, T[] tmp) {
        if (l == r) {
            // stop recursion
            return;
        }
        // 1 Divide into 2 halves  [l, mid] and [mid+1, r]
        int mid = (l + r) / 2;

        // 2 Sort each halves
        mergeSortRecursionHelp(arr, l, mid, tmp);
        mergeSortRecursionHelp(arr, mid + 1, r, tmp);

        // 3 Merge them into one.
        merge2(arr, l, mid, r, tmp);
    }

    /**
     * Merge sort an array with one thread using recursion
     *
     * @param arr
     */
    public static <T extends Comparable<T>> void mergeSortRecursion(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // note: arr may be empty array: {}
            return;
        }
        mergeSortRecursionHelp(arr, 0, arr.length - 1, // note: The index is included
                new Comparable[arr.length]);
    }

    private static <T extends Comparable<T>> void mergeSortNoRecursionHelp(T[] arr, T[] tmp) {
        int len = 1; /* sub arr lengh */
        while (len <= arr.length) {
            int l = 0;
            int mid = l + len - 1;
            int r = mid + len;

            int rightBound = arr.length - 1;
            while (rightBound > mid) { // note !
                r = r <= rightBound ? r : rightBound; // note
                merge2(arr, l, mid, r, tmp);

                l = r + 1;
                mid = l + len - 1;
                r = mid + len;
            }
            len = len << 1;
        }
    }

    /**
     * Merge sort an array with one thread and no recursion
     *
     * @param arr
     */
    public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr may be empty array: {}
            return;
        }
        mergeSortNoRecursionHelp(arr, new Comparable[arr.length]);
    }

    private static <T extends Comparable<T>> boolean greatThan(T i, T u) {
        return i.compareTo(u) > 0;
    }

    /**
     * Improvement of quick sort.
     *
     * @param a
     * @param l   left index
     * @param r   right index
     * @param m   middle index
     * @param <T>
     */
    private static <T extends Comparable<T>> void initPivotByMedianOf3(T[] a, int l, int r, int m) {
        if (r == l + 1) {
            return;
        }
        int medianIndex = lessThan(a[l], a[m])
                ? lessThan(a[m], a[r]) ? m : lessThan(a[l], a[r]) ? r : l
                : lessThan(a[l], a[r]) ? l : lessThan(a[m], a[r]) ? r : m;
        if (medianIndex != l) {
            swap(a, l, medianIndex);
        }
    }

    /**
     * @param arr
     * @param p     left index included, as pivot firstly
     * @param other right index included
     * @param <T>
     * @return index of pivot
     */
    private static <T extends Comparable<T>> int pivotIndex(T[] arr, int p, int other) {
        initPivotByMedianOf3(arr, p, other, (p + other) / 2);
        while (p != other) {
            if (p < other && greatThan(arr[p], arr[other])
                    || p > other && lessThan(arr[p], arr[other])
                    ) {
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
     * O(N^2) , average O(NlgN)
     *
     * @param arr
     * @param l   left index included
     * @param r   right index included
     */
    private static <T extends Comparable<T>> void doQuickSort(T[] arr, int l, int r) {
        int p = pivotIndex(arr, l, r);

        if (p - 1 > l) {
            // Note: java.lang.ArrayIndexOutOfBoundsException
            doQuickSort(arr, l, p - 1); // Note: not 0
        }
        if (p + 1 < r) {
            // Note: java.lang.ArrayIndexOutOfBoundsException
            doQuickSort(arr, p + 1, r); // Note: not arr.length
        }
    }

    /**
     * Quick sort
     * https://en.wikipedia.org/wiki/Quicksort
     *
     * @param arr
     * @param <T>
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        doQuickSort(arr, 0, arr.length - 1); // Note
    }

    /**
     * For array having many repeated elements.
     * <p>
     * http://www.sorting-algorithms.com/quick-sort-3-way
     * http://www.sorting-algorithms.com/static/QuicksortIsOptimal.pdf
     * https://en.wikipedia.org/wiki/Radix_sort
     *
     * @param arr
     * @param <T>
     */
    public static <T extends Comparable<T>> void quickSort3Way(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        // todo
    }

    /**
     * http://permalink.gmane.org/gmane.comp.java.openjdk.core-libs.devel/2628
     * https://dzone.com/articles/algorithm-week-quicksort-three
     * http://www.isical.ac.in/~pdslab/2014/slides/23Quicksort.pdf
     *
     * @param arr
     * @param <T>
     */
    public static <T extends Comparable<T>> void quickSortDualPivot(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        // todo
    }
}
