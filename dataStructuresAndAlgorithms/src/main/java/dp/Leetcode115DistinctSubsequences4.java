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
 *                0     1     2    3     4    5     6
 *          ''    r]    a]    b]   b]    b]   i]   t]  (S)
 *
 *     ''   1]    1]    1]    1]   1]    1]   1]   1]  0
 *
 *   0  r   0]    1]    1]    1]   1]    1]   1]   1]  1
 *
 *   1  a   0     0]    1]    1]   1]    1]   1]   1]  2
 *
 *   2  b   0     0]    0]    1]   2]    3]   3]   3]  3
 *
 *   3  b   0     0]    0]    0]   1]    3]   3]   3]  4
 *                                                          ^
 *   4  i   0     0]    0]    0]   0]    0]   3]   3]  5    |
 *                                                          |
 *   5  t   0     0]    0]    0]   0]    0]   0]   3]  6    |
 *                                                   index of max
 *    (T)
 *
 *    @see <a href="https://discuss.leetcode.com/topic/41268/2ms-java-solution-beats-100">fast</a>
 *    @see <a href="https://discuss.leetcode.com/topic/15675/my-0-ms-neat-dp-o-mxn-solution-in-c"> 1 dimension array</a>
 */
public class Leetcode115DistinctSubsequences4 {
    // logic is same, the max[] meaning is same as before
    // but deduce column by column. bottom to up using 1 dimension array.
    public static int numDistinct2(String S, String T) {
        int[] max = new int[T.length() + 1];
        max[0] = 1;

        for (int s = 0; s < S.length(); s++) {
            int t = s > T.length() - 1 ? T.length() - 1 : s;
            for (; t >= 0; t--) {
                if (S.charAt(s) == T.charAt(t)) {
                    max[t + 1] += max[t];
                }
            }
        }
        return max[T.length()];
    }

    public static void main(String[] args) {
        numDistinct("rabbbit", "rabbit");
    }

    /**
     * Fast. // TODO: 8/30/16 to see how this technical skill works in next steps, current step focus on ideas level
     * technical skills: An 2D int array is used to accelerate the lookup operation.
     */
    public static int numDistinct(String S, String T) {
        // map works as a hash table
        int[][] map = new int[256][T.length() + 1];
        char targetChar;
        for (int t = 0; t < T.length(); t++) {
            // map[c] is a list of all the positions character c appears
            // map[c][0] records how many times character c appears
            targetChar = T.charAt(t);

            map[targetChar][map[targetChar][0] + 1] = t + 1;
            map[targetChar][0]++;
        }

        // DP
        int[] max = new int[T.length() + 1];
        max[0] = 1;
        for (char s : S.toCharArray()) {
            if (map[s][0] != 0) {
                for (int i = map[s][0]; i > 0; i--) {
                    max[map[s][i]] += max[map[s][i] - 1];
                }
            }
        }
        return max[T.length()];
    }
}
