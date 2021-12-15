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

import java.util.Stack;

public class Leetcode394DecodeString {
  /*
   Leetcode  394. Decode String

   Given an encoded string, return its decoded string.
   The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets
   is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

   You may assume that the input string is always valid;
   No extra white spaces, square brackets are well-formed, etc.

   Furthermore, you may assume that the original data does not contain any digits
   and that digits are only for those repeat numbers, k. For example,
   there won't be input like 3a or 2[4].

   Input: s = "3[a]2[bc]"
   Output: "aaabcbc"

   Input: s = "3[a2[c]]"
   Output: "accaccacc"

   Input: s = "2[abc]3[cd]ef"
   Output: "abcabccdcdcdef"

   Input: s = "abc3[cd]xyz"
   Output: "abccdcdcdxyz"


   Constraints:

   1 <= s.length <= 30
   s consists of lowercase English letters, digits, and square brackets '[]'.
   s is guaranteed to be a valid input.
   All the integers in s are in the range [1, 300].

  */
  /*---------------------------------------------------------------------------
  nested encoded strings like k[string k[string]]. For example, string s =3[a2[c]].
  decode the innermost string firstly

  use a Stack to keep string or string of number.
  met `[` just move ahead,
  met `]` sure there 2 string in stack top, repeated number and string.
  the expanded one need
     push back if current top one is a string of number of stack is empty
     if top is string, merge with it
  for any number just put it in stack
  for any string do the same as before
     push back if current top one is a string of number of stack is empty
     if top is string, merge with it (to make sure in the [] there will
      be only one string, thus once meet ], handle the merged string)

  Always assume the input is in valid format: previous content of '[' sure is number
  positive number can include `0`, even the fist digit should not be `0`

   E.g.  1
   'abc2[sad3[z]e]y'
                        |
    abc, 2, sad,  3, z, ]e]y
                     |
    abc, 2, sadzzz,  e]y
                     |
    abc, 2, sadzzze, ],y
                       |
    abcsadzzzesadzzze, y
    abcsadzzzesadzzzey

   E.g.  2
   "ab2[cd]"
               |
    ab, 2, cd, ]
    abcdcd

   E.g.  3
    2[2[x]y]
          |
    2,2,x,]y]
         |
    2,xx,y]
          |
    2,xxy,]
    xxyxxy
   */
  public static String decodeString____(String s) {
    if (s == null || s.length() == 0) return "";
    Stack<String> stack = new Stack<>();
    int i = 0;
    while (i < s.length()) {
      if (Character.isLetter(s.charAt(i))) {
        StringBuilder sb = new StringBuilder();
        while (i < s.length() && Character.isLetter(s.charAt(i))) sb.append(s.charAt(i++));
        enStack(stack, sb.toString());
      } else if (Character.isDigit(s.charAt(i))) {
        StringBuilder sb = new StringBuilder();
        while (i < s.length() && Character.isDigit(s.charAt(i))) sb.append(s.charAt(i++));
        stack.push(sb.toString());
      } else if (s.charAt(i) == '[') {
        i++;
      } else { // s.charAt(i) ==']'
        enStack(stack, stack.pop().repeat(Integer.valueOf(stack.pop())));
        i++;
      }
    }
    return stack.peek();
  }

  private static void enStack(Stack<String> stack, String v) {
    if (stack.isEmpty() || Character.isDigit(stack.peek().charAt(0))) stack.push(v);
    else stack.push(stack.pop() + v); // if previous is a string,  merge with it
  }

