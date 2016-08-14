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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode1twoSum2 {
    /**
     * <pre>
     * HashMap runtime is O(N), space:
     *  cons: slow,
     *  pros: can find all pair if using Map<Integer, List<Integer>> map
     */
    public static int[] twoSum2(final int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            if (map.containsKey(target - v)) {
                return new int[]{map.get(target - v).get(0), i};
            }
            List indexes = map.get(v);
            if (indexes == null) {
                indexes = new ArrayList();
                indexes.add(i); // keep all indexes
                map.put(v, indexes);

            } else {
                indexes.add(i);
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * <pre>
     * simple version as only need return one pair
     * Note
     *             if (map.containsKey(v)) {
     *                  continue; // wrong, e.g. test case: [0,4,3,0], 0
     *             }
     */
    public static int[] twoSum(final int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            if (map.containsKey(target - v)) {
                return new int[]{map.get(target - v), i};
            }
            map.put(v, i);
        }
        return new int[]{-1, -1};
    }
}
