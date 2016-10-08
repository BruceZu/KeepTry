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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static common_lib.Common.mergeInsort;

public class MergeSortRecursionMultiThreads2<T extends Comparable<T>> {
    // Runnable is enough, do not need Callable as each thread only work on its elements scope of arr
    private static class DivideMergeInSort implements Runnable {
        private Comparable[] arr, tmp;
        private int l, r;

        @Override
        public void run() {
            if (l == r) {
                // stop divide
                return;
            }

            int mid = (l + r) / 2;

            try {
                executorService.submit(new DivideMergeInSort(arr, l, mid, tmp)).get();
                executorService.submit(new DivideMergeInSort(arr, mid + 1, r, tmp)).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            mergeInsort(arr, l, mid, r, tmp);
        }

        public DivideMergeInSort(Comparable[] arr, int l, int r, Comparable[] tmp) {
            this.arr = arr;
            this.l = l;
            this.r = r;
            this.tmp = tmp;
        }
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // note: arr may be empty array: {}
            return;
        }
        try {
            executorService.submit(new DivideMergeInSort(arr, 0, arr.length - 1, new Comparable[arr.length])).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
