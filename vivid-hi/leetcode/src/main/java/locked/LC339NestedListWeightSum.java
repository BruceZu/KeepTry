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

package locked;

import java.util.List;


/**
 * https://leetcode.com/problems/nested-list-weight-sum/  <p/>
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.  <p/>
 * Example 1: <p/>
 * Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)<p/>
 * Example 2:<p/>
 * Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
 * <p/>
 * Hide Company Tags LinkedIn<p/>
 * Hide Tags Depth-first Search
 */
public class LC339NestedListWeightSum {
    interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer.
        // Return null if this NestedInteger holds a nested list
        Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        List<NestedInteger> getList();
    }

    private int sumOf(List<NestedInteger> nestedList, int deep) {
        int r = 0;
        for (int i = 0; i < nestedList.size(); i++) {
            if (nestedList.get(i).isInteger()) {
                r += nestedList.get(i).getInteger() * deep;
            } else {
                r += sumOf(nestedList.get(i).getList(), deep + 1); // do not use deep ++
            }
        }
        return r;
    }

    public int depthSum(List<NestedInteger> nestedList) {
        return sumOf(nestedList, 1);
    }
}