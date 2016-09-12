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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Could you optimize your algorithm to use only O(k) extra space?
 *
 * @see <a href="https://leetcode.com/problems/pascals-triangle-ii/">leetcode</a>
 * @see <a href="https://en.wikipedia.org/wiki/Pascal%27s_triangle">Pascal's triangle</a>
 */
public class Leetcode119PascalsTriangleII {

    private static Map<Integer, List<Integer>> cache = new HashMap();

    static {
        cache.put(0, Arrays.asList(1));
        cache.put(1, Arrays.asList(1, 1));
    }

    public static List<Integer> getRow2(int rowIndex) {
        List r = cache.get(rowIndex);
        if (r != null) {
            return r;
        }
        List<Integer> pre = getRow2(rowIndex - 1);

        Integer[] cur = new Integer[rowIndex + 1];
        cur[0] = 1;
        cur[1] = rowIndex;


        for (int i = 2; i <= rowIndex / 2; i++) {
            cur[i] = pre.get(i) + pre.get(i - 1);
        }
        for (int i = rowIndex; i >= 0; i--) {
            if (cur[i] != null) {
                break;
            }
            cur[i] = cur[rowIndex - i];
        }
        cache.put(rowIndex, Arrays.asList(cur));
        return cache.get(rowIndex);
    }

    // Could you optimize your algorithm to use only O(k) extra space?
    public static List<Integer> getRow(int rowIndex) {
        if (rowIndex == 0) {
            return Arrays.asList(1);
        }
        if (rowIndex == 1) {
            return Arrays.asList(1, 1);
        }

        Integer[] arr = new Integer[rowIndex + 1];
        arr[0] = 1;
        int size = 1;
        while (size != arr.length) {
            arr[size] = 1;
            for (int i = size - 1; i > 0; i--) {
                arr[i] = arr[i] + arr[i - 1];
            }
            size++;
        }
        return Arrays.asList(arr);
    }
}
