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

/**
 * <pre>
 *        S = "rabbbit",
 *        T = "rabbit"
 *
 *  max subsequences by far:
 *    if T ='' : 1
 *    if S ='' : 0
 *    S length < T length : 0
 *    S length >= T length:
 *              if( cur char of S  == cur char of T){
 *                  max [row][column] =  max [row]  [column-1] +
 *                                       max [row-1][column-1]
 *              }else{
 *                  max [row][column] =  max [row][column-1]
 *              }
 *
 *        ''    r]    a]    b]   b]    b]   i]   t]  (S)
 *
 *    ''  1]    1]    1]    1]   1]    1]   1]   1]
 *
 *
 *    r   0]    1]    1]    1]   1]    1]   1]   1]
 *
 *    a   0     0]    1]    1]   1]    1]   1]   1]
 *
 *    b   0     0]    0]    1]   2]    3]   3]   3]
 *
 *    b   0     0]    0]    0]   1]    3]   3]   3]
 *
 *    i   0     0]    0]    0]   0]    0]   3]   3]
 *
 *    t   0     0]    0]    0]   0]    0]   0]   3]
 *
 *    (T)
 * @see <a href= "http://www.jianshu.com/p/719954a411c0">DP picture</a>
 */
public class Leetcode115DistinctSubsequences2 {
    public static int numDistinct(String S, String T) {
        int[][] max = new int[T.length() + 1][S.length() + 1]; // max subsequences by far [Ti][Sj]:
        for (int column = 0; column <= S.length(); column++) {
            max[0][column] = 1;
        }
        for (int t = 1; t <= T.length(); t++) {
            max[t][t - 1] = 0;
            for (int s = t; s <= S.length(); s++) {
                max[t][s] = max[t][s - 1];
                if (S.charAt(s - 1) == T.charAt(t - 1)) { // care it need -1 to map to String's current char
                    max[t][s] += max[t - 1][s - 1];
                }
            }
        }
        return max[T.length()][S.length()];
    }
}
