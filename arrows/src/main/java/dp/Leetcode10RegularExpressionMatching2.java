package dp;

/**
 * point:
 * <p>
 * the first time of 2D not always be false.
 * dp start from left to right.
 * <img src="../../resources/RegularExpression.png" height="400" width="600">
 *
 * @see <a href="https://www.youtube.com/watch?v=l3hda49XcDE">youtube</a>
 * @see <a href="https://leetcode.com/problems/regular-expression-matching/">leetcode</a>
 */
public class Leetcode10RegularExpressionMatching2 {
    // 27 ms, beat 46%
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        boolean T[][] = new boolean[str.length + 1][pattern.length + 1];

        T[0][0] = true;

        //Deals with patterns like a* or a*b* or a*b*c*
        for (int tColum = 1; tColum < T[0].length; tColum++) {
            int pi = tColum - 1;
            if (pattern[pi] == '*') {
                T[0][tColum] = T[0][tColum - 2];
            }
        }

        char curP, preP, curS;
        for (int stri = 1; stri < T.length; stri++) {
            for (int pi = 1; pi < T[0].length; pi++) {
                curP = pattern[pi - 1];
                curS = str[stri - 1];
                if (curP == '.' || curP == curS) {
                    T[stri][pi] = T[stri - 1][pi - 1];
                } else if (curP == '*') {
                    preP = pattern[pi - 2];
                    T[stri][pi] = T[stri][pi - 2]; // * is 0  // move left 2 step
                    if (preP == '.' || preP == curS) {
                        // T[i][j] = T[i][j-2] || T[i][j - 1] || T[i - 1][j]; // * is 0,1,more than 1.
                        T[stri][pi] |= T[stri - 1][pi]; // * is 0,1,more than 1. // move up 1 step
                    }
                } else {
                    T[stri][pi] = false;
                }
            }
        }
        return T[str.length][pattern.length];
    }

    public static void main(String args[]) {

        System.out.println(isMatch("", "a*b*c*"));


    }
}
