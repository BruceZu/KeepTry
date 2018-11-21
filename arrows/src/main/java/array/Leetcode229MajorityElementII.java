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

package array;

import java.util.ArrayList;
import java.util.List;

// Majority Element II Total
// Given an integer array of size n, find all elements that appear more than⌊ n/3 ⌋ times.
// The algorithm should run in linear time and in O(1) space.

/**
 * <pre>
 * <br><a href= "https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm">Boyer Moore vote algorithm </a>
 * <br><a href= "https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html">Boyer Moore vote algorithm </a>
 */
public class Leetcode229MajorityElementII {
  public static List<Integer> MajorityElementOf(int[] nums) {
    int numb1 = 0, numb2 = 0;
    int vote1 = 0, vote2 = 0;

    for (int i = 0; i < nums.length; i++) {
      int iv = nums[i];

      if (vote1 == 0) {
        numb1 = iv;
        vote1 = 1;
        continue;
      }
      if (iv == numb1) {
        vote1++;
        continue;
      }
      if (vote2 == 0) {
        numb2 = iv;
        vote2 = 1;
        continue;
      }
      if (iv == numb2) {
        vote2++;
        continue;
      }
      vote1--;
      vote2--;
    }

    // got 2 candidates with bigger counts than others
    int count1 = 0, count2 = 0;
    for (int n : nums) {
      if (n == numb1) {
        count1++;
        continue;
      }
      if (n == numb2) {
        count2++;
      }
    }

    List result = new ArrayList(2);
    if (count1 > nums.length / 3) {
      result.add(numb1);
    }
    if (count2 > nums.length / 3) {
      result.add(numb2);
    }
    return result;
  }
}
