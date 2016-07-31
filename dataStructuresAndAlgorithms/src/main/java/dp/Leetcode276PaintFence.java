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
 * 276. Paint Fence
 *
 * Difficulty: Easy
 * There is a fence with n posts, each post can be painted with one of the k colors.
 *
 * You have to paint all the posts such that <strong>no more than</strong> two adjacent fence posts have the same color.
 *
 * Return the total number of ways you can paint the fence.
 *
 * Note:
 * n and k are <strong>non-negative</strong> integers.
 *
 * Company Tags
 *      Google
 * Tags
 *      Dynamic Programming
 * Similar Problems
 *      (E) House Robber
 *      (M) House Robber II
 *      (M) Paint House (H) Paint House II
 *  ===========================================================================
 *  Idea:
 *    iSame = preDiff
 *    iDiff = (pre)*(k-1)
 *
 *    as:   preDiff = prepre*(k-1)
 *    so:   f(i) = f(i-1)*(k-1) + f(i-2)*(k-1)
 *               = (f(i-1) + f(i-2)) * (k-1)
 *
 *  Cases:
 *      n    k   answer
 *      4    1    0
 *      1    1    1
 *      0    1    0
 *      1    0    0
 *      4    2    10
 *      1    2    2
 *      3    3    24
 *
 *  Run time O(n)
 *
 * @see  <a href="https://leetcode.com/problems/paint-fence/">leetcode</a>
 */
public class Leetcode276PaintFence {

    public static int numWays(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }

        int same = k; // same as the pre of n=2
        int diff = k * (k - 1); // diff with pre of n=2

        for (int i = 3; i <= n; i++) {
            int id = (diff + same) * (k - 1);
            int is = diff;
            same = is;
            diff = id;
        }
        return same + diff;
    }

    public int numWays2(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        int[] f = new int[n + 1];
        f[1] = k;
        f[2] = k * k;
        for (int i = 3; i <= n; i++) {
            f[i] = (k - 1) * (f[i - 1] + f[i - 2]);
        }
        return f[n];
    }
}