  /*---------------------------------------------------------------------------

    process the char one by one, only when current char is ], start to expand it
    keep the char in Stack<Character> or StringBuilder
 Observe:
    pro: need not merge
    con: when meet ']' need fetch back the string and number from character stream in stack

    Replace  Character.isDigit(char) replace  '0'<=char && char<='9'

   Time: O(V)
     V=L*maxk^{counterk}
     L: the maximum length of encoded string
     maxk: max value of repeat number k
     counter k:  is the count of nested k values
     E.g. 20[a10[bc]]:
          maxK is 2020,
          countK is 2 as there are 2 nested k values (20 and 10)
          there are 2 encoded strings `a` and `bc` with maximum length of encoded string , L as 2
          10[ab10[cd]]10[ef], time complexity would be roughly equivalent to  2*10^2
   Space: (sum(V))
  */
  public static String decodeString___(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == ']') {
        StringBuilder v = new StringBuilder();
        while (sb.charAt(sb.length() - 1) != '[') {
          v.insert(0, sb.charAt(sb.length() - 1));
          sb.deleteCharAt(sb.length() - 1);
        }
        sb.deleteCharAt(sb.length() - 1); // delete '['
        StringBuilder repeat = new StringBuilder();
        // Notice condition: sb.length()!=0 &
        while (sb.length() != 0 && Character.isDigit(sb.charAt(sb.length() - 1))) {
          repeat.insert(0, sb.charAt(sb.length() - 1));
          sb.deleteCharAt(sb.length() - 1);
        }
        int n = Integer.valueOf(repeat.toString());
        while (n-- >= 1) sb.append(v.toString());
      } else sb.append(s.charAt(i));
    }
    return sb.toString();
  }
  /*---------------------------------------------------------------------------
    Use 2 stack to keep the number and string separately
  Observe:
    when meet ']' what is ahead of it?
     - ']': no, it is handled if it exists
     - '[': no, format is valid
     - number: no, format is valid
     - string: yeah and it is the current string not in stack yet
   when meet '['
     will step in next layer, so need reset current string and number
     in the most inner nest [], there is no number, only there is string
     between2 '[', there is a number, maybe there is a no empty string, to
     make it easy to handle, take it as there is string which can be empty.
     it only is used to concatenate


    Time O(maxK⋅N) maxK is the maximum value of k, N is the length of a given string s.
    Space O(m+n),  m is the number of letters(a-z), n is the number of digits(0-9) in string s
                   In worst case, the maximum size of stringStack and  countStack could be m  and n respectively.
  */
  static String decodeString__(String s) {
    Stack<Integer> counter = new Stack<>();
    Stack<StringBuilder> stack = new Stack<>();
    StringBuilder cur = new StringBuilder();
    int k = 0;
    for (char c : s.toCharArray()) {
      if (Character.isLetter(c)) cur.append(c);
      if (Character.isDigit(c)) k = k * 10 + c - '0';
      if (c == '[') {
        counter.push(k);
        stack.push(cur);
        // reset
        cur = new StringBuilder();
        k = 0;
      }
      if (c == ']')
        cur = stack.pop().append(cur.toString().repeat(counter.pop())); //  some kind of merge
    }
    return cur.toString();
  }
  /*---------------------------------------------------------------------------
    Recursion using internal call stack
    notice: control the index of current char.
 Observe:
    there 2 possible relation between pairs of []:
    - nested: [[[[ ... ]]]]
    - parallel at the same layer: [][][][] ...
    at the same layer, there could be more than one number,
    but prepare one string is okay, once a `k[str]` is expanded it will be string, concatenate it

    version 1:
  */

  String decodeString_(String str) {
    return f(1, str);
  }
  // a2[b3[x]c4[m]o]
  String f(int repeat, String s) {
    // current layer string and number
    StringBuilder sb = new StringBuilder();
    int k = 0;
    while (i < s.length()) {
      char c = s.charAt(i);
      if (Character.isLetter(c)) {
        sb.append(c);
        i++;
      }
      if (Character.isDigit(c)) {
        k = k * 10 + c - '0';
        i++;
      }
      if (c == ']') {
        i++;
        return sb.toString().repeat(repeat);
      }
      if (c == '[') {
        i++; // before call recursion
        sb.append(f(k, s)); // current string and number
        k = 0; // notice here
      }
    }
    return sb.toString();
  }

  int i = 0;

  /*
    version 2: not handle char one by one in the outer loop, when meet number chars, parse them all and expand the
    pattern.
    pro: k is local variable need not reset
    cons:
    Time  O(maxK⋅N) maxK is the maximum value of k, N is the length of a given string s.
    Space  O(N). used to store the internal call stack used for recursion.
                 the maximum depth of recursive call stack would not be more than N
  */
  String decodeString(String s) {
    StringBuilder sb = new StringBuilder(); // default is empty string
    while (i < s.length() && s.charAt(i) != ']') {
      if (Character.isLetter(s.charAt(i))) sb.append(s.charAt(i++));
      else { // number char
        int k = 0; // local variable need not reset
        while (i < s.length() && Character.isDigit(s.charAt(i))) k = k * 10 + s.charAt(i++) - '0';
        i++; // skip '[' to next index
        sb.append(decodeString(s).repeat(k));
        i++; // skip ']' to next index
      }
    }
    return new String(sb); // loop over or meet ']'
  }
}

/*
  a>  encoding  aaabcdddddddddd => 3abc10d.   single char `x` need not to be `1x` : compare cur char with previous char,
                                                                                  - if same the collect the previous one, count=0. preChar = cur;
                                                                                  - else count++

  b> decoding  3abc10d => aaabcdddddddddd.  if meet char then decoding it. note single char `x` need not to be `1x`;
                                            else it is digit, use a Stringbuilder keep the digit
  c> input is steam, each time consume a char and print out current decoding result:
     use Stirngbuilder keep the immute part, possible mute part:  counter,  preChar. note single char `x` need not to be `1x`;
     initial prechar is null, count=0;
        if(preChar==null||cur==preChar){
           count++;
           preChar=cur; // note here
           return immute.toString() + (count>1? count+cur: cur);
        }
 rule:
  1. consume one char each time:
  2. encoding: loop over means reach a char=null, also collect the last part '10d'
 */