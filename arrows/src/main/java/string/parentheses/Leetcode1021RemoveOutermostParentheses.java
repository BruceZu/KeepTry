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

package string.parentheses;

public class Leetcode1021RemoveOutermostParentheses {
  // O(N) time and space
  public String removeOuterParentheses(String S) {
    if (S == null || S.length() <= 1) return S;
    int pn = 0;
    int from = 0;
    char[] s = S.toCharArray();
    StringBuilder r = new StringBuilder();
    for (int i = 0; i < s.length; i++) {
      // 'Given a valid parentheses string S',
      //  consider its primitive decomposition:
      // S = P_1 + P_2 + ... + P_k,
      // where P_i are primitive valid parentheses strings.
      if (s[i] == '(') pn++;
      else pn--;
      if (pn == 0) {
        // find a composition
        r.append(S.substring(from + 1, i));
        from = i + 1;
      }
    }
    return r.toString();
  }
  // O(N) time and space
  public String removeOuterParentheses2(String S) {
    if (S == null || S.length() <= 1) return S;
    // number of not matched '(' before current index i
    //
    // 'Given a valid parentheses string S',
    //  consider its primitive decomposition:
    // S = P_1 + P_2 + ... + P_k,
    // where P_i are primitive valid parentheses strings.'
    // for each valid primitive composition
    // - skip the start '(' by checking nn==0
    // - skip the last ')'  by checking nn==1
    int nn = 0;
    StringBuilder r = new StringBuilder();
    for (int i = 0; i < S.length(); i++) {
      char c = S.charAt(i);
      if (c == '(' && nn++ != 0) {
        r.append(c);
      }
      if (c == ')' && nn-- != 1) {
        r.append(c);
      }
    }
    return r.toString();
  }
}
