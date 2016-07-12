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

package locked.nosubmmitted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 170. Two Sum III - Data structure design
 * https://leetcode.com/problems/two-sum-iii-data-structure-design/
 * <pre>
 *     Difficulty: Easy
 * Design and implement a TwoSum class. It should support the following operations: add and find.
 *
 * add - Add the number to an internal data structure.
 * find - Find if there exists any pair of numbers which sum is equal to the value.
 *
 * For example,
 * add(1); add(3); add(5);
 * find(4) -> true
 * find(7) -> false
 * Hide Company Tags LinkedIn
 * Hide Tags: Hash Table, Design
 * Hide Similar Problems: (E) Two Sum (E) Unique Word Abbreviation
 *
 * </pre>
 */
public class LC170TwoSumIIIDatastructuredesign {
    // Your TwoSum object will be instantiated and called as such:
    // TwoSum twoSum = new TwoSum();
    // twoSum.add(number);
    // twoSum.find(value);

    class TwoSum {
        //the fast one beat 99.85%

        /**
         * beat 92.63%
         * Achieved by only maintaining a list with distinct elements.
         */
        private List<Integer> list = new ArrayList<Integer>();
        private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        // Add the number to an internal data structure.
        public void add(int number) {
            if (map.containsKey(number)) map.put(number, map.get(number) + 1);
            else {
                map.put(number, 1);
                list.add(number);
            }
        }

        // Find if there exists any pair of numbers which sum is equal to the value.
        public boolean find(int value) {
            for (int i = 0; i < list.size(); i++) {
                int num1 = list.get(i), num2 = value - num1;
                if ((num1 == num2 && map.get(num1) > 1) || (num1 != num2 && map.containsKey(num2))) return true;
            }
            return false;
        }

        /**
         * other concern The big data test only have the condition that lots of add and few find.
         * In fact, there has to be one operation's time complexity is O(n) and the other is O(1),
         * no matter add or find. So clearly there's trade off when solve this problem, prefer quick find or quick add.
         * If consider more find and less add or we only care time complexity in finding.
         * For example, add operation can be pre-done.
         */
    }
}
