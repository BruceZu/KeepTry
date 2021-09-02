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

package string;

import java.util.ArrayList;
import java.util.List;

public class Leetcode68TextJustification {
  /*
       68. Text Justification
      an array of strings words and a width maxWidth,
      format the text such that
      - each line has exactly maxWidth characters and is fully (left and right) justified.
      - pack your words in a greedy approach; that is, pack as many words as you can in each line.
      - Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
        Extra spaces between words should be distributed as evenly as possible.
        If the number of spaces on a line does not divide evenly between words, the empty slots
        on the left will be assigned more spaces than the slots on the right.
      - the last line of text, it should be left-justified and no extra space is inserted between words.

     Note:
         - A word is defined as a character sequence consisting of non-space characters only.
         - Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
         - The input array words contains at least one word.

     Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
     Output:
     [
        "This    is    an",
        "example  of text",
        "justification.  "
     ]

     Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
     Output:
     [
       "What   must   be",
       "acknowledgment  ",
       "shall be        "
     ]
     Explanation: Note that the last line is "shall be    " instead of "shall     be",
      because the last line must be left-justified instead of fully-justified.
     Note that the second line is also left-justified becase it contains only one word.

     Input: words =
     ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
     Output:
     [
       "Science  is  what we",
       "understand      well",
       "enough to explain to",
       "a  computer.  Art is",
       "everything  else  we",
       "do                  "
     ]

     Constraints:
         1 <= words.length <= 300
         1 <= words[i].length <= 20
         words[i] consists of only English letters and symbols.
         1 <= maxWidth <= 100
         words[i].length <= maxWidth

  */

  /*
   Observe and Understanding
    - the last line also need have maxWidth length.
    - need keep original order of word.
  */

  /*
  Idea:
   1 plan:
       figure out the words index scope [i, j] to make the words length + j-i <= maxWidth
   2 render:
      figure out
      -  the number of whitespace inserted within 2 words:
          Note:
             - if j==i, only one word in the current line. need not insert whitespace
             - only add the whitespace after current word if it is not the last word.
          1. the average whitespace number:
             default 1, (maxWidth-words length)/(j-i)
          2. the number of left words that have one more whitespace:
             default 0, (maxWidth-words length)%(j-i)
      -  if needed, the line length < maxWidth, then to plus whitespace after the last word

   O(N) time and O(1) space
  */
  public List<String> fullJustify(String[] words, int maxWidth) {
    int i = 0;
    List<String> r = new ArrayList();
    while (i < words.length) {
      int j = i - 1, l = 0;
      // Note here: <=.  do not lost the ==
      while (j + 1 < words.length && l + words[j + 1].length() + j + 1 - i <= maxWidth) {
        j++;
        l += words[j].length();
      }
      r.add(line(words, i, j, l, maxWidth));
      i = j + 1;
    }
    return r;
  }

  private String line(String[] words, int l, int r, int wl, int max) {
    StringBuilder a = new StringBuilder();
    int q = 1, p = 0;
    if (r != l) { // Note: when l==r only one word on current line. need not insert " "
      q = (max - wl) / (r - l); // quotient| average while space within 2 words
      p = (max - wl) % (r - l); // reminder| the number of left words need a one more white space
    }

    for (int i = l; i <= r; i++) {
      a.append(words[i]);
      if (i != r) {
        if (r == words.length - 1) a.append(" ");
        else {
          for (int k = 1; k <= q; k++) a.append(" ");
          if (p-- >= 1) a.append(" ");
        }
      }
    }
    while (a.length() < max) a.append(" ");
    return a.toString();
  }
}
