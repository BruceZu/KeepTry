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

package deque;

import java.util.Deque;
import java.util.LinkedList;

/*
239. Sliding Window Maximum

You are given an array of integers nums,
there is a sliding window of size k which is
moving from the very left of the array to the very right.
You can only see the k numbers in the window.
Each time the sliding window moves right by one position.

Return the max sliding window.

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]

Input: nums = [1], k = 1
Output: [1]

Input: nums = [9,11], k = 2
Output: [11]

Input: nums = [4,-2], k = 2
Output: [4]


    1 <= nums.length <= 10^5
    -10^4 <= nums[i] <= 10^4
    1 <= k <= nums.length
*/

public class Leetcode239SlidingWindowMaximum {

  /*
   Leetcode 239. Sliding Window Maximum
     You are given an array of integers nums,
     there is a sliding window of size k which is moving
     from the very left of the array to the very right.
     You can only see the k numbers in the window.
     Each time the sliding window moves right by one position.

     Return the max sliding window.


    Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
    Output: [3,3,5,5,6,7]
    Explanation:
    Window position                Max
    ---------------               -----
    [1  3  -1] -3  5  3  6  7       3
     1 [3  -1  -3] 5  3  6  7       3
     1  3 [-1  -3  5] 3  6  7       5
     1  3  -1 [-3  5  3] 6  7       5
     1  3  -1  -3 [5  3  6] 7       6
     1  3  -1  -3  5 [3  6  7]      7

    Input: nums = [1], k = 1
    Output: [1]


    Input: nums = [1,-1], k = 1
    Output: [1,-1]

    Input: nums = [9,11], k = 2
    Output: [11]

    Input: nums = [4,-2], k = 2
    Output: [4]


    Constraints:
          1 <= nums.length <= 10^5
          -10^4 <= nums[i] <= 10^4
          1 <= k <= nums.length
  */

  /*
  Idea:
   descending order Dequeue keeps index of array element
   O(N) time, space
  */
  public int[] maxSlidingWindow(int[] nums, int k) {
    // 1 <= nums.length <= 10^5
    int[] r = new int[nums.length - k + 1];

    Deque<Integer> q = new LinkedList<>();
    for (int i = 0; i < nums.length; i++) {
      if (i - k + 1 >= 0 && !q.isEmpty() && q.peek() < i - k + 1) q.poll(); // out window Note: <
      while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast(); // Note: peekLast()
      q.offerLast(i); // in window
      if (i - k + 1 >= 0) r[i - k + 1] = nums[q.peek()];
    }
    return r;
  }
}
