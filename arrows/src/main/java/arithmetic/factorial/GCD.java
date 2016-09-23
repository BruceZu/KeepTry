package arithmetic.factorial;

public class GCD {
    /**
     * @see <a href="https://codelab.interviewbit.com/problems/gcd/">code lab</a>
     * m : 6
     * n : 9
     * <p>
     * GCD(m, n) : 3
     */
    public static int gcd(int A, int B) {
        if (A == 0) {
            return B;
        }
        int nextA = B%A;
        int nextB = A;
        return gcd(B % A, A);
    }
}
