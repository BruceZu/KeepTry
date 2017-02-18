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
 * <a href="https://leetcode.com/problems/combinations/?tab=Description">LeetCode</a>
 */
public class Leetcode77Combinations {
    // hints:  no duplicated items
    // recursion
    public static List<List<Integer>> combine(int n, int k) {
        // input checking
        List<List<Integer>> result = new ArrayList();
        recursion(result, new ArrayList<Integer>(), n, k, 1, 1);
        return result;
    }

    // k:  1-based  item number of combination to be selected
    private static void recursion(List<List<Integer>> r, List<Integer> cur, int n, int k,
                                  int ith_1basedToSelect, int selectFrom_1based) {
        if (ith_1basedToSelect > k) {
            r.add(new ArrayList<>(cur));
            return; // care
        }
        for (int i = selectFrom_1based; i <= n - k + ith_1basedToSelect; i++) {
            cur.add(i);
            recursion(r, cur, n, k, ith_1basedToSelect + 1, i + 1);
            cur.remove(ith_1basedToSelect - 1);
        }
    }

    // ----------------------------
    public static void main(String[] args) {
        combine(4, 3);
        combine2(4, 2);
    }

    // ----------------------------
    // this is a way. while not straight
    public static List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (k > n || k < 0) {
            return result;
        }
        if (k == 0) {
            result.add(new ArrayList<Integer>());
            return result;
        }
        result = combine2(n - 1, k - 1);
        for (List<Integer> list : result) {
            list.add(n);
        }
        result.addAll(combine2(n - 1, k));
        return result;
    }
}
