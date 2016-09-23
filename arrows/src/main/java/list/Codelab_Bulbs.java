package list;

import java.util.ArrayList;

/**
 * @see <a href="https://codelab.interviewbit.com/problems/bulbs/">code lab</a>
 */
public class Codelab_Bulbs {
    public static int bulbs(ArrayList<Integer> a) {
        int state = 0, r = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) == state) {
                r++;
                state = 1 - state;
            }
        }
        return r;
    }
}
