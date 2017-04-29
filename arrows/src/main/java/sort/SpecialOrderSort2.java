//  Copyright 2017 The keepTry Open Source Project
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

import common_lib.Merger;

import java.util.Arrays;

import static common_lib.Common.greatThan;

/**
 * Different with {@link SpecialOrderSort} is applying the specialIndex logical into
 * the customized merge sort process.
 */
public class SpecialOrderSort2 {
    //special order of index like 0, 2, 4, 5, 3, 1
    static public int specialIndex(int i, int len) {
        int d = 2 * i;
        return d < len ? d : (len & 1) == 0 ? len - 1 - d % len : len - d % (len - 1);
    }

    // merge 2 arrays in special order like Max, Min, second Max, second Min,....
    static public void mergeInSpecialDescendingOrder(Comparable[] array, int l, int mid, int r, Comparable[] tmp) {
        if (l >= r) return;
        // Improved, when it is already sorted in order
        if (greatThan(array[specialIndex(mid, array.length)], array[specialIndex(mid + 1, array.length)])) return;

        for (int i = l; i <= r; i++) tmp[specialIndex(i, array.length)] = array[specialIndex(i, array.length)];

        // Start merge in sort descending.
        int li = l, ri = mid + 1, k = l;
        while (!(li == mid + 1 && ri == r + 1)) {
            if (ri == r + 1 || li <= mid && greatThan(tmp[specialIndex(li, array.length)], tmp[specialIndex(ri, array.length)])) {
                array[specialIndex(k++, array.length)] = tmp[specialIndex(li++, array.length)];
            } else {
                array[specialIndex(k++, array.length)] = tmp[specialIndex(ri++, array.length)];
            }
        }
    }

    static public void SpecialOrderSort(Integer[] A) {
        if (A == null || A.length <= 1) return;
        new MergeSortNoRecursion(SpecialOrderSort2::mergeInSpecialDescendingOrder).mergeSortNoRecursion(A); // O(NlogN)
    }

    // ---------------------------------------------------------------------------------
    public static void main(String[] args) {
        // test cases
        //  6, 5, 4, 3, 2, 1
        Integer[] num = new Integer[]{6, 5, 4, 3, 2, 1};
        SpecialOrderSort(num);
        System.out.println(Arrays.toString(num));
    }
}
