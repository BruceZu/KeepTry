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

package string;

import java.util.*;

public class WordsWrap {

  /*
  given workd list and max length
  connect words with "-" but not extends the max length
  Assume maxLen > max word length

  Arrays.copyOfRange(ws, from, i)
  String.join("-", iterable)
  */
  public List<String> wordWrap(String[] ws, int maxLen) {
    List<String> result = new LinkedList<>();
    int i = 0;
    while (i < ws.length) {
      int len = maxLen; // left allowed length
      int from = i;
      while (i < ws.length) {
        if (len < ws[i].length()) break;
        len -= ws[i].length() + 1;
        i++;
      }

      result.add(String.join("-", Arrays.asList(Arrays.copyOfRange(ws, from, i))));
    }
    return result;
  }
  /*
    Follow up: re-flow the words with a new max line length
    Given an array containing lines of text and a new maximum width,
    re-flow the text to fit the new width.
    Each line should have the exact specified width.
    If any line is too short, insert '-' ( stand for spaces) between words as equally possible until it fits.
    Note: we are using '-' instead of spaces between words to make testing and visual verification of the results easier.

    lines = [ "The day began as still as the",
               "night abruptly lighted with",
               "brilliant flame" ]

     reflowAndJustify(lines, 24) ... "reflow lines and justify to length 24" =>

             [ "The--day--began-as-still",
               "as--the--night--abruptly",
               "lighted--with--brilliant",
               "flame" ] // <--- a single word on a line is not padded with spaces
  */

  /*
  Assume the maxLen >= max word length
  Note: when the last word is precessed. the  last line may be left there. pick it up
        handle the extra '-' paddings
  */
  public static String[] reflow(String[] lines, int maxLen) {
    Iterator<String> ws =
        Arrays.asList(String.join(" ", Arrays.asList(lines)).split("\\s+")).iterator();
    List<String> ans = new LinkedList<>();

    List<String> l = new LinkedList<>();
    int left = maxLen;
    while (ws.hasNext()) {
      String w = ws.next();
      if (left >= w.length()) { // note the ==
        l.add(w);
        left -= w.length();
        left--;
      } else {
        // form a line
        ans.add(line(l, left + 1));
        left = maxLen - w.length() - 1;
        l = new LinkedList<>();
        l.add(w);
        // may be this is the last word
      }
    }
    if (l.size() != 0) ans.add(line(l, left + 1)); // don't forget the left one.

    String[] result = new String[ans.size()];
    return ans.toArray(result);
  }
  // for a line with words and "-" and extra pads number of "-";
  // a single word on a line is not padded with spaces
  private static String line(List<String> ws, int pads) {
    if (ws.size() == 1) return ws.get(0);
    StringBuilder l = new StringBuilder();
    // now: ws.size() is not 1
    int x = pads / (ws.size() - 1), mod = pads % (ws.size() - 1);
    int mi = 0, cnt = 0;
    for (String w : ws) {
      cnt++;
      if (cnt != ws.size()) {
        l.append(w);
        l.append("-");

        for (int i = 1; i <= x; i++) l.append("-");
        if (mi < mod) {
          l.append("-");
          mi++;
        }
      } else {
        l.append(w);
      }
    }
    return l.toString();
  }

  public static void main(String[] args) {
    /*
      lines = [ "The day began as still as the",
              "night abruptly lighted with",
              "brilliant flame" ]

      reflowAndJustify(lines, 24) ... "reflow lines and justify to length 24" =>

               [ "The--day--began-as-still",
              "as--the--night--abruptly",
              "lighted--with--brilliant",
              "flame" ] // <--- a single word on a line is not padded with spaces
    */
    String[] lines =
        new String[] {
          "The day began as still as the", "night abruptly lighted with", "brilliant flame"
        };
    for (String l : reflow(lines, 24)) System.out.println(l);
  }
}
