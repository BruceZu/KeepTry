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
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/factor-combinations/?tab=Description'>LeetCode</a>
 */
public class Leetcode254FactorCombinations {
    /**
     * [2, 16]
     * [2, 2, 8]
     * [2, 2, 2, 4]
     * [2, 2, 2, 2, 2]
     * [2, 4, 4]
     * [4, 8]
     */
    static public List<List<Integer>> getFactors_(int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getFactors_(result, new ArrayList<Integer>(), 2, n);
        return result;
    }

    static public void getFactors_(List<List<Integer>> result, List<Integer> cur, int scopeFrom, int n) {
        for (int i = scopeFrom; i * i <= n; ++i) {
            if (n % i == 0) {
                cur.add(i);
                //-
                cur.add(n / i);
                result.add(new ArrayList<Integer>(cur));
                cur.remove(cur.size() - 1);
                //-
                getFactors_(result, cur, i, n / i);
                cur.remove(cur.size() - 1);
            }
        }
    }

    /**
     * <pre>
     * improvement: provide the scope right limit to be Math.sqrt(last n)
     * if (candidatePrimeFactor > Math.sqrt(n)) {
     *   candidatePrimeFactor = n;
     * }
     * [2, 2, 2, 2, 2]
     * [2, 2, 2, 4]
     * [2, 2, 8]
     * [2, 4, 4]
     * [2, 16]
     * [4, 8]
     */
    static public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList();
        getFactors(result, new ArrayList<Integer>(), n, 2);
        return result;
    }

    static public void getFactors(List<List<Integer>> r, List<Integer> cur, int n, int scopeFrom) {
        if (n == 1) {// stop
            if (cur.size() > 1) {// n is prime when cur.size ==1. not valid
                r.add(new ArrayList(cur));
            }
            return;
        }

        // candidatorPrimeFactor scope: [scopeFrom, n]
        for (int i = scopeFrom; i <= n; i++) {
            if (n % i == 0) {
                cur.add(i);
                getFactors(r, cur, n / i, i); // next scope from current candidate to make sure the ascending order
                cur.remove(cur.size() - 1);
            }
        }
    }

    //-------------------------------------------------
    public static void main(String[] args) {
        getFactors_(2 * 3 * 5 * 7);
        getFactors_(2 * 2 * 3 * 3 * 5 * 7);
        getFactors_(32);
        getFactors(31);
    }
}
