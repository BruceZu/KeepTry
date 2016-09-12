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

package string;

/**
 * For a given input string, find the search pattern “1” and “2”  and print the number of occurrences which has highest matches
 */
public class MaxMatchNumberOfPatterns {
    /**
     * @param s  given string
     * @param p1 pattern 1
     * @param p2 pattern 2
     * @return
     */
    public static int maxMatchNumberOfPatterns(String s, String p1, String p2) {
        int repeat1 = 0, repeat2 = 0;
        for (int i = 0; i <= s.length() - Math.min(p1.length(), p2.length()); i++) {
            if (s.startsWith(p1, i)) {
                repeat1++;
            }
            if (s.startsWith(p2, i)) {
                repeat2++;
            }
        }

        return Math.max(repeat1, repeat2);
    }
}
