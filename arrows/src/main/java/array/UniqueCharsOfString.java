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

public class UniqueCharsOfString {
  // Hello xxx, This is Bruce. Thank you very much for your time!
  // Hi, This is xxxx

  /**
   * <pre>
   *
   * 1  Use char[256] array
   * 2  Use bit operation to check a char is repeat or not
   * Make sure: Run time O(N) and Space O(1)
   *
   * e.g.
   * Input 1: “Humpty Dumpty”
   * Output 1: “Humpty D”
   *
   * Input 2:"Bruce Zu"
   * StOutput 2:"Bruce Z"
   *
   * Input 3:"Developer"
   * StOutput 3:"Devlopr"
   *
   *
   * ASCII code.
   * http://www.theasciicode.com.ar/ascii-printable-characters/single-quote-apostrophe-ascii-code-39.html
   * - ASCII control characters: [0~31] and 127
   * - ASCII printable characters: [32-126]
   * - Extended ASCII characters:  [128-255]
   */
  public static String getUniqueChars(String input) {
    // input check
    if (input == null || input.length() <= 1) {
      return input;
    }

    boolean[] exist =
        new boolean[256]; // exist, a boolean array using char value as index, default is false
    StringBuilder r = new StringBuilder(); // keep result

    for (int i = 0; i < input.length(); i++) {
      char v = input.charAt(i);
      if (!exist[v]) {
        r.append(v);
        exist[v] = true;
      }
    }
    return r.toString();
  }

  /**
   * Using bit operation and assume the char is alphabet only."A~Za~z", [65~122]. Note [91~96] is
   * not alphabet
   */
  public static String getUniqueCharsBitWise(String input) {
    // input check
    if (input == null || input.length() <= 1) {
      return input;
    }

    StringBuilder r = new StringBuilder(); // keep result
    Long exits = 0l; //  Long type the 1 need be 1l
    for (int i = 0; i < input.length(); i++) {
      int move = (int) (input.charAt(i) - 'A');
      Long it = 1l << move; //  Long type the 1 need be 1l
      if ((exits & it) == 0) { // &  not &&
        r.append(input.charAt(i));
        exits += it;
      }
    }

    return r.toString();
  }
}
