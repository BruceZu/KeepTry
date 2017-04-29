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

import java.util.Arrays;
import java.util.Comparator;

/**
 * 2 pointer and a FIFO queue is good enough.
 * This class using index-mapping resolution like that in wiggle sort
 */
public class SpecialOrderSort {
    //special order of index like 0, 2, 4, 5, 3, 1
    public static int specialIndex(int i, int len) {
        int d = 2 * i;
        return d < len ? d : (len & 1) == 0 ? len - 1 - d % len : len - d % (len - 1);
    }

    // sort array in special order like Max, Min, second Max, second Min,....
    // O(NlogN) runtime, O(N) space.
    public static Integer[] specialSort(Integer[] A) {
        if (A == null || A.length <= 1) return A;
        Arrays.sort(A, Comparator.reverseOrder()); // O(NlogN)
        Integer[] r = new Integer[A.length];
        int i = 0;
        while (i < A.length) {
            r[specialIndex(i, A.length)] = A[i];
            i++;
        }
        return r;
    }

    // ---------------------------------------------------------------------------------
    public static void main(String[] args) {
        // test cases
        //  6, 5, 4, 3, 2, 1
        Integer[] num = new Integer[]{6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(specialSort(num)));
    }
}
