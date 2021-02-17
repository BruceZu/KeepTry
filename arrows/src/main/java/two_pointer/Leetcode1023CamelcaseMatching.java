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

package two_pointer;

import java.util.ArrayList;
import java.util.List;

public class Leetcode1023CamelcaseMatching {

  public List<Boolean> camelMatch(String[] queries, String pattern) {
    /*
     1 <= queries.length <= 100
     1 <= queries[i].length <= 100
     1 <= pattern.length <= 100
     All strings consists only of lower and upper case English letters.
    */
    List<Boolean> result = new ArrayList<Boolean>();
    for (String str : queries) result.add(isMatch(str, pattern));
    return result;
  }

  // no matter what the current pattern char is, current char of string should not be upper case
  // before match.
  private static Boolean isMatch(String str, String pattern) {
    int i = 0, j = 0;
    while (j < pattern.length()) {
      while (i < str.length() && str.charAt(i) != pattern.charAt(j) && str.charAt(i) >= 'a') i++;
      if (i == str.length()) return j == pattern.length();
      if (str.charAt(i) == pattern.charAt(j)) {
        j++;
        i++;
        continue;
      }
      if (str.charAt(i) < 'a') return false;
    }
    while (i < str.length() && str.charAt(i) >= 'a') i++;
    return i == str.length();
  }
}
