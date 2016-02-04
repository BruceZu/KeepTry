//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.Arrays;

public class UniqueProblem {
    // element uniqueness problem
    // O(N^2) running time
    private static boolean my_isUnique(int[] a, int checked, int from) {
        if (a[checked] == a[from]) {
            return false;
        }
        if (from - 1 != checked) {
            return my_isUnique(a, checked, from - 1);
        }
        if (checked + 1 <= a.length - 2) {
            return my_isUnique(a, checked + 1, a.length - 1);
        }
        return true;
    }

    public static boolean isUnique(int[] a) {
        if (a.length == 1) {
            return true;
        }
        return my_isUnique(a, 0, a.length - 1);
    }

    // element uniqueness problem
    // O(NlogN) running time
    public static boolean isUnique2(int[] data) {
        int n = data.length;
        int[] clone = Arrays.copyOf(data, n);
        Arrays.sort(clone);
        for (int j = 0; j < n - 1; j++) {
            if (clone[j] == clone[j + 1]) {
                return false;
            }
        }
        return true;
    }
}
