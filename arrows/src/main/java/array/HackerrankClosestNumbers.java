package array;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 2<= N <= 200000
 * -10^7 <= ai <= 10^7
 * ai!=aj,  1 <= i <= j <= N
 *
 * @see <a href="https://www.hackerrank.com/challenges/closest-numbers">hacker rank</a>
 */
public class HackerrankClosestNumbers {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] ar = new int[s.nextInt()];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = s.nextInt();
        }
        Arrays.sort(ar);
        int min = Integer.MAX_VALUE;
        int[] r = new int[ar.length * 2];
        int size = 0;
        for (int i = 1; i < ar.length; i++) {
            int dis = ar[i] - ar[i - 1];
            if (dis < min) {
                min = dis;
                size = 0;
                r[size++] = ar[i - 1];
                r[size++] = ar[i];

            } else if (dis == min) {
                r[size++] = ar[i - 1];
                r[size++] = ar[i];
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.print(r[i] + " ");
        }
    }
}
