package array;

import java.util.Scanner;

/**
 * n is odd
 * 1<= n <= 1000001
 * -10000 <= x <= 10000
 *
 * @see <a href="https://www.hackerrank.com/challenges/find-the-median/submissions/code/27801277">hacker rank</a>
 */
public class HackerrankFindtheMedian {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] ar = new int[s.nextInt()];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < ar.length; i++) {
            ar[i] = s.nextInt();
            min = Math.min(min, ar[i]);
            max = Math.max(max, ar[i]);

        }
        int[] num = new int[max - min + 1]; // ar may be have duplicated member
        for (int i = 0; i < ar.length; i++) {
            num[ar[i] - min]++;
        }
        int count = 0;
        for (int i = 0; i < num.length; i++) {
            if (num[i] != 0 && num[i] + count >= ar.length / 2 + 1) {// care: >=
                System.out.println(i + min);
                break;
            } else {
                count += num[i];
            }
        }
    }
}
