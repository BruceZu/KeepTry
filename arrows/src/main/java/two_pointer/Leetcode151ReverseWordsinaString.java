//  Copyright 2022 The KeepTry Open Source Project
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

package two_pointer;

import java.util.*;

public class Leetcode151ReverseWordsinaString {
  /*
    151. Reverse Words in a String
    Given an input string s, reverse the order of the words.
    A word is defined as a sequence of non-space characters.
    The words in s will be separated by at least one space.

    Return a string of the words in reverse order concatenated by a single space.

    Note that
     - s may contain leading or trailing spaces or multiple spaces between two words.
     - The returned string should only have a single space separating the words.
       Do not include any extra spaces.

     Input: s = "the sky is blue"
     Output: "blue is sky the"


     Input: s = "  hello world  "
     Output: "world hello"
     Explanation: Your reversed string should not contain leading or trailing spaces.


     Input: s = "a good   example"
     Output: "example good a"
     Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.


     Constraints:

     1 <= s.length <= 104
     s contains English letters (upper-case and lower-case), digits, and spaces ' '.
     There is at least one word in s.


     Follow-up:
       If the string data type is mutable in your language,
       can you solve it in-place with O(1) extra space?
  */
  /*---------------------------------------------------------------------------
   Built-in Split + Reverse
   time and space O(N)
   using   Collections.reverse(ws); // O(N) ?
           String.join(" ", ws); // O(N) ?

   Follow-up: mutable means can change it on-site
   Reverse the Whole String and Then Reverse Each Word
   Space is still O(N) not space O(1)
   String in Java is immutable
  */
  // time and space O(N)
  public String reverseWords__(String s) {
    s = s.trim(); // remove leading and trailing space
    List<String> ws = Arrays.asList(s.split("\\s+")); // O(N) ?
    Collections.reverse(ws);
    return String.join(" ", ws);
  }

  /*---------------------------------------------------------------------------
  Use  SpringBuilder
  StringBuilder.length();
  StringBuilder.charAt(index);
  StringBuilder.setCharAt(index, char);

    time and space O(N)
    remove leading and tailing and within duplicated space
    reverse str
    reverse words
   */

  public String reverseWords_(String s) {
    StringBuilder sb = trimSpaces(s);
    reverse(sb, 0, sb.length() - 1);
    reverseEachWord(sb);
    return sb.toString();
  }

  public StringBuilder trimSpaces(String s) {
    int l = 0, r = s.length() - 1;
    // remove leading trailing spaces
    while (l <= r && s.charAt(l) == ' ') ++l;
    while (l <= r && s.charAt(r) == ' ') --r;

    // reduce multiple spaces to single one
    StringBuilder sb = new StringBuilder();
    while (l <= r) {
      char c = s.charAt(l);
      if (c != ' ') sb.append(c);
      else if (sb.charAt(sb.length() - 1) != ' ') sb.append(c);
      ++l;
    }
    return sb;
  }

  public void reverse(StringBuilder sb, int l, int r) {
    while (l < r) {
      char t = sb.charAt(l);
      sb.setCharAt(l, sb.charAt(r));
      sb.setCharAt(r, t);
      l++;
      r--;
    }
  }

  public void reverseEachWord(StringBuilder sb) {
    int N = sb.length();
    int s = 0, e = 0; // word start and end index

    while (s < N) {
      while (e <= N - 1 && sb.charAt(e) != ' ') e++;
      // e is space or at index N
      reverse(sb, s, e - 1);

      s = e + 1; // assume there is only one space
      e = s;
    }
  }

  /*---------------------------------------------------------------------------
  Deque of Words
  time and space O(N)

  StringBuilder.setLength(0), not new
  */
  public String reverseWords(String s) {
    int l = 0, r = s.length() - 1;
    while (l <= r && s.charAt(l) == ' ') ++l;
    while (l <= r && s.charAt(r) == ' ') --r;
    // Got valid index range, still there are some duplicate within spaces
    Deque<String> dq = new ArrayDeque();
    StringBuilder sb = new StringBuilder();
    while (l <= r) {
      char c = s.charAt(l);
      if (c != ' ') {
        sb.append(c);
      } else if (s.charAt(l - 1) != ' ') {
        dq.offerFirst(sb.toString()); // fore reverse order
        sb.setLength(0);
      }
      l++;
    }
    // pick up the last one
    dq.offerFirst(sb.toString());
    return String.join(" ", dq);
  }
}
