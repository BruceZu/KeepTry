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

package bitmanipulation;

public class Leetcode1016BinaryStringWithSubstringsRepresenting1ToN {

  public boolean queryString(String S, int N) {
    if (N > 1024 * 2) return false;
    //     1 <= S.length <= 1000
    //            1 <= N <= 10^9
    for (int i = N; i > N / 2; --i) if (!S.contains(Integer.toBinaryString(i))) return false;
    return true;
  }
}
