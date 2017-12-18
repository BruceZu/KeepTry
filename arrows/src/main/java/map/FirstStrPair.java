//  Copyright 2017 The KeepTry Open Source Project
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

package map;import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FirstStrPair {
  public static void firstStringPair(List<String> strs) {
    //  Take the 26 English characters into account only. upper and lower cases.
    Map<String, String> mapping = new TreeMap();
    // O(NlgN)
    for (int j = 0; j < strs.size(); j++) {
      boolean[] temp = new boolean[58]; // 26 +26 +6 = 58
      char[] chars = strs.get(j).toCharArray();
      for (int i = 0; i < chars.length; i++) { // max length of strings' member
        temp[chars[i] - 'A'] = true;
      }
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < temp.length; i++) { // 58
        if (temp[i]) {
          sb.append((char) (i + 'A'));
        }
      }

      if (mapping.containsKey(sb.toString())) {
        // O(lgN)
        // If using HashMap: avg O(1), worse O(N)
        // If using Tire
        System.out.println(mapping.get(sb.toString()) + " and " + strs.get(j));
        return;
      }
      mapping.put(sb.toString(), strs.get(j));
    }
  }

  public static void main(String[] args) {
    firstStringPair(Arrays.asList("AABBCC", "AAAAB", "CCCGGG", "AACCBBAA"));
  }
}
