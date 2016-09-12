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

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Let A be an array of size n ≥ 2 containing integers from 1 to n−1 inclusive,
 *
 *  e.g. n = 5
 *  3 1 2 3 4
 *  0 1 2 3 4
 *
 *  4 1 2 3 4
 *  0 1 2 3 4
 *
 *  one of which is repeated. Describe an algorithm for finding the integer in A that is
 *  repeated.
 */
public class FindRepeatedInteger {
    public static int getTheRepeatedInteger(int[] array) {
        Set set = new HashSet(array.length - 1);
        for (int i = 0; i < array.length; i++) {
            if (set.contains(array[i])) {
                return array[i];
            }
            set.add(array[i]);
        }
        return 0;
    }

    public static int getTheRepeatedInteger2(int[] array) {
        int sum = array[0];
        int s = 0;
        for (int i = 1; i < array.length; i++) {
            sum += array[i];
            s += i;
        }
        return sum - s;
    }
}
