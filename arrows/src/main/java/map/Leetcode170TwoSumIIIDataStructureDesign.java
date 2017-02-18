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

package map;

import java.util.*;

/**
 * <a href="https://leetcode.com/problems/two-sum-iii-data-structure-design/?tab=Description">LeetCode</a>
 * find only need a boolean=> It is enough to check if there is one exists.
 */
public class Leetcode170TwoSumIIIDataStructureDesign {
    //=> maybe duplicated, even though map can be used here,
    // pros: do not need sort O(nlogn)
    static class TwoSum {
        Map<Integer, Integer> numToCounts;

        public TwoSum() {
            numToCounts = new HashMap();
        }

        // O(1)
        public void add(int number) {
            Integer intToCounts = numToCounts.get(number);
            numToCounts.put(number, intToCounts == null ? 1 : intToCounts + 1);
        }

        //Find if there exists any pair of numbers which sum is equal to the value.
        // O(N)
        public boolean find(int value) {
            for (Map.Entry<Integer, Integer> cur : numToCounts.entrySet()) {
                int curNum = cur.getKey();
                int curNumCounts = cur.getValue();
                int left = value - curNum;
                if (curNum == left && curNumCounts > 1  || curNum != left && numToCounts.containsKey(left)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        // Your TwoSum object will be instantiated and called as such:
        TwoSum obj = new TwoSum();
        obj.add(0);
        boolean param_2 = obj.find(0);
        System.out.println(param_2);
    }
}
