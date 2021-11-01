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

package array.duplicated;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Leetcoe1209RemoveAllAdjacentDuplicatesinStringII {
  /*
  Leetcoe 1209. Remove All Adjacent Duplicates in String II

    You are given a string s and an integer k, a k duplicate removal consists of choosing k
    adjacent and equal letters from s and removing them, causing the left and the right side of
    the deleted substring to concatenate together.

    We repeatedly make k duplicate removals on s until we no longer can.

    Return the final string after all such duplicate removals have been made.
    It is guaranteed that the answer is unique.

    Input: s = "abcd", k = 2
    Output: "abcd"
    Explanation: There's nothing to delete.


    Input: s = "deeedbbcccbdaa", k = 3
    Output: "aa"
    Explanation:
    First delete "eee" and "ccc", get "ddbbbdaa"
    Then delete "bbb", get "dddaa"
    Finally delete "ddd", get "aa"
    Example 3:

    Input: s = "pbbcggttciiippooaais", k = 2
    Output: "ps"


    Constraints:
    1 <= s.length <= 105
    2 <= k <= 104
    s only contains lower case English letters.
  */

  /*
    "pbbcggttciiippooaais", 2 -> "ps"
    "deeedbbcccbdaa", 3-> "aa"
    "xzxxyyyxb", 3 -> "xzb"
    "xzxxyyyxb", 2 -> "xzyyyxb"

   // xzxb or xzb?

  Note:
  1  "It is guaranteed that the answer is unique"
  2  "choosing k adjacent and equal letters from s and removing them"
     means just exactly k adjacent and equal letters, or some times of k.


  Use list and stack O(N) time and space.
  */
  String removeDuplicates_____(String s, int number) {
    if (s == null || s.length() == 0) return "";
    List<Interval> list = new ArrayList<>();
    char[] ar = s.toCharArray();
    char cur = '\0';
    int n = 0;
    for (int i = 0; i < ar.length; i++) {
      if (i == 0) {
        cur = ar[i];
        n = 1;
      } else {
        if (ar[i] == ar[i - 1]) n++;
        else {
          list.add(new Interval(cur, n));
          cur = ar[i];
          n = 1;
        }
      }
    }
    // note here, the last cur is left
    list.add(new Interval(cur, n));

    Stack<Interval> stack = new Stack<>();

    for (Interval iv : list) {
      if (stack.isEmpty()) stack.push(iv);
      else {
        if (iv.n % number == 0) continue; // notice here.
        if (iv.c == stack.peek().c) {
          if ((iv.n + stack.peek().n) % number == 0) stack.pop();
          else stack.peek().n = (iv.n + stack.peek().n) % number;
          continue;
        }
        stack.push(iv);
      }
    }
    StringBuilder r = new StringBuilder();

    while (!stack.isEmpty()) {
      Interval curI = stack.pop();
      r.append(String.valueOf(curI.c).repeat(curI.n));
    }
    return r.reverse().toString();
  }

  class Interval {
    char c;
    int n;

    public Interval(Character c, int n) {
      this.c = c;
      this.n = n;
    }
  }
  /* --------------------------------------------------------------------------
  use StringBuilder to handler the string
  Memoise Count
  O(N) time and space.
  */
  public String removeDuplicates____(String s, int k) {
    StringBuilder sb = new StringBuilder(s);
    int count[] = new int[sb.length()];
    for (int i = 0; i < sb.length(); i++) {
      if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
        count[i] = 1;
      } else {
        count[i] = count[i - 1] + 1;
        if (count[i] == k) {
          sb.delete(i - k + 1, i + 1);
          i = i - k; // next is i++
        }
      }
    }
    return sb.toString();
  }

  public String removeDuplicates___(String s, int k) {
    StringBuilder sb = new StringBuilder(s);
    int count[] = new int[sb.length()];
    int i = 0;
    while (i < sb.length()) {
      if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
        count[i] = 1;
        i++;
      } else {
        count[i] = count[i - 1] + 1;
        if (count[i] == k) {
          sb.delete(i - k + 1, i + 1);
          i = i - k + 1;
        } else i++;
      }
    }
    return sb.toString();
  }
  /*
  same a bit space but does not change the O(N) space
  O(N) time and space.
  */
  public String removeDuplicates__(String s, int k) {
    StringBuilder sb = new StringBuilder(s);
    Stack<Integer> stack = new Stack<>(); // keep current counts at top
    for (int i = 0; i < sb.length(); i++) {
      if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
        stack.push(1);
      } else {
        int incremented = stack.pop() + 1;
        if (incremented == k) {
          sb.delete(i - k + 1, i + 1);
          i = i - k; // next is i++
        } else {
          stack.push(incremented);
        }
      }
    }
    return sb.toString();
  }

  /*
   Stack with Reconstruction
    If we store both the character and count in the stack,
    we do not have to modify the string.

    use stack keep the result.
    O(N) time and space.
  */
  public String removeDuplicates(String s, int k) {
    Stack<Interval> stack = new Stack<>();
    for (int i = 0; i < s.length(); ++i) {
      if (stack.empty() || s.charAt(i) != stack.peek().c) {
        stack.push(new Interval(s.charAt(i), 1));
      } else {
        stack.peek().n++;
        if (stack.peek().n == k) {
          stack.pop();
        }
      }
    }
    StringBuilder b = new StringBuilder();
    while (!stack.empty()) {
      Interval v = stack.pop();
      b.append(String.valueOf(v.c).repeat(v.n));
    }
    return b.reverse().toString();
  }

  public static void main(String[] args) {
    Leetcoe1209RemoveAllAdjacentDuplicatesinStringII t =
        new Leetcoe1209RemoveAllAdjacentDuplicatesinStringII();
    System.out.println(t.removeDuplicates("pbbcggttciiippooaais", 2).equals("ps"));
    System.out.println(t.removeDuplicates("deeedbbcccbdaa", 3).equals("aa"));
    System.out.println(t.removeDuplicates("xzxxyyyxb", 3).equals("xzb"));
    System.out.println(t.removeDuplicates("xzxxyyyxb", 2).equals("xzyyyxb"));
    // xzxb or xzb? "It is guaranteed that the answer is unique"
  }
}
