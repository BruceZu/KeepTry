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

/**
 * <pre>
 * given an inorder array.
 * find the number to match
 *     array[i] <= it <= array[j]
 *     i <  its index
 *     j > its index
 * Require runtime Big o(n), space Big O(1).
 * Assume there exists always at least one pivotal number.
 * ========================================================
 *                                update maxByfar
 *   ------------maxByfar------------------------------
 *
 *                             continue
 *    ---candidate--------------------------------------
 *                    invalid
 *   [array]
 */
public class FindPivotalNumber {
    public static int pivotalOf(int[] arr) {
        int maxByfar = arr[0];
        boolean stillValid = true;
        int candidate = arr[0];
        for (int i = 1; i < arr.length; i++) {

        }
        // TODO: 8/6/16
        return 0;
    }
}
