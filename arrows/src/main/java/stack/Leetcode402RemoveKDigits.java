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

package stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Leetcode402RemoveKDigits {
  /*
   Leetcode 402. Remove K Digits

    Given string num representing a non-negative integer num, and an integer k,
    return the smallest possible integer after removing k digits from num.

    Input: num = "1432219", k = 3
    Output: "1219"
    Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

    Input: num = "10200", k = 1
    Output: "200"
    Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.


    Input: num = "10", k = 2
    Output: "0"
    Explanation: Remove all the digits from the number and it is left with nothing which is 0.


    Constraints:

    1 <= k <= num.length <= 105
    num consists of only digits.
    num does not have any leading zeros except for the zero itself.
  */
  /*
    A = 1axxx, B = 1bxxx, if the digits a > b, then A > B.
     iterate from the left to right,
     The more a digit to the left-hand side, the more weight it carries.
     E.g. 4259, if k=1, candidates as 4, 2, 5 and 9.
              compare each digit with its left neighbor. Starting from 2,
              2 < 4.  remove 4.
               if keep 4, result will be leading with 4, 459.
                  keep 2, result will be leading with 2, 259
              if k=2
              now it is 259
              compare 29, 25? remove 9 -> 25
     E.g. 189      k=1, -> 18
     E.g. 181      k=1, -> 11
     E.g. 12345    k=3, -> 12  monotonic increasing sequence,
     E.g. 12345264 k=4, ->  1224
     E.g. 1234524  k=4, -> remove3,4,5->  1224, compare 122, 124 -> 122
     E.g. 1002341  k=3  -> 0021 has leading 0

  loop: compare axxx and bxxx for abxxx
       - count the number of removed elements, it is possible
       the right top valve is still bigger than current number but k is matched now
       - when k becomes 0, left digits is appended to queue
       - compare happened from the second element.
  after the loop if k is not 0 then the q is a monotonic no descending sequence
  compare xxxa with xxxb for  xxxab
  E.g. 1002341  k=3  -> 0021 has leading 0

   Deque.peekLast()
   Deque.pollLast()
   Deque.offerLast()
  */
  class Solution {
    public String removeKdigits(String num, int k) {
      Deque<Character> q = new LinkedList<>();
      for (char n : num.toCharArray()) {
        while (q.size() > 0 && k > 0 && q.peekLast() > n) {
          q.pollLast();
          k--;
        }
        q.offerLast(n);
      }

      for (int i = 0; i < k; ++i) q.pollLast();

      StringBuilder r = new StringBuilder();
      boolean is0 = true; // is leading Zero
      for (char d : q) {
        if (is0 && d == '0') continue;
        is0 = false;
        r.append(d);
      }
      return is0 ? "0" : r.toString();
    }
  }
}
