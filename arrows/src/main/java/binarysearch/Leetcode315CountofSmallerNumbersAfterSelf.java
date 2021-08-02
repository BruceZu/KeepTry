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

package binarysearch;

import java.util.*;

public class Leetcode315CountofSmallerNumbersAfterSelf {
  /*
  You are given an integer array nums and
  you have to return a new counts array. The counts array has the
  property where counts[i] is the number of smaller elements to
  the right of nums[i].


  Input: nums = [5,2,6,1]
  Output: [2,1,1,0]

  Input: nums = [-1]
  Output: [0]

  Input: nums = [-1,-1]
  Output: [0,0]


    1 <= nums.length <= 10^5
    -10^4 <= nums[i] <= 10^4
   */

  /*
  Skill:
  - if binary search result x is a negative number
    then the index should be got by -(x+1) = -x-1 = (~x+1)-1 = ~x
  - duplicated number will be taken as not same (a, b) -> a >= b ? 1 : -1
      when a == b, they will not be token as same.
                   if inserting the one not existing in list b,
                   b would be inserted at the index of the existing one a and
                   a would be moved right.
    to make the result of   Collections.binarySearch(l, A[i], (a, b) -> a >= b ? 1 : -1)
    always be negative
  Idea: intuitive
  log(N^2) time
  */
  public static List<Integer> countSmaller(int[] A) {
    Integer[] r = new Integer[A.length];
    List<Integer> l = new ArrayList<>(A.length); // is a sorted list
    for (int i = A.length - 1; i >= 0; i--) {
      r[i] = ~Collections.binarySearch(l, A[i], (a, b) -> a >= b ? 1 : -1);
      l.add(r[i], A[i]); // no matter ArrayList or LinkedList it O(N)
    }
    return Arrays.asList(r);
  }
}
