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

package backtracing;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/combination-sum-iii/?tab=Description">Leetcode</a>
 */
public class Leetcode216CombinationSumIII {
    // hints:  no duplicated items
    public List<List<Integer>> combinationSum3(int k, int target) {
        // input checking
        List<List<Integer>> result = new ArrayList();
        selectOne(result, new ArrayList<Integer>(), k, target, 0, 1, 1);
        return result;
    }

    // k:  1-based  item number of combination to be selected
    // can save ith_1basedToSelect by cur.size()
    // can save curSum by (target - each added item)
    private static void selectOne(List<List<Integer>> r, List<Integer> cur,
                                  int k, int target, int curSum,
                                  int ith_1basedToSelect, int selectFrom_1based) {
        if (ith_1basedToSelect > k) {
            if (curSum==target) {
                r.add(new ArrayList<>(cur));
            }
            return; // care
        }
        for (int i = selectFrom_1based; i <= 9 - k + ith_1basedToSelect; i++) {
            cur.add(i);
            selectOne(r, cur, k, target, curSum + i, ith_1basedToSelect + 1, i + 1);
            cur.remove(ith_1basedToSelect - 1);
        }
    }
}
