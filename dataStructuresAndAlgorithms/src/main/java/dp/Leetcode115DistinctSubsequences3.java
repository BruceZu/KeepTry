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

package dp;

public class Leetcode115DistinctSubsequences3 {
    /**
     * 2 dimension array -> 1 dimension array
     */
    public static int numDistinct2(String Source, String Target) {
        int[] r = new int[Source.length() + 1];
        for (int s = 0; s <= Source.length(); s++) {
            r[s] = 1;
        }
        int leftTop = 1, leftTopForNext;
        r[0] = 0; // care
        for (int t = 1; t <= Target.length(); t++) {
            for (int s = 1; s <= Source.length(); s++) {
                leftTopForNext = r[s];

                r[s] = r[s - 1];
                if (Source.charAt(s - 1) == Target.charAt(t - 1)) {
                    r[s] += leftTop;
                }
                leftTop = leftTopForNext;
            }
            leftTop = 0; // care
        }
        return r[Source.length()];
    }
}
