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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AmazonOAValidDiscountCoupon {

  /*
   rule
    - empty string
    - if string A is valid then string xAx is valid
    - if string A, B is valid then AB or BA is valid

  Constrains:
    each discount coupon string length>=1
    each discount coupon string contains only lower case English letter
  */
  /*
  Stack
  O(N) time and space
   */
  public static List<Integer> findValidDiscountCoupons(List<String> discounts) {
    List<Integer> r = new ArrayList<Integer>();
    for (String c : discounts) {
      Stack<Character> stack = new Stack<>(); // clean the stack for every string
      for (int i = 0; i < c.length(); i++) {
        if (!stack.isEmpty() && stack.peek() == c.charAt(i)) {
          stack.pop();
        } else {
          stack.push(c.charAt(i));
        }
      }
      if (stack.isEmpty()) r.add(1);
      else r.add(0);
    }
    return r;
  }
  /*
  Recursion
  Implicit stack
  */
  public static List<Integer> findValidDiscountCoupons2(List<String> discounts) {
    List<Integer> r = new ArrayList<Integer>();
    for (String c : discounts) {
      r.add(f(c));
    }
    return r;
  }

  public static int f(String str) {
    int n = str.length();
    if (n == 0) return 1;
    if (n == 1) return 0;
    if (n == 2) return str.charAt(0) == str.charAt(n - 1) ? 1 : 0;

    // n>=3
    int i = str.lastIndexOf(str.charAt(0));
    if (i == 0) return 0; // not find pair

    // `i` may be same as 1, in this case sub is empty str
    if (f(str.substring(1, i)) == 0) return 0;
    return f(str.substring(i + 1));
  }

  public static void main(String[] args) {
    List<String> list =
        List.of(
            "abba",
            "abca",
            "abbacbbc",
            "aabb",
            "xaaxybbyzccz",
            "vaas",
            "jay",
            "abbaabba",
            "aabbaa");
    System.out.println(findValidDiscountCoupons(list));
    System.out.println(findValidDiscountCoupons2(list));
  }
}
