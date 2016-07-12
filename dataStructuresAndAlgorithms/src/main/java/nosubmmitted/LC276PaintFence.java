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

package nosubmmitted;

/**
 * 276. Paint Fence
 * https://leetcode.com/problems/paint-fence/
 * Difficulty: Easy <pre>
 * There is a fence with n posts, each post can be painted with one of the k colors.
 * <p/>
 * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
 * <p/>
 * Return the total number of ways you can paint the fence.
 * <p/>
 * Note:
 * n and k are non-negative integers.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Dynamic Programming
 * Hide Similar Problems (E) House Robber (M) House Robber II (M) Paint House (H) Paint House II
 */
public class LC276PaintFence {
    /**
     * beat 5%  , the fast one currently
     * O(n) time O(n) space can be reduced to O(1) space Java solution
     */
    public int numWays(int n, int k) {
        if (n < 1) return 0;
        if (n == 1) return k;
        /* _1_map means this node has k choices;
         * _2_map means this node has k - 1 choices;
         * _3_map means this node has 1 choice.
         * can be reduced to 3 integers.
         */
        int[] _1_map = new int[n + 1], _2_map = new int[n + 1], _3_map = new int[n + 1];
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= 3; j++) {
                if (i == n) {
                    switch (j) {
                        case 1:
                            _1_map[i] = k;
                            break;
                        case 2:
                            _2_map[i] = k - 1;
                            break;
                        case 3:
                            _3_map[i] = 1;
                            break;
                    }
                    continue;
                }
                switch (j) {
                    case 1:
                        _1_map[i] = k * (_3_map[i + 1] + _2_map[i + 1]);
                        break;
                    case 2:
                        _2_map[i] = (k - 1) * (_3_map[i + 1] + _2_map[i + 1]);
                        break;
                    case 3:
                        _3_map[i] = _2_map[i + 1];
                        break;
                }
            }
        }
        return _1_map[1];
    }


    /**
     * same fast as above <pre>
     * Explanation: As we can paint at most two adjacent posts with same color,
     * we can never have such a situation: For example: BBBW or WWWB or BWWW or BWWW. Here B = Black, W = White.
     * <p/>
     * Let's say we start painting from left side. Posts will look like this: |^|..|^|..|^|..|^|
     * <p/>
     * Here there are 4 posts. So n = 4.
     * <p/>
     * Now for painting, i = 1, Post #1, we have.
     * <p/>
     * 1. sameColor = 0 as there is no Post #(i-1)
     * 2. diffColor = k. because we can use any 1 of the k colors.
     * 3. total = sameColor + diffColor = 0 + k = k
     * At any given Post i, i > 1, we can have two ways
     * <p/>
     * 1. sameColor = Paint with same color as Post #(i-1)
     * 2. diffColor = Paint with different color than Post #(i-1)
     * 3. total = sameColor + diffColor
     * Or,
     * <p/>
     * 1. sameColor = diffColor*1 = diffColor of Post #(i-1)
     * 2. diffColor = (k-1)*total at Post #(i-1)
     * 3. total = sameColor + diffColor
     * For diffColor, We multiply total by k-1 because you can use any color except 1 color which was the previous
     * <p/>
     * Hi Thank you for the detailed explanation.
     * I get your solution except for calculating sameColor on each iteration.
     * How is it diffColor * 1 and what does the 1 signify?
     * <p/>
     * <p/>
     * reply:
     * For Post #(i-1), there are k = diffColor choices For Post(#1) if we want the same color as Post #(i-1),
     * we have only 1 way. This means in total we have k1 total choices if we want the same color.
     * 1 here signifies that for current Pos #i, we have only 1 choice if want same color as previous one.
     * Think like this. If you have only 4 colors, A, B, C, D. You painted Post #(i-2) with B.
     * So for coloring Post#(i) with same color as Post #(i-1).. You cannot have B at Post#(i-1).
     * Else B will be on three posts i-1, i, i+1. You need to go with a differentColor for Post#(i-1)
     * and then use that same color for Post#(i). We have 3 choices for #(i-1) i.e. A, C, D.
     * Whichever color we choose for i-1, For i we have to chose the same color for counting sameColor.
     * So sameColor = 31 = 3
     *
     * @param n
     * @param k
     * @return
     */
    public int numWays2(int n, int k) {
        if (k == 0 || n == 0 || (n > 2 && k == 1)) return 0;
        int sameColor = 0;
        int diffColor = k;
        int total = diffColor + sameColor;
        for (int i = 2; i <= n; i++) {
            sameColor = diffColor;
            diffColor = (k - 1) * total;
            total = (diffColor + sameColor);
        }
        return total;
    }


    /**
     * same fast as above <pre>
     * At a first glance, I just gave it a k * (int)Math.pow(n-1, k-1),
     * until I saw the n=2 & k=1 wrong answer, and found it interesting.
     * It's actually a DP problem,
     * where sum1 is computing not same color in current node,
     * and sum2 is painting the same color currently.
     *
     * @param n
     * @param k
     * @return
     */
    public int numWays3(int n, int k) {
        if (n < 1) return 0;
        int sum1 = k, sum2 = 0;
        while (--n > 0) {
            int temp = sum2;
            sum2 = sum1;
            sum1 = (sum1 + temp) * (k - 1);
        }
        return sum1 + sum2;
    }


    /**
     * same fast as above <pre>
     * <p/>
     *     We divided it into two cases.
     * <p/>
     * the last two posts have the same color, the number of ways to paint in this case is sameColorCounts.
     * <p/>
     * the last two posts have different colors, and the number of ways in this case is diffColorCounts.
     * <p/>
     * The reason why we have these two cases is that we can easily compute both of them, and that is all I do.
     * When adding a new post, we can use the same color as the last one (if allowed) or different color.
     * If we use different color, there're k-1 options, and the outcomes shoule belong to the diffColorCounts
     * category. If we use same color, there's only one option, and we can only do this when the last
     * two have different colors (which is the diffColorCounts). There we have our induction step.
     * <p/>
     * Here is an example, let's say we have 3 posts and 3 colors. The first two posts we have 9 ways
     * to do them, (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3). Now we know that
     * <p/>
     * diffColorCounts = 6;
     * And
     * <p/>
     * sameColorCounts = 3;
     * Now for the third post, we can compute these two variables like this:
     * <p/>
     * If we use different colors than the last one (the second one), these ways can be added into
     * diffColorCounts, so if the last one is 3, we can use 1 or 2, if it's 1, we can use 2 or 3, etc.
     * Apparently there are (diffColorCounts + sameColorCounts) * (k-1) possible ways.
     * <p/>
     * If we use the same color as the last one, we would trigger a violation in these three cases
     * (1,1,1), (2,2,2) and (3,3,3). This is because they already used the same color for the last
     * two posts. So is there a count that rules out these kind of cases? YES, the diffColorCounts.
     * So in cases within diffColorCounts, we can use the same color as the last one without
     * worrying about triggering the violation. And now as we append a same-color post to them,
     * the former diffColorCounts becomes the current sameColorCounts.
     * <p/>
     * Then we can keep going until we reach the n. And finally just sum up these two variables as result.
     * <p/>
     * Hope this would be clearer.
     */
    public int numWays4(int n, int k) {
        if (n == 0) return 0;
        else if (n == 1) return k;
        int diffColorCounts = k * (k - 1);
        int sameColorCounts = k;
        for (int i = 2; i < n; i++) {
            int temp = diffColorCounts;
            diffColorCounts = (diffColorCounts + sameColorCounts) * (k - 1);
            sameColorCounts = temp;
        }
        return diffColorCounts + sameColorCounts;
    }

    /**
     * f[i] defines if choosen a color for position i, with no constraint,
     * how many solutions will it have for position [0~i-1]. with no constraint
     * means i-1 is able to choose same color as i.
     * <p/>
     * f[i] =
     * <p/>
     * (k - 1) * f[i - 1], given a color for position i,
     * you can choose k - 1 colors without constraint.
     * <p/>
     * +(k - 1) * f[i - 2], for position i - 1
     * you chose same color as i, so for pos i - 2
     * you should have constraint that it should avoid having the same color,
     * so you have k - 1 color options for position i - 2.
     */
    public int numWays5(int n, int k) {
        if (n < 1 || k < 1) return 0;
        if (n == 1) return k;
        int[] f = new int[n];
        f[0] = 1;
        f[1] = k;
        for (int i = 2; i < n; i++)
            f[i] = (k - 1) * (f[i - 1] + f[i - 2]);
        return k * f[n - 1];
    }
}
