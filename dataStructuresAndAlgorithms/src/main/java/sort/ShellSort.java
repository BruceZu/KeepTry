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

public class ShellSort {

    /**
     * This is not the bast way to calculate gap/interval, only easy to remember.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Shellsort"> wiki reference </a>
     * @param arrLength
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
     * <p>
     * Idea:
     * just like insert sort, but not loop the element one by one, instead
     * by gap distance, then reduce the gap, start next loop, till gap ==1.
     * Many ways to select the gap.
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
}
