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

import java.util.ArrayList;
import java.util.Collections;

public class Codelab_Mise_holes {
    /**
     * @see <a href="https://codelab.interviewbit.com/problems/mice/">code lab</a>
     */
    public int mice(ArrayList<Integer> A, ArrayList<Integer> B) {
        if (A == null || B == null)
            return 0;

        Collections.sort(A);
        Collections.sort(B);

        int max = 0;
        int n = A.size();

        for (int i = 0; i < n; i++) {
            max = Math.max(max, Math.abs(A.get(i) - B.get(i)));
        }

        return max;
    }
}
