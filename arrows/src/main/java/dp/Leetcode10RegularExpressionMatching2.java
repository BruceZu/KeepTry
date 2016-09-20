package dp;

/**
 * point:
 * <p>
 * the first time of 2D not always be false.
 * dp start from left to right.
 *
 * @see <a href="https://www.youtube.com/watch?v=l3hda49XcDE">youtube</a>
 * @see <a href="https://leetcode.com/problems/regular-expression-matching/">leetcode</a>
 */
public class Leetcode10RegularExpressionMatching2 {
    // 27 ms, beat 46%
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        boolean T[][] = new boolean[str.length + 1][pattern.length + 1];

        T[0][0] = true;
        //Deals with patterns like a* or a*b* or a*b*c*
        for (int i = 1; i < T[0].length; i++) {
            if (pattern[i - 1] == '*') {
                T[0][i] = T[0][i - 2];
            }
        }

        char curP, preP, curS;
        for (int i = 1; i < T.length; i++) {
            for (int j = 1; j < T[0].length; j++) {
                curP = pattern[j - 1];
                curS = str[i - 1];
                if (curP == '.' || curP == curS) {
                    T[i][j] = T[i - 1][j - 1];
                } else if (curP == '*') {
                    preP = pattern[j - 2];
                    T[i][j] = T[i][j - 2]; // * is 0
                    if (preP == '.' || preP == curS) {
                        // T[i][j] = T[i][j-2] || T[i][j - 1] || T[i - 1][j]; // * is 0,1,more than 1.
                        T[i][j] |= T[i - 1][j]; // * is 0,1,more than 1.
                    }
                } else {
                    T[i][j] = false;
                }
            }
        }
        return T[str.length][pattern.length];
    }
}
