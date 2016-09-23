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
