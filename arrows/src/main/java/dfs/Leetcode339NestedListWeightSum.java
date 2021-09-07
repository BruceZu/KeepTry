//  Copyright 2021 The KeepTry Open Source Project
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

package dfs;

import java.util.List;

/*
339. Nested List Weight Sum

You are given a nested list of integers nestedList.
Each element is either an integer or a list whose elements may also be integers or other lists.

The depth of an integer is the number of lists that it is inside of.
For example, the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth.

Return the sum of each integer in nestedList multiplied by its depth.


Input: nestedList = [[1,1],2,[1,1]]
Output: 10
Explanation: Four 1's at depth 2, one 2 at depth 1. 1*2 + 1*2 + 2*1 + 1*2 + 1*2 = 10.


Input: nestedList = [1,[4,[6]]]
Output: 27
Explanation: One 1 at depth 1, one 4 at depth 2, and one 6 at depth 3. 1*1 + 4*2 + 6*3 = 27.


Input: nestedList = [0]
Output: 0

Constraints:
    1 <= nestedList.length <= 50
    The values of the integers in the nested list is in the range [-100, 100].
    The maximum depth of any integer is less than or equal to 50.
*/
public class Leetcode339NestedListWeightSum {
  interface NestedInteger {

    boolean isInteger();

    // @return the single integer if it holds a single integer.
    // Return null if this NestedInteger holds a nested list
    Integer getInteger();

    // @return the nested list if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    List<NestedInteger> getList();
  }

  /*
   DFS recursion. (BFS also works. `queue.addAll(nestedList);` for list
   O(N) time and space
  */
  public int depthSum(List<NestedInteger> nestedList) {
    return sum(nestedList, 1); // deeep starts with 1, not 0 for multiply
  }

  private int sum(List<NestedInteger> l, int deep) {
    int r = 0;
    for (int i = 0; i < l.size(); i++) {
      if (l.get(i).isInteger()) r += l.get(i).getInteger() * deep;
      else r += sum(l.get(i).getList(), deep + 1); // do not use deep ++
    }
    return r;
  }
}
