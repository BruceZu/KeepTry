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

package math;

import java.math.BigInteger;

public class Leetcode70ClimbingStairs {
  /*
    Leetcode 70. Climbing Stairs

    You are climbing a staircase. It takes n steps to reach the top.
    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?


    Input: n = 2
    Output: 2
    Explanation: There are two ways to climb to the top.
    1. 1 step + 1 step
    2. 2 steps


    Input: n = 3
    Output: 3
    Explanation: There are three ways to climb to the top.
    1. 1 step + 1 step + 1 step
    2. 1 step + 2 steps
    3. 2 steps + 1 step


    Constraints:
    1 <= n <= 45
  */
  /*
   Fibonacci Number
  n:  0  1  2  3  4  5
  v:  0  1  1  2  3  5
   Watch and observe that current v=sum of previous 2
   O(N) time and O(1) space
  */
  public int climbStairs(int n) {
    if (n == 1) return 1;

    int a = 1;
    int b = 2;
    for (int i = 3; i <= n; i++) {
      int c = a + b;
      a = b;
      b = c;
    }
    return b;
  }
  /*
    with Fibonacci formula the nth element in the Fibonacci series may be read off directly as a closed-form expression
    see https://en.wikipedia.org/wiki/Fibonacci_number
    Binet's Formula
    O(logN) time
    O(1) space

    Note: overflow need take in account.
  */
  // O(N) N is the index, O(1) space
  public static long fib(long index) {
    if (index < 0) throw new RuntimeException("input is invalid");
    if (index == 0 || index == 1) return 1;

    BigInteger a = BigInteger.valueOf(1), b = BigInteger.valueOf(1);
    long cur = 2;
    while (cur <= index) {
      BigInteger v = a.add(b);
      if (v.longValue() < 0){
        System.out.println(a.longValue());
        System.out.println(a.longValue());
        throw new RuntimeException("result is too big to calculate");
      }
      a = b;
      b = v;
      cur++;
    }
    return b.longValue();
  }

  public static void main(String[] paras) {
    // index  0 1 2 3 4 5 6
    // value. 1 1 2 3 5 8 13
    System.out.println(fib(1) == 1);
    System.out.println(fib(2) == 2);
    System.out.println(fib(3) == 3);
    System.out.println(fib(5) == 8);
    System.out.println(fib(6) == 13);
    System.out.println(fib(600));
    System.out.println(fib(-1));
  }
}
