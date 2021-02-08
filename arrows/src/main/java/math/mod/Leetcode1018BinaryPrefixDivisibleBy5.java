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

import java.util.ArrayList;
import java.util.List;

public class Leetcode1018BinaryPrefixDivisibleBy5 {
    public List<Boolean> prefixesDivBy5(int[] A) {
        // '1 <= A.length <= 30000'
        int k = 0;
        List<Boolean> ans = new ArrayList<>();
        for (int a : A) {
            // 'A[i] is 0 or 1'
            k = (k << 1 | a) % 5;
            ans.add(k == 0);
        }
        return ans;
    }
}
