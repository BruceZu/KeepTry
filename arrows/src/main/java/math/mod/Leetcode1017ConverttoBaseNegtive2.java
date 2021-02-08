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

package math.mod;

public class Leetcode1017ConverttoBaseNegtive2 {
  public String baseNeg2(int N) {
    StringBuilder res = new StringBuilder();
    // '0 <= N <= 10^9'
    while (N != 0) {
      res.append(N & 1);
      N = -(N >> 1);
    }
    // 'The returned string must have no leading zeroes, unless the string is "0".'
    return res.length() > 0 ? res.reverse().toString() : "0";
  }
}
