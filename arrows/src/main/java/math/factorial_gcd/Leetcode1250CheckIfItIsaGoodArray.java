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

package math.factorial_gcd;

import java.util.*;

public class Leetcode1250CheckIfItIsaGoodArray {
  /*
   1250. Check If It Is a Good Array

   Given an array nums of positive integers.
   Your task is to select some subset of nums,
   multiply each element by an integer and add all these numbers.

   The array is said to be good if you can obtain a sum of 1 from the array
   by any possible subset and multiplicand.

   Return True if the array is good otherwise return False.


   Input: nums = [12,5,7,23]
   Output: true
   Explanation: Pick numbers 5 and 7.
   5*3 + 7*(-2) = 1

   Input: nums = [29,6,10]
   Output: true
   Explanation: Pick numbers 29, 6 and 10.
   29*1 + 6*(-3) + 10*(-1) = 1
   Example 3:

   Input: nums = [3,6]
   Output: false


   Constraints:
   1 <= nums.length <= 10^5
   1 <= nums[i] <= 10^9
  */

  /*
    Notice:
      - This is positive array
      - 'multiply each element by an integer and add all these numbers.' this should
        let's recall the GCD
   hints
     - Eq. ax+by=1 has solution x, y if gcd(a,b) = 1.
     - Can you generalize the formula?. Check Bézout's lemma.
    Generalization/Extension of Bezout's Lemma
    see https://artofproblemsolving.com/wiki/index.php/Bezout%27s_Lemma#Generalization.2FExtension_of_Bezout.27s_Lemma
    get the GCD of all element of the array, if it is 1 then
    There existing at least one sub set (at least 2 number) of the array has the GCD==1
  */
  public boolean isGoodArray(int[] A) {
    int x = A[0];
    for (int i = 1; i < A.length; i++) x = gcp(x, A[i]);
    return x == 1;
  }

  private int gcp(int s, int b) {
    while (s != 0) {
      int ns = b % s;
      b = s;
      s = ns;
    }
    return b;
  }
}
