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

package array;

import java.util.Arrays;
import java.util.List;

public class Leetcode945MinimumIncrementtoMakeArrayUnique {
  /*
  945. Minimum Increment to Make Array Unique

  You are given an integer array nums. In one move, you can pick an index
   i where 0 <= i < nums.length and increment nums[i] by 1.

  Return the minimum number of moves to make every value in nums unique.

  Input: nums = [1,2,2]
  Output: 1
  Explanation: After 1 move, the array could be [1, 2, 3].

  Input: nums = [3,2,1,2,1,7]
  Output: 6
  Explanation: After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
  It can be shown with 5 or less moves that it is impossible for the array to have all unique values.


  Constraints:

  1 <= nums.length <= 105
  0 <= nums[i] <= 105
  Accepted
  44,901
  Submissions
  */

  /*
  O(NlogN) time used to sort the array
  O(1) space
   */
  public int minIncrementForUnique(int[] ar) {
    Arrays.sort(ar);
    int sum = 0;
    for (int i = 1; i < ar.length; i++) {
      if (ar[i] <= ar[i - 1]) {
        sum += ar[i - 1] - ar[i] + 1;
        ar[i] = ar[i - 1] + 1;
      }
    }
    return sum;
  }
}
